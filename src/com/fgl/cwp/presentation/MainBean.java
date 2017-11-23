package com.fgl.cwp.presentation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.persistence.services.DocumentService;
import com.fgl.cwp.persistence.services.NoticeService;
import com.fgl.cwp.persistence.services.PricingIssueService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author Jessica Wong
 */
public class MainBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;
    
    private List featuredDocuments = new ArrayList();
    private List notices = null;
    private List pricingIssues = null;
    
    private static Log log = LogFactory.getLog(MainBean.class);
    
    /**
     * @return
     */
    public String loadMainPage(){
        
        ActionContext context = ActionContext.getActionContext();
        
        try{
            featuredDocuments = DocumentService.getInstance().getFeaturedDocuments();
            notices = NoticeService.getInstance().getNotices();
            pricingIssues = PricingIssueService.getInstance().getPricingIssues();
            log.debug("\nNumber of Docs: " + featuredDocuments.size());
        } catch (Exception e){
            context.addError("error",  "main.error.gettingfeatureddocsornotices");
            e.printStackTrace();
        }
        
        return "success";
    }
    
    
    /**
     * @return
     */
    public List getFeaturedDocuments() {
        return featuredDocuments;
    }
    
    /**
     * @param featuredDocuments
     */
    public void setFeaturedDocuments(List featuredDocuments) {
        this.featuredDocuments = featuredDocuments;
    }
    
    /**
     * @return
     */
    public List getNotices() {
        return notices;
    }
    
    /**
     * @param notices
     */
    public void setNotices(List notices) {
        this.notices = notices;
    }


    /**
     * @return Returns the pricingIssues.
     */
    public List getPricingIssues() {
        return pricingIssues;
    }
    
}
