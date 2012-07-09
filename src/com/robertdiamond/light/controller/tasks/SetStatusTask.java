/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.controller.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.robertdiamond.light.model.Light;
import com.robertdiamond.light.util.HTTPUtil;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 *
 */
public class SetStatusTask extends AsyncTask<Light, Void, Boolean> {

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
		String url = String.format(new StringBuffer(Settings.BASE_URL).append(Settings.BASE_URL_SET).toString(), 
				light.getRed(), light.getGreen(), light.getBlue(), light.getSpeed(), light.getNodeId());
		try {
			
			return HTTPUtil.getURL(url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		listener.onStatusReceived(result);
	};

	public interface SetStatusListener{
		public void onStatusReceived(boolean result);
	}
}
