package com.fgl.cwp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StyleCurrentPriceKey extends StylePriceKey {

	private static final long serialVersionUID = 1L;
	private Integer storeNumber;
	
    /**
     * @return Returns the store.
     * 
     * @hibernate.property
     * 		type = "java.lang.Integer"
     *      column = "store_number"
     *      
     */
    public Integer getStoreNumber() {
        return storeNumber;
    }
    /**
     * @param store The store to set.
     */
    public void setStoreNumber(Integer storeNumber) {
        this.storeNumber = storeNumber;
    }
    
    /**
     * @override
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof StyleCurrentPriceKey)) {
        	return false;
        }

        StyleCurrentPriceKey key = (StyleCurrentPriceKey) obj;
        EqualsBuilder eqBuilder = new EqualsBuilder();
        
        if(styleId != null && key.getStyleId() != null){
        	eqBuilder.append(this.styleId, key.getStyleId());
        }
        
        if(storeNumber != null && key.getStoreNumber() != null){
        	eqBuilder.append(this.storeNumber, key.getStoreNumber());
        }
        
        if(capId != null && key.getCapId() != null){
        	eqBuilder.append(this.capId, key.getCapId());
        }
        
        return eqBuilder.isEquals();
   }

    /**
     * @override
     */
   public int hashCode() {
	   HashCodeBuilder hcBuilder = new HashCodeBuilder();
	   if (styleId != null) {
		   hcBuilder.append(styleId);
	   }
	   if(storeNumber != null){
		   hcBuilder.append(storeNumber);
	   }
	   
	   if(capId != null){
		   hcBuilder.append(capId);
	   }
	   return hcBuilder.toHashCode();
   }
}
