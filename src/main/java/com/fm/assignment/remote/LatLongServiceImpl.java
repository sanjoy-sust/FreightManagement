package com.fm.assignment.remote;

import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.errorhandler.RemoteApiException;
import com.fm.assignment.util.ApiConstants;
import com.fm.assignment.util.ApiUrlBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author Sanjoy Kumer Deb
 * @since 08/10/2017.
 */
@Component
public class LatLongServiceImpl implements LatLongService {
    /**
     * Using google map api find latitude longitude of an address
     *
     * @param address
     * @return
     * @throws RemoteApiException
     */
    @Override
    public LatLongModel getLatLongPositions(String address) throws RemoteApiException {
        LatLongModel latLongModel = null;
        try {
            int responseCode;
            URL url = new URL(ApiUrlBuilder.buildGoogleMapApiUrl(address));
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.connect();
            responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpStatus.OK.value()) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(httpConnection.getInputStream());
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile(ApiConstants.XPATH_STATUS_FIELD);
                String status = (String) expr.evaluate(document, XPathConstants.STRING);
                if (status.equals(HttpStatus.OK.name())) {
                    expr = xpath.compile(ApiConstants.XPATH_LATITUDE_FIELD);
                    String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
                    expr = xpath.compile(ApiConstants.XPATH_LONGITUDE_FIELD);
                    String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
                    latLongModel = LatLongModel.builder().latitude(Double.valueOf(latitude)).longitude(Double.valueOf(longitude)).build();
                } else {
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (IOException
                | ParserConfigurationException
                | XPathExpressionException
                | SAXException
                exp) {
            throw new RemoteApiException(ErrorCodes.Feature.UNKNOWN,
                    ErrorCodes.CODE.REMOTE_API_FAIL, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }
        if(latLongModel == null)
        {
            throw new RemoteApiException(ErrorCodes.Feature.UNKNOWN,
                    ErrorCodes.CODE.REMOTE_API_FAIL, ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.DESTINATION_NOT_FOUND));
        }
        return latLongModel;
    }

    /**
     * To calculate distance between two latitude and longitude.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    @Override
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
