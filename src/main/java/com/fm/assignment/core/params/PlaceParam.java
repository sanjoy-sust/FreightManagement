package com.fm.assignment.core.params;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Created by Lenovo on 13/10/2017.
 */
@Builder
@Getter
public class PlaceParam {
    private long id;
    private String name;
    private String code;
    private Double longitude;
    private Double latitude;
}
