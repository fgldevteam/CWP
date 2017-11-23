package com.fgl.cwp.reporter;

import com.fgl.cwp.reporter.resource.ReporterConstants;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.common.SystemProperty;
import com.fgl.cwp.model.User;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.reporter.message.IUniversalMessageListener;
import com.fgl.cwp.persistence.SessionManager;
import com.fgl.cwp.persistence.services.PostalService;


/**
 * @author jmbarga
 */

public abstract class AbstractReporter implements Runnable,ReportInterface  {
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(AbstractReporter.class);
	private User user;
	private Report report;
	protected Reporter reporter;
	protected String task_key;
	protected IUniversalMessageListener listener;
	private Thread currentThread=null;	

	protected AbstractReporter(Report report,
								User user,
								Reporter reporter,
								String task_key,
								IUniversalMessageListener listener){
		this.report=report;
		this.user=user;
		this.reporter = reporter;
		this.task_key=task_key;
		this.listener=listener;
	}
	
	protected Report getReportView(){
		return this.report;
	}
	
	protected User getReportGenerator(){
		return this.user;
	}
	
	protected Thread getCurrentThread(){
		return this.currentThread;
	}
	
	public void run() {
		try{
			currentThread=Thread.currentThread();
			start();
		}catch(OutOfMemoryError oome){
			log.error("OutOfMemoryError : "+oome);
		}catch(Exception e){
			log.error("Exception : "+e);
		}finally{
			try{
				//currentThread.interrupt();
				if( currentThread !=null && currentThread.isAlive()) currentThread=null;
			}catch(SecurityException se){
				log.error("SecurityException : "+se);
			}
		}
	}
	
	private void start() {	
		doReport();	
	}

	protected void emailReport(Report report, File pdfFile) {		
		Boolean doEmail = SystemProperty.toBooleanValue(ReporterConstants.KEY_EMAIL_REPORT);
		if (doEmail.booleanValue()) {			
			String to = report.getEmail();
			String from = SystemProperty.toStringValue(ReporterConstants.KEY_EMAIL_REPORT_FROM);
			from = from.replace(ReporterConstants.TOKEN_PREFIX
					+ ReporterConstants.KEY_PDF_SERVER
					+ ReporterConstants.TOKEN_SUFFIX, SystemProperty.toStringValue(ReporterConstants.KEY_PDF_SERVER));
			String subject = pdfFile.getName();		
			String body = SystemProperty.toStringValue(ReporterConstants.KEY_EMAIL_REPORT_BODY);
			body = body.replace(ReporterConstants.TOKEN_FILENAME, pdfFile.getName());

			if (StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(from)) {
				PostalService.getInstance().sendMessage(new String[] { to },
						from, subject, body, pdfFile);
			} else {
				if (StringUtils.isEmpty(to))
					log.info("could not send email... no 'to' email address defined.");
				else
					log.info("could not send email... no 'from' email address defined.");
			}
		}
	}
	
	protected void saveReport(Report report) {		
		Session session = null;
		Transaction tx = null;
		try {
			synchronized(report){
				session = SessionManager.getSessionFactory().openSession();			
				tx = session.beginTransaction();
				session.saveOrUpdate(report);
				tx.commit();
			}
		
		} catch (HibernateException he) {
			log.error("HibernateException : "+he);
			if (tx != null){
				try{
					tx.rollback();
				}catch(HibernateException e){}
			}
		} finally {
			if (session != null){
				try{
					session.close();
				}catch(HibernateException e){}
			}
		}
	}
	
	protected File getFile(Report report, String timeStamp) {	        
	    StringBuffer filename = new StringBuffer();
	    filename.append(System.getProperty(ReporterConstants.KEY_PDF_DIRECTORY));
	    filename.append(File.separator);
	    filename.append(report.getServer());
	    filename.append(File.separator);
	    filename.append(getFilename(report, timeStamp));	        
	    return new File(filename.toString());
	 }
	 
	protected abstract String getUrl(Report report, String timeStamp);	  
	protected abstract String getFilename(Report report, String timeStamp);
	 
	protected void emailError(Report report, Throwable t) {		
        Boolean doEmail = SystemProperty.toBooleanValue(ReporterConstants.KEY_EMAIL_ERRORS);
        if (doEmail.booleanValue()) {
            String to = SystemProperty.toStringValue(ReporterConstants.KEY_EMAIL_ERRORS_TO);

            String from = SystemProperty.toStringValue(ReporterConstants.KEY_EMAIL_ERRORS_FROM);
            from = from.replace(ReporterConstants.TOKEN_PREFIX+ReporterConstants.KEY_PDF_SERVER+ReporterConstants.TOKEN_SUFFIX,
                    SystemProperty.toStringValue(ReporterConstants.KEY_PDF_SERVER));

            String subject = "Reporter-failed to generate report: " + report.getName();
            StringWriter body = new StringWriter();
            t.printStackTrace(new PrintWriter(body));

            if (StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(from)) {                
                String[] tos = toTokens(to, ReporterConstants.STRING_DELIMETER);
                PostalService.getInstance().sendMessage(tos, from, subject, body.toString());
                
            } else {
                if (StringUtils.isEmpty(to)) log.info("could not send email... no 'to' email address defined.");
                else log.info("could not send email... no 'from' email address defined.");               
            }
        }
    }    
    
    protected String[] toTokens(String str, String delims) {
        StringTokenizer tokenizer = new StringTokenizer(str, delims, false);        
        String[] tos = new String[tokenizer.countTokens()];
        int i=0;
        while (tokenizer.hasMoreTokens()) {
            tos[i++] = tokenizer.nextToken().trim();
        }
        return tos;
    }
}
