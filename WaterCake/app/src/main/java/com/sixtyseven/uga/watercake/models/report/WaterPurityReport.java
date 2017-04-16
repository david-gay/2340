package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;

import java.util.Date;

public interface WaterPurityReport {
    /**
     * Returns the report number
     * @return the report number
     */
    int getReportNumber();

    /**
     * Returns the author's username
     * @return the author's username
     */
    String getAuthorUsername();

    /**
     * Returns a DateTime object with the creation date and time
     * @return a DateTime object with the creation date and time
     */
    Date getPostDate();

    /**
     * Returns the latitude
     * @return the latitude
     */
    double getLatitude();

    /**
     * Returns the longitude
     * @return the longitude
     */
    double getLongitude();

    /**
     * Returns the WaterPurityCondition, if isWaterPurityReport() is true.
     * @return the WaterPurityCondition
     */
    WaterPurityCondition getWaterPurityCondition();

    /**
     * Returns the virus PPM, if isWaterPurityReport() is true.
     * @return the virus PPM
     */
    float getVirusPpm();

    /**
     * Returns the virus PPM, if isWaterPurityReport() is true.
     * @return the virus PPM
     */
    float getContaminantPpm();
}
