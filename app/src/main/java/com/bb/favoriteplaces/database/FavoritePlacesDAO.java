package com.bb.favoriteplaces.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface FavoritePlacesDAO {

    @Insert
    void addFavoritePlaces(FavoritePlacesEntity googlePlacesEntity);

    @Delete
    void deleteFavoritePlaces(FavoritePlacesEntity googlePlacesEntity);
}
