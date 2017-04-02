package com.sixtyseven.uga.watercake.models.dataManagement;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

// TODO add Log.d() everywhere, just so that we know what's going on at all times
public class VolleyRequestBuilder<T> {

    //    public class HTTPMethod implements Request.Method {
    //    }

    public interface VolleyRequestCallback<T> {
        void onSuccess(T response);

        void onFailure(Integer httpStatusCode, String errorMessage);
    }

    public interface VolleyResponseStatusCodeCallback {
        void onExpectedStatusCode(Integer expectedStatusCode);

        void onUnexpectedStatusCode(Integer unexpectedStatusCode, String errorMessage);
    }

    public enum HTTPMethod {
        DEPRECATED_GET_OR_POST(-1),
        // TODO consider removing this -1 because it is deprecated
        GET(0),
        POST(1),
        PUT(2),
        DELETE(3),
        HEAD(4),
        OPTIONS(5),
        TRACE(6),
        PATCH(7);
        public final int value;

        HTTPMethod(int value) {
            this.value = value;
        }

    }

    private Integer method;
    private String url;

    private Response.Listener<T> responseListener;
    private Response.ErrorListener errorListener;
    private VolleyRequestCallback<T> requestCallback; // this is not really needed... I think
    private VolleyResponseStatusCodeCallback statusCodeCallback;

    private List<Integer> expectedStatusCodes;
    private Map<String, String> headers;

    private String jsonStringRequestBody; // this is for POST and PUT
    private Object objectRequestBody; // this is for POST and PUT

    //private Type bodyType;
    private Type responseType; // this is for GET

    public VolleyRequestBuilder() {

        method = null;
        url = null;

        responseListener = null;
        errorListener = null;
        makeErrorListener();
        requestCallback = null;
        makeResponseListener();
        statusCodeCallback = null;

        expectedStatusCodes = null;
        headers = null;

        jsonStringRequestBody = null;
        objectRequestBody = null;

        // bodyType = null; // handled by Gson
        responseType = null;
    }

    // region Builder Tools
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public VolleyRequestBuilder<T> withHttpMethod(HTTPMethod method) {
        this.method = method.value;
        return this;
    }

    public VolleyRequestBuilder<T> withUrl(String url) {
        this.url = url;
        return this;
    }

    public VolleyRequestBuilder<T> withCallback(final VolleyRequestCallback<T> callback) {
        this.requestCallback = callback;
        return this;
    }

