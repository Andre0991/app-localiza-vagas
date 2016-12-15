package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by andreperictavares on 18/11/2016.
 */

class Service {

    public static final String API_VERSION = "v1";
    public static final String API_ADDRESS = "http://172.31.113.51:3000/api/";
//    public static final String API_ADDRESS = "http://172.31.113.51:3000/api/";

    public static String getErrorResponse(VolleyError error){
        if (error.networkResponse == null) {
            if (error.getClass().equals(TimeoutError.class)) {
                return "Timeout error. Por favor, tente novamente.";
            }
            if (error.getClass().equals(NoConnectionError.class)) {
                return "Sem conexão com a internet. Por favor, verifique se há alguma rede disponível.";
            }
        }
        if (error.networkResponse.data != null) {
            try {
                return new String(error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getErrorResponseFromJson(VolleyError error){
        if (error.networkResponse == null) {
            if (error.getClass().equals(TimeoutError.class)) {
                return "Timeout error. Por favor, tente novamente.";
            }
            if (error.getClass().equals(NoConnectionError.class)) {
                return "Sem conexão com a internet. Por favor, verifique se há alguma rede disponível.";
            }
        }
        if (error.networkResponse.data != null) {
            try {
                String json = new String(error.networkResponse.data, "UTF-8");
                return new JSONObject(json).getString("message");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
