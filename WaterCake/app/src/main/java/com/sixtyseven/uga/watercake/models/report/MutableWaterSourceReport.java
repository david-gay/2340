package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

/**
 *
 */
interface MutableWaterSourceReport extends WaterSourceReport {

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
}
