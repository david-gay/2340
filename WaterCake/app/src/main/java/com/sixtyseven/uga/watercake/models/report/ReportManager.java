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

    private Map<Integer, WaterSourceReportImpl> reports;
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
     * Generates a WaterSourceReportImpl and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createWaterReport(String authorUsername, WaterType waterType, WaterCondition
            condition) {
        //if validation passes {
        reports.put(nextReportId, new WaterSourceReportImpl(nextReportId, authorUsername, new Date(),
                waterType, condition));

        nextReportId++;

        return true;
    }


    /**
     * Generates a WaterSourceReportImpl and stores it
     * @param authorUsername the username of the report author
     * @param waterType the type of water reported
     * @param condition the condition of that water
     * @return true if the report is created and added
     */
    public boolean createPurityReport(String authorUsername, WaterType waterType, WaterCondition
            condition, WaterPurityCondition waterPurityCondition, float virusPPM, float
                                              contaminantPPM) {

        WaterSourceReportImpl potentialReport = new WaterSourceReportImpl(nextReportId, authorUsername, new Date(),
                waterType, condition, waterPurityCondition, virusPPM, contaminantPPM);



        reports.put(nextReportId, potentialReport);

        nextReportId++;

        return true;
    }

    /**
     * Returns the WaterSourceReportImpl for the given id.
     * @param id the id of the WaterSourceReportImpl
     * @return the WaterSourceReportImpl; null if no such report exists
     */
    public WaterSourceReportImpl getReportById(int id) {
        if (!reports.containsKey(id)) {
            return null;
        }
        return reports.get(id);
    }

    private boolean isReportValid(WaterSourceReportImpl report) {
        return true;
    }
}
