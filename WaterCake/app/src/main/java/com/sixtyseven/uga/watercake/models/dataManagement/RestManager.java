package com.sixtyseven.uga.watercake.models.dataManagement;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Type;

public class RestManager implements IDataManager {
    private static RestManager instance;
    private static Context context;
    private RequestQueue requestQueue;
    private static final String domain = "10.0.2.2";
    // the emulator sees the PC's localhost as this address
    private static final String port = "8080";
    private static final String url = "http://" + domain + ":" + port + "/";

    public static RestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RestManager(context);
        }
        return instance;
    }

    private RestManager(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void makeTestRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = getRequestQueue();
        String call = url + "water-reports";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, call,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 20 characters of the response string.
                        Toast.makeText(context.getApplicationContext(),
                                "Response is: " + response.substring(0, 100), Toast.LENGTH_LONG)
                                .show();
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "That didn't work!!!",
                        Toast.LENGTH_LONG).show();
                Log.d("volley test", error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public <T> void getAllWaterSourceReports(final Callback<T> callback, Type type) {
        String url = "http://10.0.2.2:8080/water-reports";
        // TODO add ServerManager that takes care of generating urls for the server requests

        getReportByType(callback, type, url);
    }

    public <T> void getAllWaterPurityReports(final Callback<T> callback, Type type) {
        String url = "http://10.0.2.2:8080/purity-reports";
        // TODO add ServerManager that takes care of generating urls for the server requests

        getReportByType(callback, type, url);
    }

    private <T> void getReportByType(final Callback<T> callback, Type type, String url) {
        GsonRequest<T> req = new GsonRequest<>(Request.Method.GET, url, type, null,
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getRequestQueue().add(req);
    }

    public interface Callback<T> {
        void onSuccess(T response);
        //void onFailure();
    }
}
