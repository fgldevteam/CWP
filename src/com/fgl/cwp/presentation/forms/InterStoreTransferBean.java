/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 15, 2005
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
 * @author bting
 */
public class InterStoreTransferBean extends GenericFormBean {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(InterStoreTransferBean.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static int ARRAY_LENGTH = 4;

    private String today;
    private Integer storeNum;
    
    private String sendingStoreContact;
    private String[] shipDate;
    private String[] jda;
    private String[] hoRef;
    private String[] sendingStore;
    private String[] courierPIN;
    
    private boolean answer1;
    private String traceNum;
    private String contact;
    private String dateTraceOpened;
    private boolean puroBol;
    private boolean transferSlip;
    private int totalPagesSent;
    
    private String comments;
    private boolean reviewedByStoreMgr;
    private String storeMgrName;
    private String submittedBy;
    
    
    /**
     * Reset all fields to appropriate defaults
     */
    public void reset() {
        log.debug("InterStoreTransferBean(): reset()");
        
        today = dateFormat.format(new Date());

        Store store = Utils.getStoreFromSession();
        if (store!=null) {
            storeNum = new Integer(store.getNumber());
        }
        
        sendingStoreContact = null;
        shipDate = new String[ARRAY_LENGTH];
        jda = new String[ARRAY_LENGTH];
        hoRef = new String[ARRAY_LENGTH];
        sendingStore = new String[ARRAY_LENGTH];
        courierPIN = new String[ARRAY_LENGTH];        
        answer1 = false;
        traceNum = null;
        contact = null;
        dateTraceOpened = null;
        comments = null;
        puroBol = false;
        transferSlip = false;
        totalPagesSent = 0;
        reviewedByStoreMgr = false;
        storeMgrName = null;
        submittedBy = null;
        super.reset();
    }

    /**
     * Create a new form
     * @return
     * @throws Exception
     */
    public String create() throws Exception {
        log.debug("InterStoreTransferBean(): create()");
        reset();
        
        return super.create();
    }
    

    /**
     * Ensure all required form fields are filled out
     */
    public void validate() {
        log.debug("InterStoreTransferBean(): validate()");
        ActionContext ctxt = ActionContext.getActionContext();

        int count = 0;
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (shipDate[i] != null && !shipDate[i].trim().equals("")
                    && jda[i] != null && !jda[i].trim().equals("")
                    && hoRef[i] != null && !hoRef[i].trim().equals("")
                    && sendingStore[i] != null && !sendingStore[i].trim().equals("")
                    && courierPIN[i] != null && !courierPIN[i].trim().equals("")) {
                ++count;
            }
        }
        
        if (answer1) {
            if (StringUtils.isEmpty(traceNum)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.interstoretransfer.error.tracenum.required");
            }
            if (StringUtils.isEmpty(contact)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.interstoretransfer.error.contact.required");
            }
            if (StringUtils.isEmpty(dateTraceOpened)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.interstoretransfer.error.datetraceopened.required");
            }
        }
        if (count==0) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.interstoretransfer.error.shipdate.min");
        }
        
        
        if (StringUtils.isEmpty(storeMgrName)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.interstoretransfer.error.storemgrname.required");
        }
        
        if (StringUtils.isEmpty(submittedBy)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.interstoretransfer.error.submittedby.required");
        }

    }
    
        
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nstoreNum: " + storeNum);
        buf.append("\nsendingStoreContact: " + sendingStoreContact);
        buf.append("\nanswer1: " + answer1);
        buf.append("\ntraceNum: " + traceNum);
        buf.append("\ncontact: " + contact);
        buf.append("\ndateTraceOpened: " + dateTraceOpened);
        buf.append("\ncomments: " + comments);
        buf.append("\npuroBol? " + puroBol);
        buf.append("\ntransferSlip? " + transferSlip);
        buf.append("\ntotalPagesSent: " + totalPagesSent);
        buf.append("\nreviewedByStoreMgr: " + reviewedByStoreMgr);
        buf.append("\nstoreMgrName: " + storeMgrName);
        buf.append("\nsubmittedBy: " + submittedBy);
        return buf.toString();
    }
    /**
     * @return Returns the answer1.
     */
    public boolean isAnswer1() {
        return answer1;
    }
    /**
     * @param answer1 The answer1 to set.
     */
    public void setAnswer1(boolean answer1) {
        this.answer1 = answer1;
    }
    /**
     * @return Returns the comments.
     */
    public String getComments() {
        return comments;
    }
    /**
     * @param comments The comments to set.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * @return Returns the contact.
     */
    public String getContact() {
        return contact;
    }
    /**
     * @param contact The contact to set.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * @return Returns the courierPIN.
     */
    public String[] getCourierPIN() {
        return courierPIN;
    }
    /**
     * @param courierPIN The courierPIN to set.
     */
    public void setCourierPIN(String[] courierPIN) {
        this.courierPIN = courierPIN;
    }
    /**
     * @return Returns the dateTraceOpened.
     */
    public String getDateTraceOpened() {
        return dateTraceOpened;
    }
    /**
     * @param dateTraceOpened The dateTraceOpened to set.
     */
    public void setDateTraceOpened(String dateTraceOpened) {
        this.dateTraceOpened = dateTraceOpened;
    }
    /**
     * @return Returns the hoRef.
     */
    public String[] getHoRef() {
        return hoRef;
    }
    /**
     * @param hoRef The hoRef to set.
     */
    public void setHoRef(String[] hoRef) {
        this.hoRef = hoRef;
    }
    /**
     * @return Returns the jda.
     */
    public String[] getJda() {
        return jda;
    }
    /**
     * @param jda The jda to set.
     */
    public void setJda(String[] jda) {
        this.jda = jda;
    }
    /**
     * @return Returns the puroBol.
     */
    public boolean isPuroBol() {
        return puroBol;
    }
    /**
     * @param puroBol The puroBol to set.
     */
    public void setPuroBol(boolean puroBol) {
        this.puroBol = puroBol;
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
     * @return Returns the sendingStore.
     */
    public String[] getSendingStore() {
        return sendingStore;
    }
    /**
     * @param sendingStore The sendingStore to set.
     */
    public void setSendingStore(String[] sendingStore) {
        this.sendingStore = sendingStore;
    }
    /**
     * @return Returns the sendingStoreContact.
     */
    public String getSendingStoreContact() {
        return sendingStoreContact;
    }
    /**
     * @param sendingStoreContact The sendingStoreContact to set.
     */
    public void setSendingStoreContact(String sendingStoreContact) {
        this.sendingStoreContact = sendingStoreContact;
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
     * @return Returns the shipDate.
     */
    public String[] getShipDate() {
        return shipDate;
    }
    /**
     * @param shipDate The shipDate to set.
     */
    public void setShipDate(String[] shipDate) {
        this.shipDate = shipDate;
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
     * @return Returns the totalPagesSent.
     */
    public int getTotalPagesSent() {
        return totalPagesSent;
    }
    /**
     * @param totalPagesSent The totalPagesSent to set.
     */
    public void setTotalPagesSent(int totalPagesSent) {
        this.totalPagesSent = totalPagesSent;
    }
    /**
     * @return Returns the traceNum.
     */
    public String getTraceNum() {
        return traceNum;
    }
    /**
     * @param traceNum The traceNum to set.
     */
    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum;
    }
    /**
     * @return Returns the transferSlip.
     */
    public boolean isTransferSlip() {
        return transferSlip;
    }
    /**
     * @param transferSlip The transferSlip to set.
     */
    public void setTransferSlip(boolean transferSlip) {
        this.transferSlip = transferSlip;
    }
}
