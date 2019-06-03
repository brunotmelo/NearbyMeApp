package com.brunotmelo.placesnearby.data.places.util;

public enum PlaceTypeEnum {
    ALL(""),
    AIRPORTS("airport"),
    RESTAURANTS("restaurant"),
    NIGHTCLUBS("night_club"),
    SUPERMARKETS("supermarket"),
    SHOPPINGS("shopping_mall");

    private String filterStr;

    PlaceTypeEnum(String filterStr){
        this.filterStr = filterStr;
    }

    public String getFilter(){
        return filterStr;
    }
}
