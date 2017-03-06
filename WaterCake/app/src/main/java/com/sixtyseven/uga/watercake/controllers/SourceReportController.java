package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.report.SourceReportError;
import com.sixtyseven.uga.watercake.models.report.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.WaterType;
import com.sixtyseven.uga.watercake.models.user.UserType;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.SourceReportField;
import com.sixtyseven.uga.watercake.models.report.Location;

import java.util.EnumSet;
import java.util.Map;



public class SourceReportController extends FragmentActivity {
    ReportPropertiesFragment properties;

    private EditText latitudeInput;
    private EditText longitudeInput;
    Spinner waterTypeInput;
    Spinner waterConditionInput;
    private Location location;
    private WaterType waterType;
    private WaterCondition waterCondition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_water_source_report);

        properties = (ReportPropertiesFragment) getSupportFragmentManager().findFragmentById(R.id
                .details_fragment);

        latitudeInput = (EditText) findViewById(R.id.latitudeInput);
        longitudeInput = (EditText) findViewById(R.id.longitudeInput);
        waterTypeInput = (Spinner) findViewById(R.id.waterTypeSpinner);
        waterConditionInput = (Spinner) findViewById(R.id.waterConditionSpinner);

        ArrayAdapter<WaterType> typeAdapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, WaterType.values());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeInput.setAdapter(typeAdapter);

        ArrayAdapter<WaterCondition> conditionAdatpter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, WaterCondition.values());
        conditionAdatpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionInput.setAdapter(conditionAdatpter);

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
                        attemptNewSourceReport(null);
                        handled = true;
                    }
                    return handled;
                }
            });
        }

        Log.d("SourceReportController", "source report controller created");
    }

    /**
     * Event handler for clicking the Accept button. Validates report information; if valid, then
     * creates the report. If not, then updates the Registration view with appropriate error
     * messages.
     * @param view the button that initiated this event
     */
    public void attemptNewSourceReport(View view) {

        Log.d("SourceReportController", "attemptNewSourceReport");


        Map<SourceReportField, String> fieldMap = properties.getFieldMap();

        fieldMap.put(SourceReportField.LATITUDE, latitudeInput.getText().toString());
        fieldMap.put(SourceReportField.WATER_TYPE, ((WaterType) waterTypeInput.getSelectedItem())
                .name());
/*
        EnumSet<SourceReportError> errors = ReportManager.getInstance()
                .createWaterReport(location, waterType, waterCondition);

       if (errors.isEmpty()) { //No errors = success
            Toast.makeText(getBaseContext(), "Report submitted!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Report submission failed!",
                    Toast.LENGTH_SHORT).show();

            boolean focusSet = false;

            // Handle report errors
            for (SourceReportError error : errors) {
                if (error.getField() == SourceReportField.LATITUDE) {
       //             setError(usernameTextLayout, error, !focusSet);
                    focusSet = true;
                }
            }

            properties.setErrors(errors, !focusSet);
        }

    }

    private void setError(TextInputLayout target, UserProfileError error, boolean shouldFocus) {
        Log.d("registration controller", "setting error: " + error.getMessage());
        if (target != null) {
            target.setError(error.getMessage());
            if (shouldFocus) {
                target.getEditText().requestFocus();
            }
        }
        */
    }

    /**
     * Event handler for the cancel button. Sets the current view to be the login screen.
     * @param view the button that initiated this event
     */
    public void cancelRegistration(View view) {
        finish();
        Log.d("registration", "registration canceled");
    }


    /*
    Widgets for binding and getting information
     */

}
