package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.WaterPurityReport;

import java.util.List;

/**
 * Activity for displaying Purity Reports
 */
public class PurityReportListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        listView = (ListView) findViewById(R.id.reportList);

        List<WaterPurityReport> reports = ReportManager.getInstance(this.getApplicationContext())
                .getWaterPurityReportList();

        ListAdapter adapter = new PurityReportAdapter(getApplicationContext(), reports);
        listView.setAdapter(adapter);
    }
}
