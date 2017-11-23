/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Apr 7, 2005
 */
package com.fgl.cwp.persistence.services;

/**
 * @author bting
 */
public class SkuInventoryResult {
    
    private int storeNumber;
    private String name;
    private String city;
    private String areaCode;
    private String phoneNumber;
    private String colour;
    private String size;
    private int quantity;
    private int quantityInTransit;
    
    /**
     * 
     * @param storeNumber
     * @param name
     * @param city
     * @param areaCode
     * @param phoneNumber
     * @param colour
     * @param size
     * @param quantity
     * @param quantityInTransit
     */
    public SkuInventoryResult(int storeNumber,
            String name,
            String city,
            String areaCode,
            String phoneNumber,
            String colour,
            String size,
            int quantity,
            int quantityInTransit) {
        this.storeNumber = storeNumber;
        this.name = name;
        this.city = city;
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
        this.colour = colour;
        this.size = size;
        this.quantity = quantity;
        this.quantityInTransit = quantityInTransit;
    }

    /**
     * @return Returns the areaCode.
     */
    public String getAreaCode() {
        return areaCode;
    }
    

    /**
     * @return Returns the city.
     */
    public String getCity() {
        return city;
    }
    

    /**
     * @return Returns the colour.
     */
    public String getColour() {
        return colour;
    }
    

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    

    /**
     * @return Returns the storeNumber.
     */
    public int getStoreNumber() {
        return storeNumber;
    }
    

    /**
     * @return Returns the phoneNumber.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    

    /**
     * @return Returns the quantity.
     */
    public int getQuantity() {
        return quantity;
    }
    

    /**
     * @return Returns the quantityInTransit.
     */
    public int getQuantityInTransit() {
        return quantityInTransit;
    }
    

    /**
     * @return Returns the size.
     */
    public String getSize() {
        return size;
    }
    
         
}
