/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Nov 28, 2005
 */
package com.fgl.cwp.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.PermissionsAdapter;

import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.presentation.CustomPermissionsAdapter;


/**
 * Used on the generation of the menu to filter out menu items based on a user's
 * permissions.
 * 
 * @author bting
 */
public class MenuPermissionsFilter implements Filter {

    
    public void destroy() {
        // empty
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // empty
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession httpSession = httpRequest.getSession();
        
        User user = null;
        Store store = null;
        
        if (httpSession != null) {
            user = (User)httpSession.getAttribute(Constants.USER);
            store = (Store)httpSession.getAttribute(Constants.STORE);
        }

        PermissionsAdapter permissions = new CustomPermissionsAdapter(user, store);
        
        httpRequest.getSession().setAttribute(
                Constants.KEY_CUSTOM_PERMISSIONS_ADAPTER,
                permissions);
        
        chain.doFilter(request, response);
    }

}
