package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.app.FragmentTransaction;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchFreeParkingLocationActivity extends LocationActivity {

    public static final String TAG = "SearchFreePkgLocation";

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_free_parking_location);
        super.mRequestingLocationUpdates = true;

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // add sydney
                LatLng sydney = new LatLng(-34, 151);
                final Marker sydneyMarker = googleMap.addMarker((new MarkerOptions()
                .position(sydney)
                .title("Marker")));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(sydneyMarker)) {
                            Log.d(TAG, "clicou!");
                        }
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        super.onConnected(bundle);
        if (super.mCurrentLocation != null) {
            this.latitude = mCurrentLocation.getLatitude();
            this.longitude = mCurrentLocation.getLongitude();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
    }

}
