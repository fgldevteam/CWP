/*
 * Copyright Forzani Group Ltd. 2005
 *
 */
package com.fgl.cwp.presentation.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.ParameterizedActionForward;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Document;
import com.fgl.cwp.model.PricingIssue;
import com.fgl.cwp.model.Sku;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.Style;
import com.fgl.cwp.model.Upc;
import com.fgl.cwp.persistence.SessionManager;
import com.fgl.cwp.persistence.services.DocumentService;
import com.fgl.cwp.persistence.services.PostalService;
import com.fgl.cwp.struts.ActionConstants;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author mkuervers
 */
public class PricingIssuesBean extends BaseBean {
	
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(PricingIssuesBean.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static int ISP_ARRAY_LENGTH = 10;
    private static int ARRAY_LENGTH = 4;
    private static int DAY_MILLISECONDS = 86400000; // 1000 * 24 × 60 × 60 

    private String today;
    private Integer storeNum;
    private String submittedBy;
    private String[] deptList;
    private String[] ispDept;
    private String[] ispStyle;
    private String[] ispUpc;
    private String[] ispCurrentPrice;
    private String[] ispCorrectPrice;
    private String[] pnwDept;
    private String[] pnwStyle1;
    private String[] pnwStyle2;
    private String[] pnwStyle3;
    private String[] pnwStyle4;
    private String[] pnwStyle5;
    private String[] pnwCorrectPrice;
    private String[] isdDept;
    private String[] isdStyle;
    private String[] isdComment;
    private String[] upczeroDept;
    private String[] upczeroUpc;
    private String[] upcnsDept;
    private String[] upcnsUpc;
    private String[] itpDept;
    private String[] itpCurrentTag;
    private String[] itpCorrectTag;
    private String[] otherDept;
    private String[] otherComment;

    private int ispCount;
    private int pnwCount;
    private int isdCount;
    private int upczeroCount;
    private int upcnsCount;
    private int itpCount;
    private int otherCount;

    private int documentId = 0;
    private Document document;
    private String to;
    private String subject;
    private Map<String,String> upcStyleNumbers;

    /**
     * Reset all fields to appropriate defaults
     */
    public void reset() {
        log.debug("PricingIssuesBean(): reset()");
        
        today = dateFormat.format(new Date());
        Store store = Utils.getStoreFromSession();
        if (store!=null) {
            storeNum = new Integer(store.getNumber());
        }

        submittedBy = null;
        deptList = new String[]{"","Hardgood","Softgood","Footwear","Other"};
        ispDept = new String[ISP_ARRAY_LENGTH];
        ispStyle = new String[ISP_ARRAY_LENGTH];
        ispUpc = new String[ISP_ARRAY_LENGTH];
        ispCurrentPrice = new String[ISP_ARRAY_LENGTH];
        ispCorrectPrice = new String[ISP_ARRAY_LENGTH];
        pnwDept = new String[ARRAY_LENGTH];
        pnwStyle1 = new String[ARRAY_LENGTH];
        pnwStyle2 = new String[ARRAY_LENGTH];
        pnwStyle3 = new String[ARRAY_LENGTH];
        pnwStyle4 = new String[ARRAY_LENGTH];
        pnwStyle5 = new String[ARRAY_LENGTH];
        pnwCorrectPrice = new String[ARRAY_LENGTH];
        isdDept = new String[ARRAY_LENGTH];
        isdStyle = new String[ARRAY_LENGTH];
        isdComment = new String[ARRAY_LENGTH];
        upczeroDept = new String[ARRAY_LENGTH];
        upczeroUpc = new String[ARRAY_LENGTH];
        upcnsDept = new String[ARRAY_LENGTH];
        upcnsUpc = new String[ARRAY_LENGTH];
        itpDept = new String[ARRAY_LENGTH];
        itpCurrentTag = new String[ARRAY_LENGTH];
        itpCorrectTag = new String[ARRAY_LENGTH];
        otherDept = new String[ARRAY_LENGTH];
        otherComment = new String[ARRAY_LENGTH];

        ispCount = 0;
        pnwCount = 0;
        isdCount = 0;
        upczeroCount = 0;
        upcnsCount = 0;
        itpCount = 0;
        otherCount = 0;
        
        super.reset();
    }
    
    /**
     * Create a new form
     * @return
     * @throws Exception
     */
    public String create() throws Exception {
        log.debug("PricingIssuesBean(): create()");

        log.debug("Loading document.id: " + documentId);
        this.setDocument(DocumentService.getInstance().getDocumentById(documentId));
        
        // clean up
        documentId = 0;

        reset();
        return Constants.SUCCESS;
    }
    

    /**
     * Ensure all required form fields are filled out
     */
    public void validate() {
        log.debug("PricingIssuesBean(): validate()");
        ActionContext ctxt = ActionContext.getActionContext();
        
        if (StringUtils.isEmpty(submittedBy)) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.submittedby.required");
        }

        int count = 0;
        this.upcStyleNumbers = new HashMap<String, String>();
        
        for (int i=0; i<ISP_ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(ispDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(ispStyle[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(ispUpc[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(ispCurrentPrice[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(ispCorrectPrice[i]))) {
            	Style style = null;
            	Upc upc = null;
                ++ispCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(ispDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
	            // Check for one of Style or UPC
            	if (StringUtils.isEmpty(ispStyle[i]) && StringUtils.isEmpty(ispUpc[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.styleorupc.required");
            	}
            	else {
		            // Check for valid Style
	            	if (StringUtils.isNotEmpty(ispStyle[i])){
	            		style = validStyle(ispStyle[i]);
	            		if(style == null){
	            			//not valid
	            			ctxt.addError(ActionMessages.GLOBAL_MESSAGE, 
	            					"form.pricingissues.error.style.invalid", 
	            					new Object[] {ispStyle[i]});
	            		}
	            	}
		            // Check for valid UPC
	            	if (StringUtils.isNotEmpty(ispUpc[i])){
	            		upc = validUPC(ispUpc[i]);
	            		if (upc == null){
	            			//not valid
	            			ctxt.addError(ActionMessages.GLOBAL_MESSAGE, 
	            					"form.pricingissues.error.upc.invalid", 
	            					new Object[] {ispUpc[i]});
	            		}
	            	}
            	}
            	
            	// if UPC and style entered and valid, check that they correspond
            	if(upc != null && style != null){
            		Integer upcStyleId = upc.getSku().getStyle().getId();
            		if(upcStyleId == null){
            			//error couldn't find corresponding style for the upc
            		}
            		else if(!upcStyleId.equals(style.getId())){
            			//error not equal form.pricingissues.error.upcStyle.invalid
            			ctxt.addError(ActionMessages.GLOBAL_MESSAGE, 
            					"form.pricingissues.error.upcStyle.invalid",
            					new Object[]{ispStyle[i],ispUpc[i]});
            		}
            	}
            	// only upc entered, attempt to find associated style number and
            	// store in the map
            	else if (upc != null && style == null){
            		String styleNumber = upc.getSku().getStyle().getNumber();
            		if(styleNumber != null && !styleNumber.equals("")){
            			upcStyleNumbers.put(ispUpc[i], StringUtils.leftPad(styleNumber,9,'0'));
            		}
            	}
            	
            	// Check for valid Current Price
            	if (StringUtils.isEmpty(ispCurrentPrice[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.currentprice.required");
            	} else {
	                ispCurrentPrice[i] = StringUtils.strip(ispCurrentPrice[i], "$");
	                if (!validPrice(ispCurrentPrice[i])) {
	            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.currentprice.invalid", new Object[] {ispCurrentPrice[i]});
	                }
            	}
            	// Check for valid Correct Price (Not required)
            	if (StringUtils.isNotEmpty(ispCorrectPrice[i])) {
	                ispCorrectPrice[i] = StringUtils.strip(ispCorrectPrice[i], "$");
	                if (!validPrice(ispCorrectPrice[i])) {
	            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.correctprice.invalid", new Object[] {ispCorrectPrice[i]});
	                }
                }
                // Check for duplicate entries
            	String styleNumber = null;
            	if(StringUtils.isEmpty(ispStyle[i])){
            		styleNumber = upcStyleNumbers.get(ispUpc[i]);
            	}
            	else{
            		styleNumber = ispStyle[i];
            	}
            	
            	if (duplicatePricingIssue(ispDept[i], styleNumber,ispUpc[i],ispCurrentPrice[i])) {
        			ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.ispentryexists", new Object[] {new Integer(i+1)});
        		}
            }
        }
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(pnwDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle1[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle2[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle3[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle4[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle5[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwCorrectPrice[i]))) {
                ++pnwCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(pnwDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
	            // Check for valid Style
            	if (StringUtils.isNotEmpty(pnwStyle1[i]) && validStyle(pnwStyle1[i]) == null) {
        		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.invalid", new Object[] {pnwStyle1[i]});
        	}
            	if (StringUtils.isNotEmpty(pnwStyle2[i]) && validStyle(pnwStyle2[i]) == null) {
        		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.invalid", new Object[] {pnwStyle2[i]});
        	}
            	if (StringUtils.isNotEmpty(pnwStyle3[i]) && validStyle(pnwStyle3[i]) == null) {
        		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.invalid", new Object[] {pnwStyle3[i]});
        	}
            	if (StringUtils.isNotEmpty(pnwStyle4[i]) && validStyle(pnwStyle4[i]) == null) {
        		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.invalid", new Object[] {pnwStyle4[i]});
        	}
            	if (StringUtils.isNotEmpty(pnwStyle5[i]) && validStyle(pnwStyle5[i]) == null) {
        		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.invalid", new Object[] {pnwStyle5[i]});
        	}
            	// Check for valid Correct Price (Not Required)
            	if (StringUtils.isNotEmpty(pnwCorrectPrice[i])) {
            		pnwCorrectPrice[i] = StringUtils.strip(pnwCorrectPrice[i], "$");
	                if (!validPrice(pnwCorrectPrice[i])) {
	            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.correctprice.invalid", new Object[] {pnwCorrectPrice[i]});
	                }
                }
            }
        }
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(isdDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(isdStyle[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(isdComment[i]))) {
                ++isdCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(isdDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
	            // Check for valid Style
            	if (StringUtils.isEmpty(isdStyle[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.required");
            	}
            	if (StringUtils.isNotEmpty(isdStyle[i]) && validStyle(isdStyle[i]) != null) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.style.invalid", new Object[] {isdStyle[i]});
            	}
            }
        }
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(upczeroDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(upczeroUpc[i]))) {
                ++upczeroCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(upczeroDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
	            // Check for valid UPC
            	if (StringUtils.isEmpty(upczeroUpc[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.upc.required");
            	}
            	if (StringUtils.isNotEmpty(upczeroUpc[i]) && validUPC(upczeroUpc[i]) == null) {
        			ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.upc.invalid", new Object[] {upczeroUpc[i]});
        		}
            }
        }
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(upcnsDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(upcnsUpc[i]))) {
                ++upcnsCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(upcnsDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
	            // Check for valid UPC
            	if (StringUtils.isEmpty(upcnsUpc[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.upc.required");
            	}
            }
        }
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(itpDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(itpCurrentTag[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(itpCorrectTag[i]))) {
                ++itpCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(itpDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
            }
        }
        for (int i=0; i<ARRAY_LENGTH; ++i) {
            if (StringUtils.isNotEmpty(StringUtils.trim(otherDept[i]))
            		|| StringUtils.isNotEmpty(StringUtils.trim(otherComment[i]))) {
                ++otherCount;
                ++count;
	            // Check for valid Department
            	if (StringUtils.isEmpty(otherDept[i])) {
            		ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.dept.required");
            	}
            }
        }

        // Verify that at least 1 line item has been entered
        if (count==0) {
            ctxt.addError(ActionMessages.GLOBAL_MESSAGE, "form.pricingissues.error.minrecords");
        }
    }
    
    /**
     * @param styleNum
     * @return Style or null if not valid
     * @throws Exception
     */
    protected Style validStyle(String styleNum) {
        log.debug("validStyle('"+styleNum+"'): start");
        Style result = null;
        Session session = null;
        String leftPadStyleNum = StringUtils.leftPad(styleNum,9,'0');
        log.debug("validStyle(): leftPadStyleNum="+leftPadStyleNum);
        
        List styles = new ArrayList();

        try {
        	// ASSUMPTION - If a style number or r12 number is ever left padded with
        	// leading zeros it will be to fill up 9 characters (ie. 008012022)
            session = SessionManager.getSessionFactory().openSession();
            styles = session.createCriteria(Style.class)
            	.add( Expression.or( 
            			Expression.or( 
            					Expression.eq( "number", styleNum), 
            					Expression.eq( "r12Number", styleNum)
                    	),Expression.or( 
                    			Expression.eq( "number", leftPadStyleNum),
                    			Expression.eq( "r12Number", leftPadStyleNum)
                        )
                      )
                    )
            	.list();
            if (styles.size() > 0) {
            	result = (Style)styles.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
        	try {
	            if (session != null) {
	                session.close();
	            }
        	} catch (Exception e) {}
        }

        log.debug("validStyle('"+styleNum+"'): end - " + (result == null ? false : true));
        return result;
    }

    /**
     * @param upc
     * @return
     * @throws Exception
     */
    protected Upc validUPC(String upc) {
        log.debug("validUPC('"+upc+"'): start");
        Upc result = null;
        Session session = null;
        List upcs = new ArrayList();

        try {
            session = SessionManager.getSessionFactory().openSession();
            Query query1 = session.createQuery(" select sku from Sku sku, Upc upc where sku = upc.sku and upc.upc = "+upc);          
            List sku_result = query1.list();
            if( sku_result.size() == 0 ){
            	Query query2 = session.createQuery(" select style from Style style, Upc upc where style.id = upc.sku and upc.upc = "+upc);
            	Style style = (Style)query2.uniqueResult();
            	if(style!=null){
            		Set skus = style.getSkus();
            		Object[] skus_array = skus.toArray();
                	result = new Upc();
                	result.setUpc(upc);
                	result.setSku((Sku)skus_array[0]);
            	}            	
            }else{
	            upcs = session.createCriteria(Upc.class)
	            	.add( Expression.eq( "upc", upc))
	            	.list();
	            if (upcs.size() > 0) {
	            	result = (Upc)upcs.get(0);
	            	Integer styleId = result.getSku().getStyle().getId();
	            	String styleNumber = result.getSku().getStyle().getNumber();
	            	result.getSku().getStyle().setId(styleId);
	            	result.getSku().getStyle().setNumber(styleNumber);
	            }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
        	try {
	            if (session != null) {
	                session.close();
	            }
        	} catch (Exception e) {}
        }

        log.debug("validUPC('"+upc+"'): end - " + (result == null ? true : false));
        return result;
    }

    /**
     * @param upc
     * @return
     * @throws Exception
     */
    protected boolean duplicatePricingIssue(
    		String dept, String styleNum, String upc, String currentPrice) {
        log.debug("duplicatePricingIssue('"+dept+"','"+styleNum+"','"+upc+"','"+currentPrice+"'): start");

        boolean result = false;
        int id = findPricingIssue(dept,styleNum,upc,currentPrice);
        if (id != -1) {
           	result = true;
        }

        log.debug("duplicatePricingIssue(): end - " + result);
        return result;
    }

    /**
     * @param upc
     * @return
     * @throws Exception
     */
    protected int findPricingIssue(
    		String dept, String styleNum, String upc, String currentPrice) {
        log.debug("findPricingIssue('"+dept+"','"+styleNum+"','"+upc+"','"+currentPrice+"'): start");
        int id = -1;
        Session session = null;
        List pricingIssues = null;

        try {
        	Calendar cal = Calendar.getInstance();

            session = SessionManager.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(PricingIssue.class);
            criteria.add( Expression.eq( "divisionName", dept));
            //if style number not null or empty, use it and current price
            //to find matching pricing issue, upc is irrelevant
            if(styleNum != null && !"".equals(styleNum)){
            	log.debug("style number = "+styleNum);
            	criteria.add( Expression.eq( "styleNum", styleNum));
            }
            else {
            	log.debug("style number is null");
       			//only need to use the UPC when style number is null or empty
       			log.debug("upc = "+upc);
           		if (!upc.equals("")) {
           			log.debug("upc = "+upc);
           			criteria.add( Expression.eq( "upc", upc));
           		} else {
           			log.debug("upc is null");
           			criteria.add( Expression.isNull("upc"));
           		}
            }
       		
        	criteria.add( Expression.eq( "currentPrice", currentPrice));
        	criteria.add( Expression.or(
        		Expression.isNull("closedDate"),
        		Expression.gt("closedDate", new java.util.Date(cal.getTimeInMillis() - DAY_MILLISECONDS))));
        	criteria.addOrder( Order.desc("id"));
        	pricingIssues = criteria.list();
        	if (pricingIssues.size() > 1) {
        		log.error("ERROR - Duplicate entries found in database. Returning most recent id.");
        	}
            if (pricingIssues.size() > 0) {
            	id = ((PricingIssue) pricingIssues.get(0)).getId().intValue();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
        	try {
	            if (session != null) {
	                session.close();
	            }
        	} catch (Exception e) {}
        }

        log.debug("findPricingIssue(): end - PricingIssueID=" + id);
        return id;
    }

    /**
     * Ensure all required form fields are filled out
     */
    public boolean validPrice(String price) {
    	boolean result = false;
    	
        try {
        	new Float(price);
        	result = true;
        } catch (Exception e) {
        	log.debug("validPrice Exception - " + e);
        }
    	
    	return result;
    }
    
    public String formatPrice(String price) {
    	
    	price = StringUtils.strip(price, "$");
    	price = "$" + price;

    	if (StringUtils.indexOf(price,".") != -1) {
    		if (price.length() - 1 - StringUtils.indexOf(price,".") == 1)
    			price = price + "0";
    	} else {
    		price = price + ".00";
    	}
    	log.debug("formatPrice price = " + price);

    	return price;
    }
    
    /**
     * Submit form. Generate the pdf file and send it to claims department.
     * @return
     */
    public String doSubmit() {
        
        String fwd = Constants.FAILURE;

        log.debug(this.toString());
        
        try {
            ActionContext actionCtxt = ActionContext.getActionContext();

            HttpServletRequest request = actionCtxt.getRequest();

            // Configure the subject line of the email
            String emailSubject = subject;
            if (emailSubject == null || emailSubject.trim().equals("")) {
                emailSubject = actionCtxt.getResources().getMessage("form.pricingissues.title");
            }
            
            // Configure the CC of the email
            String cc = null;
            Store store = Utils.getStoreFromSession();
            if (store != null) {
                cc = store.getEmailAddress();
            }

            for (int i=0; i<ISP_ARRAY_LENGTH; ++i) {
                if (StringUtils.isNotEmpty(StringUtils.trim(ispDept[i]))
                		|| StringUtils.isNotEmpty(StringUtils.trim(ispStyle[i]))
                		|| StringUtils.isNotEmpty(StringUtils.trim(ispUpc[i]))
                		|| StringUtils.isNotEmpty(StringUtils.trim(ispCurrentPrice[i]))
                		|| StringUtils.isNotEmpty(StringUtils.trim(ispCorrectPrice[i]))) {
                	log.debug("Add Pricing Issue to DB");
		            PricingIssue pricingIssue = new PricingIssue();
		            pricingIssue.setStoreNum(storeNum);
		            pricingIssue.setSubmittedBy(submittedBy);
		            pricingIssue.setDivisionName(ispDept[i]);
		            // if no style number, then a upc must have been entered in
		            // the form. Check if a style number was found for this upc
		            if(ispStyle[i] == null || "".equals(ispStyle[i])){
		            	if(upcStyleNumbers != null){
		            		// either find an associated style number or set the 
		            		// style number to null 
		            		ispStyle[i] = upcStyleNumbers.get(ispUpc[i]);
		            	}
		            }
		            pricingIssue.setStyleNum(ispStyle[i]);
		            pricingIssue.setUpc(ispUpc[i]);
		            pricingIssue.setCurrentPrice(ispCurrentPrice[i]);
		            pricingIssue.setCorrectPrice(ispCorrectPrice[i]);
		
		            savePricingIssue(pricingIssue);
                }
            }

            String body = generateEmailBody(actionCtxt);
            boolean testing = isTesting(actionCtxt.getResources());
            if (testing) {
                // only cc the store if we are on the production environment
                body += "\n\nOn the production environment, the store will be CCed a copy of this email using address: " + cc;
                cc = null;
            }
            log.debug("cc address: " + cc);
            
            boolean msgSent = PostalService.getInstance()
                    .sendMessage(getEmailTo(),
                            cc == null? null : new String[] {cc},
                            null/*null=use default from*/,
                            emailSubject, body);
            
            if (!msgSent) {
                actionCtxt.addError("error", "form.error.couldnotsendemail");
                fwd = Constants.FAILURE; 
            } else {   
                
                // Create an dynamic action forward
                ParameterizedActionForward downloadFwd = new ParameterizedActionForward(
                        actionCtxt.getMapping().findForward(Constants.SUCCESS));
                downloadFwd.setRedirect(true);
                
                request.setAttribute(ActionConstants.DYNAMIC_FORWARD, downloadFwd);
                fwd = null;
            }
        } catch (Exception e) {
            fwd = Constants.FAILURE;
            log.error(e.getMessage(), e);
        }
        finally{
        	// cleanup
        	upcStyleNumbers = null;
        }

        return fwd;
    }
    
    /**
     * Save a pricingIssue to the database.
     * @param issue
     * @return
     * @throws Exception
     */
    public boolean savePricingIssue(PricingIssue issue) throws Exception {
        log.debug("savePricingIssue - start");
        boolean success = false;
        Session session = null;
        Transaction tx = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(issue);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.debug("savePricingIssue - end");
        return success;
    }
    
    /**
     * Generate the body of the email.
     * @return
     */
    public String generateEmailBody(ActionContext actionCtxt) {
    	StringBuffer result = new StringBuffer();
    	
    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.title"));
    	result.append("\n\n");
    	result.append(actionCtxt.getResources().getMessage("form.label.date"));
    	result.append(": " + today + "     ");
    	result.append(actionCtxt.getResources().getMessage("form.label.storenum"));
    	result.append(": " + storeNum + "     ");
    	result.append(actionCtxt.getResources().getMessage("form.label.submittedby"));
    	result.append(": " + submittedBy + "\n");
    	result.append("\n");
    	if (ispCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.ispheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ISP_ARRAY_LENGTH; ++i) {
	            if (StringUtils.isNotEmpty(StringUtils.trim(ispDept[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(ispStyle[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(ispUpc[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(ispCurrentPrice[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(ispCorrectPrice[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.id"));
		            result.append(": " + findPricingIssue(ispDept[i],ispStyle[i],ispUpc[i],ispCurrentPrice[i]) + "\n");
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + ispDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + ispStyle[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.upc"));
		            result.append(": " + ispUpc[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.currentprice"));
		            result.append(": " + formatPrice(ispCurrentPrice[i]) + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.correctprice"));
		            result.append(": " + formatPrice(ispCorrectPrice[i]) + "\n\n");
	            }
	        }
	    	result.append("\n");
    	}
    	if (pnwCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.pnwheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ARRAY_LENGTH; ++i) {
	            if (StringUtils.isNotEmpty(StringUtils.trim(pnwDept[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle1[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle2[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle3[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle4[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwStyle5[i]))
	            		|| StringUtils.isNotEmpty(StringUtils.trim(pnwCorrectPrice[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + pnwDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + pnwStyle1[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + pnwStyle2[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + pnwStyle3[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + pnwStyle4[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + pnwStyle5[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.correctprice"));
		            result.append(": " + formatPrice(pnwCorrectPrice[i]) + "\n\n");
	            }
	        }
	    	result.append("\n");
    	}
    	if (isdCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.isdheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ARRAY_LENGTH; ++i) {
		        if (StringUtils.isNotEmpty(StringUtils.trim(isdDept[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(isdStyle[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(isdComment[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + isdDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.stylenum"));
		            result.append(": " + isdStyle[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.comments"));
		            result.append(": " + isdComment[i] + "\n\n");
		        }
	        }
	    	result.append("\n");
    	}
    	if (upczeroCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.upczeroheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ARRAY_LENGTH; ++i) {
		        if (StringUtils.isNotEmpty(StringUtils.trim(upczeroDept[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(upczeroUpc[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + upczeroDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.upc"));
		            result.append(": " + upczeroUpc[i] + "\n\n");
		        }
	        }
	    	result.append("\n");
    	}
    	if (upcnsCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.upcnsheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ARRAY_LENGTH; ++i) {
		        if (StringUtils.isNotEmpty(StringUtils.trim(upcnsDept[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(upcnsUpc[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + upcnsDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.upc"));
		            result.append(": " + upcnsUpc[i] + "\n\n");
		        }
	        }
	    	result.append("\n");
    	}
    	if (itpCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.itpheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ARRAY_LENGTH; ++i) {
		        if (StringUtils.isNotEmpty(StringUtils.trim(itpDept[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(itpCurrentTag[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(itpCorrectTag[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + itpDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.currenttag"));
		            result.append(": " + itpCurrentTag[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.correcttag"));
		            result.append(": " + itpCorrectTag[i] + "\n\n");
		        }
	        }
	    	result.append("\n");
    	}
    	if (otherCount > 0) {
	    	result.append(actionCtxt.getResources().getMessage("form.pricingissues.otherheader").toUpperCase());
	    	result.append("\n\n");
	        for (int i=0; i<ARRAY_LENGTH; ++i) {
		        if (StringUtils.isNotEmpty(StringUtils.trim(otherDept[i]))
		        		|| StringUtils.isNotEmpty(StringUtils.trim(otherComment[i]))) {
	            	result.append(actionCtxt.getResources().getMessage("form.label.dept"));
		            result.append(": " + otherDept[i] + "\n");
		            result.append(actionCtxt.getResources().getMessage("form.label.description"));
		            result.append(": " + otherComment[i] + "\n\n");
		        }
	        }
    	}
    	return result.toString();
    }
    
    private boolean isTesting(MessageResources msgResource) {
        String testing = msgResource.getMessage("testing");
        if (testing.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }
    
    protected String[] getEmailTo() {
        ArrayList<String> toList = new ArrayList<String>();

        if (document.getEmailAddress() != null) {
            String[] dbEmails = document.getEmailAddress().split(";");
            if (dbEmails != null) {
                for (int i=0; i<dbEmails.length; ++i) {
                    if (!dbEmails[i].trim().equals("")) {
                        toList.add(dbEmails[i].trim());
                    }
                }
            }
        }
        
        if (to != null && !to.trim().equals("")) {
            toList.add(to);
        }
        
        String[] tos = new String[toList.size()];
        toList.toArray(tos);
        return tos;
    }
    
    /**
     * Writes out all properties
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("today: " + today);
        buf.append("\nstoreNum: " + storeNum);
        buf.append("\nsubmittedBy: " + submittedBy);
        return buf.toString();
    }

    /**
     * @return Returns the today.
     */
    public String getToday() {
        return today;
    }
    /**
     * @param today The today to set.
     */
    public void setToday(String today) {
        this.today = today;
    } 
    /**
     * @return Returns the storeNum.
     */
    public Integer getStoreNum() {
        return storeNum;
    }
    /**
     * @param storeNum The storeNum to set.
     */
    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }
    /**
     * @return Returns the submittedBy.
     */
    public String getSubmittedBy() {
        return submittedBy;
    }
    /**
     * @param submittedBy The submittedBy to set.
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
    /**
     * @return Returns the deptList.
     */
    public String[] getDeptList() {
        return deptList;
    }
    /**
     * @param deptList The deptList to set.
     */
    public void setDeptList(String[] deptList) {
        this.deptList = deptList;
    }
    /**
     * @return Returns the ispDept.
     */
    public String[] getIspDept() {
        return ispDept;
    }
    /**
     * @param ispDept The ispDept to set.
     */
    public void setIspDept(String[] ispDept) {
        this.ispDept = ispDept;
    }
    /**
     * @return Returns the ispStyle.
     */
    public String[] getIspStyle() {
        return ispStyle;
    }
    /**
     * @param ispStyle The ispStyle to set.
     */
    public void setIspStyle(String[] ispStyle) {
        this.ispStyle = ispStyle;
    }
    /**
     * @return Returns the ispUpc.
     */
    public String[] getIspUpc() {
        return ispUpc;
    }
    /**
     * @param ispUpc The ispUpc to set.
     */
    public void setIspUpc(String[] ispUpc) {
        this.ispUpc = ispUpc;
    }
    /**
     * @return Returns the ispCurrentPrice.
     */
    public String[] getIspCurrentPrice() {
        return ispCurrentPrice;
    }
    /**
     * @param ispCurrentPrice The ispCurrentPrice to set.
     */
    public void setIspCurrentPrice(String[] ispCurrentPrice) {
        this.ispCurrentPrice = ispCurrentPrice;
    }
    /**
     * @return Returns the ispCorrectPrice.
     */
    public String[] getIspCorrectPrice() {
        return ispCorrectPrice;
    }
    /**
     * @param ispCorrectPrice The ispCorrectPrice to set.
     */
    public void setIspCorrectPrice(String[] ispCorrectPrice) {
        this.ispCorrectPrice = ispCorrectPrice;
    }
    /**
     * @return Returns the pnwDept.
     */
    public String[] getPnwDept() {
        return pnwDept;
    }
    /**
     * @param pnwDept The pnwDept to set.
     */
    public void setPnwDept(String[] pnwDept) {
        this.pnwDept = pnwDept;
    }
    /**
     * @return Returns the pnwStyle1.
     */
    public String[] getPnwStyle1() {
        return pnwStyle1;
    }
    /**
     * @param pnwStyle1 The pnwStyle1 to set.
     */
    public void setPnwStyle1(String[] pnwStyle1) {
        this.pnwStyle1 = pnwStyle1;
    }
    /**
     * @return Returns the pnwStyle2.
     */
    public String[] getPnwStyle2() {
        return pnwStyle2;
    }
    /**
     * @param pnwStyle2 The pnwStyle2 to set.
     */
    public void setPnwStyle2(String[] pnwStyle2) {
        this.pnwStyle2 = pnwStyle2;
    }
    /**
     * @return Returns the pnwStyle3.
     */
    public String[] getPnwStyle3() {
        return pnwStyle3;
    }
    /**
     * @param pnwStyle3 The pnwStyle3 to set.
     */
    public void setPnwStyle3(String[] pnwStyle3) {
        this.pnwStyle3 = pnwStyle3;
    }
    /**
     * @return Returns the pnwStyle4.
     */
    public String[] getPnwStyle4() {
        return pnwStyle4;
    }
    /**
     * @param pnwStyle4 The pnwStyle4 to set.
     */
    public void setPnwStyle4(String[] pnwStyle4) {
        this.pnwStyle4 = pnwStyle4;
    }
    /**
     * @return Returns the pnwStyle5.
     */
    public String[] getPnwStyle5() {
        return pnwStyle5;
    }
    /**
     * @param pnwStyle5 The pnwStyle5 to set.
     */
    public void setPnwStyle5(String[] pnwStyle5) {
        this.pnwStyle5 = pnwStyle5;
    }
    /**
     * @return Returns the pnwCorrectPrice.
     */
    public String[] getPnwCorrectPrice() {
        return pnwCorrectPrice;
    }
    /**
     * @param pnwCorrectPrice The pnwCorrectPrice to set.
     */
    public void setPnwCorrectPrice(String[] pnwCorrectPrice) {
        this.pnwCorrectPrice = pnwCorrectPrice;
    }
    /**
     * @return Returns the isdDept.
     */
    public String[] getIsdDept() {
        return isdDept;
    }
    /**
     * @param isdDept The isdDept to set.
     */
    public void setIsdDept(String[] isdDept) {
        this.isdDept = isdDept;
    }
    /**
     * @return Returns the isdStyle.
     */
    public String[] getIsdStyle() {
        return isdStyle;
    }
    /**
     * @param isdStyle The isdStyle to set.
     */
    public void setIsdStyle(String[] isdStyle) {
        this.isdStyle = isdStyle;
    }
    /**
     * @return Returns the isdComment.
     */
    public String[] getIsdComment() {
        return isdComment;
    }
    /**
     * @param isdComment The isdComment to set.
     */
    public void setIsdComment(String[] isdComment) {
        this.isdComment = isdComment;
    }
    /**
     * @return Returns the upczeroDept.
     */
    public String[] getUpczeroDept() {
        return upczeroDept;
    }
    /**
     * @param upczeroDept The upczeroDept to set.
     */
    public void setUpczeroDept(String[] upczeroDept) {
        this.upczeroDept = upczeroDept;
    }
    /**
     * @return Returns the upczeroUpc.
     */
    public String[] getUpczeroUpc() {
        return upczeroUpc;
    }
    /**
     * @param upczeroUpc The upczeroUpc to set.
     */
    public void setUpczeroUpc(String[] upczeroUpc) {
        this.upczeroUpc = upczeroUpc;
    }
    /**
     * @return Returns the upcnsDept.
     */
    public String[] getUpcnsDept() {
        return upcnsDept;
    }
    /**
     * @param upcnsDept The upcnsDept to set.
     */
    public void setUpcnsDept(String[] upcnsDept) {
        this.upcnsDept = upcnsDept;
    }
    /**
     * @return Returns the upcnsUpc.
     */
    public String[] getUpcnsUpc() {
        return upcnsUpc;
    }
    /**
     * @param upcnsUpc The upcnsUpc to set.
     */
    public void setUpcnsUpc(String[] upcnsUpc) {
        this.upcnsUpc = upcnsUpc;
    }
    /**
     * @return Returns the itpDept.
     */
    public String[] getItpDept() {
        return itpDept;
    }
    /**
     * @param itpDept The itpDept to set.
     */
    public void setItpDept(String[] itpDept) {
        this.itpDept = itpDept;
    }
    /**
     * @return Returns the itpCurrentTag.
     */
    public String[] getItpCurrentTag() {
        return itpCurrentTag;
    }
    /**
     * @param itpCurrentTag The itpCurrentTag to set.
     */
    public void setItpCurrentTag(String[] itpCurrentTag) {
        this.itpCurrentTag = itpCurrentTag;
    }
    /**
     * @return Returns the itpCorrectTag.
     */
    public String[] getItpCorrectTag() {
        return itpCorrectTag;
    }
    /**
     * @param itpCorrectTag The itpCorrectTag to set.
     */
    public void setItpCorrectTag(String[] itpCorrectTag) {
        this.itpCorrectTag = itpCorrectTag;
    }
    /**
     * @return Returns the otherDept.
     */
    public String[] getOtherDept() {
        return otherDept;
    }
    /**
     * @param otherDept The otherDept to set.
     */
    public void setOtherDept(String[] otherDept) {
        this.otherDept = otherDept;
    }
    /**
     * @return Returns the otherComment.
     */
    public String[] getOtherComment() {
        return otherComment;
    }
    /**
     * @param otherComment The otherComment to set.
     */
    public void setOtherComment(String[] otherComment) {
        this.otherComment = otherComment;
    }
    /**
     * @return Returns the document.
     */
    public Document getDocument() {
        return document;
    }
    /**
     * @param document The document to set.
     */
    public void setDocument(Document document) {
        this.document = document;
    }
    /**
     * @return Returns the documentId.
     */
    public int getDocumentId() {
        return documentId;
    }
    /**
     * @param documentId The documentId to set.
     */
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
    /**
     * @return Returns the subject.
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subject The subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
