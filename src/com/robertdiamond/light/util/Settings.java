/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Alvaro Pereda
 *
 */
public class Settings {
	public static final int DISCOVERY = 0, GET_SERVER = 1, SELECT_DEVICE = 2;
	
	public static final String PREFS_NAME = "LightsFile";
	public static final String IP = "IP";
	public static final String DEFAULT_DEVICE = "DefaultDevice";
	public static final String DEVICE_LIST = "DeviceList";

	/**
	 * Saves the Setting in the {@code SharedPreferences}
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveSetting(Context context, String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
	}
}
