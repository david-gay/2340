package com.sixtyseven.uga.watercake.models;

import android.util.Log;

import com.sixtyseven.uga.watercake.models.response.LoginResult;
import com.sixtyseven.uga.watercake.models.Registration.RegistrationError;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dimitar on 2/11/2017.
 */
public class UserSession {
    private static UserSession ourInstance = new UserSession();

    public static UserSession currentSession() {
        return ourInstance;
    }

    private Map<String, User> users;

    private User currentUser;

    private UserSession() {
        users = new HashMap<>();
        users.put("user", new RegisteredUser("user","pass"));

        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public LoginResult tryLogin(String username, String password) {
        LoginResult result;
        username = username.toLowerCase();
        if (!users.containsKey(username)) {
            result = LoginResult.USER_DOES_NOT_EXIST;
        } else if (!users.get(username).hasPassword(password)) {
            result = LoginResult.WRONG_PASSWORD;
        } else {
            currentUser = users.get(username);
            result = LoginResult.SUCCESS;
        }
        Log.d("login", result.getMessage());
        return result;
    }

    public boolean logout() {
        currentUser = null;
        return true;
    }

    /**
     * Attempts to register a user and returns an EnumSet of all registration errors present.
     *
     * @param username       the username to register
     * @param password       the password to register
     * @param passwordRepeat the repeat password
     * @return an EnumSet of every error encountered in registration. Empty if registration was successful.
     */
    public EnumSet<RegistrationError> registerUser(String username, String password, String passwordRepeat) {
        EnumSet<RegistrationError> results = EnumSet.noneOf(RegistrationError.class);

        if (username.equals("")) {
            results.add(RegistrationError.INVALID_USERNAME);
        }
        if (users.containsKey(username.toLowerCase())) {
            results.add(RegistrationError.USERNAME_TAKEN);
        }
        if (!password.equals(passwordRepeat)) {
            results.add(RegistrationError.PASSWORD_MISMATCH);
        }
        if (password.length() < 6) {
            results.add(RegistrationError.PASSWORD_TOO_SHORT);
        }

        if (results.isEmpty()) { // if we had no problems, then go ahead and register
            users.put(username.toLowerCase(), new RegisteredUser(username,password));
        }

        StringBuilder logOutput = new StringBuilder();
        boolean first = true;
        for (RegistrationError result : results) {
            if (!first) {
                logOutput.append(", ");
            } else {
                first = false;
            }
            logOutput.append(result.getMessage());
        }

        Log.d("register", logOutput.toString());
        return results;
    }


}
