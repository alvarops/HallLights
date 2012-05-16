/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.digi.addp.AddpDevice;
import com.digi.addp.DeviceFoundListener;
import com.robertdiamond.light.model.Device;
import com.robertdiamond.light.model.DiscoveryClient;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 *
 */
public class DiscoveryActivity extends Activity implements DeviceFoundListener {
	public static final String TAG = "DiscoveryActivity";
	
	ArrayList<Device> devices = new ArrayList<Device>();
	
	@Override
	public void onStart() {
		super.onStart();

		new DiscoveryClient(this).discoverAsync();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setResult(resultCode, data);
		finish();
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
		
		if (devices == null || devices.isEmpty()) {
			
			Intent intent = new Intent(getApplicationContext(), AskDefaultServerActivity.class);
			startActivityForResult(intent, Settings.GET_SERVER);
		
		} else if (devices.size() == 1) {
		
			Intent resp = new Intent();
			resp.putExtra(Settings.IP, devices.get(0).getIP());
			setResult(RESULT_OK, resp);
			finish();
		
		} else {
		
			Intent intent = new Intent(getApplicationContext(), ChooseDeviceActivity.class);
			startActivityForResult(intent, Settings.CHOOSE_DEVICE);
			
		}
	}
}
