package com.example.avinash.jsonobjectdemo.api;

import android.app.ProgressDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.avinash.jsonobjectdemo.utility.AlertUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deep on 8/24/2017.
 */

public class APIManager {

    public static String GENERIC_API_ERROR_MESSAGE = "Some error occured. Please try again later.";
    private static int statusCode = 0;
    private static Map<String, String> responseHeaders = new HashMap<>();
    private static ProgressDialog progressDialog;
    // private static Dialog_gif_loader progressDialog;

    public static void request(final APIRequest apiRequest, final APIResponse apiResponse) {
        // return if url is invalid
        if (apiRequest.url == null || apiRequest.url.length() == 0) {
            // Show error and print log
            return;
        }

        // Show loader if required
        if (apiRequest.context != null && apiRequest.showLoader) {
            progressDialog = new ProgressDialog(apiRequest.context);
            //  progressDialog = new Dialog_gif_loader(apiRequest.context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        if (apiRequest.context != null) {
            if (!ConnectionDetector.isNetworkAvailable(apiRequest.context)) {
                // Hide loader if shown
                if (apiRequest.context != null && apiRequest.showLoader) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
                if (apiResponse != null) {
                    apiResponse.onResponse(null, null, responseHeaders, statusCode);
                }
                AlertUtils.showAlert("Alert", "No internet connected? please connect to internet!", "ok", apiRequest.context, new AlertUtils.AlertClickHandler() {
                    @Override
                    public void alertButtonAction(boolean isPositiveAction) {

                    }
                });
                return;
            }
        }

        // API Call
        final StringRequest stringRequest = new StringRequest(apiRequest.method, apiRequest.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (apiResponse != null) {
                    apiResponse.onResponse(response, null, responseHeaders, statusCode);
                }
                // Hide loader if shown
                if (apiRequest.context != null && apiRequest.showLoader) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (apiRequest.context != null && apiRequest.showError && apiRequest.showLoader) {
                    String errorText = GENERIC_API_ERROR_MESSAGE;
                    if (error != null && error.getLocalizedMessage() != null) {
                        errorText = error.getLocalizedMessage();
                    }
                    AlertUtils.showAlert("Error", errorText, null, apiRequest.context, null);
                }
                if (apiResponse != null) {
                    apiResponse.onResponse(null, error, responseHeaders, statusCode);
                }
                // Hide loader if shown
                if (apiRequest.context != null && apiRequest.showLoader) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            }
        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                if (apiRequest.params == null) {
//                    apiRequest.params = new HashMap<>();
//                }
//                return apiRequest.params;
//            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                if (apiRequest.objectParams == null) {
                    apiRequest.objectParams = new JSONObject();
                }
                String requestBody = "";
                try {
                    requestBody = apiRequest.objectParams.toString();
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (apiRequest.headers == null) {
                    apiRequest.headers = new HashMap<>();
                }
                apiRequest.headers.put("Content-Type", "application/json; charset=utf-8");
                return apiRequest.headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                responseHeaders = response.headers;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(12000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        APIRequestQueue.sharedinstance().addToRequestQueue(stringRequest);
    }

}
