package com.sixtyseven.uga.watercake;

import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;
import com.sixtyseven.uga.watercake.models.user.UserType;

import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Tests the validateUserFields method from the UserSession class
 */
public class UserSessionTest {
    private UserSession session;

    @Before
    public void setUp() throws Exception {
        session = UserSession.currentSession(null);
    }

    @Test
    public void testValidatingBlank() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("", "", "");
        assertTrue("All fields being empty should produce a username error!",
                result.contains(UserProfileError.INVALID_USERNAME));
        assertTrue("All fields being empty should produce a password too short error!",
                result.contains(UserProfileError.PASSWORD_TOO_SHORT));
    }

    @Test
    public void testValidatingUsernameError() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("", "password", "password");
        assertTrue("Empty username should produce an Invalid Username error!",
                result.contains(UserProfileError.INVALID_USERNAME));
    }

    @Test
    public void testValidatingPasswordEmpty() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("username", "", "");
        assertTrue("Empty password should produce a password too short error!",
                result.contains(UserProfileError.PASSWORD_TOO_SHORT));
    }

    @Test
    public void testValidatingPasswordJustGood() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("username", "123456", "123456");
        assertTrue("username and 6 char password should work", result.isEmpty());
    }

    @Test
    public void testValidatingPasswordMismatch() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("username", "123456", "12345");
        assertTrue("Password mismatch should produce a password mismatch error!",
                result.contains(UserProfileError.PASSWORD_MISMATCH));
    }

    @Test
    public void testValidatingPasswordMismatchPasswordBlank() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("username", "", "123456");
        assertTrue("Password blank should produce a password too short!",
                result.contains(UserProfileError.PASSWORD_TOO_SHORT));
        assertTrue("Password mismatch should produce a password mismatch error!",
                result.contains(UserProfileError.PASSWORD_MISMATCH));
    }

    @Test
    public void testValidatingPasswordMismatchPasswordRepeatBlank() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("username", "123456", "");
        assertTrue("Password mismatch should produce a password mismatch error!",
                result.contains(UserProfileError.PASSWORD_MISMATCH));
    }

    @Test
    public void testValidatingEverythingGood() throws Exception {
        EnumSet<UserProfileError> result = testWithValues("username", "password", "password");
        assertTrue("all fields filled in with good values should work", result.isEmpty());
    }

    /**
     * Helper method to generate the EnumSet with the given username, password, and password repeat
     * cleaner to read than repeating it in each test case.
     * @param username the username to test with
     * @param password the password to test with
     * @param passwordrepeat the repeat password to test with
     * @return the enumset produced
     */
    private EnumSet<UserProfileError> testWithValues(String username, String password,
            String passwordrepeat) {
        HashMap<UserProfileField, String> fields = new HashMap<>();
        fields.put(UserProfileField.USERNAME, username);
        fields.put(UserProfileField.PASSWORD, password);
        fields.put(UserProfileField.REPEAT_PASSWORD, passwordrepeat);
        //This isn't actually used in validation, but it's actually there when processed,
        //so it is here for accuracy
        fields.put(UserProfileField.USER_TYPE, UserType.CONTRIBUTOR.name());
        return session.validateUserFields(fields);
    }
}