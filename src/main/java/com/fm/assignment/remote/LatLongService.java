package com.fm.assignment.remote;

import com.fm.assignment.errorhandler.RemoteApiException;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
public interface LatLongService {
    LatLongModel getLatLongPositions(String address) throws RemoteApiException;

    double distance(double lat1, double lon1, double lat2, double lon2);
}
