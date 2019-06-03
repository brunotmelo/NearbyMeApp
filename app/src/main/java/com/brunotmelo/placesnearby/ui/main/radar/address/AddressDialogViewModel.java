package com.brunotmelo.placesnearby.ui.main.radar.address;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import com.brunotmelo.placesnearby.data.location.LocationRepository;
import com.brunotmelo.placesnearby.data.places.PlacesRepository;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;

public class AddressDialogViewModel extends ViewModel {

    private LocationRepository locationRepository;
    private PlacesRepository placesRepository;

    public LiveData<Location> myLiveLocation;
    public LiveData<Place> liveChosenPlace;

    public AddressDialogViewModel(){
        locationRepository = LocationRepository.getInstance();
        placesRepository = PlacesRepository.getInstance();
    }

    public LiveData<Location> getMyLiveLocation(){
        if(myLiveLocation == null){
            myLiveLocation = locationRepository.getLiveLocation();
        }
        return myLiveLocation;
    }

    public LiveData<Place> getChosenPlace(){
        if(liveChosenPlace == null){
            liveChosenPlace = placesRepository.getLiveChosenPlace();
        }
        return liveChosenPlace;
    }




}
