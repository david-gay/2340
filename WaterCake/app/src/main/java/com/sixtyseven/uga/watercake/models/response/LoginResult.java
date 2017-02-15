package com.sixtyseven.uga.watercake.models.response;

/**
 * Created by Dimitar on 2/13/2017.
 */

public enum LoginResult {
    SUCCESS("Login successful"),
    USER_DOES_NOT_EXIST("User does not exist"),
    WRONG_PASSWORD("Wrong password");

    private String message;

    LoginResult(String message) {
        this.message = message;
    }

    /**
     * Gets a friendly status message for this result
     * @return a friendly status message
     */
    public String getMessage() {
        return message;
    }
}
