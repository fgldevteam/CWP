package com.fgl.cwp.reporter.resource;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.reporter.TaskEngine;
/**
 * 
 * @author jmbarga
 *
 */
public class TaskRegistry implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(TaskRegistry.class);
	private static int nextResourceID = 0;
	private static TaskRegistry _centerInstance = null;

	private TaskRegistry() {}

	public static TaskRegistry getInstance() {
		if (_centerInstance == null) {
			synchronized(TaskRegistry.class){
				_centerInstance = new TaskRegistry();
				if(_centerInstance==null){
					synchronized(TaskRegistry.class){
						_centerInstance = new TaskRegistry();
					}
				}
			}
		}
		log.debug("ResourceCenter .....");
		return _centerInstance;
	}

	private Map<String,Object> repository = new TreeMap<String,Object>();
	private Map<String,String> identifierMap = new TreeMap<String,String>();

	public String register(Report obj, String name, int resourceType) {
		String key;
		if (resourceType == TaskEngine.TYPE_REPORT_NAME) {
			key = getNextResourceKey();
			repository.put(key, obj);
			identifierMap.put(key, name);
			return key;
		} else if (resourceType == TaskEngine.TYPE_REPORT_OBJ) {
			key = getNextResourceKey();
			repository.put(key, ((Report) obj));
			identifierMap.put(key, name);
			return key;
		} else	return null;
	}

	/** Not used at this point, but could be if we do have an admin page */
	/*public Object getResource(String resource_key) {
		return repository.get(resource_key);
	}*/

	public Report toReportView(String resource_key) {
		Report report = (Report)repository.get(resource_key);
		if( report != null) return report;
		else return null;
	}

	/**for now we will let it null until we decide to extend*/
	/*public Report getResourceIdentifier(String resource_key) {		
		return null;
	}*/

	/**Not use at this point but can be if we do have an admin page */
	/*public void removeResource(String resource_key) {
		repository.remove(resource_key);
		identifierMap.remove(resource_key);
	}*/

	private String getNextResourceKey() {
		String id = "" + nextResourceID;
		nextResourceID++;
		return id;
	}

}
