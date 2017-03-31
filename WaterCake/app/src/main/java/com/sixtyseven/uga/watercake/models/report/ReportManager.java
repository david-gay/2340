package com.sixtyseven.uga.watercake.models.report;

import android.content.Context;
import android.icu.text.RelativeDateTimeFormatter;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sixtyseven.uga.watercake.models.dataManagement.RestManager;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;
import com.sixtyseven.uga.watercake.models.report.helpers.GsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Manager singleton for all WaterSourceReports.
 */
public class ReportManager {
    private static Context context;
    private List<WaterSourceReport> waterSourceReports;
    private List<WaterPurityReport> waterPurityReports;
    private int nextReportId;

    private static ReportManager ourInstance;

    /**
     * Gets the ReportManager instance
     * @return the ReportManager instance
     */
    public static ReportManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new ReportManager(context);
        }
        return ourInstance;
    }

    /**
     * Constructor
     */
    private ReportManager(Context context) {
        this.context = context;
        waterSourceReports = new LinkedList<>();
        waterPurityReports = new LinkedList<>();

        nextReportId = 2; // TODO get rid of this when you do POST
    }

    public void fetchAllReports() {
        getWaterSourceReportsFromServer();
        getWaterPurityReportsFromServer();
    }

    public void getWaterSourceReportsFromServer() {
        String url = "http://10.0.2.2:8080/water-reports";
        JsonArrayRequest getAllWaterReportsRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                waterSourceReports = new Gson().fromJson(response.toString(),
                        new TypeToken<LinkedList<WaterSourceReportImpl>>() {
                        }.getType());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });

        RestManager.getInstance(context).addToRequestQueue(getAllWaterReportsRequest);
    }

    public void getWaterPurityReportsFromServer() {

        String url = "http://10.0.2.2:8080/purity-reports";
        JsonArrayRequest getAllWaterPurityReportsRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                waterPurityReports = new Gson().fromJson(response.toString(),
                        new TypeToken<LinkedList<WaterPurityReportImpl>>() {
                        }.getType());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });

        RestManager.getInstance(context).addToRequestQueue(getAllWaterPurityReportsRequest);
    }

    /**
     * Generates a Water Source Report and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createWaterReport(String authorUsername, double latitude, double longitude,
            WaterType waterType, WaterCondition condition) {
        try {
            JSONObject sourceReportStub = new JSONObject(new Gson()
                    .toJson(new WaterSourceReportImpl(0, "dimitar", null, latitude, longitude,
                            waterType, condition)));
            // TODO change dimitar to authorUsername when login is done
            // you can also do CurrentSession.get username or whatever and remove the param
            String url = "http://10.0.2.2:8080/dimitar/water-reports";
            JsonObjectRequest createWaterSourceReportRequest = new JsonObjectRequest(
                    Request.Method.POST, url, sourceReportStub,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            waterSourceReports.add(new Gson()
                                    .fromJson(response.toString(), WaterSourceReportImpl.class));
                            // TODO add callback to show the user that the addition was successful -- i don't like this return true thing for async
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                }
            });

            RestManager.getInstance(context).addToRequestQueue(createWaterSourceReportRequest);

            return true;
        } catch (JSONException ex) {
            Log.d("sourceReportPOST", ex.getMessage());
        }
        return false;
    }

    public boolean createPurityReport(String authorUsername, double latitude, double longitude,
            WaterPurityCondition waterPurityCondition, float virusPPM, float contaminantPPM) {
        try {
            JSONObject purityReportStub = new JSONObject(new Gson()
                    .toJson(new WaterPurityReportImpl(0, "dimitar", null, latitude, longitude,
                            waterPurityCondition, virusPPM, contaminantPPM)));
            // TODO change dimitar to authorUsername when login is done
            // you can also do CurrentSession.get username or whatever and remove the param
            String url = "http://10.0.2.2:8080/dimitar/purity-reports";
            JsonObjectRequest createWaterPurityReportRequest = new JsonObjectRequest(
                    Request.Method.POST, url, purityReportStub,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            waterPurityReports.add(new Gson()
                                    .fromJson(response.toString(), WaterPurityReportImpl.class));
                            // TODO add callback to show the user that the addition was successful -- i don't like this return true thing for async
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                }
            });

            RestManager.getInstance(context).addToRequestQueue(createWaterPurityReportRequest);

            return true;
        } catch (JSONException ex) {
            Log.d("sourceReportPOST", ex.getMessage());
        }
        return false;
    }

    /**
     * Returns a list of all WaterSourceReports
     * @return a list of all WaterSourceReports
     */
    public List<WaterSourceReport> getWaterSourceReportList() {
        //        List<WaterSourceReport> list = new ArrayList<>(waterSourceReports.values());
        //        Collections.sort(list, new Comparator<WaterSourceReport>() {
        //            @Override
        //            public int compare(WaterSourceReport o1, WaterSourceReport o2) {
        //                return o1.getReportNumber() - o2.getReportNumber();
        //            }
        //        });
        //
        //        return list;
        return waterSourceReports;
    }

    /**
     * Returns a list of all PurityReports
     * @return a list of all PurityReports
     */
    public List<WaterPurityReport> getWaterPurityReportList() {
        //        List<WaterPurityReport> list = new ArrayList<>(waterPurityReports.values());
        //        Collections.sort(list, new Comparator<WaterPurityReport>() {
        //            @Override
        //            public int compare(WaterPurityReport o1, WaterPurityReport o2) {
        //                return o1.getReportNumber() - o2.getReportNumber();
        //            }
        //        });
        //
        //        return list;
        return waterPurityReports;
    }

    /**
     * Returns the WaterSourceReport for the given id.
     * @param id the id of the WaterSourceReport
     * @return the WaterSourceReportImpl; null if no such report exists
     */
    public WaterSourceReport getReportById(int id) {
        for (WaterSourceReport wsr : waterSourceReports) {
            if (wsr.getReportNumber() == id) {
                return wsr;
            }
        }
        return null;
    }

    /**
     * Returns the WaterPurityReport for the given id.
     * @param id the id of the WaterPurityReport
     * @return the WaterSourceReportImpl; null if no such report exists
     */
    public WaterPurityReport getPurityReportById(int id) {
        for (WaterPurityReport wpr : waterPurityReports) {
            if (wpr.getReportNumber() == id) {
                return wpr;
            }
        }
        return null;
    }

    /**
     * Deletes the report with id
     * @param id the report id to delete
     * @return true if a report was delete; false otherwise
     */
    public boolean deleteReportById(int id) {
        boolean deleted = false;

        for (int i = 0; i < waterSourceReports.size(); i++) {
            if (waterSourceReports.get(i).getReportNumber() == id) {
                waterSourceReports.remove(i);
                deleted = true;
            }
        }

        for (int i = 0; i < waterPurityReports.size(); i++) {
            if (waterPurityReports.get(i).getReportNumber() == id) {
                waterPurityReports.remove(i);
                deleted = true;
            }
        }

        return deleted;
    }
}
