/*
 * Created on Nov 21, 2005
 */
package com.fgl.cwp.presentation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.ParameterizedActionForward;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Functionality;
import com.fgl.cwp.model.PricingIssue;
import com.fgl.cwp.persistence.services.PricingIssueService;
import com.fgl.cwp.struts.ActionConstants;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author urobertson
 */
public class ManagePricingIssueBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(ManagePricingIssueBean.class);

    private List pricingIssues;

    private PricingIssue pricingIssue;

    private Integer pricingIssueIndex;

    /**
     * @return Returns the pricingIssue.
     */
    public PricingIssue getPricingIssue() {
        return pricingIssue;
    }

    /**
     * @param pricingIssue
     *            The pricingIssue to set.
     */
    public void setPricingIssue(PricingIssue pricingIssue) {
        this.pricingIssue = pricingIssue;
    }

    /**
     * @return Returns the pricingIssueIndex.
     */
    public Integer getPricingIssueIndex() {
        return pricingIssueIndex;
    }

    /**
     * @param pricingIssueIndex
     *            The pricingIssueIndex to set.
     */
    public void setPricingIssueIndex(Integer pricingIssueIndex) {
        this.pricingIssueIndex = pricingIssueIndex;
    }

    /**
     * @param pricingIssues
     *            The pricingIssues to set.
     */
    public void setPricingIssues(List pricingIssues) {
        this.pricingIssues = pricingIssues;
    }

    /**
     * @return Returns the pricingIssues.
     */
    public List getPricingIssues() {
        return pricingIssues;
    }

    public void reset() {
        if (pricingIssue != null) {
            pricingIssue.setClosedDate(null);
        }
    }

    /**
     * Load all pricing issues.
     * @return Returns Success or Failure
     * @throws Exception
     */
    public String loadPricingIssues() throws Exception {

        log.debug("PricingIssueBean: loading Pricing Issues");

        Utils.checkFunction(Functionality.VIEW_PRICING_ISSUES);

        pricingIssues = PricingIssueService.getInstance().getPricingIssues();

        return Constants.SUCCESS;

    }

    /**
     * Close a pricing issue in the database.
     * @return Returns Success or Failure
     * @throws Exception
     */
    public String closePricingIssue() throws Exception {

        Utils.checkFunction(Functionality.EDIT_PRICING_ISSUES);

        String result = Constants.FAILURE;

        if (pricingIssueIndex != null) {
            int idx = pricingIssueIndex.intValue();

            if (idx >= 0 && idx < pricingIssues.size()) {

                PricingIssueService.getInstance().closePricingIssue((PricingIssue) pricingIssues.get(idx));
                ActionContext.getActionContext().addMessage("status", "admin.pricingissues.status.closePricingIssue.success");

                ActionContext actionCtxt = ActionContext.getActionContext();

                HttpServletRequest request = actionCtxt.getRequest();

                // Create an dynamic action forward
                ParameterizedActionForward downloadFwd = new ParameterizedActionForward(actionCtxt.getMapping().findForward(Constants.SUCCESS));
                downloadFwd.setRedirect(true);

                request.setAttribute(ActionConstants.DYNAMIC_FORWARD, downloadFwd);
                result = null;
            }
        }
        return result;

    }

    /**
     * Read only view of the Pricing Issue.
     * @return Success or Failure
     */
    public String viewPricingIssue() {

        String result = Constants.FAILURE;

        if (pricingIssueIndex != null) {
            int idx = pricingIssueIndex.intValue();
            if (idx >= 0 && idx < pricingIssues.size()) {
                pricingIssue = (PricingIssue) pricingIssues.get(idx);
                result = Constants.SUCCESS;
            }
        }

        return result;
    }
}
