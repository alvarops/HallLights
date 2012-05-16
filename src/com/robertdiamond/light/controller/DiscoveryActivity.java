/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.digi.addp.AddpDevice;
import com.digi.addp.DeviceFoundListener;
import com.robertdiamond.light.R;
import com.robertdiamond.light.model.Device;
import com.robertdiamond.light.model.DiscoveryClient;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 *
 */
public class DiscoveryActivity extends Activity implements DeviceFoundListener {
	public static final String TAG = "DiscoveryActivity";
	
	ArrayList<Device>	devices = new ArrayList<Device>();
	DiscoveryClient		client;
	ProgressDialog		dialog;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();

		dialog = ProgressDialog.show(this, getString(R.string.discovering), getString(R.string.please_wait_discovering), true);
		
		client = new DiscoveryClient(this);
		client.discoverAsync();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setResult(resultCode, data);
		finish();
	}

	@Override
	public void onStop() {
		super.onStop();
		
		if (dialog != null) {
			dialog.dismiss();
		}
		
		if (client != null) {
			client.cancel();
			client = null;
		}
	}
	
	/**
	 * @see com.digi.addp.DeviceFoundListener#onFound(java.lang.String, com.digi.addp.AddpDevice)
	 */
	public void onFound(String mac, AddpDevice device) {
		Log.i(TAG, String.format("Device found: %s .", device.toString()));
		devices.add(new Device(device));
	}

	/**
	 * @see com.digi.addp.DeviceFoundListener#onSearchComplete()
	 */
	public void onSearchComplete() {
		
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		
		if (devices == null || devices.isEmpty()) {
			
			Log.d(TAG, "Search Complete. No device found.");
				
			Intent intent = new Intent(getApplicationContext(), AskDefaultServerActivity.class);
			startActivityForResult(intent, Settings.GET_SERVER);
		
		} else if (devices.size() == 1) {
		
			Log.d(TAG, "Search Complete. One device found. Make default.");
			
			Intent resp = new Intent();
			resp.putExtra(Settings.DEFAULT_DEVICE, devices.get(0));
			setResult(RESULT_OK, resp);
			finish();
		
		} else {
		
			Log.d(TAG, "Search Complete. Multiple devices found. Select default.");
			
			Intent intent = new Intent(getApplicationContext(), SelectDeviceActivity.class);
			intent.putParcelableArrayListExtra(Settings.DEVICE_LIST, devices);
			startActivityForResult(intent, Settings.SELECT_DEVICE);
			
		}
	}
}
