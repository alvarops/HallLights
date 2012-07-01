/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.robertdiamond.light.R;
import com.robertdiamond.light.util.BaseInterface;

/**
 * @author Alvaro Pereda
 * 
 */
public class BaseImplement implements BaseInterface {
	boolean loading = false;
	ProgressDialog progressDialog;
	protected Object currentTaskLoading;
	protected boolean taskCanceled;
	private Context context;
	
	public BaseImplement(Context context) {
		this.context = context;
	}

	/**
	 * Show the loading progress dialog with dialog_progress_loading string
	 * 
	 * @param task
	 *            - AsyncTask<?, ?, ?>
	 */
	public void showLoading(AsyncTask<?, ?, ?> task) {
		showLoading(context.getString(R.string.dialog_progress_loading), task);
	}

	/**
	 * I create a cancelable spinner progress dialog
	 * 
	 * @param progressText
	 *            - title
	 * @param task
	 *            - AsyncTask<?, ?, ?> task that can be cancelled
	 */
	public void showLoading(String progressText,
			final AsyncTask<?, ?, ?> task) {
		if (!loading) {
			progressDialog = ProgressDialog.show(context, "", progressText, true);
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
    public void hideLoading(){
          progressDialog.dismiss();
          loading = false;
    }
}
