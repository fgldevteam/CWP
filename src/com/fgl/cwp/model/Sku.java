package com.fgl.cwp.model;

import java.util.Set;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "sku"
 *      proxy = "com.fgl.cwp.model.Sku"
 */
public class Sku {

     private Integer id;
     private String description;
     private String number;
     private ColourSize colour;
     private ColourSize size;
     private Set upcs;
     private Set inventories;
     private Style style;
     private String notes;


     /**
     * @return
     * 
     * @hibernate.id
     *      column = "pmm_sku_id"
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
     * 
     * @hibernate.property 
     *      column = "description"
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

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "number"
     */
    public String getNumber() {
          return number;
     }

     /**
     * @param number
     */
    public void setNumber(String number) {
          this.number = number;
     }

     /**
     * @return
     * 
     * @hibernate.many-to-one 
     *      column = "colour_code"
     */
    public ColourSize getColour() {
          return colour;
     }

     /**
     * @param colour
     */
    public void setColour(ColourSize colour) {
          this.colour = colour;
     }

     /**
     * @return
     * 
     * @hibernate.many-to-one 
     *      column = "size_code"
     */
    public ColourSize getSize() {
          return size;
     }

     /**
     * @param size
     */
    public void setSize(ColourSize size) {
          this.size = size;
     }

     /**
     * @return
     * 
     * @hibernate.set
     *      lazy = "true"
     * @hibernate.collection-key 
     *      column = "pmm_sku_id"
     * @hibernate.collection-one-to-many 
     *      class = "com.fgl.cwp.model.Upc"
     */
    public Set getUpcs() {
          return upcs;
     }

     /**
     * @param upcs
     */
    public void setUpcs(Set upcs) {
          this.upcs = upcs;
     }

     /**
     * @return Returns the inventory
     * 
     * @hibernate.set
     *      table = "inventory_store_sku"
     *      lazy = "true"
     * @hibernate.collection-key 
     *      column = "pmm_sku_id"
     * @hibernate.collection-one-to-many 
     *      class = "com.fgl.cwp.model.SkuInventory"
     */
    public Set getInventories() {
          return inventories;
     }

     /**
     * @param inventories
     */
    public void setInventories(Set inventories) {
          this.inventories = inventories;
     }

     /**
     * @return
     * 
     * @hibernate.many-to-one 
     *      column = "pmm_style_id"
     */
    public Style getStyle() {
          return style;
     }

     /**
     * @param style
     */
    public void setStyle(Style style) {
          this.style = style;
     }

    /**
     * @return Returns the notes.
     * @hibernate.property
     *      column = "notes"
     *      not-null = "false"
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes The notes to set.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    

}
