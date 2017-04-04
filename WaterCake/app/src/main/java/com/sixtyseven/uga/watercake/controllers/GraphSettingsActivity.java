package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;

public class GraphSettingsActivity extends Activity {
    TextInputLayout longitudeInput;
    TextInputLayout latitudeInput;
    TextInputLayout yearInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_settings);
        longitudeInput = (TextInputLayout) findViewById(R.id.longitudeInput);
        latitudeInput = (TextInputLayout) findViewById(R.id.latitudeInput);
        yearInput = (TextInputLayout) findViewById(R.id.yearInput);
        Log.d("GraphSettingsActivity", "GraphSettingsActivity created");
    }

    /**
     * Button handler for attempting to generate a graph. Attempts to generate the graph and updates
     * any fields that may be invalid.
     * @param view the button initiating this method
     */
    public void attemptGenerateGraph(View view) {
        //checks if anything is formatted badly, just in case
        if (!longitudeInput.getEditText().getText().toString().matches("-?\\d+\\.?\\d*")
                || !latitudeInput.getEditText().getText().toString().matches("-?\\d+\\.?\\d*")
                || !yearInput.getEditText().getText().toString().matches("\\d+")) {
            Toast.makeText(getBaseContext(), "Invalid number format!", Toast.LENGTH_SHORT).show();
            Log.d("generateGraphFailure", "incorrect formatting of numbers");
        } else {
            double longitude = Double.parseDouble(
                    longitudeInput.getEditText().getText().toString());
            double latitude = Double.parseDouble(latitudeInput.getEditText().getText().toString());
            int year = Integer.parseInt(yearInput.getEditText().getText().toString());
            if ((longitude > 180 || longitude < -180) || (latitude > 90 || latitude < -90)) {
                Toast.makeText(getBaseContext(), "Invalid coordinates!", Toast.LENGTH_SHORT).show();
                Log.d("generateGraphFailure", "incorrect lat/long range");
            } else {
                Log.d("generateGraphSuccess", "graph should be generated");
                Intent intent = new Intent(GraphSettingsActivity.this, GraphActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", latitude);
                bundle.putDouble("long", longitude);
                bundle.putInt("year", year);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}
