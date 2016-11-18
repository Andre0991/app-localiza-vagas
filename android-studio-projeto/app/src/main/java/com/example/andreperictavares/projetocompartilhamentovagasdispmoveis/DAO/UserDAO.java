package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.DAO;


import com.android.volley.Request;
import com.android.volley.Response;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;

import org.json.JSONObject;
import com.android.volley.toolbox.*;

/**
 * Created by andre.peric.tavares on 11/17/2016.
 */

public class UserDAO implements DAO {
    @Override
    public void create(Object o) {
        User user = (User) o;
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest();
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
    }

    @Override
    public Object read(Object o) {
        User user = (User) o;
        return null;
    }

    @Override
    public void update(Object o) {
        User user = (User) o;

    }

    @Override
    public void delete(Object o) {
        User user = (User) o;
    }
}
