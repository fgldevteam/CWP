package com.fgl.cwp.model;

import java.io.Serializable;

/**
 * @author gdelve
 * 
 * @hibernate.class
 * 		table = "cap"
 * 		proxy = "com.fgl.cwp.model.Cap"
 * 
 * @hibernate.query
 * 		name = "getAllCaps"
 * 		query = "SELECT cap
 * 				FROM Cap cap
 * 				ORDER BY cap.capId"
 * 
 * @hibernate.query
 * 		name = "getCapsById"
 * 		query = "SELECT cap
 * 				FROM Cap cap
 * 				WHERE cap.capId IN (:capIds)
 * 				ORDER by cap.capId"
 * 		
 */
public class Cap implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer capId;
	private String capName;
	
	/**
	 * @hibernate.id
	 * 		column = "cap_id"
	 * 		generator-class = "native"
	 * 
	 * @return The cap ID
	 */
	public Integer getCapId() {
		return capId;
	}
	
	public void setCapId(Integer capId) {
		this.capId = capId;
	}
	
	/**
	 * @hibernate.property
	 * 		column = "cap_name"
	 * 
	 * @return the descriptive name for the cap
	 */
	public String getCapName() {
		return capName;
	}
	
	public void setCapName(String capName) {
		this.capName = capName;
	}
}
