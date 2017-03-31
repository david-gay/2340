package com.sixtyseven.uga.watercake.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.WaterSourceReport;

import java.util.List;
import java.util.Locale;

/**
 * Report Adapter for displaying Water Source Reports
 */
class ReportAdapter extends ArrayAdapter<WaterSourceReport> {
    private List<WaterSourceReport> reports;

    /**
     * Constructor
     * @param context the Context using the adapter
     * @param reports the list of reports to display
     */
    ReportAdapter(Context context, List<WaterSourceReport> reports) {
        super(context, R.layout.fragment_report_row, reports);

        this.reports = reports;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        // only inflate if we're not reusing a view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_report_row, null);
        }

        WaterSourceReport report = reports.get(position);

        if (report != null) {

            TextView reportNumberText = (TextView) view.findViewById(R.id.reportNumberTextView);
            if (reportNumberText != null) {
                reportNumberText.setText(
                        String.format(Locale.US, "Report: %d", report.getReportNumber()));
            }

            TextView usernameText = (TextView) view.findViewById(R.id.usernameTextView);
            if (usernameText != null) {
                usernameText.setText(report.getAuthorUsername());
            }

            TextView latitudeText = (TextView) view.findViewById(R.id.latitudeTextView);
            if (latitudeText != null) {
                // NB, lat and long are never "negative"; they just have different direction
                if (report.getLatitude() < 0) {
                    latitudeText.setText(
                            String.format(Locale.US, "%4fS", Math.abs(report.getLatitude())));
                } else {
                    latitudeText.setText(String.format(Locale.US, "%4fN", report.getLatitude()));
                }
            }

            TextView longitudeText = (TextView) view.findViewById(R.id.longitudeTextView);
            if (longitudeText != null) {
                // NB, lat and long are never "negative"; they just have different direction
                if (report.getLongitude() < 0) {
                    longitudeText.setText(
                            String.format(Locale.US, "%4fW", Math.abs(report.getLongitude())));
                } else {
                    longitudeText.setText(String.format(Locale.US, "%4fE", report.getLongitude()));
                }
            }

            TextView waterTypeText = (TextView) view.findViewById(R.id.waterTypeTextView);
            if (waterTypeText != null) {
                waterTypeText.setText(report.getWaterType().toString());
            }

            TextView waterConditionText = (TextView) view.findViewById(R.id.waterConditionTextView);
            if (waterConditionText != null) {
                waterConditionText.setText(report.getWaterCondition().toString());
            }
        }

        // the view must be returned to our activity
        return view;
    }
}
