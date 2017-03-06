package com.sixtyseven.uga.watercake.models.reports;

/**
 * Immutable class for storing location
 */
public class Location {
    private double latitude;
    private double longitude;

    /**
     * This constructor creates the location with the specified latitude and longitude
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
     * Gets the latitude
     *
     * @return the latitude of the point
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude
     *
     * @return the longitude of the point
     */
    public double getLongitude() {
        return longitude;
    }
}
