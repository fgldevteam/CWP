package com.fgl.cwp.common;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.net.URLEncoder;

/*
 * ParameterizedActionForward.java
 *
 * Created on August 3, 2001, 11:45 AM
 * @author  Jan Soresen
 * @version
 */
/**
 * 
 *
 */
public class ParameterizedActionForward extends ActionForward {

    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new <CODE>ParameterizedActionForward</CODE>, populated from an <CODE>ActionForward</CODE>
     * @param forward Populate the receiver from this <CODE>ActionForward</CODE>
     */
    public ParameterizedActionForward(ActionForward forward) {
        setPath(forward.getPath());
        setRedirect(forward.getRedirect());
        firstParameter = getPath().indexOf('?') == -1;
    }

    /**
     * Create a new <CODE>ParameterizedActionForward</CODE>, based on an <CODE>ActionForward</CODE> found in an
     * <CODE>ActionMapping</CODE>
     * @param mapping     The <CODE>ActionMapping</CODE> to get the base forward from
     * @param forwardName The <CODE>ActionMapping</CODE> to get the base forward from
     */
    public ParameterizedActionForward(ActionMapping mapping, String forwardName) {
        this(mapping.findForward(forwardName));
    }

    /**
     * Create a new <CODE>ParameterizedActionForward</CODE>
     * @param forward The base <CODE>ActionForward</CODE>
     * @param key     The key of the parameter
     * @param value   The value of the parameter
     */
    public ParameterizedActionForward(ActionForward forward, String key, String value) {
        this(forward);
        addParameter(key, value);
    }

    /**
     * Create a new <CODE>ParameterizedActionForward</CODE>
     * @param mapping     The <CODE>ActionMapping</CODE> to get the base forward from
     * @param forwardName The <CODE>ActionMapping</CODE> to get the base forward from
     * @param key         The key of the parameter
     * @param value       The value of the parameter
     */
    public ParameterizedActionForward(ActionMapping mapping, String forwardName, String key, String value) {
        this(mapping, forwardName);
        addParameter(key, value);
    }

    /**
     * Add a new parameter
     * @param key   The key of the parameter
     * @param value The value of the parameter
     */
    public void addParameter(String key, String value) {
        StringBuffer buffer = new StringBuffer(getPath());
        buffer.append(firstParameter ? '?' : '&');
        firstParameter = false;
        try {
            buffer.append(URLEncoder.encode(key,"UTF-8"));
            buffer.append('=');
            buffer.append(URLEncoder.encode(value,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPath(buffer.toString());
    }

    private boolean firstParameter;
}