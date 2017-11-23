package com.fgl.cwp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.fgl.cwp.persistence.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.hibernate.HibernateException;

public class SumTotalInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(SumTotalInitServlet.class);

	public void init() throws ServletException {
		super.init();

		Runnable initThread = new Runnable() {
			private boolean interrupted = false;

			public void run() {
				while (!interrupted) {
					log.debug("Running Summtotal Initialization code");					
					try{
						SessionManager.openSession();                       
                        log.debug("Finished Summtotal Initialization");
					}catch(HibernateException he){
                    }finally{
                    	try{
                    		SessionManager.closeSession();
                    	}catch(HibernateException he){
                    		
                    	}
                    }
					try {
						Thread.sleep(600000); // redo the data fetch every 10 minutes to make sure the data is there
					} catch (InterruptedException e) {
						interrupted = true;
					}
				}
			}
		};
		Thread thread = new Thread(initThread);
		thread.setDaemon(true);
		thread.start();
	}

}
