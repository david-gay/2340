package com.sixtyseven.uga.watercake.models.pins;

/**
 * Created by Thor on 2017-03-01.
 */

public class Location {
    private double latitude;
    private double longitude;

    /**
     * Constructor creates the location with the specified
     *
     * @param latitude  The latitude of the point
     * @param longitude The longitude of the point
     */
    public Location(double latitude, double longitude) {
        if (latitude > 85 || latitude < -85) {
            throw new IllegalArgumentException("latitude out of range");
        }
        if (longitude > 180 || longitude < -180) {
            throw new IllegalArgumentException("longitude out of range");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter for the latitude
     *
     * @return the latitude of the point
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter for the longitude
     *
     * @return the longitude of the point
     */
    public double getLongitude() {
        return longitude;
    }
}
