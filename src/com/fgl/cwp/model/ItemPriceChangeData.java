/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package com.fgl.cwp.model;

import java.util.Date;

/**
 * TODO Add Class Commments
 * @author dschultz
 */
public class ItemPriceChangeData {
    private int storeNumber;
    private Date startDate;
    private Date endDate;
    private String priorityName;
    private int deptId;
    private int subDeptId;
    private String brandCode;
    private String brandName;
    private String styleNumber;
    private String r12StyleNumber;
    private String vpn;
    private String styleDescription;
    private float listPrice;
    private float previousPrice;
    private float eventPrice;
    private int quantity;
    private int quantityInTransit;
    private String deptName;
    private String subDeptName;
    private String categoryName;
    private String upc;
    
    /**
     * @return Returns the upc.
     */
    public String getUpc() {
        Upc upc = new Upc();
        upc.setUpc(this.upc);
        return upc.getUpc();
    }
    
    /**
     * @param storeNumber
     * @param startDate
     * @param endDate
     * @param priorityName
     * @param deptId
     * @param subDeptId
     * @param brandCode
     * @param brandName
     * @param styleNumber
     * @param r12StyleNumber
     * @param vpn
     * @param styleDescription
     * @param listPrice
     * @param previousPrice
     * @param eventPrice
     * @param quantity
     * @param quantityInTransit
     * @param deptName
     * @param subDeptName
     * @param categoryName
     * @param upc
     */
    
    public ItemPriceChangeData(int storeNumber, Date startDate, Date endDate, String priorityName, int deptId,
            int subDeptId, String brandCode, String brandName, String styleNumber, String r12StyleNumber, String vpn, String styleDescription,
            float listPrice, float previousPrice, float eventPrice, int quantity, int quantityInTransit, String deptName, String subDeptName, String categoryName, String upc) {
        super();
        this.storeNumber = storeNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priorityName = priorityName;
        this.deptId = deptId;
        this.subDeptId = subDeptId;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.styleNumber = styleNumber;
        this.r12StyleNumber = r12StyleNumber;
        this.vpn = vpn;
        this.styleDescription = styleDescription;
        this.listPrice = listPrice;
        this.previousPrice = previousPrice;
        this.eventPrice = eventPrice;
        this.quantity = quantity;
        this.quantityInTransit = quantityInTransit;
        this.deptName = deptName;
        this.subDeptName = subDeptName;
        this.categoryName = categoryName;
        this.upc = upc;
    }
    /**
     * @return Returns the brandCode.
     */
    public String getBrandCode() {
        return brandCode;
    }
    
    /**
     * @param brandCode The brandCode to set.
     */
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }
    
    /**
     * @return Returns the brandName.
     */
    public String getBrandName() {
        return brandName;
    }
    
    /**
     * @param brandName The brandName to set.
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    /**
     * @return Returns the categoryName.
     */
    public String getCategoryName() {
        return categoryName;
    }
    /**
     * @param categoryName The categoryName to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    /**
     * @return Returns the deptId.
     */
    public int getDeptId() {
        return deptId;
    }
    /**
     * @param deptId The deptId to set.
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    /**
     * @return Returns the deptName.
     */
    public String getDeptName() {
        return deptName;
    }
    /**
     * @param deptName The deptName to set.
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    /**
     * @return Returns the endDate.
     */
    public Date getEndDate() {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    /**
     * @return Returns the eventPrice.
     */
    public float getEventPrice() {
        return eventPrice;
    }
    /**
     * @param eventPrice The eventPrice to set.
     */
    public void setEventPrice(float eventPrice) {
        this.eventPrice = eventPrice;
    }
    /**
     * @return Returns the listPrice.
     */
    public float getListPrice() {
        return listPrice;
    }
    /**
     * @param listPrice The listPrice to set.
     */
    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }
    
    /**
     * @return Returns the priorityName.
     */
    public String getPriorityName() {
        return priorityName;
    }
    /**
     * @param priorityName The priorityName to set.
     */
    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }
    /**
     * @return Returns the quantity.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * @param quantity The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * @return Returns the quantityInTransit.
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
     * @return Returns the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    /**
     * @return Returns the storeNumber.
     */
    public int getStoreNumber() {
        return storeNumber;
    }
    /**
     * @param storeNumber The storeNumber to set.
     */
    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }
    /**
     * @return Returns the styleDescription.
     */
    public String getStyleDescription() {
        return styleDescription;
    }
    /**
     * @param styleDescription The styleDescription to set.
     */
    public void setStyleDescription(String styleDescription) {
        this.styleDescription = styleDescription;
    }
    /**
     * @return Returns the styleNumber.
     */
    public String getStyleNumber() {
        return styleNumber;
    }
    /**
     * @param styleNumber The styleNumber to set.
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }
    /**
     * @return Returns the subDeptId.
     */
    public int getSubDeptId() {
        return subDeptId;
    }
    /**
     * @param subDeptId The subDeptId to set.
     */
    public void setSubDeptId(int subDeptId) {
        this.subDeptId = subDeptId;
    }
    /**
     * @return Returns the subDeptName.
     */
    public String getSubDeptName() {
        return subDeptName;
    }
    /**
     * @param subDeptName The subDeptName to set.
     */
    public void setSubDeptName(String subDeptName) {
        this.subDeptName = subDeptName;
    }
    /**
     * @return Returns the vpn.
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
     * @return Returns the r12StyleNumber.
     */
    public String getR12StyleNumber() {
        return r12StyleNumber;
    }
    /**
     * @param styleNumber The r12StyleNumber to set.
     */
    public void setR12StyleNumber(String styleNumber) {
        r12StyleNumber = styleNumber;
    }

    /**
     * @return The previous ticket price for the item
     */
	public float getPreviousPrice() {
		return previousPrice;
	}

	/**
	 * @param previousPrice The previous ticket price for the item
	 */
	public void setPreviousPrice(float previousPrice) {
		this.previousPrice = previousPrice;
	}
}
