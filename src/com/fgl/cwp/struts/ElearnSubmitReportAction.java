package com.fgl.cwp.struts;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.ExcelPrinter;
import com.fgl.cwp.common.util.SumMultiMap;
import com.fgl.cwp.exception.GeneratePdfException;
import com.fgl.cwp.model.QueryParameters;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.model.User;
import com.fgl.cwp.model.sumtotal.ReportType;
import com.fgl.cwp.model.sumtotal.eLearnReport.ELearnReportData;
import com.fgl.cwp.persistence.services.ElearningReportService;
import com.fgl.cwp.persistence.services.PDFService;
import com.fgl.cwp.presentation.ELearningReportBean;

/**
 * Creates either the MS Excel or PDF version of the E-Learning report.
 * 
 * @author jMbarga/gdelve
 *
 */
public class ElearnSubmitReportAction extends DispatchAction {

	private static final Log log = LogFactory.getLog(ElearnSubmitReportAction.class);
	private static String E_LEARNING_FILE_NAME = "E_Learning_Report_";
	private static final String DATE_FORMAT = "MM/dd/yyyy";	
	private static final String PDF_FILEPATH = "/forms/eLearning_report.jsp";
	private static final String ELEARN_REPORT_FILEPATH = "report.elearning.filepath";
	private static final String ELEARN_VIRTUAL_DIRECTORY = "report.elearning.virtualdirectory";
	private static final String ELEARN_PARAMETER = "eLearnFileName";
	private static final String REPORT_TYPE_PARAM = "reportType";
	private static final int EXCEL = 1;
	private static final int PDF = 2;
	
	/** Identifies the maximum number of courses that may be reported on */
	private static final int MAX_SELECTED_COURSES = 10;

	/** Identifies the maximum number of stores that may be reported on */
	//private static final int MAX_SELECTED_STORES = 5;

	/**
	 * Default constructor
	 */
	public ElearnSubmitReportAction() {
	}
	
