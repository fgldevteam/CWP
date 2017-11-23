/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Apr 11, 2005
 */
package com.fgl.cwp.presentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.persistence.services.StoreService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class StoreDetailsBean extends BaseBean {
    private static final long serialVersionUID = 1L;
    
    private static Log log = LogFactory.getLog(StoreDetailsBean.class);
    private int storeNumber;
    private Store store;
    
    /**
     * Return all store information given store number.
     * @return
     */
    public String getStoreDetails() {
        log.debug("In getStore...number: " + storeNumber);

        boolean isError = false;
        ActionContext context = ActionContext.getActionContext();
        try {
            store = StoreService.getInstance().getStoreById(new Integer(storeNumber));
        } catch (Exception e) {
            isError = true;
            log.error("Error retrieving the store info...", e);
        }
        if (store == null || isError) {
            context.addError("error", "product.details.error.loading.store");            
        }

        return Constants.SUCCESS;
    }

    /**
     * @return Returns the storeNumber.
     */
    public int getStoreNumber() {
        return storeNumber;
    }
    
    /**
     * @param storeNumber The storeNumber to set.
     */
    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }
    
    /**
     * @return Returns the store.
     */
    public Store getStore() {
        return store;
    }   
}
