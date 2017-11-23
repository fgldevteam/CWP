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
 */
public class StyleInventoryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private Style style;
    private Store store;
    
    /**
     * @return Returns the store.
     * 
     * @hibernate.many-to-one
     *      column = "store_number"
     *      
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
    /**
     * @return Returns the style.
     * 
     * @hibernate.many-to-one  
     *      column = "pmm_style_id"
     */
    public Style getStyle() {
        return style;
    }
    /**
     * @param style The style to set.
     */
    public void setStyle(Style style) {
        this.style = style;
    }
     
    
    public boolean equals(Object obj) {
        if (!(obj instanceof StyleInventoryKey)) return false;

        StyleInventoryKey key = (StyleInventoryKey) obj;
        return new EqualsBuilder()
             .append(this.getStyle().getId(), key.getStyle().getId())
             .append(this.getStore().getNumber(), key.getStore().getNumber())
             .isEquals();
   }

   public int hashCode() {
        return new HashCodeBuilder()
             .append(getStore().getNumber())
             .append(getStyle().getId())
             .toHashCode();
   }

}
