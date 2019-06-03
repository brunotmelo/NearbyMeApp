package com.brunotmelo.placesnearby.ui.main.radar.address;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.brunotmelo.placesnearby.R;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Place;
import org.w3c.dom.Text;

public class AddressDialog extends DialogFragment {

    private AddressDialogViewModel viewModel;

    private Location myCurrentLocation;
    private Place currentPlace;

    private TextView titleText;
    private TextView addrText;
    private ImageView traceRouteBtn;

    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = (inflater.inflate(R.layout.address_dialog, null));
        traceRouteBtn = view.findViewById(R.id.route_btn);
        traceRouteBtn.setEnabled(false);
        traceRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRouteOnMaps();
            }
        });
        titleText = view.findViewById(R.id.addr_title);
        addrText = view.findViewById(R.id.addr_text);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AddressDialogViewModel.class);
        LiveData<Location> myLiveLocation = viewModel.getMyLiveLocation();
        LiveData<Place> liveChosenPlace = viewModel.getChosenPlace();

        myLiveLocation.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                myCurrentLocation = location;
                if (currentPlace != null) {
                    traceRouteBtn.setEnabled(true);
                }
            }
        });

        liveChosenPlace.observe(getViewLifecycleOwner(), new Observer<Place>() {
            @Override
            public void onChanged(@Nullable Place place) {
                currentPlace = place;
                titleText.setText(place.title);
                String addrString = place.address;
                if(place.cep != null && place.cep.isEmpty()){
                    addrString = addrString.concat(".CEP:" + place.cep);
                }

                addrText.setText(addrString);
                if (myCurrentLocation != null) {
                    traceRouteBtn.setEnabled(true);
                }

            }
        });
    }

    private void openRouteOnMaps() {
        System.out.println("open route intent");
        String urlStr = "https://www.google.com/maps/dir/?api=1";
        urlStr = urlStr.concat("&origin=" + myCurrentLocation.getLatitude() + "," + myCurrentLocation.getLongitude());
        urlStr = urlStr.concat("&destination=" + currentPlace.location.getLatitude() + "," + currentPlace.location.getLongitude());
        urlStr = urlStr.concat("&destination_place_id=" + currentPlace.id);

        Uri uri = Uri.parse(urlStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        view = null;
        super.onDestroyView();
    }
}
