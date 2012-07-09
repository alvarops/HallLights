/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.util;

import android.graphics.Color;

/**
 * @author Alvaro Pereda
 *
 */
public class ColorUtil {

	/**
	 * @see {http://stackoverflow.com/questions/946544/good-text-foreground-color-for-a-given-background-color} 
	 * @param color
	 * @return white or black depending on the brightness of the color
	 */
	public static int getBestContrast(int color) {
		return isDark(color) ? Color.WHITE : Color.BLACK;
	}
	
	/**
	 * @see {http://stackoverflow.com/questions/946544/good-text-foreground-color-for-a-given-background-color} 
	 * @param color
	 * @return light or dark gray depending on the brightness of the color
	 */
	public static int getBestGrayContrast(int color) {
		return isDark(color) ? Color.LTGRAY : Color.DKGRAY;
	}
	
	public static boolean isDark(int color) {
		int valuedColor = Color.red(color) * 299 + Color.green(color) * 587 + Color.blue(color) * 114;
		
		return valuedColor < 186000;
	}
}
