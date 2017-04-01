package com.sixtyseven.uga.watercake.models.dataManagement;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * A class for making network requests and converting the response to an object using Gson.
 * @param <T> The type of parsed response this request expects.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Type type;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private int statusCode;

    /**
     * Make a GET request and return a parsed object from JSON.
     * @param method request method type
     * @param url URL of the request to make
     * @param type Relevant class type object, for Gson's reflection
     * @param headers Map of request headers
     * @param listener response listener
     * @param errorListener response error listener
     */
    public GsonRequest(int method, String url, Type type, Map<String, String> headers,
            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.type = type;
        this.headers = headers;
        this.listener = listener;
        this.statusCode = 0;
    }

    /**
     * A method to expose the status code that came with the response. Should only be used inside
     * the response listener or the error listener.
     * @return the status code that came with the response
     */
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            return (Response<T>) Response.success(gson.fromJson(json, type),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
