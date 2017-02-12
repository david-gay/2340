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

    public void tryLogin(String username, String password) {
        if (!users.containsKey(username)) {
            Log.d("login", "unknown username");
            return;
        }
        if (users.get(username) != password) {
            Log.d("login", "wrong password");
            return;
        }

        currentUser = username;
        Log.d("login", "wrong successful");
    }

    public void logout() {
        currentUser = null;
    }

    public void registerUser(String username, String password, String passwordRepeat) {
        if (users.containsKey(username)) {
            Log.d("register", "username taken");
            return;
        }
        if (password != passwordRepeat) {
            Log.d("register", "passwords don't match");
            return;
        }
        if (password.length() < 6) {
            Log.d("register", "passwords too short");
            return;
        }

        users.put(username, password);
        Log.d("register", "registration successful");
    }


}
