package com.fgl.cwp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class QueryParameters implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String vp = null;
    private List<List> rgm = new ArrayList<List>();
    private List<List> dm = new ArrayList<List>();
    private List<List> stores = new ArrayList<List>();
    private List<List> courses = new ArrayList<List>();
    private String[] departments;
    private GregorianCalendar startDate = null;
    private GregorianCalendar endDate = null;
    private String type = null;
    private Store store;
	
	public QueryParameters(){}
	
	public void setVicePresident(String vp) {this.vp = vp;}
    
    public void setRegionalManagers(List rgm){        
        this.rgm.clear();
        this.rgm.addAll(rgm);
    }
    
    public void setDistrictManagers(List dm){
        this.dm.clear();
        this.dm.addAll(dm);
    }
    
    public void setStores(List stores){
        this.stores.clear();
        this.stores.addAll(stores);
    }
    
    public void setCourses(List courses){
        this.courses.clear();
        this.courses.addAll(courses);
    }
    
    public void setStartDate(GregorianCalendar startDate){
        this.startDate = startDate;
    }
    
    public void setEndDate(GregorianCalendar endDate){
        this.endDate = endDate;
    }
    
    public void setReportType(String type) {
        this.type = type;
    }
    
    public String getVicePresident(){
        return this.vp;
    }
    
    public List getRegionalManagers(){        
        return (List)((ArrayList)this.rgm).clone();
    }
    
    public List getDistrictManagers(){
        return (List)((ArrayList)this.dm).clone();
    }
    
    public List getStores(){
        return (List)((ArrayList)this.stores).clone();
    }
    
    public List<SumTotalCourse> getCourses(){
        return (List)((ArrayList)this.courses).clone();
    }
    
    public GregorianCalendar getStartDate(){
        return this.startDate;
    }
    
    public GregorianCalendar getEndDate(){
        return this.endDate;
    }
    
    public String getReportType(){
        return this.type;
    }
    
    public void setStore(Store store){
    	this.store = store;
    }
    public Store getstore(){
    	return this.store;
    }

	public String[] getDepartments() {
		return departments;
	}

	public void setDepartments(String[] departments) {
		this.departments = departments;
	}

}
