package com.fgl.cwp.common;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.User;

/**
 * @author Jessica Wong
 */
public class SecurityFilter implements Filter {
    private static final Log log = LogFactory.getLog(SecurityFilter.class);
    private FilterConfig filterConfig;

    private Set<String> ignoredURIs = null;
    
    private Properties securityConstraints = null;
    
    /**
     * Initializes the Filter.
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        // Configure URIs to ignore
        ignoredURIs = new HashSet<String>();        
        String ignoreList = filterConfig.getInitParameter("ignoredURIs");
        if (ignoreList != null) {
            StringTokenizer parser = new StringTokenizer(ignoreList, ",", false);
            while (parser.hasMoreElements()) {
                String uri = (String)parser.nextElement();
                if (uri != null && !uri.trim().equals("")) {
                    ignoredURIs.add(uri.trim());
                }
            }
        }
    
        securityConstraints = new Properties();
        String securityConstraintFile = filterConfig.getInitParameter("securityConstraint");
        try {
            securityConstraints.load(SecurityFilter.class.getResourceAsStream(securityConstraintFile));
            
            Enumeration e = securityConstraints.keys();
            while (e.hasMoreElements()) {
                log.debug(securityConstraints.getProperty((String)e.nextElement()));
            }
            
        } catch (IOException ioe) {
            log.error("Failed to load security constraints for security filter");
        }
        
    }

    /**
     * Checks if there is a User-Store stored in the session.
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();

        log.debug(" Security Filter for URI: " + uri);

        if ((uri != null) && !inIgnoreList(uri) && !uri.equals(req.getContextPath() + '/')) {
            String contextRelativeURI = uri;
            String contextPath = req.getContextPath();                    
            if (contextPath != null) {
                if (uri.length() >= contextPath.length()) {
                    contextRelativeURI = uri.substring(contextPath.length());
                }
            }

            if (session.getAttribute(Constants.STORE) != null) {
                // Auto-login by store
                log.debug("Auto-login by store... request can continue");
            } else if (session.getAttribute(Constants.USER) != null) {

                log.debug("Login by user... checking permissions");
                
                // Check permissions for this user
                User user = (User)session.getAttribute(Constants.USER);
                if (user != null) { 

                    String function = securityConstraints.getProperty(contextRelativeURI);
                    if (function != null && !user.canUserPerformFunction(Integer.parseInt(function))) {
                        log.warn("Attempted access of resource: " + contextRelativeURI + " by user: " + user.getUsername() + " denied due to insufficient privilege.");
                        RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/jsp/errorPages/insufficientPrivilege.jsp");
                        rd.forward(req, response);
                        return;
                    }
                }
                
            } else {
                log.debug("User must log in");
                RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/login.do");
                rd.forward(req, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Filter teardown.
     */
    public void destroy() {
        filterConfig = null;
    }

    /**
     * Tests if the given URI is one that security should be ignored for.
     * @param uri the URI to test
     * @return true if the specified URI does not required login etc.
     */
    private boolean inIgnoreList(String uri) {

        for (Iterator iter = ignoredURIs.iterator(); iter.hasNext();) {
            String ignoreURI = (String)iter.next();
            if (uri.indexOf(ignoreURI) >= 0) {
                return true;
            }
        }
        return false;
    }
}