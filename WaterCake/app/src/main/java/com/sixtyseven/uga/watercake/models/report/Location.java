package com.sixtyseven.uga.watercake.models.report;

<<<<<<< HEAD
import java.text.DecimalFormat;

=======
/**
 * Immutable class for storing location
 */
>>>>>>> origin/report-generation
public class Location {
    private double latitude;
    private double longitude;

<<<<<<< HEAD
    public Location(double latitude, double longitude) {
=======
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
>>>>>>> origin/report-generation
        this.latitude = latitude;
        this.longitude = longitude;
    }

<<<<<<< HEAD
    public double getLatitude() {return latitude;}


    public double getLongitude() {return longitude;}


    public String getLatitudeString() {
        return new DecimalFormat("#.####").format(latitude);
    }


    public String getLongitudeString() {
        return new DecimalFormat("#.####").format(longitude);
    }


    public String toString() {
        return getLatitudeString() + ", " + getLongitudeString();
    }

=======
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
>>>>>>> origin/report-generation
}
