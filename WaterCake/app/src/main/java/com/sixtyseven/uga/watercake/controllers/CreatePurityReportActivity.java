package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;

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


    }

    /**
     * Button handler for attempting to submit a report. Attempts to submit a report and updates any
     * fields that may be invalid.
     * @param view the button initiating this method
     */
    public void attemptSubmitReport(View view) {
        String sLongitude;
        String sLatitude;
        String sVirusPpm;
        String sContaminantPpm;

        double longitude = 0;
        double latitude = 0;
        float virusPpm = 0;
        float contaminantPpm = 0;

        ReportManager manager = ReportManager.getInstance();

        // Get String Versions of Every Field, Empty fields are == ""
        sLongitude = longitudeInput.getEditText().getText().toString();
        sLatitude = latitudeInput.getEditText().getText().toString();
        sVirusPpm = virusPpmInput.getEditText().getText().toString();
        sContaminantPpm = contaminantPpmInput.getEditText().getText().toString();

        // Make sure all fields are inputted
        if (sLongitude.equalsIgnoreCase("") || sLatitude.equalsIgnoreCase("")
                || sVirusPpm.equalsIgnoreCase("") || sContaminantPpm.equalsIgnoreCase("")) {
            // At least one blank input! User must give input for all fields
            Toast.makeText(getBaseContext(), "Fields cannot be blank!", Toast.LENGTH_SHORT).show();
        } else {
            // Yay no blank fields!

            Log.d("state", "Entering no blank fields else");

            Log.d("sLongitude", sLongitude);
            Log.d("sLatitude", sLatitude);
            Log.d("sVirus", sVirusPpm);
            Log.d("sContaminant", sContaminantPpm);

            // Kludgy last-minute implementation of the try-catch to handle non-character input
            try {
                longitude = Double.parseDouble(sLongitude);
                latitude = Double.parseDouble(sLatitude);
                virusPpm = Float.parseFloat(sVirusPpm);
                contaminantPpm = Float.parseFloat(sContaminantPpm);

                Log.d("val", "Long: " + longitude);
                Log.d("val", "Lat: " + latitude);
                Log.d("val", "virus: " + virusPpm);
                Log.d("val", "contam: " + contaminantPpm);

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
                    WaterCondition condition = WaterCondition.WASTE; // Placeholder value, need to remove later
                    WaterType waterType = WaterType.BOTTLED; // Placeholder value, need to remove later

                    manager.createPurityReport(UserSession.currentSession().getCurrentUser().getUsername(),
                            latitude, longitude, waterType, condition, purityCondition, virusPpm, contaminantPpm);

                    Toast.makeText(getBaseContext(), "Report successful!", Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Invalid field input!", Toast.LENGTH_SHORT).show();
            }

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
