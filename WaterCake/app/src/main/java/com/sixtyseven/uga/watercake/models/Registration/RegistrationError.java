package com.sixtyseven.uga.watercake.models.registration;

/**
 * Enum of every possible registration error. Also gives access to the associated RegistrationField
 * that error applies to.
 */
public enum RegistrationError {
    USERNAME_TAKEN("Username taken", RegistrationField.USERNAME),
    INVALID_USERNAME("Username invalid", RegistrationField.USERNAME),
    PASSWORD_TOO_SHORT("Password too short", RegistrationField.PASSWORD),
    PASSWORD_MISMATCH("Passwords do not match", RegistrationField.REPEAT_PASSWORD);

    private String message;
    private RegistrationField field;

    RegistrationError(String message, RegistrationField field) {
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
    public RegistrationField getField() {
        return field;
    }
}
