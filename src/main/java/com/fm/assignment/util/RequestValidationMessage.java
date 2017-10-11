package com.fm.assignment.util;

/**
 * Created by Sanjoy on 10/11/2017.
 */
public class RequestValidationMessage {
    public static final String COST_FROM_GREATER_THAN_TO = "Cost from must be smaller than cost to";
    public static final String DURATION_FROM_GREATER_THAN_TO = "Duration from must be smaller than duration to";
    public static final String CONTAINER_SIZE_NOT_NULL= "Container size is mandatory field. It will be greater than 0";
    public static final String SOURCE_NOT_NULL= "Source can not be null or empty";
    public static final String DESTINATION_NOT_NULL= "Destination can not be null or empty";
    public static final String TRANSPORT_NOT_NULL= "Transport is mandatory field. Please give at least one transport type";
}
