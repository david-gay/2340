package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager singleton for all WaterSourceReports.
 */
public class ReportManager {

    private Map<Integer, MutableWaterSourceReport> waterSourceReports;
    private Map<Integer, MutableWaterPurityReport> waterPurityReports;
    private int nextReportId;

    private static ReportManager ourInstance = new ReportManager();

    /**
     * Gets the ReportManager instance
     * @return the ReportManager instance
     */
    public static ReportManager getInstance() {
        return ourInstance;
    }

    private ReportManager() {
        waterSourceReports = new HashMap<>();
        waterPurityReports = new HashMap<>();
        nextReportId = 0;
    }

    /**
     * Generates a Water Source Report and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createWaterReport(String authorUsername, Location location, WaterType waterType,
            WaterCondition condition) {

        waterSourceReports.put(nextReportId,
                new WaterSourceReportImpl(nextReportId, authorUsername, new Date(), location,
                        waterType, condition));

        nextReportId++;

        return true;
    }

    /**
     * Generates a Water Purity Report and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createPurityReport(String authorUsername, Location location, WaterType waterType,
            WaterCondition condition, WaterPurityCondition waterPurityCondition, float virusPPM,
            float contaminantPPM) {

        WaterSourceReportImpl potentialReport = new WaterSourceReportImpl(nextReportId,
                authorUsername, new Date(), location, waterType, condition, waterPurityCondition,
                virusPPM, contaminantPPM);

        waterSourceReports.put(nextReportId, potentialReport);
        waterPurityReports.put(nextReportId, potentialReport);

        nextReportId++;

        return true;
    }

    /**
     * Returns the WaterSourceReport for the given id.
     * @param id the id of the WaterSourceReport
     * @return the WaterSourceReportImpl; null if no such report exists
     */
    public WaterSourceReport getReportById(int id) {
        if (!waterSourceReports.containsKey(id)) {
            return null;
        }
        return waterSourceReports.get(id);
    }

    /**
     * Returns the WaterPurityReport for the given id.
     * @param id the id of the WaterPurityReport
     * @return the WaterSourceReportImpl; null if no such report exists
     */
    public WaterPurityReport getPurityReportById(int id) {
        if (!waterPurityReports.containsKey(id)) {
            return null;
        }
        return waterPurityReports.get(id);
    }

    /**
     * Updates a water source report, if it exists.
     * @param id the id of the report to update
     * @param waterType the new waterType
     * @param condition the new condition
     * @return true if the report was updated
     */
    public boolean updateWaterSourceReportWithId(int id, Location location, WaterType waterType,
            WaterCondition condition) {
        if (waterSourceReports.containsKey(id)) {
            MutableWaterSourceReport report = waterSourceReports.get(id);
            report.setWaterType(waterType);
            report.setCondition(condition);
            report.setLocation(location);
            return true;
        }
        return false;
    }

    /**
     * Updates a water purity report, if it exists.
     * @param id the id of the report to update
     * @param waterType the new waterType
     * @param condition the new condition
     * @return true if the report was updated
     */
    public boolean updateWaterPurityReportWithId(int id, Location location, WaterType waterType,
            WaterCondition condition, WaterPurityCondition waterPurityCondition, float virusPPM,
            float contaminantPPM) {
        if (waterPurityReports.containsKey(id)) {
            MutableWaterPurityReport report = waterPurityReports.get(id);
            report.setWaterType(waterType);
            report.setCondition(condition);
            report.setLocation(location);
            report.setWaterPurityCondition(waterPurityCondition);
            report.setVirusPPM(virusPPM);
            report.setContaminantPPM(contaminantPPM);
            return true;
        }
        return false;
    }

    /**
     * Deletes the report with id
     * @param id the report id to delete
     * @return true if a report was delete; false otherwise
     */
    public boolean deleteReportById(int id) {
        if (waterSourceReports.containsKey(id) || waterPurityReports.containsKey(id)) {
            waterSourceReports.remove(id);
            waterPurityReports.remove(id);
            return true;
        }
        return false;
    }
}
