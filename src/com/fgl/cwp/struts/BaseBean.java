package com.fgl.cwp.struts;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;

/**
 * All actions mapped through the BeanAction class should be mapped
 * to a subclass of BaseBean (or have no form bean mapping at all).
 * <p/>
 * The BaseBean class simplifies the validate() and reset() methods
 * by allowing them to be managed without Struts dependencies. Quite
 * simply, subclasses can override the parameterless validate()
 * and reset() methods and set errors and messages using the ActionContext
 * class.
 * <p/>
 * Date: Mar 12, 2004 9:20:39 PM
 * @author Clinton Begin, David Duffy
 */
public abstract class BaseBean extends ActionForm {

    public void reset(ActionMapping mapping, ServletRequest request) {
        reset(mapping, (HttpServletRequest)request);
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        ActionContext.initialize(mapping, request, null, null);
        reset();
    }

    public ActionErrors validate(ActionMapping mapping, ServletRequest request) {
        return validate(mapping, (HttpServletRequest)request);
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionContext.initialize(mapping, request, null, null);
        ActionContext ctx = ActionContext.getActionContext();

        validate();

        return ctx.getErrors();
    }

    /**
     * 
     */
    public void validate() {
        //empty class
    }

    /**
     * 
     */
    public void reset() {
        //empty class
    }

    /**
     * 
     */
    public void clear() {
        //empty class
    }

    /**
     * Tests if the specified String is null or empty.
     * @param test the String to test
     * @return true if the specified String is null or empty.
     */
    public static boolean isEmpty(String test) {
        return (test == null || test.trim().length() <= 0);
    }
    
    protected boolean validateSession(HttpServletRequest request){		
		ActionContext context = ActionContext.getActionContext();
		User user  = (User)context.getRequest().getSession().getAttribute(Constants.USER);
		Store store = (Store)context.getRequest().getSession().getAttribute(Constants.STORE);
		if( user != null || store != null)
			return true;
		else{
			ActionContext.getActionContext().addError("error", "session.timeout.message");
			return false;
		}
	}

}