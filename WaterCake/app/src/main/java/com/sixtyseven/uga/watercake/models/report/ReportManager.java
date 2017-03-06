package com.sixtyseven.uga.watercake.models.report;


import android.util.Log;

import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.EnumSet;

/**
 * Manager singleton for all WaterSourceReports.
 */
public class ReportManager {

    private Map<Integer, WaterSourceReport> reports;
    private List reportsList;
    private int nextReportId;
    private Location location;
    private WaterSourceReport currentReport;

    private static ReportManager ourInstance = new ReportManager();

    /**
     * Gets the ReportManager instance
     * @return the ReportManager instance
     */
    public static ReportManager getInstance() {
        return ourInstance;
    }

    private ReportManager() {
        reports = new HashMap<>();
        nextReportId = 0;

    }

    /**
     * Attempts to register a user and returns an EnumSet of any registration errors present.
     * @param fieldMap a map of UserProfileFields to their associated data Strings to use for user
     * creation. Must have UserProfileField.USERNAME.
     * @return an EnumSet of every error encountered in registration. Empty if registration was
     * successful.
     */
    public EnumSet<SourceReportError> registerUser(Map<SourceReportField, String> fieldMap) {
        EnumSet<SourceReportError> results = validateReportFields(fieldMap);

        String username = fieldMap.get(SourceReportField.LATITUDE);

 //       if (results.isEmpty()) { // if we had no problems, then go ahead and register
  //          createWaterReport(location, WaterSourceReport.generateReportFromFieldsMap(fieldMap));
//        }

        StringBuilder logOutput = new StringBuilder();
        boolean first = true;
        for (SourceReportError result : results) {
            if (!first) {
                logOutput.append(", ");
            } else {
                first = false;
            }
            logOutput.append(result.getMessage());
        }

        Log.d("newReport", logOutput.toString());
        return results;
    }

    /**
     * Performs validation on all fields present in fieldMap.
     * @param fieldMap a map of UserProfileFields to their associated data Strings
     * @return an EnumSet of every error encountered in validation.
     */
    public EnumSet<SourceReportError> validateReportFields(Map<SourceReportField, String> fieldMap) {

        EnumSet<SourceReportError> results = EnumSet.noneOf(SourceReportError.class);

        if (fieldMap.containsKey(SourceReportField.LATITUDE)) {
            String latitudeString = fieldMap.get(SourceReportField.LATITUDE);
            if (latitudeString.equals("")) {
                results.add(SourceReportError.INVALID_LOCATION);
            } else {
                double latitude = Double.parseDouble(latitudeString);
                if (latitude < -90 || latitude > 90) {
                    results.add(SourceReportError.INVALID_LOCATION);
                }
            }
        }

        if (fieldMap.containsKey(SourceReportField.LONGITUDE)) {
            String longitudeString = fieldMap.get(SourceReportField.LONGITUDE);
            if (longitudeString.equals("")) {
                results.add(SourceReportError.INVALID_LOCATION);
            }
            if (!longitudeString.equals("")){
                double longitude = Double.parseDouble(longitudeString);
                if (longitude < -180 || longitude > 180) {
                    results.add(SourceReportError.INVALID_LOCATION);
                }
            }
        }

        return results;
    }

    /**
     * Updates the current user based on a map of UserProfileFields to Strings
     * @param fieldMap a map of UserProfileFields to their associated data Strings
     * @return an EnumSet of every error encountered in updating. Empty if update was successful.
     */
    public EnumSet<SourceReportError> updateUserFields(Map<SourceReportField, String> fieldMap) {
        EnumSet<SourceReportError> results = validateReportFields(fieldMap);

        if (results.isEmpty()) {
        //    currentReport.setFieldsFromFieldsMap(fieldMap);
        }
        return results;
    }

    /**
     * Generates a WaterSourceReport and stores it
     * @param location location of water source
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createWaterReport(Location location, WaterType waterType,
             WaterCondition condition) {
        //todo validation here
        //if validation passes {
        WaterSourceReport current = new WaterSourceReport(nextReportId, location,waterType,
                condition);
        reports.put(nextReportId, current);
        reportsList.add(current);


        nextReportId++;

        //todo return false if validation fails
        return true;
    }

    public List getReportsList() {
        return reportsList;
    }

    public void setCurrentReport(WaterSourceReport current) {
        currentReport = current;
    }

    public WaterSourceReport getCurrentReport() {return currentReport;}

    public WaterSourceReport getReportByNumber(Integer number) {
        return reports.get(number);
    }

    /**
     * Generates a WaterSourceReport and stores it
     * @param location location of water source
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createPurityReport(Location location, WaterType waterType, WaterCondition
            condition, WaterPurityCondition waterPurityCondition, float virusPPM, float
            contaminantPPM) {
        //todo validation here
        //if validation passes {
        reports.put(nextReportId, new WaterSourceReport(nextReportId, location, waterType,
                condition, waterPurityCondition, virusPPM, contaminantPPM));

        nextReportId++;

        //todo return false if validation fails
        return true;
    }
}
