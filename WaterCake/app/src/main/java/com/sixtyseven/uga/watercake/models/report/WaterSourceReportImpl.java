package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;

/**
 * The concrete implementation for all WaterSourceReports containing all of the data for a single
 * water source.
 */
class WaterSourceReportImpl implements WaterSourceReport {
    private final int id;
    private final String authorUsername;
    private final Date postDate;
    private double latitude;
    private double longitude;
    private WaterType waterType;
    private WaterCondition waterCondition;

    /**
     * Constructor for a WaterSourceReportImpl that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param postDate the time and date that the report was submitted
     * @param waterType the type of water
     * @param waterCondition the condition of the water
     */
    WaterSourceReportImpl(int reportNumber, String authorUsername, Date postDate, double latitude,
            double longitude, WaterType waterType, WaterCondition waterCondition) {
        this.id = reportNumber;
        this.authorUsername = authorUsername;
        this.postDate = postDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }

    @Override
    public int getReportNumber() {
        return id;
    }

    @Override
    public String getAuthorUsername() {
        return authorUsername;
    }

    @Override
    public Date getPostDate() {
        return postDate;
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
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }
}
