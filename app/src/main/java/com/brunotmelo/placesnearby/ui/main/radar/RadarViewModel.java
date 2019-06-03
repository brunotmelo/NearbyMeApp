package com.brunotmelo.placesnearby.ui.main.radar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import com.brunotmelo.placesnearby.data.facebook.FacebookRepository;
import com.brunotmelo.placesnearby.data.location.LocationRepository;
import com.brunotmelo.placesnearby.data.places.PlacesRepository;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Profile;

import java.util.List;

public class RadarViewModel extends ViewModel {

    private LocationRepository locationRepository;
    private PlacesRepository placesRepository;
    private FacebookRepository facebookRepository;

    private LiveData<Location> myLiveLocation;
    private LiveData<Float> myLiveBearing;
    private LiveData<List<Place>> livePlacesList;
    private LiveData<Profile> liveProfile;

    RadarViewModel(){
        locationRepository = LocationRepository.getInstance();
        placesRepository = PlacesRepository.getInstance();
        facebookRepository = FacebookRepository.getInstance();
    }

    public LiveData<Location> getMyLocation() {
        if (myLiveLocation == null) {
            initLocation();
        }
        return myLiveLocation;
    }

    public LiveData<Float> getMyBearing(){
        if(myLiveBearing == null){
            initBearing();
        }
        return myLiveBearing;
    }

    public LiveData<List<Place>> getLivePlaces(){
        if(livePlacesList == null){
            initLivePlacesList();
        }
        return livePlacesList;
    }

    public LiveData<Profile> getLiveProfile(){
        if(liveProfile == null){
            initLiveProfile();
        }
        return liveProfile;
    }

    private void initBearing(){
        myLiveBearing = locationRepository.getLiveBearing();
    }

    public void initLocation() {
        myLiveLocation = locationRepository.getLiveLocation();
    }

    public void initLivePlacesList(){
        livePlacesList = placesRepository.getLivePlaces();
    }

    public void initLiveProfile(){
        liveProfile = facebookRepository.getUserProfile();
    }

    public void updateSelectedPlace(Place place){
        placesRepository.updateChosenPlace(place);
    }


}
