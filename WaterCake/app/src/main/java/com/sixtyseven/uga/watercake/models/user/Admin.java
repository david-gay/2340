package com.sixtyseven.uga.watercake.models.user;

/**
 * Created by Thor on 2017-02-19.
 */

public class Admin extends User {
    /**
     * Constructor does the same as User's
     *
     * @param username the name the user sets
     * @param password the password that the user sets
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Used to get a string of the type of user for display purposes
     *
     * @return the name of the class
     */
    public String getUserType() {
        return "Admin";
    }
}
