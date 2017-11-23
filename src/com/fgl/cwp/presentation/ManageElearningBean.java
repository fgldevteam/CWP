package com.fgl.cwp.presentation;

import com.fgl.cwp.struts.BaseBean;
import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.common.Constants;
import com.fgl.cwp.persistence.services.CourseService;
import java.util.List;

public class ManageElearningBean extends BaseBean {
	private static final long serialVersionUID = 1L;
	private List<SumTotalCourse> allCourses=null;
	
	public String loadCourses() throws Exception{
		setAllCourses( CourseService.getAllCourses() );	
		return Constants.SUCCESS;
	}
	
	private void setAllCourses(List<SumTotalCourse> list){ 
		this.allCourses = list;
	}
	
	public List<SumTotalCourse> getAllCourses(){ 
		return this.allCourses;
	}	
	
	public String saveReportableCourses() throws Exception{
		return Constants.SUCCESS;
	}
}
