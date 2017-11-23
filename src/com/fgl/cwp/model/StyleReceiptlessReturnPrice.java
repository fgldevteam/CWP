package com.fgl.cwp.model;

/**
 * @author Graeme Delve
 * 
 * @hibernate.class
 *      table = "style_receiptless_return_price"
 *      proxy = "com.fgl.cwp.model.StyleReceiptlessReturnPrice"
 *      
 * @hibernate.query 
 *      name = "getReceiptlessReturnPriceForStyle"
 *      query = "SELECT		price
 *               FROM       StyleReceiptlessReturnPrice price
 *               WHERE      price.id.styleId in (:styleIds) 
 *               AND		price.id.capId = :capId
 *               ORDER BY 	price.id.styleId asc"
 */

public class StyleReceiptlessReturnPrice {
	private StylePriceKey id;
	private Double receiptlessReturnPrice;
	
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
	 * @return the receiptless price for the style at cap level
	 * @hibernate.property
	 * 	column = "return_price"
	 */
	public Double getReceiptlessReturnPrice() {
		return receiptlessReturnPrice;
	}
	
	public void setReceiptlessReturnPrice(Double receiptlessReturnPrice) {
		this.receiptlessReturnPrice = receiptlessReturnPrice;
	}
}
