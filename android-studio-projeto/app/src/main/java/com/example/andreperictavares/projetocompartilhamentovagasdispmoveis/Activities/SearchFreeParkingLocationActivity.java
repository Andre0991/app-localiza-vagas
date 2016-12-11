package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;

public class SearchFreeParkingLocationActivity extends LocationActivity {

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_free_parking_location);
        super.mRequestingLocationUpdates = true;
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
