package com.fgl.cwp.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StylePriceKey implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Integer styleId;
    protected Integer capId;
    
    /**
     * @return Returns the style.
     * 
     * @hibernate.property
     * 		type = "java.lang.Integer"  
     *      column = "pmm_style_id"
     */
    public Integer getStyleId() {
        return styleId;
    }
    /**
     * @param style The style to set.
     */
    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }
    
    /**
     * @return Returns the cap ID.
     * 
     * @hibernate.property
     * 		type = "java.lang.Integer"  
     *      column = "cap_id"
     */
    public Integer getCapId() {
    	return capId;
    }
    /**
     * @param capId The cap ID to set.
     */
    public void setCapId(Integer capId) {
    	this.capId = capId;
    }
     
    public boolean equals(Object obj) {
        if (!(obj instanceof StylePriceKey)) {
        	return false;
        }

        StylePriceKey key = (StylePriceKey) obj;
        EqualsBuilder eqBuilder = new EqualsBuilder();
        
        if(styleId != null && key.getStyleId() != null){
        	eqBuilder.append(this.styleId, key.getStyleId());
        }
        
        if(capId != null && key.getCapId() != null){
        	eqBuilder.append(this.capId, key.getCapId());
        }
        
        return eqBuilder.isEquals();
   }

   public int hashCode() {
	   HashCodeBuilder hcBuilder = new HashCodeBuilder();
	   if (styleId != null) {
		   hcBuilder.append(styleId);
	   }
	   
	   if(capId != null){
		   hcBuilder.append(capId);
	   }
	   return hcBuilder.toHashCode();
   }

}

