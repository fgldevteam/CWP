/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Feb 9, 2005
 */
package com.fgl.cwp.presentation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Functionality;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.model.UserRole;
import com.fgl.cwp.persistence.services.CipherService;
import com.fgl.cwp.persistence.services.UserService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class ManageUserBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;

//    private static Log log = LogFactory.getLog(ManageUserBean.class);
    
    private List users;
    private User user;
    private Integer userIndex;
    
    private boolean roleAdministrator;
    private boolean roleStoreManager;
    private boolean roleRegionalManager;
    private boolean roleDocumentManager;
    private boolean roleNoticeManager;
    private boolean roleUserManager;
    private boolean roleReadOnlyUser;
    private boolean rolePricingIssuesManager;
    private boolean roleElearningAdministrator;
    
    private Integer[] selectedManagedStores = new Integer[0];
    private Integer[] selectedUnmanagedStores = new Integer[0];
    
    
    private Set<Store> unmanagedStores;
    
    
    
    public void reset() {
        selectedManagedStores = new Integer[0];
        selectedUnmanagedStores = new Integer[0];
        roleAdministrator = false;
        roleDocumentManager = false;
        roleNoticeManager = false;
        roleStoreManager = false;
        roleRegionalManager = false;
        roleUserManager = false;
        roleReadOnlyUser = false;
        rolePricingIssuesManager = false;
        roleElearningAdministrator = false;
    }

    /**
     * Create a new user.
     * @return
     * @throws Exception
     */
    public String createUser() throws Exception {
        
        Utils.checkFunction(Functionality.CREATE_USERS);
        
        user = new User();
        user.setStores(new TreeSet());
        unmanagedStores = UserService.getInstance().getUsersStoresNotManaged(user);
        return Constants.SUCCESS;
    }
    

    /**
     * Edit a user.
     * @return
     * @throws Exception
     */
    public String editUser() throws Exception {
        
        Utils.checkFunction(Functionality.EDIT_USERS);

        String result = Constants.FAILURE;
        if (userIndex != null) {
            int idx = userIndex.intValue();
            if (idx >=0 && idx < users.size()) {
                user = (User)users.get(idx);
                UserService.getInstance().loadUsersStores(user);
                unmanagedStores = UserService.getInstance().getUsersStoresNotManaged(user);

                // pre-populate user role(s)
                Set roles = user.getRoles();
                for (Iterator iter = roles.iterator(); iter.hasNext();) {
                    
                    int roleId = ((UserRole)iter.next()).getId().intValue();
                    
                    if (roleId == UserRole.ADMINISTRATOR) {
                        roleAdministrator = true;
                    }
                    if (roleId == UserRole.STORE_MANAGER) {
                        roleStoreManager = true;
                    }
                    if (roleId == UserRole.REGIONAL_MANAGER) {
                        roleRegionalManager = true;
                    }
                    if (roleId == UserRole.DOCUMENT_MANAGER) {
                        roleDocumentManager = true;
                    }
                    if (roleId == UserRole.NOTICE_MANAGER) {
                        roleNoticeManager = true;
                    }
                    if (roleId == UserRole.USER_MANAGER) {
                        roleUserManager = true;
                    }
                    if (roleId == UserRole.READ_ONLY_USER) {
                        roleReadOnlyUser = true;
                    }
                    if (roleId == UserRole.PRICING_ISSUES_MANAGER) {
                        rolePricingIssuesManager = true;
                    }
                    if( roleId == UserRole.ELEARNING_ADMINISTRATOR){
                    	roleElearningAdministrator = true;
                    }
                }
                
                result = Constants.SUCCESS;
            }
        }
        return result;
    }
    
    
    /**
     * Add stores to be managed by this user. 
     * @return
     */
    public String addManagedStores() {
        for (int i=0; i<selectedUnmanagedStores.length; ++i) {
            for (Iterator iter = unmanagedStores.iterator(); iter.hasNext();) {
                Store store = (Store)iter.next();
                if (store.getNumber() == selectedUnmanagedStores[i].intValue()) {
                    iter.remove();
                    user.getStores().add(store);
                }
            }
        }
        return Constants.SUCCESS;
    }
    
    /**
     * Remove stores managed by this user.
     * @return
     */
    public String removedManagedStores() {
        for (int i=0; i<selectedManagedStores.length; ++i) {
            for (Iterator iter = user.getStores().iterator(); iter.hasNext();) {
                Store store = (Store)iter.next();
                if (store.getNumber() == selectedManagedStores[i].intValue()) {
                    iter.remove();
                    unmanagedStores.add(store);
                }
            }
        }
        return Constants.SUCCESS;
    }
    
    
    
    /**
     * Load all users.
     * @return
     * @throws Exception
     */
    public String loadUsers() throws Exception {

        Utils.checkFunction(Functionality.VIEW_USERS);

        users = UserService.getInstance().getUsers();
        if (users.isEmpty()) {
            createUser();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }
    
    
    /**
     * Save a user.
     * @return
     * @throws Exception
     */
    public String saveUser() throws Exception {
        
        Utils.checkFunction(Functionality.EDIT_USERS);

        if (validateSaveUser()) {
            if (UserService.getInstance().saveUser(user)) {
                ActionContext.getActionContext().addMessage("status", "status.writeToDB.success");
                return Constants.SUCCESS;
            }
        }
        return Constants.FAILURE;
    }

    
    /**
     * Delete an existing user.
     * @return
     * @throws Exception
     */
    public String deleteUser() throws Exception {
        
        Utils.checkFunction(Functionality.DELETE_USERS);

        String result = Constants.FAILURE;

        if (userIndex != null) {
            int idx = userIndex.intValue();

            if (idx >=0 && idx < users.size()) {
                UserService.getInstance().deleteUser((User)users.get(idx));
                ActionContext.getActionContext().addMessage("status", "status.deleteFromDB.success");
                result = Constants.SUCCESS;
            }
        }

        return result;
    }
    
    
    private boolean validateSaveUser() throws Exception {
        
        boolean success = true;
        if (user != null) {
            ActionContext ctxt = ActionContext.getActionContext();
            
            if (user.getFirstName() == null || user.getFirstName().trim().equals("")) {
                ctxt.addError("error", "admin.user.edit.error.firstName.required");
                success = false;
            }
            if (user.getLastName() == null || user.getLastName().trim().equals("")) {
                ctxt.addError("error", "admin.user.edit.error.lastName.required");
                success = false;
            }
            if (user.isNew()) {
                if (user.getUsername() == null || user.getUsername().trim().equals("")) {
                    ctxt.addError("error", "admin.user.edit.error.username.required");
                    success = false;
                } else {
                    // Check to see if this username already exists in the database
                    if (UserService.getInstance().countUserByUsername(user.getUsername().trim())>0) {
                        ctxt.addError("error", "admin.user.edit.error.username.duplicate");
                        success = false;
                    }
                    
                }

            }
            if (user.isNew()) {
                if (user.getPlainTextPassword() == null || user.getPlainTextPassword().trim().equals("")) {
                    ctxt.addError("error", "admin.user.edit.error.password.required");
                    success = false;
                } else {
                    user.setPassword(CipherService.getInstance().encrypt(user.getPlainTextPassword().trim()));
                }
            } else {
                if (user.getNewPassword() != null && !user.getNewPassword().trim().equals("")) {
                    if (user.getConfirmPassword() == null || user.getConfirmPassword().equals("")) {
                        // user forgot to enter confirm password
                        ctxt.addError("error", "admin.user.edit.error.confirmPassword.required");
                        success = false;
                    } else {
                        if (!user.getNewPassword().trim().equals(user.getConfirmPassword().trim())) {
                            ctxt.addError("error", "admin.user.edit.error.newPasswordDoesNotMatchConfirmPassword");
                            success = false;
                        } else {
                            user.setPassword(CipherService.getInstance().encrypt(user.getNewPassword().trim()));
                        }
                    }
                } else {
                    if (user.getConfirmPassword() != null && !user.getConfirmPassword().trim().equals("")) {
                        // user forgot to enter new password
                        ctxt.addError("error", "admin.user.edit.error.newPassword.required");
                        success = false;
                    }
                }
            }
            
            //Update security role
            Set<UserRole> roles = new HashSet<UserRole>();
            
            if (isRoleAdministrator()) {
                roles.add(new UserRole(UserRole.ADMINISTRATOR));
            }
            if (isRoleStoreManager()) {
                roles.add(new UserRole(UserRole.STORE_MANAGER));
            }
            if (isRoleRegionalManager()) {
                roles.add(new UserRole(UserRole.REGIONAL_MANAGER));
            }
            if (isRoleDocumentManager()) {
                roles.add(new UserRole(UserRole.DOCUMENT_MANAGER));
            }
            if (isRoleNoticeManager()) {
                roles.add(new UserRole(UserRole.NOTICE_MANAGER));
            }
            if (isRoleUserManager()) {
                roles.add(new UserRole(UserRole.USER_MANAGER));
            }
            if (isRoleReadOnlyUser()) {
                roles.add(new UserRole(UserRole.READ_ONLY_USER));
            }
            if (isRolePricingIssuesManager()) {
                roles.add(new UserRole(UserRole.PRICING_ISSUES_MANAGER));
            }
            if ( isRoleElearningAdministrator() ){
            	roles.add(new UserRole(UserRole.ELEARNING_ADMINISTRATOR));
            }
            if (roles.isEmpty()) {
                ctxt.addError("error", "admin.user.edit.error.role.required");
                success =false;
            } else {
                user.setRoles(roles);
            }

        } else {
            success = false;
        }
        return success;
    }
    
    /**
     * @return Returns the user.
     */
    public User getUser() {
        return user;
    }
    /**
     * @return Returns the users.
     */
    public List getUsers() {
        return users;
    }
    /**
     * @param userIndex The userIndex to set.
     */
    public void setUserIndex(Integer userIndex) {
        this.userIndex = userIndex;
    }
    /**
     * @return Returns the unmanagedStores.
     */
    public Set getUnmanagedStores() {
        return unmanagedStores;
    }
    /**
     * @return Returns the selectedManagedStores.
     */
    public Integer[] getSelectedManagedStores() {
        return selectedManagedStores;
    }
    /**
     * @param selectedManagedStores The selectedManagedStores to set.
     */
    public void setSelectedManagedStores(Integer[] selectedManagedStores) {
        this.selectedManagedStores = selectedManagedStores;
    }
    /**
     * @return Returns the selectedUnmanagedStores.
     */
    public Integer[] getSelectedUnmanagedStores() {
        return selectedUnmanagedStores;
    }
    /**
     * @param selectedUnmanagedStores The selectedUnmanagedStores to set.
     */
    public void setSelectedUnmanagedStores(Integer[] selectedUnmanagedStores) {
        this.selectedUnmanagedStores = selectedUnmanagedStores;
    }

    

    /**
     * @return Returns the roleAdministrator.
     */
    public boolean isRoleAdministrator() {
        return roleAdministrator;
    }
    
    /**
     * @return Returns the roleElearning Administrator
     */
    public boolean isRoleElearningAdministrator(){
    	return roleElearningAdministrator;
    }

    /**
     * param roleElearningAdministrator the roleElearningAdministrator to set
     */
    public void setRoleElearningAdministrator(boolean roleElearniAdmin){
    	this.roleElearningAdministrator = roleElearniAdmin;
    }
    /**
     * @param roleAdministrator The roleAdministrator to set.
     */
    public void setRoleAdministrator(boolean roleAdministrator) {
        this.roleAdministrator = roleAdministrator;
    }
    

    /**
     * @return Returns the rolePricingIssuesManager.
     */
    public boolean isRolePricingIssuesManager() {
        return rolePricingIssuesManager;
    }

    /**
     * @param rolePricingIssuesManager The rolePricingIssuesManager to set.
     */
    public void setRolePricingIssuesManager(boolean rolePricingIssuesManager) {
        this.rolePricingIssuesManager = rolePricingIssuesManager;
    }

    /**
     * @return Returns the roleReadOnlyUser.
     */
    public boolean isRoleReadOnlyUser() {
        return roleReadOnlyUser;
    }
    

    /**
     * @param roleReadOnlyUser The roleReadOnlyUser to set.
     */
    public void setRoleReadOnlyUser(boolean roleReadOnlyUser) {
        this.roleReadOnlyUser = roleReadOnlyUser;
    }
    

    /**
     * @return Returns the roleRegionalManager.
     */
    public boolean isRoleRegionalManager() {
        return roleRegionalManager;
    }
    

    /**
     * @param roleRegionalManager The roleRegionalManager to set.
     */
    public void setRoleRegionalManager(boolean roleRegionalManager) {
        this.roleRegionalManager = roleRegionalManager;
    }
    

    /**
     * @return Returns the roleStoreManager.
     */
    public boolean isRoleStoreManager() {
        return roleStoreManager;
    }
    

    /**
     * @param roleStoreManager The roleStoreManager to set.
     */
    public void setRoleStoreManager(boolean roleStoreManager) {
        this.roleStoreManager = roleStoreManager;
    }


    /**
     * @return Returns the roleDocumentManager.
     */
    public boolean isRoleDocumentManager() {
        return roleDocumentManager;
    }
    


    /**
     * @param roleDocumentManager The roleDocumentManager to set.
     */
    public void setRoleDocumentManager(boolean roleDocumentManager) {
        this.roleDocumentManager = roleDocumentManager;
    }
    


    /**
     * @return Returns the roleNoticeManager.
     */
    public boolean isRoleNoticeManager() {
        return roleNoticeManager;
    }
    


    /**
     * @param roleNoticeManager The roleNoticeManager to set.
     */
    public void setRoleNoticeManager(boolean roleNoticeManager) {
        this.roleNoticeManager = roleNoticeManager;
    }

    /**
     * @return Returns the roleUserManager.
     */
    public boolean isRoleUserManager() {
        return roleUserManager;
    }
    

    /**
     * @param roleUserManager The roleUserManager to set.
     */
    public void setRoleUserManager(boolean roleUserManager) {
        this.roleUserManager = roleUserManager;
    }
    
    
    
}
