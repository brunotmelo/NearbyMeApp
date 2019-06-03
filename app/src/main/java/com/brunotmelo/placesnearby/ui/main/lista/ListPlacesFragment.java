package com.brunotmelo.placesnearby.ui.main.lista;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.brunotmelo.placesnearby.R;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;
import com.brunotmelo.placesnearby.ui.main.lista.recyclerview.PlacesAdapter;

import java.util.List;

public class ListPlacesFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_places_fragment, container, false);
        final RecyclerView placesListView = view.findViewById(R.id.places_list);
        final PlacesAdapter placesAdapter = new PlacesAdapter();
        placesListView.setAdapter(placesAdapter);


        ListPlacesViewModel viewModel = ViewModelProviders.of(this).get(ListPlacesViewModel.class);
        LiveData<List<Place>> livePlaces = viewModel.getNearbyPlaces();
        livePlaces.observe(getViewLifecycleOwner(), new Observer<List<Place>>(){
            @Override
            public void onChanged(@Nullable List<Place> places) {
                placesAdapter.setData(places);
                placesAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
