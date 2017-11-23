package com.fgl.cwp.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "inventory_store_style"
 *      proxy = "com.fgl.cwp.model.StyleInventory"
 *  
 * @hibernate.query 
 *      name = "getProductInventory"
 *      query = "SELECT               inv
 *               FROM                 StyleInventory inv
 *               INNER JOIN FETCH     inv.id.store store
 *               WHERE                store.region = :region
 *               AND				  store.banner in (:bannerList)
 *               AND                  inv.id.style.id = :styleID
 *               AND                  (inv.quantity > 0 OR inv.quantityInTransit > 0)
 *               ORDER BY             store.number asc"
 *         
 * @hibernate.query 
 *      name = "getProductInventoryForCMSABBC"
 *      query = "SELECT               inv
 *               FROM                 StyleInventory inv
 *               INNER JOIN FETCH     inv.id.store store
 *               WHERE				  store.banner != 'SM'
 *               AND                  (store.region = :region OR (store.banner = 'CMS' and store.region in ('AB', 'BC')))
 *               AND                  inv.id.style.id = :styleID
 *               AND                  (inv.quantity > 0 OR inv.quantityInTransit > 0)
 *               ORDER BY             store.number asc"
 * 
 */
public class StyleInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private StyleInventoryKey id;
    private int quantity = 0;
    private int quantityInTransit = 0;

     /**
     * @return
     * 
     * @hibernate.property
     *      column = "quantity"
     */
    public int getQuantity() {
          return quantity;
     }

     /**
     * @param quantity
     */
    public void setQuantity(int quantity) {
          this.quantity = quantity;
     }

    /**
     * @return Returns the quantityInTransit.
     * @hibernate.property
     *      column = "qty_in_transit"
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
    
    public boolean equals(Object obj) {
          if (!(obj instanceof StyleInventory)) return false;

          StyleInventory inv = (StyleInventory) obj;
          return new EqualsBuilder()
               .append(this.getId(), inv.getId())
               .isEquals();
     }

     public int hashCode() {
          return id.hashCode();
     }

    /**
     * @return Returns the id.
     * 
     * @hibernate.id
     */
    public StyleInventoryKey getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(StyleInventoryKey id) {
        this.id = id;
    }
}
