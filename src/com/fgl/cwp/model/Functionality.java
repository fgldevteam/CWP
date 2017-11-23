/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Jun 2, 2005
 */
package com.fgl.cwp.model;

import java.util.Set;

/**
 * @author bting
 * @hibernate.class
 *      table = "functionality"
 *      proxy = "com.fgl.cwp.model.Functionality"
 *      mutable = "false"
 */
public class Functionality {

    public static int CREATE_USERS = 1;
    public static int VIEW_USERS = 2;
    public static int EDIT_USERS = 3;
    public static int DELETE_USERS = 4;
    public static int CREATE_DOCUMENTS = 5;
    public static int VIEW_DOCUMENTS = 6;
    public static int EDIT_DOCUMENTS = 7;
    public static int DELETE_DOCUMENTS = 8;
    public static int CREATE_NOTICES = 9;
    public static int VIEW_NOTICES = 10;
    public static int EDIT_NOTICES = 11;
    public static int DELETE_NOTICES = 12;
    public static int CREATE_REPORTS = 13;
    public static int VIEW_REPORTS = 14;
    public static int VIEW_PRODUCTS = 15;
    public static int VIEW_PRICING_ISSUES = 16;
    public static int EDIT_PRICING_ISSUES = 17;
    
    
    private Integer id;
    private String description;
    private Set roles;
    
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
     * @return Returns the roles.
     * @hibernate.set
     *      lazy = "true"
     *      table = "role_functionality"
     *      cascade = "none"
     * @hibernate.collection-key
     *      column = "functionality_id"
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
