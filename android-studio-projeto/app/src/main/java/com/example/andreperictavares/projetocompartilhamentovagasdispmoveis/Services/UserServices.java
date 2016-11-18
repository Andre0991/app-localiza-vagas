package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.RequestsQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andreperictavares on 18/11/2016.
 */

public class UserServices {

    private static final String API_VERSION = "v1";

    public static void addUser(User user, String user_hash, Context ctx) {
        String url = "http://172.31.92.56:3000/api/" + API_VERSION + "/user";
        final String body = new Gson().toJson(user);
        StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println("resposta ao tentar add usuário: " + response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("something went wrong :(");
                        error.printStackTrace();
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
