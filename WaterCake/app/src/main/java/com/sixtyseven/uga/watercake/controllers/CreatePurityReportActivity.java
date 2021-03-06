package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.constants.WaterPurityCondition;

/**
 * Activity for creating Purity reports.
 */
public class CreatePurityReportActivity extends AppCompatActivity {

    private TextInputLayout latitudeInput;
    private TextInputLayout longitudeInput;
    private Spinner waterPurityConditionSpinner;
    private TextInputLayout virusPpmInput;
    private TextInputLayout contaminantPpmInput;

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

        double longitude;
        double latitude;
        float virusPpm;
        float contaminantPpm;

        ReportManager manager = ReportManager.getInstance(this.getApplicationContext());

        // Get String Versions of Every Field, Empty fields are == ""
        sLongitude = longitudeInput.getEditText().getText().toString();
        sLatitude = latitudeInput.getEditText().getText().toString();
        sVirusPpm = virusPpmInput.getEditText().getText().toString();
        sContaminantPpm = contaminantPpmInput.getEditText().getText().toString();

        // Make sure all fields are inputted
        if (sLongitude.equalsIgnoreCase("") || sLatitude.equalsIgnoreCase("") || sVirusPpm
                .equalsIgnoreCase("") || sContaminantPpm.equalsIgnoreCase("")) {
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
                    Toast.makeText(getBaseContext(), "Invalid coordinates!", Toast.LENGTH_SHORT)
                            .show();
                } else if (virusPpm < 0) {
                    // Negative Virus PPM
                    Toast.makeText(getBaseContext(), "Invalid Virus PPM!", Toast.LENGTH_SHORT)
                            .show();
                } else if (contaminantPpm < 0) {
                    // Negative Contaminant PPM
                    Toast.makeText(getBaseContext(), "Invalid Contaminant PPM!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Success!
                    WaterPurityCondition purityCondition =
                            (WaterPurityCondition) waterPurityConditionSpinner.getSelectedItem();

                    manager.createPurityReport(latitude, longitude, purityCondition, virusPpm,
                            contaminantPpm, new CreateWaterPurityReportCallback() {
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
    }

    /**
     * Button handler to return to the previous screen.
     * @param view the button initiating this method
     */
    public void cancel(View view) {
        finish();
    }

    public interface CreateWaterPurityReportCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
