package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.pins.Location;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;

/**
 * The concrete implementation for all WaterSource reports containing all of the data for a single
 * water source.
 */
class WaterSourceReportImpl implements MutableWaterSourceReport, MutableWaterPurityReport {
    private final int reportNumber;
    private final String authorUsername;
    private final Date dataAndTime;
    private Location location;
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
            Location location, WaterType waterType, WaterCondition condition) {
        this(reportNumber, authorUsername, dataAndTime, location, waterType, condition, null);
    }

    /**
     * Constructor that has water purity details
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityCondition the overall purity of the water
     * @param virusPPM the virus parts per million
     * @param contaminantPPM the contaminant parts per million
     */
    WaterSourceReportImpl(int reportNumber, String authorUsername, Date dataAndTime,
            Location location, WaterType waterType, WaterCondition condition,
            WaterPurityCondition waterPurityCondition, float virusPPM, float contaminantPPM) {
        this(reportNumber, authorUsername, dataAndTime, location, waterType, condition,
                new WaterPurityDetails(waterPurityCondition, virusPPM, contaminantPPM));
    }

    /**
     * Constructor for a WaterSourceReportImpl that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityDetails the WaterPurityDetails that contains waterPurityCondition, virusPPM
     * and contaminantPPM
     */
    private WaterSourceReportImpl(int reportNumber, String authorUsername, Date dataAndTime,
            Location location, WaterType waterType, WaterCondition condition,
            WaterPurityDetails waterPurityDetails) {
        this.reportNumber = reportNumber;
        this.authorUsername = authorUsername;
        this.dataAndTime = dataAndTime;
        this.location = location;
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
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public WaterType getWaterType() {
        return waterType;
    }

    @Override
    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    @Override
    public WaterCondition getCondition() {
        return condition;
    }

    @Override
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

    @Override
    public void setWaterPurityCondition(WaterPurityCondition condition) {
        if (isWaterPurityReport()) {
            waterPurityDetails.setCondition(condition);
        }
        throw new UnsupportedOperationException("getWaterPurityCondition() is unsupported when "
                + "isWaterPurityReport() is false.");
    }

    @Override
    public float getVirusPPM() {
        if (isWaterPurityReport()) {
            return waterPurityDetails.getVirusPPM();
        }
        throw new UnsupportedOperationException(
                "getVirusPPM() is unsupported when " + "isWaterPurityReport() is false.");
    }

    @Override

    public void setVirusPPM(float ppm) {
        if (isWaterPurityReport()) {
            waterPurityDetails.setVirusPPM(ppm);
        }
        throw new UnsupportedOperationException(
                "setVirusPPM() is unsupported when " + "isWaterPurityReport() is false.");
    }

    @Override
    public float getContaminantPPM() {
        if (isWaterPurityReport()) {
            return waterPurityDetails.getContaminantPPM();
        }
        throw new UnsupportedOperationException(
                "getContaminantPPM() is unsupported when " + "isWaterPurityReport() is false.");
    }

    @Override
    public void setContaminantPPM(float ppm) {
        if (isWaterPurityReport()) {
            waterPurityDetails.setContaminantPPM(ppm);
        }
        throw new UnsupportedOperationException(
                "setContaminantPPM() is unsupported when " + "isWaterPurityReport() is false.");
    }
}
