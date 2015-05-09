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

public class LondonLoopOptions extends Activity implements OnItemSelectedListener {

    Button button;
    Spinner source, destination, direction;
    List<String> sourceContents, destContents;
    ArrayAdapter<String> dataAdapter1, dataAdapter2;
    HashMap<String, Integer> hshSectionMap;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_london_loop_options);

        res = getResources();
        GlobalObjects glob = (GlobalObjects)getApplicationContext();
        Route currRoute = glob.getCurrentRoute();
        currRoute = new LondonLoop();
        glob.setCurrentRoute(currRoute);

        hshSectionMap = glob.getSectionMap();
        hshSectionMap = new HashMap<String, Integer>();
        String[] sections = res.getStringArray(R.array.london_loop_sections);
        for(int i = 0; i < sections.length; i++) {
            hshSectionMap.put((String)sections[i], i);
        }
        glob.setSectionMap(hshSectionMap);

        source = populateSpinner(R.id.spinner1, R.array.london_loop_sections);
        destination = populateSpinner(R.id.spinner2, R.array.london_loop_sections);

        direction = populateSpinner(R.id.spinner3, R.array.directions);
//        if(!currRoute.circular) {
//            direction.setVisibility(View.GONE);
//        }

        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.london_loop_options, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView arg0, View arg1, int pos, long arg3) {
        destination = (Spinner) findViewById(R.id.spinner2);

        String strStart = (String)source.getSelectedItem();
        String strEnd = (String)destination.getSelectedItem();

        if(strStart.equals(strEnd)) {
            // Clear end to prevent start and end being the same.
            strEnd = "";
        }

        destContents = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.london_loop_sections)));

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

        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Grab start and end from spinner controls
                Spinner start = (Spinner)findViewById(R.id.spinner1);
                Spinner end = (Spinner)findViewById(R.id.spinner2);

                String strStart = (String)start.getSelectedItem();
                int iStart = hshSectionMap.get(strStart);

                String strEnd = (String)end.getSelectedItem();
                int iEnd = hshSectionMap.get(strEnd);

                // Invoke Map Activity, passing in the start and end points
                Intent intent = new Intent(context, ShowMapActivity.class);
                intent.putExtra("start", iStart);
                intent.putExtra("end", iEnd);
                intent.putExtra("direction", ((Spinner)findViewById(R.id.spinner3)).getSelectedItemPosition());
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

        if(iSpinnerId == R.id.spinner1) {
            spinner.setOnItemSelectedListener(this);
        }

        return spinner;
    }

}
