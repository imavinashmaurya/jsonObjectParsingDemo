package com.example.avinash.jsonobjectdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.example.avinash.jsonobjectdemo.api.APIManager;
import com.example.avinash.jsonobjectdemo.api.APIRequest;
import com.example.avinash.jsonobjectdemo.api.APIResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiCall();
    }

    /**
     * Example of passing JsonObject and JsonArray
     */
    public void apiCall() {
        try {
            // to make an JsonObject to pass
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("PID", "id");
            jsonObject.put("Product_Quantity", "5");
            jsonObject.put("Product_Price", "44");
            // creating jsonArray to pass
            JSONArray array = new JSONArray();
            for (int i = 0; i < 5; i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("PID", "id");
                jsonObject1.put("Product_Quantity", "5");
                jsonObject1.put("Product_Price", "44");
                array.put(jsonObject1);
            }
            jsonObject.put("Detail", array);

/**
 * Above JsonObject
 * {"PID":"id","Product_Quantity":"5","Product_Price":"44","Detail":[{"PID":"id","Product_Quantity":"5","Product_Price":"44"},{"PID":"id","Product_Quantity":"5","Product_Price":"44"},{"PID":"id","Product_Quantity":"5","Product_Price":"44"},{"PID":"id","Product_Quantity":"5","Product_Price":"44"},{"PID":"id","Product_Quantity":"5","Product_Price":"44"}]}
 */

            APIRequest apiRequest = new APIRequest("url", Request.Method.POST, jsonObject, null, MainActivity.this);
            apiRequest.showLoader = false;
            APIManager.request(apiRequest, new APIResponse() {
                @Override
                public void onResponse(String response, Exception error, Map<String, String> headers, int statusCode) {
                    Log.e("response", response);
                    if (response != null && error == null) {
                        try {
                            // response
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
