package com.sixtyseven.uga.watercake.models.dataManagement;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * A class for making network requests and returning the response code.
 */
public class StatusCodeRequest extends Request<Integer> {
    private final Map<String, String> headers;
    private final Response.Listener<Integer> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     * @param method request method type
     * @param url URL of the request to make
     * @param headers Map of request headers
     * @param listener response listener
     * @param errorListener response error listener
     */
    public StatusCodeRequest(int method, String url, Map<String, String> headers,
            Response.Listener<Integer> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<Integer> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.statusCode, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Integer response) {
        listener.onResponse(response);
    }
}
