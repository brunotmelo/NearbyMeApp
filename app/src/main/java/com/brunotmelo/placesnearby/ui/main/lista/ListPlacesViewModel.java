package com.brunotmelo.placesnearby.ui.main.lista;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.brunotmelo.placesnearby.data.places.PlacesRepository;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.PlaceDetail;

import java.util.List;

public class ListPlacesViewModel extends ViewModel {

    private PlacesRepository placesRepository;

    private MutableLiveData<List<PlaceDetail>> livePlaceDetails;
    private LiveData<List<Place>> livePlaces;

    public ListPlacesViewModel(){
        placesRepository = PlacesRepository.getInstance();
    }

    LiveData<List<Place>> getNearbyPlaces() {
        if (livePlaces == null) {
            livePlaces = placesRepository.getLivePlaces();
        }
        return livePlaces;
    }

    LiveData<List<PlaceDetail>> getNearbyPlaceDetails() {
        if (livePlaceDetails == null) {
            livePlaceDetails = new MutableLiveData<>();
           //load place details
        }
        return livePlaceDetails;
    }


}
