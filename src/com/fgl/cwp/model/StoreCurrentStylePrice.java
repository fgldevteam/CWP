package com.fgl.cwp.model;
/**
 * @author Graeme Delve
 * 
 * @hibernate.class
 *      table = "item_current_prices"
 *      proxy = "com.fgl.cwp.model.StoreCurrentStylePrice"
 *      
 * @hibernate.query 
 *      name = "getStorePriceForStyle"
 *      query = "SELECT		price
 *               FROM       StoreCurrentStylePrice price
 *               WHERE      price.id.styleId in (:styleIds) 
 *               AND        (price.id.storeNumber = :storeNumber OR price.id.storeNumber IS NULL)
 *               AND		price.id.capId = :capId
 *               ORDER BY 	price.id.styleId asc"
 */
public class StoreCurrentStylePrice {
	
	private StyleCurrentPriceKey id;
	private Double currentPrice;
	
	/**
     * @return the id
     * 
     * @hibernate.id
     */
	public StyleCurrentPriceKey getId() {
		return id;
	}
	public void setId(StyleCurrentPriceKey id) {
		this.id = id;
	}
	
	/**
	 * @return the current price for the style at the store
	 * @hibernate.property
	 * 	column = "current_price"
	 */
	public Double getCurrentPrice() {
		return currentPrice;
	}
	
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
}
