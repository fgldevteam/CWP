package com.fgl.cwp.model.sumtotal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.fgl.cwp.model.sumtotal.ReportType;

public class ReportParams implements Serializable {
	private static final long serialVersionUID = 1L;

	private String vp = null;

	private List<List> rgm = new ArrayList<List>();

	private List<List> dm = new ArrayList<List>();

	private List<List> stores = new ArrayList<List>();

	private List<List> courses = new ArrayList<List>();

	private GregorianCalendar startDate = null;

	private GregorianCalendar endDate = null;

	private ReportType type = null;

	public void setVicePresident(String vp) {
		this.vp = vp;
	}

	public void setRegionalManagers(List rgm) {
		this.rgm.clear();
		this.rgm.addAll(rgm);
	}

	public void setDistrictManagers(List dm) {
		this.dm.clear();
		this.dm.addAll(dm);
	}

	public void setStores(List stores) {
		this.stores.clear();
		this.stores.addAll(stores);
	}

	public void setCourses(List courses) {
		this.courses.clear();
		this.courses.addAll(courses);
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}

	public void setReportType(ReportType type) {
		this.type = type;
	}

	public String getVicePresident() {
		return this.vp;
	}

	public List getRegionalManagers() {
		return (List) ((ArrayList) this.rgm).clone();
	}

	public List getDistrictManagers() {
		return (List) ((ArrayList) this.dm).clone();
	}

	public List getStores() {
		return (List) ((ArrayList) this.stores).clone();
	}

	public List getCourses() {
		return (List) ((ArrayList) this.courses).clone();
	}

	public GregorianCalendar getStartDate() {
		return this.startDate;
	}

	public GregorianCalendar getEndDate() {
		return this.endDate;
	}

	public ReportType getReportType() {
		return this.type;
	}

	public String toString() {
		return "VP:\t"
				+ this.vp
				+ "\n"
				+ "RGM:\t"
				+ (this.rgm.size() == 0 ? "Not Set" : this.rgm.toString())
				+ "\n\r"
				+ "DM:\t"
				+ (this.dm.size() == 0 ? "Not Set" : this.dm.toString())
				+ "\n\r"
				+ "STORES:\t"
				+ (this.stores.size() == 0 ? "Not Set" : this.stores.toString())
				+ "\n\r"
				+ "COURSES:\t"
				+ this.courses.toString()
				+ "\n\r"
				+ "STARTDATE:\t"
				+ (this.startDate == null ? "Not Set" : this.startDate
						.get(Calendar.YEAR)
						+ "-"
						+ this.startDate.get(Calendar.MONTH)
						+ 1
						+ "-"
						+ this.startDate.get(Calendar.DAY_OF_MONTH))
				+ "\n\r"
				+ "ENDDATE:\t"
				+ (this.endDate == null ? "Not Set" : this.endDate
						.get(Calendar.YEAR)
						+ "-"
						+ this.endDate.get(Calendar.MONTH)
						+ 1
						+ "-"
						+ this.endDate.get(Calendar.DAY_OF_MONTH));

	}
}
