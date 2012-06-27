/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author alvaroperedasancho
 *
 */
@Root
public class Lights implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2636073283923484935L;

	@ElementList(inline=true)
	private List<Light> lights;

	/**
	 * @return the lights
	 */
	public List<Light> getLights() {
		return lights;
	}

	/**
	 * @param lights the lights to set
	 */
	public void setLights(List<Light> lights) {
		this.lights = lights;
	}
	
}
