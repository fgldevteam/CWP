package com.fgl.cwp.reporter.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jmbarga 
 */
public class ReporterController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ReporterController.class);
	private static String PROPERTY_FILE = "reporter.properties";
	private static ReporterController _instance = null;

	private ReporterController() {/***/}
	
	public static ReporterController getInstance()  {
		if( _instance == null){
			synchronized(ReporterController.class){
				_instance = new ReporterController();
				if( _instance == null){
					synchronized(ReporterController.class){
						_instance = new ReporterController();
					}
				}
				_instance.initialize();
			}			
		}
		return _instance;
	}
	
	private void initialize()  {
		log.info("=====================   Initializing the Reporter Property File   ====================");
		try {
			readConfig();		
		} catch (IOException ioe) {
			log.error("Failed to read configuration file", ioe);			
		} catch (URISyntaxException use) {
			log.error("Failed to read configuration file", use);			
		} catch(Exception e){
			log.error("Exception : "+e);
		}
		log.info("====================    End of the Reporter Property file Initialization    =====================");
	}

	private void readConfig() throws IOException, URISyntaxException {		
		Properties props = new Properties();
		InputStream is = null;
		try {		
			is = ReporterController.class.getResourceAsStream(PROPERTY_FILE);
			props.load(is);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				log.error("IOException : " + ioe);
			}
		}
		// Load the configurations as system properties
		Set keys = props.keySet();
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = props.getProperty(key);
			System.setProperty(key, value);			
		}		
	}	
}
