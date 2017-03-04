package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;

public interface WaterPurityReport extends WaterSourceReport {
    /**
     * Returns the WaterPurityCondition, if isWaterPurityReport() is true.
     * @return the WaterPurityCondition
     */
    WaterPurityCondition getWaterPurityCondition();

    /**
     * Returns the virus PPM, if isWaterPurityReport() is true.
     * @return the virus PPM
     */
    float getVirusPPM();

    /**
     * Returns the virus PPM, if isWaterPurityReport() is true.
     * @return the virus PPM
     */
    float getContaminantPPM();
}
