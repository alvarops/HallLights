package com.robertdiamond.light.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.robertdiamond.light.R;
import com.robertdiamond.light.util.Settings;

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
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String serverIP;
		
		serverIP = data.getStringExtra(Settings.IP);
		
		Settings.saveSetting(getApplicationContext(), Settings.IP, serverIP);
	}
}