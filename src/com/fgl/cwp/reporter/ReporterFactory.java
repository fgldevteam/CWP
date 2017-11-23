package com.fgl.cwp.reporter;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.model.User;
/**
 * @author jmbarga
 */

public class ReporterFactory implements Serializable{
	private static final long serialVersionUID=1L;
	private static Log log  = LogFactory.getLog(ReporterFactory.class);
	
	private static ReporterFactory _singleton = new ReporterFactory();	
	private Map reporters = null;

	private ReporterFactory() {
		this.reporters = new TreeMap();
	}

	/**Double locking checking*/
	public synchronized static ReporterFactory getInstance() {
		log.debug("");
		if( _singleton==null){
			synchronized(ReporterFactory.class){
				_singleton = new ReporterFactory();
				if( _singleton==null){
					synchronized(ReporterFactory.class){
						_singleton = new ReporterFactory();
					}
				}
			}
		}
		return _singleton;
	}

	public Reporter getReporter(User user){
		//we are not going to use the reporter type based on the user for now
		return new Reporter();
	}
	public Map getReporters(){
		return this.reporters;
	}
}
