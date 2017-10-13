package com.fm.assignment.api.model;

import lombok.Data;

import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@Data
public class ResultsResource {
    private List<RouteResource> route;
    private Double totalCost;
    private Long totalDuration;

}
