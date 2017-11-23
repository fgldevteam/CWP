/*
 * Copyright Forzani Group Ltd. 2005
 *
 */
package com.fgl.cwp.presentation.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessages;

import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.struts.ActionContext;

/**
 * @author urobertson
 */
public class InventoryAdjBean extends GenericFormBean {
	
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(InventoryAdjBean.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static int ARRAY_LENGTH = 20;

    private String today;
    private Integer storeNum;
    private String submittedBy;
    private String[] upc;
    private String[] quantity;
    private String[] price;
    private String[] description;
    private String explanation;
    private boolean reviewedByStoreMgr;
    private String storeMgrName;

    /**
     * Reset all fields to appropriate defaults
     */
    public void reset() {
        log.debug("InventoryAdjBean(): reset()");
        
        today = dateFormat.format(new Date());
        Store store = Utils.getStoreFromSession();
        if (store!=null) {
            storeNum = new Integer(store.getNumber());
        }

        submittedBy = null;
        upc = new String[ARRAY_LENGTH];
        quantity = new String[ARRAY_LENGTH];
        price = new String[ARRAY_LENGTH];
        description = new String[ARRAY_LENGTH];
        explanation = null;
        reviewedByStoreMgr = false;
        storeMgrName = null;
        
        super.reset();
    }
    
    /**
     * Create a new form
     * @return
     * @throws Exception
     */
    public String create() throws Exception {
        log.debug("InventoryAdjBean(): create()");

        reset();
        return super.create();
    }
    

    /**
     * Ensure all required form fields are filled out
     */
    public void validate() {
        log.debug("InventoryAdjBean(): validate()");
        ActionContext ctxt = ActionContext.getActionContext();
        
        int count = 0;
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(upc[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(quantity[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(price[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(description[i]))) {

            	// Count the # of line items
                ++count;
                
                // Verify that each line item has the requred UPC and quantity values
                if (StringUtils.isEmpty(StringUtils.trim(upc[i]))
                		|| StringUtils.isEmpty(StringUtils.trim(quantity[i]))) {
                	ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.upc.quantity.required");
                } else if (!StringUtils.isNumeric(upc[i])) {
                    ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.upc.quantity.number");
                } else {
                // Verify that the quantity is an integer
                	int tmp=-1;
                	try {
                		tmp = Integer.parseInt(quantity[i]);
                	} catch (Exception e) {
                		log.error(" tmp : "+tmp);
                		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.upc.quantity.number");
                	}
                }
            }
        }

        // Verify that at least 1 line item has been entered
        if (count==0) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.minrecords");
        }

        // Verify that the form has been reviewed by the Store Manager
        if (reviewedByStoreMgr == false) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.reviewedByStoreMgr.required");
        }
        
        // Verify that the Store Manager's Name has been entered
        if (StringUtils.isEmpty(StringUtils.trim(storeMgrName))) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.storemgrname.required");
        }
        
        // Verify that the person who completed the form has filled in their name
        if (StringUtils.isEmpty(StringUtils.trim(submittedBy))) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.inventoryadj.error.submittedby.required");
        }
    }
    
    /**
     * Writes out all properties
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nstoreNum: " + storeNum);
        buf.append("\nsubmittedBy: " + submittedBy);
        buf.append("\nexplanation: " + explanation);
        buf.append("\nreviewedByStoreMgr: " + reviewedByStoreMgr);
        buf.append("\nstoreMgrName: " + storeMgrName);
        return buf.toString();
    }

    /**
     * @return Returns the today.
     */
    public String getToday() {
        return today;
    }
    /**
     * @param today The today to set.
     */
    public void setToday(String today) {
        this.today = today;
    } 
    /**
     * @return Returns the storeNum.
     */
    public Integer getStoreNum() {
        return storeNum;
    }
    /**
     * @param storeNum The storeNum to set.
     */
    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }
    /**
     * @return Returns the submittedBy.
     */
    public String getSubmittedBy() {
        return submittedBy;
    }
    /**
     * @param submittedBy The submittedBy to set.
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
    /**
     * @return Returns the upc.
     */
    public String[] getUpc() {
        return upc;
    }
    /**
     * @param upc The upc to set.
     */
    public void setUpc(String[] upc) {
        this.upc = upc;
    }
    /**
     * @return Returns the quantity.
     */
    public String[] getQuantity() {
        return quantity;
    }
    /**
     * @param quantity The quantity to set.
     */
    public void setQuantity(String[] quantity) {
        this.quantity = quantity;
    }
    /**
     * @return Returns the price.
     */
    public String[] getPrice() {
        return price;
    }
    /**
     * @param price The price to set.
     */
    public void setPrice(String[] price) {
        this.price = price;
    }
    /**
     * @return Returns the description.
     */
    public String[] getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String[] description) {
        this.description = description;
    }
    /**
     * @return Returns the explanation.
     */
    public String getExplanation() {
        return explanation;
    }
    /**
     * @param explanation The explanation to set.
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    /**
     * @return Returns the reviewedByStoreMgr.
     */
    public boolean isReviewedByStoreMgr() {
        return reviewedByStoreMgr;
    }
    /**
     * @param reviewedByStoreMgr The reviewedByStoreMgr to set.
     */
    public void setReviewedByStoreMgr(boolean reviewedByStoreMgr) {
        this.reviewedByStoreMgr = reviewedByStoreMgr;
    }
    /**
     * @return Returns the storeMgrName.
     */
    public String getStoreMgrName() {
        return storeMgrName;
    }
    /**
     * @param storeMgrName The storeMgrName to set.
     */
    public void setStoreMgrName(String storeMgrName) {
        this.storeMgrName = storeMgrName;
    }
 }
