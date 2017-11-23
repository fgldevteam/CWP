package com.fgl.cwp.model.sumtotal.eLearnReport;

/**
 * @author gdelve
 *
 * @hibernate.class
 * 		table="sum_total_dept"
 * 
 * @hibernate.query
 *       name = "allDepartments"
 *       query = "from Department ed order by ed.departmentDesc"
 */

public class Department {
	
	private Integer id;
	private String departmentAbbrv;
	private String departmentDesc;
	
	/**
	 * @return The ID number of the department record
	 * @hibernate.id 
     *      column = "id"
     *      generator-class = "assigned"
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return The abbreviation for the department
	 * @hibernate.property 
     *      column = "dept_abbrv"
	 */
	public String getDepartmentAbbrv() {
		return departmentAbbrv;
	}
	
	public void setDepartmentAbbrv(String departmentAbbrv) {
		this.departmentAbbrv = departmentAbbrv;
	}
	
	/**
	 * @return The description for the department
	 * @hibernate.property 
     *      column = "dept_desc"
	 */
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
}
