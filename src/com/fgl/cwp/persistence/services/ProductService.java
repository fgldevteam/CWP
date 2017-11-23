package com.fgl.cwp.persistence.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.ConceptShop;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.StoreCurrentStylePrice;
import com.fgl.cwp.model.Style;
import com.fgl.cwp.model.StyleCostPrice;
import com.fgl.cwp.model.StyleReceiptlessReturnPrice;
import com.fgl.cwp.persistence.SessionManager;
import com.fgl.cwp.presentation.ProductDetailsBean;
import com.fgl.cwp.presentation.ProductSearchBean;
import com.fgl.cwp.struts.ActionContext;

/**
 * @author Jessica Wong
 */
public class ProductService {

    private static final ProductService instance = new ProductService();
    private static final Log log = LogFactory.getLog(ProductService.class);

    private ProductService() {
        // empty
    }
    
    /**
     * @return the singleton instance of the ProductService
     */
    public static ProductService getInstance() {
        return instance;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Style getProduct(Integer id) throws Exception {

        Style style = null;
        Session session = null;

        try {
            session = SessionManager.getSessionFactory().openSession();
            style = (Style) session.getNamedQuery("getProductById")
                    .setParameter("id", id)
                    .uniqueResult();
            
            if (style != null) {            
                //Set colours and sizes for the style
                style.setColours(session.getNamedQuery("getDistinctColoursForStyle")
                        .setParameter("styleID", style.getId())
                        .list());
                style.setSizes(session.getNamedQuery("getDistinctSizesForStyle")
                        .setParameter("styleID", style.getId())
                        .list());
                style=populateCSID(session, style);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return style;
    }

    /**
     * @param division
     * @return
     * @throws Exception
     */
    public List getDepartments(Integer division) throws Exception {

        // parameter division is never read
        List depts = new ArrayList();
        Session session = SessionManager.getSessionFactory().openSession();
        try {
            depts = new ArrayList();
            depts = session.getNamedQuery("getDepartments").list();
        } finally {
            session.close();
        }
        return depts;

    }

    /**
     * @param parentDepartment
     * @return
     * @throws Exception
     */
    public List getSubDepartments(Integer parentDepartment) throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        List subDepts = new ArrayList();
        try {
            Query qry = session.getNamedQuery("getSubDepartmentsByDepartment");
            if (parentDepartment != null)
                qry.setParameter("dept", parentDepartment);
            subDepts = qry.list();
        } finally {
            session.close();
        }
        return subDepts;
    }

    /**
     * @param parentSubDepartment
     * @return
     * @throws Exception
     */
    public List getClasses(Integer parentSubDepartment) throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        List classes = new ArrayList();
        try {
            Query qry = session.getNamedQuery("getClassesBySubDepartment");
            if (parentSubDepartment != null)
                qry.setParameter("subDept", parentSubDepartment);
            classes = qry.list();
        } finally {
            session.close();
        }
        return classes;
    }

    /**
     * @param parentClass
     * @return
     * @throws Exception
     */
    public List getSubClasses(Integer parentClass) throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        List subClasses = new ArrayList();
        try {
            Query qry = session.getNamedQuery("getSubClassesByClass");
            if (parentClass != null)
                qry.setParameter("class", parentClass);
            subClasses = qry.list();
        } finally {
            session.close();
        }
        return subClasses;
    }
    
    /**
     * Wrapper method to call appropriate search method based on criteria.
     * @param searchBean
     * @return
     * @throws Exception
     */
    public List getSearchResults(ProductSearchBean searchBean) throws Exception {
    	// get the style list by the appropriate means
    	List <Style> styleList  = new ArrayList<Style>();
        if (searchBean.getUpc() != null
                && searchBean.getUpc().trim().length() > 0) {
            //return getUPCSearch(searchBean.getUpc().trim());
        	styleList = getUPCSearch(searchBean.getUpc().trim());
        } else if (searchBean.getWildCard() != null
                && searchBean.getWildCard().trim().length() > 0) {
            //return getWildCardSearch(searchBean.getWildCard().trim());
        	styleList = getWildCardSearch(searchBean.getWildCard().trim());
        } else {
        //return getSearchResultsByHierarchy(searchBean);
        	styleList = getSearchResultsByHierarchy(searchBean);
        }
        
        // now get the pricing data
        Store store = (Store)ActionContext.getActionContext().getSessionAttributes().get(Constants.STORE);
        if (store != null) {
        	setProductPricing(styleList, store.getNumber(), store.getCapId());
        } else {
        	setProductPricing(styleList, null, searchBean.getCapSelection());
        }
       
        return styleList;
    }

