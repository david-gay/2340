package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.WaterSourceReport;

import java.util.List;


/**
 * Creates a map that the user can interact with for displaying water locations.
 */
public class MapsActivity extends AppCompatActivity implements OnInfoWindowClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();


        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled((true));
        mUiSettings.setRotateGesturesEnabled(true);

        // place markers
        List<WaterSourceReport> reports = ReportManager.getInstance().getWaterSourceReportList();
        List<Marker> reportMarkers = new java.util.LinkedList<>();
        for (WaterSourceReport report : reports) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(report.
                    getLatitude(), report.getLongitude())).title("Report Number: " +
                    report.getReportNumber()).snippet("Created: " + report.getDataAndTime()
                    + "\nAuthor: " + report.getAuthorUsername()
                    + "\nType: " + report.getWaterType()
                    + "\nCondition: " + report.getCondition()));
        }

        //set info window adapter and clickListener
        mMap.setInfoWindowAdapter(new MarkerWindowAdapter(getLayoutInflater()));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
    }

}
