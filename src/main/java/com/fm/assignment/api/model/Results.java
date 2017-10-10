package com.fm.assignment.api.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Lenovo on 07/10/2017.
 */
@Data
public class Results {
    private List<RouteResponse> route;
    private Double totalCost;
    private Long totalDuration;

}
