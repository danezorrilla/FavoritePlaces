package com.bb.favoriteplaces.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {FavoritePlacesEntity.class})
public abstract class FavoritePlacesDB extends RoomDatabase {
    public abstract FavoritePlacesDAO getFavoritePlacesDAO();
}
