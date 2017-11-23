package com.fgl.cwp.model.sumtotal;

import java.io.Serializable;
import com.fgl.cwp.model.sumtotal.CourseLabel;
/**
 * Course
 * @author jmbarga
 * 
 * @hibernate.cache usage="read-only"
 * 
 * @hibernate.class
 * 			table="TBL_TMX_Activity" * 			
 *          lazy="false"
 *          mutable="false"
 *          
 * @hibernate.query
 *          name="allCourses"
 *          query="from Course c where c.levelVal = 0 order by c.courseName"
 *       
 * 
 *
 */

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int courseId;
	private String courseName="";
	private CourseLabel courseLabel=null;
	private String courseDescr="";
	private int levelVal; 
	
	public Course(){}
	
	public Course(int id, String name,CourseLabel label, String description, int levelVal){
		this.setCourseId(id);
		this.setCourseName(name);
		this.setCourseLabel(label);
		this.setCourseDescr(description);
		this.setLevelVal(levelVal);
	}
	
	/**
	 * 
	 * @param id The id to set
	 */
	public void setCourseId(int id){
		this.courseId = id;
	}
	
	/**
	 * 
	 * @return Returns the id.
	 * @hibernate.id column = "Activity_PK" generator-class = "assigned"
	 */
	public int getCourseId(){
		return this.courseId;
	}
	
	/**
	 * @param courseName The name of the course
	 */
	public void setCourseName(String name){
		this.courseName = name;
	}
	
	/**
	 * @return Returns the course name
	 * @hibernate.property column="ActivityName"
	 */
	public String getCourseName(){
		return this.courseName;
	}
	
	/**
	 * @param courseLabel The course Label
	 * 
	 */
	public void setCourseLabel(CourseLabel label){
		this.courseLabel = label;
	}
	
	/**
	 * @return Returns the course label
	 * @hibernate.component
	 */
	public CourseLabel getCourseLabel(){
		return this.courseLabel;
	}
	
	/**
	 * @param courseDescr The course Description
	 */
	public void setCourseDescr(String description){
		this.courseDescr = description;
	}
	
	/**
	 * @return Returns the course description
	 * @hibernate.property column="ActivityDesc"
	 */
	public String getCourseDescr(){
		return this.courseDescr;
	}
	
	/**
	 * 
	 * @param levelVal The levalVal to set
	 */
	public void setLevelVal(int val){
		this.levelVal = val;
	}
	
	/**
	 * 
	 * @return Returns the levelVal
	 * @hibernate.property column="LevelVal"
	 */
	public int getLevelVal(){
		return this.levelVal;
	}
}
