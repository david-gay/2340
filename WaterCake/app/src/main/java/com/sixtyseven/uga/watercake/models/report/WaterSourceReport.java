package com.sixtyseven.uga.watercake.models.report;


import java.util.Date;

/**
 * Report containing all of the data for a single water source.
 */
class WaterSourceReport {
    private final int reportNumber;
    private String authorUsername;
    private Date dataAndTime;
    private WaterType waterType;
    private WaterCondition condition;
    private WaterPurityDetails waterPurityDetails;

    /**
     * Constructor for a standard WaterSourceReport
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     */
    WaterSourceReport(int reportNumber, String authorUsername,
                      Date dataAndTime, WaterType waterType, WaterCondition condition) {
        this(reportNumber, authorUsername, dataAndTime, waterType, condition, null);
    }

    /**
     * Constructor for a WaterSourceReport that has water purity details
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityCondition the overall purity of the water
     * @param virusPPM the virus parts per million
     * @param contaminantPPM the contaminant parts per million
     */
    WaterSourceReport(int reportNumber, String authorUsername,
                      Date dataAndTime, WaterType waterType, WaterCondition condition,
                      WaterPurityCondition waterPurityCondition, float virusPPM, float
                              contaminantPPM) {
        this(reportNumber, authorUsername, dataAndTime, waterType, condition, new
                WaterPurityDetails(waterPurityCondition, virusPPM, contaminantPPM));
    }

    /**
     * Constructor for a WaterSourceReport that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param authorUsername the author's username
     * @param dataAndTime the time and date that the report was submitted
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityDetails the WaterPurityDetails that contains waterPurityCondition, virusPPM
     * and contaminantPPM
     */
    private WaterSourceReport(int reportNumber, String authorUsername, Date dataAndTime,
                              WaterType waterType, WaterCondition condition, WaterPurityDetails
                                      waterPurityDetails) {
        this.reportNumber = reportNumber;
        this.authorUsername = authorUsername;
        this.dataAndTime = dataAndTime;
        this.waterType = waterType;
        this.condition = condition;
        this.waterPurityDetails = waterPurityDetails;
    }

    /**
     * Returns true if this WaterSourceReport has water purity data
     * @return true if this WaterSourceReport has water purity data
     */
    public boolean isWaterPurityReport() {
        return waterPurityDetails != null;
    }

    /**
     * Returns the report number
     * @return the report number
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Returns the author's username
     * @return the author's username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Returns a DateTime object with the creation date and time
     * @return a DateTime object with the creation date and time
     */
    public Date getDataAndTime() {
        return dataAndTime;
    }

    /**
     * Returns the water type
     * @return the water type
     */
    public WaterType getWaterType() {
        return waterType;
    }

    /**
     * Returns the water condition
     * @return the water condition
     */
    public WaterCondition getCondition() {
        return condition;
    }
}
