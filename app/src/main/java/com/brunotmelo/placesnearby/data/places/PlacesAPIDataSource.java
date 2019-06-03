package com.brunotmelo.placesnearby.data.places;

import android.location.Location;
import com.brunotmelo.placesnearby.data.places.datatypes.PlacesAPIDetailsResponse;
import com.brunotmelo.placesnearby.data.places.datatypes.PlacesAPINearbyResponse;
import com.brunotmelo.placesnearby.data.places.util.PlaceTypeEnum;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class PlacesAPIDataSource {

    private static String API_KEY = "AIzaSyBUuLGjaOZFA1Gscz8wEq6dEZrj4qUJUu8";
    private String nearbyCallUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private String detailsCallUrl = "https://maps.googleapis.com/maps/api/place/details/json?";

    private Gson gson;

    public PlacesAPIDataSource() {
        gson = new Gson();
    }

    public PlacesAPINearbyResponse getNearbyPlaces(Location currentLocation, PlaceTypeEnum placeType) {
        String filterString = placeType.getFilter();
        String output = makeNearbyCall(currentLocation.getLatitude(), currentLocation.getLongitude(), filterString);
        PlacesAPINearbyResponse response = gson.fromJson(output, PlacesAPINearbyResponse.class);
        return response;
    }

    // To get the rest of the details. Every elementin the repository would run this
    // the view would be updated when the livedata Returned
    public PlacesAPIDetailsResponse getDetailsPlace(String placeId) {
        String output = makeDetailsCall(placeId);
        PlacesAPIDetailsResponse response = gson.fromJson(output, PlacesAPIDetailsResponse.class);
        return response;
    }

    private String makeDetailsCall(String placeId) {
        try {
            String urlText = detailsCallUrl;
            urlText = urlText.concat("key=" + API_KEY);
            urlText = urlText.concat("&placeid=" + placeId);
            urlText = urlText.concat("&fields=formatted_phone_number,address_components");

            URL url = new URL(urlText);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = "";
            String aux;
            while ((aux = br.readLine()) != null) {
                output = output.concat(aux);
            }
            conn.disconnect();
            return output;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // http://localhost:8080/RESTfulExample/json/product/get
    private String makeNearbyCall(double latitude, double longitude, String typeFilter) {
        try {
            String urlText = nearbyCallUrl;
            urlText = urlText.concat("key=" + API_KEY);
            urlText = urlText.concat("&location=" + latitude + "," + longitude);
            urlText = urlText.concat("&rankby=distance");
            if (!typeFilter.isEmpty()) {
                urlText = urlText.concat("&type=" + typeFilter);
            }

            URL url = new URL(urlText);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = "";
            String aux;
            while ((aux = br.readLine()) != null) {
                output = output.concat(aux);
            }
            conn.disconnect();
            return output;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
