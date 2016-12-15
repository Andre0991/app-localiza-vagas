package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.CalcadaInfo;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.CalcadaServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFreeParkingLocationActivity extends LocationActivity {

    public static final String TAG = "SearchFreePkgLocation";
    public static final float ZOOM_LEVEL = 14.0f;

    double latitude;
    double longitude;

    protected Button btnGoToLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_free_parking_location);
        super.mRequestingLocationUpdates = true;

        btnGoToLocation    = (Button) findViewById(R.id.park_in_this_location);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        super.onConnected(bundle);
        if (super.mCurrentLocation != null) {
            this.latitude = mCurrentLocation.getLatitude();
            this.longitude = mCurrentLocation.getLongitude();
                    final Double latitudeMock =  this.latitude;
        final Double longitudeMock =  this.longitude;

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                // from http://stackoverflow.com/questions/13904651/android-google-maps-v2-how-to-add-marker-with-multiline-snippet
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        Context context = SearchFreeParkingLocationActivity.this;

                        LinearLayout info = new LinearLayout(context);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(context);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(context);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });


                final List<CalcadaInfo> cInfoList = new ArrayList<CalcadaInfo>();
                CalcadaServices.getNearestAvailableCalcadas(SearchFreeParkingLocationActivity.this, latitudeMock, longitudeMock, new VolleyJsonOBJCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        // TODO: gerar calçadas...
                        Log.d(TAG, "pegou calcadas");
                        try {
                            JSONArray jsonArr = result.getJSONArray("data");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                CalcadaInfo c = getCalcadaInfoFromJSONObject(jsonObj, googleMap);
                                cInfoList.add(c);
                            }

                            zoomNearestCalcada(cInfoList, googleMap);

                            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    for (CalcadaInfo calcadaInfo : cInfoList) {
                                        if (marker.equals(calcadaInfo.getMarker())) {
                                            btnGoToLocation.setVisibility(View.VISIBLE);
                                            marker.showInfoWindow();
                                        }
                                    }
                                    return true;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String result) {
                        Log.d(TAG, "erro");
                    }
                });
            }
        });
        }
        else {
            String erro = "Não foi possível obter localização";
            Toast.makeText(SearchFreeParkingLocationActivity.this, erro, Toast.LENGTH_LONG).show();
        }
    }

    private void zoomNearestCalcada(List<CalcadaInfo> cInfoList, GoogleMap googleMap) {
        Calcada firstCalcada = cInfoList.get(0).getCalcada();
        LatLng coord = new LatLng(firstCalcada.getLatitude(), firstCalcada.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
        googleMap.animateCamera( CameraUpdateFactory.zoomTo(ZOOM_LEVEL));
    }

    @NonNull
    private CalcadaInfo getCalcadaInfoFromJSONObject(JSONObject jsonObj, GoogleMap googleMap) throws JSONException {
        // TODO: remove hardcoded stuff
        JSONObject calcadaJsonObj = jsonObj.getJSONObject("calcada");
        String calcadaJsonStr = calcadaJsonObj.toString();
        Gson gson = new Gson();
        Calcada calcada = gson.fromJson(calcadaJsonStr, Calcada.class);
        String distance = jsonObj.getString("distance");
        String duration = jsonObj.getString("duration");

        return new CalcadaInfo(calcada, distance, duration, googleMap, SearchFreeParkingLocationActivity.this);
    }


    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
    }

    public void parkInThisLocation(View view) {
    }
}
