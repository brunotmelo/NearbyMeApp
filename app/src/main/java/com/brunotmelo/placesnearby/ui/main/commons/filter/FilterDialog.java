package com.brunotmelo.placesnearby.ui.main.commons.filter;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.brunotmelo.placesnearby.R;
import com.brunotmelo.placesnearby.data.places.util.PlaceTypeEnum;

public class FilterDialog extends DialogFragment {

    private FilterDialogViewModel viewModel;
    private View view;
    private ImageView allCheck;
    private ImageView airportCheck;
    private ImageView restaurantCheck;
    private ImageView nightClubCheck;
    private ImageView superMarketCheck;
    private ImageView shoppingCheck;

    private FrameLayout all;
    private FrameLayout airport;
    private FrameLayout restaurant;
    private FrameLayout nightClub;
    private FrameLayout superMarket;
    private FrameLayout shopping;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.filter_dialog, null);

        allCheck = view.findViewById(R.id.check_all);
        airportCheck = view.findViewById(R.id.check_airport);
        restaurantCheck = view.findViewById(R.id.check_restaurant);
        nightClubCheck = view.findViewById(R.id.check_nightclub);
        superMarketCheck = view.findViewById(R.id.check_supermarket);
        shoppingCheck = view.findViewById(R.id.check_shopping);

        all = view.findViewById(R.id.l_all);
        airport = view.findViewById(R.id.l_airport);
        restaurant = view.findViewById(R.id.l_restaurant);
        nightClub = view.findViewById(R.id.l_nightclub);
        superMarket = view.findViewById(R.id.l_supermarket);
        shopping = view.findViewById(R.id.l_shopping);


        builder.setTitle(R.string.dialog_filter_title)
                .setView(view)
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FilterDialog.this.getDialog().cancel();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FilterDialogViewModel.class);
        LiveData<PlaceTypeEnum> liveFilter = viewModel.getLiveFilter();

        liveFilter.observe(getViewLifecycleOwner(), new Observer<PlaceTypeEnum>() {
            @Override
            public void onChanged(@Nullable PlaceTypeEnum placeTypeEnum) {
                renderChosenFilter(placeTypeEnum);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateFilter(PlaceTypeEnum.ALL);
                dismissDialog();

            }
        });
        airport.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewModel.updateFilter(PlaceTypeEnum.AIRPORTS);
                dismissDialog();
            }
        });
        restaurant.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewModel.updateFilter(PlaceTypeEnum.RESTAURANTS);
                dismissDialog();
            }
        });
        nightClub.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewModel.updateFilter(PlaceTypeEnum.NIGHTCLUBS);
                dismissDialog();
            }
        });
        superMarket.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewModel.updateFilter(PlaceTypeEnum.SUPERMARKETS);
                dismissDialog();
            }
        });
        shopping.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewModel.updateFilter(PlaceTypeEnum.SHOPPINGS);
                dismissDialog();
            }
        });


    }

    private void dismissDialog(){
        FilterDialog.this.getDialog().cancel();
    }

    private void renderChosenFilter(PlaceTypeEnum filter){
        allCheck.setVisibility(View.INVISIBLE);
        airportCheck.setVisibility(View.INVISIBLE);
        restaurantCheck.setVisibility(View.INVISIBLE);
        nightClubCheck.setVisibility(View.INVISIBLE);
        superMarketCheck.setVisibility(View.INVISIBLE);
        shoppingCheck.setVisibility(View.INVISIBLE);

        switch (filter){
            case ALL:
                allCheck.setVisibility(View.VISIBLE);
                break;
            case AIRPORTS:
                airportCheck.setVisibility(View.VISIBLE);
                break;
            case RESTAURANTS:
                restaurantCheck.setVisibility(View.VISIBLE);
                break;
            case NIGHTCLUBS:
                nightClubCheck.setVisibility(View.VISIBLE);
                break;
            case SUPERMARKETS:
                superMarketCheck.setVisibility(View.VISIBLE);
                break;
            case SHOPPINGS:
                shoppingCheck.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDestroyView(){
        view = null;
        super.onDestroyView();
    }
}

