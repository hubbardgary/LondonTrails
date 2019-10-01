package com.hubbardgary.londontrails.view;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IGlobalObjects;
import com.hubbardgary.londontrails.presenter.RouteOptionsPresenter;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

public class RouteOptionsActivity extends Activity implements IRouteOptionsView, OnItemSelectedListener {

    private Spinner startSpinner;
    private Spinner endSpinner;
    private Spinner directionSpinner;
    private RouteOptionsPresenter presenter;
    private RouteViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_options);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new RouteOptionsPresenter(this, (IGlobalObjects)getApplicationContext(), getApplicationContext().getResources());
        vm = presenter.getViewModel();
        setTitle(vm.name);
        initializeSpinners();
        if(!vm.isCircular)
            hideDirection();
        startSpinner.setSelection(vm.startSelectedIndex);
        endSpinner.setSelection(vm.endSelectedIndex);
        addListenerOnGoButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            presenter.menuItemSelected(item.getItemId());
            return true;
        }
        catch(Exception e) {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("startSection", GetSelectedItemId(startSpinner));
        savedInstanceState.putInt("endSection", GetSelectedItemId(endSpinner));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // If app is killed by Android, route options may need to be restored manually
        vm.startSection = savedInstanceState.getInt("startSection");
        vm.endSection = savedInstanceState.getInt("endSection");
    }

    private void initializeSpinners() {
        startSpinner = populateSpinner(R.id.StartLocationSpinner, vm.startOptions);
        endSpinner = populateSpinner(R.id.DestinationSpinner, vm.endOptions);
        directionSpinner = populateSpinner(R.id.DirectionSpinner, vm.directionOptions);
        startSpinner.setOnItemSelectedListener(this);
        endSpinner.setOnItemSelectedListener(this);
        directionSpinner.setOnItemSelectedListener(this);
    }

    private Spinner populateSpinner(int spinnerId, List<String> contents) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contents);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        return spinner;
    }

    private void hideDirection() {
        directionSpinner.setVisibility(View.GONE);
        findViewById(R.id.DirectionLbl).setVisibility(View.GONE);
    }

    private int GetSelectedItemId(Spinner s) {
        return presenter.getSectionId(s.getSelectedItem().toString());
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        switch(parent.getId()) {
            case R.id.DirectionSpinner :
                vm.direction = directionSpinner.getSelectedItemPosition();
                break;
            case R.id.StartLocationSpinner :
                vm.startSection = GetSelectedItemId(startSpinner);
                presenter.startSectionChanged(vm);
                break;
            case R.id.DestinationSpinner :
                vm.endSection = GetSelectedItemId(endSpinner);
                vm.endSelectedIndex = endSpinner.getSelectedItemPosition();
                break;
        }
        presenter.optionsChanged(vm);
    }

    @Override
    public void onNothingSelected(AdapterView arg0) {
    }

    private void addListenerOnGoButton() {
        findViewById(R.id.GoBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                presenter.activitySubmit(vm);
            }
        });
    }

    // IRouteOptions methods. Invoked from presenter.
    public void invokeActivity(Map<String, Integer> intents, Class<?> activity) {
        Intent intent = new Intent(this, activity);
        for (Map.Entry<String, Integer> entry : intents.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        this.startActivity(intent);
    }

    public void endActivity() {
        finish();
    }

    public int getRouteSectionsFromIntent() {
        return getIntent().getExtras().getInt("routeSections");
    }

    public void refreshDestinationSpinner(RouteViewModel vm) {
        populateSpinner(endSpinner.getId(), vm.endOptions);
        endSpinner.setSelection(vm.endSelectedIndex);
    }

    public void refreshDistance(RouteViewModel vm) {
        TextView txtDistance = (TextView) findViewById(R.id.DistanceValue);
        txtDistance.setText(String.format(Locale.UK, "%.2f km (%.2f miles)", vm.distanceKm, vm.distanceMiles));
    }
}