package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreperictavares on 20/11/2016.
 */

public class NominatimServices {
    private static final String TAG = "NominatimServices";
    private static final String USER_AGENT = "appVagasDisponiveis/1.0";
    private String AUTHORITY = "nominatim.openstreetmap.org";


    public void getLocationDetails(Context ctx, Double lat, Double lon, final VolleyCallback callback){

        // http://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(AUTHORITY)
                .appendPath("reverse")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lon.toString())
                .appendQueryParameter("zoom", "18")
                .appendQueryParameter("addressdetails", "1");
        String url = builder.build().toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: tratar erro (ex: internet off)
                        Log.d(TAG, "erro!");
                        // TODO Auto-generated method stub

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("User-agent", USER_AGENT);
                return headers;
    }
        };
        RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }
}
