package com.sixtyseven.uga.watercake.models.report;

/**
 * Purity details that a WaterSourceReport can optionally have. Internally used by
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
     * Returns the virus parts per million
     * @return the virus parts per million
     */
    float getVirusPPM() {
        return virusPPM;
    }

    /**
     * Returns the contaminant parts per million
     * @return the contaminant parts per million
     */
    float getContaminantPPM() {
        return contaminantPPM;
    }
}
