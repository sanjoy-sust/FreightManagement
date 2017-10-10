package com.fm.assignment.api.model;

import lombok.Data;

/**
 * Created by Lenovo on 07/10/2017.
 */
@Data
public class PlaceResource {
    private long id;
    private String name;
    private String code;
    private Double longitude;
    private Double latitude;
}
