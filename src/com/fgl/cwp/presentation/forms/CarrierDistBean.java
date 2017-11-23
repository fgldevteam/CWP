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
public class CarrierDistBean extends GenericFormBean {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(CarrierDistBean.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static int ARRAY_LENGTH = 4;

    private String today;
    private Integer storeNum;
    private String[] carton;
    private String[] dds;
    private boolean answer1;
    private boolean answer1a;
    private boolean answer2;
    private boolean answer3;
    private boolean answer4;
    private boolean answer5;
    private boolean answer6;
    private String answer4a;
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
        log.debug("CarrierDistBean(): reset()");
        
        today = dateFormat.format(new Date());        
        Store store = Utils.getStoreFromSession();
        if (store!=null) {
            storeNum = new Integer(store.getNumber());
        }
        
        carton = new String[ARRAY_LENGTH];
        dds = new String[ARRAY_LENGTH];
        answer1 = false;
        answer1a = false;
        answer2 = false;
        answer3 = false;
        answer4 = false;
        answer4a = null;
        answer5 = false;
        answer6 = false;
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
        log.debug("CarrierDistBean(): create()");

        reset();
        return super.create();
    }
    

    /**
     * Ensure all required form fields are filled out
     */
    public void validate() {
        log.debug("CarrierDistBean(): validate()");
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
        
        if (answer4) {
            if (StringUtils.isEmpty(answer4a)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.carrierdist.error.q4a.required");
            }
        }
        
        if (answer6) {
            if (StringUtils.isEmpty(reportedTo)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.carrierdist.error.q6a.reportedto.required");
            }
            if (StringUtils.isEmpty(dateReported)) {
                ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.carrierdist.error.q6a.datereported.required");                
            }
        }
        
        if (StringUtils.isEmpty(storeMgrName)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.carrierdist.error.storemgrname.required");
        }
        
        if (StringUtils.isEmpty(submittedBy)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.carrierdist.error.submittedby.required");
        }

    }
    
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nstoreNum: " + storeNum);
        buf.append("\nanswer1: " + answer1);
        buf.append("\nanswer1a: " + answer1a);
        buf.append("\nanswer2: " + answer2);
        buf.append("\nanswer2a: " + answer4a);
        buf.append("\nanswer3: " + answer3);
        buf.append("\nreportedTo: " + reportedTo);
        buf.append("\ndateReported: " + dateReported);
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
     * @return Returns the answer1a.
     */
    public boolean isAnswer1a() {
        return answer1a;
    }
    /**
     * @param answer1a The answer1a to set.
     */
    public void setAnswer1a(boolean answer1a) {
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
    
	public boolean isAnswer4() {
		return answer4;
	}

	public void setAnswer4(boolean answer4) {
		this.answer4 = answer4;
	}
	
	public String getAnswer4a() {
		return answer4a;
	}

	public void setAnswer4a(String answer4a) {
		this.answer4a = answer4a;
	}

	public boolean isAnswer5() {
		return answer5;
	}

	public void setAnswer5(boolean answer5) {
		this.answer5 = answer5;
	}

	public boolean isAnswer6() {
		return answer6;
	}

	public void setAnswer6(boolean answer6) {
		this.answer6 = answer6;
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
