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
public class IronHorseBean extends GenericFormBean {
    
    private static final long serialVersionUID = 1L;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static int MAX_SIZE = 100;
    
    private String today;
    private String manager;
    private Store store;
    private boolean answer1;
    
    // customer information
    private String name;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String phone;
    
    private boolean[] selectedSizes;
    
    
    public void reset() {
        today = dateFormat.format(new Date());
        this.store = Utils.getStoreFromSession();
        manager = null;
        name = null;
        address = null;
        city = null;
        province = null;
        postalCode = null;
        phone = null;
        answer1 = false;
        selectedSizes = new boolean[MAX_SIZE];
        super.reset();
    }

    public String create() throws Exception {
        reset();
        return super.create();
    }
    
    public void validate() {
        ActionContext ctxt = ActionContext.getActionContext();
        
        if (manager == null || manager.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.manager.required");
        }
        
        if (name == null || name.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.name.required");
        }

        if (address == null || address.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.address.required");
        }

        if (city == null || city.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.city.required");
        }

        if (province == null || province.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.province.required");
        }

        if (phone == null || phone.trim().equals("")) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.phone.required");
        }
        
        // Check that at least one size is selected
        int count = 0;
        for (int i=0; i<MAX_SIZE; ++i) {
            if (selectedSizes[i]) {
                ++count;                
            }
        }        
        if (count == 0) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.ironhorse.error.size.required");
        }
    }
    
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nmanager: " + manager);
        buf.append("\nstoreName: " + store.getName());
        buf.append("\nphone: " + store.getPhoneNumber());
        buf.append("\ncity: " + store.getCity());
        buf.append("\nfax: " + store.getFaxNumber());
        buf.append("\nname: " + name);
        buf.append("\naddress: " + address);
        buf.append("\ncity: " + city);
        buf.append("\nprovince: " + province);
        buf.append("\npostalCode: " + postalCode);
        buf.append("\nphone: " + phone);
        return buf.toString();
    }
    
    
    /**
     * @return Returns the address.
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return Returns the city.
     */
    public String getCity() {
        return city;
    }
    /**
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the phone.
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone The phone to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return Returns the postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @param postalCode The postalCode to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return Returns the province.
     */
    public String getProvince() {
        return province;
    }
    /**
     * @param province The province to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }
    /**
     * @return Returns the selectedSizes.
     */
    public boolean[] getSelectedSizes() {
        return selectedSizes;
    }
    /**
     * @param selectedSizes The selectedSizes to set.
     */
    public void setSelectedSizes(boolean[] selectedSizes) {
        this.selectedSizes = selectedSizes;
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
}
