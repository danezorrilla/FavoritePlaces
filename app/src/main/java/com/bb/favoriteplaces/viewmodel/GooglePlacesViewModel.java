package com.bb.favoriteplaces.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bb.favoriteplaces.database.FavoritePlacesDB;
import com.bb.favoriteplaces.model.FireUser;
import com.bb.favoriteplaces.model.GooglePlaces;
import com.bb.favoriteplaces.model.Place;
import com.bb.favoriteplaces.network.GooglePlacesRetrofit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class GooglePlacesViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;

    private DatabaseReference reference;

    private FavoritePlacesDB favoritePlacesDB;

    private GooglePlacesRetrofit googlePlacesRetrofit;

    List<Place> favoritePlaceList = new ArrayList<>();

    private PublishSubject<List<Place>> favoritePlaceObservable = PublishSubject.create();

    public GooglePlacesViewModel(@NonNull Application application) {
        super(application);

        googlePlacesRetrofit = new GooglePlacesRetrofit();

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("favorite_places");
    }

    public Observable<GooglePlaces> getGooglePlaces(String LatLng){
        return googlePlacesRetrofit
                .getGooglePlacesRx(LatLng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Place>> getFavoritePlacesList(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Place currentPlace = ds.getValue(Place.class);
                    favoritePlaceList.add(currentPlace);
                }
                favoritePlaceObservable.onNext(favoritePlaceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return favoritePlaceObservable;
    }

    public void registerUser(FireUser user){
        mAuth.createUserWithEmailAndPassword(user.getUserName(), user.getUserPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplication(), "User Creation", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplication(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void addNewPlace(Place place){
        String databaseKey = reference.push().getKey();
        if(databaseKey != null)
            reference.child(databaseKey).setValue(place);
    }



}
