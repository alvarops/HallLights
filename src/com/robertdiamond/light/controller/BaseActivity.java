/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.controller;

import com.robertdiamond.light.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * @author Alvaro Pereda
 * 
 */
public class BaseActivity extends Activity {
	boolean loading = false;
	ProgressDialog progressDialog;
	protected Object currentTaskLoading;
	protected boolean taskCanceled;

	/**
	 * Show the loading progress dialog with dialog_progress_loading string
	 * 
	 * @param task
	 *            - AsyncTask<?, ?, ?>
	 */
	protected void showLoading(AsyncTask<?, ?, ?> task) {
		showLoading(getString(R.string.dialog_progress_loading), task);
	}

	/**
	 * I create a cancelable spinner progress dialog
	 * 
	 * @param progressText
	 *            - title
	 * @param task
	 *            - AsyncTask<?, ?, ?> task that can be cancelled
	 */
	protected void showLoading(String progressText,
			final AsyncTask<?, ?, ?> task) {
		if (!loading) {
			progressDialog = ProgressDialog.show(this, "", progressText, true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

						public void onCancel(DialogInterface dialog) {
							if (task != null && loading) {
								task.cancel(true);
								loading = false;
								taskCanceled = true;
								hideLoading();
							}

						}

					});
			loading = true;
		}
	}
	
	/**
     * Hide the current AlertDialog(progressDialog)
     */
    protected void hideLoading(){
          progressDialog.dismiss();
          loading = false;
    }
}
