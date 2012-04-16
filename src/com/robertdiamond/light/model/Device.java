/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.digi.addp.AddpDevice;

/**
 * @author alvaroperedasancho
 * 
 */
public class Device implements Parcelable {

	AddpDevice device;
	String name;
	String ip;
	boolean selected;

	public Device() {

	}

	public Device(AddpDevice device) {
		this.device = device;
	}
	
	public Device(Parcel in) {
		boolean[] bool = new boolean[1];
		this.name = in.readString();
		this.ip = in.readString();
		
		in.readBooleanArray(bool);
		this.selected = bool[0];
	}

	/**
	 * @see android.os.Parcelable#describeContents()
	 */
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
		public Device createFromParcel(Parcel in) {
			return new Device(in);
		}

		public Device[] newArray(int size) {
			return new Device[size];
		}
	};

	/**
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(ip);
		dest.writeBooleanArray(new boolean []{selected});
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ip
	 */
	public String getIP() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIP(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
