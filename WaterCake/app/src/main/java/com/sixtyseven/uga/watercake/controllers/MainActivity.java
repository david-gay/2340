package com.sixtyseven.uga.watercake.controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.WaterSourceReport;
import com.sixtyseven.uga.watercake.models.user.User;
import com.sixtyseven.uga.watercake.models.user.UserType;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private User currentUser;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = UserSession.currentSession(getApplicationContext()).getCurrentUser();
        setupToolbar();
        initNavigationDrawer();
        fetchAllReports();
        initMap();
        initFAB();
    }

    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_source_report_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startCreateSourceReportActivity();
            }
        });
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void fetchAllReports() {
        ReportManager.getInstance(this.getApplicationContext()).fetchAllReports(
                new FetchReportsCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("MainActivity", "reports fetched successfully");
                        placeMarkers(mMap);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("MainActivity", "failed to fetch reports: " + errorMessage);
                        Snackbar.make(mNavigationView, "Failed to fetch reports!",
                                Snackbar.LENGTH_INDEFINITE).show();
                    }
                });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupActionBarDrawerToggle();
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        View hView = mNavigationView.getHeaderView(0);

        TextView usernameTextView = (TextView) hView.findViewById(R.id.txtViewName);
        usernameTextView.setText(currentUser.getUsername());

        TextView emailTextView = (TextView) hView.findViewById(R.id.txtViewEmail);
        emailTextView.setText(currentUser.getEmail());
    }

    /**
     * In case if you require to handle drawer open and close states
     */
    private void setupActionBarDrawerToggle() {

        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                // Snackbar.make(view, R.string.drawer_close, Snackbar.LENGTH_SHORT).show();
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                // Snackbar.make(drawerView, R.string.drawer_open, Snackbar.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setupDrawerContent(final NavigationView navigationView) {

        addItemsRunTime(navigationView);

        //setting up selected item listener
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        navigationView.setCheckedItem(R.id.nav_water_sources);

                        switch (menuItem.getItemId()) {
                            case R.id.nav_water_sources:
                                // it's this page so break
                                break;

                            case R.id.nav_create_source_report:
                                startCreateSourceReportActivity();
                                break;

                            case R.id.nav_view_source_list:
                                Log.d("MainActivity", "go to View Water Report");
                                startActivity(
                                        new Intent(MainActivity.this, ReportListActivity.class));
                                break;

                            case R.id.nav_create_purity_report:
                                Log.d("MainActivity", "go to Create Purity Report");
                                startActivity(new Intent(MainActivity.this,
                                        CreatePurityReportActivity.class));
                                break;

                            case R.id.nav_view_purity_reports:
                                Log.d("MainActivity", "go to View Purity Report");
                                startActivity(new Intent(MainActivity.this,
                                        PurityReportListActivity.class));
                                break;

                            case R.id.nav_view_historical_report:
                                Log.d("MainActivity", "go to Create Historical Report");
                                startActivity(
                                        new Intent(MainActivity.this, GraphSettingsActivity.class));
                                break;

                            case R.id.nav_edit_profile:
                                Log.d("MainActivity", "go to Edit Profile.");
                                startActivity(
                                        new Intent(MainActivity.this, EditProfileActivity.class));
                                break;

                            case R.id.nav_exit:
                                Log.d("MainActivity", "logout");
                                UserSession.currentSession(getApplicationContext()).logout();
                                finish();
                                break;

                            default:
                                return false;
                        }

                        return true;
                    }
                });
    }

    private void startCreateSourceReportActivity() {
        Log.d("MainActivity", "go to Create Source Report");
        startActivity(new Intent(MainActivity.this, CreateWaterReportActivity.class));
    }

    private void addItemsRunTime(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        UserType userType = currentUser.getUserType();

        if (userType.ordinal() < UserType.WORKER.ordinal()) {
            menu.setGroupVisible(R.id.grp2, false);
        }

        if (userType.ordinal() < UserType.MANAGER.ordinal()) {
            menu.getItem(4).setVisible(false);
            menu.setGroupVisible(R.id.grp3, false);
        }

        // refreshing navigation drawer adapter
        for (int i = 0, count = mNavigationView.getChildCount(); i < count; i++) {
            final View child = mNavigationView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            // this is for the hamburger
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            //            //this is for the triple dot
            //            case R.id.action_settings:
            //                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = googleMap.getUiSettings();

        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomGesturesEnabled((true));
        uiSettings.setRotateGesturesEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now; get them if we need them
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // permission has been granted, continue as usual
            googleMap.setMyLocationEnabled(true);
        }

        //set info window adapter and clickListener
        googleMap.setInfoWindowAdapter(new MarkerWindowAdapter(getLayoutInflater()));
        googleMap.setOnInfoWindowClickListener(this);
    }

    private void placeMarkers(GoogleMap googleMap) {
        List<WaterSourceReport> reports = ReportManager.getInstance(this.getApplicationContext())
                .getWaterSourceReportList();
        //        Toast.makeText(getApplicationContext(), "Water Reports: " + reports.size(),
        //                Toast.LENGTH_LONG).show();
        Log.d("reportsNUM", "" + reports.size());
        for (WaterSourceReport report : reports) {
            Log.d("reportsNUM", "" + report.getReportNumber());
            googleMap.addMarker(new MarkerOptions().position(new LatLng(report.
                    getLatitude(), report.getLongitude()))
                    .title("Report Number: " + report.getReportNumber()).snippet(
                            "Created: " + report.getPostDate() + "\nAuthor: " + report
                                    .getAuthorUsername() + "\nType: " + report.getWaterType()
                                    + "\nCondition: " + report.getWaterCondition()));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationView.setCheckedItem(R.id.nav_water_sources);
    }

    public interface FetchReportsCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}