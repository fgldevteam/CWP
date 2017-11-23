package com.fgl.cwp.model.sumtotal.eLearnReport;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.fgl.cwp.persistence.services.ElearningReportService.DepartmentInStoreData;

public class ELearnEmployee implements Comparable {
	private String employeeName;

	private String employeeLastName;

	private String employeeFirstName;

	private String employeeId;

	private Map<String, ELearnCourse> courses;

	private DecimalFormat decimalFormat;

	public ELearnEmployee(DepartmentInStoreData employeeData,
			DecimalFormat decimalFormat) {
		this.employeeName = employeeData.getEmpName();
		this.employeeFirstName = employeeData.getEmpFirstName();
		this.employeeLastName = employeeData.getEmpLastName();
		this.employeeId = employeeData.getEmpId();
		this.decimalFormat = decimalFormat;
		this.courses = new HashMap<String, ELearnCourse>();
	}

	public Map getCourses() {
		return courses;
	}

	public void setCourses(Map<String, ELearnCourse> courses) {
		this.courses = courses;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void addCourseRecord(DepartmentInStoreData courseData) {
		String courseId = courseData.getCourseId();
		ELearnCourse course = new ELearnCourse(courseId, courseData
				.getCourseName(), decimalFormat.format(Double
				.parseDouble(courseData.getScore())), courseData.getDateTaken());
		this.courses.put(courseId, course);
	}

	/**
	 * Determines equality for ELearnEmployee objects
	 */
	public boolean equals(Object object) {
		if (this.employeeName.equals(object.toString())) {
			return true;
		}
		return false;
	}

	public int compareTo(Object o) {
		int result = 0;
		if (o != null && o instanceof ELearnEmployee) {
			ELearnEmployee emp = (ELearnEmployee) o;
			result = this.employeeLastName.compareTo(emp.getEmployeeLastName());
			if (result == 0) {// same last name, compare first names
				result = this.employeeFirstName.compareTo(emp
						.getEmployeeFirstName());
				if (result == 0) {// same first names, compare ID's
					result = this.employeeId.compareTo(emp.getEmployeeId());
				}
			}
		}
		return result;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String toString() {
		return this.employeeName;
	}
}
