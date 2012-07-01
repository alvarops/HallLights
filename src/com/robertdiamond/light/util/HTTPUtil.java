/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
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

	public static <T>Object fetchURL(String path, Class<T> clazz) throws Exception {
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
			urlConnection.disconnect();
		}
		
	}
}
