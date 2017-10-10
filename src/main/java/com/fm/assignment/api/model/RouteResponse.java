package com.fm.assignment.api.model;

import com.fm.assignment.core.enums.TransportTypeEnum;
import lombok.Data;
import lombok.experimental.Builder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Lenovo on 06/10/2017.
 */
@Data
public class RouteResponse {
    String from;
    String to;
    @Enumerated(EnumType.STRING)
    TransportTypeEnum transportType;
    Double cost;
    Long duration;
}
