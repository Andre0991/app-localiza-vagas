package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyStringCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.Service.API_ADDRESS;
import static com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.Service.API_VERSION;

/**
 * Created by andreperictavares on 18/11/2016.
 */

public class UserServices {

    // TODO: add boolean return
    // TODO: continuar
    // TODO: usar uri builder
    // TODO: não precisa enviar User inteiro, basta username e password
    public static void authUser(User user, final Context ctx, final VolleyJsonOBJCallback callback) {
        String url = API_ADDRESS + API_VERSION + "/auth";
        final String body = new Gson().toJson(user);
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
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
            @Override
            public byte[] getBody() {
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }};
        RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(req);
    }

    // TODO: add boolean return
    // TODO: usar uri builder
    public static void addUser(User user, final Context ctx, final VolleyJsonOBJCallback callback) {
        String url = API_ADDRESS + API_VERSION + "/user";
        final String body = new Gson().toJson(user);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = Service.getErrorResponseFromJson(error);
                        if (errorMsg != null){
                            Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                })
        {
            @Override
            public byte[] getBody() {
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }};
        RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }

    public static void getOne(final Context ctx, String username, final VolleyJsonOBJCallback callback) {
        String url = API_ADDRESS + API_VERSION + "/oneUser";
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", username);
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
            jsObjRequest.setShouldCache(false);
            RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

/**/