	/**
	 * Generates the MS Excel version of the E-Learning report
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward excel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("excel", "checked");
		//request.setAttribute("pdf","false");
		if( !validateSession(request) ){
		       ActionContext.getActionContext().addError("error", "session.timeout.message");
		       saveContextMessages(request);
		       return mapping.findForward(Constants.SESSION_TIMEOUT);
		   }
		
		QueryParameters params = getParameters((ELearningReportBean) form);
		
		SumMultiMap report = validation(mapping, request, response, params);
		if( report == null ){
			return mapping.findForward(Constants.FAILURE);
		}
		
		ExcelPrinter printer = new ExcelPrinter(report,params);
		String directory = ActionContext.getActionContext().getResources().getMessage(ELEARN_REPORT_FILEPATH);
		String virtualDirectory = ActionContext.getActionContext().getResources().getMessage(ELEARN_VIRTUAL_DIRECTORY);
		String filePath = directory + E_LEARNING_FILE_NAME +System.currentTimeMillis()+ ".xls";
        File excelFile = new File(filePath);
        
        // make sure the parent directory exists
        File parentDirectory = new File(excelFile.getParent());
        if (!parentDirectory.exists()) {
        	parentDirectory.mkdirs();
        }
		try {
			printer.generateReport(excelFile);
			request.setAttribute(REPORT_TYPE_PARAM, new Integer(EXCEL));
			request.setAttribute(ELEARN_PARAMETER, "http://"+request.getServerName()+virtualDirectory+excelFile.getName());
		}
		catch (IOException ioe){
			log.error(ioe);
	    	ActionContext.getActionContext().addError("error",
			"report.error.applicationerror");
			saveContextMessages(request);
			return mapping.findForward(Constants.FAILURE);
		}
		
		return mapping.findForward(Constants.SUCCESS);	
	}

	/**
	 * Generates the PDF version of the E-Learning report
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//request.setAttribute("excel", "false");
		request.setAttribute("pdf","checked");
		if( !validateSession(request) ){
	       ActionContext.getActionContext().addError("error", "session.timeout.message");
	       saveContextMessages(request);
	       return mapping.findForward(Constants.SESSION_TIMEOUT);
		}

		QueryParameters params = getParameters((ELearningReportBean) form);
		SumMultiMap dataMap = validation(mapping, request, response, params);
		if(dataMap == null){
			return mapping.findForward(Constants.FAILURE);
		}
			
    	ELearnReportData data = new ELearnReportData(dataMap, params.getCourses(), params.getStores());
    	dataMap = null;
    	if(params.getStartDate() != null){
    		data.setStartDate(params.getStartDate().getTime());
    	}
    	if(params.getEndDate() != null){
    		data.setEndDate(params.getEndDate().getTime());
    	}
    	
    	request.getSession().setAttribute("eLearnReportData", data);
    	String xmlTemplateURL = response.encodeURL(PDF_FILEPATH);
	    	
        if (!xmlTemplateURL.contains("jsessionid")) {
            xmlTemplateURL =  request.getScheme()
                    + "://"
                    + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + request.getContextPath()
                    + xmlTemplateURL
                    + ";jsessionid="
                    + request.getSession().getId();
        }
           
	    try{
	       String directory = ActionContext.getActionContext().getResources().getMessage(ELEARN_REPORT_FILEPATH);
	       String virtualDirectory = ActionContext.getActionContext().getResources().getMessage(ELEARN_VIRTUAL_DIRECTORY);
	       File pdf = PDFService.getInstance().generatePdf(
	    		   xmlTemplateURL, directory , "E_Learning_Report");
	       request.setAttribute(REPORT_TYPE_PARAM, new Integer(PDF));
	       request.setAttribute(ELEARN_PARAMETER, "http://"+request.getServerName()+virtualDirectory+pdf.getName());
	    }
	    catch(GeneratePdfException gpe){
	    	log.error(gpe);
	    	if(gpe.getCause() instanceof java.lang.OutOfMemoryError){
	    		throw (java.lang.OutOfMemoryError)gpe.getCause();
	    	}
	    	ActionContext.getActionContext().addError("error",
			"report.error.applicationerror");
			saveContextMessages(request);
			return mapping.findForward(Constants.FAILURE);
	    }
	    finally{
	    	data = null;
	    	request.getSession().removeAttribute("eLearnReportData");
	    }	    
	   	return mapping.findForward(Constants.SUCCESS);	
	}

	/**
	 * Retrieves the parameters from the report bean and creates and populates
	 * the {@link QueryParameter}
	 * 
	 * @param reportBean
	 * @return QueryParameter
	 */
	private QueryParameters getParameters(ELearningReportBean reportBean) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		QueryParameters params = new QueryParameters();
		if (reportBean.getSelectedStoreIds() != null) {
			String[] chosenIds = reportBean.getSelectedStoreIds();
			java.util.List storesList = reportBean.getStoreList();
			ArrayList<Store> storesArray = new ArrayList<Store>();
			for (int x = 0; storesList != null && x < storesList.size(); x++) {
				Store store = (Store) storesList.get(x);
				for (int i = 0; i < chosenIds.length; i++) {
					if (Integer.parseInt(chosenIds[i]) == store.getNumber()) {
						if (!storesArray.contains(store))
							storesArray.add(store);
					}
				}
			}
			params.setStores(storesArray);
		} else if (reportBean.getStore() != null) {
			params.setStores(Arrays.asList(reportBean.getStore()));
		}
		if (reportBean.getSelectedDepartments() != null) {
			params.setDepartments(reportBean.getSelectedDepartments());
		}
		if (reportBean.getSelectedCourseIds() != null) {
			String[] chosenCoursesIds = reportBean.getSelectedCourseIds();
			List<SumTotalCourse> allCourses = reportBean.getCourses();
			ArrayList<SumTotalCourse> chosenArray = new ArrayList<SumTotalCourse>();
			for (int x = 0; allCourses != null && x < allCourses.size(); x++) {
				SumTotalCourse course = allCourses.get(x);
				for (int i = 0; i < chosenCoursesIds.length; i++) {
					if (course.getCourseId() == Integer
							.parseInt(chosenCoursesIds[i])) {
						chosenArray.add(course);
					}
				}
			}
			params.setCourses(chosenArray);
		}
		params.setStartDate(parseDateString(reportBean.getStartDate(),
				formatter));
		params.setEndDate(parseDateString(reportBean.getEndDate(), formatter));
		params.setReportType( (new ReportType(reportBean.getMethod())).getReportType());
		return params;
	}

	/**
	 * Verifies that the necessary information has been submitted and it is
	 * valid.
	 * 
	 * @return True if valid.
	 */

	private boolean validateReportCriteria(QueryParameters params) {
		boolean valid = true;
		// validate the store selection
		if (params.getStores().size() == 0) {
			log.error(
				"Error checking data for the ELearning Report (invalid number of stores selected)");
				ActionContext.getActionContext().addError("error",
					"report.error.nostoreselection");
				valid = false;
		}
		
		// ensure the dates are valid
		if ((params.getStartDate() != null && params.getEndDate() != null)
				&& params.getStartDate().after(params.getEndDate())) {
			ActionContext.getActionContext().addError("error",
					"report.selection.validate.startDateAfterEndDate");
			valid = false;
		}

		String format = params.getReportType();
		//validate the course selection
		if ( (format !=null && !format.equalsIgnoreCase("excel")) && params.getCourses().size() > MAX_SELECTED_COURSES) {
			log.error("Error checking data for the ELearning Report (invalid number of courses selected)");
			ActionContext.getActionContext().addError("error","report.error.maxcourseselection");
			valid = false;
		} else if (params.getCourses().size() == 0) {
			log.error("Error checking data for the ELearning Report (invalid number of courses selected)");
			ActionContext.getActionContext().addError("error","report.error.nocourseselection");
			valid = false;
		}
		return valid;
	}

	private GregorianCalendar parseDateString(String dateString,
			SimpleDateFormat formatter) {
		Date parsedDate;
		GregorianCalendar calendarDate = null;
		try {
			if (dateString != null && !"".equalsIgnoreCase(dateString)) {
				parsedDate = formatter.parse(dateString);
				calendarDate = new GregorianCalendar();
				calendarDate.setTime(parsedDate);
			}

		} catch (ParseException pe) {
			ActionContext.getActionContext().addError("error",
					"report.selection.validate.badDateFormat",
					new Object[] { formatter.toPattern() });
		}
		return calendarDate;
	}
	
	private void saveContextMessages(HttpServletRequest request) {
		ActionContext context = ActionContext.getActionContext();
		if (context.getErrors() != null) {
			saveErrors(request, (ActionMessages) context.getErrors());
		}
		if (context.getMessages() != null) {
			saveMessages(request, context.getMessages());
		}
	}

	private SumMultiMap getData(QueryParameters params) {
		SumMultiMap report = null;
		try {
			report = ElearningReportService.getElearnReportData(params);
		} catch (ClassCastException cce) {
			log.error(cce);
		} catch (HibernateException he) {
			log.error(he);
		}
		return report;
	}	
	
	private boolean validateSession(HttpServletRequest request){		
		ActionContext context = ActionContext.getActionContext();
		User user  = (User)context.getRequest().getSession().getAttribute(Constants.USER);
		Store store = (Store)context.getRequest().getSession().getAttribute(Constants.STORE);
		if( user != null || store != null)
			return true;
		else return false;
	}
	
	/**
	 * Calls the methods of to perform specific validation, and attempts to 
	 * retrieve the elearning report data. If a failure encountered, an error 
	 * message is added to the context
	 * 
	 * @param mapping
	 * @param request
	 * @param response
	 * @param params
	 * @return SumMultiMap containing the report data or null if validation or 
	 * 	data retrieval error
	 */
	private SumMultiMap validation(ActionMapping mapping, 
			HttpServletRequest request, HttpServletResponse response, QueryParameters params){
		
		ActionContext.initialize(mapping, request, response,
				getResources(request));	
		
		if (!validateReportCriteria(params)){
			saveContextMessages(request);
			return null;
		}

		SumMultiMap report = getData(params);
		if( report == null ){
			ActionContext.getActionContext().addError("error",
			"report.error.applicationerror");
			saveContextMessages(request);
			return null;
		}		
		return report;
	}
}
