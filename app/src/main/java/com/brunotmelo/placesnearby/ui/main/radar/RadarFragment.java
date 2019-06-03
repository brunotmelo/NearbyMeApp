package com.brunotmelo.placesnearby.ui.main.radar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.brunotmelo.placesnearby.R;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Profile;
import com.brunotmelo.placesnearby.ui.main.radar.address.AddressDialog;
import com.bumptech.glide.Glide;

import java.util.*;

public class RadarFragment extends Fragment {

    private ConstraintLayout constraintLayout;
    private ImageView myImage;

    private RadarViewModel viewModel;
    private HashMap<String, ImageView> renderedPlaces;
    private List<Place> currentPlaces;
    private Location currentLocation;
    private Float currentBearing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.radar_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintLayout = view.findViewById(R.id.parent_layout);
        myImage = view.findViewById(R.id.my_image);
        renderedPlaces = new HashMap<>();
        viewModel = ViewModelProviders.of(this).get(RadarViewModel.class);

        final LiveData<Location> myLiveLocation = viewModel.getMyLocation();
        final LiveData<Float> myLiveBearing = viewModel.getMyBearing();
        final LiveData<List<Place>> livePlacesList = viewModel.getLivePlaces();
        final LiveData<Profile> liveProfile = viewModel.getLiveProfile();

        livePlacesList.observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(@Nullable List<Place> places) {
                currentPlaces = places;
                tryRenderPlaces();
            }
        });

        myLiveLocation.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                currentLocation = location;
                tryRenderPlaces();
            }
        });

        myLiveBearing.observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                currentBearing = aFloat;
                tryRenderPlaces();
            }
        });

        liveProfile.observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable Profile profile) {
                Glide.with(getContext())
                        .load(profile.profilePictureUrl)
                        .placeholder(myImage.getDrawable())
                        .centerCrop()
                        .into(myImage);
            }
        });
    }

    private void tryRenderPlaces(){
        if(currentPlaces != null && currentLocation != null){
            if(currentBearing == null){
                currentBearing = 0f;
            }
            currentLocation.setBearing(currentBearing);
            //add loader and stop it here
            renderPlaces();
        }
    }


    //new layout will be added for each place
    private void renderPlaces(){
        cleanUnusedPlaces();
        for(final Place curPlace: currentPlaces){
            ImageView imagePlace;
            if(!renderedPlaces.containsKey(curPlace.id)){
                imagePlace= new ImageView(getContext());
                imagePlace.setImageResource(R.drawable.ic_place_restaurant);
                imagePlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(curPlace);
                    }
                });
                constraintLayout.addView(imagePlace,0);
                renderedPlaces.put(curPlace.id, imagePlace);
            }else{
                imagePlace = renderedPlaces.get(curPlace.id);
            }
            assert imagePlace != null;
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imagePlace.getLayoutParams();
            layoutParams.reset();
            layoutParams.circleConstraint = myImage.getId();
            float placeBearing;
            placeBearing = (currentLocation.bearingTo(curPlace.location)) - currentBearing;
            layoutParams.circleAngle = placeBearing;

            layoutParams.circleRadius = 10* (int) currentLocation.distanceTo(curPlace.location);
            imagePlace.setLayoutParams(layoutParams);
        }
    }

    private void cleanUnusedPlaces(){
        Set<String> currentPlacesSet = new HashSet<>();
        for(Place place : currentPlaces){
            currentPlacesSet.add(place.id);
        }
        Set<String> renderedSet = renderedPlaces.keySet();
        List<String> renderedList = new ArrayList<>(renderedSet);
        for(String key: renderedList){
            if(!currentPlacesSet.contains(key)){
                constraintLayout.removeView(renderedPlaces.get(key));
                renderedPlaces.remove(key);
            }
        }

    }


    //whenever the user moves, the layouts will change position
    private void repositionPlaces(){

    }

    private void openDialog(Place place){
        viewModel.updateSelectedPlace(place);
        DialogFragment dialog = new AddressDialog();
        dialog.show(getChildFragmentManager(), "RouteDialog");
    }
}
