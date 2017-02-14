package com.sixtyseven.uga.watercake.models.response;

/**
 * Created by Dimitar on 2/13/2017.
 */

public class RegisterResponse extends LoginResponse {
    public RegisterResponse(boolean condition, String reason) {
        super(condition, reason);
    }
}
