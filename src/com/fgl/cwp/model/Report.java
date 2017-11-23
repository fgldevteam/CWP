/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.model;

import java.util.Date;

/**
 * A report submission
 * 
 * @author dschultz
 * @hibernate.class
 *      table = "reporter"
 */
public class Report {
    /**
     * <code>STATUS_PROCESSED</code> indicates that the report has been generated
     */
    public static final String STATUS_PROCESSED = "P";
    /**
     * <code>STATUS_QUEUED</code> indicates that the user's report parameters have been saved in the database and
     * that the report is ready to be processed.
     */
    public static final String STATUS_QUEUED = "Q";
    /**
     * <code>STATUS_ERROR</code> indicates that there was an error processing the users report request
     */
    public static final String STATUS_ERROR = "E";
    
    private int id;
    private String server;
    private int storeId;
    private String name;
    private String parameters;
    private String executeReportAtUrl;
    private String generatedPdfUrl;
    private Date modifiedDate;
    private String status;
    private String email;
    
    /**
     * @return Returns the email.
     * 
     * @hibernate.property 
     *      column = "email" 
     *      not-null = "true"
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the executeReportAtUrl.
     * @hibernate.property
     *      column = "url"
     *      not-null = "true"
     */
    public String getExecuteReportAtUrl() {
        return executeReportAtUrl;
    }
    /**
     * @param executeReportAtUrl The executeReportAtUrl to set.
     */
    public void setExecuteReportAtUrl(String executeReportAtUrl) {
        this.executeReportAtUrl = executeReportAtUrl;
    }
    /**
     * @return Returns the generatedPdfUrl.
     * @hibernate.property 
     *      column = "pdf_file"
     */
    public String getGeneratedPdfUrl() {
        return generatedPdfUrl;
    }
    /**
     * @param generatedPdfUrl The generatedPdfUrl to set.
     */
    public void setGeneratedPdfUrl(String generatedPdfUrl) {
        this.generatedPdfUrl = generatedPdfUrl;
    }
    /**
     * @return Returns the id.
     * @hibernate.id
     *      column = "report_id"
     *      generator-class = "native"
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the modifiedDate.
     * @hibernate.version
     *      column = "moddate"
     *      type = "timestamp"
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }
    /**
     * @param modifiedDate The modifiedDate to set.
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    /**
     * @return Returns the reportName.
     * @hibernate.property
     *      column = "report_name"
     *      not-null = "true"
     */
    public String getName() {
        return name;
    }
    /**
     * @param reportName The reportName to set.
     */
    public void setName(String reportName) {
        this.name = reportName;
    }
    /**
     * @return Returns the reportParameters.
     * @hibernate.property
     *      column = "report_parameters"
     *      not-null = "true"
     */
    public String getParameters() {
        return parameters;
    }
    /**
     * @param reportParameters The reportParameters to set.
     */
    public void setParameters(String reportParameters) {
        this.parameters = reportParameters;
    }
    /**
     * @return Returns the server.
     * 
     * @hibernate.property 
     *      column = "server"
     *      not-null = "true"
     */
    public String getServer() {
        return server;
    }
    /**
     * @param server The server to set.
     */
    public void setServer(String server) {
        this.server = server;
    }
    /**
     * @return Returns the status.
     * @hibernate.property 
     *      column = "status"
     *      not-null = "true"
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return Returns the userId.
     * @hibernate.property
     *      column = "user_id"
     *      not-null = "true"
     */
    public int getStoreId() {
        return storeId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setStoreId(int userId) {
        this.storeId = userId;
    }
}
