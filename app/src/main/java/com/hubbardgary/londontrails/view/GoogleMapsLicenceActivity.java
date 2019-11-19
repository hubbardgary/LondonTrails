package com.hubbardgary.londontrails.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.presenter.GoogleMapsLicencePresenter;
import com.hubbardgary.londontrails.proxy.AndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public class GoogleMapsLicenceActivity extends Activity implements IGoogleMapsLicenceView {

    private GoogleMapsLicencePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_licence);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new GoogleMapsLicencePresenter(this, new AndroidFrameworkProxy());
        presenter.initializeView();
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
    public void setText(String text) {
        TextView textView = findViewById(R.id.licenceTextView);
        textView.setText(text);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void endActivity() {
        finish();
    }
}
