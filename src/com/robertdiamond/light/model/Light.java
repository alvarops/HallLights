/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.model;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

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
	private int speed;
	
	@Element
	private String nodeId;
	
	@Element
	private double lastActive;

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
