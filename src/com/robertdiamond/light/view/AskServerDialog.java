/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.robertdiamond.light.R;

/**
 * @author Alvaro
 *
 */
public class AskServerDialog extends Dialog {

	public interface OnServerChangedListener {
        void serverChanged(String ip);
    }
	
	OnServerChangedListener listener;

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public AskServerDialog(Context context, OnServerChangedListener listener) {
		super(context);
		
		this.listener = listener;
	}

	/** 
	 * @see android.app.Dialog#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		listener.serverChanged(result);
	}

	

}
