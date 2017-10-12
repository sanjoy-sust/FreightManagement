package com.fm.assignment.api.model;

import com.fm.assignment.core.enums.TransportTypeEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@Data
public class PathResource {
    private long id;
    private String from;
    private String to;
    private Double cost;
    private long containerSize;
    @Enumerated(EnumType.STRING)
    private TransportTypeEnum routeType;
    private long duration;
}
