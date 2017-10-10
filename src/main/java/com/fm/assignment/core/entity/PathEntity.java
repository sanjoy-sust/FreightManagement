package com.fm.assignment.core.entity;

import com.fm.assignment.core.enums.TransportTypeEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * This Entity is for Path from one place to another place.
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@Data
@Entity(name = "PATH")
public class PathEntity extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "FROM_CODE", referencedColumnName = "CODE")
    private PlaceEntity fromCode;

    @ManyToOne
    @JoinColumn(name = "TO_CODE", referencedColumnName = "CODE")
    private PlaceEntity toCode;

    @Column(name = "COST_EURO")
    private Double cost;

    @Column(name = "CONTAINER_SIZE_FEET")
    private long containerSize;

    @Column(name = "ROUTE_TYPE")
    @Enumerated(EnumType.STRING)
    private TransportTypeEnum routeType;

    @Column(name = "DURATION_DAY")
    private long duration;
}
