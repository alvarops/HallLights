/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.robertdiamond.light.R;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 * 
 */
public class HallLightsActivity extends Activity {
	private static final String TAG = "HallLightsActivity";
	private static final int MENU_ITEM_0 = 0;
	private static final int MENU_START_DISCOVERING = 1;


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

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Settings.DISCOVERY:
			if (data != null) {
				Settings.saveSetting(getApplicationContext(), Settings.IP,
						data.getStringExtra(Settings.IP));
			}
			break;
		default:

		}
	}
	
	/**
	 * Add menu items
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ITEM_0, 0, "Menu Item 0");
		menu.add(0, MENU_START_DISCOVERING, 0, R.string.start_discover);
		return true;
	}

	/**
	 * Define menu action
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_0:
			// put your code here
			break;
		case MENU_START_DISCOVERING:
			launchDiscovery();
			break;
		default:
			// put your code here
		}
		return false;
	}

	private void launchDiscovery() {
		Intent intent = new Intent(getApplicationContext(),
				DiscoveryActivity.class);
		startActivityForResult(intent, Settings.DISCOVERY);
	}


}