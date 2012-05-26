/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.controller;

import com.robertdiamond.light.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

/**
 * @author Alvaro Pereda
 *
 */
public class AskDefaultServerActivity extends Activity {
	public static final String TAG = "AskDefaultServerActivity";
	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ask_default_server);
		
	}
	
	/**
	 * @see android.app.Dialog#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		TextView ip;
		TextView port;
		String result;
		
		super.onBackPressed();
		ip = (TextView)findViewById(R.id.server_ip);
		port = (TextView)findViewById(R.id.server_port);
		result = String.format("%s:%s", ip.getText(), port.getText());
		
		Log.d(TAG, result);
	}

}
