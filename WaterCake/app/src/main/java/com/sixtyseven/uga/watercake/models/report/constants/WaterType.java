package com.sixtyseven.uga.watercake.models.report.constants;

/**
 * Type of water for WaterSourceReports
 */
public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String friendlyString;

    WaterType(String friendlyString) {
        this.friendlyString = friendlyString;
    }

    public String toString() {
        return friendlyString;
    }
}
