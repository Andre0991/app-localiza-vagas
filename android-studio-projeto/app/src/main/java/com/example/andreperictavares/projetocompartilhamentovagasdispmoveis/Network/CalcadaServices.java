package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.Service.API_ADDRESS;
import static com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.Service.API_VERSION;

/**
 * Created by andreperictavares on 27/11/2016.
 */

public class CalcadaServices {

    // TODO: add boolean return
    // TODO: usar uri builder
    public static void addCalcada(Calcada calcada, final Context ctx, final VolleyJsonOBJCallback callback) {
        String url = API_ADDRESS + API_VERSION + "/calcada";
        JSONObject jsonObj = new JSONObject();
        try {
            String str = new Gson().toJson(calcada);
            jsonObj.put("username", SharedPreferencesUtils.getUsername(ctx));
            jsonObj.put("data", new JSONObject(str));
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            callback.onSuccessResponse(response);
                        }
                    },
                            new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    String errorMsg = Service.getErrorResponseFromJson(error);
                                    callback.onErrorResponse(errorMsg);
                                }
                            })
            {
//                @Override
//                public byte[] getBody() {
//                    return body.getBytes();
//                }
                @Override
                public Map<String, String> getHeaders(){
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", SharedPreferencesUtils.getToken(ctx));
                    return headers;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }};
            RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
