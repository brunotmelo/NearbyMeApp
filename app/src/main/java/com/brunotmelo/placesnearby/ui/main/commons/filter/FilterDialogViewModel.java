package com.brunotmelo.placesnearby.ui.main.commons.filter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.brunotmelo.placesnearby.data.places.PlacesRepository;
import com.brunotmelo.placesnearby.data.places.util.PlaceTypeEnum;

public class FilterDialogViewModel extends ViewModel {

    private PlacesRepository placesRepository;

    public FilterDialogViewModel(){
        placesRepository = PlacesRepository.getInstance();
    }

    public LiveData<PlaceTypeEnum> getLiveFilter(){
        return placesRepository.getLiveFilter();
    }

    public void updateFilter(PlaceTypeEnum type){
        placesRepository.updateFilter(type);
    }



}
