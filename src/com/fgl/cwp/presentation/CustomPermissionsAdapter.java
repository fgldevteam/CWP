/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Nov 27, 2005
 */
package com.fgl.cwp.presentation;

import java.util.StringTokenizer;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;


/**
 * Adapter used by struts menu to determine what menu items to display
 * on the main menu based on the current user/store permissions.
 * 
 * @author bting
 */

public class CustomPermissionsAdapter implements PermissionsAdapter {
    
    private static Log log = LogFactory.getLog(CustomPermissionsAdapter.class);
    
    /**
     * The role name for stores (used in menu-config.xml)
     */
    private static final String STORE_MENU_ROLE = "store";
    
    private User user;
    private Store store;
    
    /**
     * 
     * @param menuNames
     * @param user
     * @param store
     */
    public CustomPermissionsAdapter(User user, Store store) {
        this.user = user;
        this.store = store;
    }
    
    public boolean isAllowed(MenuComponent menuComponent) {
        String roles = menuComponent.getRoles();
        
        if (user != null || store != null) {
            if (roles != null) {
                StringTokenizer tokenizer = new StringTokenizer(roles, ",", false);
               
                while (tokenizer.hasMoreTokens()) {
                    String role = tokenizer.nextToken();
                    
                    if (user != null) {
                        try {
                            if (user.doesUserHaveRole(Integer.parseInt(role))) {
                                log.debug("Access to menu: " + menuComponent.getName() + " granted for: " + getCurrentUser());
                                return true;
                            }
                        } catch (Exception e) {
                            log.warn("Failed to parse menu role: " + role);
                        }
                    } else if (store != null) {                        
                        if (role.equals(STORE_MENU_ROLE) && store != null) {
                            log.debug("Access to menu: " + menuComponent.getName() + " granted for: " + getCurrentUser());
                            return true;
                        }
                    }
                } 
                log.debug("Access to menu: " + menuComponent.getName() + " denied for: " + getCurrentUser());
                return false;
            }
            log.debug("Access to menu: " + menuComponent.getName() + " granted for: " + getCurrentUser());
            return true;
        }
        
        return false;
    }
    

    /**
     * Return the current user name.
     * @return
     */
    private String getCurrentUser() {
        if (user != null) {
            return user.getUsername();
        }
        if (store != null) {
            return store.getShortName();
        }
        return "";
    }

}
