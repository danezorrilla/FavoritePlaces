package com.bb.favoriteplaces.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bb.favoriteplaces.R;
import com.bb.favoriteplaces.adapter.FavoritePlacesAdapter;
import com.bb.favoriteplaces.model.Place;
import com.bb.favoriteplaces.viewmodel.GooglePlacesViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritePlaces extends Fragment {

    private GooglePlacesViewModel googlePlacesViewModel;
    private CompositeDisposable compositeDisposable;

    RecyclerView favoritePlacesList;

    public FavoritePlaces() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googlePlacesViewModel = ViewModelProviders.of(this).get(GooglePlacesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_places_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoritePlacesList = view.findViewById(R.id.favorite_place_list);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(googlePlacesViewModel.getFavoritePlacesList().subscribe(FavoritePlaceList -> {
            displayFavoritePlaces(FavoritePlaceList);
        }));
    }

    private void displayFavoritePlaces(List<Place> favoritePlaceList){
        FavoritePlacesAdapter favoritePlacesAdapter = new FavoritePlacesAdapter(favoritePlaceList);
        favoritePlacesList.setLayoutManager(new LinearLayoutManager(getContext()));
        favoritePlacesList.setAdapter(favoritePlacesAdapter);
    }

    public void returnToMain(View view){}
}
