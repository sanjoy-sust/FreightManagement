package com.fm.assignment.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
public class ApiUrlBuilder {
    /**
     * Build google map api url with address
     * @param address
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String buildGoogleMapApiUrl(String address) throws UnsupportedEncodingException {
            return ApiConstants.GOOGLE_MAP_URL
                    .concat(ApiConstants.GOOGLE_MAP_ADDRESS_PREFIX_URL)
                    .concat(URLEncoder.encode(address, "UTF-8"))
                    .concat(ApiConstants.GOOGLE_MAP_ADDRESS_SUFFIX_URL);
    }

}
