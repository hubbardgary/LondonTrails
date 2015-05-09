package com.hubbard.android.maps;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
		
		 // Getting Google Play availability status
	    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
	    if(status != ConnectionResult.SUCCESS) {
	    	// Google Play Services are not available
	        int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	        dialog.show();
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {
		final Context context = this;

        // Add Capital Ring listener
		button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, CapitalRingOptions1.class);
				startActivity(intent);
			}
		});

        // Add London Loop listener
        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, LondonLoopOptions.class);
                startActivity(intent);
            }
        });
	}

}
