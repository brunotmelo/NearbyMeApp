package com.brunotmelo.placesnearby.data.location;

import android.arch.lifecycle.MutableLiveData;
import android.location.Location;

public class LocationRepository {

    private static LocationRepository instance;
    private MutableLiveData<Location> myLiveLocation;
    private MutableLiveData<Float> myLiveBearing;

    public static LocationRepository getInstance(){
        if(instance == null){
            instance = new LocationRepository();
        }
        return instance;
    }

    private LocationRepository(){
        myLiveLocation = new MutableLiveData<>();
        myLiveBearing = new MutableLiveData<>();
    }

    public void updateLocation(Location location){
        myLiveLocation.postValue(location);
    }

    public void updateBearing(Float bearing){
        myLiveBearing.postValue(bearing);
    }

    public MutableLiveData<Location> getLiveLocation(){
        return myLiveLocation;
    }

    public MutableLiveData<Float> getLiveBearing(){
        return myLiveBearing;
    }




}
