package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;

/**
 * Purity details that a WaterSourceReportImpl can optionally have. Internally used by
 * WaterSourceReports.
 */
class WaterPurityDetails {
    private WaterPurityCondition condition;
    private float virusPPM;
    private float contaminantPPM;

    /**
     * Constructor taking the condition, virusPPM, and contaminant PPM
     * @param condition the WaterPurityCondition for the water source corresponding to this object
     * @param virusPPM the virusPPM for the water source corresponding to this object
     * @param contaminantPPM the contaminantPPM for the water source corresponding to this object
     */
    WaterPurityDetails(WaterPurityCondition condition, float virusPPM, float contaminantPPM) {
        this.condition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
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
    float getVirusPPM() {
        return virusPPM;
    }

    /**
     * Sets the virus parts per million
     * @param virusPPM the new virus parts per million
     */
    void setVirusPPM(float virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Returns the contaminant parts per million
     * @return the contaminant parts per million
     */
    float getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Sets the contaminant parts per million
     * @param contaminantPPM the new contaminant parts per million
     */
    void setContaminantPPM(float contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }
}
