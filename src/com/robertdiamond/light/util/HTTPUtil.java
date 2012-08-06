/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * @author Alvaro Pereda
 * 
 */
public class HTTPUtil {

	/**
	 * @see http 
	 *      ://android-developers.blogspot.ie/2011/09/androids-http-clients.html
	 */
	public void enableHttpResponseCache(File cache) {
		try {
			long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
			File httpCacheDir = new File(cache, "http");
			Class.forName("android.net.http.HttpResponseCache")
					.getMethod("install", File.class, long.class)
					.invoke(null, httpCacheDir, httpCacheSize);
		} catch (Exception httpResponseCacheNotAvailable) {
		}
	}

	public static <T> Object fetchURL(String path, Class<T> clazz)
			throws Exception {
		HttpURLConnection urlConnection = null;

		try {
			URL url;
			Serializer serializer;
			InputStream in;

			url = new URL(path);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			serializer = new Persister();

			return serializer.read(clazz, in);

		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean getURL(URL url) throws Exception {
		HttpURLConnection urlConnection = null;

		try {
			urlConnection = (HttpURLConnection) url.openConnection();

			return (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK);

		} finally {
			urlConnection.disconnect();
		}
	}

	/**
	 * 
	 * @param path
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static String postURL(String path, String parameters)
			throws Exception {
		HttpURLConnection urlConnection = null;

		try {
			URL url;
			
			url = new URL(path);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			urlConnection.setRequestProperty("Content-Length",
					"" + Integer.toString(parameters.getBytes().length));
			urlConnection.setRequestProperty("Content-Language", "en-US");

			urlConnection.setUseCaches(false);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					urlConnection.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = urlConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} finally {
			urlConnection.disconnect();
		}
	}
}
