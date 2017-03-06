package com.sixtyseven.uga.watercake.models.report;

import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Report containing all of the data for a single water source.
 */
public class WaterSourceReport {
    private final int reportNumber;
    private String authorUsername;
    private DateTime dateAndTime;
    private WaterType waterType;
    private WaterCondition condition;
    private Location location;
    private String latitudeInput;
    private String longitudeInput;
    private WaterPurityDetails waterPurityDetails;
    private String waterTypeString;
    private String conditionString;


    public Map<SourceReportField, String> getFieldsMap() {
        Map<SourceReportField, String> fieldsMap = new HashMap<>();
        fieldsMap.put(SourceReportField.LATITUDE, latitudeInput);
        fieldsMap.put(SourceReportField.LONGITUDE, longitudeInput);

        double latitude = Double.parseDouble(latitudeInput);
        double longitude = Double.parseDouble(longitudeInput);
        location = new Location(latitude, longitude);

        fieldsMap.put(SourceReportField.LOCATION, location.toString());
        fieldsMap.put(SourceReportField.WATER_TYPE, waterTypeString);
        fieldsMap.put(SourceReportField.WATER_CONDITION, conditionString);

        return fieldsMap;
    }
    /**
     * Constructor for a standard WaterSourceReport
     * @param reportNumber the reportNumber
     * @param location location of water source
     * @param waterType the type of water
     * @param condition the condition of the water
     */
    WaterSourceReport(int reportNumber, Location location, WaterType waterType, WaterCondition
            condition) {
        this(reportNumber, location, waterType, condition, null);
    }

    /**
     * Constructor for a WaterSourceReport that has water purity details
     * @param reportNumber the reportNumber
     * @param location location of water source
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityCondition the overall purity of the water
     * @param virusPPM the virus parts per million
     * @param contaminantPPM the contaminant parts per million
     */
    WaterSourceReport(int reportNumber, Location location, WaterType waterType, WaterCondition
            condition, WaterPurityCondition waterPurityCondition, float virusPPM, float
            contaminantPPM) {
        this(reportNumber, location, waterType, condition,
                new WaterPurityDetails(waterPurityCondition, virusPPM, contaminantPPM));
    }

    /**
     * Constructor for a WaterSourceReport that takes in a WaterPurityDetails.
     * @param reportNumber the reportNumber
     * @param location location of water source
     * @param waterType the type of water
     * @param condition the condition of the water
     * @param waterPurityDetails the WaterPurityDetails that contains waterPurityCondition, virusPPM
     * and contaminantPPM
     */
    private WaterSourceReport(int reportNumber, Location location, WaterType
            waterType, WaterCondition condition, WaterPurityDetails waterPurityDetails) {
        this.reportNumber = reportNumber;
        this.authorUsername = UserSession.currentSession().getCurrentUser().getUsername();
        this.dateAndTime = dateAndTime.now();
        this.location = location;
        this.waterType = waterType;
        this.condition = condition;
        this.waterPurityDetails = waterPurityDetails;
    }

    /**
     * Returns true if this WaterSourceReport has water purity data
     * @return true if this WaterSourceReport has water purity data
     */
    public boolean isWaterPurityReport() {
        return waterPurityDetails != null;
    }

    /**
     * Returns the report number
     * @return the report number
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Returns the author's username
     * @return the author's username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Returns a DateTime object with the creation date and time
     * @return a DateTime object with the creation date and time
     */
    public DateTime getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Returns a Location object with latitude and longitude of the water source
     * @return a Location object with latitude and longitude of the water source
     */
    public Location getLocation() {return location;}

    /**
     * Returns the water type
     * @return the water type
     */
    public WaterType getWaterType() {
        return waterType;
    }

    /**
     * Returns the water condition
     * @return the water condition
     */
    public WaterCondition getCondition() {
        return condition;
    }
}
