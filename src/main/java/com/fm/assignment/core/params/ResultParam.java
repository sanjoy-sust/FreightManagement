package com.fm.assignment.core.params;

import com.fm.assignment.api.model.RouteResource;
import lombok.Getter;
import lombok.experimental.Builder;

import java.util.List;

/**
 * Created by Lenovo on 13/10/2017.
 */

@Builder
@Getter
public class ResultParam {
    private List<PathParam> route;
    private Double totalCost;
    private Long totalDuration;
}
