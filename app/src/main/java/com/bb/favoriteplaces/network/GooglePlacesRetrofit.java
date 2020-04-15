package com.bb.favoriteplaces.network;

import com.bb.favoriteplaces.model.GooglePlaces;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GooglePlacesRetrofit {

    GooglePlacesService googlePlacesService;

    public GooglePlacesRetrofit(){
        googlePlacesService = createGooglePlacesService(getRetrofitInstance());
    }

    private Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private GooglePlacesService createGooglePlacesService(Retrofit retrofit){
        return retrofit.create(GooglePlacesService.class);
    }

    public Observable<GooglePlaces> getGooglePlacesRx(String LatLng){
        return googlePlacesService.getGooglePlacesRx(LatLng, "1000",
                "AIzaSyASLA6dWDosNUkXa8Or1uhvWptsahsCCtY");
    }

}
