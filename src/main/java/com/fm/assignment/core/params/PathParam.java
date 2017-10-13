package com.fm.assignment.core.params;

import com.fm.assignment.core.enums.TransportTypeEnum;
import lombok.Getter;
import lombok.experimental.Builder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Lenovo on 13/10/2017.
 */
@Builder
@Getter
public class PathParam {
    private long id;
    private String from;
    private String to;
    private Double cost;
    private long containerSize;
    @Enumerated(EnumType.STRING)
    private TransportTypeEnum routeType;
    private long duration;
}
