package com.fgl.cwp.reporter.bean;

import java.io.Serializable;
import com.fgl.cwp.reporter.ReporterType;
import com.fgl.cwp.model.User;

/**  
 * @author jmbarga 
 */

public class TaskBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String task_key;
	private User user;
	private int priority;
	private ReporterType reportType;
	private String resourceKeys;
	
	public TaskBean(String task_key,String resource_key, User user, ReporterType type, int priority){
		this.priority=priority;
		this.reportType=type;
		this.task_key=task_key;
		this.resourceKeys = resource_key;
		this.user = user;
	}	
	
	public String getTaskKey() {
		  return this.task_key;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public String getResourceKey(){
		return this.resourceKeys;
	}
	
	public ReporterType getReporterType(){
		return this.reportType;
	}

	public int getPriority(){
		return this.priority;
	}
}
