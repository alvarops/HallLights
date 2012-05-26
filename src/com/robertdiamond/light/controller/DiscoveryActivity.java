/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

	private static final int DIALOG_CHOOSE = 0;
	private static final int DIALOG_INPUT = 1;

	ArrayList<Device> devices = new ArrayList<Device>();
	DiscoveryClient client;
	ProgressDialog dialog;
	CharSequence[] items;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

		dialog = ProgressDialog.show(this, getString(R.string.discovering),
				getString(R.string.please_wait_discovering), true);
		client = new DiscoveryClient(this);
//		client.discoverAsync();
		
		//devices = client.discover();
		storeDevices();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setResult(resultCode, data);
		finish();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
		case DIALOG_CHOOSE:
			return buildDeviceChooser();
		case DIALOG_INPUT:
			return buildDeviceInput();
		}

		return null;
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
	 * @see com.digi.addp.DeviceFoundListener#onFound(java.lang.String,
	 *      com.digi.addp.AddpDevice)
	 */
	public void onFound(String mac, AddpDevice device) {
		Log.i(TAG, String.format("Device found: %s .", device.toString()));
		devices.add(new Device(device));
	}

	public void serverChanged(String ip) {
		Intent resp = new Intent();
		resp.putExtra(Settings.DEFAULT_DEVICE, ip);
		setResult(RESULT_OK, resp);
		finish();
	}

	/**
	 * @see com.digi.addp.DeviceFoundListener#onSearchComplete()
	 */
	public void onSearchComplete() {

		storeDevices();
	}

	/**
	 * 
	 */
	private void storeDevices() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

		if (devices == null || devices.isEmpty()) {

			Log.d(TAG, "Search Complete. No device found.");

			showDialog(DIALOG_INPUT);

		} else if (devices.size() == 1) {

			Log.d(TAG, "Search Complete. One device found. Make default.");

			Intent resp = new Intent();
			resp.putExtra(Settings.DEFAULT_DEVICE, devices.get(0));
			setResult(RESULT_OK, resp);
			finish();

		} else {

			Log.d(TAG,
					"Search Complete. Multiple devices found. Select default.");

			showDialog(DIALOG_CHOOSE);
		}
	}
	
	/**
	 * @return
	 */
	private Dialog buildDeviceChooser() {
		Builder builder = new AlertDialog.Builder(this);
		items = new CharSequence[devices.size()];
		
		for (Device device: devices) {
			items[items.length] = device.getName();
		}
		
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.choose_a_device);
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getBaseContext(), getString(R.string.server_name_selected, items[which]), Toast.LENGTH_SHORT);
				dialog.dismiss();
			}
		});
		return builder.create();
	}

	/**
	 * @return
	 */
	private Dialog buildDeviceInput() {
		Context mContext;
		Dialog dialog;
		
		mContext = this;
		
		dialog = new Dialog(mContext) {
			@Override
			public void onBackPressed() {
				TextView ip;
				TextView port;
				String result;
				
				ip = (TextView)findViewById(R.id.server_ip);
				port = (TextView)findViewById(R.id.server_port);
								
				result = String.format("%s:%s", ip.getText(), port.getText());
				
				Log.d(TAG, result);
				
				Toast.makeText(
						getBaseContext(),
						getString(R.string.server_name_selected, result), Toast.LENGTH_SHORT);
				
				super.onBackPressed();
			}
		};
		
		dialog.setContentView(R.layout.ask_default_server);
		dialog.setTitle("Input default server");

		return dialog;
	}

}
