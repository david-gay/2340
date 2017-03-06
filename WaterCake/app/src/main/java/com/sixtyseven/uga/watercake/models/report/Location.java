package com.sixtyseven.uga.watercake.models.report;

import java.text.DecimalFormat;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

}
