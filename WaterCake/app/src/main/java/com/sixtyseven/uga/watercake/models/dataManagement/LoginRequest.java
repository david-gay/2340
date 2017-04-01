package com.sixtyseven.uga.watercake.models.dataManagement;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.io.UnsupportedEncodingException;

public class LoginRequest extends Request<Integer> {
    /**
     * Default charset for JSON request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE = String.format(
            "application/json; charset=%s", PROTOCOL_CHARSET);

    private final Listener<Integer> mListener;
    private final String mRequestBody;

    public LoginRequest(int method, String url, String requestBody, Listener<Integer> listener,
            Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mRequestBody = requestBody;
    }

    @Override
    protected void deliverResponse(Integer response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (error.networkResponse != null) {
            Integer statusCode = error.networkResponse.statusCode;
            if (statusCode == 204 || statusCode == 401 || statusCode == 404) {
                mListener.onResponse(statusCode);
            } else {
                super.deliverError(error);
            }
        } else {
            super.deliverError(error);
        }
    }

    @Override
    protected Response<Integer> parseNetworkResponse(NetworkResponse response) {
        return Response.error(new AuthFailureError(response));
        //return Response.success(response.statusCode, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
