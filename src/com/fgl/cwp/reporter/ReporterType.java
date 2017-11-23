package com.fgl.cwp.reporter;

import java.io.Serializable;
/**
 * @author jmbarga
 */
public class ReporterType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static ReporterType pdfReporter = new ReporterType("com.fgl.cwp.reporter.PdfReporter");
	
	private final String name;
	
	private ReporterType(String type){
		this.name=type;
	}
	
	public static ReporterType getReporterType(String type){
		return pdfReporter;
	}
	
	public String getReportTypeName(){
		return this.name;
	}
}
