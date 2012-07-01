/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.robertdiamond.light.R;
import com.robertdiamond.light.controller.tasks.QueryStatusTask;
import com.robertdiamond.light.controller.tasks.QueryStatusTask.QueryStatusListener;
import com.robertdiamond.light.model.LightScheme;
import com.robertdiamond.light.model.Lights;
import com.robertdiamond.light.util.BaseInterface;
import com.robertdiamond.light.util.Settings;
import com.robertdiamond.light.view.ColorPickerDialog;
import com.robertdiamond.light.view.ColorPickerDialog.OnColorChangedListener;

/**
 * @author Alvaro Pereda
 * 
 */
public class HallLightsActivity extends Activity implements QueryStatusListener, BaseInterface {
	private static final String TAG = "HallLightsActivity";

	private static final int MENU_DISCOVER = 0;
	private static final int MENU_ITEM_1 = 1;

	private LightScheme lightScheme;

	private QueryStatusTask statusTask;

	private BaseImplement baseInterface;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.lightScheme = new LightScheme();

		OnCheckedChangeListener radioGroupListener = new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				HallLightsActivity.this.lightScheme
						.setLightsOn(checkedId == R.id.rb_lights_on);
			}

		};

		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_all_lights);
		radioGroup.setOnCheckedChangeListener(radioGroupListener);
		this.baseInterface = new BaseImplement(getBaseContext());
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
			Intent intent = new Intent(this, DiscoveryActivity.class);
			startActivityForResult(intent, Settings.DISCOVERY);
			break;
		case MENU_ITEM_1:
			statusTask = new QueryStatusTask(this, getApplicationContext());
			showLoading(statusTask);
			
			break;
		default:

		}
		return false;
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
	 * The user wants to change the light's color
	 * @param view
	 */
	public void onSelectColorClicked(View view) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, Color.RED,
				new OnAmbilWarnaListener() {

					public void onOk(AmbilWarnaDialog dialog, int color) {
						HallLightsActivity.this.lightScheme.setColor(color);
					}

					public void onCancel(AmbilWarnaDialog dialog) {
						// cancel was selected by the user
					}
				});

		dialog.show();
	}
	
	/**
	 * Old color selector Dialog
	 * @param view
	 */
	public void onRoundSelectColorClicked(View view) {
		ColorPickerDialog dialog = new ColorPickerDialog(getApplicationContext(), new OnColorChangedListener() {
			
			public void colorChanged(int color) {
				HallLightsActivity.this.lightScheme.setColor(color);
			}
			
		}, Color.RED);
		dialog.show();
	}
	
	public void onApply(View view) {
		
	}

	/* (non-Javadoc)
	 * @see com.robertdiamond.light.controller.tasks.QueryStatusTask.QueryStatusListener#onStatusReceived(com.robertdiamond.light.model.Lights)
	 */
	public void onStatusReceived(Lights lights) {
		hideLoading();
		
	}

	/* (non-Javadoc)
	 * @see com.robertdiamond.light.util.BaseInterface#showLoading(android.os.AsyncTask)
	 */
	public void showLoading(AsyncTask<?, ?, ?> task) {
		baseInterface.showLoading(task);
	}

	/* (non-Javadoc)
	 * @see com.robertdiamond.light.util.BaseInterface#showLoading(java.lang.String, android.os.AsyncTask)
	 */
	public void showLoading(String progressText, AsyncTask<?, ?, ?> task) {
		baseInterface.showLoading(progressText, task);
	}

	/* (non-Javadoc)
	 * @see com.robertdiamond.light.util.BaseInterface#hideLoading()
	 */
	public void hideLoading() {
		baseInterface.hideLoading();
	}

}