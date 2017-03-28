package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;

public interface WaterSourceReport {
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
}
