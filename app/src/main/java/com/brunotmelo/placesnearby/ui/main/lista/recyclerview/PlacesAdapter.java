package com.brunotmelo.placesnearby.ui.main.lista.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.brunotmelo.placesnearby.R;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<Place> placeList;

    public PlacesAdapter() {
        this.placeList = new ArrayList<>();
    }

    public void setData(List<Place> places){
        placeList = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View placeView = inflater.inflate(R.layout.list_places_item, viewGroup, false);
        return new PlacesAdapter.ViewHolder(placeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(placeList.get(i));
    }

    @Override
    public int getItemCount() {
        if (placeList != null) {
            return placeList.size();
        } else {
            return 0;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView addressText;
        private TextView cepText;
        private TextView phoneText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_title);
            addressText = itemView.findViewById(R.id.text_addr);
            cepText = itemView.findViewById(R.id.text_cep);
            phoneText = itemView.findViewById(R.id.text_phone);
        }

        public void bind(Place place) {
            titleText.setText(place.title);
            addressText.setText(place.address);
            cepText.setText(place.cep);
            phoneText.setText(place.phone);
        }
    }

}
