package com.fm.assignment.core.service;

import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.enums.AttachmentYnEnum;
import com.fm.assignment.core.enums.MailStatusEnum;
import com.fm.assignment.core.enums.TransportTypeEnum;
import com.fm.assignment.core.params.*;
import com.fm.assignment.core.util.ParamAndEntityBuilder;
import com.fm.assignment.core.validator.FindPathValidator;
import com.fm.assignment.errorhandler.RemoteApiException;
import com.fm.assignment.mail.EmailService;
import com.fm.assignment.remote.LatLongService;
import com.fm.assignment.remote.LatLongModel;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import com.fm.assignment.core.util.GraphBuilder;
import com.fm.assignment.core.util.PathFinder;
import com.fm.assignment.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class use for path related task.
 *
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@Service
@Slf4j
public class PathServiceImpl implements PathService {
    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private LatLongService latLongService;

    @Autowired
    private MailBoxService mailBoxService;

    @Autowired
    private ParamAndEntityBuilder paramAndEntityBuilder;

    /**
     * This will use to add path to path table.
     *
     * @param pathParam
     * @return
     * @throws ResourceNotFoundException
     * @throws DatabaseException
     */
    @Override
    @Transactional
    public long addPath(PathParam pathParam) throws ResourceNotFoundException, DatabaseException {
        log.info("Adding Path : {}", pathParam);
        PathEntity pathEntity = paramAndEntityBuilder.buildPathEntity(pathParam);
        PathEntity savedEntity;
        try {
            savedEntity = pathRepository.save(pathEntity);
            addMailBox(savedEntity);
        } catch (Exception exp) {
            throw new DatabaseException(ErrorCodes.Feature.PATH_ADD,
                    ErrorCodes.CODE.PATH_SAVE_FAIL, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PATH_SAVE_FAIL));
        }
        log.info("Path Added : {}", savedEntity);
        return savedEntity.getId();
    }

    /**
     * This method will use to add all possible path from source to destination.
     * If destination not found then use nearest point to find Path from source to destination.
     *
     * @param param
     * @return
     * @throws ResourceNotFoundException
     * @TODO need to introduce a layer like params layer. for example FindPathParam instead of FindPathRequest.
     * Its not recommended to use request object to service layer.
     */
    @Override
    public List<ResultParam> getAllPaths(FindPathParam param) throws ResourceNotFoundException, RemoteApiException {
        PlaceEntity sourceObj = placeRepository.findByName(param.getSource());
        PlaceEntity destinationObj = placeRepository.findByName(param.getDestination());
        String sourceCode = sourceObj == null ? null : sourceObj.getCode();
        String destinationCode = destinationObj == null ? null : destinationObj.getCode();
        if (sourceCode == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.SOURCE_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.SOURCE_NOT_FOUND));
        }

        List<List<PathEntity>> allPaths = new ArrayList<>();
        if (destinationCode != null) {
            /*Find All posible path from source to destination*/
            allPaths = getPaths(param, sourceCode, destinationCode);
        }
        /*If destination not found then find near location for destination within 50KM.*/
        else if (destinationCode == null) {
            List<PlaceParam> derivedLocationAsDestination = findDerivedLocationAsDestination(param);
            for (PlaceParam placeParam : derivedLocationAsDestination) {
                List<List<PathEntity>> paths = getPaths(param, sourceCode, placeParam.getCode());
                allPaths.addAll(paths);
            }
        }


        if (allPaths == null || allPaths.size() == 0) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.PATH_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PATH_NOT_FOUND));
        }

        /**/
        List<ResultParam> resultsResources = calculateTotalCostAndDurationOfRoutes(allPaths, param);
        return resultsResources;
    }

    /**
     * If destination not available in our DB
     * then get nearest location
     * Here I use google map api to find latitude and longitude for which destination searched for.
     * Then find nearest locations available within 50KM from our predefined location.
     *
     * @param param
     * @return
     * @throws Exception
     * @TODO if nearest location not match then next nearest location to be calculated.
     * @TODO if two nearest location find with same distance then both will pick.
     */
    private List<PlaceParam> findDerivedLocationAsDestination(FindPathParam param) throws RemoteApiException, ResourceNotFoundException {
        LatLongModel latLongModel = latLongService.getLatLongPositions(param.getDestination());
        List<PlaceParam> allDerivedLocations = placeService.getAllNearestPlaces(
                latLongModel.getLatitude(),
                latLongModel.getLongitude(),
                Constants.MINIMUM_DISTANCE_TO_NEAR_LOCATION);
        if (allDerivedLocations.size() == 0) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.DESTINATION_NOT_FOUND,
                    ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }
        return allDerivedLocations;
    }

    /**
     * This method used to find all paths from DB Using transportationMode and container size.
     *
     * @param param
     * @param sourceCode
     * @param destinationCode
     * @return
     * @throws ResourceNotFoundException
     * @TODO here we can use factory pattern. I will implement it in future
     */
    private List<List<PathEntity>> getPaths(FindPathParam param, String sourceCode, String destinationCode) throws ResourceNotFoundException {
        GraphBuilder paths = new GraphBuilder();
        List<PathEntity> pathEntityList = new ArrayList<>();
        for (TransportTypeEnum transportTypeEnum : param.getModeOfTransports()) {
            switch (transportTypeEnum) {
                case All:
                    pathEntityList.addAll(pathRepository.findByContainerSize(param.getContainerSize()));
                    buildGraph(paths, pathEntityList);
                    break;
                default:
                    pathEntityList.addAll(pathRepository.findByRouteTypeAndContainerSize(transportTypeEnum, param.getContainerSize()));
                    buildGraph(paths, pathEntityList);
                    break;
            }

        }
        /*If pathEntityList size is zero that means its contain no path for requested routType and containerSize*/
        if (pathEntityList.size() <= 0) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.ROUTE_TYPE_OR_CONTAINER_NOT_MATCHED, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.ROUTE_TYPE_OR_CONTAINER_NOT_MATCHED));
        }
        PathFinder findAllPaths = new PathFinder(paths);
        return findAllPaths.getAllPaths(sourceCode, destinationCode);
    }

    /**
     * @param paths          //pass to build graph for different combination of routes
     * @param pathEntityList
     */
    private void buildGraph(GraphBuilder paths, List<PathEntity> pathEntityList) throws ResourceNotFoundException {
        for (PathEntity entity : pathEntityList) {
            PlaceEntity fromPlace = entity.getFromCode();
            PlaceEntity toPlace = entity.getToCode();
            FindPathValidator.isPlaceNull(fromPlace, toPlace);
            paths.addLocation(fromPlace.getCode());
            paths.addLocation(toPlace.getCode());
            paths.addPath(entity.getFromCode().getCode(), entity.getToCode().getCode(), entity);
        }
    }

    /**
     * @param allPaths
     * @param param
     * @return
     * @throws ResourceNotFoundException
     */
    private List<ResultParam> calculateTotalCostAndDurationOfRoutes(List<List<PathEntity>> allPaths, FindPathParam param) throws ResourceNotFoundException {
        List<ResultParam> results = new ArrayList<>();

        for (List<PathEntity> pathEntityList : allPaths) {
            List<PathParam> routes = new ArrayList<>();
            Double totalCost = 0.0;
            Long totalDuration = 0L;
            for (PathEntity entity : pathEntityList) {
                PathParam route = paramAndEntityBuilder.buildPathParam(entity);
                routes.add(route);
                totalCost += entity.getCost();
                totalDuration += entity.getDuration();
            }
            /*Filter path against cost/duration range*/
            FindPathValidator.filterPaths(param, results, routes, totalCost, totalDuration);

        }
        if (results.size() == 0) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.COST_DURATION_NOT_MATCHED, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.COST_DURATION_NOT_MATCHED));
        }
        return results;
    }

    /**
     * This is for temporary when message template feature add then this code will remove
     *
     * @param pathEntity
     */
    private void addMailBox(PathEntity pathEntity) {
        MailBoxParam param = new MailBoxParam();
        param.setToEmail("sanjoyd.cse@gmail.com");
        param.setSubject("Freight Management Path Added.");
        param.setText("<html><body><h1>Path added From " + pathEntity.getFromCode().getName() + " to " + pathEntity.getToCode().getName() + "</h1></br>" +
                "<p>" +
                "Route Type : " + pathEntity.getRouteType().name() + "</br>" +
                "Container Size : " + pathEntity.getContainerSize() + "</br>" +
                "</p>" +
                "</body></html>");
        param.setAttachmentYN(AttachmentYnEnum.YES);
        param.setAttachmentName("teddy.jpeg");
        param.setStatus(MailStatusEnum.PENDING);
        mailBoxService.addMailBox(param);
    }
}
