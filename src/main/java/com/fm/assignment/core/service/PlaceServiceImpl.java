package com.fm.assignment.core.service;

import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.core.util.ParamAndEntityBuilder;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.mail.EmailService;
import com.vividsolutions.jts.awt.PointShapeFactory;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** This service is used to add Locations to location table
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@Service
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ParamAndEntityBuilder paramAndEntityBuilder;

    @Autowired
    private EmailService emailService;

    @Override
    public Long addPlace(PlaceParam placeParam) throws DatabaseException {
        PlaceEntity entity = new PlaceEntity();
        entity.setCode(placeParam.getCode());
        entity.setName(placeParam.getName());
        entity.setLongitude(placeParam.getLongitude());
        entity.setLatitude(placeParam.getLatitude());
        PlaceEntity savedPlaceEntity;
        try {
            savedPlaceEntity = placeRepository.save(entity);
            emailService.sendSimpleMessage("flopcoder.82@gmail.com","Test","Test Freight Management");
        } catch (Exception exp) {
            log.info("{}",exp);
            throw new DatabaseException(ErrorCodes.Feature.PLACE_ADD,
                    ErrorCodes.CODE.PLACE_SAVE_FAIL,ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PLACE_SAVE_FAIL));
        }
        return savedPlaceEntity.getId();
    }

    @Override
    public Long updatePlace(long id, PlaceResource resource) {
        return null;
    }

    @Override
    public List<PlaceParam> getAllNearestPlaces(Double latitude,Double longitude,Double distance) {
        List<PlaceParam> locationWithinParams = new ArrayList<>();
        List<PlaceEntity> locationsWithinDistance = placeRepository.findLocationWithin(latitude,longitude,distance);
        locationWithinParams.addAll(locationsWithinDistance.stream().map(paramAndEntityBuilder::buildPlaceParam).collect(Collectors.toList()));
        return locationWithinParams;
    }

    @Override
    public List<PlaceParam> getAllPlaces() {
        List<PlaceEntity> placeEntities = placeRepository.findAll();
        List<PlaceParam> placeParams = new ArrayList<>();
        placeParams.addAll(placeEntities.stream().map(paramAndEntityBuilder::buildPlaceParam).collect(Collectors.toList()));
        return placeParams;
    }
}
