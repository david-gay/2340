package com.sixtyseven.uga.watercake.models.user;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Core user class for representing any user with privileges determined by UserType
 */
public class User {
    private final String name;
    private String password;
    //defaults for when the user is created - can be updated through the profile changing option
    private String email = "";
    private String city = "";
    private String title = "";

    private UserType userType = UserType.CONTRIBUTOR;

    /**
     * Factory method for creating a User with the fields in fieldsMap populated appropriately.
     * @param fieldsMap the map of UserProfileFields and their associated values to use
     * @return the new User object. Null if fieldsMap did not contain both UserProfileField.USERNAME
     * and UserProfileField.PASSWORD
     */
    public static User generateUserFromFieldsMap(Map<UserProfileField, String> fieldsMap) {
        User output = null;
        if (fieldsMap.containsKey(UserProfileField.USERNAME) && fieldsMap.containsKey
                (UserProfileField.PASSWORD)) {
            output = new User(fieldsMap.get(UserProfileField.USERNAME), fieldsMap.get
                    (UserProfileField.PASSWORD));

            output.setFieldsFromFieldsMap(fieldsMap);
        }
        return output;
    }

    /**
     * Sets fields in this User to the values in fieldsMap. This will not clear any fields that are
     * already set.
     * @param fieldsMap the map of UserProfileFields and their associated values to use
     */
    public void setFieldsFromFieldsMap(Map<UserProfileField, String>
                                               fieldsMap) {
        if (fieldsMap.containsKey(UserProfileField.PASSWORD)) {
            setPassword(fieldsMap.get(UserProfileField.PASSWORD));
        }
        if (fieldsMap.containsKey(UserProfileField.EMAIL)) {
            setEmail(fieldsMap.get(UserProfileField.EMAIL));
        }
        if (fieldsMap.containsKey(UserProfileField.TITLE)) {
            setTitle(fieldsMap.get(UserProfileField.TITLE));
        }
        if (fieldsMap.containsKey(UserProfileField.CITY)) {
            setCity(fieldsMap.get(UserProfileField.CITY));
        }
        if (fieldsMap.containsKey(UserProfileField.USER_TYPE)) {
            setUserType(UserType.valueOf(fieldsMap.get(UserProfileField.USER_TYPE)));
        }
    }

    /**
     * Gets a UserProfileField to String map of all of the fields of User
     * @return a UserProfileField to String map of all of the fields of User
     */
    public Map<UserProfileField, String> getFieldsMap() {
        Map<UserProfileField, String> fieldsMap = new HashMap<>();
        fieldsMap.put(UserProfileField.USERNAME, name);
        fieldsMap.put(UserProfileField.PASSWORD, password);
        fieldsMap.put(UserProfileField.EMAIL, email);
        fieldsMap.put(UserProfileField.TITLE, title);
        fieldsMap.put(UserProfileField.CITY, city);
        fieldsMap.put(UserProfileField.USER_TYPE, userType.name());

        return fieldsMap;
    }

    /**
     * Constructor
     * @param username the username of the User created at registration
     * @param password the password that the user chooses
     */
    public User(String username, String password) {
        Log.d("user instance", "Created instance of user " + username + " of type " + getUserType
                ());
        this.name = username;
        this.password = password;
    }

    /**
     * Used to check if the parameter password is the same as the user's password
     * @param password the password entered
     * @return whether the password matches the one passed in
     */
    public boolean hasPassword(String password) {
        Log.d("password", "User entered password " + password + ", which " + (password.equals
                (this.password) ?
                "matches" : "does not match") + " the correct password");
        return this.password.equals(password);
    }

    /**
     * Setter for password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the username
     * @return the username
     */
    public String getUsername() {
        return this.name;
    }

    /**
     * Getter for the email city
     * @return the email city
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for the email city
     * @param email the email city
     */
    public void setEmail(String email) {
        Log.d("profile change", name + "'s email was changed to " + email);
        this.email = email;
    }

    /**
     * Getter for the home city
     * @return the home city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setter for the home city
     * @param city the home city
     */
    public void setCity(String city) {
        Log.d("profile change", name + "'s city was changed to " + city);
        this.city = city;
    }

    /**
     * Getter for the title
     * @return the home city
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for the title
     * @param title the person's title
     */
    public void setTitle(String title) {
        Log.d("profile change", name + "'s title was changed to " + title);
        this.title = title;
    }

    /**
     * Gets the User's UserType
     * @return the User's UserType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets the User's UserType
     * @param userType the User's new UserType
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
