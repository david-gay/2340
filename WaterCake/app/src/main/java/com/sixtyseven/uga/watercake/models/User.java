package com.sixtyseven.uga.watercake.models;

import android.util.Log;

/**
 * Created by Thor on 2017-02-19.
 */

public abstract class User {
    private String name;
    private String password;

    /**
     * @param username the username of the User created at registration
     * @param password the password that the user chooses
     */
    public User(String username, String password)
    {
        Log.d("register","Registered user "+username+" of type "+getUserType());
        this.name = username;
        this.password = password;
    }

    /**
     * Used to check if the parameter password is the same as the user's password
     * @param password the password entered
     * @return whether the password matches the one passed in
     */
    public boolean hasPassword(String password)
    {
        Log.d("password","User entered password "+password+", which "+(password.equals(this.password)?
            "matches":"does not match")+" the correct password");
        return this.password.equals(password);
    }

    /**
     * Getter for the username
     * @return the username
     */
    public String getUsername()
    {
        return this.name;
    }
    public abstract String getUserType();
}
