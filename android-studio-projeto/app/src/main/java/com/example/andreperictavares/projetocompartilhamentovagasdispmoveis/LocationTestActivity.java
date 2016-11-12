package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationTestActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "LocationTestActivity";
    public static final long INTERVAL = 1500;
    public static final long FASTEST_INTERVAL = 1500;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_location_test);
    }

    protected void onStop(){
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected synchronized void buildGoogleApiClient(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    // TODO: need to ask for permission?
    // (" If the device is running Android 6.0 or higher, and your app's target SDK is 23 or higher,
    // the app has to list the permissions in the manifest and request those permissions at run time. ")
    // TODO: Check permission (see intellij warning)
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        // TODO: e se retorno for nulo?
        if (mLastLocation != null) {
//            mlatitudetext.settext(string.valueof(mlastlocation.getlatitude()));
//            mlongitudetext.settext(string.valueof(mlastlocation.getlongitude()));
            Log.d("latitude", Double.toString(mLastLocation.getLatitude()));
            Log.d("longitude", Double.toString(mLastLocation.getLongitude()));
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void getCurrentLocationSettings(LocationRequest mLocationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                // FIXME: are android docs wrong?
                // (https://developer.android.com/training/location/change-location-settings.html#prompt)
                final LocationSettingsStates = locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // show user dialog
                        try {
                           status.startResolutionForResult(
                                   // FIXME: docs says "OuterClass.this"
                                   OuterClass.this,
//                                   this,
                                   REQUEST_CHECK_SETTINGS);
                        }
                        catch (IntentSender.SendIntentException e){
                            // ignore the error
                    }
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The Google Play Services connection was lost for some reason.
        // We try to reconnect.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorcOde() = " + connectionResult.getErrorCode());
    }
}
