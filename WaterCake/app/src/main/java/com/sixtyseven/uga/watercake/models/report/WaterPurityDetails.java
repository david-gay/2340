package com.sixtyseven.uga.watercake.models.report;

/**
 * Purity details that a WaterSourceReport can optionally have.
 */
class WaterPurityDetails {
    private WaterPurityCondition condition;
    private float virusPPM;
    private float contaminantPPM;

    WaterPurityDetails(WaterPurityCondition condition, float virusPPM, float contaminantPPM) {
        this.condition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    WaterPurityCondition getCondition() {
        return condition;
    }

    float getVirusPPM() {
        return virusPPM;
    }

    float getContaminantPPM() {
        return contaminantPPM;
    }
}
