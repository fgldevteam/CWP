/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.common;

import com.fgl.cwp.exception.InsufficientPrivilege;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.struts.ActionContext;

/**
 * General application helper methods.
 * 
 * @author dschultz
 */
public class Utils {
    /**
     * Returns the current user's store number
     * @return
     * @throws NumberFormatException
     */
    public static Store getStoreFromSession() {
        return (Store)ActionContext
                .getActionContext()
                .getRequest()
                .getSession()
                .getAttribute(Constants.STORE);
    }
    /**
     * 
     * @return
     */
    public static User getUserFromSession(){
        return (User) ActionContext
                .getActionContext()
                .getRequest()
                .getSession()
                .getAttribute(Constants.USER);
    }
    

    /**
     * Check if the current session user is allowed access for the given function.
     * @param function
     * @throws InsufficientPrivilege
     */
    public static void checkFunction(int function) throws InsufficientPrivilege {
        
        User sessionUser = Utils.getUserFromSession();
        if (sessionUser != null) {
            if (sessionUser.canUserPerformFunction(function)) {
                return;
            }
        }
        throw new InsufficientPrivilege("User does not have the correct privileges");
    }
}
