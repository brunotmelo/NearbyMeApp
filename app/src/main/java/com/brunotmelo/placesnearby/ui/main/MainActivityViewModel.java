package com.brunotmelo.placesnearby.ui.main;

import android.arch.lifecycle.ViewModel;
import android.location.Location;
import com.brunotmelo.placesnearby.data.location.LocationRepository;
import com.brunotmelo.placesnearby.data.places.PlacesAPIDataSource;
import com.brunotmelo.placesnearby.data.places.PlacesRepository;

public class MainActivityViewModel extends ViewModel {

    private LocationRepository locationRepository;
    private PlacesRepository placesRepository;

    public MainActivityViewModel(){
        locationRepository = LocationRepository.getInstance();
        placesRepository = PlacesRepository.getInstance();
    }

    public void updateLocation(Location location){
        locationRepository.updateLocation(location);
        placesRepository.updateLocation(location);
    }

    public void updateBearing(Float bearing){
        locationRepository.updateBearing(bearing);
    }

}
