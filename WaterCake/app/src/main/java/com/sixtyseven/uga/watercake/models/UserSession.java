package com.sixtyseven.uga.watercake.models;

import android.util.Log;

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

    private Map<String, String> users;

    private String currentUser;

    private UserSession() {
        users = new HashMap<>();
        users.put("user", "pass");

        currentUser = null;
    }

    public String getCurrentUser() {
        return currentUser;
    }


    public boolean tryLogin(String username, String password) {
        if (!users.containsKey(username)) {
            Log.d("login", "unknown username");
            return false;
        }
        if (!users.get(username).equals(password)) {
            Log.d("login", "wrong password");
            return false;
        }

        currentUser = username;
        Log.d("login", "login successful");
        return true;
    }

    public boolean logout() {
        currentUser = null;
        return true;
    }

    public boolean registerUser(String username, String password, String passwordRepeat) {
        if (users.containsKey(username)) {
            Log.d("register", "username taken");
            return false;
        }
        if (!password.equals(passwordRepeat)) {
            Log.d("register", "passwords don't match");
            return false;
        }
        if (password.length() < 6) {
            Log.d("register", "passwords too short");
            return false;
        }

        users.put(username, password);
        Log.d("register", "registration successful");
        return true;
    }


}
