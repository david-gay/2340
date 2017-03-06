package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.pins.Location;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

import java.util.Date;
>>>>>>> origin/report-generation

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

    public DateTime getDateAndTime() {
        return dateAndTime;
    }


    /**
     * Returns a Location object with latitude and longitude of the water source
     * @return a Location object with latitude and longitude of the water source
     */
    public Location getLocation() {return location;}

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
     * Returns the location of this report
     * @return the lcoation of this report
     */
    Location getLocation();
}
