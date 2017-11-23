package com.fgl.cwp.model;

/**
 * @author Jessica Wong
 */
public class HierarchyItem {

    private Integer id = null;
    private String description = null;

    /**
     * 
     *
     */
    public HierarchyItem() {
        //empty constructor
    }

    /**
     * 
     * @param hierarchyID
     * @param desc
     */
    public HierarchyItem(Integer hierarchyID, String desc) {
        id = hierarchyID;
        description = desc;
    }

    /**
     * @return
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
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}