package com.hubbardgary.londontrails.view;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hubbardgary.londontrails.R;

class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private ShowMapActivity activity;

    public InfoWindowAdapter(ShowMapActivity activity) {
        this.activity = activity;
    }

    /* The in-built marker functionality truncates snippets that would span more than one line.
     * Override getInfoContents to display a custom view within the info window. This uses the popup.xml layout.
     */
    @Override
    public View getInfoWindow(Marker marker) {
        if(marker.getTitle().isEmpty() && marker.getSnippet().isEmpty()) {
            // No text to display, so don't show the info window
            return null;
        } else {
            View v = activity.getLayoutInflater().inflate(R.layout.popup, null);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView info = (TextView) v.findViewById(R.id.snippet);
            title.setText(marker.getTitle());
            info.setText(marker.getSnippet());
            return v;
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
