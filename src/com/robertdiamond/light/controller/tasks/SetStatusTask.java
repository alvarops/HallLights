/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.controller.tasks;

import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.robertdiamond.light.model.Light;
import com.robertdiamond.light.util.HTTPUtil;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 * 
 */
public class SetStatusTask extends AsyncTask<Light, Void, Boolean> {

	private static final String TAG = "SetStatusTask";
	private static final String URL = Settings.BASE_URL + Settings.BASE_URL_SET;
	private SetStatusListener listener;
	private Context context;

	public SetStatusTask(SetStatusListener listener, Context context) {
		this.listener = listener;
		this.context = context;
	}

	/**
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Boolean doInBackground(Light... params) {
		Light light = params[0];
		String urlParams = String.format(Settings.QUERY_SET, light.getRed(),
				light.getGreen(), light.getBlue(), light.getSpeed(),
				light.getNode());
		try {
			URL url;
			
			url = new URL("http", "diamond.homelinux.com", 2525, Settings.BASE_URL_SET + "?" + urlParams);
			
			return HTTPUtil.getURL(url);
			
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (listener != null) {
			listener.onStatusReceived(result);
		}
	};

	public interface SetStatusListener {
		public void onStatusReceived(boolean result);
	}
}
