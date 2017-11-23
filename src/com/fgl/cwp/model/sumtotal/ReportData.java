package com.fgl.cwp.model.sumtotal;

import java.io.Serializable;
import java.util.Date;

/**
 * a data container for the elearning report data
 * @author jmbarga
 *
 */
public class ReportData implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;
	
	private String empFullName;
	private String empLastName;
	private String empFirstName;
	private String empId;
	private String vicePresident;
	private String regManager;
	private String distManager;
	private String store;
	private String department;
	private Double attemptScore;
	private Date dateTaken;
	private String courseName;
	private int numEmp;
	
	/**
	 * Constructor
	 * 
	 * @param empLastName
	 * @param empFirstName
	 * @param empId
	 * @param vPresident
	 * @param rManager
	 * @param dManager
	 * @param courseName
	 * @param department
	 * @param attemptScore
	 * @param dateTaken
	 * @param store
	 * @param numEmp
	 */
	public ReportData(String empLastName, String empFirstName, String empId,
	          String vPresident, String rManager, String dManager, String courseName,
	          String department, String attemptScore, Date dateTaken,
	          String store, String numEmp){
		
		this.empLastName = empLastName;
		this.empFirstName = empFirstName;
		this.empId = empId;
		this.vicePresident = vPresident;
		this.regManager = rManager;
		this.distManager = dManager;
		this.store = store;
		this.department = department;
		this.attemptScore = attemptScore != null ? Double.valueOf(attemptScore): Double.valueOf("0");
		this.dateTaken = dateTaken ;
		this.courseName = courseName;
		this.numEmp = numEmp != null ? Integer.valueOf(numEmp) : Integer.valueOf("0");
		this.empFullName = this.empLastName+", "+this.empFirstName+" "+this.empId;
	}
	
	/**
	 * @param empFullName the employee fullname to set
	 */
	public void setEmpFullName(String name){
		this.empFullName = name;
	}
	
	/**
	 * 
	 * @return empFullName 
	 */
	public String getEmpFullName(){
		return this.empFullName;
	}
	
	public void setVicePresident(String vpresident){
		this.vicePresident = vpresident;
	}
	
	public String getVicePresident(){
		return this.vicePresident;
	}
	
	public void setRegManager(String manager){
		this.regManager = manager;
	}
    
	public String getRegManager(){
		return this.regManager;
	}
	
	public void setDistManager(String manager){
		this.distManager = manager;
	}
	
	public String getDistManager(){
		return this.distManager;
	}
	
	public void setStore(String store){
		this.store = store;
	}
	
	public String getStore(){
		return this.store;
	}
	
	public void setDepartment(String department){
		this.department = department;
	}
	
	public String getDepartment(){
		return this.department;
	}
	
	public void setAttemptScore(String score){
		this.attemptScore = Double.valueOf(score);
	}
	
	public Double getAttemptScore(){
		return this.attemptScore;
	}
	
	public void setDateTaken(Date date){
		this.dateTaken = date;
	}
	public Date getDateTaken(){
		return this.dateTaken;
	}
	
	public void setCourseName(String course){
		this.courseName = course;
	}
	
	public String getCourseName(){
		return this.courseName;
	}	
	
	public int getNumberEmp(){
		return this.numEmp;
	}

	public String getEmpFirstname() {
		return empFirstName;
	}

	public void setEmpFirstname(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastname) {
		this.empLastName = empLastname;
	}

	public int compareTo(Object o) {
		int result = 0;
		if (o != null && o instanceof ReportData) {
			ReportData empReportData = (ReportData) o;
			result = this.empLastName.compareTo(empReportData.getEmpLastName());
			if (result == 0) {// same last name, compare first names
				result = this.empFirstName.compareTo(empReportData
						.getEmpFirstname());
				if (result == 0) {// same first names, compare ID's
					result = this.empId.compareTo(empReportData.getEmpId());
				}
			}
		}
		return result;
	}
	
	public String toString(){
		return this.empFullName;
	}
}
