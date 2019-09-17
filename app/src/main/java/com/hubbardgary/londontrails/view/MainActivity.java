package com.hubbardgary.londontrails.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.view.interfaces.IMainView;
import com.hubbardgary.londontrails.viewmodel.ButtonViewModel;
import com.hubbardgary.londontrails.presenter.MainPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements IMainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        presenter = new MainPresenter(this, new GlobalObjects());
        presenter.initializeView();
    }

    private void addOnClickListener(final Button button) {
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                presenter.routeButtonClicked(button.getId());
            }
        });
    }

    private Button addButton(ButtonViewModel buttonVm) {
        Button button = new Button(this);
        button.setId(buttonVm.id);
        button.setText(buttonVm.label);
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background));
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        button.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 25;
        LinearLayout layout = (LinearLayout)findViewById(R.id.MainLayout);
        layout.addView(button, params);
        return button;
    }

    public void displayButtons(List<ButtonViewModel> buttons) {
        for(ButtonViewModel buttonVm : buttons) {
            Button button = addButton(buttonVm);
            addOnClickListener(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    public void checkGooglePlayAvailability() {
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int status = googleAPI.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(status)) {
                // Google Play Services might need installing/updating, so prompt user to take appropriate action
                Dialog dialog = googleAPI.getErrorDialog(this, status, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.show();
            } else {
                // Google Play Services unavailable
                Toast.makeText(this, "Error: Google Maps is not available on this device.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void invokeActivity(HashMap<String, Integer> intents, Class<?> activity) {
        Intent intent = new Intent(this, activity);
        for (Map.Entry<String, Integer> entry : intents.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        this.startActivity(intent);
    }

    public void endActivity() {
        finish();
    }
}