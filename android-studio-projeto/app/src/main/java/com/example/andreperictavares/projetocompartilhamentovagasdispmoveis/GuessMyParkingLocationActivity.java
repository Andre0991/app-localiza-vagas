package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.NominatimServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.LocationListener;

// excertos de https://github.com/googlesamples/android-play-location/blob/master/LocationSettings/app/src/main/java/com/google/android/gms/location/sample/locationsettings/MainActivity.java
public class GuessMyParkingLocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {
    public static final String TAG = "LocationActivity";


    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1500;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * Constant used in the location settings dialog.
     */
    public static final int REQUEST_CHECK_SETTINGS = 0x1;


    /**
     * Provides the entry point to Google Play services.
     */
    private GoogleApiClient mGoogleApiClient;


    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using.
     * Used for checking * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    /**
     * App specific int to tie a requestPermissions() call to the corresponding
     * onRequestPermissionsResult() callback.
     */
    private static final int MY_FINE_LOCATION_PERMISSION_REQUEST = 0;


    // UI
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_my_parking_location);

        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);

        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
//        buildCurrentLocationSettings(mLocationRequest);
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    // TODO: change 'this' to other activity?
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Connected to Google Services");
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mCurrentLocation != null) {
                updateUI();
                // TODO: passar para lugar correto
                NominatimServices nominatimServices = new NominatimServices();
                Log.i(TAG, "Obtendo detalhes sobre localização no Nominatim");
                nominatimServices.getLocationDetails(this,
                        mCurrentLocation.getLatitude(),
                        mCurrentLocation.getLongitude(),
                        new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                    }
                    @Override
                    public void onErrorResponse(String result) {
                    }
                });
            }
            else {
                // TODO: Criar dialogbox?
                Log.e(TAG, "Não foi possível obter localização depois de se conectar ao Google Services!");
            }
        }
        else {
            // Show explanation for the user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            // No explanation needed.
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_FINE_LOCATION_PERMISSION_REQUEST);
            }
        }
        startLocationUpdates();
    }



//    @Override
    // TODO: change 'this' to other activity?
    public void onConnectedyyy(@Nullable Bundle bundle) {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startLocationUpdates();
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateUI();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_FINE_LOCATION_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The Google Play Services connection was lost for some reason.
        // We try to reconnect.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed: ConnectionResult.getErrorcOde() = " + connectionResult.getErrorCode());
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();
        Toast.makeText(this, "Location changed", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Location changed");
    }

    private void updateUI() {
        if (mCurrentLocation != null) {
            mLatitudeTextView.setText(String.format("%s: %f", "latitude",
                    mCurrentLocation.getLatitude()));
            mLongitudeTextView.setText(String.format("%s: %f", "longitude",
                    mCurrentLocation.getLongitude()));
        }
        else {
            Log.i(TAG, "Location is null when updating UI..");
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
            final Status status = locationSettingsResult.getStatus();
            // FIXME: are android docs wrong?
            // (https://developer.android.com/training/location/change-location-settings.html#prompt)
            // final LocationSettingsStates = locationSettingsResult.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can
                    // initialize location requests here.
                    //new
                    // end new

                    Log.i(TAG, "All location settings are satisfied.");
                    startLocationUpdates();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // show user dialog
                    Log.e(TAG, "Location settings are not satisfied");
                    try {
                        status.startResolutionForResult(
                                // TODO: docs says "OuterClass.this"
                                // OuterClass.this,
                                GuessMyParkingLocationActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    }
                    catch (IntentSender.SendIntentException e){
                        Log.i(TAG, "PendingIntent unable to execute request.");
                    }
            }
        }

    public void perm() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mCurrentLocation != null) {
                Log.d(TAG, Double.toString(mCurrentLocation.getLatitude()));
                Log.d(TAG, Double.toString(mCurrentLocation.getLongitude()));
            }
            else {
                // TODO: Criar dialogbox?
                Log.d(TAG, "Não foi possível obter localização!");
            }
        }
        else {
            // Show explanation for the user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            // No explanation needed.
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_FINE_LOCATION_PERMISSION_REQUEST);
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "Google Services API is connected - restarting updates.");
            startLocationUpdates();
        }
    }

}
