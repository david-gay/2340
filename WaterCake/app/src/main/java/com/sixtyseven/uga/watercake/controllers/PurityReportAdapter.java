package com.sixtyseven.uga.watercake.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.WaterPurityReport;

import java.util.List;
import java.util.Locale;

/**
 * Array Adapter for displaying Purity Reports in a List View
 */
class PurityReportAdapter extends ArrayAdapter<WaterPurityReport> {
    private final List<WaterPurityReport> reports;

    /**
     * Constructor
     * @param context the context displaying the contents of this adapter
     * @param reports a list of reports to display
     */
    public PurityReportAdapter(Context context, List<WaterPurityReport> reports) {
        super(context, R.layout.fragment_purity_report_row, reports);

        this.reports = reports;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        View view = convertView;

        //only inflate if we're not reusing a view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_purity_report_row, null);
        }

        WaterPurityReport report = reports.get(position);

        if (report != null) {

            TextView reportNumberText = (TextView) view.findViewById(R.id.reportNumberTextView);
            if (reportNumberText != null) {
                reportNumberText.setText(String.format(Locale.US, "Report: %d",report.getReportNumber()));
            }

            TextView usernameText = (TextView) view.findViewById(R.id.usernameTextView);
            if (usernameText != null) {
                usernameText.setText(report.getAuthorUsername());
            }

            TextView latitudeText = (TextView) view.findViewById(R.id.latitudeTextView);
            if (latitudeText != null) {
                //NB, lat and long are never "negative"; they just have different direction
                if (report.getLatitude() < 0) {
                    latitudeText.setText(
                            String.format(Locale.US, "%4fS", Math.abs(report.getLatitude())));
                } else {
                    latitudeText.setText(String.format(Locale.US, "%4fN", report.getLatitude()));
                }
            }

            TextView longitudeText = (TextView) view.findViewById(R.id.longitudeTextView);
            if (longitudeText != null) {
                //NB, lat and long are never "negative"; they just have different direction
                if (report.getLongitude() < 0) {
                    longitudeText.setText(
                            String.format(Locale.US, "%4fW", Math.abs(report.getLongitude())));
                } else {
                    longitudeText.setText(String.format(Locale.US, "%4fE", report.getLongitude()));
                }
            }

            TextView waterPurityConditionText = (TextView) view.findViewById(R.id.waterPurityConditionTextView);
            if (waterPurityConditionText != null) {
                waterPurityConditionText.setText(report.getWaterPurityCondition().toString());
            }

            TextView virusPpmText = (TextView) view.findViewById(R.id.virusPpmTextView);
            if (virusPpmText != null) {
                virusPpmText.setText(Float.toString(report.getVirusPpm()));
            }

            TextView contaminantPpmText = (TextView) view.findViewById(R.id.contaminantPpmTextView);
            if (contaminantPpmText != null) {
                contaminantPpmText.setText(Float.toString(report.getContaminantPpm()));
            }

        }

        // the view must be returned to our activity
        return view;
    }
}
