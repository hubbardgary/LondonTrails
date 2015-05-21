package com.hubbard.android.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RouteOptions extends Activity implements OnItemSelectedListener {

    private Button button;
    private Spinner source;
    private Spinner destination;
    private Spinner direction;
    private List<String> destContents;
    private ArrayAdapter<String> dataAdapter1;
    private ArrayAdapter<String> dataAdapter2;
    private HashMap<String, Integer> hshSectionMap;
    private Resources res;
    private int sectionResource;
    private String[] sectionsArray;
    private Route currRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_options);

        res = getResources();
        sectionResource = getIntent().getExtras().getInt("routeSections");
        sectionsArray = res.getStringArray(sectionResource);

        GlobalObjects glob = (GlobalObjects) getApplicationContext();
        currRoute = glob.getCurrentRoute();

        switch (sectionResource) {
            case R.array.capital_ring_sections:
                currRoute = new CapitalRing();
                break;
            case R.array.london_loop_sections:
                currRoute = new LondonLoop();
                break;
        }

        glob.setCurrentRoute(currRoute);

        hshSectionMap = glob.getSectionMap();
        hshSectionMap = new HashMap<String, Integer>();
        String[] sections = sectionsArray;
        for (int i = 0; i < sections.length; i++) {
            hshSectionMap.put(sections[i], i);
        }
        glob.setSectionMap(hshSectionMap);

        source = populateSpinner(R.id.StartLocationSpinner, sectionResource);
        destination = populateSpinner(R.id.DestinationSpinner, sectionResource);

        direction = populateSpinner(R.id.DirectionSpinner, R.array.directions);
        if (!currRoute.isCircular()) {
            direction.setVisibility(View.GONE);
            findViewById(R.id.DirectionLbl).setVisibility(View.GONE);
        }

        addListenerOnButton();
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int pos, long id) {

        Spinner clickedSpinner = (Spinner) parent;
        String strStart = (String) source.getSelectedItem();
        String strEnd = (String) destination.getSelectedItem();

        if (clickedSpinner.getId() == R.id.StartLocationSpinner) {
            if (strStart.equals(strEnd)) {
                // Clear latLngEnd to prevent latLngStart and latLngEnd being the same.
                strEnd = "";
            }

            destContents = new ArrayList<String>(Arrays.asList(sectionsArray));

            if (!strStart.equals("")) {
                destContents.remove(destContents.indexOf(source.getSelectedItem()));
            }
            new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, destContents);
            dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, destContents);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            destination.setAdapter(dataAdapter2);
            dataAdapter2.notifyDataSetChanged();
            destination.setSelection(destContents.indexOf(strEnd));
        }

        if (strStart != null && !strStart.equals("") && strEnd != null && !strEnd.equals("") && !strStart.equals(strEnd))
            UpdateDistance(CalculateDistance());
    }

    @Override
    public void onNothingSelected(AdapterView arg0) {
    }

    private void addListenerOnButton() {
        final Context context = this;

        button = (Button) findViewById(R.id.GoBtn);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Grab latLngStart and latLngEnd from spinner controls
                int iStart = GetSelectedItemId((Spinner) findViewById(R.id.StartLocationSpinner));
                int iEnd = GetSelectedItemId((Spinner) findViewById(R.id.DestinationSpinner));

                // Invoke Map Activity, passing in the latLngStart and latLngEnd points
                Intent intent = new Intent(context, ShowMapActivity.class);
                intent.putExtra("latLngStart", iStart);
                intent.putExtra("latLngEnd", iEnd);

                if (currRoute.isCircular()) {
                    intent.putExtra("direction", ((Spinner) findViewById(R.id.DirectionSpinner)).getSelectedItemPosition());
                } else {
                    if (iStart < iEnd)
                        // Route will be walked forwards, so clockwise
                        intent.putExtra("direction", 0);
                    else
                        // Route will be walked backwards, so anticlockwise
                        intent.putExtra("direction", 1);
                }
                startActivity(intent);
            }
        });
    }

    private Spinner populateSpinner(int iSpinnerId, int iContents) {
        List<String> arrContents;
        arrContents = new ArrayList<String>(Arrays.asList(res.getStringArray(iContents)));
        Spinner spinner = (Spinner) findViewById(iSpinnerId);
        dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrContents);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);
        spinner.setOnItemSelectedListener(this);

        return spinner;
    }

    private double CalculateDistance() {
        int startSection = GetSelectedItemId((Spinner) findViewById(R.id.StartLocationSpinner));
        int endSection = GetSelectedItemId((Spinner) findViewById(R.id.DestinationSpinner));

        if (currRoute.isCircular()) {
            int direction = ((Spinner) findViewById(R.id.DirectionSpinner)).getSelectedItemPosition();
            if (direction == 1) {
                // anti-clockwise, so swap latLngStart and latLngEnd and calculate as clockwise.
                int temp = endSection;
                endSection = startSection;
                startSection = temp;
            }
        } else {
            if (startSection > endSection) {
                // anti-clockwise, so swap latLngStart and latLngEnd and calculate as clockwise.
                int temp = endSection;
                endSection = startSection;
                startSection = temp;
            }
        }

        double totalDistance = 0;
        int i = startSection;
        do {
            totalDistance += currRoute.getSection(i).getDistanceInKm();
            i++;

            if (currRoute.isCircular()) {
                // allow wraparound for circular routes
                if (i < 0)
                    i = currRoute.getSections().length - 1;
                if (i > currRoute.getSections().length - 1)
                    i = 0;
            }

        } while (i != endSection);

        int previousSection = endSection - 1;
        if (currRoute.isCircular() && previousSection < 0)
            previousSection = currRoute.getSections().length - 1;

        totalDistance += currRoute.getSection(startSection).getStartLinkDistanceInKm();
        totalDistance += currRoute.getSection(previousSection).getEndLinkDistanceInKm();

        return totalDistance;
    }

    private int GetSelectedItemId(Spinner s) {
        String item = (String) s.getSelectedItem();
        return hshSectionMap.get(item);
    }

    private void UpdateDistance(double distance) {
        TextView txtDistance = (TextView) findViewById(R.id.DistanceValue);
        txtDistance.setText(String.format("%.2f km (%.2f miles)", distance, distance * 0.62137));
    }
}
