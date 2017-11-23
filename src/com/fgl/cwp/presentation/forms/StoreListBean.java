/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Apr 25, 2005
 */
package com.fgl.cwp.presentation.forms;

import java.util.List;

import com.fgl.cwp.persistence.services.StoreService;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class StoreListBean extends BaseBean {
	private static final long serialVersionUID = 1L;
    private List stores;
    
    /**
     * Read all the stores from the databse. 
     */
    public StoreListBean() {
        try {
            stores = StoreService.getInstance().getAllStores();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return Returns the stores.
     */
    public List getStores() {
        return stores;
    }
}
