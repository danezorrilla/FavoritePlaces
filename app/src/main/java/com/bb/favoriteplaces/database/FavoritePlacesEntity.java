package com.bb.favoriteplaces.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoritePlaces")
public class FavoritePlacesEntity {

    @PrimaryKey(autoGenerate = true)
    private int favoritePlaceID;

    @ColumnInfo(name = "favoritePlaceTitle")
    private String favoritePlaceTitle;

    @ColumnInfo(name = "favoritePlaceLat")
    private float favoritePlaceLat;

    @ColumnInfo(name = "favoritePlaceLng")
    private float favoritePlaceLng;

    public FavoritePlacesEntity(int favoritePlaceID, String favoritePlaceTitle, float favoritePlaceLat, float favoritePlaceLng) {
        this.favoritePlaceID = favoritePlaceID;
        this.favoritePlaceTitle = favoritePlaceTitle;
        this.favoritePlaceLat = favoritePlaceLat;
        this.favoritePlaceLng = favoritePlaceLng;
    }

    @Ignore
    public FavoritePlacesEntity(String favoritePlaceTitle, float favoritePlaceLat, float favoritePlaceLng) {
        this.favoritePlaceTitle = favoritePlaceTitle;
        this.favoritePlaceLat = favoritePlaceLat;
        this.favoritePlaceLng = favoritePlaceLng;
    }

    public int getFavoritePlaceID() {
        return favoritePlaceID;
    }

    public void setFavoritePlaceID(int favoritePlaceID) {
        this.favoritePlaceID = favoritePlaceID;
    }

    public String getFavoritePlaceTitle() {
        return favoritePlaceTitle;
    }

    public void setFavoritePlaceTitle(String favoritePlaceTitle) {
        this.favoritePlaceTitle = favoritePlaceTitle;
    }

    public float getFavoritePlaceLat() {
        return favoritePlaceLat;
    }

    public void setFavoritePlaceLat(float favoritePlaceLat) {
        this.favoritePlaceLat = favoritePlaceLat;
    }

    public float getFavoritePlaceLng() {
        return favoritePlaceLng;
    }

    public void setFavoritePlaceLng(float favoritePlaceLng) {
        this.favoritePlaceLng = favoritePlaceLng;
    }
}
