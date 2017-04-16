package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.models.report.WaterPurityReport;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        savedInstanceState = getIntent().getExtras();
        Log.d("GraphActivity", "Getting values to graph");
        List<WaterPurityReport> reports = ReportManager.getInstance(this.getApplicationContext())
                .filterWaterPurityReportList(savedInstanceState.getInt("year"),
                        savedInstanceState.getDouble("lat"), savedInstanceState.getDouble("long"));
        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        Log.d("GraphActivity", "Graphing values");
        if (reports.size() > 0) {
            List<Entry> contamentries = convertReportsToContaminantEntry(reports);
            List<Entry> virusentries = convertReportsToVirusEntry(reports);

            LineDataSet contamdataset = new LineDataSet(contamentries, "Contaminant");
            LineDataSet virusdataset = new LineDataSet(virusentries, "Viruses");
            contamdataset.setCircleColors(ColorTemplate.COLORFUL_COLORS[0]);
            virusdataset.setCircleColors(ColorTemplate.LIBERTY_COLORS[1]);
            contamdataset.setColors(ColorTemplate.COLORFUL_COLORS[0]);
            virusdataset.setColors(ColorTemplate.LIBERTY_COLORS[1]);
            LineData data = new LineData(contamdataset, virusdataset);

            lineChart.setData(data);

            lineChart.getDescription().setText("PPM by month");
            Log.d("GraphActivity", "GraphActivity created");
        }
    }

    private List<Entry> convertReportsToVirusEntry(List<WaterPurityReport> data) {
        List<Entry> entries = new ArrayList<>();
        int[] count = new int[12];
        float[] total = new float[12];
        for (WaterPurityReport d : data) {
            count[d.getPostDate().getMonth()]++;
            total[d.getPostDate().getMonth()] += d.getVirusPpm();
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                entries.add(new Entry(i + 1, total[i] / count[i]));
            }
        }
        return entries;
    }

    private List<Entry> convertReportsToContaminantEntry(List<WaterPurityReport> data) {
        List<Entry> entries = new ArrayList<>();
        int[] count = new int[12];
        float[] total = new float[12];
        for (WaterPurityReport d : data) {
            count[d.getPostDate().getMonth()]++;
            total[d.getPostDate().getMonth()] += d.getContaminantPpm();
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                entries.add(new Entry(i + 1, total[i] / count[i]));
            }
        }
        return entries;
    }
}
