package com.fm.assignment.core.service;

import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.errorhandler.DatabaseException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since  07/10/2017.
 */
public interface PlaceService {
    Long addPlace(PlaceParam param) throws DatabaseException;
    Long updatePlace(long id,PlaceResource resource);
    List<PlaceParam> getAllNearestPlaces(Double latitude,Double longitude,Double distance);
    List<PlaceParam> getAllPlaces();
}
