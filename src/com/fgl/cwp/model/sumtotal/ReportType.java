package com.fgl.cwp.model.sumtotal;

import java.io.Serializable;

public class ReportType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String type;
	
	public static ReportType PDF_REPORT = new ReportType("PDF");
	public static ReportType EXCEL_REPORT = new ReportType("EXCEL");
	
	public ReportType(String type){
		this.type = type;
	}
	
	public String getReportType(){return this.type;}
}
