/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Apr 18, 2005
 */
package com.fgl.cwp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.persistence.SessionManager;

/**
 * A servlet for initializing application-wide services
 * @author dschultz
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() throws ServletException {
        super.init();
        try {
            SessionManager.getSessionFactory();
        } catch (Exception e) {
            LogFactory.getLog(InitServlet.class).error("Cannot get Hibernate Session Factory", e);
            throw new ServletException(e);
        }
    }
}
