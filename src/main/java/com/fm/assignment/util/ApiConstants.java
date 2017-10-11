package com.fm.assignment.util;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
public class ApiConstants {
    /*Added constants for google map api to find latitude and longitude of an address*/
    public static final String GOOGLE_MAP_URL = "http://maps.googleapis.com/maps/api/geocode/xml";
    public static final String GOOGLE_MAP_ADDRESS_PREFIX_URL = "?address=";
    public static final String GOOGLE_MAP_ADDRESS_SUFFIX_URL = "&sensor=true";
    public static final String XPATH_STATUS_FIELD = "/GeocodeResponse/status";
    public static final String XPATH_LATITUDE_FIELD = "//geometry/location/lat";
    public static final String XPATH_LONGITUDE_FIELD = "//geometry/location/lng";
}
