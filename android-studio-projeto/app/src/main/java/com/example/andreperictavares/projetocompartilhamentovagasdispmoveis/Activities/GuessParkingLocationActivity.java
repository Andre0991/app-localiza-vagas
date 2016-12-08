package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.NominatimServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class GuessParkingLocationActivity extends LocationActivity implements GoogleApiClient.ConnectionCallbacks
{

    protected TextView ruaTextView;
    protected TextView bairroTextView;
    protected TextView cidadeTextView;
    protected TextView estadoTextView;
    protected TextView postcodeView;
    protected ProgressBar mProgress;

    double latitude;
    double longitude;

    private static String TAG = "GuessParkingLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_parking_location);

        ruaTextView    = (TextView) findViewById(R.id.rua);
        bairroTextView = (TextView) findViewById(R.id.bairro);
        cidadeTextView = (TextView) findViewById(R.id.cidade);
        estadoTextView = (TextView) findViewById(R.id.estado);
        postcodeView = (TextView) findViewById(R.id.postcode);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    // TODO: change 'this' to other activity?
    public void onConnected(@Nullable Bundle bundle) {
        super.onConnected(bundle);
        if (super.mCurrentLocation != null) {
            NominatimServices nominatimServices = new NominatimServices();
            Log.i(TAG, "Obtendo detalhes sobre localização no Nominatim");
            this.latitude = mCurrentLocation.getLatitude();
            this.longitude = mCurrentLocation.getLongitude();
            nominatimServices.getLocationDetails(this,
                    latitude,
                    longitude,
                    new VolleyJsonOBJCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            mProgress.setVisibility(View.INVISIBLE);
                            if (gotAllMandatoryFieldsForDatabaseInsertion(result)) {
                                updateUI(result);
                            }
                            else {
                                skipThisActivity();
                            }
                        }
                        @Override
                        public void onErrorResponse(String result) {
                        }
                    });
        }
        else {
            Log.e(TAG, "Não foi possível obter localização.");
            skipThisActivity();
        }
    }

    private void skipThisActivity() {
            Intent intent = new Intent(this, InsertAddressManuallyActivity.class);
            startActivity(intent);
            GuessParkingLocationActivity.this.finish();
    }

    private void updateUI(JSONObject result) {
        try {
            JSONObject address = result.getJSONObject("address");
            ruaTextView.setText(address.getString("road"));
            bairroTextView.setText(address.getString("suburb"));
            cidadeTextView.setText(address.getString("city"));
            estadoTextView.setText(address.getString("state"));
            postcodeView.setText(address.getString("postcode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean gotAllMandatoryFieldsForDatabaseInsertion(JSONObject obj) {
        JSONObject address = null;
        try {
            address = obj.getJSONObject("address");
            address.getString("road");
            address.getString("suburb");
            address.getString("city");
            address.getString("state");
            address.getString("postcode");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void sendParkingLocation(int number) {
        String road = ruaTextView.getText().toString();
        String postcode = generatePostCodeWithoutNonNumericChars(postcodeView.getText().toString());
        Calcada calcada = new Calcada(number, postcode, road, latitude, longitude, User.getCurrentUser(this));
    }

        private String generatePostCodeWithoutNonNumericChars(String postcode) {
        return postcode.replace("-", "");
    }
}
