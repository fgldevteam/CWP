package com.fgl.cwp.struts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.fgl.cwp.struts.httpmap.ApplicationMap;
import com.fgl.cwp.struts.httpmap.CookieMap;
import com.fgl.cwp.struts.httpmap.ParameterMap;
import com.fgl.cwp.struts.httpmap.RequestMap;
import com.fgl.cwp.struts.httpmap.SessionMap;

/**
 * The ActionContext class gives simplified, thread-safe access to
 * the request and response, as well as form parameters, request
 * attributes, session attributes, application attributes.  Much
 * of this can be accopmplished without using the Struts or even
 * the Servlet API, therefore isolating your application from
 * presentation framework details.
 * <p/>
 * Date: Mar 9, 2004 9:57:39 PM
 * @author Clinton Begin, David Duffy
 */
public class ActionContext {

    private static final ThreadLocal localContext = new ThreadLocal();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private Map cookies;
    private Map requestParameters;
    private Map requestAttributes;
    private Map sessionAttributes;
    private Map applicationAttributes;

    private ActionErrors errors;
    private ActionMessages msgs;
    private MessageResources resources;
    private ActionMapping mapping;

    private ActionContext() {
        //empty constructor
        }

    /**
     * Initialize this instance.
     * @param mapping
     * @param request
     * @param response
     * @param resources
     */
    public static void initialize(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, MessageResources resources) {
        ActionContext ctx = getActionContext();
        ctx.mapping = mapping;
        ctx.request = request;
        ctx.response = response;
        ctx.cookies = null;
        ctx.requestParameters = null;
        ctx.requestAttributes = null;
        ctx.sessionAttributes = null;
        ctx.applicationAttributes = null;
        ctx.errors = null;
        ctx.msgs = null;
        ctx.resources = resources;
    }

    /**
     * Adds an error message to the current list of application errors encountered while processing this thread.
     * @param property the property the error is associated with
     * @param messageKey the resource key to the error message
     */
    public void addError(String property, String messageKey) {
        if (errors == null) {
            errors = new ActionErrors();
        }
        errors.add(property, new ActionMessage(messageKey));
    }

    /**
     * Adds an error message to the current list of application errors encountered while processing this thread.
     * @param property the property the error is associated with
     * @param messageKey the resource key to the error message
     * @param parameters any runtime parameters to fill in details in the error message with
     */
    public void addError(String property, String messageKey, Object[] parameters) {
        if (errors == null) {
            errors = new ActionErrors();
        }
        errors.add(property, new ActionMessage(messageKey, parameters));
    }
    
    /**
     * Add message to the current list of application messages for this thread.
     * @param property
     * @param messageKey
     */
    public void addMessage(String property, String messageKey) {
        if (msgs == null) {
            msgs = new ActionMessages();
        }
        msgs.add(property, new ActionMessage(messageKey));
    }
    /**
     * Add message to the current list of application messages for this thread.
     * @param property
     * @param messageKey
     * @param parameters
     */
    public void addMessage(String property, String messageKey, Object[] parameters) {
        if (msgs == null) {
            msgs = new ActionMessages();
        }
        msgs.add(property, new ActionMessage(messageKey, parameters));
    }

    /**
     * @return
     */
    public Map getCookies() {
        if (cookies == null) {
            cookies = new CookieMap(request);
        }
        return cookies;
    }

    /**
     * @return
     */
    public Map getRequestParameters() {
        if (requestParameters == null) {
            requestParameters = new ParameterMap(request);
        }
        return requestParameters;
    }

    /**
     * @return
     */
    public Map getRequestAttributes() {
        if (requestAttributes == null) {
            requestAttributes = new RequestMap(request);
        }
        return requestAttributes;
    }

    /**
     * @return
     */
    public Map getSessionAttributes() {
        if (sessionAttributes == null) {
            sessionAttributes = new SessionMap(request);
        }
        return sessionAttributes;
    }

    /**
     * @return
     */
    public Map getApplicationAttributes() {
        if (applicationAttributes == null) {
            applicationAttributes = new ApplicationMap(request);
        }
        return applicationAttributes;
    }

    /**
     * @return
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @return
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @return
     */
    public ActionErrors getErrors() {
        return errors;
    }

    /**
     * @return
     */
    public ActionMessages getMessages() {
        return msgs;
    }
    
    /**
     * @return
     */
    public static ActionContext getActionContext() {
        ActionContext ctx = (ActionContext)localContext.get();
        if (ctx == null) {
            ctx = new ActionContext();
            localContext.set(ctx);
        }
        return ctx;
    }
    /**
     * @return
     */
    public MessageResources getResources() {
        return resources;
    }
    /**
     * @return Returns the mapping.
     */
    public ActionMapping getMapping() {
        return mapping;
    }
}