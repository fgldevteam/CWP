package com.fgl.cwp.presentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Cap;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.CapService;
import com.fgl.cwp.persistence.services.ProductService;
import com.fgl.cwp.persistence.services.UserService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;


/**
 * @author Jessica Wong
 */
public class ProductSearchBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;
    
    private static final Log log = LogFactory.getLog(ProductSearchBean.class);
    
    //for HTMLTable
    private String action;
    private List classOptions;
    
    //Hierarchy search Options for dropdowns
    private List departmentOptions;
    private int level;
    
    //incoming parameters: corresponding to hierarchy (dropdowns)
    private int[] parentSelection = {0, 0, 0, 0};    //dept, subdept, class, subclass, brand
    private String styleDescription;
    private String styleNumber;
    private String brand;
    
    private List styleResults;
    private List subClassOptions;
    private List subDeptOptions;
    private String upc;   
    
    //incoming parameters for product descriptions
    private String vpn;
    private String wildCard;
    private List <Cap> caps;
    private Integer capSelection;
    
    protected void logParams() {
        log.debug("upc: " + upc);
        log.debug("wild card: " + wildCard);
        log.debug("vpn: " + vpn);
        log.debug("style description: " + styleDescription);
        log.debug("style number: " + styleNumber);
        
        log.debug("dept: " + parentSelection[0]);
        log.debug("sub dept: " + parentSelection[1]);
        log.debug("class: " + parentSelection[2]);
        log.debug("sub class: " + parentSelection[3]);
        log.debug("brand: " + brand);
    }
    
    /**
     * Search for products matching the user search criteria
     * @return "SUCCESS" will return control to the jsp with a table of the search results
     */
    public String getProductSearchResults() {
        
        ActionContext context = ActionContext.getActionContext();
        logParams();

        if (validateProductSearchResults()) {
            //Get the results based on the product search criteria
            try {
                styleResults = ProductService.getInstance().getSearchResults(this);
                log.debug("Number of Results returned from getProductSearchResults: " + styleResults.size());
            } catch (Exception e) {
                log.error("Error getting the product search results, Cause: " + e);
                e.printStackTrace();
                context.addError("error", "upclookup.error.loading.productsearch");
            }
            
            //Clear other selections on jsp so that it is clear what the search was for on upc:
            if (upc != null && !upc.trim().equals("")) {
                resetDescriptions();
                wildCard = null;
            } else {
                //Clear other selections on jsp so that it is clear what the search was for on wildcard:
                if (wildCard != null && wildCard.trim().length() > 0) {
                    resetDescriptions();
                    upc = null;
                }
            }
        
            this.setAction("search");
            return Constants.SUCCESS;
        }
        return Constants.FAILURE;
    }
    
    
    protected boolean validateProductSearchResults() {
        boolean success = true;
        
        if (parentSelection[0] <= 0
                && parentSelection[1] <= 0
                && parentSelection[2] <= 0
                && parentSelection[3] <= 0
                && (styleDescription == null || styleDescription.equals(""))
                && (styleNumber == null || styleNumber.equals(""))
                && (brand == null || brand.equals(""))
                && (upc == null || upc.equals(""))
                && (vpn == null || vpn.equals(""))
                && (wildCard == null || wildCard.equals(""))) {
            ActionContext.getActionContext().addError("error", "product.search.error.criteriaRequired");
            success = false;
        }
        
        if (upc != null && !upc.equals("")) {
            // Make sure upc is numeric
            if (!upc.trim().matches("(\\d)+")) {
                ActionContext.getActionContext().addError("error", "product.search.error.upc.numeric");
                success = false;
            }
        }

        return success;
    }
    
    /**
     * @return
     */
    public String loadHierarchySearch() {
        log.debug("\nIn loadHierarchySearch...level= " + level + "   parentSelected= " + parentSelection[0]);
        ActionContext context = ActionContext.getActionContext();
        User user = (User)context.getSessionAttributes().get(Constants.USER);
       
        try {
        	
        	if (user != null){
        		if (caps == null) {
        			// load the user store list
                	UserService.getInstance().loadUsersStores(user);
                	caps = getCapListFromStoreList(user.getStores());
        		}
            } else {
            	// must be logged in as a store
            	capSelection = ((Store)context.getSessionAttributes().get(Constants.STORE)).getCapId();
            }
        	
            if (level == 0) {
                departmentOptions = ProductService.getInstance().getDepartments(null);
                subDeptOptions = null;
                classOptions = null;
                subClassOptions = null;
            }
            if (level == 1) {
                subDeptOptions = ProductService.getInstance().getSubDepartments(new Integer(parentSelection[0]));
                classOptions = null;
                subClassOptions = null;
            }
            if (level == 2) {
                classOptions = ProductService.getInstance().getClasses(new Integer(parentSelection[1]));
                subClassOptions = null;
            }
            if (level == 3) {
                subClassOptions = ProductService.getInstance().getSubClasses(new Integer(parentSelection[2]));
            }

        } catch (Exception e) {
            log.error("Error loading the HierarchySearch", e);
            context.addError("error", "upclookup.error.loading.hierarchysearch");
        }
        
        //reset selected hierarchies
        resetSelected();
        
        upc = null;
        wildCard = null;
        styleResults = null;
        
        this.setAction(null);
        
        return Constants.SUCCESS;
    }
    
    /**
     * @return
     */
    public String resetAll(){
        resetDescriptions();
        
        upc = null;
        wildCard = null;
        styleResults = null;
        capSelection = null;
        action = null;
        
        return Constants.SUCCESS;
    }
    /**
     * 
     */
    public void resetDescriptions() {
        subDeptOptions = null;
        classOptions = null;
        subClassOptions = null;
        parentSelection = new int[]{0, 0, 0, 0};
        vpn = null;
        styleDescription = null;
        styleNumber = null;
        brand = null;
    }
    
    /**
     * 
     */
    public void resetSelected(){
        int cnt = parentSelection.length - (level > 0? level:1);
        while (cnt >= level) {
            parentSelection[cnt] = 0;
            cnt = cnt - 1;
        }
    }
    
    /**
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }
    
    /**
     * @return
     */
    public String getAction() {
        return action;
    }
    
    /**
     * @return
     */
    public List getClassOptions() {
        return classOptions;
    }
    
    /**
     * @return
     */
    public List getDepartmentOptions() {
        return departmentOptions;
    }
    
    /**
     * @return
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * @return
     */
    public int[] getParentSelection() {
        return parentSelection;
    }
    
    /**
     * @return
     */
    public String getStyleDescription() {
        return styleDescription;
    }
    
    /**
     * @return
     */
    public String getStyleNumber() {
        return styleNumber;
    }
    
    /**
     * @return
     */
    public List getStyleResults() {
        return styleResults;
    }
    
    /**
     * @return
     */
    public List getSubClassOptions() {
        return subClassOptions;
    }
    
    /**
     * @return
     */
    public List getSubDeptOptions() {
        return subDeptOptions;
    }
    
    /**
     * @return
     */
    public String getUpc() {
        return upc;
    }
        
    /**
     * @return
     */
    public String getVpn() {
        return vpn;
    }
    
    /**
     * @return
     */
    public String getWildCard() {
        return wildCard;
    }
    
    /**
     * @param classOptions
     */
    public void setClassOptions(List classOptions) {
        this.classOptions = classOptions;
    }
    
    /**
     * @param departmentOptions
     */
    public void setDepartmentOptions(List departmentOptions) {
        this.departmentOptions = departmentOptions;
    }
    
    /**
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * @param parentSelection
     */
    public void setParentSelection(int[] parentSelection) {
        this.parentSelection = parentSelection;
    }
    
    /**
     * @param styleDescription
     */
    public void setStyleDescription(String styleDescription) {
        this.styleDescription = styleDescription;
    }
    
    /**
     * @param styleNumber
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }
    
    /**
     * @param styleResults
     */
    public void setStyleResults(List styleResults) {
        this.styleResults = styleResults;
    }
    
    /**
     * @param subClassOptions
     */
    public void setSubClassOptions(List subClassOptions) {
        this.subClassOptions = subClassOptions;
    }
    
    /**
     * @param subDeptOptions
     */
    public void setSubDeptOptions(List subDeptOptions) {
        this.subDeptOptions = subDeptOptions;
    }
    
    /**
     * @param upc
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }
    
    /**
     * @param vpn
     */
    public void setVpn(String vpn) {
        this.vpn = vpn;
    }
    
    /**
     * @param wildCard
     */
    public void setWildCard(String wildCard) {
        this.wildCard = wildCard;
    }

    /**
     * @return Returns the brand.
     */
    public String getBrand() {
        return brand;
    }
    
    public List<Cap> getCaps() {
		return caps;
	}

	public void setCaps(List<Cap> caps) {
		this.caps = caps;
	}
	
	public Integer getCapSelection() {
		return capSelection;
	}

	public void setCapSelection(Integer capSelection) {
		this.capSelection = capSelection;
	}
    

    /**
     * @param brand The brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    private List<Cap> getCapListFromStoreList(Set<Store> stores) throws Exception{
    	Set<Integer> capIds = new HashSet<Integer>();
    	for (Store store : stores) {
    		capIds.add(store.getCapId());
    	}
    	return CapService.getInstance().getCapsById(capIds);
    }

}
