package com.sixtyseven.uga.watercake;

import com.sixtyseven.uga.watercake.models.dataManagement.VolleyRequestBuilder;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for the @see{VolleyRequestBuilder} class
 */
public class VolleyRequestBuilderUnitTest {
    @Test
    public void assertBuildable_methodNotProvided() {
        try {
            new VolleyRequestBuilder().assertBuildable();
            fail("Exception not thrown.");
        } catch (Exception ex) {
            assertEquals(ex.getLocalizedMessage(),
                    "VolleyRequestBuilder needs an HTTPMethod. Use .withHttpMethod(HTTPMethod method)");
        }
    }

    @Test
    public void assertBuildable_urlNotProvided(){
        try {
            new VolleyRequestBuilder()
                    .withHttpMethod(VolleyRequestBuilder.HTTPMethod.GET)
                    .assertBuildable();
            fail("Exception not thrown.");
        } catch (Exception ex) {
            assertEquals(ex.getLocalizedMessage(),
                    "VolleyRequestBuilder needs an URL. Use .withUrl(String url)");
        }
    }

    @Test
    public void assertBuildable_callbackNotProvided() {
        try {
            new VolleyRequestBuilder()
                    .withHttpMethod(VolleyRequestBuilder.HTTPMethod.GET)
                    .withUrl("http://10.0.2.2:8080/accounts/")
                    .assertBuildable();
            fail("Exception not thrown.");
        } catch (Exception ex) {
            assertEquals(ex.getLocalizedMessage(),
                    "VolleyRequestBuilder needs a requestCallback. Use .withCallback(final VolleyRequestCallback requestCallback) OR .withStatusCodeCallback(final VolleyResponseStatusCodeCallback statusCodeCallback)");
        }
    }

    @Test
    public <T> void assertBuildable_bothCallbacksProvided() {
        try {
            new VolleyRequestBuilder<T>()
                    .withHttpMethod(VolleyRequestBuilder.HTTPMethod.GET)
                    .withUrl("http://10.0.2.2:8080/accounts/")
                    .withCallback(new VolleyRequestBuilder.VolleyRequestCallback<T>() {
                        @Override
                        public void onSuccess(Object response) {

                        }

                        @Override
                        public void onFailure(Integer httpStatusCode, String errorMessage) {

                        }
                    })
                    .withStatusCodeCallback(Arrays.asList(204, 401, 404), new VolleyRequestBuilder.VolleyResponseStatusCodeCallback() {

                        @Override
                        public void onExpectedStatusCode(Integer expectedStatusCode) {

                        }

                        @Override
                        public void onUnexpectedStatusCode(Integer unexpectedStatusCode,
                                String errorMessage) {

                        }
                    })
                    .assertBuildable();
            fail("Exception not thrown.");
        } catch (Exception ex) {
            assertEquals(ex.getLocalizedMessage(),
                    "VolleyRequestBuilder needs EXACTLY ONE callback. You have two.");
        }
    }

    @Test
    public void assertBuildable_bodyNotProvidedForPostOrPut() {
        try {
            new VolleyRequestBuilder()
                    .withHttpMethod(VolleyRequestBuilder.HTTPMethod.POST)
                    .withUrl("http://10.0.2.2:8080/accounts/")
                    .withStatusCodeCallback(Arrays.asList(204, 401, 404), new VolleyRequestBuilder.VolleyResponseStatusCodeCallback() {

                        @Override
                        public void onExpectedStatusCode(Integer expectedStatusCode) {

                        }

                        @Override
                        public void onUnexpectedStatusCode(Integer unexpectedStatusCode,
                                String errorMessage) {

                        }
                    })
                    .assertBuildable();
            fail("Exception not thrown.");
        } catch (Exception ex) {
            assertEquals(ex.getLocalizedMessage(),
                    "VolleyRequestBuilder needs a body for " + VolleyRequestBuilder.HTTPMethod.POST.value
                            + ". Use either .withJsonStringBody(String body) or .withObjectBody(Object body)");
        }
    }

    @Test
    public void assertBuildable_twoBodiesProvidedForPostOrPut() {
        try {
            new VolleyRequestBuilder()
                    .withHttpMethod(VolleyRequestBuilder.HTTPMethod.POST)
                    .withUrl("http://10.0.2.2:8080/accounts/")
                    .withStatusCodeCallback(Arrays.asList(204, 401, 404), new VolleyRequestBuilder.VolleyResponseStatusCodeCallback() {

                        @Override
                        public void onExpectedStatusCode(Integer expectedStatusCode) {

                        }

                        @Override
                        public void onUnexpectedStatusCode(Integer unexpectedStatusCode,
                                String errorMessage) {

                        }
                    })
                    .withJsonStringBody("{\"test\":\"test\"}")
                    .withObjectBody("test")
                    .assertBuildable();
            fail("Exception not thrown.");
        } catch (Exception ex) {
            assertEquals(ex.getLocalizedMessage(),
                    "VolleyRequestBuilder needs a exactly one body for " + VolleyRequestBuilder.HTTPMethod.POST.value
                            + ". Use EITHER .withJsonStringBody(String body) OR .withObjectBody(Object body), but not both.");
        }
    }
}
