package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by andreperictavares on 18/11/2016.
 */

// Excerpts from https://developer.android.com/training/volley/requestqueue.html#singleton
class RequestsQueueSingleton {
    private static RequestsQueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestsQueueSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    static synchronized RequestsQueueSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestsQueueSingleton(context);
        }
        return mInstance;
    }

    <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

}
