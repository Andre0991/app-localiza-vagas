package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

import android.content.Context;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

/**
 * Created by andreperictavares on 13/12/2016.
 */
public class CalcadaInfo {
    String distance;
    String duration;
    Calcada calcada;
    Marker marker;

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public Marker getMarker() {
        return marker;
    }

    public Calcada getCalcada() {
        return calcada;
    }

    public CalcadaInfo(Calcada calcada, String distance, String duration, GoogleMap googleMap, Context ctx) {
        this.distance = distance;
        this.duration = duration;
        this.calcada = calcada;

        LatLng coord = new LatLng(calcada.getLatitude(), calcada.getLongitude());
        String infoTxt = formatInfoTxt(distance, duration, ctx);
        MarkerOptions markerOptions = (new MarkerOptions()
                .position(coord)
                .snippet(infoTxt)
                .title(calcada.getRua()));
        this.marker = googleMap.addMarker(markerOptions);
    }

    private String formatInfoTxt(String distance, String duration, Context ctx) {
        return String.format("%s: %s \n %s %s", ctx.getString(R.string.time_label), duration,
                ctx.getString(R.string.distance_label), distance);
    }
}
