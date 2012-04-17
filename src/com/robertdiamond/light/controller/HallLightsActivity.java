/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.robertdiamond.light.R;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 *
 */
public class HallLightsActivity extends Activity {
	private static final String TAG = "HallLightsActivity";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "starting");
		
		Intent intent = new Intent(getApplicationContext(), DiscoveryActivity.class);
		startActivityForResult(intent, Settings.DISCOVERY);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Settings.DISCOVERY:
			if (data != null) {
				Settings.saveSetting(getApplicationContext(), Settings.IP, data.getStringExtra(Settings.IP));
			}
		break;
		default:
			
		}
	}

	
	
}