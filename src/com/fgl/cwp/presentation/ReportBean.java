package com.fgl.cwp.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.HibernateException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.ReportService;
import com.fgl.cwp.reporter.ReporterType;
import com.fgl.cwp.reporter.TaskEngine;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author Jessica Wong
 */
public class ReportBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    private static final Log log = LogFactory.getLog(ReportBean.class);
    
    /**
     * Mark up
     */
    public static final String REPORT_MARKUP = "up";
    /**
     * Mark down
     */
    public static final String REPORT_MARKDOWN = "down";
    
    private List storeList;
    private int storeId;
    private Store store;
    
    private List queuedReports;
    
    /* Report Parameters */
    private String startDate;
    private String endDate;
    private String mark = REPORT_MARKUP; //markup or markdown
    private String dateFormat;
    private User user =null;

    /**
     * Initialize store.
     */
    void initStore() {
        
        /* One or the other of these should be null */        
    	user = Utils.getUserFromSession();
        store = Utils.getStoreFromSession();
        assert user==null || store == null;
        
        if (user != null) {
            storeList = new ArrayList(user.getStores());
            store = (Store)storeList.get(0);
        }
        if (storeId == 0){
            storeId = store.getNumber();
        }
    }
    /**
     * Initialize the bean's properties
     * @return success or failure
     */
    public String initializePackageReport() {
        log.debug("In initializePackageReport");
        
        initStore();
        
        // Set default with mark up
        mark = REPORT_MARKUP;
        
        return Constants.SUCCESS;
    }

    /**
     * Initialize the bean's properties
     * @return success or failure
     */
    public String initializeItemPriceChangeReport() {
        log.debug("In initializeItemPriceChangeReport");
        
        initStore();
        
        // Set default with mark down
        mark = REPORT_MARKDOWN;
        
        return Constants.SUCCESS;
    }

    /**
     * Fetch the list of reports submitted at the given store
     * @return
     * @throws HibernateException
     */
    public String fetchReportQueue() throws HibernateException{
        initStore();
        String serverName = ActionContext.getActionContext().getRequest().getServerName();
        queuedReports = ReportService.getInstance().getReportQueueAtStore( storeId, serverName );
        return Constants.SUCCESS;
    }
    
    

    /**
     * submit the price change report
     * @return success or failure
     */
    public String submitItemPriceChangeReport() {
        log.debug("In Submit Item Price Change Report");

        initStore();
        
        ActionContext context=ActionContext.getActionContext();
        if (!validateReportCriteria()) {
            return Constants.FAILURE;
        }

        String success = Constants.SUCCESS;        
        try {            
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            int count = ReportService.getInstance()
                    .checkForItemPriceChangeReportData(storeId,
                            (mark.equals(REPORT_MARKUP) ? 1 : 2),
                            formatter.parse(startDate),
                            formatter.parse(endDate));

            if (count <=0) {
                context.addError("error", "report.error.nodata");
                success = Constants.FAILURE;
            } else {
                Report report = new Report();
                
                report.setExecuteReportAtUrl(createExecuteUrl("/reporter/createItemPriceChangeReport.do"));
                report.setStoreId(storeId);
                report.setName(Constants.ITEM_PRICE_CHANGE_REPORT);
                report.setParameters("Mark " + mark + " " + startDate + " " + endDate);
                report.setEmail("");
                report.setServer(context.getRequest().getServerName());
                report.setStatus(Report.STATUS_QUEUED);
        
                ReportService.getInstance().submitReport(report);
                
                TaskEngine engine = TaskEngine.getInstance();
                engine.addTask(report,TaskEngine.TYPE_REPORT_OBJ,ReporterType.pdfReporter,user,1);
                
                success = Constants.SUCCESS;
            }
        } catch (Exception e) {
            log.error("Error checking for data for the Item Price Change Report", e);
            context.addError("error", "report.error.checkingdata");
            success = Constants.FAILURE;
        }
        
        return success;
    }
    
    /**
     * Submit a Package Report Request to the database
     * @return success or failure
     */
    public String submitPackageReport() {
        log.debug("In Submit Package Report");

        initStore();
        
        ActionContext context=ActionContext.getActionContext();
        if (!validateReportCriteria()) {
            return Constants.FAILURE;
        }
        
        String success = Constants.SUCCESS;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            int count = ReportService.getInstance()
                    .checkForPackageReportData(storeId,
                            formatter.parse(startDate),
                            formatter.parse(endDate));
                            
            if (count <=0) {
                context.addError("error", "report.error.nodata");
                success = Constants.FAILURE;
            } else {
                Report report = new Report();
                                
                report.setExecuteReportAtUrl(createExecuteUrl("/reporter/createPackageReport.do"));
                report.setStoreId(storeId);
                report.setName(Constants.PACKAGE_PRICE_CHANGE_REPORT);
                report.setParameters("Mark " + mark + " " + startDate + " " + endDate);
                report.setEmail("");
                report.setServer(context.getRequest().getServerName());
                report.setStatus(Report.STATUS_QUEUED);
                
                TaskEngine engine = TaskEngine.getInstance();
                engine.addTask(report,TaskEngine.TYPE_REPORT_OBJ,ReporterType.pdfReporter,user,1);
        
                ReportService.getInstance().submitReport(report);
                success = Constants.SUCCESS;
            }
        } catch (Exception e) {
            log.error("Error checking for data for the Item Price Change Report", e);
            context.addError("error", "report.error.checkingdata");
            success = Constants.FAILURE;
        }
        
        return success;
    }

    private String createExecuteUrl(String reportPath) {
        StringBuffer executeUrl = new StringBuffer();
        HttpServletRequest request = ActionContext.getActionContext().getRequest();
        executeUrl.append("http://");
        executeUrl.append(request.getServerName());
        executeUrl.append(":" + request.getServerPort());
        executeUrl.append(request.getContextPath());                
        executeUrl.append(reportPath);
        executeUrl.append("?display=true");//required so that reporter can save PDF to file
        executeUrl.append("&storeNumber=" + storeId);
        executeUrl.append("&startDate=" + startDate);
        executeUrl.append("&endDate=" + endDate);
        if (!StringUtils.isEmpty(mark)){
            executeUrl.append("&mark=" + mark);
        }
        log.debug("\nURL: " + executeUrl.toString());
        return executeUrl.toString();
    }

    /**
     * @return the validity of the user's report parameters.
     */
    private boolean validateReportCriteria() {
        log.debug("In Validate Report Criteria");
        ActionContext context = ActionContext.getActionContext();
        boolean status = true;

        if ((startDate.equals(null) || endDate.equals(null) || ((startDate.trim().length() == 0) || (endDate.trim().length() == 0)))) {
            context.addError("error", "report.selection.validate.dates");
            status = false;
        } else {
            log.debug("dateFormat: " + dateFormat);
            
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            try {
                Date startDateAsDate = formatter.parse(startDate);
                Date endDateAsDate = formatter.parse(endDate);
                
                if (startDateAsDate.after(endDateAsDate)) {
                    context.addError("error", "report.selection.validate.startDateAfterEndDate");
                    status = false;
                }
            } catch (ParseException pe) {
                context.addError("error", "report.selection.validate.badDateFormat", new Object[] {dateFormat});
                status = false;
            }
        }

        if (store == null && storeId <= 0) {
            context.addError("error", "report.selection.validate.store");
            status = false;
        }

        log.debug("STATUS: " + status);
        return status;
    }

    /**
     * @return list of stores available for this user
     */
    public List getStoreList() {
        return storeList;
    }

    /**
     * @param storeList
     */
    public void setStoreList(List storeList) {
        this.storeList = storeList;
    }

    /**
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return markup or markdown
     */
    public String getMark() {
        return mark;
    }

    /**
     * @param mark
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * @return Returns the selectedStore.
     */
    public Store getStore() {
        return store;
    }
    /**
     * @param selectedStore The selectedStore to set.
     */
    public void setStore(Store selectedStore) {
        this.store = selectedStore;
    }
    /**
     * @return Returns the queuedReports.
     */
    public List getQueuedReports() {
        return queuedReports;
    }
    /**
     * @param queuedReports The queuedReports to set.
     */
    public void setQueuedReports(List queuedReports) {
        this.queuedReports = queuedReports;
    }
    /**
     * @return Returns the selectedStoreId.
     */
    public int getStoreId() {
        return storeId;
    }
    /**
     * @param selectedStoreId The selectedStoreId to set.
     */
    public void setStoreId(int selectedStoreId) {
        this.storeId = selectedStoreId;
    }

    /**
     * @return Returns the dateFormat.
     */
    public String getDateFormat() {
        return dateFormat;
    }
    

    /**
     * @param dateFormat The dateFormat to set.
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    /**
     * @return String the current date formatted either using the set date format value
     * or the default if none set
     */
    public String getCurrentDate(){
    	SimpleDateFormat formatter = null;
    	if (dateFormat != null) {
    		formatter = new SimpleDateFormat(dateFormat);
    	} else {
    		formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    	}
    	return formatter.format(new Date());
    }
    
}