/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author dschultz
 * 
 */
public class SkuInventoryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private Store store;
    private Sku sku;

    /**
     * @return Returns the sku.
     * 
     * @hibernate.many-to-one 
     *      column = "pmm_sku_id" 
     */
    public Sku getSku() {
        return sku;
    }
    /**
     * @param sku The sku to set.
     */
    public void setSku(Sku sku) {
        this.sku = sku;
    }
    /**
     * @return Returns the store
     * 
     * @hibernate.many-to-one
     *      column = "store_number"
     */
    public Store getStore() {
        return store;
    }
    /**
     * @param store The store to set.
     */
    public void setStore(Store store) {
        this.store = store;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof SkuInventoryKey)) return false;

        SkuInventoryKey key = (SkuInventoryKey) obj;
        return new EqualsBuilder()
             .append(this.getStore().getNumber(), key.getStore().getNumber())
             .append(this.getSku().getId(), key.getSku().getId())
             .isEquals();
   }

   public int hashCode() {
        return new HashCodeBuilder()
             .append(getSku().getId())
             .append(getStore().getNumber())
             .toHashCode();
   }
}

