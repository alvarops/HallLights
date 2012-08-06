/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.model;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.graphics.Color;

/**
 * @author Alvaro Pereda
 *
 */
@Root
public class Light implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2489529080608328786L;

	@Attribute
	private String node;
	
	@Element
	private int red;
	
	@Element
	private int green;
	
	@Element
	private int blue;
	
	@Element
	private int red2;
	
	@Element
	private int green2;
	
	@Element
	private int blue2;
	
	@Element
	private int speed;
	
	@Element
	private String nodeId;
	
	@Element
	private double lastActive;
	
	private Integer color;
	private Integer color2;
	

	/**
	 * @return the color
	 */
	public Integer getColor() {
		if (color == null) {
			color = Color.rgb(red, green, blue);
		}
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Integer color) {
		this.color = color;
		blue = Color.blue(color);
		green = Color.green(color);
		red = Color.red(color);
	}

	/**
	 * @return the color2
	 */
	public Integer getColor2() {
		if (color2 == null) {
			color2 = Color.rgb(red2, green2, blue2);
		}
		return color2;
	}

	/**
	 * @param color2 the color2 to set
	 */
	public void setColor2(Integer color2) {
		this.color2 = color2;
		blue2 = Color.blue(color);
		green2 = Color.green(color);
		red2 = Color.red(color);
	}

	/**
	 * @return the node
	 */
	public String getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return red;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(int red) {
		this.red = red;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(int green) {
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}

	/**
	 * @return the red2
	 */
	public int getRed2() {
		return red2;
	}

	/**
	 * @param red2 the red2 to set
	 */
	public void setRed2(int red2) {
		this.red2 = red2;
	}

	/**
	 * @return the green2
	 */
	public int getGreen2() {
		return green2;
	}

	/**
	 * @param green2 the green2 to set
	 */
	public void setGreen2(int green2) {
		this.green2 = green2;
	}

	/**
	 * @return the blue2
	 */
	public int getBlue2() {
		return blue2;
	}

	/**
	 * @param blue2 the blue2 to set
	 */
	public void setBlue2(int blue2) {
		this.blue2 = blue2;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the lastActive
	 */
	public double getLastActive() {
		return lastActive;
	}

	/**
	 * @param lastActive the lastActive to set
	 */
	public void setLastActive(double lastActive) {
		this.lastActive = lastActive;
	}
	
	
}
