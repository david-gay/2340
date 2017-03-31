package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;

/**
 * The concrete implementation for all WaterSourceReports containing all of the data for a single
 * water source.
 */
class WaterSourceReportImpl implements WaterSourceReport {
    private final int reportNumber;
    private final String authorUsername;
    private final Date dataAndTime;
    private double latitude;
    private double longitude;
    private WaterType waterType;
    private WaterCondition condition;

    /**
     * Constructor for a WaterSourceReportImpl that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     */
    WaterSourceReportImpl(int reportNumber, String authorUsername, Date dataAndTime,
            double latitude, double longitude, WaterType waterType, WaterCondition condition) {
        this.reportNumber = reportNumber;
        this.authorUsername = authorUsername;
        this.dataAndTime = dataAndTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterType = waterType;
        this.condition = condition;
    }

    @Override
    public int getReportNumber() {
        return reportNumber;
    }

    @Override
    public String getAuthorUsername() {
        return authorUsername;
    }

    @Override
    public Date getDataAndTime() {
        return dataAndTime;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public WaterType getWaterType() {
        return waterType;
    }

    @Override
    public WaterCondition getCondition() {
        return condition;
    }
}
