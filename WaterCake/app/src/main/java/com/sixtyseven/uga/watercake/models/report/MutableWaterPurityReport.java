package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.pins.Location;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

interface MutableWaterPurityReport extends WaterPurityReport {
    /**
     * Sets the Location
     * @param location the new location
     */
    void setLocation(Location location);

    /**
     * Sets the WaterType
     * @param waterType the new WaterType
     */
    void setWaterType(WaterType waterType);

    /**
     * Sets the WaterCondition
     * @param condition the new WaterCondition
     */
    void setCondition(WaterCondition condition);

    /**
     * Sets the WaterPurityCondition
     * @param condition the new WaterPurityCondition
     */
    void setWaterPurityCondition(WaterPurityCondition condition);

    /**
     * Sets the virus parts per million
     * @param ppm the new virus parts per million
     */
    void setVirusPPM(float ppm);

    /**
     * Set the contaminant parts per million
     * @param ppm the new contaminant parts per million
     */
    void setContaminantPPM(float ppm);
}
