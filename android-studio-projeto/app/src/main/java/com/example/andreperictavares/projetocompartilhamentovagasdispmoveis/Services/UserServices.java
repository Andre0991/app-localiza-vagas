package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyStringCallback;
import com.google.gson.Gson;

import static com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.Service.API_ADDRESS;
import static com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.Service.API_VERSION;

/**
 * Created by andreperictavares on 18/11/2016.
 */

public class UserServices {

    // TODO: add boolean return
    // TODO: usar uri builder
    public static void addUser(User user, String user_hash, final Context ctx, final VolleyStringCallback callback) {
        String url = API_ADDRESS + API_VERSION + "/user";
        final String body = new Gson().toJson(user);
        StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = Service.getErrorResponse(error);
                        if (errorMsg != null){
                            Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }};
        RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(req);
    }


    // TODO: add boolean return
    // TODO: continuar
    // TODO: usar uri builder
    public static void authUser(User user, String user_hash, final Context ctx, final VolleyStringCallback callback) {
        String url = API_ADDRESS + API_VERSION + "/user";
        final String body = new Gson().toJson(user);
        StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = Service.getErrorResponse(error);
                        if (errorMsg != null){
                            Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }};
        RequestsQueueSingleton.getInstance(ctx).addToRequestQueue(req);
    }
}
/**/
