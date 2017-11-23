package com.fgl.cwp.presentation;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.CipherService;
import com.fgl.cwp.persistence.services.StoreService;
import com.fgl.cwp.persistence.services.UserService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author Jessica Wong
 */
public class LoginBean extends BaseBean {
    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(LoginBean.class);
    
    private String username;
    private String password;
    private String action = "";
    
    private static final String ACTION_LOGIN_BY_USERNAME = "loginByUsername";

    /**
     * @return
     */
    public String login() {
        String success = Constants.FAILURE;
        
        ActionContext context = ActionContext.getActionContext();

        if (action.equals(ACTION_LOGIN_BY_USERNAME)) {
            try {
                
                User user = UserService.getInstance().getUserByUsername(username);

                if (user != null) {
                    String cipherPassword = CipherService.getInstance().encrypt(password);
                    if (user.getPassword().equals(cipherPassword)) {
                        success = Constants.SUCCESS;
                    }
                }
                
                if (success.equals(Constants.SUCCESS)) {
                    context.getRequest().getSession().setAttribute(Constants.USER, user);
                } else {
                    context.addError("error", "login.error.invalidlogin");
                    password = null;
                }
                
            } catch (Exception e) {
                context.addError("error", "login.error.gettinguser");
                log.error("Error getting the user ...", e);
                success = Constants.FAILURE;
            }            
            
        } else {
            if (autoLogin()) {
                success = Constants.SUCCESS;
            } else {
                context.addError("error", "login.error.gettingstore");
                success = Constants.FAILURE;
            }
        }
        
        return success;
    }
    
    /**
     * Validate the form if logging in using credentials.
     */
    public void validate() {
        
        if (action.equals(ACTION_LOGIN_BY_USERNAME)) {
            ActionContext context = ActionContext.getActionContext();
            if (username == null || username.trim().length() <= 0) {
                context.addError("error", "login.validate.username");
            }
            if (password == null || password.trim().length() <= 0) {
                context.addError("error", "login.validate.password");
            }
        }
    }

    
    /**
     * Check the user-agent header from the client's http request. The user-agent
     * must contain the string which matches the regular expression '##(\\d{4})##'.
     * The value between the two tokens '##' contain the user's store number. If
     * found and the user's store is valid, then allow access to the portal. This
     * 'hack' allows the user to automatically login without having to provide any
     * credentials.
     * 
     * @return boolean
     */
    protected boolean autoLogin() {
        boolean success = false;
        ActionContext context = ActionContext.getActionContext();

        // First check to see if the session already has a user/store
        if (context.getRequest().getSession().getAttribute(Constants.USER) != null) {
            return true;
        }
        if (context.getRequest().getSession().getAttribute(Constants.STORE) != null) {
            return true;
        }
        
        // we should not have to do this...
        context.getRequest().getSession().removeAttribute(Constants.USER);
        context.getRequest().getSession().removeAttribute(Constants.STORE);
        
        
        //Check for the User-Agent in the registry
        String userAgent = context.getRequest().getHeader("User-Agent");
        log.debug("HTTP-User-Agent : " + userAgent);
        
        // Extract the '##{store number}##'
        int start = userAgent.indexOf("##");
        int end = userAgent.indexOf("##", start+1);
        if (start > -1 && end > -1) {
            
            // to compensate for the ending '##'
            end += 2;

            if (end < userAgent.length()) {
                String block = userAgent.substring(start, end);
                
                if (block.matches("##(\\d{4})##")) {
                    StringTokenizer tokenizer = new StringTokenizer(block, "##", false);
                    if (tokenizer.hasMoreElements()) {
                        String storeNumber = tokenizer.nextToken();                
                        log.debug("Store Number from HTTP-User-Agent : " + storeNumber);
                        
                        // validate the store number against the database stores
                        try {
                            Store store = StoreService.getInstance()
                                    .getStoreById(new Integer(storeNumber));
                            
                            //if store exists in the database
                            if (store != null) {
                                //set the store number in the session
                                context.getRequest().getSession().setAttribute(Constants.STORE, store);
                                success = true;
                            }
                        } catch (Exception e) {
                            log.error("Error getting the user's store ...", e);
                            success = false;
                        }
                    }
                }
            }
        }
        
        if (!success) {
            log.debug("Store number from registry does not exist");

        }
        return success;
    }

    /**
     * Log user out of application
     * @return
     */
    public String logoff() {
        ActionContext context = ActionContext.getActionContext();
        context.getRequest().getSession().removeAttribute(Constants.USER);
        context.getRequest().getSession().removeAttribute(Constants.STORE);
        context.getRequest().getSession().invalidate();
        return Constants.SUCCESS;
    }
    /**
     * @return
     */
    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

}