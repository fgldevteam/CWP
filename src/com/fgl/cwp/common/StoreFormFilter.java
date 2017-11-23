package com.fgl.cwp.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StoreFormFilter implements Filter {
	
	private static final Log log = LogFactory.getLog(StoreFormFilter.class);
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
    	this.filterConfig = filterConfig;
	}
    
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;  
        log.debug(" Store Form Filter for URI: " + req.getRequestURI());
        if (req.getSession().getAttribute(Constants.STORE) == null) {
            // not logged in as a Store
            log.debug("Not logged in as Store, cannot access form");
            RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/jsp/errorPages/storeFormError.jsp");
            rd.forward(req, response);
            return;
        } 
        chain.doFilter(request, response); 
	}

	public void destroy() {
		this.filterConfig = null;
	}

}
