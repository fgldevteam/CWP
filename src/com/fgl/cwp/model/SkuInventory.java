package com.fgl.cwp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "inventory_store_sku"
 *      proxy = "com.fgl.cwp.model.SkuInventory"
 * 
 * @hibernate.query 
 *      name="getSkuInventory"
 *      query="SELECT         inv
 *             FROM           SkuInventory inv
 *             JOIN           inv.id.store store
 *             WHERE          store.region = :region
 *             AND            inv.id.sku.id = :skuID
 *             ORDER BY       store.number asc"
 *   
 * @hibernate.query 
 *      name="getSkuInventoryForCMSABBC"
 *      query="SELECT         inv
 *             FROM           SkuInventory inv
 *             JOIN           inv.id.store store
 *             WHERE          (store.region = :region OR (store.banner = 'CMS' AND store.region in ('AB', 'BC')))
 *             AND            inv.id.sku.id = :skuID
 *             ORDER BY       store.number asc"
 *            
 */
public class SkuInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private SkuInventoryKey id = new SkuInventoryKey();
    private int quantity;
    private int quantityInTransit;
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "quantity"
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @return Returns the quantityInTransit.
     * @hibernate.property
     *      column = "qty_in_transit"
     */
    public int getQuantityInTransit() {
        return quantityInTransit;
    }
    /**
     * @param quantityInTransit The quantityInTransit to set.
     */
    public void setQuantityInTransit(int quantityInTransit) {
        this.quantityInTransit = quantityInTransit;
    }
    /**
     * @return returns the store
     */
    public Store getStore() {
        return id.getStore();
    }
    
    /**
     * @param store
     */
    public void setStore(Store store) {
        this.id.setStore(store);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof SkuInventory)) return false;
        
        SkuInventory inv = (SkuInventory) obj;
        return new EqualsBuilder()
        .append(this.getStore().getNumber(), inv.getStore().getNumber())
        .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
        .append(getStore().getNumber())
        .toHashCode();
    }
    
    /**
     * @return Returns the sku.
     */
    public Sku getSku() {
        return id.getSku();
    }
    /**
     * @param sku The sku to set.
     */
    public void setSku(Sku sku) {
        this.id.setSku(sku);
    }
    /**
     * @return Returns the id.
     * 
     * @hibernate.id 
     */
    public SkuInventoryKey getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(SkuInventoryKey id) {
        this.id = id;
    }
}

