package com.sixtyseven.uga.watercake.models.dataManagement;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.WaterPurityReport;
import com.sixtyseven.uga.watercake.models.report.WaterSourceReport;
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
        RestManager.context = context;
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

    public <T> void getAllWaterSourceReports(final Callback<T> callback, Type type) {
        // TODO add ServerManager that takes care of generating urls for the server requests
        getReportByType(callback, type, url + "water-reports");
    }

    public <T> void getAllWaterPurityReports(final Callback<T> callback, Type type) {
        // TODO add ServerManager that takes care of generating urls for the server requests
        getReportByType(callback, type, url + "purity-reports");
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
                        .withUrl(url + "login")
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
    }

    public void createWaterSourceReport(String username, WaterSourceReport report,
            final ReportManager.CreateSourceReportCallback callback)
    {
        getRequestQueue().add(
                new VolleyRequestBuilder<WaterSourceReport>()
                        .withUrl(url + username + "/water-reports")
                        .withHttpMethod(VolleyRequestBuilder.HTTPMethod.POST)
                        .withObjectBody(report)
                        .withResponseType(report.getClass())
                        .withCallback(new VolleyRequestBuilder.VolleyRequestCallback<WaterSourceReport>() {
                            @Override
                            public void onSuccess(WaterSourceReport response) {
                                callback.onSuccess(response);
                            }

                            @Override
                            public void onFailure(Integer httpStatusCode, String errorMessage) {
                                callback.onFailure(errorMessage);
                            }
                        })
                        .build());
    }

    public void createWaterPurityReport(String username, WaterPurityReport report,
            final ReportManager.CreatePurityReportCallback callback)
    {
        getRequestQueue().add(
                new VolleyRequestBuilder<WaterPurityReport>()
                        .withUrl(url + username + "/purity-reports")
                        .withHttpMethod(VolleyRequestBuilder.HTTPMethod.POST)
                        .withObjectBody(report)
                        .withResponseType(report.getClass())
                        .withCallback(new VolleyRequestBuilder.VolleyRequestCallback<WaterPurityReport>() {
                            @Override
                            public void onSuccess(WaterPurityReport response) {
                                callback.onSuccess(response);
                            }

                            @Override
                            public void onFailure(Integer httpStatusCode, String errorMessage) {
                                callback.onFailure(errorMessage);
                            }
                        })
                        .build());
    }

    public void registerUser(User user, final UserSession.RegisterCallback registerCallback) {
        new VolleyRequestBuilder()
                .withUrl("http://10.0.2.2:8080/accounts/")
                .withHttpMethod(VolleyRequestBuilder.HTTPMethod.POST)
                .withObjectBody(user)
                .withStatusCodeCallback(Arrays.asList(201),
                        new VolleyRequestBuilder.VolleyResponseStatusCodeCallback() {
                            @Override
                            public void onExpectedStatusCode(Integer expectedStatusCode) {
                                registerCallback.onSuccess();
                            }

                            @Override
                            public void onUnexpectedStatusCode(Integer unexpectedStatusCode,
                                    String errorMessage) {
                                registerCallback.onFailure(errorMessage);
                            }
                        })
                .addToQueue(getRequestQueue());
    }

    public void getUser(final String username, final UserSession.UserCallback userCallback) {
        getRequestQueue().add(
                new VolleyRequestBuilder<User>()
                        .withUrl(url + "accounts/" + username)
                        .withHttpMethod(VolleyRequestBuilder.HTTPMethod.GET)
                        .withResponseType(User.class)
                        .withCallback(new VolleyRequestBuilder.VolleyRequestCallback<User>() {
                            @Override
                            public void onSuccess(User response) {
                                userCallback.onSuccess(response);
                            }

                            @Override
                            public void onFailure(Integer httpStatusCode, String errorMessage) {
                                userCallback.onFailure(errorMessage);
                            }
                        })
                        .build());
    }
}
