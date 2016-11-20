package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils;

import org.json.JSONObject;

/**
 * Created by andreperictavares on 20/11/2016.
 */

public interface VolleyJsonOBJCallback {
    void onSuccessResponse(JSONObject result);
    void onErrorResponse(String result);
}
