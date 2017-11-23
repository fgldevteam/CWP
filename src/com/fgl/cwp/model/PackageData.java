package com.fgl.cwp.model;
import java.util.Date;

/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */

/**
 * A data container for the Package Price Change Report
 * @author dschultz
 */
public class PackageData {
    private Date endDate;
    private int deptID;
    private int classID;
    private int subClassID;
    private int subDeptID;
    private String brandCode;
    private String brandName;
    private String styleDescription;
    private float listPrice;
    private float eventPrice;
    private String priorityName;
    private Date effectiveDate;
    private String styleNumber;
    private String r12StyleNumber;
    private int storeNumber;
    private String method;
    private String methodName;
    private String methodNumber;
    private String method2;
    private int multiples;
    private String methodName2;
    private String methodNumber2;
    private int breakQuantity;
    private int buyQuantity;
    private int getQuantity;
    private String vpn;
    private Date createdDate;
    private int getValue;
    private char getType;

    /**
     * @param endDate
     * @param deptID
     * @param classID
     * @param subClassID
     * @param subDeptID
     * @param brandCode
     * @param brandName
     * @param styleDescription
     * @param listPrice
     * @param eventPrice
     * @param priorityName
     * @param effectiveDate
     * @param styleNumber
     * @param r12StyleNumber
     * @param storeNumber
     * @param method
     * @param methodName
     * @param methodNumber
     * @param method2
     * @param multiples
     * @param methodName2
     * @param methodNumber2
     * @param breakQuantity
     * @param buyQuantity
     * @param getQuantity
     * @param vpn
     * @param createdDate
     * @param getValue
     * @param getType
     */
    public PackageData(Date endDate, int deptID, int classID, int subClassID, int subDeptID, String brandCode,
            String brandName, String styleDescription, float listPrice, float eventPrice, String priorityName,
            Date effectiveDate, String styleNumber, String r12StyleNumber, int storeNumber, String method, String methodName,
            String methodNumber, String method2, int multiples, String methodName2, String methodNumber2,
            int breakQuantity, int buyQuantity, int getQuantity, String vpn, Date createdDate, int getValue,
            char getType) {
        super();
        this.endDate = endDate;
        this.deptID = deptID;
        this.classID = classID;
        this.subClassID = subClassID;
        this.subDeptID = subDeptID;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.styleDescription = styleDescription;
        this.listPrice = listPrice;
        this.eventPrice = eventPrice;
        this.priorityName = priorityName;
        this.effectiveDate = effectiveDate;
        this.styleNumber = styleNumber;
        this.r12StyleNumber = r12StyleNumber;
        this.storeNumber = storeNumber;
        this.method = method;
        this.methodName = methodName;
        this.methodNumber = methodNumber;
        this.method2 = method2;
        this.multiples = multiples;
        this.methodName2 = methodName2;
        this.methodNumber2 = methodNumber2;
        this.breakQuantity = breakQuantity;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.vpn = vpn;
        this.createdDate = createdDate;
        this.getValue = getValue;
        this.getType = getType;
    }
    /**
     * @return Returns the breakQuantity.
     */
    public int getBreakQuantity() {
        return breakQuantity;
    }
    /**
     * @param breakQuantity The breakQuantity to set.
     */
    public void setBreakQuantity(int breakQuantity) {
        this.breakQuantity = breakQuantity;
    }
    /**
     * @return Returns the buyQuantity.
     */
    public int getBuyQuantity() {
        return buyQuantity;
    }
    /**
     * @param buyQuantity The buyQuantity to set.
     */
    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
    /**
     * @return Returns the classID.
     */
    public int getClassID() {
        return classID;
    }
    /**
     * @param classID The classID to set.
     */
    public void setClassID(int classID) {
        this.classID = classID;
    }
    /**
     * @return Returns the createdDate.
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
     * @return Returns the deptID.
     */
    public int getDeptID() {
        return deptID;
    }
    /**
     * @param deptID The deptID to set.
     */
    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
    /**
     * @return Returns the effectiveDate.
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
     * @return Returns the getQuantity.
     */
    public int getGetQuantity() {
        return getQuantity;
    }
    /**
     * @param getQuantity The getQuantity to set.
     */
    public void setGetQuantity(int getQuantity) {
        this.getQuantity = getQuantity;
    }
    /**
     * @return Returns the getType.
     */
    public char getGetType() {
        return getType;
    }
    /**
     * @param getType The getType to set.
     */
    public void setGetType(char getType) {
        this.getType = getType;
    }
    /**
     * @return Returns the getValue.
     */
    public int getGetValue() {
        return getValue;
    }
    /**
     * @param getValue The getValue to set.
     */
    public void setGetValue(int getValue) {
        this.getValue = getValue;
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
     * @return Returns the method.
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
     * @return Returns the methodNumber2.
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
     * @return Returns the multiples.
     */
    public int getMultiples() {
        return multiples;
    }
    /**
     * @param multiples The multiples to set.
     */
    public void setMultiples(int multiples) {
        this.multiples = multiples;
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
     * @return Returns the subClassID.
     */
    public int getSubClassID() {
        return subClassID;
    }
    /**
     * @param subClassID The subClassID to set.
     */
    public void setSubClassID(int subClassID) {
        this.subClassID = subClassID;
    }
    /**
     * @return Returns the subDeptID.
     */
    public int getSubDeptID() {
        return subDeptID;
    }
    /**
     * @param subDeptID The subDeptID to set.
     */
    public void setSubDeptID(int subDeptID) {
        this.subDeptID = subDeptID;
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
}