    // TODO don't really need the reponse listener -- you can call requestCallback.OnSuccess directly
    private void makeResponseListener() {
        this.responseListener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (requestCallback != null) {
                    requestCallback.onSuccess(response);
                }
            }
        };
    }

    private void makeErrorListener() {
        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Integer statusCode = null;
                if (error.networkResponse != null) {
                    statusCode = error.networkResponse.statusCode;
                }
                String errorMessage = null;
                // TODO refactor these messages
                // list of all VolleyErrors:
                // http://afzaln.com/volley/com/android/volley/class-use/VolleyError.html
                if (error.getMessage() != null) {
                    errorMessage = error.getMessage();
                } else if (error instanceof NetworkError) {
                    errorMessage = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    errorMessage =
                            "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    errorMessage = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    errorMessage = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    errorMessage = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    errorMessage = "Connection TimeOut! Please check your internet connection.";
                }
                if (requestCallback != null) {
                    requestCallback.onFailure(statusCode, errorMessage);
                }
                if (statusCodeCallback != null) {
                    if (expectedStatusCodes == null) {
                        statusCodeCallback.onExpectedStatusCode(statusCode);
                    } else {
                        if (expectedStatusCodes.contains(statusCode)) {
                            statusCodeCallback.onExpectedStatusCode(statusCode);
                        } else {
                            statusCodeCallback.onUnexpectedStatusCode(statusCode, errorMessage);
                        }
                    }
                }
            }
        };
    }

    public VolleyRequestBuilder<T> withStatusCodeCallback(List<Integer> expectedStatusCodes,
            final VolleyResponseStatusCodeCallback statusCodeCallback) {

        this.expectedStatusCodes = expectedStatusCodes;
        this.statusCodeCallback = statusCodeCallback;
        return this;
    }

    public VolleyRequestBuilder<T> withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public VolleyRequestBuilder<T> withJsonStringBody(String body) {
        this.jsonStringRequestBody = body;
        return this;
    }

    // TODO this body type might need to know the body for Gson to work properly
    public VolleyRequestBuilder<T> withObjectBody(Object body) {
        this.objectRequestBody = body;
        return this;
    }

    //    public VolleyRequestBuilder withBodyType(Type bodyType) {
    //        this.bodyType = bodyType;
    //        return this;
    //    }

    public VolleyRequestBuilder<T> withResponseType(Type responseType) {
        this.responseType = responseType;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // endregion

    public Request<T> build() {
        try {
            assertBuildable();

            return new Request<T>(method, url, errorListener) {
                // region Network Response
                ///////////////////////////////////////////////////////////////////////////////////////

                // this is for GET requests
                @Override
                protected Response<T> parseNetworkResponse(NetworkResponse response) {
                    // TODO make it so that you can have both types of callbacks
                    if (statusCodeCallback != null) { // response is just a HTTP Status Code
                        Log.d("VolleyRequestBuilder",
                                "parseNetworkResponse: statusCode=" + response.statusCode);
                        return Response.error(new VolleyError(response));
                    } else { // response is JSON => parse it
                        try {
                            //gets the JSON string from the network response
                            String jsonString = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers));
                            Log.d("VolleyRequestBuilder",
                                    "parseNetworkResponse: JSON response string=" + jsonString);
                            return (Response<T>) Response.success(
                                    new Gson().fromJson(jsonString, responseType),
                                    HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (JsonSyntaxException e) {
                            return Response.error(new ParseError(e));
                        }
                    }
                }

                @Override
                protected void deliverResponse(T response) {
                    //TODO don't really need the response Listener -- you can just call requestCallback.onSuccess
                    responseListener.onResponse(response);
                }

                @Override
                public void deliverError(VolleyError error) {
                    Log.d("VolleyRequestBuilder", "deliverError: " + error.getMessage());
                    super.deliverError(error);
                }
                ///////////////////////////////////////////////////////////////////////////////////////
                // endregion

                // region methods for POST and PUT requests
                ///////////////////////////////////////////////////////////////////////////////////////

                // this is for POST and PUT requests
                @Override
                public String getBodyContentType() {
                    // TODO not needed for now but you can extend this to support other protocol content types and charsets
                    if (objectRequestBody != null) {
                        return "application/json; charset=utf-8";
                    }
                    return super.getBodyContentType();
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    String finalBody;
                    // TODO add the ASSERT check that EXACTLY one of the two bodies is present
                    if (jsonStringRequestBody != null) { // JSON string has been passed
                        finalBody = jsonStringRequestBody;
                    } else { // Gson must convert the object to string
                        // TODO: objectRequestBody is of type Object so this might not work properly and might need the actual type of the object
                        finalBody = new Gson().toJson(objectRequestBody);
                    }

                    try {
                        return finalBody == null ? null : finalBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf(
                                "Unsupported Encoding while trying to get the bytes of %s using %s",
                                finalBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers != null ? headers : super.getHeaders();
                }

                ///////////////////////////////////////////////////////////////////////////////////////
                // endregion
            };
        } catch (Exception e) {
            Log.d("VolleyRequestBuilder", e.getMessage());
            return null;
        }
    }

    private void assertBuildable() throws Exception {
        if (method == null) {
            throw new Exception(
                    "VolleyRequestBuilder needs an HTTPMethod. Use .withHttpMethod(HTTPMethod method)");
        }
        if (url == null) {
            throw new Exception("VolleyRequestBuilder needs an URL. Use .withUrl(String url)");
        }
        if (requestCallback == null && statusCodeCallback == null) {
            throw new Exception(
                    "VolleyRequestBuilder needs a requestCallback. Use .withCallback(final VolleyRequestCallback requestCallback) OR .withStatusCodeCallback(final VolleyResponseStatusCodeCallback statusCodeCallback)");
        }
        // TODO make it so that you can have both callbacks!!!
        if (requestCallback != null && statusCodeCallback != null) {
            throw new Exception("VolleyRequestBuilder needs EXACTLY ONE callback. You have two.");
        }
        if (method == HTTPMethod.POST.value || method == HTTPMethod.PUT.value) {
            if (jsonStringRequestBody == null && objectRequestBody == null) {
                throw new Exception("VolleyRequestBuilder needs a body for " + method.toString()
                        + ". Use either .withJsonStringBody(String body) or .withObjectBody(Object body)");
            }
            if (jsonStringRequestBody != null && objectRequestBody != null) {
                throw new Exception(
                        "VolleyRequestBuilder needs a exactly one body for " + method.toString()
                                + ". Use EITHER .withJsonStringBody(String body) OR .withObjectBody(Object body), but not both.");
            }
        }
    }

    public void addToQueue(RequestQueue queue) {
        queue.add(this.build());
    }
}
