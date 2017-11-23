package com.fgl.cwp.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "application_user"
 *      proxy = "com.fgl.cwp.model.User"
 *      dynamic-insert = "true"
 *      dynamic-update = "true"      
 *
 * @hibernate.query
 *      name="getUserByUsername"
 *      query="SELECT     user
 *             FROM       User user
 *             LEFT JOIN FETCH user.stores
 *             WHERE      user.username = :username"
 *             
 * @hibernate.query
 *      name="countUserByUsername"
 *      query="SELECT     count(user.username)
 *             FROM       User user
 *             WHERE      user.username = :username"
 *
 * @hibernate.query
 *      name="getUsers"
 *      query = "SELECT             user
 *               FROM               User user
 *               LEFT JOIN FETCH    user.roles
 *               ORDER BY           user.firstName ASC"
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String fax;
    private Set stores;
    private Set roles;
    
    private String plainTextPassword;
    private String newPassword;
    private String confirmPassword;
    /**
     * @return Returns the plainTextPassword.
     */
    public String getPlainTextPassword() {
        return plainTextPassword;
    }
    /**
     * @param plainTextPassword The plainTextPassword to set.
     */
    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }
    /**
     * @return Returns the confirmPassword.
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }
    /**
     * @param confirmPassword The confirmPassword to set.
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    /**
     * @return Returns the newPassword.
     */
    public String getNewPassword() {
        return newPassword;
    }
    /**
     * @param newPassword The newPassword to set.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    
    /**
     * Is this user allowed to perform the given function?
     * @param function
     * @return
     */
    public boolean canUserPerformFunction(int function) {
        if (roles != null) {
            for (Iterator iter = roles.iterator(); iter.hasNext();) {
                UserRole userRole = (UserRole)iter.next();
                if (userRole.canRolePerformFunction(function)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Does this user belong to the given role?
     * @param inRole
     * @return
     */
    public boolean doesUserHaveRole(int inRole) {
        if (roles != null) {
            for (Iterator iter = roles.iterator(); iter.hasNext();) {
                UserRole userRole = (UserRole)iter.next();
                if (userRole.getId().intValue() == inRole) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Is this a new user? (i.e. has not been saved to the database yet)
     * @return boolean
     */
    public boolean isNew() {
        return id == null ? true : false;
    }
    
    
    /**
     * Convenience function to see if this user is an administrator.
     * Useful on JSPs using JSTL
     * @return
     */
    public boolean isAdministrator() {
        return doesUserHaveRole(UserRole.ADMINISTRATOR);
    }
    
    /**
     * Convenience function to see if this user is an e-learning administrator
     * Useful on JSPs using JSTL
     */
    public boolean isElearningAdministrator(){
    	return doesUserHaveRole(UserRole.ELEARNING_ADMINISTRATOR);
    }
    
    public boolean equals(Object o) {
        
        if (!(o instanceof User)) {
            return false;
        }
        User u = (User)o;

        return new EqualsBuilder()
                .append(this.getUsername(), u.getUsername())
                .isEquals();
    }
    
    
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getUsername())
                .toHashCode();
    }
    
    /**
     * Return the user's full name.
     * @return
     */
    public String getFullName() {
        StringBuffer buf = new StringBuffer();
        if (firstName != null) {
            buf.append(firstName);
        }
        if (buf.length()>0) {
            buf.append(" ");
        }
        if (lastName != null) {
            buf.append(lastName);
        }
        return buf.toString();
    }
    
    /**
     * @return
     * 
     * @hibernate.id
     *      column = "user_id"
     *      generator-class = "native"
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return
     * @hibernate.property 
     *      column = "username"
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
     * 
     * @hibernate.property 
     *      column = "password"
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
     * 
     * @hibernate.property 
     *      column = "first_name"
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return
     * 
     * @hibernate.property
     *      column = "last_name"
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * @return Returns the email.
     * @hibernate.property
     *      column = "email_address"
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the fax.
     * @hibernate.property
     *      column = "fax"
     */
    public String getFax() {
        return fax;
    }
    /**
     * @param fax The fax to set.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @return Returns the phone.
     * @hibernate.property
     *      column = "phone"
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone The phone to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return
     * 
     * @hibernate.set 
     *      table = "user_store"
     *      lazy = "true"
     *      sort = "natural"
     * @hibernate.collection-key 
     *      column = "user_id" 
     * @hibernate.collection-many-to-many  
     *      class = "com.fgl.cwp.model.Store"
     *      column = "store_number"
     */
    public Set getStores() {
        return stores;
    }
    
    /**
     * @param stores
     */
    public void setStores(Set stores) {
        this.stores = stores;
    }
    /**
     * @return Returns the roles.
     * @hibernate.set
     *      table = "user_roles"
     *      lazy = "true"
     *      cascade = "none"
     * @hibernate.collection-key
     *      column = "user_id"
     * @hibernate.collection-many-to-many
     *      class = "com.fgl.cwp.model.UserRole"
     *      column = "user_role_id"
     */
    public Set getRoles() {
        return roles;
    }
    
    /**
     * @param roles The roles to set.
     */
    public void setRoles(Set roles) {
        this.roles = roles;
    }
    
    
}
