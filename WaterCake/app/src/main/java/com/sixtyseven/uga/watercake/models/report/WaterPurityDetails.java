package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;

/**
 * Purity details that a WaterSourceReportImpl can optionally have. Internally used by
 * WaterSourceReports.
 */
class WaterPurityDetails {
    private WaterPurityCondition condition;
    private float virusPpm;
    private float contaminantPpm;

    /**
     * Constructor taking the condition, virusPPM, and contaminant PPM
     * @param condition the WaterPurityCondition for the water source corresponding to this object
     * @param virusPpm the virusPPM for the water source corresponding to this object
     * @param contaminantPpm the contaminantPPM for the water source corresponding to this object
     */
    WaterPurityDetails(WaterPurityCondition condition, float virusPpm, float contaminantPpm) {
        this.condition = condition;
        this.virusPpm = virusPpm;
        this.contaminantPpm = contaminantPpm;
    }

    /**
     * Returns the WaterPurityCondition
     * @return the WaterPurityCondition
     */
    WaterPurityCondition getCondition() {
        return condition;
    }

    /**
     * Sets the WaterPurityCondition
     * @param condition the new WaterPurityCondition
     */
    void setCondition(WaterPurityCondition condition) {
        this.condition = condition;
    }

    /**
     * Returns the virus parts per million
     * @return the virus parts per million
     */
    float getVirusPpm() {
        return virusPpm;
    }

    /**
     * Sets the virus parts per million
     * @param virusPpm the new virus parts per million
     */
    void setVirusPpm(float virusPpm) {
        this.virusPpm = virusPpm;
    }

    /**
     * Returns the contaminant parts per million
     * @return the contaminant parts per million
     */
    float getContaminantPpm() {
        return contaminantPpm;
    }

    /**
     * Sets the contaminant parts per million
     * @param contaminantPpm the new contaminant parts per million
     */
    void setContaminantPpm(float contaminantPpm) {
        this.contaminantPpm = contaminantPpm;
    }
}
