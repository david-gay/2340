package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.user.UserType;

/**
 * Welcome screen Controller
 */
public class WelcomeCakeController extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_cake);
        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageResource(R.mipmap.water_cat_cake);
        ((TextView) findViewById(R.id.welcomeText)).setText(
                "Welcome, " + UserSession.currentSession().getCurrentUser().getUsername() + "!");

        // Hide Purity Create Report Buttons unless Worker+
        Button createPurityReportButton = (Button) findViewById(R.id.createPurityReportButton);
        UserType userType = UserSession.currentSession().getCurrentUser().getUserType();
        // Only show the button if above a Contributor
        if (userType != UserType.CONTRIBUTOR) {
            createPurityReportButton.setVisibility(View.VISIBLE); //SHOW the button
        }

        // Hide Purity View Report Buttons unless Manager+
        Button viewPurityReportButton = (Button) findViewById(R.id.ViewPurityReportButton);
        UserType userType2 = UserSession.currentSession().getCurrentUser().getUserType();
        // Only show the button if above a Worker
        if ((userType2 != UserType.CONTRIBUTOR) && (userType2 != UserType.WORKER)) {
            viewPurityReportButton.setVisibility(View.VISIBLE); //SHOW the button
        }

        Log.d("welcomecake controller", "welcomecake controller created");
        ReportManager.getInstance(this.getApplicationContext()).fetchAllReports();
    }

    /**
     * Event handler for the logout button.
     * @param view the button that initiated this event
     */
    public void logout(View view) {
        Log.d("logout", "water cake view logout");
        UserSession.currentSession().logout();
        finish();
    }

    public void editProfile(View view) {
        Log.d("WelcomeCakeController", "Clicked Edit Profile.");
        startActivity(new Intent(WelcomeCakeController.this, EditProfileActivity.class));
    }

    public void createWaterReport(View view) {
        Log.d("report", "go to Create Water Report");
        startActivity(new Intent(WelcomeCakeController.this, CreateWaterReportActivity.class));
    }

    public void viewWaterReports(View view) {
        Log.d("report", "go to View Water Report");
        startActivity(new Intent(WelcomeCakeController.this, ReportListActivity.class));
    }

    public void goToMap(View view) {
        Log.d("map", "go to map");
        startActivity(new Intent(WelcomeCakeController.this, MapsActivity.class));
    }

    public void createPurityReport(View view) {
        Log.d("purity report", "go to Create Purity Report");
        startActivity(new Intent(WelcomeCakeController.this, CreatePurityReportActivity.class));
    }

    public void viewPurityReports(View view) {
        Log.d("purity report", "go to View Purity Report");
        startActivity(new Intent(WelcomeCakeController.this, PurityReportListActivity.class));
    }
}
