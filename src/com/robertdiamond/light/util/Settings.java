/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author alvaroperedasancho
 * 
 */
public class Settings {
	public static final String PREFS_NAME = "LightsFile";
	public static final String IP = "IP";

	public static void saveSetting(Context context, String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
	}
}
