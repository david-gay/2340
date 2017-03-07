package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager singleton for all WaterSourceReports.
 */
public class ReportManager {

    private Map<Integer, WaterSourceReport> waterSourceReports;
    private Map<Integer, WaterPurityReport> waterPurityReports;
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
        nextReportId = 1;
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

        waterSourceReports.put(nextReportId,
                new WaterSourceReportImpl(nextReportId, authorUsername, new Date(), latitude,
                        longitude, waterType, condition));

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
    public boolean createPurityReport(String authorUsername, double latitude, double longitude,
            WaterType waterType, WaterCondition condition,
            WaterPurityCondition waterPurityCondition, float virusPPM, float contaminantPPM) {

        WaterSourceReportImpl potentialReport = new WaterSourceReportImpl(nextReportId,
                authorUsername, new Date(), latitude, longitude, waterType, condition,
                waterPurityCondition, virusPPM, contaminantPPM);

        waterSourceReports.put(nextReportId, potentialReport);
        waterPurityReports.put(nextReportId, potentialReport);

        nextReportId++;

        return true;
    }

    /**
     * Returns a list of all WaterSourceReports
     * @return a list of all WaterSourceReports
     */
    public List<WaterSourceReport> getWaterSourceReportList() {
        List<WaterSourceReport> list = new ArrayList<>(waterSourceReports.values());
        Collections.sort(list,new Comparator<WaterSourceReport>() {
            @Override
            public int compare(WaterSourceReport o1, WaterSourceReport o2) {
                return o1.getReportNumber() - o2.getReportNumber();
            }
        });

        return list;
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
