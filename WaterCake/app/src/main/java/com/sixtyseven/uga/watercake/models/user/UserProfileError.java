package com.sixtyseven.uga.watercake.models.user;

/**
 * Enum of every possible registration error. Also gives access to the associated UserProfileField
 * that error applies to.
 */
public enum UserProfileError {
    USERNAME_TAKEN("Username taken", UserProfileField.USERNAME),
    INVALID_USERNAME("Username invalid", UserProfileField.USERNAME),
    PASSWORD_TOO_SHORT("Password too short", UserProfileField.PASSWORD),
    PASSWORD_MISMATCH("Passwords do not match", UserProfileField.REPEAT_PASSWORD);

    private String message;
    private UserProfileField field;

    UserProfileError(String message, UserProfileField field) {
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
    public UserProfileField getField() {
        return field;
    }
}
