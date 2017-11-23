package com.fgl.cwp.reporter;

import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.reporter.bean.TaskBean;
import com.fgl.cwp.reporter.message.IUniversalMessageListener;
import com.fgl.cwp.reporter.message.UniversalMessageBean;
import com.fgl.cwp.reporter.resource.TaskRegistry;
import com.fgl.cwp.model.User;
/**
 * @author jmbarga
 */
public class TaskEngine implements IUniversalMessageListener {
	private static final long serialVersionUID = 1L;
	
	private static Log log  = LogFactory.getLog(TaskEngine.class);
	public static int TYPE_REPORT_OBJ=0;
	public static int TYPE_REPORT_NAME=1;


	private static TaskEngine _centerInstance=null;
    private static Map<String,TaskBean> taskMap=new TreeMap<String,TaskBean>();
    ReporterManager reporterManager=null;
    private TaskEngine(){}

    public static TaskEngine getInstance(){
    	log.debug("");
    	if(_centerInstance==null){
    		synchronized(TaskEngine.class){
    			_centerInstance = new TaskEngine();
    			if(_centerInstance==null){
    				synchronized(TaskEngine.class){
    					_centerInstance=new TaskEngine();
    				}
    			}
    			_centerInstance.initializeQueue();
    		}    		
    	}
    	return _centerInstance;
    }
    
    private void initializeQueue(){
        if(reporterManager==null){
            reporterManager=ReporterManager.getInstance();
            reporterManager.addListener(this);
        }
    }

    public String addTask(Report  report,int resource_type,ReporterType type, User user, int task_priority){
    	String task_key = ""+report.getId();
    	String resource_key=TaskRegistry.getInstance().register(report,report.getName(),resource_type);
    	TaskBean taskBean = new TaskBean(task_key,resource_key,user,type,task_priority);
    	addTask(taskBean);
        return task_key;
    }
    private void addTask(TaskBean task_bean){
        taskMap.put(task_bean.getTaskKey(),task_bean);    	
        reporterManager.put(task_bean.getTaskKey());
    }

    protected void removeTask(String task_key){
    	TaskBean task = (TaskBean)taskMap.get(task_key);
    	if( task==null)return;
    	synchronized(taskMap){
            taskMap.remove(task_key);
        }    	
    }
    
    protected TaskBean getTaskBean(String task_key){
    	return (TaskBean)taskMap.get(task_key);
    }
    
	/**we will use this to remove the task from the taskMap*/
	public void notifyMessage(UniversalMessageBean msg) {
	}
}
