package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.NominatimServices;
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

    private static String TAG = "GuessParkingLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_parking_location);

        ruaTextView    = (TextView) findViewById(R.id.rua);
        bairroTextView = (TextView) findViewById(R.id.bairro);
        cidadeTextView = (TextView) findViewById(R.id.cidade);
        estadoTextView = (TextView) findViewById(R.id.estado);
    }

    @Override
    // TODO: change 'this' to other activity?
    public void onConnected(@Nullable Bundle bundle) {
        super.onConnected(bundle);
        if (super.mCurrentLocation != null) {
            NominatimServices nominatimServices = new NominatimServices();
            Log.i(TAG, "Obtendo detalhes sobre localização no Nominatim");
            nominatimServices.getLocationDetails(this,
                    mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude(),
                    new VolleyJsonOBJCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            if (gotAllMandatoryFieldsForDatabaseInsertion(result)) {
                                updateUI(result);
                            }
                            else {
                                // We don't have all required information.
                                // Let the user fill it in the next acitivity.

//            Intent intent = new Intent(FILL_THIS.this, MainMenuActivity.class);
//            startActivity(intent);
//            GuessParkingLocationActivity.this.finish();
                            }
                        }
                        @Override
                        public void onErrorResponse(String result) {
                        }
                    });
        }
        else {
            // TODO: tratar?
            Log.e(TAG, "Não foi possível obter localização.");
        }
    }


    private void updateUI(JSONObject result) {
        try {
            JSONObject address = result.getJSONObject("address");
            ruaTextView.setText(address.getString("road"));
            bairroTextView.setText(address.getString("suburb"));
            cidadeTextView.setText(address.getString("city"));
            estadoTextView.setText(address.getString("state"));
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
}
