package com.fgl.cwp.model;
import java.util.Date;

/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */

/**
 * A data container for viewing and closing Pricing Issues.
 * @author mkuervers, urobertson
 * 
 * @hibernate.class 
 * 		table = "pricing_issues"
 *      dynamic-insert = "true"
 *      dynamic-update = "true"
 * 
 *     
 * This query uses a SQL Server specific getdate() function. 
 * Will not port to other db automagically.     
 * @hibernate.query 
 *      name="getOpenPricingIssues"
 *      query = "select 	pricingIssue
 *       		from    	PricingIssue pricingIssue
 *              where   	pricingIssue.closedDate is null
 *              or			pricingIssue.closedDate > :selectedDate
 *              order by 	pricingIssue.closedDate, pricingIssue.openedDate desc, pricingIssue.styleNum"   
 */
public class PricingIssue {
    private Date closedDate; 
    private String comment;
    private String correctPrice;
    private String currentPrice;
    private String divisionName;
    private Integer id;
    private Date openedDate;
    private Integer storeNum;
    private String styleNum;
    private String submittedBy;
    private String upc;
    /**
     * 
     */
    public PricingIssue() {
        super();
    }
    /**
     * @return Returns the closedDate.
     * 
     * @hibernate.property 
     *      column = "closed_timestamp"
     */
    public Date getClosedDate() {
        return closedDate;
    }
    /**
     * @return Returns the comment.
     * 
     * @hibernate.property 
     *      column = "comment"
     */
    public String getComment() {
        return comment;
    }
    /**
     * @return Returns the correctPrice.
     * 
     * @hibernate.property 
     *      column = "correct_price"
     */
    public String getCorrectPrice() {
        return correctPrice;
    }
    /**
     * @return Returns the currentPrice.
     * 
     * @hibernate.property 
     *      column = "current_price"
     */
    public String getCurrentPrice() {
        return currentPrice;
    }
    /**
     * @return Returns the divisionName.
     * 
     * @hibernate.property 
     *      column = "division_name"
     */
    public String getDivisionName() {
        return divisionName;
    }
    /**
     * @return Returns the id.
     * 
     * @hibernate.id 
     *      column = "id"
     *      generator-class = "native"
    */
    public Integer getId() {
        return id;
    }
    /**
     * @return Returns the openedDate.
     * 
     * @hibernate.property 
     *      column = "opened_timestamp"
     */
    public Date getOpenedDate() {
        return openedDate;
    }
    
    /**
     * @return Returns the storeNum.
     * 
     * @hibernate.property 
     * 		column = "store_number"
     */
    public Integer getStoreNum() {
        return storeNum;
    }

    /**
     * @return Returns the style number.
     * 
     * @hibernate.property 
     *      column = "style_number"
     */
    public String getStyleNum() {
        return styleNum;
    }
    
    /**
     * @return Returns the submittedBy.
     * 
     * @hibernate.property 
     *      column = "submitted_by"
     */
    public String getSubmittedBy() {
        return submittedBy;
    }
    /**
     * @return Returns the upc.
     * 
     * @hibernate.property 
     *      column = "upc"
     */
    public String getUpc() {
        return upc;
    }
    /**
     * @param closedDate The closedDate to set.
     */
    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }
    /**
     * @param comment The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @param correctPrice The correctPrice to set.
     */
    public void setCorrectPrice(String correctPrice) {
        this.correctPrice = correctPrice;
    }
    /**
     * @param currentPrice The currentPrice to set.
     */
    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }
    /**
     * @param divisionName The divisionName to set.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @param openedDate The openedDate to set.
     */
    public void setOpenedDate(Date openedDate) {
        this.openedDate = openedDate;
    }
    /**
     * @param storeNum The storeNum to set.
     */
    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }
    /**
     * @param styleNum The style number to set.
     */
    public void setStyleNum(String styleNum) {
        this.styleNum = styleNum;
    }
    /**
     * @param submittedBy The submittedBy to set.
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
    /**
     * If no value is submitted for UPC (ie. an empty string)
     * then we need to set upc to null. The reason for this is
     * that upc is stored as a bigint, which will convert any
     * empty strings to a zero in the database. We want the db
     * value to be null, so that when retrieved from the db it
     * won't display anything (ie. it won't display a 0)
     * @param upc The upc to set.
     */
    public void setUpc(String upc) {
    	if (upc != null && upc.equals("")) upc = null;
        this.upc = upc;
    }
}
