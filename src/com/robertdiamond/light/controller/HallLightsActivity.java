/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.robertdiamond.light.R;
import com.robertdiamond.light.controller.tasks.QueryStatusTask;
import com.robertdiamond.light.controller.tasks.QueryStatusTask.QueryStatusListener;
import com.robertdiamond.light.controller.tasks.SetStatusTask;
import com.robertdiamond.light.controller.tasks.SetStatusTask.SetStatusListener;
import com.robertdiamond.light.model.Light;
import com.robertdiamond.light.model.Lights;
import com.robertdiamond.light.util.BaseInterface;
import com.robertdiamond.light.util.Settings;
import com.robertdiamond.light.view.ColorPickerDialog;
import com.robertdiamond.light.view.ColorPickerDialog.OnColorChangedListener;
import com.robertdiamond.light.view.LightsAdapter;

/**
 * @author Alvaro Pereda
 * 
 */
public class HallLightsActivity extends ListActivity implements QueryStatusListener, BaseInterface, SetStatusListener {
	private static final String TAG = "HallLightsActivity";

	private static final int MENU_DISCOVER = 0;
	private static final int MENU_QUERY_STATUS = 1;

	@SuppressWarnings("rawtypes")
	private AsyncTask statusTask;
	private BaseImplement baseInterface;
	private LightsAdapter adapter;

	private Lights lights;

	private View toggleButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list);

		this.baseInterface = new BaseImplement(this);
		this.adapter = new LightsAdapter(this, R.layout.main, R.id.node_id);
		setListAdapter(this.adapter);
		
		if (savedInstanceState != null && savedInstanceState.containsKey(Settings.LIGHTS)) {
		
			this.lights = (Lights) savedInstanceState.getSerializable(Settings.LIGHTS);
			updateAdapter(this.lights);
		
		} else {
			
			fetchStatus();
	
		}
		
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
		menu.add(0, MENU_QUERY_STATUS, 0, R.string.get_server_status);
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
		case MENU_QUERY_STATUS:
			fetchStatus();
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
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(Settings.LIGHTS, lights);
	}
	
	/**
	 * The user wants to turn the lights on / off 
	 * @param view
	 */
	public void onToggleLightsClicked(final View view) {
		int position = (Integer) view.getTag();
		Light light = adapter.getItem(position);
		
		this.toggleButton = view;
		
		view.setEnabled(false);
		statusTask = new SetStatusTask(this, getApplicationContext());
		showLoading(statusTask);
		((SetStatusTask)statusTask).execute(light);
	}

	/**
	 * @see com.robertdiamond.light.controller.tasks.SetStatusTask.SetStatusListener#onStatusReceived(boolean)
	 */
	public void onStatusReceived(boolean success) {
		if (this.toggleButton != null) {
			this.toggleButton.setEnabled(true);
			this.toggleButton = null;
		}
		
		if (success) {
			Toast.makeText(this, R.string.lights_updated, Toast.LENGTH_SHORT);
		} else {
			Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT);
			adapter.clear();
			fetchStatus();
		}
	}
	
	/**
	 * The user wants to change the light's color
	 * @param view
	 */
	public void onSelectColorClicked(final View view) {
		int position = (Integer) view.getTag();
		final Light light = adapter.getItem(position);
		Integer color = Color.rgb(light.getRed(), light.getGreen(),
				light.getBlue());
		
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, color,
				new OnAmbilWarnaListener() {

					public void onOk(AmbilWarnaDialog dialog, int color) {
						Log.d(TAG, "Changing color");
						light.setBlue(Color.blue(color));
						light.setGreen(Color.green(color));
						light.setRed(Color.red(color));
						adapter.notifyDataSetChanged();
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
		final Light light = (Light) view.getTag();
		
		ColorPickerDialog dialog = new ColorPickerDialog(getApplicationContext(), new OnColorChangedListener() {
			
			public void colorChanged(int color) {
				light.setBlue(Color.blue(color));
				light.setRed(Color.red(color));
				light.setGreen(Color.green(color));
				adapter.notifyDataSetChanged();
			}
			
		}, Color.rgb(light.getRed(), light.getGreen(),light.getBlue()));
		dialog.show();
	}
	
	public void onApply(View view) {
		
	}

	/* (non-Javadoc)
	 * @see com.robertdiamond.light.controller.tasks.QueryStatusTask.QueryStatusListener#onStatusReceived(com.robertdiamond.light.model.Lights)
	 */
	public void onStatusReceived(Lights lights) {
		hideLoading();
		this.lights = lights;
		
		updateAdapter(lights);
		
	}

	/**
	 * @param lights
	 */
	private void updateAdapter(Lights lights) {
		for (Light light:lights.getLights()) {
			adapter.add(light);
		}
	}

	/**
	 * Starts the QueryStatus AsyncTask
	 */
	private void fetchStatus() {
		statusTask = new QueryStatusTask(this, this);
		showLoading(statusTask);
		((QueryStatusTask)statusTask).execute();
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