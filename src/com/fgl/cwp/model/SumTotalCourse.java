package com.fgl.cwp.model;

/**
 * 
 * @author gdelve
 *
 * @hibernate.class
 * 		table="sum_total_course"
 * 
 * @hibernate.query
 *       name = "allCourses"
 *       query = "from SumTotalCourse stc order by stc.originalName"
 *       
 * @hibernate.query
 *       name = "reportableCourses"
 *       query = "from SumTotalCourse stc
 *       where stc.webReportable = 1 order by stc.displayName"
 */
public class SumTotalCourse{
	
	public static final int REPORTABLE = 1;
	
	/** Identifies the specific course */
	private int courseId;
	
	/** Identifies whether or not the course is available for reporting on */
	private int webReportable;
	
	/** User friendly name for the course */
	private String displayName;
	
	/** course shortcut */
	private String shortName;
	
	/** original course's name */
	private String originalName;
	
	/**
	 * @return The ID number of the course
	 * @hibernate.id 
     *      column = "course_id"
     *      generator-class = "assigned"
	 */
	public int getCourseId() {
		return courseId;
	}
	
	/**
	 * @param courseId
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * @return
	 * @hibernate.property
	 * 		column="display_name"
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return true if can report on the course
	 * @hibernate.property
	 * 		column="web_reportable"
	 */
	public int getWebReportable() {
		return webReportable;
	}
	
	/**
	 * @param webReportable
	 */
	public void setWebReportable(int webReportable) {
		this.webReportable = webReportable;
	}
	
	/**
	 * 
	 * @param shortcut
	 */
	public void setShortName(String name){
		this.shortName = name;
	}
	/**
	 * @return the shortname of the course
	 * @hibernate.property
	 *    column="course_shortname"
	 * 
	 */
	public String getShortName(){
		return shortName;
	}
	
	/**
	 * 
	 * @param course's original name
	 */
	public void setOriginalName(String name){
		this.originalName = name;
	}
	
	/**
	 * 
	 * @return the original name of the course
	 * @hibernate.property
	 *   column="course_originalname"
	 */
	public String getOriginalName(){
		return this.originalName;
	}
	
	public String toString() {
		return originalName;
	}
}
