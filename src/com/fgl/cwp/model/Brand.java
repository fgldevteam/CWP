/*
 * Created on Jan 4, 2005
 *
 */
package com.fgl.cwp.model;

/**
 * @author urobertson
 * 
 * @hibernate.class
 *      table = "brand"
 *      proxy = "com.fgl.cwp.model.Brand"
 *      mutable = "false"
 */
public class Brand {

    private int id;
    private String code;
    private String name;
    
    
    /**
     * @return Returns the code.
     *
     * @hibernate.property 
     *      column = "brand_code"
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return Returns the name.
     *
     * @hibernate.property 
     *      column = "brand_name"
*/
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the id.
     * 
     * @hibernate.id
     *      column = "pmm_brand_id"
     *      generator-class = "native"
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
}
