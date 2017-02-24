package com.sixtyseven.uga.watercake.models;

import android.util.Log;

import com.sixtyseven.uga.watercake.models.response.LoginResult;
import com.sixtyseven.uga.watercake.models.user.User;
import com.sixtyseven.uga.watercake.models.userprofile.UserProfileError;
import com.sixtyseven.uga.watercake.models.userprofile.UserProfileField;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton User login and registration manager. Also handles validation and managing the current
 * user.
 */
public class UserSession {
    private static UserSession ourInstance = new UserSession();

    /**
     * Returns the current UserSession
     * @return the current UserSession
     */
    public static UserSession currentSession() {
        return ourInstance;
    }

    private Map<String, User> users;

    private User currentUser;

    private UserSession() {
        users = new HashMap<>();
        users.put("user", new User("user", "pass"));

        currentUser = null;
    }

    /**
     * Gets the current instance of UserSession
     * @return the current instance of UserSession
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Attempts to log the user in with the supplied username and password.
     * @param username the username to login with
     * @param password the plaintext password to log in with
     * @return LoginResult.SUCCESS if successful; LoginResult corresponding with the problem
     * otherwise.
     */
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
     * Attempts to register a user and returns an EnumSet of any registration errors present.
     * @param fieldMap a map of UserProfileFields to their associated data Strings to use for user
     * creation. Must have UserProfileField.USERNAME.
     * @return an EnumSet of every error encountered in registration. Empty if registration was
     * successful.
     */
    public EnumSet<UserProfileError> registerUser(Map<UserProfileField, String> fieldMap) {
        EnumSet<UserProfileError> results = validateUserFields(fieldMap);

        String username = fieldMap.get(UserProfileField.USERNAME);

        if (results.isEmpty()) { // if we had no problems, then go ahead and register
            users.put(username.toLowerCase(), User.generateUserFromFieldsMap(fieldMap));
        }

        StringBuilder logOutput = new StringBuilder();
        boolean first = true;
        for (UserProfileError result : results) {
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

    /**
     * Performs validation on all fields present in fieldMap.
     * @param fieldMap a map of UserProfileFields to their associated data Strings
     * @return
     */
    public EnumSet<UserProfileError> validateUserFields(Map<UserProfileField, String> fieldMap) {

        EnumSet<UserProfileError> results = EnumSet.noneOf(UserProfileError.class);

        if (fieldMap.containsKey(UserProfileField.USERNAME)) {
            String username = fieldMap.get(UserProfileField.USERNAME);
            if (username.equals("")) {
                results.add(UserProfileError.INVALID_USERNAME);
            }
            if (users.containsKey(username.toLowerCase())) {
                results.add(UserProfileError.USERNAME_TAKEN);
            }
        }

        if (fieldMap.containsKey(UserProfileField.PASSWORD)) {
            String password = fieldMap.get(UserProfileField.PASSWORD);
            if (password.length() < 6) {
                results.add(UserProfileError.PASSWORD_TOO_SHORT);
            }
            if (fieldMap.containsKey(UserProfileField.REPEAT_PASSWORD)) {
                String passwordRepeat = fieldMap.get(UserProfileField.REPEAT_PASSWORD);
                if (!password.equals(passwordRepeat)) {
                    results.add(UserProfileError.PASSWORD_MISMATCH);
                }
            }
        }

        return results;
    }
}
