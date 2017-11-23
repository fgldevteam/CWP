/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.persistence.services.ReportService;
import com.fgl.cwp.struts.BaseBean;

/**
 * Struts Action class for the Package Price Change Report
 * @author dschultz
 */
public class PackageReportBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private String storeNumber;
    private String startDate;
    private String endDate;
    private List results;

    
    /**
     * Get the data for the Package Price Change report
     * @return
     * @throws ParseException
     * @throws HibernateException
     */
    public String fetchData() throws ParseException, HibernateException{
        int storeNumber = Integer.parseInt(this.storeNumber);
        Date startDate = format.parse(this.startDate);
        Date endDate = format.parse(this.endDate);
        
        results = ReportService.getInstance().fetchPackageReportData(storeNumber, startDate, endDate);
        
        return Constants.SUCCESS;
    }
    
    /**
     * @return Returns the endDate.
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return Returns the format.
     */
    public SimpleDateFormat getFormat() {
        return format;
    }
    /**
     * @param format The format to set.
     */
    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }
    /**
     * @return Returns the startDate.
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return Returns the storeNumber.
     */
    public String getStoreNumber() {
        return storeNumber;
    }
    /**
     * @param storeNumber The storeNumber to set.
     */
    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }
    /**
     * @return Returns the results.
     */
    public List getResults() {
        return results;
    }
    /**
     * @param results The results to set.
     */
    public void setResults(List results) {
        this.results = results;
    }
    
    /**
     * @return the startDate as a java.util.Date object
     */
    public Date getStartDateValue(){        
        try {
            return format.parse(startDate);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * @return the endDate as a java.util.Date object
     */
    public Date getEndDateValue(){
        try {
            return format.parse(endDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
