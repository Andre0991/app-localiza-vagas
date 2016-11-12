package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationTestActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiServices();
        setContentView(R.layout.activity_location_test);
    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    private void buildGoogleApiServices(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    // TODO: Check permission
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
//            mlatitudetext.settext(string.valueof(mlastlocation.getlatitude()));
//            mlongitudetext.settext(string.valueof(mlastlocation.getlongitude()));
            Log.d("latitude", Double.toString(mLastLocation.getLatitude()));
            Log.d("longitude", Double.toString(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
