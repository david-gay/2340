package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

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
    Date getDataAndTime();

    /**
     * Returns the water type
     * @return the water type
     */
    WaterType getWaterType();

    /**
     * Returns the water condition
     * @return the water condition
     */
    WaterCondition getCondition();


    double getLatitude();

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
