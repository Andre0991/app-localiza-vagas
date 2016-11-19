package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;

/**
 * Created by andreperictavares on 18/11/2016.
 */

class Service {

    public static final String API_VERSION = "v1";
    public static final String API_ADDRESS = "http://172.31.92.56:3000/api/";


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
}
