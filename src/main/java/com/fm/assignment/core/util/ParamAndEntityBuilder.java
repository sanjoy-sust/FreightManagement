package com.fm.assignment.core.util;

import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.params.PathParam;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.core.validator.FindPathValidator;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Lenovo on 13/10/2017.
 */
@Component
public class ParamAndEntityBuilder {
    @Autowired
    private PlaceRepository placeRepository;

    public PlaceParam buildPlaceParam(PlaceEntity placeEntity) {
        return PlaceParam.builder()
                .id(placeEntity.getId())
                .code(placeEntity.getCode())
                .latitude(placeEntity.getLatitude())
                .longitude(placeEntity.getLongitude())
                .name(placeEntity.getName())
                .build();
    }

    public PlaceEntity buildPlaceResource(PlaceParam param) {

        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setId(param.getId());
        placeEntity.setName(param.getName());
        placeEntity.setCode(param.getCode());
        placeEntity.setLatitude(param.getLatitude());
        placeEntity.setLongitude(param.getLongitude());

        return placeEntity;
    }

    public PathParam buildPathParam(PathEntity entity) {
        return PathParam.builder()
                .id(entity.getId())
                .from(entity.getFromCode() == null ? null : entity.getFromCode().getName())
                .to(entity.getToCode() == null ? null : entity.getToCode().getName())
                .containerSize(entity.getContainerSize())
                .cost(entity.getCost())
                .duration(entity.getDuration())
                .routeType(entity.getRouteType())
                .build();

    }

    public PathEntity buildPathEntity(PathParam param) throws ResourceNotFoundException {
        PathEntity pathEntity = new PathEntity();
        pathEntity.setId(param.getId());
        PlaceEntity from = placeRepository.findByName(param.getFrom());
        PlaceEntity to = placeRepository.findByName(param.getFrom());
        FindPathValidator.isPlaceNull(from, to);
        pathEntity.setFromCode(from);
        pathEntity.setToCode(to);
        pathEntity.setContainerSize(param.getContainerSize());
        pathEntity.setCost(param.getCost());
        pathEntity.setDuration(param.getDuration());
        pathEntity.setRouteType(param.getRouteType());
        return pathEntity;
    }
}
