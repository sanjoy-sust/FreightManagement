package com.fm.assignment.util;

import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.params.PlaceParam;

/**
 * Created by Lenovo on 13/10/2017.
 */
public class RequestAndParamBuilder {

    public static PlaceParam buildPlaceParam(PlaceResource resource) {
        return PlaceParam.builder()
                .id(resource.getId())
                .code(resource.getCode())
                .latitude(resource.getLatitude())
                .longitude(resource.getLongitude())
                .name(resource.getName())
                .build();
    }

    public static PlaceResource buildPlaceResource(PlaceParam param) {

        PlaceResource placeResource = new PlaceResource();
        placeResource.setId(param.getId());
        placeResource.setName(param.getName());
        placeResource.setCode(param.getCode());
        placeResource.setLatitude(param.getLatitude());
        placeResource.setLongitude(param.getLongitude());

        return placeResource;
    }
}
