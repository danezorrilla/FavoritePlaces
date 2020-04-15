package com.bb.favoriteplaces.model;

public class Place {

    private String PlaceTitle;

    private Double PlaceLat;

    private Double PlaceLng;

    public Place() {
    }

    public Place(String placeTitle, Double placeLat, Double placeLng) {
        PlaceTitle = placeTitle;
        PlaceLat = placeLat;
        PlaceLng = placeLng;
    }

    public String getPlaceTitle() {
        return PlaceTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        PlaceTitle = placeTitle;
    }

    public Double getPlaceLat() {
        return PlaceLat;
    }

    public void setPlaceLat(Double placeLat) {
        PlaceLat = placeLat;
    }

    public Double getPlaceLng() {
        return PlaceLng;
    }

    public void setPlaceLng(Double placeLng) {
        PlaceLng = placeLng;
    }
}
