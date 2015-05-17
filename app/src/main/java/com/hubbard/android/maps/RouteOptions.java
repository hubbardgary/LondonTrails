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

    Button button;
    Spinner source, destination, direction;
    List<String> sourceContents, destContents;
    ArrayAdapter<String> dataAdapter1, dataAdapter2;
    HashMap<String, Integer> hshSectionMap;
    Resources res;
    int sectionResource;
    String[] sectionsArray;
    Route currRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_options);

        res = getResources();
        sectionResource = getIntent().getExtras().getInt("routeSections");
        sectionsArray = res.getStringArray(sectionResource);

        GlobalObjects glob = (GlobalObjects)getApplicationContext();
        currRoute = glob.getCurrentRoute();

        switch (sectionResource) {
            case R.array.capital_ring_sections :
                currRoute = new CapitalRing();
                break;
            case R.array.london_loop_sections :
                currRoute = new LondonLoop();
                break;
        }

        glob.setCurrentRoute(currRoute);

        hshSectionMap = glob.getSectionMap();
        hshSectionMap = new HashMap<String, Integer>();
        String[] sections = sectionsArray;
        for(int i = 0; i < sections.length; i++) {
            hshSectionMap.put((String)sections[i], i);
        }
        glob.setSectionMap(hshSectionMap);

        source = populateSpinner(R.id.StartLocationSpinner, sectionResource);
        destination = populateSpinner(R.id.DestinationSpinner, sectionResource);

        direction = populateSpinner(R.id.DirectionSpinner, R.array.directions);
        if(!currRoute.circular) {
            direction.setVisibility(View.GONE);
            findViewById(R.id.DirectionLbl).setVisibility(View.GONE);
        }

        addListenerOnButton();
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int pos, long id) {

        Spinner clickedSpinner = (Spinner)parent;
        String strStart = (String) source.getSelectedItem();
        String strEnd = (String) destination.getSelectedItem();

        if(clickedSpinner.getId() == R.id.StartLocationSpinner) {
            if (strStart.equals(strEnd)) {
                // Clear end to prevent start and end being the same.
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

        if(strStart != null && strStart != "" && strEnd != null && strEnd != "" && strStart != strEnd)
            UpdateDistance(CalculateDistance());
    }

    @Override
    public void onNothingSelected(AdapterView arg0) {
    }

    public void addListenerOnButton() {
        final Context context = this;

        button = (Button)findViewById(R.id.GoBtn);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Grab start and end from spinner controls
                int iStart = GetSelectedItemId((Spinner) findViewById(R.id.StartLocationSpinner));
                int iEnd = GetSelectedItemId((Spinner) findViewById(R.id.DestinationSpinner));

                // Invoke Map Activity, passing in the start and end points
                Intent intent = new Intent(context, ShowMapActivity.class);
                intent.putExtra("start", iStart);
                intent.putExtra("end", iEnd);

                if (currRoute.circular) {
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
        int startSection = GetSelectedItemId((Spinner)findViewById(R.id.StartLocationSpinner));
        int endSection = GetSelectedItemId((Spinner)findViewById(R.id.DestinationSpinner));
        
        if(currRoute.circular) {
            int direction = ((Spinner) findViewById(R.id.DirectionSpinner)).getSelectedItemPosition();
            if(direction == 1) {
                // anti-clockwise, so swap start and end and calculate as clockwise.
                int temp = endSection;
                endSection = startSection;
                startSection = temp;
            }
        }
        else {
            if(startSection > endSection) {
                // anti-clockwise, so swap start and end and calculate as clockwise.
                int temp = endSection;
                endSection = startSection;
                startSection = temp;
            }
        }
        
        double totalDistance = 0;
        int i = startSection;
        do {
            totalDistance += currRoute.sections[i].distanceInKm;
            i++;

            if(currRoute.circular) {
                // allow wraparound for circular routes
                if (i < 0)
                    i = currRoute.sections.length - 1;
                if (i > currRoute.sections.length - 1)
                    i = 0;
            }

        } while(i != endSection);

        int previousSection = endSection - 1;
        if(currRoute.circular && previousSection < 0)
            previousSection = currRoute.sections.length - 1;

        totalDistance += currRoute.sections[startSection].startLinkDistanceInKm;
        totalDistance += currRoute.sections[previousSection].endLinkDistanceInKm;
        
        return totalDistance;
    }

    private int GetSelectedItemId(Spinner s) {
        String item = (String)s.getSelectedItem();
        return hshSectionMap.get(item);
    }

    private void UpdateDistance(double distance) {
        TextView txtDistance = (TextView)findViewById(R.id.DistanceValue);
        txtDistance.setText(String.format("%.2f km (%.2f miles)", distance, distance * 0.62137));
    }
}
