package com.sixtyseven.uga.watercake.models;

import android.content.Context;
import android.util.Log;

import com.sixtyseven.uga.watercake.models.dataManagement.RestManager;
import com.sixtyseven.uga.watercake.models.user.User;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;

import java.util.EnumSet;
import java.util.Map;

/**
 * Singleton User login and registration manager. Also handles validation and managing the current
 * user.
 */
public class UserSession {
    private static UserSession ourInstance;
    private static Context context;
    private User currentUser;

    /**
     * Returns the current UserSession
     * @return the current UserSession
     */
    public static UserSession currentSession(Context context) {
        if (ourInstance == null) {
            ourInstance = new UserSession(context);
        }
        return ourInstance;
    }

    /**
     * Default constructor; should normally only be called once at startup.
     */
    private UserSession(Context context) {
        UserSession.context = context;
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
     */
    public void tryLogin(String username, String password, final LoginCallback loginCallback) {
        username = username.toLowerCase();
        final String finalUsername = username;
        RestManager.getInstance(context).validateUser(username, password,
                new RestManager.Callback<Integer>() {
                    @Override
                    public void onSuccess(Integer response) {
                        Log.d("UserSession", "response code: " + response);
                        switch (response) {
                            case 204:
                                RestManager.getInstance(context).getUser(finalUsername,
                                        new UserCallback() {
                                            @Override
                                            public void onSuccess(User user) {
                                                currentUser = user;
                                                loginCallback.onSuccess();
                                            }

                                            @Override
                                            public void onFailure(String errorMessage) {
                                                Log.d("UserSession",
                                                        "get user error: " + errorMessage);
                                                loginCallback.onError(errorMessage);
                                            }
                                        });
                                break;
                            case 401:
                                loginCallback.onWrongPassword();
                                break;
                            case 404:
                                loginCallback.onUserNotFound();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("UserSession", "login error: " + errorMessage);
                        loginCallback.onError(errorMessage);
                    }
                });
    }

    /**
     * Logs the current user out.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Attempts to register a user and returns an EnumSet of any registration errors present.
     * @param fieldMap a map of UserProfileFields to their associated data Strings to use for user
     * creation. Must have UserProfileField.USERNAME.
     */
    public void registerUser(Map<UserProfileField, String> fieldMap,
            final RegisterCallback registerCallback) {
        RestManager.getInstance(context).registerUser(User.generateUserFromFieldsMap(fieldMap),
                registerCallback);
    }

    /**
     * Performs validation on all fields present in fieldMap.
     * @param fieldMap a map of UserProfileFields to their associated data Strings
     * @return an EnumSet of every error encountered in validation.
     */
    public EnumSet<UserProfileError> validateUserFields(Map<UserProfileField, String> fieldMap) {

        EnumSet<UserProfileError> results = EnumSet.noneOf(UserProfileError.class);

        if (fieldMap.containsKey(UserProfileField.USERNAME)) {
            String username = fieldMap.get(UserProfileField.USERNAME);
            if (username.equals("")) {
                results.add(UserProfileError.INVALID_USERNAME);
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

    /**
     * Updates the current user based on a map of UserProfileFields to Strings
     * @param fieldMap a map of UserProfileFields to their associated data Strings
     * @return an EnumSet of every error encountered in updating. Empty if update was successful.
     */
    public EnumSet<UserProfileError> updateUserFields(Map<UserProfileField, String> fieldMap) {
        EnumSet<UserProfileError> results = validateUserFields(fieldMap);

        if (results.isEmpty()) {
            currentUser.setFieldsFromFieldsMap(fieldMap);
        }
        return results;
    }

    public interface UserCallback {
        void onSuccess(User user);

        void onFailure(String errorMessage);
    }

    /**
     * A login callback interface
     */
    public interface LoginCallback {
        void onSuccess();

        void onWrongPassword();

        void onUserNotFound();

        void onError(String errorMessage);
    }

    /**
     * A register callback interface
     */
    public interface RegisterCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
