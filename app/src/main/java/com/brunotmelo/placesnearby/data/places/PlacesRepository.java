package com.brunotmelo.placesnearby.data.places;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import com.brunotmelo.placesnearby.data.places.datatypes.AddressComponent;
import com.brunotmelo.placesnearby.data.places.datatypes.PlacesAPIDetailsResponse;
import com.brunotmelo.placesnearby.data.places.datatypes.PlacesAPINearbyResponse;
import com.brunotmelo.placesnearby.data.places.datatypes.NearbyResult;
import com.brunotmelo.placesnearby.data.places.util.PlaceTypeEnum;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.PlaceDetail;
import com.brunotmelo.placesnearby.util.AppBackgroundExecutor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlacesRepository {

    private static PlacesRepository instance;

    private PlacesAPIDataSource placesDataSource;

    private MutableLiveData<List<Place>> livePlaces;
    private MutableLiveData<Place> chosenPlace;
    private MutableLiveData<PlaceTypeEnum> liveFilter;

    private PlaceTypeEnum curFilter;
    private Location curLocation;

    private Map<String, PlaceDetail> placeDetailsMap;
    private List<Place> curPlaces;

    private AppBackgroundExecutor executor;


    public static PlacesRepository getInstance() {
        if (instance == null) {
            instance = new PlacesRepository();
        }
        return instance;
    }

    private PlacesRepository() {
        executor = AppBackgroundExecutor.getInstance();
        livePlaces = new MutableLiveData<>();
        chosenPlace = new MutableLiveData<>();
        liveFilter = new MutableLiveData<>();
        placesDataSource = new PlacesAPIDataSource();
        placeDetailsMap = new HashMap<>();
        curFilter = PlaceTypeEnum.ALL;
        liveFilter.postValue(curFilter);
    }

    public void updateFilter(PlaceTypeEnum filter) {
        this.curFilter = filter;
        if (curLocation != null) {
            callUpdatePlaces();
        }
        liveFilter.postValue(curFilter);
    }

    public void updateLocation(Location location) {
        this.curLocation = location;
        callUpdatePlaces();
    }

    public LiveData<List<Place>> getLivePlaces() {
        if (curLocation != null) {
            callUpdatePlaces();
        }
        return livePlaces;
    }

    public LiveData<PlaceTypeEnum> getLiveFilter() {
        return liveFilter;
    }

    private void callUpdatePlaces() {

        executor.queueBackground(new Runnable() {
            @Override
            public void run() {
                PlacesAPINearbyResponse response = placesDataSource.getNearbyPlaces(curLocation, curFilter);
                List<Place> places = new LinkedList<>();
                for (int i = 0; i < response.results.size(); i++) {
                    NearbyResult result = response.results.get(i);
                    Place place = new Place();
                    place.id = result.place_id;
                    place.title = result.name;
                    place.address = result.vicinity;
                    Location location = new Location("");
                    location.setLatitude(result.geometry.location.lat);
                    location.setLongitude(result.geometry.location.lng);
                    place.location = location;
                    places.add(place);
                    if (placeDetailsMap.containsKey(place.id)) {
                        PlaceDetail detail = placeDetailsMap.get(place.id);
                        place.cep = detail.cep;
                        place.phone = detail.phone;
                    } else {
                        callUpdateDetails(place.id);
                    }
                }
                curPlaces = places;
                livePlaces.postValue(curPlaces);
            }
        });
    }

    private void callUpdateDetails(final String placeId) {
        executor.queueBackground(new Runnable() {
            @Override
            public void run() {
                PlacesAPIDetailsResponse response = placesDataSource.getDetailsPlace(placeId);
                parseResponse(response, placeId);
            }
        });
    }

    private void parseResponse(PlacesAPIDetailsResponse response, String placeId){
        PlaceDetail detail = new PlaceDetail();
        detail.phone = response.result.formatted_phone_number;
        List<AddressComponent> addrComponents = response.result.address_components;
        for(AddressComponent component : addrComponents){
            if(component.types != null){
                if(component.types.get(0).equals("postal_code")){
                    detail.cep = component.long_name;
                    break;
                }
            }
        }

        placeDetailsMap.put(placeId, detail);
        for(Place place: curPlaces){
            if(place.id.equals(placeId)){
                place.cep = detail.cep;
                place.phone = detail.phone;
                livePlaces.postValue(curPlaces);
            }
        }



    }

    public LiveData<Place> getLiveChosenPlace() {
        return chosenPlace;
    }

    public void updateChosenPlace(Place place) {
        chosenPlace.postValue(place);
    }

}
