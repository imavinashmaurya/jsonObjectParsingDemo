package com.example.avinash.jsonobjectdemo.api;


import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deep on 8/24/2017.
 */

public class APIRequest {

    public String url = "";
    public int method;
    public JSONObject objectParams = new JSONObject();
    public Map<String, String> headers = new HashMap<>();
    public Context context;
    public boolean showLoader;
    public boolean showError; // Set default value as per the app environment or do it in constructor
    // timeOutInterval
    // retry policy

    public APIRequest() {
    }

    public APIRequest(String url, int method, JSONObject params, Map<String, String> headers, Context context) {
        this.url = url;
        this.method = method;
        this.objectParams = params;
        // If authToken in headers is mandatory,
        // Create new if headers is null and pass authToken here
        if (headers == null) {
            headers = new HashMap<>();
        }
        //   headers.put(LocalStorage.AUTH_TOKEN_KEY,LocalStorage.getToken());
        this.headers = headers;
        if (context != null) {
            this.context = context;
            showLoader = true;
            showError = true;
        }
    }

}
