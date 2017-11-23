/*
 * Created on Jan 27, 2012
 *
 */
package com.fgl.cwp.model;

/**
 * @author mmohammed
 * 
 * @hibernate.class
 *      table = "concept_shop"
 *      proxy = "com.fgl.cwp.model.ConceptShop"
 *      mutable = "false"
 */
public class ConceptShop {

    private int id;
    private String code;
    private String name;
    
    
    /**
     * @return Returns the code.
     *
     * @hibernate.property 
     *      column = "pmm_concept_shop_code"
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
     *      column = "pmm_concept_shop_name"
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
     *      column = "pmm_concept_shop_id"
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
