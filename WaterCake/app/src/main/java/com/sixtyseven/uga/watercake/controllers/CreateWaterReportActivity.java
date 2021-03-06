package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

/**
 * Activity for creating Water Reports
 */
public class CreateWaterReportActivity extends AppCompatActivity {

    private TextInputLayout longitudeInput;
    private TextInputLayout latitudeInput;
    private Spinner waterTypeSpinner;
    private Spinner waterConditionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_report);

        longitudeInput = (TextInputLayout) findViewById(R.id.longitudeInput);
        latitudeInput = (TextInputLayout) findViewById(R.id.latitudeInput);

        waterTypeSpinner = (Spinner) findViewById(R.id.waterTypeSpinner);
        ArrayAdapter<WaterType> waterTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterType.values());
        waterTypeArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeArrayAdapter);

        waterConditionSpinner = (Spinner) findViewById(R.id.waterConditionSpinner);
        ArrayAdapter<WaterCondition> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterCondition.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionSpinner.setAdapter(adapter);
    }

    /**
     * Button handler for attempting to submit a report. Attempts to submit a report and updates any
     * fields that may be invalid.
     * @param view the button initiating this method
     */
    public void attemptSubmitReport(View view) {
        ReportManager manager = ReportManager.getInstance(this.getApplicationContext());

        try {
            double longitude = Double.parseDouble(
                    longitudeInput.getEditText().getText().toString());
            double latitude = Double.parseDouble(latitudeInput.getEditText().getText().toString());

            if ((longitude > 180 || longitude < -180) || (latitude > 90 || latitude < -90)) {
                // we can literally only fail because the coords were invalid:
                Toast.makeText(getBaseContext(), "Invalid coordinates!", Toast.LENGTH_SHORT).show();
            } else {
                WaterType type = (WaterType) waterTypeSpinner.getSelectedItem();
                WaterCondition condition = (WaterCondition) waterConditionSpinner.getSelectedItem();

                manager.createWaterReport(latitude, longitude, type, condition,
                        new CreateWaterSourceReportCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getBaseContext(), "Report successful!",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Toast.makeText(getBaseContext(), "Report FAILED!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(), "Invalid field input!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Button handler to return to the previous screen.
     * @param view the button initiating this method
     */
    public void cancel(View view) {
        finish();
    }

    public interface CreateWaterSourceReportCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
