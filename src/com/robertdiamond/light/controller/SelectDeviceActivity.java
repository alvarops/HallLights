/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.robertdiamond.light.R;
import com.robertdiamond.light.model.Device;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 *
 */
public class SelectDeviceActivity extends Activity implements
		OnItemSelectedListener {

	private ArrayList<Device> devices;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ddlistlayout);
		
		devices = getIntent().getParcelableArrayListExtra(Settings.DEVICE_LIST);
		
		if (devices == null || devices.isEmpty()) {
	
			Intent resp = new Intent();
			setResult(RESULT_CANCELED, resp);
			finish();
		
		} else {
			
			ArrayAdapter<Device> adapter = new ArrayAdapter<Device>(this,
					android.R.layout.simple_spinner_item, devices);
	
			Spinner spinner = (Spinner) findViewById(R.id.spinner);
	
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setPrompt(getString(R.string.sample_ddlist_pick_item));
			spinner.setOnItemSelectedListener(this);

		}
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		Intent resp = new Intent();
		resp.putExtra(Settings.DEFAULT_DEVICE, devices.get(arg2));
		setResult(RESULT_OK, resp);
		
		Toast.makeText(
				arg0.getContext(),
				getString(R.string.sample_ddlist_select)
						+ arg0.getItemAtPosition(arg2).toString(),
				Toast.LENGTH_SHORT).show();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// do nothing
	}
}