/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Jun 3, 2005
 */
package com.fgl.cwp.model;

import java.util.Iterator;
import java.util.Set;

/**
 * @author bting
 * @hibernate.class
 *      table = "user_role"
 *      proxy = "com.fgl.cwp.model.UserRole"
 *      dynamic-insert = "true"
 *      dynamic-update = "true"
 */
public class UserRole {
    /**
     * Administrator
     */
    public static int ADMINISTRATOR = 1;

    /**
     * Document Manager
     */
    public static int DOCUMENT_MANAGER = 2;
    
    /**
     * Notice Manager
     */
    public static int NOTICE_MANAGER = 3;
    
    /**
     * Store Manager
     */
    public static int STORE_MANAGER = 4;
    
    /**
     * Regional General Manager
     */
    public static int REGIONAL_MANAGER = 5;
    
    /**
     * Read-only Users
     */
    public static int READ_ONLY_USER = 6;
    
    /**
     * User Manager
     */
    public static int USER_MANAGER = 7;
    
    /**
     * Pricing Issues Manager
     */
    public static int PRICING_ISSUES_MANAGER = 8;
    
    /**
     * Elearning administrator
     */
    public static final int ELEARNING_ADMINISTRATOR = 9;
    
    
    private Integer id;
    private String description;
    private Set functionality;

    /**
     * Default constructor
     */
    public UserRole() {
        // empty
    }
    
    /**
     * Create a role with given id
     * @param id
     */
    public UserRole(Integer id) {
        this.id = id;
    }
    
    /**
     * Wrapper
     * @param id
     */
    public UserRole(int id) {
        this(new Integer(id));
    }
    
    
    /**
     * Can this role perform the given function?
     * @param function
     * @return
     */
    public boolean canRolePerformFunction(int function) {
        if (functionality != null) {
            for (Iterator iter = functionality.iterator(); iter.hasNext();) {
                Functionality functionality = (Functionality)iter.next();
                if (functionality.getId().intValue() == function) {
                    return true;
                }
            }
        }
        return false;
    }
    public String toString() {
        return id + " " + description;
    }
    
    /**
     * @return Returns the description.
     * @hibernate.property
     *      column = "description"
     *      not-null = "true"
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return Returns the id.
     * @hibernate.id
     *      generator-class = "assigned"
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the functionality.
     * @hibernate.set
     *      lazy = "true"
     *      table = "role_functionality"
     *      cascade = "none"
     * @hibernate.collection-key
     *      column = "user_role_id"
     * @hibernate.collection-many-to-many
     *      class = "com.fgl.cwp.model.Functionality"
     *      column = "functionality_id"
     */
    public Set getFunctionality() {
        return functionality;
    }
    
    /**
     * @param functionality The functionality to set.
     */
    public void setFunctionality(Set functionality) {
        this.functionality = functionality;
    }
    
}
