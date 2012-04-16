/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import com.robertdiamond.light.model.Device;
import com.robertdiamond.light.model.DiscoveryClient;
import com.robertdiamond.light.util.Settings;

/**
 * @author alvaroperedasancho
 * 
 */
public class DiscoveryActivity extends Activity {

	@Override
	public void onStart() {
		super.onStart();

		ArrayList<Device> devices = new DiscoveryClient().discover();

		if (devices == null || devices.isEmpty()) {

			askDefaultServerIP();

		} else if (devices.size() == 1) {

			Intent intent = new Intent();
			intent.putExtra(Settings.IP, devices.get(0).getIP());
			setResult(0, intent);

		} else {

		}
	}

	/**
	 * Gets the URL of the default server
	 */
	private void askDefaultServerIP() {
		// ask default webserverIP
		setResult(0, new Intent());
		finish();
	}
}
