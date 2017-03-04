package com.sixtyseven.uga.watercake.models.pins;

/**
 * Created by Thor on 2017-03-01.
 */

public class Location {
    private int latitude;
    private int longitude;

    /**
     * Constructor creates the location with the specified
     *
     * @param latitude  The latitude of the point
     * @param longitude The longitude of the point
     */
    public Location(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter for the latitude
     *
     * @return the latitude of the point
     */
    public int getLatitude() {
        return latitude;
    }

    /**
     * Getter for the longitude
     *
     * @return the longitude of the point
     */
    public int getLongitude() {
        return longitude;
    }
}
