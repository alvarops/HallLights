package com.robertdiamond.light;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;

import com.digi.addp.AddpClient;
import com.digi.addp.AddpDevice;
import com.digi.addp.AddpDeviceList;

public class HallLightsActivity extends Activity {
	Logger log = Logger.getLogger(HallLightsActivity.class.getName());
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		AddpClient addpClient = new AddpClient();

		if (addpClient.SearchForDevices()) {
			AddpDeviceList deviceList = addpClient.getDevices();

			Enumeration<AddpDevice> e = deviceList.elements();
			while (e.hasMoreElements()) {
				AddpDevice device = e.nextElement();

				// do something with the device here
				log.log(Level.INFO, device.toString());

				// if device is not configured for DHCP, then turn it on and
				// reboot.
				if (device.getDHCP() == 0) {
					addpClient.setDHCP(device, true, "dbps");
					addpClient.rebootDevice(device, "dbps");
				}
			}
		} else {
			log.log(Level.INFO, "No device found, trying webclient");
			
		}
	}
}