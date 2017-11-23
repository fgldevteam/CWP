package com.fgl.cwp.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class 
 *      table = "store"
 *      mutable = "false"
 * 
 * @hibernate.query 
 *       name = "getStoreByID"
 *       query = "SELECT    store
 *                FROM      Store store
 *                WHERE     store.number = :number"
 * @hibernate.query
 *       name = "getAllStores"
 *       query = "from Store"
 */
public class Store implements Comparable {

     private static String BANNER_SPORT_CHECK = "SC";
     private static String BANNER_COAST_MOUNTAIN_SPORTS = "CMS";
     
     private int number;
     private String name;
     private String shortName;
     private String description;
     private String areaCode;
     private String phoneNumber;
     private String faxAreaCode;
     private String faxNumber;
     private String city;
     private String banner;
     private String region;
     private String address;
     private String province;
     private String postalCode;
     private Integer relatedStoreNumber;
     private Integer capId;

     /**
      * Compare value.
      * @param o
      * @return
      */
     public int compareTo(Object o) {
         Store s = (Store)o;
         if (this.number < s.getNumber()) {
             return -1;
         }
         if (this.number == s.getNumber()) {
             return 0;
         }
         return 1;
     }
     
     /**
      * Return the email address for this store. This is not a persisted value.
      * It is generated based on a mask value:
      * For Sport Check:
      *     sc####@forzani.net
      *     
      * For Coast Mountain Sports:
      *     cm####&forzani.net
      *     
      * @return
      */
     public String getEmailAddress() {

         if (banner != null) {
             if (banner.toUpperCase().equals(BANNER_SPORT_CHECK.toUpperCase())) {
                 return "sc" + StringUtils.leftPad(String.valueOf(this.number),4,'0') + "@forzani.net";
             }
             if (banner.toUpperCase().equals(BANNER_COAST_MOUNTAIN_SPORTS.toUpperCase())) {
                 return "cm" + StringUtils.leftPad(String.valueOf(this.number),4,'0') + "@forzani.net";
             }
         }
         return null;
     }
     
     /**
      * Return the store number and name in one string.
      * @return
      */
     public String getFullName() {
         StringBuffer buf = new StringBuffer();
         buf.append(number);
         if (buf.length()>0) {
             buf.append(" - ");
         }
         buf.append(name);
         return buf.toString();
         
     }
     /**
      * @return
      * 
      * @hibernate.id 
      *      column = "number"
      *      generator-class = "assigned"
      */
     public int getNumber() {
         return number;
     }
     
     /**
      * @param number
      */
     public void setNumber(int number) {
         this.number = number;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "full_name"
      */
     public String getName() {
         return name;
     }
     
     /**
      * @param name
      */
     public void setName(String name) {
         this.name = name;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "short_name"
      */
     public String getShortName() {
         return shortName;
     }
     
     /**
      * @param shortName
      */
     public void setShortName(String shortName) {
         this.shortName = shortName;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "description"
      */
     public String getDescription() {
         return description;
     }
     
     /**
      * @param description
      */
     public void setDescription(String description) {
         this.description = description;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "area_code"
      */
     public String getAreaCode() {
         return areaCode;
     }
     
     /**
      * @param areaCode
      */
     public void setAreaCode(String areaCode) {
         this.areaCode = areaCode;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "phone_number"
      */
     public String getPhoneNumber() {
         return phoneNumber;
     }
     
     /**
      * @param phoneNumber
      */
     public void setPhoneNumber(String phoneNumber) {
         this.phoneNumber = phoneNumber;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "city"
      */
     public String getCity() {
         return city;
     }
     
     /**
      * @param city
      */
     public void setCity(String city) {
         this.city = city;
     }
     
     /**
      * @return
      * @hibernate.property
      *      column = "chain"
      */
     public String getBanner() {
         return banner;
     }
     
     /**
      * @param banner
      */
     public void setBanner(String banner) {
         this.banner = banner;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "region"
      */
     public String getRegion() {
         return region;
     }
     
     /**
      * @param region
      */
     public void setRegion(String region) {
         this.region = region;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "fax_area_code"
      */
     public String getFaxAreaCode() {
         return faxAreaCode;
     }
     
     /**
      * @param faxAreaCode
      */
     public void setFaxAreaCode(String faxAreaCode) {
         this.faxAreaCode = faxAreaCode;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "fax_number"
      */
     public String getFaxNumber() {
         return faxNumber;
     }
     
     /**
      * @param faxNumber
      */
     public void setFaxNumber(String faxNumber) {
         this.faxNumber = faxNumber;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "address"
      */
     public String getAddress() {
         return address;
     }
     
     /**
      * @param address
      */
     public void setAddress(String address) {
         this.address = address;
     }
     
     /**
      * @return
      * 
      * @hibernate.property 
      *      column = "province"
      */
     public String getProvince() {
         return province;
     }
     
     /**
      * @param province
      */
     public void setProvince(String province) {
         this.province = province;
     }
     
     /**
      * @return Returns the postalCode.
      * @hibernate.property
      *         column = "postal_code"
      */
     public String getPostalCode() {
         return postalCode;
     }
     /**
      * @param postalCode The postalCode to set.
      */
     public void setPostalCode(String postalCode) {
         this.postalCode = postalCode;
     }

    /**
     * @return Returns the relatedStoreNumber.
     * @hibernate.property
     *      column = "related_store_number"
     *      not-null = "false"
     */
    public Integer getRelatedStoreNumber() {
        return relatedStoreNumber;
    }
    

    /**
     * @param relatedStoreNumber The relatedStoreNumber to set.
     */
    public void setRelatedStoreNumber(Integer relatedStoreNumber) {
        this.relatedStoreNumber = relatedStoreNumber;
    }

    /**
     * @hibernate.property
     * 		column = "cap_id"
     * @return
     */
	public Integer getCapId() {
		return capId;
	}

	public void setCapId(Integer capId) {
		this.capId = capId;
	}
    

}
