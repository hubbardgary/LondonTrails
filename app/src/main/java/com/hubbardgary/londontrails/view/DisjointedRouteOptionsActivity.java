package com.hubbardgary.londontrails.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.presenter.DisjointedRouteOptionsPresenter;
import com.hubbardgary.londontrails.presenter.RouteOptionsPresenter;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisjointedRouteOptionsActivity extends Activity implements IRouteOptionsView, AdapterView.OnItemSelectedListener {

    private Spinner sectionSpinner;
    private DisjointedRouteOptionsPresenter presenter;
    private RouteViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disjointed_route_options);

        presenter = new DisjointedRouteOptionsPresenter(this, (GlobalObjects)getApplicationContext(), getApplicationContext().getResources());
        vm = presenter.getViewModel();
        setTitle(vm.name);
        initializeSpinners();
        sectionSpinner.setSelection(vm.startSelectedIndex);
        addListenerOnGoButton();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("selectedSection", GetSelectedItemId(sectionSpinner));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // If app is killed by Android, route options may need to be restored manually
        vm.startSection = savedInstanceState.getInt("selectedSection");
    }

    private void initializeSpinners() {
        sectionSpinner = populateSpinner(R.id.StartLocationSpinner, vm.startOptions);
        sectionSpinner.setOnItemSelectedListener(this);
    }

    private Spinner populateSpinner(int spinnerId, List<String> contents) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contents);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        return spinner;
    }

    private int GetSelectedItemId(Spinner s) {
        return presenter.getSectionId(s.getSelectedItem().toString());
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        switch(parent.getId()) {
            case R.id.StartLocationSpinner :
                vm.startSection = GetSelectedItemId(sectionSpinner);
                vm.endSection = vm.startSection;
                break;
        }
        presenter.optionsChanged(vm);
    }

    @Override
    public void onNothingSelected(AdapterView arg0) {
    }

    private void addListenerOnGoButton() {
        findViewById(R.id.GoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                presenter.activitySubmit(vm);
            }
        });
    }

    // IRouteOptions methods. Invoked from presenter.
    public void invokeActivity(HashMap<String, Integer> intents, Class<?> activity) {
        Intent intent = new Intent(this, activity);
        for (Map.Entry<String, Integer> entry : intents.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        this.startActivity(intent);
    }

    public int getRouteSectionsFromIntent() {
        return getIntent().getExtras().getInt("routeSections");
    }

    @Override
    public void refreshDestinationSpinner(RouteViewModel vm) {

    }

    public void refreshDistance(RouteViewModel vm) {
        TextView txtDistance = (TextView) findViewById(R.id.DistanceValue);
        txtDistance.setText(String.format("%.2f km (%.2f miles)", vm.distanceKm, vm.distanceMiles));
    }
}
