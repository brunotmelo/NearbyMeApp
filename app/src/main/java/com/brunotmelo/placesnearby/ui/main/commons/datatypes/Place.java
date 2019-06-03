package com.brunotmelo.placesnearby.ui.main.commons.datatypes;

import android.location.Location;

public class Place {

    public Location location;
    public String id;
    public String title;
    public String address;
    public String cep;
    public String phone;

    public Place(){

    }

    public Place(Location location){
        this.location = location;
    }
}
