package com.example.greenstoreproject.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;

@Service
public class GeoCodingService {
    private GeoApiContext geoApiContext;

    public GeoCodingService() {
        this.geoApiContext = new GeoApiContext.Builder().apiKey("AIzaSyAjRUxjNVufm969kWFcrLNj8YCvHk-IByU").build();

    }

    public String getAddressFromCoordinates(double latitude, double longitude) {
        try {

            GeocodingResult[] results = GeocodingApi.reverseGeocode(geoApiContext, new LatLng(latitude, longitude)).await();
            if(results != null && results.length > 0) {
                return results[0].formattedAddress;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
