package com.sixtyseven.uga.watercake.models.report.constants;

/**
 * Condition of water for normal WaterSourceReports
 */
public enum WaterCondition {
    POTABLE("Potable"),
    TREATABLE_CLEAR("Treatable - Clear"),
    TREATABLE_MUDDY("Treatable - Muddy"),
    WASTE("Waste");

    private String friendlyString;

    WaterCondition(String friendlyString) {
        this.friendlyString = friendlyString;
    }

    public String toString() {
        return friendlyString;
    }
}
