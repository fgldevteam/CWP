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
public class DamagedCartonBean extends GenericFormBean {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(DamagedCartonBean.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static int ARRAY_LENGTH = 4;

    private String today;
    private Integer storeNum;
    private String[] carton;
    private String[] dds;
    private boolean answer1;
    private String answer1a;
    private boolean answer2;
    private boolean answer3;
    private boolean answer3a;
    private String answer4;
    private String answer5;
    private boolean answer6;
    private boolean answer7;
    private boolean answer8;
    private boolean answer10;
    private boolean answer11;
    private String answer11a;
    private String reportedTo;
    private String dateReported;
    private String comments;
    private boolean reviewedByStoreMgr;
    private String storeMgrName;
    private String submittedBy;
        
    
    /**
     * Reset all fields to appropriate defaults
     */
    public void reset() {
        log.debug("DamagedCartonBean(): reset()");
        
        today = dateFormat.format(new Date());

        Store store = Utils.getStoreFromSession();
        if (store!=null) {
            storeNum = new Integer(store.getNumber());
        }
        
        carton = new String[ARRAY_LENGTH];
        dds = new String[ARRAY_LENGTH];
        answer1 = false;
        answer1a = null;
        answer2 = false;
        answer3 = false;
        answer3a = false;
        answer4 = null;
        answer5 = null;
        answer6 = false;
        answer7 = false;
        answer8 = false;
        answer10 = false;
        answer11 = false;
        answer11a = null;
        reportedTo = null;
        dateReported = null;
        comments = null;
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
        log.debug("DamagedCartonBean(): create()");
        reset();
        return super.create();
    }
    

    /**
     * Ensure all required form fields are filled out
     */
    public void validate() {
        log.debug("DamagedCartonBean(): validate()");

        ActionContext ctxt = ActionContext.getActionContext();
        int count = 0;
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (carton[i] != null && !carton[i].trim().equals("")
                    && dds[i] != null && !dds[i].trim().equals("")) {
                ++count;
            }
        }
        
        if (count==0) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.claim.error.carton.dds.min");
        }
                
        if (!answer1) {
            if (StringUtils.isEmpty(answer1a)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.damagedcartons.error.q1a.required");
            }            
        }
        
        if (answer7) {
            if (StringUtils.isEmpty(reportedTo)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.damagedcartons.error.q7a.reportedto.required");
            }
            if (StringUtils.isEmpty(dateReported)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.damagedcartons.error.q7a.datereported.required");                
            }
        }
        
        if (answer11) {
            if (StringUtils.isEmpty(answer11a)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.damagedcartons.error.q11a.required");
            }
        }
        
        if (StringUtils.isEmpty(storeMgrName)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.damagedcartons.error.storemgrname.required");
        }
        
        if (StringUtils.isEmpty(submittedBy)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.damagedcartons.error.submittedby.required");
        }
    }
    
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nstoreNum: " + storeNum);
        buf.append("\nanswer1: " + answer1);
        buf.append("\nanswer1a: " + answer1a);
        buf.append("\nanswer2: " + answer2);
        buf.append("\nanswer3: " + answer3);
        buf.append("\nanswer3a: " + answer3a);
        buf.append("\nanswer4: " + answer4);
        buf.append("\nanswer5: " + answer5);
        buf.append("\nanswer6: " + answer6);
        buf.append("\nreportedTo: " + reportedTo);
        buf.append("\ndateReported: " + dateReported);
        buf.append("\nanswer8: " + answer8);
        buf.append("\nanswer10: " + answer10);
        buf.append("\nanswer11: " + answer11);
        buf.append("\nanswer11a: " + answer11a);
        buf.append("\ncomments: " + comments);
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
     * @return Returns the answer10.
     */
    public boolean isAnswer10() {
        return answer10;
    }
    /**
     * @param answer10 The answer10 to set.
     */
    public void setAnswer10(boolean answer10) {
        this.answer10 = answer10;
    }
    /**
     * @return Returns the answer11.
     */
    public boolean isAnswer11() {
        return answer11;
    }
    /**
     * @param answer11 The answer11 to set.
     */
    public void setAnswer11(boolean answer11) {
        this.answer11 = answer11;
    }
    /**
     * @return Returns the answer11a.
     */
    public String getAnswer11a() {
        return answer11a;
    }
    /**
     * @param answer11a The answer11a to set.
     */
    public void setAnswer11a(String answer11a) {
        this.answer11a = answer11a;
    }
    /**
     * @return Returns the answer1a.
     */
    public String getAnswer1a() {
        return answer1a;
    }
    /**
     * @param answer1a The answer1a to set.
     */
    public void setAnswer1a(String answer1a) {
        this.answer1a = answer1a;
    }
    /**
     * @return Returns the answer2.
     */
    public boolean isAnswer2() {
        return answer2;
    }
    /**
     * @param answer2 The answer2 to set.
     */
    public void setAnswer2(boolean answer2) {
        this.answer2 = answer2;
    }
    /**
     * @return Returns the answer3.
     */
    public boolean isAnswer3() {
        return answer3;
    }
    /**
     * @param answer3 The answer3 to set.
     */
    public void setAnswer3(boolean answer3) {
        this.answer3 = answer3;
    }
    /**
     * @return Returns the answer3a.
     */
    public boolean isAnswer3a() {
        return answer3a;
    }
    /**
     * @param answer3a The answer3a to set.
     */
    public void setAnswer3a(boolean answer3a) {
        this.answer3a = answer3a;
    }
    /**
     * @return Returns the answer4.
     */
    public String getAnswer4() {
        return answer4;
    }
    /**
     * @param answer4 The answer4 to set.
     */
    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }
    /**
     * @return Returns the answer5.
     */
    public String getAnswer5() {
        return answer5;
    }
    /**
     * @param answer5 The answer5 to set.
     */
    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }
    /**
     * @return Returns the answer6.
     */
    public boolean isAnswer6() {
        return answer6;
    }
    /**
     * @param answer6 The answer6 to set.
     */
    public void setAnswer6(boolean answer6) {
        this.answer6 = answer6;
    }
    /**
     * @return Returns the answer7.
     */
    public boolean isAnswer7() {
        return answer7;
    }
    /**
     * @param answer7 The answer7 to set.
     */
    public void setAnswer7(boolean answer7) {
        this.answer7 = answer7;
    }
    /**
     * @return Returns the answer8.
     */
    public boolean isAnswer8() {
        return answer8;
    }
    /**
     * @param answer8 The answer8 to set.
     */
    public void setAnswer8(boolean answer8) {
        this.answer8 = answer8;
    }
    /**
     * @return Returns the carton.
     */
    public String[] getCarton() {
        return carton;
    }
    /**
     * @param carton The carton to set.
     */
    public void setCarton(String[] carton) {
        this.carton = carton;
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
     * @return Returns the dateReported.
     */
    public String getDateReported() {
        return dateReported;
    }
    /**
     * @param dateReported The dateReported to set.
     */
    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }
    /**
     * @return Returns the dds.
     */
    public String[] getDds() {
        return dds;
    }
    /**
     * @param dds The dds to set.
     */
    public void setDds(String[] dds) {
        this.dds = dds;
    }
    /**
     * @return Returns the reportedTo.
     */
    public String getReportedTo() {
        return reportedTo;
    }
    /**
     * @param reportedTo The reportedTo to set.
     */
    public void setReportedTo(String reportedTo) {
        this.reportedTo = reportedTo;
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
