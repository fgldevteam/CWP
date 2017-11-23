package com.fgl.cwp.model;

/**
 * @author Graeme Delve
 * 
 * @hibernate.class
 *      table = "style_cap_cost"
 *      proxy = "com.fgl.cwp.model.StyleCostPrice"
 *      
 * @hibernate.query 
 *      name = "getCostPriceForStyle"
 *      query = "SELECT		price
 *               FROM       StyleCostPrice price
 *               WHERE      price.id.styleId in (:styleIds) 
 *               AND		price.id.capId = :capId
 *               ORDER BY 	price.id.styleId asc"
 */

public class StyleCostPrice {
	private StylePriceKey id;
	private Double costPrice;
	
	/**
     * @return the id
     * 
     * @hibernate.id
     */
	public StylePriceKey getId() {
		return id;
	}
	public void setId(StylePriceKey id) {
		this.id = id;
	}
	
	/**
	 * @return the cost price for the style at cap level
	 * @hibernate.property
	 * 	column = "cost"
	 */
	public Double getCostPrice() {
		return costPrice;
	}
	
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
}
