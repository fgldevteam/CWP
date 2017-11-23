package com.fgl.cwp.common;

import java.io.Serializable;
//import java.util.List;
import com.fgl.cwp.model.QueryParameters;
import com.fgl.cwp.common.util.SumMultiMap;
//import com.fgl.cwp.model.SumTotalCourse;

import jxl.write.WritableWorkbook;
/**
 * 
 * @author jmbarga
 *
 */

public class ReportPrinter implements Serializable {
	private static final long serialVersionUID = 1L;	
	private SumMultiMap multiMap;
	private QueryParameters parameters;
	
	public ReportPrinter(SumMultiMap data,QueryParameters parameters){
		this.multiMap = data;
		this.parameters = parameters;
	}	
	public void doPrint(WritableWorkbook book){}	
	
	protected SumMultiMap getReportMultObject(){
		return this.multiMap;
	}	
	public void generateReport(WritableWorkbook book){}
	
	public QueryParameters getReportParameters(){return this.parameters;}
}
