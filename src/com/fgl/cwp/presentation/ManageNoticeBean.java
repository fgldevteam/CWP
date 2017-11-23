/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Feb 7, 2005
 */
package com.fgl.cwp.presentation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.exception.InsufficientPrivilege;
import com.fgl.cwp.model.Functionality;
import com.fgl.cwp.model.Notice;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.NoticeService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class ManageNoticeBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private static Log log = LogFactory.getLog(ManageNoticeBean.class);

    private List notices;
    private Notice notice;
    private Integer noticeIndex;
    
    public void reset() {
        log.debug("reset():start");
        if (notice != null) {
            notice.setAttachment(null);
        }
        log.debug("reset():end");
    }
    
    
    /**
     * Create a new notice.
     * @return
     * @throws InsufficientPrivilege
     */
    public String createNotice() throws InsufficientPrivilege {
        
        Utils.checkFunction(Functionality.CREATE_NOTICES);
        
        notice = new Notice();
        notice.setUsername(getUserName());
        return Constants.SUCCESS;
    }

    /**
     * Edit an existing notice.
     * @return String
     * @throws InsufficientPrivilege
     */
    public String editNotice() throws InsufficientPrivilege {
        
        Utils.checkFunction(Functionality.EDIT_NOTICES);
        
        String result = Constants.FAILURE;
        if (noticeIndex != null) {
            int idx = noticeIndex.intValue();
            if (idx >=0 && idx < notices.size()) {
                notice = (Notice)notices.get(idx);
                result = Constants.SUCCESS;
            }
        }
        return result;
    }
    
    
    /**
     * Load all notices.
     * @return
     * @throws Exception
     */
    public String loadNotices() throws Exception {
        
        Utils.checkFunction(Functionality.VIEW_NOTICES);
        
        notices = NoticeService.getInstance().getNotices();
        if (notices.isEmpty()) {
            // If there are no notices, jump to the add notice page
            createNotice();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    /**
     * Save a notice.
     * @return
     * @throws Exception
     */
    public String saveNotice() throws Exception {

        Utils.checkFunction(Functionality.EDIT_NOTICES);
        
        if (validateSave()) {  
        	notice.setUsername(getUserName());
            if (NoticeService.getInstance().saveNotice(notice)) {
                ActionContext.getActionContext().addMessage("status", "status.writeToDB.success");
                return Constants.SUCCESS;
            }
        }
        return Constants.FAILURE;
    }
    
    /**
     * Validate a notice before saving.
     * @return boolean
     */
    private boolean validateSave() {

        boolean success = true;
        if (notice != null) {
            ActionContext ctxt = ActionContext.getActionContext();
            if (notice.getName() == null
                    || StringUtils.isEmpty(notice.getName().trim())) {
                ctxt.addError("error", "admin.notice.edit.error.name.required");
                success = false;
            }
            if (notice.getDescription() == null
                    || StringUtils.isEmpty(notice.getDescription().trim())) {
                ctxt.addError("error", "admin.notice.edit.error.description.required");
                success = false;
            }
            if (notice.isNew()
                    && (notice.getAttachment() == null
                            || notice.getAttachment().getFileName() == null
                            || StringUtils.isEmpty(notice.getAttachment().getFileName().trim()))) {
                ctxt.addError("error", "attachment.error.file.required");
                success = false;
            }
        }

        return success;
    }

    /**
     * Delete a notice.
     * @return
     * @throws Exception
     */
    public String deleteNotice() throws Exception {

        Utils.checkFunction(Functionality.DELETE_NOTICES);
        
        String result = Constants.FAILURE;

        if (noticeIndex != null) {
            int idx = noticeIndex.intValue();

            if (idx >=0 && idx < notices.size()) {
                NoticeService.getInstance().deleteNotice((Notice)notices.get(idx));
                ActionContext.getActionContext().addMessage("status", "status.deleteFromDB.success");
                result = Constants.SUCCESS;
            }
        }

        return result;
    }
    
    
    
    /**
     * @return Returns the notice.
     */
    public Notice getNotice() {
        return notice;
    }
    /**
     * @return Returns the notices.
     */
    public List getNotices() {
        return notices;
    }
    /**
     * @param noticeIndex The noticeIndex to set.
     */
    public void setNoticeIndex(Integer noticeIndex) {
        this.noticeIndex = noticeIndex;
    }
    
    private String getUserName(){
    	User user = Utils.getUserFromSession();
    	return user.getFirstName()+" "+user.getLastName();
    }
}
