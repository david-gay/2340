package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;

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
        ((TextView) findViewById(R.id.welcomeText)).setText("Welcome, "
                + UserSession.currentSession().getCurrentUser().getUsername() + "!");
        Log.d("welcomecake controller", "welcomecake controller created");

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
}
