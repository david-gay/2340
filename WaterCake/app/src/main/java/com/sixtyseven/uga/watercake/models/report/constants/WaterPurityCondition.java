package com.sixtyseven.uga.watercake.models.report.constants;

/**
 * Condition of water for worker filed water purity reports
 */
public enum WaterPurityCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private String friendlyString;

    WaterPurityCondition(String friendlyString) {
        this.friendlyString = friendlyString;
    }

    public String toString() {
        return friendlyString;
    }
}
