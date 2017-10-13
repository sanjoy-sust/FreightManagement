package com.fm.assignment.util;

import com.fm.assignment.api.model.FindPathRequest;
import com.fm.assignment.api.model.PathResource;
import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.api.model.RouteResource;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.params.FindPathParam;
import com.fm.assignment.core.params.PathParam;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.errorhandler.ResourceNotFoundException;

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

    public static PathParam buildPathParam(PathResource resource) {
        return PathParam.builder()
                .id(resource.getId())
                .from(resource.getFrom())
                .to(resource.getTo())
                .containerSize(resource.getContainerSize())
                .cost(resource.getCost())
                .duration(resource.getDuration())
                .routeType(resource.getRouteType())
                .build();
    }

    public static PathResource buildPathResource(PathParam param) {
        PathResource pathResource = new PathResource();
        pathResource.setId(param.getId());
        pathResource.setFrom(param.getFrom());
        pathResource.setTo(param.getTo());
        pathResource.setContainerSize(param.getContainerSize());
        pathResource.setCost(param.getCost());
        pathResource.setDuration(param.getDuration());
        pathResource.setRouteType(param.getRouteType());

        return pathResource;
    }

    public static FindPathParam buildFindPathParam(FindPathRequest request) {
        return FindPathParam.builder()
                .source(request.getSource())
                .destination(request.getDestination())
                .containerSize(request.getContainerSize())
                .modeOfTransports(request.getModeOfTransports())
                .costFrom(request.getCostFrom())
                .costTo(request.getCostTo())
                .durationFrom(request.getDurationFrom())
                .durationTo(request.getDurationTo())
                .build();
    }

    public static FindPathRequest buildFindPathRequest(FindPathParam param) {
        FindPathRequest request = new FindPathRequest();
        request.setSource(param.getSource());
        request.setDestination(param.getDestination());
        request.setContainerSize(param.getContainerSize());
        request.setModeOfTransports(param.getModeOfTransports());
        request.setCostFrom(param.getCostFrom());
        request.setCostTo(param.getCostTo());
        request.setDurationFrom(param.getDurationFrom());
        request.setDurationTo(param.getDurationTo());

        return request;
    }

    public static RouteResource buildRoutResource(PathParam pathParam) {

        RouteResource route = new RouteResource();
        route.setFrom(pathParam.getFrom());
        route.setTo(pathParam.getTo());
        route.setCost(pathParam.getCost());
        route.setTransportType(pathParam.getRouteType());
        route.setDuration(pathParam.getDuration());
        return route;
    }
}
