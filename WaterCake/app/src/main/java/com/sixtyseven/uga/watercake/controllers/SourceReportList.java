package com.sixtyseven.uga.watercake.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.models.report.ReportManager;
import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.WaterSourceReport;

import java.util.List;

public class SourceReportList extends AppCompatActivity {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_report_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.sourceReportList);
        assert recyclerView != null;

        setupRecyclerview((RecyclerView) recyclerView);

    }

    private void setupRecyclerview(@NonNull RecyclerView recyclerView) {
        ReportManager reportManager = ReportManager.getInstance();
        recyclerView.setAdapter(new SourceReportsRecyclerViewAdapter(reportManager.
                getReportsList()));
    }

    public class SourceReportsRecyclerViewAdapter
            extends RecyclerView.Adapter<SourceReportsRecyclerViewAdapter.ViewHolder> {

        private final List mSourceReports;

        public SourceReportsRecyclerViewAdapter(List items) {
            mSourceReports = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                    source_report_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final ReportManager reportManager = ReportManager.getInstance();

            holder.mSourceReport = (WaterSourceReport) mSourceReports.get(position);

            holder.mIdView.setText("" + holder.mSourceReport.getReportNumber());
            holder.mContentView.setText(mSourceReports.get(position).toString());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent intent = new Intent(context, SourceReportController.class);

                   // intent.putExtra(SourceReportDetailFragment.ARG_REPORT_ID,
                   //         holder.mSourceReport.getReportNumber());

                    reportManager.setCurrentReport(holder.mSourceReport);

                    context.startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mSourceReports.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public WaterSourceReport mSourceReport;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
