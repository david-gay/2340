package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;

import java.util.Date;

/**
 * The concrete implementation for all WaterPurityReports containing all of the data for a single
 * water source.
 */
class WaterPurityReportImpl implements WaterPurityReport {
    private final int reportNumber;
    private final String authorUsername;
    private final Date postDate;
    private double latitude;
    private double longitude;
    private WaterPurityCondition waterPurityCondition;
    private float virusPpm;
    private float contaminantPpm;

    /**
     * /**
     * Constructor for a WaterSourceReportImpl that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param postDate the time and date that the report was submitted and contaminantPpm
     * @param latitude the latitude
     * @param longitude the longitude
     * @param waterPurityCondition the purity condition of the water
     * @param virusPpm virus parts per million
     * @param contaminantPpm contaminants parts per million
     */
    WaterPurityReportImpl(int reportNumber, String authorUsername, Date postDate, double latitude,
            double longitude, WaterPurityCondition waterPurityCondition, float virusPpm,
            float contaminantPpm) {
        this.reportNumber = reportNumber;
        this.authorUsername = authorUsername;
        this.postDate = postDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterPurityCondition = waterPurityCondition;
        this.virusPpm = virusPpm;
        this.contaminantPpm = contaminantPpm;
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
    public WaterPurityCondition getWaterPurityCondition() {
        return waterPurityCondition;
    }

    @Override
    public float getVirusPpm() {
        return virusPpm;
    }

    @Override
    public float getContaminantPpm() {
        return contaminantPpm;
    }
}
