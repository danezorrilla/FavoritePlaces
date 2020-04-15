package com.bb.favoriteplaces.network;

import com.bb.favoriteplaces.model.GooglePlaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesService {

    @GET("/maps/api/place/nearbysearch/json?")
    Observable<GooglePlaces> getGooglePlacesRx(@Query("location") String LatLng,
                                                 @Query("radius") String radius,
                                                 @Query("key") String api_key);
}
