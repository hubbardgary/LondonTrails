package com.hubbardgary.londontrails.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.presenter.GoogleMapsLicencePresenter;
import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public class GoogleMapsLicenceActivity extends Activity implements IGoogleMapsLicenceView {

    private GoogleMapsLicencePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_licence);
        presenter = new GoogleMapsLicencePresenter(this);
        presenter.initializeView();
    }

    public void setText(String text) {
        TextView textView = (TextView)findViewById(R.id.licenceTextView);
        textView.setText(text);
    }

    public Context getContext() {
        return getApplicationContext();
    }
}
