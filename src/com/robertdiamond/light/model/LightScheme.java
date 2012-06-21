/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alvaro Pereda
 * 
 */
public class LightScheme implements Parcelable {
	private Boolean lightsOn;
	private int color;
	
	/**
	 * Parcel constructor
	 * @param in
	 */
	private LightScheme(Parcel in) {
		lightsOn = (Boolean) in.readValue(Boolean.class.getClassLoader());
		color = in.readInt();
	}

	/**
	 * Default constructor
	 */
	public LightScheme() {
		
	}

	/**
	 * @param lightsOn
	 *            the lightsOn to set
	 */
	public void setLightsOn(Boolean lightsOn) {
		this.lightsOn = lightsOn;
	}

	/**
	 * @return the lightsOn
	 */
	public Boolean getLightsOn() {
		return lightsOn;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * CREATOR see android.os.Parcelable
	 */
	public static final Parcelable.Creator<LightScheme> CREATOR = new Parcelable.Creator<LightScheme>() {
		public LightScheme createFromParcel(Parcel in) {
			return new LightScheme(in);
		}

		public LightScheme[] newArray(int size) {
			return new LightScheme[size];
		}
	};
	
	/**
	 * @see android.os.Parcelable#describeContents()
	 */
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(lightsOn);
		dest.writeInt(color);
	}

}
