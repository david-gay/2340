package com.sixtyseven.uga.watercake.models.response;

/**
 * Created by Dimitar on 2/13/2017.
 */

public class LoginResponse implements Response {

    public final boolean condition;
    public final String reason;

    public LoginResponse(boolean condition, String reason) {
        this.condition = condition;
        this.reason = reason;
    }

}
