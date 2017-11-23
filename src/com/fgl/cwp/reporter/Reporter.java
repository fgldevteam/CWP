package com.fgl.cwp.reporter;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.reporter.message.IUniversalMessageListener;
import com.fgl.cwp.model.User;
/**
 * @author jmbarga
 */
public class Reporter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(Reporter.class);
	
	public final void userReporter(ReporterType type,Report report, User user,String task_key, IUniversalMessageListener listener ){
		getReporter(type,report, user,task_key, listener);
	}
	
	private final void getReporter(ReporterType type,Report report,User user, String task_key,IUniversalMessageListener listener ){
		AbstractReporter reporter=null;
		Class reporterClass=null;
		Constructor construct=null;
		Class[] classes=null;
		Object[] params=null;
		
		try{			
			classes = new Class[] {Report.class,
					               User.class,
					               Reporter.class,
					               String.class,
					               IUniversalMessageListener.class};
			params = new Object[]{report,
					              user,
					              this,
					              task_key,
					              listener};
			
			reporterClass = Class.forName( type.getReportTypeName() );
			construct = reporterClass.getDeclaredConstructor(classes);
			reporter = (AbstractReporter)construct.newInstance(params);			
			doReport( reporter );				
		}catch(ClassNotFoundException  cnfe){
			log.error("ClassNotFoundException :"+cnfe);
		}catch(IllegalAccessException iae){
			log.error("IllegalAccessException : "+iae);
		}catch(InstantiationException ie){
			log.error("InstantiationException :"+ie);
		}catch(InvocationTargetException ite){
			log.error("InvocationTargetException : "+ite);
		}catch(NoSuchMethodException nsme){
			log.error("NoSuchMethodException : "+nsme);
		}
	}
	
	private void doReport(AbstractReporter reporter){
		Thread thread = new Thread( (Runnable) reporter );
		thread.setPriority( 1 );
		thread.start();
	}
}
