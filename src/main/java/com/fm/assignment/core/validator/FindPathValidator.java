package com.fm.assignment.core.validator;

import com.fm.assignment.api.model.FindPathRequest;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.params.FindPathParam;
import com.fm.assignment.core.params.PathParam;
import com.fm.assignment.core.params.ResultParam;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.errorhandler.ResourceNotFoundException;

import java.util.List;

/**
 * Created by Lenovo on 13/10/2017.
 */
public class FindPathValidator {

    /**
     * This will check Source and Destination is valid or not
     * @param placeEntityFrom
     * @param placeEntityTo
     * @throws ResourceNotFoundException
     */
    public static void isPlaceNull(PlaceEntity placeEntityFrom, PlaceEntity placeEntityTo) throws ResourceNotFoundException {
        if (placeEntityFrom == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.SOURCE_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.SOURCE_NOT_FOUND));
        }
        if (placeEntityTo == null) {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.DESTINATION_NOT_FOUND, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }
    }

    /**
     * Filter path for cost limitation and duration limitation
     * Both fields are optional
     *
     * @param param
     * @param results
     * @param routes
     * @param totalCost
     * @param totalDuration
     */
    public static void filterPaths(FindPathParam param, List<ResultParam> results, List<PathParam> routes,
                             Double totalCost, Long totalDuration) {
        if ((param.getCostTo() == null && param.getCostFrom() == null)
                && (param.getDurationTo() == null && param.getDurationFrom() == null)) {
            buildResult(results, routes, totalCost, totalDuration);
        } else if ((param.getCostTo() != null && param.getCostFrom() != null)
                && (param.getDurationTo() == null && param.getDurationFrom() == null)) {
            if (totalCost >= param.getCostFrom() && totalCost <= param.getCostTo()) {
                buildResult(results, routes, totalCost, totalDuration);
            }
        } else if ((param.getCostTo() == null && param.getCostFrom() == null)
                && (param.getDurationTo() != null && param.getDurationFrom() != null)) {
            if (totalDuration >= param.getDurationFrom() && totalDuration <= param.getDurationTo()) {
                buildResult(results, routes, totalCost, totalDuration);
            }
        } else if ((param.getCostTo() != null && param.getCostFrom() != null)
                && (param.getDurationTo() != null && param.getDurationFrom() != null)) {
            if (totalDuration >= param.getDurationFrom() && totalDuration <= param.getDurationTo()) {
                if (totalCost >= param.getCostFrom() && totalCost <= param.getCostTo()) {
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
    private static void buildResult(List<ResultParam> results, List<PathParam> routes, Double totalCost, Long totalDuration) {

        ResultParam result = ResultParam.builder()
                .route(routes)
                .totalCost(totalCost)
                .totalDuration(totalDuration)
                .build();
        results.add(result);
    }


}
