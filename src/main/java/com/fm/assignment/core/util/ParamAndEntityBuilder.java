package com.fm.assignment.core.util;

import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.params.PlaceParam;

/**
 * Created by Lenovo on 13/10/2017.
 */
public class ParamAndEntityBuilder {
    public static PlaceParam buildPlaceParam(PlaceEntity placeEntity) {
        return PlaceParam.builder()
                .id(placeEntity.getId())
                .code(placeEntity.getCode())
                .latitude(placeEntity.getLatitude())
                .longitude(placeEntity.getLongitude())
                .name(placeEntity.getName())
                .build();
    }

    public static PlaceEntity buildPlaceResource(PlaceParam param) {

        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setId(param.getId());
        placeEntity.setName(param.getName());
        placeEntity.setCode(param.getCode());
        placeEntity.setLatitude(param.getLatitude());
        placeEntity.setLongitude(param.getLongitude());

        return placeEntity;
    }
}
