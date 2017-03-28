package com.sixtyseven.uga.watercake.controllers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import com.sixtyseven.uga.watercake.R;

/**
 * adapter to create custom infoWindows for markers on map
 */
public class MarkerWindowAdapter implements InfoWindowAdapter {
    private View markerWindow = null;
    private LayoutInflater inflater = null;

    /**
     * Internal constructor
     * @param inflater the LayoutInflater to use for inflating the marker window
     */
    MarkerWindowAdapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (markerWindow == null) {
            markerWindow = inflater.inflate(R.layout.marker_window, null);
        }

        TextView textView = (TextView) markerWindow.findViewById(R.id.title);

        textView.setText(marker.getTitle());
        textView = (TextView) markerWindow.findViewById(R.id.snippet);
        textView.setText(marker.getSnippet());

        return(markerWindow);
    }
}
