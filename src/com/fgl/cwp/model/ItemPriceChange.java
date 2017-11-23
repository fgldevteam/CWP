package com.fgl.cwp.model;
import java.util.Date;


/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */

/**
 * Used for the item price change report and deal group reports
 * @author dschultz
 * @hibernate.class table = "v_item_price_change"
 * 
 * @hibernate.query
 *      name = "countItemPriceChangeReport"
 *      query = 
 "select count(ipc.id.style.id) from 
     ItemPriceChange ipc 
     inner join ipc.id.style.inventories inv 
  where 
     ipc.id.store.number = :storeNumber 
     and ipc.direction = :priceDirection 
     and ipc.effectiveDate between :startDate and :endDate 
     and ipc.method = 'SP' 
     and inv.id.store.number = ipc.id.store.id 
     and (inv.quantity > 0 or inv.quantityInTransit > 0)" 
     
 * @hibernate.query
 *      name = "itemPriceChangeReport"
 *      query = 
 "select new com.fgl.cwp.model.ItemPriceChangeData(
     ipc.id.store.number, 
     ipc.effectiveDate, 
     ipc.endDate, 
     ipc.priorityName, 
     h.deptID, 
     h.subDeptID, 
     b.code, 
     b.name, 
     s.number, 
     s.r12Number, 
     s.vpn, 
     s.description, 
     ipc.listPrice,
     ipc.previousPrice,
     ipc.eventPrice, 
     inv.quantity,
     inv.quantityInTransit,
     h.deptDescription, 
     h.subDeptDescription, 
     h.divisionDescription,
     ipc.upc ) 
 from 
     ItemPriceChange ipc 
     inner join ipc.id.style.hierarchy as h 
     inner join ipc.id.style as s 
     inner join ipc.id.style.brand as b 
     inner join ipc.id.style.inventories inv 
 where 
     ipc.id.store.number = :storeNumber 
     and ipc.direction in (:priceDirectionList) 
     and ipc.effectiveDate between :startDate and :endDate 
     and ipc.method = 'SP' 
     and inv.id.store.number = ipc.id.store.id 
     and (inv.quantity > 0 or inv.quantityInTransit > 0) 
 order by 
     h.divisionDescription, 
     ipc.effectiveDate, 
     ipc.endDate, 
     ipc.priorityName, 
     h.deptID, 
     h.subDeptID, 
     h.classID, 
     b.code, 
     ipc.id.style.number"
     
 * @hibernate.query
 *      name = "countPackageReport"
 *      query =
 "select count(ipc.id.style.id) from
     ItemPriceChange ipc
 where
     ipc.method <> 'SP'
     and ipc.id.store.number = :storeNumber
     and ipc.direction = :priceDirection
     and ipc.effectiveDate between :startDate and :endDate"
          
 * @hibernate.query
 *      name = "packageReport"
 *      query =
 "select new com.fgl.cwp.model.PackageData(
     ipc.endDate,
     h.deptID,
     h.classID,
     h.subClassID,
     h.subDeptID,
     b.code,
     b.name,
     s.description,
     ipc.listPrice,
     ipc.eventPrice,
     ipc.priorityName,
     ipc.effectiveDate,
     s.number,
     s.r12Number,
     ipc.id.store.number,
     ipc.method,
     ipc.methodName,
     ipc.methodNumber,
     ipc.method2,
     ipc.multiples,
     ipc.methodName2,
     ipc.methodNumber2,
     ipc.breakQuantity,
     ipc.buyQuantity,
     ipc.getQuantity,
     s.vpn,
     ipc.createdDate,
     ipc.getValue,
     ipc.getType)
 from
     ItemPriceChange ipc
     inner join ipc.id.style s
     inner join ipc.id.style.hierarchy h
     inner join ipc.id.style.brand b
 where
     ipc.method <> 'SP'
     and ipc.id.store.number = :storeNumber
     and ipc.direction = :priceDirection
     and ipc.effectiveDate between :startDate and :endDate
 order by
     ipc.effectiveDate,
     ipc.endDate,
     ipc.method,
     ipc.methodNumber,
     ipc.method2,
     ipc.methodNumber2,
     ipc.id.style.number,
     ipc.breakQuantity"
 */
public class ItemPriceChange {
    private ItemPriceChangeKey id;
    private Date createdDate;
    private Date effectiveDate;
    private Date endDate;
    private String priorityName;
    private float listPrice;
    private float previousPrice;
    private float eventPrice;
    private int direction; //0 = deal group, 1= mark up, 2= markdown
    private String method; //must be "SP" for the item price change report
    private String methodName;
    private String methodNumber;
    private String method2;
    private String methodName2;
    private String methodNumber2;    
    private Integer multiples;
    private Integer breakQuantity;
    private Integer buyQuantity;
    private Integer getQuantity;
    private Integer getValue;
    private Character getType;
    private String upc;
    
    /**
     * <code>MARKUP</code> valid value for priceDirection property
     */
    public static final int MARKUP = 1;
    /**
     * <code>MARKDOWN</code> valid value for priceDirection property
     */
    public static final int MARKDOWN = 2;
    /**
     * <code>TICKET_PRICE</code> valid value for priceDirection property
     */
    public static final int TICKET_PRICE = 3;
    /**
     * <code>DEAL_GROUP</code> valid value for priceDirection property
     */
    public static final int DEAL_GROUP = 0;
    
