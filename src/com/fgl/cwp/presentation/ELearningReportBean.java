package com.fgl.cwp.presentation;

import java.util.List;

import net.sf.hibernate.HibernateException;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.model.sumtotal.eLearnReport.Department;
import com.fgl.cwp.persistence.services.ReportService;

/**
 * Used for the collection of parameters for the E-Learning report
 * 
 * @author gdelve
 * 
 */
public class ELearningReportBean extends ReportBean {

	private static final long serialVersionUID = 1L;

	/** The courses that can be reported on */
	private List<SumTotalCourse> courses;
	
	/** the departments (jobs) retrieved from the SumTotal Database */
	private List<Department> departments;

	/** The store selections made by the user */
	private String[] selectedStoreIds;

	/** The course selections made by the user */
	private String[] selectedCourseIds;
	
	/** the department selections made by the user */
	private String[] selectedDepartments;
	
	/** The store name if it is a store report */
	private String storeName;
	
	private Store theStore=null;	
	
	private String method=null;
	

	/**
	 * Gets the stores, courses, and initializes the index arrays and report
	 * format.
	 * 
	 * @return String indicating if successful
	 * @throws HibernateException
	 */
	public String initializeELearningReport() throws HibernateException {
		initStore();		
		this.setCourses( ReportService.getInstance().fetchReportableCourses() );
		this.departments = ReportService.getInstance().fetchDepartments();
		selectedStoreIds = null;
		selectedCourseIds = null;
		selectedDepartments = null;
		setStartDate(null);
		setEndDate(null);
		setMethod(null);
		return Constants.SUCCESS;
	}

	public List<SumTotalCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<SumTotalCourse> courses) {
		this.courses = courses;
	}

	public String[] getSelectedStoreIds() {
		return selectedStoreIds;
	}

	public void setSelectedStoreIds(String[] selectedStoreIds) {
		this.selectedStoreIds = selectedStoreIds;
	}

	public String[] getSelectedCourseIds() {
		return selectedCourseIds;
	}

	public void setSelectedCourseIds(String[] selectedCourseIds) {
		this.selectedCourseIds = selectedCourseIds;
	}
	
	public void setStoreName(String name){
		this.storeName = name;
	}
	public String getStoreName(){
		return this.storeName;
	}
	
	public void setTheStore(Store store){
		this.theStore =  store;
	}
	
	public Store getTheStore(){
		return this.theStore;
	}
	
	public void setMethod(String format){
		this.method = format;
	}
	public String getMethod(){
		return this.method;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public String[] getSelectedDepartments() {
		return selectedDepartments;
	}

	public void setSelectedDepartments(String[] selectedDepartments) {
		this.selectedDepartments = selectedDepartments;
	}
}
