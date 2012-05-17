/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond - Jennifer Casavantes
 */
package com.robertdiamond.light.model;

import java.util.ArrayList;
import java.util.Enumeration;

import android.util.Log;

import com.digi.addp.AddpClient;
import com.digi.addp.AddpDevice;
import com.digi.addp.AddpDeviceList;
import com.digi.addp.DeviceFoundListener;
import com.digi.addp.MagicCookie;
import com.digi.addp.data.Message;
import com.digi.addp.data.MessageType;

/**
 * @author Alvaro Pereda
 *
 */
public class DiscoveryClient implements DeviceFoundListener {
	private static final String TAG = "DiscoveryClient";
	
	private AddpClient 				addpClient = new AddpClient();
	private DeviceFoundListener 	listener;

	public DiscoveryClient() {
		
	}
	
	/**
	 * Constructor with {@code DeviceFoundListener} callback.
	 * @param listener
	 */
	public DiscoveryClient(DeviceFoundListener listener) {
		super();
		this.listener = listener;
	}
	
	/**
	 * Discover sync
	 * 
	 * @return list of {@code Device}
	 */
	public ArrayList<Device> discover() {
		AddpClient addpClient = new AddpClient();
		ArrayList<Device> devices = new ArrayList<Device>();
		
		Log.d(TAG, "Discovering start");
		
		if (addpClient.SearchForDevices()) {
			AddpDeviceList deviceList = addpClient.getDevices();
			Enumeration<AddpDevice> e = deviceList.elements();
			
			while (e.hasMoreElements()) {
				AddpDevice	device = e.nextElement();
				Device		myDevice = new Device(device);
				
				// do something with the device here
				System.out.println(device.toString());
				
				// if device is not configured for DHCP, then turn it on
				// and reboot.
				if (device.getDHCP() == 0) {
					addpClient.setDHCP(device, true, "dbps");
					addpClient.rebootDevice(device, "dbps");
				}
				
				devices.add(myDevice);
			}
		} else {
			Log.i(TAG, "No device found.");
		}
		
		return devices;
	}
	
	/**
	 * Discover async. Uses the callback defined in constructor, or self.
	 */
	public void discoverAsync() {
		addpClient.SearchForDevicesAsync(this);
	}

	/**
	 * Cancels discovery
	 */
	public void cancel() {
		listener = null;
	}
	
	/** 
	 * @see com.digi.addp.DeviceFoundListener#onFound(java.lang.String, com.digi.addp.AddpDevice)
	 */
	public void onFound(String mac, AddpDevice device) {
		if (listener != null)
			listener.onFound(mac, device);
	}

	/** 
	 * @see com.digi.addp.DeviceFoundListener#onSearchComplete()
	 */
	public void onSearchComplete() {
		
		if (listener != null) {
			if (addpClient.getDevices() == null || addpClient.getDevices().size() == 0) {
				try {
					
					for (MagicCookie cookie: AddpClient.getMagicCookieList()) {
						AddpDevice device = new AddpDevice(new Message(cookie, MessageType.SET_ADDR));
						//listener.onFound("fa:15:ea:dd:4e:55", device);
					}
					
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}
				
			}
			listener.onSearchComplete();
		}
	}
}