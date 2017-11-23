package com.fgl.cwp.presentation.forms;

import org.apache.struts.action.ActionForm;


public class ElearningCoursesForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String[] original_names;	
	private String[] desired_names;	
	private String[] reportable_courses;
	private String[] displayNames;
	
	
	public void setOriginal_names(String[] names){
		this.original_names = names;
	}
	public String[] getOriginal_names(){
		return this.original_names;
	}
	
	public void setDesired_names(String[] names){
		this.desired_names = names;
	}
	public String[] getDesired_names(){
		return this.desired_names;
	}
	
	public void setReportable_courses(String[] courses){
		this.reportable_courses = courses;
	}
	public String[] getReportable_courses(){
		return this.reportable_courses;
	}
	public String[] getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(String[] displayNames) {
		this.displayNames = displayNames;
	}
}
