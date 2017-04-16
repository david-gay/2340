package com.sixtyseven.uga.watercake.models.report;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sixtyseven.uga.watercake.controllers.CreatePurityReportActivity;
import com.sixtyseven.uga.watercake.controllers.CreateWaterReportActivity;
import com.sixtyseven.uga.watercake.controllers.MainActivity;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.dataManagement.RestManager;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Manager singleton for all WaterSourceReports.
 */
public class ReportManager {
    private static ReportManager ourInstance;

    private static Context context;
    private List<WaterSourceReport> waterSourceReports;
    private List<WaterPurityReport> waterPurityReports;

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
        ReportManager.context = context;
        waterSourceReports = new LinkedList<>();
        waterPurityReports = new LinkedList<>();
    }

    /**
     * Fetches all Water Source Reports and Water Purity Reports from the server.
     * @param fetchReportsCallback the callback for reports fetching
     */
    public void fetchAllReports(final MainActivity.FetchReportsCallback fetchReportsCallback) {
        getWaterSourceReportsFromServer(fetchReportsCallback);
        getWaterPurityReportsFromServer(fetchReportsCallback);
    }

    /**
     * Fetches all Water Source Reports from the server.
     * @param fetchReportsCallback the callback for the report fetching
     */
    public void getWaterSourceReportsFromServer(
            final MainActivity.FetchReportsCallback fetchReportsCallback) {
        RestManager.getInstance(context).getAllWaterSourceReports(
                new RestManager.Callback<List<WaterSourceReport>>() {
                    @Override
                    public void onSuccess(List<WaterSourceReport> response) {
                        Log.d("ReportManager",
                                "Received " + response.size() + " water source reports");
                        fetchReportsCallback.onSuccess();
                        waterSourceReports = response;
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        fetchReportsCallback.onFailure(errorMessage);
                        Log.d("ReportManagerError", errorMessage);
                    }
                }, new TypeToken<LinkedList<WaterSourceReportImpl>>() {
                }.getType());
    }

    /**
     * Fetches all Water Purity Reports from the server.
     * @param fetchReportsCallback the callback for reports fetching
     */
    public void getWaterPurityReportsFromServer(
            final MainActivity.FetchReportsCallback fetchReportsCallback) {

        RestManager.getInstance(context).getAllWaterPurityReports(
                new RestManager.Callback<List<WaterPurityReport>>() {
                    @Override
                    public void onSuccess(List<WaterPurityReport> response) {
                        fetchReportsCallback.onSuccess();
                        waterPurityReports = response;
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        fetchReportsCallback.onFailure(errorMessage);
                        Log.d("ReportManagerError", errorMessage);
                    }
                }, new TypeToken<LinkedList<WaterPurityReportImpl>>() {
                }.getType());
    }

    /**
     * Generates a Water Source Report and stores it
     * @param latitude the latitude for the report
     * @param longitude the longitude for the report
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @param callback the callback for the response
     */
    public void createWaterReport(double latitude, double longitude, WaterType waterType,
            WaterCondition condition,
            final CreateWaterReportActivity.CreateWaterSourceReportCallback callback) {
        RestManager.getInstance(context).createWaterSourceReport(UserSession.currentSession(context)
                        .getCurrentUser().getUsername(),
                new WaterSourceReportImpl(0, "", null, latitude, longitude, waterType, condition),
                new CreateSourceReportCallback() {
                    @Override
                    public void onSuccess(WaterSourceReport report) {
                        waterSourceReports.add(report);
                        MainActivity.placeNewMarker(report);
                        callback.onSuccess();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("ReportManager", errorMessage);
                        callback.onFailure(errorMessage);
                    }
                });
    }

    /**
     * Generates a Water Purity Report and stores it
     * @param latitude the latitude coordinate of the report
     * @param longitude the longitude coordinate of the report
     * @param waterPurityCondition the purity condition of the water
     * @param virusPPM the virus parts per million in the water
     * @param contaminantPPM the contaminants parts per million in the water
     * @param callback the callback for the response
     */
    public void createPurityReport(double latitude, double longitude,
            WaterPurityCondition waterPurityCondition, float virusPPM, float contaminantPPM,
            final CreatePurityReportActivity.CreateWaterPurityReportCallback callback) {
        RestManager.getInstance(context).createWaterPurityReport(UserSession.currentSession(context)
                        .getCurrentUser().getUsername(),
                new WaterPurityReportImpl(0, "", null, latitude, longitude, waterPurityCondition,
                        virusPPM, contaminantPPM), new CreatePurityReportCallback() {
                    @Override
                    public void onSuccess(WaterPurityReport report) {
                        waterPurityReports.add(report);
                        callback.onSuccess();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("ReportManager", errorMessage);
                        callback.onFailure(errorMessage);
                    }
                });
    }

    /**
     * Returns a list of all WaterSourceReports
     * @return a list of all WaterSourceReports
     */
    public List<WaterSourceReport> getWaterSourceReportList() {
        return waterSourceReports;
    }

    /**
     * Returns a list of all PurityReports
     * @return a list of all PurityReports
     */
    public List<WaterPurityReport> getWaterPurityReportList() {

        return waterPurityReports;
    }

    /**
     * Returns a list of the PurityReports that are in the passed in year and near the position
     * @return a list of all PurityReports
     */
    public List<WaterPurityReport> filterWaterPurityReportList(int year, double latitude,
            double longitude) {
        List<WaterPurityReport> filteredWaterPurityReports = new ArrayList<>(
                waterPurityReports.size());
        for (WaterPurityReport wpr : waterPurityReports) {
            if (wpr.getPostDate().getYear() + 1900 == year && Math.abs(wpr.getLatitude() - latitude)
                    + Math.abs(wpr.getLongitude() - longitude) < 0.0001) {
                filteredWaterPurityReports.add(wpr);
            }
        }
        Collections.sort(filteredWaterPurityReports, new Comparator<WaterPurityReport>() {
            @Override
            public int compare(WaterPurityReport o1, WaterPurityReport o2) {
                return o1.getReportNumber() - o2.getReportNumber();
            }
        });

        return filteredWaterPurityReports;
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

    public interface CreateSourceReportCallback {
        void onSuccess(WaterSourceReport report);

        void onFailure(String errorMessage);
    }

    public interface CreatePurityReportCallback {
        void onSuccess(WaterPurityReport report);

        void onFailure(String errorMessage);
    }
}