    /**
     * 
     * @param searchBean
     * @return
     * @throws Exception
     */
    protected List getSearchResultsByHierarchy(ProductSearchBean searchBean) throws Exception {
        List <Style>styles = new ArrayList<Style>();
        List <Style> styleList = new ArrayList<Style>();
        Session session = null;
    	
        try {
            int deptId = searchBean.getParentSelection()[0];
            int subDeptId = searchBean.getParentSelection()[1];
            int classId = searchBean.getParentSelection()[2];
            int subClassId = searchBean.getParentSelection()[3];
            String vpn = searchBean.getVpn().trim().replaceAll("'", "''");
            String styleDesc = searchBean.getStyleDescription().trim().replaceAll("'", "''");
            String styleNum = searchBean.getStyleNumber().trim().replaceAll("'", "''");
            String brand = searchBean.getBrand().trim().replaceAll("'", "''");

            StringBuffer query = new StringBuffer();
            query.append("select p from Style p");
            query.append(" join fetch p.hierarchy");
            query.append(" join p.brand b");
            query.append(" where");
            query.append(" p.id is not null");

            if (StringUtils.isNotEmpty(brand)) {
                query.append(" and b.name like '%" + brand + "%'");
            }
            if (StringUtils.isNotEmpty(vpn)) {
                query.append(" and p.vpn like '%" + vpn + "%'");
            }
            if (StringUtils.isNotEmpty(styleDesc)) {
                query.append(" and p.description like '%" + styleDesc + "%'");
            }
            if (StringUtils.isNotEmpty(styleNum)) {
                // search styleNum against both the pmm number or r12number
                query.append(" and (p.number = '" + styleNum + "'");
                query.append(" or p.r12Number = '" + styleNum + "')");
            }
            if (subClassId > 0) {
                query.append(" and p.hierarchy.subClassID = " + subClassId);
            }
            if (classId > 0) {
                query.append(" and p.hierarchy.classID = " + classId);
            }
            if (subDeptId > 0) {
                query.append(" and p.hierarchy.subDeptID = " + subDeptId);
            }
            if (deptId > 0) {
                query.append(" and p.hierarchy.deptID = " + deptId);
            }

            session = SessionManager.getSessionFactory().openSession();
            styles = session.createQuery(query.toString())
                    .setMaxResults(Constants.MAX_RESULTS)
                    .list();
            
            if((null!= styles)&&(styles.size()>0)){            	
	            for(Style style:styles){
	            	styleList.add(populateCSID(session,style));
	            }
            }            
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return styleList;
    }
    
    /**
     * This method is used to populate the Concept Shop ID 
     * @param session Session 
     * @param style Style
     * @return Stye
     * @throws HibernateException
     */
    public Style populateCSID(Session session,Style style) throws HibernateException{
    	if(null!= style.getConceptShopID()){
        	String SQL_QUERY="from ConceptShop cs where cs.id="+style.getConceptShopID();
        	 List<ConceptShop> conceptShopList=session.find(SQL_QUERY);
        	 if(null != conceptShopList && conceptShopList.size()>0){
        		 style.setConceptShop(conceptShopList.get(0));	 
        	 }
        }
    	return style;
    }
        
    /**
     * @param wildCard
     * @return
     * @throws Exception
     */
    protected List getWildCardSearch(String wildCard) throws Exception {

        List <Style>styles = new ArrayList();
        List <Style> styleList = new ArrayList<Style>();
        String searchString;
        boolean and = false;
        Session session = SessionManager.getSessionFactory().openSession();

        StringBuffer qry = new StringBuffer();
        qry.append("SELECT  style FROM Style style");
        qry.append(" JOIN style.brand brand");
        qry.append(" JOIN FETCH style.hierarchy");
        qry.append(" WHERE ");

        wildCard = wildCard + " ";
        while (wildCard.length() > 1) {
            int index = wildCard.indexOf('\u0020'); //=space
            searchString = wildCard.substring(0, index);
            wildCard = wildCard.substring(index + 1, wildCard.length());

            String searchStringLike = "%" + searchString + "%";
            log.debug("Search String= " + searchString);
            if (and)
                qry.append(" AND ");
            qry.append(" (style.description like '" + searchStringLike + "' ");
            qry.append(" or style.number = '" + searchString + "' ");
            qry.append(" or style.r12Number = '" + searchString + "' ");
            qry.append(" or style.vpn = '" + searchString + "' ");
            qry.append(" or brand.name like '" + searchStringLike + "' ");
            qry.append(" or style.hierarchy.subClassDescription like '" + searchStringLike + "' ");
            qry.append(" or style.hierarchy.classDescription like '" + searchStringLike + "' ");
            qry.append(" or style.hierarchy.subDeptDescription like '" + searchStringLike + "' ");
            qry.append(" or style.hierarchy.deptDescription like '" + searchStringLike + "' )");
            log.debug("Query= " + qry.toString());
            and = true;
        }

        try {
            Query query = session.createQuery(qry.toString());
            query.setMaxResults(Constants.MAX_RESULTS);
            styles = query.list();
            
            if((null!= styles)&&(styles.size()>0)){            	
	            for(Style style:styles){
	            	styleList.add(populateCSID(session,style));
	            }
            }
        } finally {
            session.close();
        }

        return styleList;
    }

    /**
     * @param upc
     * @return
     * @throws Exception
     */
    protected List getUPCSearch(String upc) throws Exception {
        log.debug("getUPCSearch(): start");
        Session session = null;
        List <Style> styles = new ArrayList();
        List <Style> styleList = new ArrayList();

        try {
            Long upcNumber = new Long(Long.parseLong(upc));
            session = SessionManager.getSessionFactory().openSession();
            styles = session.getNamedQuery("getProductByUPC")
                    .setParameter("upc", upcNumber)
                    .setMaxResults(Constants.MAX_RESULTS)
                    .list();
            if((null!= styles)&&(styles.size()>0)){            	
	            for(Style style:styles){
	            	styleList.add(populateCSID(session,style));
	            }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return styleList;
    }

    /**
     * Get style inventory.
     * @param style
     * @param store 
     * @throws Exception
     */
    public void loadStyleInventory(Style style, Store store) throws Exception {
        Collection inv = null;
        Session session = null;
        
        if (store != null) {

            try {
                session = SessionManager.getSessionFactory().openSession();
                
                String region = store.getRegion();
                String banner = store.getBanner();
                Object [] bannerList;
                
                if ("SM".equalsIgnoreCase(banner)){
                	bannerList = new Object [] {"SM"};
                } else {
                	bannerList = new Object [] {"SC","HX","CMS"};
                }
                log.debug("store: " + store.getFullName());
                log.debug("region: " + region);
                log.debug("banner: " + banner);
                
                //CMS stores in AB or BC must see inventory for both regions
                if (banner.equals("CMS") && (region.equals("AB") || region.equals("BC"))) {
                    inv = session.getNamedQuery("getProductInventoryForCMSABBC")
                            .setParameter("region", region)
                            .setParameter("styleID", style.getId())
                            .list();
                } else {
                    inv = session.getNamedQuery("getProductInventory")
                            .setParameter("region", region)
                            .setParameterList("bannerList", bannerList)
                            .setParameter("styleID", style.getId())
                            .list();
                }
            } finally {
                if (session!=null) {
                    session.close();
                }
            }
        } else {
            //else the user belongs to a false store: and will not see inventory
            inv = new ArrayList();
        }
        
        // TODO: BYT - determine why we should wrap with a LinkedHashSet???
        style.setInventories(new LinkedHashSet(inv));
    }
    
    /**
     * @param styleID
     * @param colourSizeList
     * @return
     * @throws Exception
     */
    public Set getSkusByColourAndSize(Integer styleID, List colourSizeList) throws Exception {

        StringBuffer query = new StringBuffer();
        query.append("select sku from Sku sku");
        query.append(" join fetch sku.colour col");
        query.append(" join fetch sku.size siz");
        query.append(" join fetch sku.upcs");
        query.append(" where sku.style.id = " + styleID);
        
        if (colourSizeList != null) {
            if (!colourSizeList.isEmpty()) {
                query.append(" and (");
            }
            for (Iterator iter = colourSizeList.iterator(); iter.hasNext();) {
                
                // Add one colour/size selection
                ProductDetailsBean.ColourSize colourSize = (ProductDetailsBean.ColourSize)iter.next();
                query.append(" sku.colour.codiCode=" + colourSize.getColour());
                query.append(" and sku.size.codiCode=" + colourSize.getSize());
                
                
                if (iter.hasNext()) {
                    query.append(" or");
                } else {
                    query.append(" )");
                }
            }
        }
        

        Session session = null;
        Set skus = new HashSet();
        
        try {
            session = SessionManager.getSessionFactory().openSession();
            
            List skuList = session.createQuery(query.toString()).list();
            
            skus.addAll(skuList);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return skus;
    }
    
    
    /**
     * Get 
     * @param styleID
     * @param colourSizeList
     * @param store
     * @return
     * @throws Exception
     */
    public List getSkuInvByColourAndSize(Integer styleID, List colourSizeList, Store store) throws Exception {

        List skuList = null;
        Session session = null;
        
        try {
                        
            String region = store.getRegion();
            String banner = store.getBanner().toUpperCase();            
            
            log.debug("store: " + store.getFullName());
            log.debug("region: " + region);
            log.debug("banner: " + banner);

            // Create query
            StringBuffer query = new StringBuffer();
            query.append("select new com.fgl.cwp.persistence.services.SkuInventoryResult(");
            query.append(" store.number,");
            query.append(" store.name,");
            query.append(" store.city,");
            query.append(" store.areaCode,");
            query.append(" store.phoneNumber,");
            query.append(" sku.colour.codiDescription,");
            query.append(" sku.size.codiDescription,");
            query.append(" inv.quantity,");
            query.append(" inv.quantityInTransit)");
            query.append(" from Sku sku");
            query.append(" join sku.colour col");
            query.append(" join sku.size siz");
            query.append(" join sku.inventories inv");
            query.append(" join inv.id.store store");
            query.append(" where sku.style.id = " + styleID);
            query.append(" and (inv.quantity > 0 or inv.quantityInTransit > 0)");
            
            if (colourSizeList != null) {
                if (!colourSizeList.isEmpty()) {
                    query.append(" and (");
                }
                for (Iterator iter = colourSizeList.iterator(); iter.hasNext();) {
                    
                    // Add one colour/size selection
                    ProductDetailsBean.ColourSize colourSize = (ProductDetailsBean.ColourSize)iter.next();
                    query.append(" sku.colour.codiCode=" + colourSize.getColour());
                    query.append(" and sku.size.codiCode=" + colourSize.getSize());
                    
                    
                    if (iter.hasNext()) {
                        query.append(" or");
                    } else {
                        query.append(" )");
                    }
                }
            }
            if ("SM".equals(banner)){
            	query.append(" and store.banner = '" + banner + "'");
            	query.append(" and store.region = '" + region + "'");
            } else {
            	query.append("and store.banner != 'SM'");
            	// CMS stores in AB or BC must see inventory for both regions
                if ("CMS".equals(banner) && (region.equals("AB") || region.equals("BC"))) {
                    query.append(" and (store.region = '" + region + "'");
                    query.append(" or (store.banner = 'CMS' and store.region in ('AB', 'BC')))");
                    
                } else {
                    query.append(" and store.region = '" + region + "'");
                } 
            }
                 
            query.append(" order by store.number asc, col.codiCode asc, siz.codiCode asc");


            // Execute query
            session = SessionManager.getSessionFactory().openSession();
            skuList = session.createQuery(query.toString()).list();


            // Debugging information
            if (skuList != null && log.isDebugEnabled()) {
                log.debug("skuList.size()="+skuList.size());
                for (Iterator iter=skuList.iterator(); iter.hasNext();) {
                    SkuInventoryResult sku = (SkuInventoryResult)iter.next();
                    log.debug("returning: store num: " + sku.getStoreNumber() + " name: " + sku.getName() + " colour: " + sku.getColour() + " size: " + sku.getSize()); 
                }
            }
                        
        } finally {
            if (session != null) {
                session.close();
            }
        }
        
        if (skuList == null) {
            skuList = new ArrayList();
        }
        return skuList;
    }
    
    /**
     * Retrieves the chain and (if exists) store level price for a given list of styles
     * @param styleIds
     * @param storeNumber
     * @param capId
     * @return
     * @throws Exception
     */
    private List<StoreCurrentStylePrice> getCurrentProductPricing(List<Integer> styleIds, Integer storeNumber, Integer capId) throws Exception {
    	List <StoreCurrentStylePrice> prices = new ArrayList<StoreCurrentStylePrice>();
        if (storeNumber != null && capId != null && styleIds != null && styleIds.size() > 0){
        	Session session = null;
        	
    		try{
        		session = SessionManager.getSessionFactory().openSession();
        		Query query = session.getNamedQuery("getStorePriceForStyle");
        		query.setParameterList("styleIds", styleIds);
        		query.setParameter("storeNumber", storeNumber);
        		query.setParameter("capId", capId);
        		prices = query.list();
    		}
    		finally {
    			session.close();
    		}
        }
    	return prices;
    }
    
    /**
     * Assigns various prices (current, return) to the list of Styles
     * @param styleList The Styles
     * @param currentPriceList The current selling prices
     * @param receiptlessReturnPrices The receiptless return prices
     */
    private void setPricesForStyles(List<Style> styleList, List<StoreCurrentStylePrice> currentPriceList, 
    								Map<Integer,Double> receiptlessReturnPrices,
    								Map<Integer,Double> costPrices) {
    	if (styleList != null && styleList.size() > 0) {
    		if(currentPriceList != null) {
    			Integer priceStyleId = null;
		    	for (StoreCurrentStylePrice price : currentPriceList) {
		    		priceStyleId = price.getId().getStyleId();
		    		for (Style style : styleList) {
		    			if (priceStyleId.equals(style.getId())) {
		    				if (price.getId().getStoreNumber() != null) {
		    					style.setCurrentPrice(price.getCurrentPrice());
		    				} else {
		    					style.setChainPrice(price.getCurrentPrice());
		    				}
		    				//assign the receiptless return and cost price while looping		    				
		    				style.setReturnPrice(receiptlessReturnPrices.get(priceStyleId));
		    				style.setCapCostPrice(costPrices.get(priceStyleId));
		    				break;
		    			}
		    		}
		    	}
    		} else if (receiptlessReturnPrices != null && costPrices != null) {
    			for (Style style : styleList) {
    				style.setReturnPrice(receiptlessReturnPrices.get(style.getId()));
    				style.setCapCostPrice(costPrices.get(style.getId()));
    			}
    		}
    	}
    }
    
    /**
     * Retrieves receiptless return prices for a given list of styles
     * @param styleIds The styles to retrieve the return price for
     * @param capId The CAP ID to be used to filter the return prices query
     * @return Map<Integer,Double> Consisting of the Style ID and the associated return price
     * @throws Exception
     */

    private Map<Integer,Double> getReceiptlessReturnPricing(List<Integer> styleIds, Integer capId) throws Exception {
    	List <StyleReceiptlessReturnPrice> prices = new ArrayList<StyleReceiptlessReturnPrice>(); 
    	Map<Integer,Double> priceMap = null;
        if (capId != null && styleIds != null && styleIds.size() > 0){
        	Session session = null;
        	
    		try {
        		session = SessionManager.getSessionFactory().openSession();
        		Query query = session.getNamedQuery("getReceiptlessReturnPriceForStyle");
        		query.setParameterList("styleIds", styleIds);
        		query.setParameter("capId", capId);
        		prices = query.list();
    		}
    		finally {
    			session.close();
    		}
        
        	if (prices != null) {
        		priceMap = new HashMap<Integer,Double>();
        		for (StyleReceiptlessReturnPrice price : prices){
        			priceMap.put(price.getId().getStyleId(), price.getReceiptlessReturnPrice());
        		}
        	}
        }
    	return priceMap;
    }
    
    /**
     * Retrieves cost prices for a given list of styles
     * @param styleIds The styles to retrieve the return price for
     * @param capId The CAP ID to be used to filter the return prices query
     * @return Map<Integer,Double> Consisting of the Style ID and the associated return price
     * @throws Exception
     */
    private Map<Integer,Double> getCostPricing(List<Integer> styleIds, Integer capId) throws Exception {
    	List <StyleCostPrice> prices = new ArrayList<StyleCostPrice>(); 
    	Map<Integer,Double> priceMap = null;
        if (capId != null && styleIds != null && styleIds.size() > 0){
        	Session session = null;
        	
    		try {
        		session = SessionManager.getSessionFactory().openSession();
        		Query query = session.getNamedQuery("getCostPriceForStyle");
        		query.setParameterList("styleIds", styleIds);
        		query.setParameter("capId", capId);
        		prices = query.list();
    		}
    		finally {
    			session.close();
    		}
        
        	if (prices != null) {
        		priceMap = new HashMap<Integer,Double>();
        		for (StyleCostPrice price : prices){
        			priceMap.put(price.getId().getStyleId(), price.getCostPrice());
        		}
        	}
        }
    	return priceMap;
    }
    
    /**
     * Gets and sets the various prices for a List of Styles
     * @param styleList
     * @param storeId
     * @param capId
     * @throws Exception
     */
    public void setProductPricing(List<Style> styleList, Integer storeId, Integer capId) throws Exception{
    	List <StoreCurrentStylePrice> currentStylePrices = null;
    	Map <Integer,Double> receiptlessReturnPrices = null;
    	Map <Integer,Double> costPrices = null;
    	List <Integer> styleIds = new ArrayList<Integer>();
		for(Style style : styleList){
			styleIds.add(style.getId());
		}
		
		if (capId != null){
			if (storeId != null) {
				currentStylePrices = getCurrentProductPricing(styleIds, storeId, capId);
			}
			receiptlessReturnPrices = getReceiptlessReturnPricing(styleIds, capId);
			costPrices = getCostPricing(styleIds, capId);
		}
		
		setPricesForStyles(styleList, currentStylePrices, receiptlessReturnPrices, costPrices);
    }
}