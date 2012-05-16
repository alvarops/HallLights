package com.robertdiamond.light.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.robertdiamond.light.R;
import com.robertdiamond.light.util.Settings;
import com.robertdiamond.light.view.ColorPickerDialog;
import com.robertdiamond.light.view.ColorPickerDialog.OnColorChangedListener;

public class HallLightsActivity extends Activity implements OnColorChangedListener {
	private static final String TAG = "HallLightsActivity";

	private static final int MENU_DISCOVER = 0;
	private static final int MENU_ITEM_1 = 1;

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

	/**
	 * Add menu items
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_DISCOVER, 0, R.string.discover);
		menu.add(0, MENU_ITEM_1, 0, "Menu Item 1");
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
		case MENU_DISCOVER:
			Intent intent = new Intent(getApplicationContext(), DiscoveryActivity.class);
			startActivityForResult(intent, 0);
			break;
		case MENU_ITEM_1:
			// put your code here
			break;
		default:
			// put your code here
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String serverIP;

		serverIP = data.getStringExtra(Settings.IP);

		Settings.saveSetting(getApplicationContext(), Settings.IP, serverIP);
	}

	public void onSelectColorClicked(View view) {
		ColorPickerDialog dialog = new ColorPickerDialog(this, this, Color.RED);
		dialog.show();
	}

	/** 
	 * @see com.robertdiamond.light.view.ColorPickerDialog.OnColorChangedListener#colorChanged(int)
	 */
	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		
	}
}