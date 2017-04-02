package com.sixtyseven.uga.watercake.models.dataManagement;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sixtyseven.uga.watercake.models.user.User;

import java.lang.reflect.Type;
import java.util.Arrays;

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
        getRequestQueue().add(
                new VolleyRequestBuilder<T>()
                        .withUrl(url)
                        .withHttpMethod(VolleyRequestBuilder.HTTPMethod.GET)
                        .withResponseType(type)
                        .withCallback(new VolleyRequestBuilder.VolleyRequestCallback<T>() {
                            @Override
                            public void onSuccess(T response) {
                                callback.onSuccess(response);
                            }

                            @Override
                            public void onFailure(Integer httpStatusCode, String errorMessage) {
                                callback.onFailure(errorMessage);
                            }
                        })
                        .build());
    }

    public void validateUser(String username, String password, final Callback<Integer> callback) {
        getRequestQueue().add(
                new VolleyRequestBuilder()
                        .withUrl("http://10.0.2.2:8080/login")
                        .withHttpMethod(VolleyRequestBuilder.HTTPMethod.POST)
                        .withObjectBody(new Credentials(username, password))
                        .withStatusCodeCallback(Arrays.asList(204, 401, 404),
                                new VolleyRequestBuilder.VolleyResponseStatusCodeCallback() {
                                    @Override
                                    public void onExpectedStatusCode(Integer expectedStatusCode) {
                                        callback.onSuccess(expectedStatusCode);
                                    }

                                    @Override
                                    public void onUnexpectedStatusCode(Integer unexpectedStatusCode,
                                            String errorMessage) {
                                        callback.onFailure(errorMessage);
                                    }
                                })
                        .build());

        //        String requestBody = new Gson().toJson(new Credentials(username, password));
        //        LoginRequest req = new LoginRequest(Request.Method.POST, url, requestBody,
        //                Arrays.asList(204, 401, 404), new Response.Listener<Integer>() {
        //            @Override
        //            public void onExpectedStatusCode(Integer response) {
        //                Log.d("validateUser", "Response: " + response);
        //                callback.onSuccess(response);
        //            }
        //        }, new Response.ErrorListener() {
        //            @Override
        //            public void onErrorResponse(VolleyError error) {
        //                String message = null;
        //                if (error instanceof NetworkError) {
        //                    message = "Cannot connect to Internet...Please check your connection!";
        //                } else if (error instanceof ServerError) {
        //                    message = "The server could not be found. Please try again after some time!!";
        //                } else if (error instanceof AuthFailureError) {
        //                    message = "Cannot connect to Internet...Please check your connection!";
        //                } else if (error instanceof ParseError) {
        //                    message = "Parsing error! Please try again after some time!!";
        //                } else if (error instanceof NoConnectionError) {
        //                    message = "Cannot connect to Internet...Please check your connection!";
        //                } else if (error instanceof TimeoutError) {
        //                    message = "Connection TimeOut! Please check your internet connection.";
        //                }
        //                Log.d("validateUser", "error: " + message);
        //                callback.onFailure(message);
        //            }
        //        });
        //        getRequestQueue().add(req);
    }

    public void registerUser(User user) {

    }

    public interface Callback<T> {
        void onSuccess(T response);

        void onFailure(String errorMessage);
    }
}
