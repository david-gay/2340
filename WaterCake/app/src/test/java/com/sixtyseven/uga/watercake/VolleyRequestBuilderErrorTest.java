package com.sixtyseven.uga.watercake;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.sixtyseven.uga.watercake.models.dataManagement.VolleyRequestBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import com.android.volley.VolleyError;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for getErrorMessage in VolleyRequestBuilder
 */

public class VolleyRequestBuilderErrorTest {
    String errorMessage;
    VolleyRequestBuilder volleyRequestBuilder;
    VolleyError volleyError;

    @Before
    public void setup() {
        this.errorMessage = null;
        volleyRequestBuilder = new VolleyRequestBuilder();
    }

    //Test for errorMessage != null
    @Test
    public void getErrorMessage_getMessageNotNull() {
        volleyError = new VolleyError("testing");
        assertEquals("testing", volleyRequestBuilder.getErrorMessage(volleyError));
    }

    //Test for NetworkError
    @Test
    public void getErrorMessage_networkError() {
        volleyError = new NetworkError();
        assertEquals("Cannot connect to Internet...Please check your connection!",
                volleyRequestBuilder.getErrorMessage(volleyError));
    }

    //Test ServerError
    @Test
    public void getErrorMessage_serverError() {
        volleyError = new ServerError();
        assertEquals("The server could not be found. Please try again after some time!!",
                volleyRequestBuilder.getErrorMessage(volleyError));
    }

    //Test AuthFailureError
    @Test
    public void getErrorMessage_authFailureMessage() {
        volleyError = new AuthFailureError();
        assertEquals("Cannot connect to Internet...Please check your connection!",
                volleyRequestBuilder.getErrorMessage(volleyError));
    }

    //Test ParseError
    @Test
    public void getErrorMessage_parseError() {
        volleyError = new ParseError();
        assertEquals("Parsing error! Please try again after some time!!",
                volleyRequestBuilder.getErrorMessage(volleyError));
    }

    //Test NoConnectionError
    @Test
    public void getErrorMessage_noConnectionError() {
        volleyError = new NoConnectionError();
        assertEquals("Cannot connect to Internet...Please check your connection!",
                volleyRequestBuilder.getErrorMessage(volleyError));
    }

    //Test TimeoutError
    @Test
    public void getErrorMessage_timeoutError() {
        volleyError = new TimeoutError();
        assertEquals("Connection TimeOut! Please check your internet connection.",
                volleyRequestBuilder.getErrorMessage(volleyError));
    }
}
