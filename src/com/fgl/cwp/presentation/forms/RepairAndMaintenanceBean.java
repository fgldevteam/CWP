/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 28, 2005
 */
package com.fgl.cwp.presentation.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionMessages;

import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.struts.ActionContext;

/**
 * @author bting
 */
public class RepairAndMaintenanceBean extends GenericFormBean {
    
    private static final long serialVersionUID = 1L;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    private String today;
    private String manager;
    private String fax;
    private Store store;
    private String workDetail;

    
    public void reset() {
        today = dateFormat.format(new Date());
        this.store = Utils.getStoreFromSession();
        manager = null;

        // set fax to store's fax number
        if (this.store!=null) {
            fax = this.store.getFaxNumber();
        }
        workDetail = null;
        super.reset();
    }

    public String create() throws Exception {
        reset();
        return super.create();
    }
    
    public void validate() {
        ActionContext ctxt = ActionContext.getActionContext();
        
        if (fax == null || fax.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.repairandmaintain.error.fax.required");
        }
    }
    
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nmanager: " + manager);
        buf.append("\nfax: " + fax);
        buf.append("\nstoreName: " + store.getName());
        buf.append("\nphone: " + store.getPhoneNumber());
        buf.append("\ncity: " + store.getCity());
        buf.append("\nfax: " + store.getFaxNumber());
        buf.append("\nworkDetail: " + workDetail);
        return buf.toString();
    }
    
    
    /**
     * @return Returns the fax.
     */
    public String getFax() {
        return fax;
    }
    /**
     * @param fax The fax to set.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @return Returns the manager.
     */
    public String getManager() {
        return manager;
    }
    /**
     * @param manager The manager to set.
     */
    public void setManager(String manager) {
        this.manager = manager;
    }
    /**
     * @return Returns the store.
     */
    public Store getStore() {
        return store;
    }
    /**
     * @param store The store to set.
     */
    public void setStore(Store store) {
        this.store = store;
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
     * @return Returns the workDetail.
     */
    public String getWorkDetail() {
        return workDetail;
    }
    /**
     * @param workDetail The workDetail to set.
     */
    public void setWorkDetail(String workDetail) {
        this.workDetail = workDetail;
    }
}
