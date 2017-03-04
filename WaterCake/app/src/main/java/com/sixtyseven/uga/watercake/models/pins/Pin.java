package com.sixtyseven.uga.watercake.models.pins;

/**
 * Created by Thor on 2017-03-01.
 */

public class Pin {
    private String label;
    private Location location;

    public Pin(String label, Location location) {
        this.label = label;
        this.location = location;
    }

    /**
     * Getter for the label of the pin
     *
     * @return the label for the pin
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label for the pin
     *
     * @param label the new label for the pin
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for the location
     *
     * @return the location of the pin
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the pin
     *
     * @param location the new label for the pin
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
