package com.sixtyseven.uga.watercake.models;

import android.util.Log;

import com.sixtyseven.uga.watercake.models.response.LoginResponse;
import com.sixtyseven.uga.watercake.models.response.RegisterResponse;
import com.sixtyseven.uga.watercake.models.response.Response;

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


    public LoginResponse tryLogin(String username, String password) {
        if (!users.containsKey(username)) {
            Log.d("login", "User does not exist");
            return new LoginResponse(false, "User does not exist");
        }
        if (!users.get(username).equals(password)) {
            Log.d("login", "wrong password");
            return new LoginResponse(false, "Wrong password");
        }

        currentUser = username;
        Log.d("login", "login successful");
        return new LoginResponse(true, "Login successful!");
    }

    public boolean logout() {
        currentUser = null;
        return true;
    }

    public RegisterResponse registerUser(String username, String password, String passwordRepeat) {
        if (users.containsKey(username)) {
            Log.d("register", "username taken");
            return new RegisterResponse(false, "Username already exists");
        }
        if (!password.equals(passwordRepeat)) {
            Log.d("register", "passwords don't match");
            return new RegisterResponse(false, "Passwords don't match");
        }
        if (password.length() < 6) {
            Log.d("register", "passwords too short");
            return new RegisterResponse(false, "Passwords too short");
        }

        users.put(username, password);
        Log.d("register", "registration successful");
        return new RegisterResponse(true, "Registration successful");
    }


}