    /**
     * @return Returns the methodNumber2.
     * @hibernate.property column = "prc_method_number_2"
     */
    public String getMethodNumber2() {
        return methodNumber2;
    }
    /**
     * @param methodNumber2 The methodNumber2 to set.
     */
    public void setMethodNumber2(String methodNumber2) {
        this.methodNumber2 = methodNumber2;
    }
        
    /**
     * @return Returns the breakQuantity.
     * @hibernate.property column = "prc_qty_brk_qty"  
     */
    public Integer getBreakQuantity() {
        return breakQuantity;
    }
    /**
     * @param breakQuantity The breakQuantity to set.
     */
    public void setBreakQuantity(Integer breakQuantity) {
        this.breakQuantity = breakQuantity;
    }
    /**
     * @return Returns the buyQuantity.
     * @hibernate.property column = "prc_buy_qty"
     */
    public Integer getBuyQuantity() {
        return buyQuantity;
    }
    /**
     * @param buyQuantity The buyQuantity to set.
     */
    public void setBuyQuantity(Integer buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
    /**
     * @return Returns the createdDate.
     * @hibernate.property column = "date_created"
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * @return Returns the direction.
     * @hibernate.property column = "prc_direction"
     */
    public int getDirection() {
        return direction;
    }
    /**
     * @param direction The direction to set.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
    /**
     * @return Returns the effectiveDate.
     * @hibernate.property column = "prc_effective_date"
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    /**
     * @param effectiveDate The effectiveDate to set.
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    /**
     * @return Returns the endDate.
     * @hibernate.property column = "prc_end_date"
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
     * @hibernate.property column = "event_price"
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
     * @return Returns the getQuantity.
     * @hibernate.property column = "prc_get_qty"
     */
    public Integer getGetQuantity() {
        return getQuantity;
    }
    /**
     * @param getQuantity The getQuantity to set.
     */
    public void setGetQuantity(Integer getQuantity) {
        this.getQuantity = getQuantity;
    }
    /**
     * @return Returns the getType.
     * @hibernate.property column = "prc_get_type"
     */
    public Character getGetType() {
        return getType;
    }
    /**
     * @param getType The getType to set.
     */
    public void setGetType(Character getType) {
        this.getType = getType;
    }
    /**
     * @return Returns the getValue.
     * @hibernate.property column = "prc_get_value"
     */
    public Integer getGetValue() {
        return getValue;
    }
    /**
     * @param getValue The getValue to set.
     */
    public void setGetValue(Integer getValue) {
        this.getValue = getValue;
    }
    /**
     * @return Returns the key.
     * @hibernate.id
     */
    public ItemPriceChangeKey getId() {
        return id;
    }
    /**
     * @param key The key to set.
     */
    public void setId(ItemPriceChangeKey key) {
        this.id = key;
    }
    /**
     * @return Returns the listPrice.
     * @hibernate.property column = "list_price"
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
     * @return Returns the previousPrice.
     * @hibernate.property column = "previous_price"
     */
    public float getPreviousPrice() {
        return previousPrice;
    }
    /**
     * @param previousPrice The previous price to set.
     */
    public void setPreviousPrice(float previousPrice) {
        this.previousPrice = previousPrice;
    }
    /**
     * @return Returns the method.
     * @hibernate.property column = "prc_method"
     */
    public String getMethod() {
        return method;
    }
    /**
     * @param method The method to set.
     */
    public void setMethod(String method) {
        this.method = method;
    }
    /**
     * @return Returns the method2.
     * @hibernate.property column = "prc_method_2"
     */
    public String getMethod2() {
        return method2;
    }
    /**
     * @param method2 The method2 to set.
     */
    public void setMethod2(String method2) {
        this.method2 = method2;
    }
    /**
     * @return Returns the methodName.
     * @hibernate.property column = "prc_method_name"
     */
    public String getMethodName() {
        return methodName;
    }
    /**
     * @param methodName The methodName to set.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /**
     * @return Returns the methodName2.
     * @hibernate.property column = "prc_method_name_2"
     */
    public String getMethodName2() {
        return methodName2;
    }
    /**
     * @param methodName2 The methodName2 to set.
     */
    public void setMethodName2(String methodName2) {
        this.methodName2 = methodName2;
    }
    /**
     * @return Returns the methodNumber.
     * @hibernate.property column = "prc_method_number"
     */
    public String getMethodNumber() {
        return methodNumber;
    }
    /**
     * @param methodNumber The methodNumber to set.
     */
    public void setMethodNumber(String methodNumber) {
        this.methodNumber = methodNumber;
    }
    /**
     * @return Returns the multiples.
     * @hibernate.property column = "prc_mult"
     */
    public Integer getMultiples() {
        return multiples;
    }
    /**
     * @param multiples The multiples to set.
     */
    public void setMultiples(Integer multiples) {
        this.multiples = multiples;
    }
    /**
     * @return Returns the priorityName.
     * @hibernate.property column = "price_priority_name"
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
     * @return Returns the upc.
     * @hibernate.property column="upc"
     */
    public String getUpc() {
        return upc;
    }
    
    /**
     * @param upc The upc to set.
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }
}
