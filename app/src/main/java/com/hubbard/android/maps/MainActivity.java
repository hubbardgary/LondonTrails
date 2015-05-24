package com.hubbard.android.maps;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button button;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.CapitalRingBtn)).setText(GlobalObjects.getButtonText("Capital Ring", "approx 122km (76 miles)"));
        ((Button)findViewById(R.id.LondonLoopBtn)).setText(GlobalObjects.getButtonText("London Loop", "approx 240km (149 miles)"));

        addListenerOnButton();

        res = getResources();

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
    }

    private void addListenerOnButton() {
        final Context context = this;

        // Add Capital Ring listener
        button = (Button) findViewById(R.id.CapitalRingBtn);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, RouteOptions.class);
                intent.putExtra("routeSections", R.array.capital_ring_sections);
                startActivity(intent);
            }
        });

        // Add London Loop listener
        button = (Button) findViewById(R.id.LondonLoopBtn);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, RouteOptions.class);
                intent.putExtra("routeSections", R.array.london_loop_sections);
                startActivity(intent);
            }
        });
    }

}
