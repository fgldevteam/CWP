package com.fgl.cwp.presentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.Style;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.ProductService;
import com.fgl.cwp.persistence.services.StoreService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author Jessica Wong
 */
public class ProductDetailsBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(ProductDetailsBean.class);

    private int productID;
    private Style product;

    private String[] skuColourSizes;

    private Store store;
    private Integer storeNumber;
    private List storeList;
    
    private String buttonAction;
    
    // Inventory list for sku (by colour/size)
    private List skuInventoryList = null;
    
    public void reset() {
        if (skuColourSizes != null) {
            for (int i=0; i<skuColourSizes.length; ++i) {
                skuColourSizes[i] = null;
            }
        }
    }
    
    protected void resetProductSkuDetails() {
        product.setSkus(new HashSet());
    }
    
    /**
     * Load up user's store info.
     */
    public void initStore() {
        store = null;
        storeNumber = null;
        
        store = Utils.getStoreFromSession();
        if (store == null) {
            User user = Utils.getUserFromSession();
            
            if (user != null) {
                storeList = new ArrayList(user.getStores());
                store = (Store)storeList.get(0);
            }            
        }
        
        if (store != null) {
            storeNumber = new Integer(store.getNumber());
        }
    }

    /**
     * @return
     */
    public String loadProduct() {
        String success = Constants.SUCCESS;
        
        ActionContext context = ActionContext.getActionContext();

        log.debug("Product ID to retrieve: " + productID);

        skuColourSizes=null;

        try {
            initStore();
            
            product = ProductService.getInstance().getProduct(new Integer(productID));

            if (product == null) {
                context.addError("error", "product.details.error.loading.product");
                success = Constants.FAILURE;
            } else {
                //initialize the sku set
                product.setSkus(new HashSet());
                
                int totalColours = product.getColours().size();
                int totalSizes = product.getSizes().size();
                            
                skuColourSizes = new String[totalColours*totalSizes];
            }
        } catch (Exception e) {
            log.error("Error loading the Product", e);
            context.addError("error", "product.details.error.loading.product");
            success = Constants.FAILURE;
        }

        return success;
    }

    
    /**
     * Load up sku details based on color/size selection(s).
     * Used in displaying the upcs to user.
     * @throws Exception
     * @return
     */
    public String loadSkuDetails() throws Exception {
        String success = Constants.FAILURE;
        
        List<String> lookupSkuColourSizes = new ArrayList<String>();        
        ActionContext context = ActionContext.getActionContext();

        // reset sku set
        this.getProduct().setSkus(new HashSet());
        if (storeNumber != null){
        	List<Style> styleList = new ArrayList<Style>();
        	//clear prices with the exception of the return price as different 
        	//store number could have been selected
        	this.product.setChainPrice(null);
        	this.product.setCurrentPrice(null);
        	styleList.add(this.product);
        	
        	if(store.getNumber() != storeNumber){
        		store = StoreService.getInstance().getStoreById(storeNumber);
        	}
        	
        	ProductService.getInstance().setProductPricing(styleList, storeNumber, store.getCapId());
        }

        if (validateColourSize(lookupSkuColourSizes)) {    
            try {
                
                List colourSizeList = parseColourSizes(lookupSkuColourSizes);
                
                this.getProduct().setSkus(ProductService.getInstance()
                        .getSkusByColourAndSize(this.getProduct().getId(), colourSizeList));
                                
                
                success = Constants.SUCCESS;
                
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error loading the Sku", e);
                context.addError("error", "product.details.error.loading.sku");
                success = Constants.FAILURE;
            }
        }
        return success;
    }

    /**
     * Load up inventory for skus based on colour/size selections.
     * @return
     */
    public String loadSkuInventory() {
        String success = Constants.FAILURE;
        
        List<String> lookupSkuColourSizes = new ArrayList<String>();
        ActionContext context = ActionContext.getActionContext();

        // reset sku details
        resetProductSkuDetails();
        
        // reset sku set
        skuInventoryList = null;
        
        try {
            if (validateColourSize(lookupSkuColourSizes)) {
                
                List colourSizeList = parseColourSizes(lookupSkuColourSizes);
                
                // Determine which store to search inventory against
                Store lookupStore = store;
                if (storeNumber.intValue() != store.getNumber()) {
                    lookupStore = StoreService.getInstance().getStoreById(storeNumber);
                }
                
                skuInventoryList = ProductService.getInstance()
                        .getSkuInvByColourAndSize(this.getProduct().getId(),
                                colourSizeList,
                                lookupStore);
                
                if (skuInventoryList.isEmpty()) {
                    context.addMessage("status", "product.details.error.sku.noinventory");
                    success = Constants.FAILURE;
                } else {
                    success = Constants.SUCCESS;
                }
            }
            
        } catch (Exception e) {
            log.error("Error loading the Sku inventory", e);
            context.addError("error", "product.details.error.loading.sku");
            success = Constants.FAILURE;
        }
        return success;
    }
    
    /**
     * Load up all inventory based on style information.
     * @return
     * @throws Exception
     */
    public String loadStyleInventory() throws Exception {
        String success = Constants.SUCCESS;

        // reset sku details
        resetProductSkuDetails();
        
        // Determine which store to search inventory against
        Store lookupStore = store;
        if (storeNumber.intValue() != store.getNumber()) {
            lookupStore = StoreService.getInstance().getStoreById(storeNumber);
        }

        ProductService.getInstance().loadStyleInventory(getProduct(), lookupStore);
        
        if (getProduct().getInventories() == null
                || getProduct().getInventories().isEmpty()) {
            ActionContext.getActionContext().addMessage("status", "product.details.error.style.noinventory");
            success = Constants.FAILURE;
        }
        
        return success;
    }
    
    
    /**
     * Ensure that the user selects a colour/size combination
     * @param   lookupSkuColourSizes
     * @return
     */
    protected boolean validateColourSize(List<String> lookupSkuColourSizes) {

        boolean success = true;
        
        if (skuColourSizes!=null) {
            for (int i=0; i<skuColourSizes.length; ++i) {
                if (StringUtils.isNotEmpty(skuColourSizes[i])) {
                    lookupSkuColourSizes.add(skuColourSizes[i]);
                }
            }
            if (lookupSkuColourSizes.isEmpty()) {
                success = false;
            }
        }
        if (!success) {
            ActionContext.getActionContext().addError("error", "product.details.validate.missingcolourorsize");
        }
        return success;
    }

    /**
     * Parse the colour/size selections made by the user.
     * @param lookupSkuColourSizes
     * @return
     */
    protected List parseColourSizes(List lookupSkuColourSizes) {
        List<ColourSize> selections = new ArrayList<ColourSize>();
        for (Iterator iter = lookupSkuColourSizes.iterator(); iter.hasNext();) {
            String skuColourSize = (String)iter.next();
            Integer skuColour = null;
            Integer skuSize = null;
            
            // Each value passed from html form looks like ${colour}:${size}
            int separator = skuColourSize.indexOf(":");
            if (skuColourSize != null && separator!=-1) {       
                skuColour = new Integer(skuColourSize.substring(0,separator));
                skuSize = new Integer(skuColourSize.substring(separator+1,skuColourSize.length()));
            }
            
            selections.add(new ColourSize(skuColour, skuSize));
            
            log.debug("SIZE: " + skuSize + "    COLOUR: " + skuColour);
        }
        
        return selections;
    }

    /**
     * Inner class used for one colour/size selection.
     * @author bting
     */
    public class ColourSize {
        private Integer colour;
        private Integer size;
        /**
         * 
         * @param colour
         * @param size
         */
        public ColourSize(Integer colour, Integer size) {
            this.colour = colour;
            this.size = size;
        }
        /**
         * @return Returns the colour.
         */
        public Integer getColour() {
            return colour;
        }
        
        /**
         * @param colour The colour to set.
         */
        public void setColour(Integer colour) {
            this.colour = colour;
        }
        
        /**
         * @return Returns the size.
         */
        public Integer getSize() {
            return size;
        }
        
        /**
         * @param size The size to set.
         */
        public void setSize(Integer size) {
            this.size = size;
        }
        
        
    }
    
    /**
     * @return Returns the skuInventoryList.
     */
    public List getSkuInventoryList() {
        return skuInventoryList;
    }
    

    /**
     * @return
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @param productID
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * @return
     */
    public Style getProduct() {
        return product;
    }

    /**
     * @param product
     */
    public void setProduct(Style product) {
        this.product = product;
    }

    /**
     * @return Returns the skuColourSizes.
     */
    public String[] getSkuColourSizes() {
        return skuColourSizes;
    }
    
    /**
     * @param skuColourSizes The skuColourSizes to set.
     */
    public void setSkuColourSizes(String[] skuColourSizes) {
        this.skuColourSizes = skuColourSizes;
    }
    /**
     * @return Returns the storeNumber.
     */
    public Integer getStoreNumber() {
        return storeNumber;
    }
    /**
     * @param storeNumber The storeNumber to set.
     */
    public void setStoreNumber(Integer storeNumber) {
        this.storeNumber = storeNumber;
    }
    /**
     * @return Returns the store.
     */
    public Store getStore() {
        return store;
    }
    /**
     * @return Returns the storeList.
     */
    public List getStoreList() {
        return storeList;
    }

    /**
     * @return Returns the action.
     */
    public String getButtonAction() {
        return buttonAction;
    }
    

    /**
     * @param action The action to set.
     */
    public void setButtonAction(String buttonAction) {
        this.buttonAction = buttonAction;
    }
    
    
}