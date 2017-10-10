package com.fm.assignment.remote;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Builder;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
@Builder
@Getter
public class LatLongModel {
    private Double latitude;
    private Double longitude;
}
