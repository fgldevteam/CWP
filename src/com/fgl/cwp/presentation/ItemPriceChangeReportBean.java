/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.ItemPriceChange;
import com.fgl.cwp.persistence.services.ReportService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * Fetches the objects necessary for generating the Item Price Change Report
 * @author dschultz
 */
public class ItemPriceChangeReportBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private static final String MARKUP_MSG_KEY = "report.pricechange.markup";
    private static final String MARKDOWN_MSG_KEY = "report.pricechange.markdown";
    
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private String mark;
    private String markDescription;
    private String storeNumber;
    private String startDate;
    private String endDate;
    
    private List results;
    
    /**
     * Retrieve data for the Item Price Change Report
     * @return
     * @throws ParseException
     * @throws HibernateException
     */
    public String fetchData() throws ParseException, HibernateException{
        //this List allows the TICKET_PRICE records to be fetched along 
        //with the MARKDOWN records
        List<Integer> priceDirectionList = new ArrayList<Integer>();
        int storeNumber = Integer.parseInt(this.storeNumber);
        Date startDate = format.parse(this.startDate);
        Date endDate = format.parse(this.endDate);
        
        HttpServletRequest request = ActionContext.getActionContext().getRequest();
        MessageResources msg = (MessageResources) request.getAttribute( Globals.MESSAGES_KEY );
        Locale locale = (Locale) request.getAttribute( Globals.LOCALE_KEY );
        
        if (ReportBean.REPORT_MARKUP.equalsIgnoreCase(mark)){        
            this.markDescription = msg.getMessage(locale, MARKUP_MSG_KEY);
            priceDirectionList.add(new Integer(ItemPriceChange.MARKUP));
        } else {
            this.markDescription = msg.getMessage(locale, MARKDOWN_MSG_KEY);
            priceDirectionList.add(new Integer(ItemPriceChange.MARKDOWN));
            priceDirectionList.add(new Integer(ItemPriceChange.TICKET_PRICE));
        }
        
        results = ReportService.getInstance().fetchItemPriceChangeReportData(storeNumber, priceDirectionList, startDate, endDate);
        
        return Constants.SUCCESS;
    }
        
    /**
     * @return Returns the endDate.
     */
    public Date getEndDateValue() {
        Date date = null;
        try {
            date = format.parse(endDate);
        } catch (ParseException e) {
            //ignore the parse exception
        }
        return date;
    }
    
    /**
     * @return endDate
     */
    public String getEndDate(){
        return this.endDate;
    }
    
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return Returns the mark.
     */
    public String getMark() {
        return mark;
    }
    /**
     * @param mark The mark to set.
     */
    public void setMark(String mark) {
        this.mark = mark;
    }
    /**
     * @return Returns the markDescription.
     */
    public String getMarkDescription() {
        return markDescription;
    }
    /**
     * @param markDescription The markDescription to set.
     */
    public void setMarkDescription(String markDescription) {
        this.markDescription = markDescription;
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
     * @return Returns the startDate.
     */
    public Date getStartDateValue() {
        Date date = null;
        try {
            date = format.parse(startDate);
        } catch (ParseException e) {
            //ignore the parse exception
        }
        return date;
    }
    
    /**
     * @return startDate
     */
    public String getStartDate(){
        return this.startDate;
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
}
