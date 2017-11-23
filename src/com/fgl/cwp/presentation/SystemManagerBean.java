/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 28, 2005
 */
package com.fgl.cwp.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionMessages;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.persistence.services.FileSystemService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class SystemManagerBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;

    private String date;
    
    /**
     * 
     * @return
     */
    public String deleteForms() {
        String success = Constants.FAILURE;
        
        ActionContext ctxt = ActionContext.getActionContext();
        
        String dirPath = ctxt.getResources().getMessage("forms.filepath");
        String dateFormatString = ctxt.getResources().getMessage("default.dateformat");

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);            
            FileSystemService.getInstance().deleteFilesOlderThanDate(dirPath, dateFormat.parse(date));
            success = Constants.SUCCESS;
        } catch (ParseException pe) {
            pe.printStackTrace();
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "admin.system.error.invaliddateformat", new Object[]{dateFormatString});
        }
        
        return success;
    }
    
    

    /**
     * @return Returns the date.
     */
    public String getDate() {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }
}
