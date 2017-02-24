package com.sixtyseven.uga.watercake.models.user;

import android.util.Log;

import com.sixtyseven.uga.watercake.models.userprofile.UserProfileField;

import java.util.Map;

/**
 * Core user class for representing any user with privileges determined by UserType
 */
public class User {
    private final String name;
    private String password;
    //defaults for when the user is created - can be updated through the profile changing option
    private String email = "";
    private String address = "";
    private String title = "";

    private UserType userType;

    /**
     * Factory method for creating a User with the fields in fieldsMap populated appropriately.
     * @param fieldsMap the map of UserProfileFields and their associated values to use
     * @return the new User object
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
        if (fieldsMap.containsKey(UserProfileField.USER_TYPE)) {
            setUserType(UserType.valueOf(fieldsMap.get(UserProfileField.USER_TYPE)));
        }
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
     * Getter for the email address
     * @return the email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for the email address
     * @param email the email address
     */
    public void setEmail(String email) {
        Log.d("profile change", name + "'s email was changed to " + email);
        this.email = email;
    }

    /**
     * Getter for the home address
     * @return the home address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Setter for the home address
     * @param address the home address
     */
    public void setAddress(String address) {
        Log.d("profile change", name + "'s address was changed to " + address);
        this.address = address;
    }

    /**
     * Getter for the title
     * @return the home address
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
