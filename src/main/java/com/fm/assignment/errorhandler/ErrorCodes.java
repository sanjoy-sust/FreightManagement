package com.fm.assignment.errorhandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sanjoy Kumer Deb
 * @since  06/10/2017.
 */
public class ErrorCodes {
    public interface Feature{
        String PATH_FIND = "Path Find";
        String PATH_ADD = "Path Add";
        String PLACE_ADD = "Place Add";
        String UNKNOWN = "Unknown";
    }

    public interface CODE{
        String PATH_NOT_FOUND = "ERR-4200";
        String SOURCE_NOT_FOUND = "ERR-4201";
        String DESTINATION_NOT_FOUND = "ERR-4202";
        String COST_DURATION_NOT_MATCHED = "ERR-4203";
        String ROUTE_TYPE_OR_CONTAINER_NOT_MATCHED = "ERR-4204";
        String PATH_SAVE_FAIL = "ERR-4301";
        String PLACE_SAVE_FAIL = "ERR-4302";
        String REMOTE_API_FAIL = "ERR-4000";
        String METHOD_ARG_NOT_VALID = "ERR-4001";
        String GENERIC_ERROR = "ERR-4444";
    }

    public static final Map<String, String> REASON_MAP = new HashMap<String, String>();

    static {
        REASON_MAP.put(CODE.PATH_NOT_FOUND,"Path not found for specific source and destination");
        REASON_MAP.put(CODE.SOURCE_NOT_FOUND,"Source not correct. Please try with another");
        REASON_MAP.put(CODE.DESTINATION_NOT_FOUND,"Destination not correct. Please try with another");
        REASON_MAP.put(CODE.PATH_SAVE_FAIL,"Path add fail. probably UK exception. Please check data");
        REASON_MAP.put(CODE.PLACE_SAVE_FAIL,"Place add fail. Probably UK exception. Check Data");
        REASON_MAP.put(CODE.REMOTE_API_FAIL,"Google Map remote call error. Please check internet connection.");
        REASON_MAP.put(CODE.GENERIC_ERROR,"Internal Server Error!!!. Please communicate with vendor");
        REASON_MAP.put(CODE.METHOD_ARG_NOT_VALID,"Please check mandatory/optional fields of your request.");
        REASON_MAP.put(CODE.COST_DURATION_NOT_MATCHED,"Please check Cost or Duration Range. Path not found for request.");
        REASON_MAP.put(CODE.ROUTE_TYPE_OR_CONTAINER_NOT_MATCHED,"Please check transportation mode and container size. Path not found for request.");
    }
}
