package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;

/**
 * Created by Dimitar on 2/11/2017.
 */

public class WelcomeCakeController extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_cake);
        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageResource(R.mipmap.water_cat_cake);

    }

    public void logout(View view) {
        Log.d("logout", "water cake view logout");
        UserSession.currentSession().logout();
        startActivity(new Intent(WelcomeCakeController.this, LoginController.class));
    }
}
