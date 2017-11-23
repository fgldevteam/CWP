package com.fgl.cwp.model;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "upc"
 */
public class Upc implements Serializable {
    private static final long serialVersionUID = 1L;
    private String upc;
    private Sku sku;
    private boolean type;

    /**
     * @return Returns the sku.
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
     * @return
     * 
     * @hibernate.id
     *      generator-class = "assigned"
     */
    public String getUpc() {
        
        // TODO: BYT - This code doesn't seem to be doing anything???
        //String upcTemp = ("000000000000" + upc);
        //upcTemp = upcTemp.substring(upcTemp.length() - 12);
        // END BYT
        if (upc != null) {
            //return StringUtils.leftPad(upc, 14, '0');
        	return upc;
        }
        return "";
    }
    
    /**
     * @param upc
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }
    
    /**
     * @return True if the UPC is internal (i.e., starts with 4)
     */
    public boolean isType() {
        this.type = isInternalUPC();
        
        return this.type;
    }
    
    /**
     * @return true if internal UPC
     */
    private boolean isInternalUPC() {
        //internal upc's start with a 4
        if (upc != null) {
            if (upc.toString().substring(0, 1).equals("4")
                    && upc.toString().length() == 12) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @param type
     */
    public void setType(boolean type) {
        this.type = type;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj instanceof Upc) {
            Upc upcInstance = (Upc) obj;
            if (this.getUpc().equals(upcInstance.getUpc())) {
                return true;
            }
        }
        return false;
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
                .append(upc)
                .toHashCode();
    }
    
}
