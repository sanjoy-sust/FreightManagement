package com.fm.assignment.core.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import com.vividsolutions.jts.geom.Point;

/**
 * Here code field just use for unique Place Id.
 * Although in this project name also unique.
 * Keeping it for next extended implementation.
 * And currently used as foreign key of PathEntity.
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@Data
@Entity(name = "Place")
public class PlaceEntity extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CODE")
    private String code;
    @Column(name = "LONGITUDE")
    private Double longitude;
    @Column(name = "LATITUDE")
    private Double latitude;
}
