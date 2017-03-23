package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.constants.WaterCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;
import com.sixtyseven.uga.watercake.models.report.constants.WaterType;

/**
 * 2017-03-23 Susannah Doss
 */
public class CreatePurityReportActivity extends AppCompatActivity {

    TextInputLayout latitudeInput;
    TextInputLayout longitudeInput;
    Spinner waterPurityConditionSpinner;
    TextInputLayout virusPpmInput;
    TextInputLayout contaminantPpmInput;
    Spinner waterTypeSpinner;
    Spinner waterConditionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_purity_report);

        latitudeInput = (TextInputLayout) findViewById(R.id.latitudeInput);
        longitudeInput = (TextInputLayout) findViewById(R.id.longitudeInput);

        waterPurityConditionSpinner = (Spinner) findViewById(R.id.waterPurityConditionSpinner);
        ArrayAdapter<WaterPurityCondition> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterPurityCondition.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterPurityConditionSpinner.setAdapter(adapter);

        virusPpmInput = (TextInputLayout) findViewById(R.id.virusPpmInput);
        contaminantPpmInput = (TextInputLayout) findViewById(R.id.contaminantPpmInput);

        waterTypeSpinner = (Spinner) findViewById(R.id.waterTypeSpinner);
        ArrayAdapter<WaterType> waterTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterType.values());
        waterTypeArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeArrayAdapter);

        waterConditionSpinner = (Spinner) findViewById(R.id.waterConditionSpinner);
        ArrayAdapter<WaterCondition> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterCondition.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionSpinner.setAdapter(adapter2);

    }

    /**
     * Button handler for attempting to submit a report. Attempts to submit a report and updates any
     * fields that may be invalid.
     * @param view the button initiating this method
     */
    public void attemptSubmitReport(View view) {
        ReportManager manager = ReportManager.getInstance();

        double longitude = Double.parseDouble(longitudeInput.getEditText().getText().toString());
        double latitude = Double.parseDouble(latitudeInput.getEditText().getText().toString());

        float virusPpm = Float.parseFloat(virusPpmInput.getEditText().getText().toString());
        float contaminantPpm = Float.parseFloat(contaminantPpmInput.getEditText().getText().toString());

        if ((longitude > 180 || longitude < -180) || (latitude > 90 || latitude < -90)) {
            // Invalid coords
            Toast.makeText(getBaseContext(), "Invalid coordinates!", Toast.LENGTH_SHORT).show();
        } else if (virusPpm < 0) {
            // Negative Virus PPM
            Toast.makeText(getBaseContext(), "Invalid Virus PPM!", Toast.LENGTH_SHORT).show();
        } else if (contaminantPpm < 0) {
            // Negative Contaminant PPM
            Toast.makeText(getBaseContext(), "Invalid Contaminant PPM!", Toast.LENGTH_SHORT).show();
        } else {
            // Success!
            WaterPurityCondition purityCondition = (WaterPurityCondition) waterPurityConditionSpinner.getSelectedItem();
            WaterCondition condition = (WaterCondition) waterConditionSpinner.getSelectedItem();
            WaterType type = (WaterType) waterTypeSpinner.getSelectedItem();

            //manager.createPurityReport(UserSession.currentSession().getCurrentUser().getUsername(),
            //        latitude, longitude, waterType, condition, purityCondition, virusPpm, contaminantPpm);

            Toast.makeText(getBaseContext(), "Report successful!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Button handler to return to the previous screen.
     * @param view the button initiating this method
     */
    public void cancel(View view) {
        finish();
    }
}
