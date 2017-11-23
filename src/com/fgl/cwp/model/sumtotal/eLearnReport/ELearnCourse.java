package com.fgl.cwp.model.sumtotal.eLearnReport;

/**
 * 
 * @author gdelve
 * 
 */
public class ELearnCourse implements Comparable {
	private String courseId;

	private String courseName;

	private String score;

	private String dateTaken;
	
	public ELearnCourse(String courseId, String courseName){
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public ELearnCourse(String courseId, String courseName, String score,
			String dateTaken) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.score = score;
		this.dateTaken = dateTaken;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDateTaken() {
		if (dateTaken != null && dateTaken.length() >= 11) {
			return dateTaken.substring(2, 10);
		}
		return dateTaken;
	}

	public void setDateTaken(String dateTaken) {
		this.dateTaken = dateTaken;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String toString() {
		return "Course ID: " + this.courseId + " Course Name: "
				+ this.courseName;
	}

	public int compareTo(Object o) {
		if (o instanceof ELearnCourse) {
			return this.courseName
					.compareToIgnoreCase(((ELearnCourse) o).courseName);
		}
		return 0;
	}

}
