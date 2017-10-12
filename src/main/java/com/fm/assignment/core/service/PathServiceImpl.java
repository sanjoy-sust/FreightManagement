package com.fm.assignment.core.service;

import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.enums.TransportTypeEnum;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.errorhandler.RemoteApiException;
import com.fm.assignment.remote.LatLongService;
import com.fm.assignment.remote.LatLongModel;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import com.fm.assignment.api.model.*;
import com.fm.assignment.core.util.GraphBuilder;
import com.fm.assignment.core.util.PathFinder;
import com.fm.assignment.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class use for path related task.
 * @author Sanjoy Kumer Deb
 * @since  06/10/2017.
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

    /**
     * This will use to add path to path table.
     * @param pathResource
     * @return
     * @throws ResourceNotFoundException
     * @throws DatabaseException
     */
    @Override
    public long addPath(PathResource pathResource) throws ResourceNotFoundException, DatabaseException {
        log.info("Adding Path : {}",pathResource);
        PathEntity pathEntity = new PathEntity();
        pathEntity.setRouteType(pathResource.getRouteType());
        pathEntity.setContainerSize(pathResource.getContainerSize());
        pathEntity.setCost(pathResource.getCost());
        pathEntity.setDuration(pathResource.getDuration());
        PlaceEntity placeEntityFrom;
        PlaceEntity placeEntityTo;

        placeEntityFrom = placeRepository.findByName(pathResource.getFrom());
        placeEntityTo = placeRepository.findByName(pathResource.getTo());
        isPlaceNull(placeEntityFrom, placeEntityTo);
        pathEntity.setFromCode(placeEntityFrom);
        pathEntity.setToCode(placeEntityTo);
        PathEntity savedEntity;
        try {
            savedEntity = pathRepository.save(pathEntity);
        } catch (Exception exp) {
            throw new DatabaseException(ErrorCodes.Feature.PATH_ADD,
                    ErrorCodes.CODE.PATH_SAVE_FAIL, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PATH_SAVE_FAIL));
        }
        log.info("Path Added : {}",savedEntity);
        return savedEntity.getId();
    }

    /**
     * This method will use to add all possible path from source to destination.
     * If destination not found then use nearest point to find Path from source to destination.
     * @TODO need to introduce a layer like params layer. for example FindPathParam instead of FindPathRequest.
     * Its not recommended to use request object to service layer.
     * @param request
     * @return
     * @throws ResourceNotFoundException
     */
    @Override
    public FindPathResponse getAllPaths(FindPathRequest request) throws ResourceNotFoundException, RemoteApiException {
        PlaceEntity sourceObj = placeRepository.findByName(request.getSource());
        PlaceEntity destinationObj = placeRepository.findByName(request.getDestination());
        String sourceCode = sourceObj == null ? null : sourceObj.getCode();
        String destinationCode = destinationObj == null ? null : destinationObj.getCode();
        if (sourceCode == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.SOURCE_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.SOURCE_NOT_FOUND));
        }
        if (destinationCode == null) {
            destinationCode = findNearestLocationAsDestination(request);
        }
        if (destinationCode == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.DESTINATION_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }
        List<List<PathEntity>> allPaths = getPaths(request, sourceCode, destinationCode);
        if (allPaths == null || allPaths.size() == 0 ) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.PATH_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PATH_NOT_FOUND));
        }
        FindPathResponse response = buildFindPathResponse(allPaths, request);
        return response;
    }

    /**
     * If destination not available in our DB
     * then get nearest location
     * Here I use google map api to find latitude and longitude for which destination searched for.
     * Then find nearest locations available within 50KM from our predefined location.
     *
     * @TODO if nearest location not match then next nearest location to be calculated.
     * @TODO if two nearesr location find with same distance then both will pick.
     * @param request
     * @return
     * @throws Exception
     */
    private String findNearestLocationAsDestination(FindPathRequest request) throws RemoteApiException, ResourceNotFoundException {
        String destinationCode;LatLongModel latLongModel = latLongService.getLatLongPositions(request.getDestination());
        List<PlaceParam> allNearestPlaces = placeService.getAllNearestPlaces(
                latLongModel.getLatitude(),
                latLongModel.getLongitude(),
                Constants.MINIMUM_DISTANCE_TO_NEAR_LOCATION);
        if(allNearestPlaces.size() ==0)
        {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.DESTINATION_NOT_FOUND,
                    ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }

        double minimumDistance = Constants.MINIMUM_DISTANCE_TO_NEAR_LOCATION;
        PlaceParam targetPlace = null;
        for (PlaceParam placeParam : allNearestPlaces) {
            double lat2 = placeParam.getLatitude();
            double lang2 = placeParam.getLongitude();
            Double distance = latLongService.distance(
                    latLongModel.getLatitude(),
                    latLongModel.getLongitude(),
                    lat2,
                    lang2);
            if (distance < minimumDistance) {
                minimumDistance = distance;
                targetPlace = placeParam;
            }
        }
        destinationCode = targetPlace.getCode();
        return destinationCode;
    }

    /**
     * This method used to find all paths from DB Using transportationMode and container size.
     * @TODO here we can use factory pattern. I will implement it in future
     * @param request
     * @param sourceCode
     * @param destinationCode
     * @return
     * @throws ResourceNotFoundException
     */
    private List<List<PathEntity>> getPaths(FindPathRequest request, String sourceCode, String destinationCode) throws ResourceNotFoundException {
        GraphBuilder paths = new GraphBuilder();
        List<PathEntity> pathEntityList = new ArrayList<>();
        for (TransportTypeEnum transportTypeEnum : request.getModeOfTransports()) {
            switch (transportTypeEnum) {
                case All:
                    pathEntityList.addAll(pathRepository.findByContainerSize(request.getContainerSize()));
                    buildPath(paths, pathEntityList);
                    break;
                case Road:
                    pathEntityList.addAll(pathRepository.findByRouteTypeAndContainerSize(TransportTypeEnum.Road, request.getContainerSize()));
                    buildPath(paths, pathEntityList);
                    break;
                case Ocean:
                    pathEntityList.addAll(pathRepository.findByRouteTypeAndContainerSize(TransportTypeEnum.Ocean, request.getContainerSize()));
                    buildPath(paths, pathEntityList);
                    break;
                case Air:
                    pathEntityList.addAll(pathRepository.findByRouteTypeAndContainerSize(TransportTypeEnum.Air, request.getContainerSize()));
                    buildPath(paths, pathEntityList);
                    break;
            }

        }
        if (pathEntityList.size() <= 0) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.ROUTE_TYPE_OR_CONTAINER_NOT_MATCHED, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.ROUTE_TYPE_OR_CONTAINER_NOT_MATCHED));
        }
        PathFinder findAllPaths = new PathFinder(paths);
        return findAllPaths.getAllPaths(sourceCode, destinationCode);
    }

    /**
     * @param paths
     * @param pathEntityList
     */
    private void buildPath(GraphBuilder paths, List<PathEntity> pathEntityList) throws ResourceNotFoundException {
        for (PathEntity entity : pathEntityList) {
            PlaceEntity fromPlace = entity.getFromCode();
            PlaceEntity toPlace = entity.getToCode();
            isPlaceNull(fromPlace, toPlace);
            paths.addLocation(fromPlace.getCode());
            paths.addLocation(toPlace.getCode());
            paths.addPath(entity.getFromCode().getCode(), entity.getToCode().getCode(), entity);
        }
    }

    /**
     * @TODO Its not recommened to build response in service layer. I will convert corresponding response to DTO
     * @TODO and build response in controller
     * @param allPaths
     * @param request
     * @return
     * @throws ResourceNotFoundException
     */
    private FindPathResponse buildFindPathResponse(List<List<PathEntity>> allPaths, FindPathRequest request) throws ResourceNotFoundException {
        FindPathResponse response = new FindPathResponse();
        List<Results> results = new ArrayList<>();

        for (List<PathEntity> pathEntityList : allPaths) {
            List<RouteResponse> routes = new ArrayList<>();
            Double totalCost = 0.0;
            Long totalDuration = 0L;
            for (PathEntity entity : pathEntityList) {
                RouteResponse route = getRouteResponse(entity);
                routes.add(route);
                totalCost += entity.getCost();
                totalDuration += entity.getDuration();
            }
            filterPaths(request, results, routes, totalCost, totalDuration);

        }
        if (results.size() == 0) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.COST_DURATION_NOT_MATCHED, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.COST_DURATION_NOT_MATCHED));
        }
        response.setResults(results);
        return response;
    }

    /**
     * Filter path for cost limitation and duration limitation
     * Both fields are optional
     *
     * @param request
     * @param results
     * @param routes
     * @param totalCost
     * @param totalDuration
     */
    private void filterPaths(FindPathRequest request, List<Results> results, List<RouteResponse> routes, Double totalCost, Long totalDuration) {
        if ((request.getCostTo() == null && request.getCostFrom() == null)
                && (request.getDurationTo() == null && request.getDurationFrom() == null)) {
            buildResult(results, routes, totalCost, totalDuration);
        } else if ((request.getCostTo() != null && request.getCostFrom() != null)
                && (request.getDurationTo() == null && request.getDurationFrom() == null)) {
            if (totalCost >= request.getCostFrom() && totalCost <= request.getCostTo()) {
                buildResult(results, routes, totalCost, totalDuration);
            }
        } else if ((request.getCostTo() == null && request.getCostFrom() == null)
                && (request.getDurationTo() != null && request.getDurationFrom() != null)) {
            if (totalDuration >= request.getDurationFrom() && totalDuration <= request.getDurationTo()) {
                buildResult(results, routes, totalCost, totalDuration);
            }
        } else if ((request.getCostTo() != null && request.getCostFrom() != null)
                && (request.getDurationTo() != null && request.getDurationFrom() != null)) {
            if (totalDuration >= request.getDurationFrom() && totalDuration <= request.getDurationTo()) {
                if (totalCost >= request.getCostFrom() && totalCost <= request.getCostTo()) {
                    buildResult(results, routes, totalCost, totalDuration);
                }
            }
        }
    }

    /**
     * Build results from all routes.
     * @param results
     * @param routes
     * @param totalCost
     * @param totalDuration
     */
    private void buildResult(List<Results> results, List<RouteResponse> routes, Double totalCost, Long totalDuration) {
        Results result = new Results();
        result.setRoute(routes);
        result.setTotalCost(totalCost);
        result.setTotalDuration(totalDuration);
        results.add(result);
    }

    /**
     * Build route from Path which path get from DB
     * Build route response.
     * @param entity
     * @return
     */
    private RouteResponse getRouteResponse(PathEntity entity) throws ResourceNotFoundException {

        PlaceEntity fromPlace = entity.getFromCode();
        PlaceEntity toPlace = entity.getToCode();
        isPlaceNull(fromPlace, toPlace);
        RouteResponse route = new RouteResponse();
        route.setFrom(entity.getFromCode().getName());
        route.setTo(entity.getToCode().getName());
        route.setCost(entity.getCost());
        route.setTransportType(entity.getRouteType());
        route.setDuration(entity.getDuration());
        return route;
    }

    /**
     * This will check Source and Destination is valid or not
     * @param placeEntityFrom
     * @param placeEntityTo
     * @throws ResourceNotFoundException
     */
    private void isPlaceNull(PlaceEntity placeEntityFrom, PlaceEntity placeEntityTo) throws ResourceNotFoundException {
        if (placeEntityFrom == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.SOURCE_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.SOURCE_NOT_FOUND));
        }
        if (placeEntityTo == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.DESTINATION_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }
    }
}
