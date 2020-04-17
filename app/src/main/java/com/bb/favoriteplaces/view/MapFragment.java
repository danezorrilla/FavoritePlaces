package com.bb.favoriteplaces.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bb.favoriteplaces.R;
import com.bb.favoriteplaces.model.GooglePlaces;
import com.bb.favoriteplaces.model.Place;
import com.bb.favoriteplaces.viewmodel.GooglePlacesViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener,
        GoogleMap.OnInfoWindowClickListener {

    private GooglePlacesViewModel googlePlacesViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SupportMapFragment supportMapFragment;
    private final int REQUEST_CODE = 1;
    private LocationManager locationManager;
    private Location currentLocation;
    private GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googlePlacesViewModel = ViewModelProviders.of(this).get(GooglePlacesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Log.d("TAG_X", "Map Ready");

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setUpLocation();
        } else {
            requestPermissions();
        }

        setPoiClick(map);

        map.setOnInfoWindowClickListener(this);
    }

    private void setPoiClick(final GoogleMap googleMap){
        googleMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = map.addMarker(new MarkerOptions()
                .position(poi.latLng)
                .title(poi.name));

                poiMarker.showInfoWindow();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initMap();
    }

    private void initMap(){

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(supportMapFragment == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }

    private void getGooglePlaces(){
        Log.d("TAG_XX", "Location: " + currentLocation);

        String LatLng;

        LatLng = currentLocation.getLatitude() + "," + currentLocation.getLongitude();

        // RxJava

        compositeDisposable.add(googlePlacesViewModel.getGooglePlaces(LatLng).subscribe(googlePlaces -> {
            displayGooglePlaces(googlePlaces);},
                throwable -> {Log.d("TAG_XX", "Error: " + throwable.getLocalizedMessage());
        }));
    }

    private void displayGooglePlaces(GooglePlaces googlePlaces){
        for(int i = 0; i < googlePlaces.getResults().size(); i++) {
            Log.d("TAG_XX", "RxJava: " + googlePlaces.getResults().get(i).getGeometry()
                    .getLocation().toString());

            addLocationMarker(googlePlaces.getResults().get(i).getName(),
                    googlePlaces.getResults().get(i).getGeometry().getLocation().getLat(),
                    googlePlaces.getResults().get(i).getGeometry().getLocation().getLng());
        }
    }

    private void addLocationMarker(String name, Double latitude, Double longitude){
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(name));
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setUpLocation();
                else { //Permission was denied
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
                        requestPermissions();
                    else
                        Log.d("TAX_X", "show requirements needed . . . "); //showRequirements();
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private void setUpLocation(){
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,5000,10, this
        );

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("TAG_X", "LOCATION : " + location.getLatitude() + "," + location.getLongitude());
        currentLocation = location;

        getGooglePlaces();

        map.addMarker(new MarkerOptions().title("User").position(loc)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Log.d("TAG_XXX", "USERNAME: " + name);

        Log.d("TAG_XXX", "Info window is clicked");
        Log.d("TAG_XXX", "Location: " + marker.getPosition());

        Place favoritePlace = new Place(marker.getTitle(), marker.getPosition().latitude,
                marker.getPosition().longitude, name);

        googlePlacesViewModel.addNewPlace(favoritePlace);
    }
}
