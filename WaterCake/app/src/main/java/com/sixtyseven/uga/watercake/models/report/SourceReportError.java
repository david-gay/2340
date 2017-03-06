package com.sixtyseven.uga.watercake.models.report;



public enum SourceReportError {
    INVALID_LOCATION("Location invalid", SourceReportField.LOCATION),
    INVALID_WATER_TYPE("Please select water type.", SourceReportField.WATER_TYPE),
    INVALID_WATER_CONDITION("Please select water condition", SourceReportField.WATER_CONDITION);

    private String message;
    private SourceReportField field;

    SourceReportError(String message, SourceReportField field) {
        this.message = message;
        this.field = field;
    }

    /**
     * Gets a friendly status message for this result
     * @return a friendly status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the field that is most associated with this result
     * @return the field most associated with this result
     */
    public SourceReportField getField() {
        return field;
    }
}
