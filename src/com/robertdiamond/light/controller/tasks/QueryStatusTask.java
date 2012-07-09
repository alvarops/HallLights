/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.controller.tasks;

import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.robertdiamond.light.model.Light;
import com.robertdiamond.light.model.Lights;
import com.robertdiamond.light.util.HTTPUtil;
import com.robertdiamond.light.util.Settings;

/**
 * @author Alvaro Pereda
 *
 */
public class QueryStatusTask extends AsyncTask<URL, Void, Lights> {
	private static final String TAG = "QueryStatusTask";
	
	private QueryStatusListener listener;
	private Context context;

	public QueryStatusTask(QueryStatusListener listener, Context context) {
		this.listener = listener;
		this.context = context;
	}
	/**
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Lights doInBackground(URL... params) {
		Lights lights = null;
		Log.d(TAG, "Start doInBackground");
		try {
			lights = (Lights) HTTPUtil.fetchURL(new StringBuffer(Settings.BASE_URL).append(Settings.BASE_URL_QUERY).toString(), Lights.class);
			
			for (Light light: lights.getLights()) {
				System.out.println(light.getNodeId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lights;
	}
	
	@Override
	protected void onPostExecute(Lights result) {
		listener.onStatusReceived(result);
	};

	public interface QueryStatusListener{
		public void onStatusReceived(Lights lights);
	}
}
