package com.fgl.cwp.model.sumtotal.course;

/**
 * 
 * @author jmbarga
 *
 *@hibernate.class
 * 		table="tbl_tmx_activity"
 * 
 * @hibernate.query
 *       name = "getCourses_sumtotal"
 *       query = "from CourseOutOfSumDB"
 *
 */

public class CourseOutOfSumDB {

	/** identifies the specific course */
	private int  activity_pk; //activity_pk
	/** identifies the course name */
	private String activityName; //activityname	
	
	
	/**
	 * @return The ID number of the course
	 * @hibernate.id 
     *      column = "Activity_PK"
     *      generator-class = "assigned"
	 */
	public int getActivity_pk() {
		return activity_pk;
	}
	
	/**
	 * @param courseId
	 */
	public void setActivity_pk(int courseId) {
		this.activity_pk = courseId;
	}
	
	/**
	 * @return
	 * @hibernate.property
	 * 		column="ActivityName"
	 */
	public String getActivityName() {
		return activityName;
	}
	
	/**
	 * @param displayName
	 */
	public void setActivityName(String name) {
		this.activityName = name;
	}
}
