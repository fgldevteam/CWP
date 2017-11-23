package com.fgl.cwp.model.sumtotal;

import java.io.Serializable;

/**
 * Course
 * @author jmbarga
 * 
 * @hibernate.cache usage="read-only"
 * 
 * @hibernate.class
 * 			table="ActLabel" * 			
 *          lazy="false"
 *          mutable="false"
 *          
 * @hibernate.query
 *          name="allCoursesLabel"
 *          query="select l from Label c where l.ActLabel_PK = ? order by l.labelName"
 *
 */

public class CourseLabel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	/**
	 * @param id
	 */	
	public CourseLabel(int id){
		this.setId(id);
	}
	
	/**
	 * 
	 */
	public CourseLabel(){}
	/**
	 * 
	 * @param id The labeid to set
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * 
	 * @return Returns the labelid.
	 * @hibernate.id column = "ActLabel_PK" generator-class = "assigned"
	 */
	public int getId(){
		return this.id;
	}
	
}
