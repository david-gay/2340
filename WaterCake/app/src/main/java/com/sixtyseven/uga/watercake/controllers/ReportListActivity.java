package com.sixtyseven.uga.watercake.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.WaterSourceReport;

import java.util.List;

/**
 * Activity for listing Water Source Reports
 */
public class ReportListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        listView = (ListView) findViewById(R.id.reportList);

        List<WaterSourceReport> reports = ReportManager.getInstance().getWaterSourceReportList();

        ListAdapter adapter = new ReportAdapter(getApplicationContext(),reports);
        listView.setAdapter(adapter);

    }
}
