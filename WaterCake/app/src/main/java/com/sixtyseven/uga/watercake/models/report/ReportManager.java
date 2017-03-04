package com.sixtyseven.uga.watercake.models.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Manager singleton for all WaterSourceReports.
 */
public class ReportManager {

    private Map<Integer, WaterSourceReport> reports;
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
        reports = new HashMap<>();
        nextReportId = 0;
    }

    /**
     * Generates a WaterSourceReport and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createWaterReport(String authorUsername, WaterType waterType, WaterCondition
            condition) {
        //if validation passes {
        reports.put(nextReportId, new WaterSourceReport(nextReportId, authorUsername, new Date()
                , waterType, condition));

        nextReportId++;

        return true;
    }


    /**
     * Generates a WaterSourceReport and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createPurityReport(String authorUsername, WaterType waterType, WaterCondition
            condition, WaterPurityCondition waterPurityCondition, float virusPPM, float
            contaminantPPM) {
        //if validation passes {
        reports.put(nextReportId, new WaterSourceReport(nextReportId, authorUsername, new Date(),
                waterType, condition, waterPurityCondition, virusPPM, contaminantPPM));

        nextReportId++;

        return true;
    }
}
