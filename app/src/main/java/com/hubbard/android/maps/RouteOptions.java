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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
    public void onItemSelected(AdapterView arg0, View arg1, int pos, long arg3) {
        destination = (Spinner) findViewById(R.id.DestinationSpinner);

        String strStart = (String)source.getSelectedItem();
        String strEnd = (String)destination.getSelectedItem();

        if(strStart.equals(strEnd)) {
            // Clear end to prevent start and end being the same.
            strEnd = "";
        }

        destContents = new ArrayList<String>(Arrays.asList(sectionsArray));

        if(!strStart.equals("")) {
            destContents.remove(destContents.indexOf(source.getSelectedItem()));
        }
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, destContents);
        dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, destContents);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination.setAdapter(dataAdapter2);
        dataAdapter2.notifyDataSetChanged();
        destination.setSelection(destContents.indexOf(strEnd));
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
                Spinner start = (Spinner)findViewById(R.id.StartLocationSpinner);
                Spinner end = (Spinner)findViewById(R.id.DestinationSpinner);

                String strStart = (String)start.getSelectedItem();
                int iStart = hshSectionMap.get(strStart);

                String strEnd = (String)end.getSelectedItem();
                int iEnd = hshSectionMap.get(strEnd);

                // Invoke Map Activity, passing in the start and end points
                Intent intent = new Intent(context, ShowMapActivity.class);
                intent.putExtra("start", iStart);
                intent.putExtra("end", iEnd);

                if(currRoute.circular) {
                    intent.putExtra("direction", ((Spinner) findViewById(R.id.DirectionSpinner)).getSelectedItemPosition());
                }
                else {
                    if(iStart < iEnd)
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

        if(iSpinnerId == R.id.StartLocationSpinner) {
            spinner.setOnItemSelectedListener(this);
        }

        return spinner;
    }
}
