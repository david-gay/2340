package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;

/**
 * The concrete implementation for all WaterSource reports containing all of the data for a single
 * water source.
 */
class WaterSourceReportImpl implements WaterSourceReport, WaterPurityReport {
    private final int reportNumber;
    private final String authorUsername;
    private final Date dataAndTime;
    private double latitude;
    private double longitude;
    private WaterType waterType;
    private WaterCondition condition;
    private WaterPurityDetails waterPurityDetails;

    /**
     * Constructor for a standard WaterSourceReportImpl
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     */
    WaterSourceReportImpl(int reportNumber, String authorUsername, Date dataAndTime,
            double latitude, double longitude, WaterType waterType, WaterCondition condition) {
        this(reportNumber, authorUsername, dataAndTime, latitude, longitude, waterType, condition,
                null);
    }

    /**
     * Constructor that has water purity details
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityCondition the overall purity of the water
     * @param virusPpm the virus parts per million
     * @param contaminantPpm the contaminant parts per million
     */
    WaterSourceReportImpl(int reportNumber, String authorUsername, Date dataAndTime,
            double latitude, double longitude, WaterType waterType, WaterCondition condition,
            WaterPurityCondition waterPurityCondition, float virusPpm, float contaminantPpm) {
        this(reportNumber, authorUsername, dataAndTime, latitude, longitude, waterType, condition,
                new WaterPurityDetails(waterPurityCondition, virusPpm, contaminantPpm));
    }

    /**
     * Constructor for a WaterSourceReportImpl that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityDetails the WaterPurityDetails that contains waterPurityCondition, virusPpm
     * and contaminantPpm
     */
    private WaterSourceReportImpl(int reportNumber, String authorUsername, Date dataAndTime,
            double latitude, double longitude, WaterType waterType, WaterCondition condition,
            WaterPurityDetails waterPurityDetails) {
        this.reportNumber = reportNumber;
        this.authorUsername = authorUsername;
        this.dataAndTime = dataAndTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterType = waterType;
        this.condition = condition;
        this.waterPurityDetails = waterPurityDetails;
    }

    /**
     * Returns true if this WaterSourceReportImpl is a water purity report
     * @return true if this WaterSourceReportImpl is a water purity report
     */
    public boolean isWaterPurityReport() {
        return waterPurityDetails != null;
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

    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    @Override
    public WaterCondition getCondition() {
        return condition;
    }

    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }

    @Override
    public WaterPurityCondition getWaterPurityCondition() {
        if (isWaterPurityReport()) {
            return waterPurityDetails.getCondition();
        }
        throw new UnsupportedOperationException("getWaterPurityCondition() is unsupported when "
                + "isWaterPurityReport() is false.");
    }

    public void setWaterPurityCondition(WaterPurityCondition condition) {
        if (isWaterPurityReport()) {
            waterPurityDetails.setCondition(condition);
        }
        throw new UnsupportedOperationException("getWaterPurityCondition() is unsupported when "
                + "isWaterPurityReport() is false.");
    }

    @Override
    public float getVirusPpm() {
        if (isWaterPurityReport()) {
            return waterPurityDetails.getVirusPpm();
        }
        throw new UnsupportedOperationException(
                "getVirusPpm() is unsupported when " + "isWaterPurityReport() is false.");
    }

    public void setVirusPpm(float Ppm) {
        if (isWaterPurityReport()) {
            waterPurityDetails.setVirusPpm(Ppm);
        }
        throw new UnsupportedOperationException(
                "setVirusPpm() is unsupported when " + "isWaterPurityReport() is false.");
    }

    @Override
    public float getContaminantPpm() {
        if (isWaterPurityReport()) {
            return waterPurityDetails.getContaminantPpm();
        }
        throw new UnsupportedOperationException(
                "getContaminantPpm() is unsupported when " + "isWaterPurityReport() is false.");
    }

    public void setContaminantPpm(float Ppm) {
        if (isWaterPurityReport()) {
            waterPurityDetails.setContaminantPpm(Ppm);
        }
        throw new UnsupportedOperationException(
                "setContaminantPpm() is unsupported when " + "isWaterPurityReport() is false.");
    }
}
