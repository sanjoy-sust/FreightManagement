package com.fm.assignment.core.service;

import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.vividsolutions.jts.awt.PointShapeFactory;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** This service is used to add Locations to location table
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Long addPlace(PlaceResource resource) throws DatabaseException {
        PlaceEntity entity = new PlaceEntity();
        entity.setCode(resource.getCode());
        entity.setName(resource.getName());
        entity.setLongitude(resource.getLongitude());
        entity.setLatitude(resource.getLatitude());
        PlaceEntity savedPlaceEntity;
        try {
            savedPlaceEntity = placeRepository.save(entity);
        } catch (Exception exp) {
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
    public List<PlaceEntity> getAllNearestPlaces(Double latitude,Double longitude,Double distance) {
        List<PlaceEntity> locationWithin = placeRepository.findLocationWithin(latitude,longitude,distance);
        return locationWithin;
    }
}
