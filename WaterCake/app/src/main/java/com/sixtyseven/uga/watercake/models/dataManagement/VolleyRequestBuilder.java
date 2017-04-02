package com.sixtyseven.uga.watercake.models.dataManagement;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
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
public class VolleyRequestBuilder {

    //    public class HTTPMethod implements Request.Method {
    //    }

    public interface VolleyRequestCallback {
        void onSuccess(Object response);

        void onFailure(Integer httpStatusCode, String errorMessage);
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

    private Response.Listener responseListener;
    private Response.ErrorListener errorListener;
    private VolleyRequestCallback callback; // this is not really needed... I think

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
        callback = null;

        expectedStatusCodes = null;
        headers = null;

        jsonStringRequestBody = null;
        objectRequestBody = null;

        // bodyType = null; // handled by Gson
        responseType = null;
    }

    // region Builder Tools
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public VolleyRequestBuilder withHttpMethod(HTTPMethod method) {
        this.method = method.value;
        return this;
    }

    public VolleyRequestBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public VolleyRequestBuilder withCallback(final VolleyRequestCallback callback) {

        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO something smart to parse VolleyError into .onFailure()
                Integer statusCode = null;
                if (error.networkResponse != null) {
                    statusCode = error.networkResponse.statusCode;
                }
                String errorMessage = null;
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
                callback.onFailure(statusCode, errorMessage);
            }
        };

        this.responseListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                // TODO use Gson to convert the response from JSON to object
                callback.onSuccess(response);
            }
        };

        this.callback = callback; // saving this for Justin Case
        return this;
    }

    public VolleyRequestBuilder withExpectedStatusCodes(List<Integer> expectedStatusCodes) {
        this.expectedStatusCodes = expectedStatusCodes;
        return this;
    }

    public VolleyRequestBuilder withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public VolleyRequestBuilder withJsonStringBody(String body) {
        this.jsonStringRequestBody = body;
        return this;
    }

    // TODO this body type might need to know the body for Gson to work properly
    public VolleyRequestBuilder withObjectBody(Object body) {
        this.objectRequestBody = body;
        return this;
    }

    //    public VolleyRequestBuilder withBodyType(Type bodyType) {
    //        this.bodyType = bodyType;
    //        return this;
    //    }

    public VolleyRequestBuilder withResponseType(Type responseType) {
        this.responseType = responseType;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // endregion

    public Request build() {
        try {
            assertBuildable();

            return new Request(method, url, errorListener) {
                // region Network Response
                ///////////////////////////////////////////////////////////////////////////////////////

                // this is for GET requests
                @Override
                protected Response parseNetworkResponse(NetworkResponse response) {
                    if (responseType == null) { // response is just a HTTP Status Code
                        Log.d("VolleyRequestBuilder",
                                "parseNetworkResponse: statusCode=" + response.statusCode);
                        return Response.success(response.statusCode,
                                HttpHeaderParser.parseCacheHeaders(response));
                    } else { // response is JSON => parse it
                        try {
                            //gets the JSON string from the network response
                            String jsonString = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers));
                            Log.d("VolleyRequestBuilder",
                                    "parseNetworkResponse: JSON response string=" + jsonString);
                            return Response.success(new Gson().fromJson(jsonString, responseType),
                                    HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (JsonSyntaxException e) {
                            return Response.error(new ParseError(e));
                        }
                    }
                }

                @Override
                protected void deliverResponse(Object response) {
                    Log.d("VolleyRequestBuilder",
                            "deliverResponse: response type=" + (response instanceof Integer ?
                                    "int" : "response object type"));
                    //TODO don't really need the response Listener -- you can just call callback.onSuccess
                    responseListener.onResponse(response);
                }

                @Override
                public void deliverError(VolleyError error) {
                    if (error.networkResponse != null) {
                        Integer statusCode = error.networkResponse.statusCode;
                        if (expectedStatusCodes.contains(statusCode)) {
                            Log.d("VolleyRequestBuilder",
                                    "deliverError: expected statusCode=" + statusCode);
                            responseListener.onResponse(statusCode);
                        } else {
                            // TODO some more graceful handling pls
                            // unexpected status code
                            Log.d("VolleyRequestBuilder",
                                    "deliverError: unexpected statusCode=" + statusCode);
                            super.deliverError(error);
                        }
                    } else {
                        // TODO some more graceful handling pls -- might be done in the error listener
                        // no response from the network was received
                        Log.d("VolleyRequestBuilder", "deliverError: network response is null");
                        super.deliverError(error);
                    }
                }
                ///////////////////////////////////////////////////////////////////////////////////////
                // endregion

                // region methods for POST and PUT requests
                ///////////////////////////////////////////////////////////////////////////////////////

                /**
                 * @deprecated Use {@link #getBodyContentType()}.
                 */
                @Override
                public String getPostBodyContentType() {
                    return getBodyContentType();
                }

                // this is for POST and PUT requests
                @Override
                public String getBodyContentType() {
                    // TODO not needed for now but you can extend this to support other protocol content types and charsets
                    if (objectRequestBody != null) {
                        return "application/json; charset=utf-8";
                    }
                    return super.getBodyContentType();
                }

                /**
                 * @deprecated Use {@link #getBody()}.
                 */
                @Override
                public byte[] getPostBody() throws AuthFailureError {
                    return getBody();
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
        if (callback == null) {
            throw new Exception(
                    "VolleyRequestBuilder needs a callback. Use .withCallback(final VolleyRequestCallback callback)");
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
}
