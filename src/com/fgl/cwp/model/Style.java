package com.fgl.cwp.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "style"
 *      proxy = "com.fgl.cwp.model.Style"
 * 
 * @hibernate.query 
 *      name = "getProductById"
 *      query = "SELECT            product
 *               FROM              Style product
 *               JOIN FETCH        product.hierarchy
 *               JOIN FETCH        product.brand
 *               WHERE             product.id = :id"
 * 
 * @hibernate.query 
 *      name = "getDistinctColoursForStyle"
 *      query = "SELECT    DISTINCT sku.colour
 *               FROM      Sku sku
 *               WHERE     sku.style.id = :styleID"
 *
 * @hibernate.query 
 *      name = "getDistinctSizesForStyle"
 *      query = "SELECT    DISTINCT sku.size
 *               FROM      Sku sku
 *               WHERE     sku.style.id = :styleID"
 *
 * @hibernate.query
 *      name = "getProductByUPC"
 *      query = "SELECT     style
 *               FROM       Style style
 *               JOIN       style.brand
 *               JOIN FETCH style.hierarchy
 *               JOIN       style.skus skus
 *               JOIN       skus.upcs upcs
 *               WHERE      upcs.upc = :upc"
 *               
 *
 */
public class Style {
     private Integer id;
     private String description;
     private String number;
     private String r12Number;
     private String vpn;
     private Vendor vendor;
     private Hierarchy hierarchy;
     private Brand brand;
     private ConceptShop conceptShop;
     private Set skus;
     private Set inventories;
     private List colours = new ArrayList();
     private List sizes = new ArrayList();
     private Double returnPrice;
     private Double capCostPrice;
     private Double	chainPrice;
     private Double currentPrice;
	 private DecimalFormat df;
	 private Integer conceptShopID;
	 
	 /**
	  * @return Returns the conceptShopID.
	  * @hibernate.property column = "pmm_concept_shop_id" 
	  */
	 public Integer getConceptShopID() {
		return conceptShopID;
	 }
	 /**
	  * @param number The conceptShopID to set.
	  */
	public void setConceptShopID(Integer conceptShopID) {
		this.conceptShopID = conceptShopID;
	}
    /**
     * @return Returns the conceptShop.
     */
	public ConceptShop getConceptShop() {
		return conceptShop;
	}
    /**
     * @param conceptShop The conceptShop to set.
     */
	public void setConceptShop(ConceptShop conceptShop) {
		this.conceptShop = conceptShop;
	}
	
	/**
      * @return Returns the r12Number.
      * @hibernate.property column = "r12_number" not-null = "false"
      */
     public String getR12Number() {
         return r12Number;
     }
     /**
      * @param number The r12Number to set.
      */
     public void setR12Number(String number) {
         r12Number = number;
     }
     
    /**
     * @return Returns the brand.
     * @hibernate.many-to-one
     *      column = "pmm_brand_id"
     */
    public Brand getBrand() {
        return brand;
    }
    /**
     * @param brand The brand to set.
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    /**
     * @return Returns the colours.
     */
    public List getColours() {
        return colours;
    }
    /**
     * @param colours The colours to set.
     */
    public void setColours(List colours) {
        this.colours = colours;
    }
    /**
     * @return Returns the description.
     * 
     * @hibernate.property
     *      column = "description"
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the hierarchy.
     * 
     * @hibernate.many-to-one 
     *      column = "subclass_id"
     */
    public Hierarchy getHierarchy() {
        return hierarchy;
    }
    /**
     * @param hierarchy The hierarchy to set.
     */
    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }
    /**
     * @return Returns the id.
     * 
     * @hibernate.id
     *      column = "pmm_style_id"
     *      generator-class = "native"
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return Returns the inventories.
     * 
     * @hibernate.set
     *      lazy = "true"
     * @hibernate.collection-key 
     *      column = "pmm_style_id"
     * @hibernate.collection-one-to-many 
     *      class = "com.fgl.cwp.model.StyleInventory"
     */
    public Set getInventories() {
        return inventories;
    }
    /**
     * @param inventories The inventories to set.
     */
    public void setInventories(Set inventories) {
        this.inventories = inventories;
    }
    /**
     * @return Returns the number.
     * 
     * @hibernate.property
     *      column = "number"
     */
    public String getNumber() {
        return number;
    }
    /**
     * @param number The number to set.
     */
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * @return Returns the sizes.
     */
    public List getSizes() {
        return sizes;
    }
    /**
     * @param sizes The sizes to set.
     */
    public void setSizes(List sizes) {
        this.sizes = sizes;
    }
    /**
     * @return Returns the skus.
     * 
     * @hibernate.set
     *      lazy = "true"
     * @hibernate.collection-key 
     *      column = "pmm_style_id"
     * @hibernate.collection-one-to-many 
     *      class = "com.fgl.cwp.model.Sku"
     */
    public Set getSkus() {
        return skus;
    }
    /**
     * @param skus The skus to set.
     */
    public void setSkus(Set skus) {
        this.skus = skus;
    }
    /**
     * @return Returns the vendor.
     * 
     * @hibernate.many-to-one 
     *      column = "vendor_id"
     */
    public Vendor getVendor() {
        return vendor;
    }
    /**
     * @param vendor The vendor to set.
     */
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    /**
     * @return Returns the vpn.
     * 
     * @hibernate.property
     *      column = "vpn"
     */
    public String getVpn() {
        return vpn;
    }
    /**
     * @param vpn The vpn to set.
     */
    public void setVpn(String vpn) {
        this.vpn = vpn;
    }
    
    /**
     * @return Returns the receiptless return price.
     */
	public Double getReturnPrice() {
		return returnPrice;
	}
	
	public void setReturnPrice(Double returnPrice) {
		this.returnPrice = returnPrice;
	}
	
	public Double getChainPrice() {
		return chainPrice;
	}
	
	public void setChainPrice(Double chainPrice) {
		this.chainPrice = chainPrice;
	}
	
	public Double getCurrentPrice() {
		return currentPrice;
	}
	
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	
	public Double getCapCostPrice() {
		return capCostPrice;
	}
	
	public void setCapCostPrice(Double capCostPrice) {
		this.capCostPrice = capCostPrice;
	}
	
	public String getPrice(){
		if (df == null) {
			df = new DecimalFormat("#.00");
		}
		if (currentPrice != null) {
			return df.format(currentPrice);
		} else if (chainPrice != null) {
			return df.format(chainPrice);
		}
		return null;
	}
	
	public String getReceiptlessPrice(){
		if (df == null) {
			df = new DecimalFormat("#.00");
		}
		if (returnPrice != null) {
			return df.format(returnPrice);
		}
		return null;
	}
	
	
	public String getCostPrice(){
		if (df == null) {
			df = new DecimalFormat("#.00");
		}
		if (capCostPrice != null) {
			return df.format(capCostPrice);
		}
		return null;
	}
		
}
