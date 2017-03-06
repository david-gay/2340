package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.SourceReportField;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.report.SourceReportError;


import java.util.EnumSet;
import java.util.Map;


public class NewReportActivity extends FragmentActivity {
    ReportPropertiesFragment properties;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_water_source_report);


        properties = (ReportPropertiesFragment) getSupportFragmentManager().findFragmentById(R.id
                .details_fragment);
        ((TextInputLayout) properties.getView().findViewById(R.id
                .latitudeInput)).setHint("Latitude");

        //Sets the final text box in the ReportPropertiesFragment to be flagged for IME_ACTION_DONE
        //and adds a listener.
        TextInputLayout textInputLayout = properties.getBottomTextInputLayout();
        if (textInputLayout != null && textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);

            textInputLayout.getEditText().setOnEditorActionListener(new TextView
                    .OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        confirmEditProfile(null);
                        handled = true;
                    }
                    return handled;
                }
            });
        }


        Map<SourceReportField, String> fieldsMap = ReportManager.getInstance().getCurrentReport()
                .getFieldsMap();

        ((TextView) findViewById(R.id.latitudeInput)).setText("Latitude: " + fieldsMap.get
                (SourceReportField.LATITUDE));
        ((TextView) findViewById(R.id.longitudeInput)).setText("Longitude: " + fieldsMap.get
                (SourceReportField.LONGITUDE));


        //Populate fields from the current user's data
        for (SourceReportField f : fieldsMap.keySet()) {
            properties.setTextFieldContents(f, fieldsMap.get(f));
        }

        Log.d("NewReportActivity", "NewReportActivity created");
    }

    /**
     * Event handler for the confirm button. Validates user info and saves.
     * @param view the button that initiated this event
     */
    public void confirmEditProfile(View view) {
        Map<SourceReportField, String> fieldsMap = properties.getFieldMap();

        EnumSet<SourceReportError> results = ReportManager.getInstance().
                validateReportFields(fieldsMap);

        if (results.isEmpty()) {
            Log.d("NewReportActivity", "New Source Report");
            finish();
        } else {
            Log.d("NewReportActivity", "Report submission failed");
            properties.setErrors(results, true);
        }
    }

    /**
     * Event handler for the cancel button. Sets the current view to be the login screen.
     * @param view the button that initiated this event
     */
    public void cancelEditProfile(View view) {
        Log.d("NewReportActivity", "Canceled");
        finish();
    }
}

