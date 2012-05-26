package com.robertdiamond.light;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DiscoveryService extends Service {
	/**
	 * @see android.app.Service#onBind(Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Put your code here
		return null;
	}

	/**
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Put your code here
	}

	/**
	 * @see android.app.Service#onStart(Intent,int)
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Put your code here
	}
}
