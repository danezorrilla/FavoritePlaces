package com.bb.favoriteplaces.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.favoriteplaces.R;
import com.bb.favoriteplaces.model.Place;

import java.util.List;

public class FavoritePlacesAdapter extends RecyclerView.Adapter<FavoritePlacesAdapter.FavoritePlacesViewHolder> {

    private List<Place> favoritePlacesList;

    public FavoritePlacesAdapter(List<Place> favoritePlacesList) {
        this.favoritePlacesList = favoritePlacesList;
    }

    @NonNull
    @Override
    public FavoritePlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_places_layout, parent, false);

        return new FavoritePlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePlacesViewHolder holder, int position) {
        holder.favoritePlaceTitle.setText(favoritePlacesList.get(position).getPlaceTitle());
        holder.favoritePlaceLat.setText(favoritePlacesList.get(position).getPlaceLat().toString());
        holder.favoritePlaceLng.setText(favoritePlacesList.get(position).getPlaceLng().toString());
    }

    @Override
    public int getItemCount() {
        return favoritePlacesList.size();
    }

    public class FavoritePlacesViewHolder extends RecyclerView.ViewHolder {

        TextView favoritePlaceTitle;
        TextView favoritePlaceLat;
        TextView favoritePlaceLng;

        public FavoritePlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            favoritePlaceTitle = itemView.findViewById(R.id.favorite_place_title);
            favoritePlaceLat = itemView.findViewById(R.id.favorite_place_latitude);
            favoritePlaceLng = itemView.findViewById(R.id.favorite_place_longitude);
        }
    }
}
