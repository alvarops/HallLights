/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.util;

import android.os.AsyncTask;

/**
 * @author Alvaro Pereda
 *
 */
public interface BaseInterface {
	
	public void showLoading(AsyncTask<?, ?, ?> task);
	public void showLoading(String progressText, final AsyncTask<?, ?, ?> task);
	public void hideLoading();
}
