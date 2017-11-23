package com.fgl.cwp.reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.reporter.bean.TaskBean;
import com.fgl.cwp.reporter.message.IUniversalMessageListener;
import com.fgl.cwp.reporter.message.ReporterMessageHelper;
import com.fgl.cwp.reporter.message.UniversalMessageBean;
import com.fgl.cwp.reporter.message.UniversalMessageBeanConstant;
import com.fgl.cwp.model.User;
import com.fgl.cwp.reporter.resource.TaskRegistry;
/**
 * @author jmbarga
 */
public class ReporterManager implements IUniversalMessageListener {
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ReporterManager.class);

	private class ReporterWrap {
		private int id;
		private Reporter reporter;
		private String currentTaskKey;
		private IUniversalMessageListener listener;

		public ReporterWrap(int id, Reporter reporter,
				IUniversalMessageListener listener) {
			this.id = id;
			this.reporter = reporter;
			this.listener = listener;
			currentTaskKey = "-1";
		}

		public int getID() {
			return id;
		}

		public Reporter getReporter() {
			return reporter;
		}

		public void addTask(String task_key) {
			TaskBean task = TaskEngine.getInstance().getTaskBean(task_key);
			String resource_key; 
			ReporterType report_type; 										
			User user; 
			synchronized (task) {
				resource_key = task.getResourceKey();
				report_type = task.getReporterType();
				user = task.getUser();
			}
			Report report = TaskRegistry.getInstance().toReportView(
					resource_key);
			try {
				reporter.userReporter(report_type, report, user, task.getTaskKey(), listener);
			} catch (Exception e) {
				log.error("Exception : " + e);
			}
		}

		public String getCurrentTask() {
			synchronized (currentTaskKey) {
				return currentTaskKey;
			}
		}

		public void setCurrentTask(String task_key) {
			synchronized (currentTaskKey) {
				currentTaskKey = task_key;
			}
		}

	}

	private static ReporterManager _managerInstance = null;
	private List<IUniversalMessageListener> listenerList = new ArrayList<IUniversalMessageListener>();
	private List<ReporterWrap> wrapperList = new ArrayList<ReporterWrap>();

	private Map<String, Integer> taskwrapperCacheMap = new TreeMap<String, Integer>();

	// private IUniversalMessageListener systemListener;

	private ReporterManager() {
		/** We don't want an instantiation */
	}

	/** We have to do the double checking locking */
	static public ReporterManager getInstance() {
		if (_managerInstance == null) {
			synchronized (ReporterManager.class) {
				_managerInstance = new ReporterManager();
				if (_managerInstance == null) {
					synchronized (ReporterManager.class) {
						_managerInstance = new ReporterManager();
					}
				}
				_managerInstance.initialize();
			}
		}
		return _managerInstance;
	}

	private void initialize() {
		// put here whatever you want to initialize
	}

	public void addListener(IUniversalMessageListener listener) {
		listenerList.add(listener);
	}

	public void addSystemListener(IUniversalMessageListener listener) {
		// systemListener = listener; this will be used if we ever want to extend the app
	}

	public void removeListener(IUniversalMessageListener listener) {
		int i = listenerList.indexOf(listener);
		if (i != -1)
			listenerList.remove(i);
	}

	public int put(String task_key) {
		sendArriveMessage(task_key);
		ReporterWrap wrapper = null;
		try {
			wrapper = getWrapper(task_key);
		} catch (Exception e) {

		}
		int wrapper_id = wrapper.getID();
		wrapper.addTask(task_key);
		return wrapper_id;
	}

	private ReporterWrap getWrapper(User user) {
		ReporterFactory rf = ReporterFactory.getInstance();
		Reporter rp = null;
		try {
			rp = rf.getReporter(user);
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
		synchronized (wrapperList) {
			for (int i = 0; i < wrapperList.size(); i++) {
				if (((ReporterWrap) wrapperList.get(i)).getReporter().equals(rp)) {
					return (ReporterWrap) wrapperList.get(i);
				}
			}
			wrapperList.add(new ReporterWrap(wrapperList.size(), rp, this));
			return (ReporterWrap) wrapperList.get(wrapperList.size() - 1);
		}
	}

	private ReporterWrap getWrapper(String task_key) {
		TaskBean task_bean = TaskEngine.getInstance().getTaskBean(task_key);
		if (task_bean == null)
			return null;
		if (taskwrapperCacheMap.get(task_key) != null)
			return (ReporterWrap) wrapperList
					.get(((Integer) taskwrapperCacheMap.get(task_key)).intValue());
		else
			return getWrapper(task_bean.getUser());
	}	

	private void sendArriveMessage(String task_key) {
		this.notifyMessage(ReporterMessageHelper.buildNotifyBean(task_key,
				UniversalMessageBeanConstant.REPORT_TOPIC_TASK, "", ""));
	}

	public void notifyMessage(UniversalMessageBean msg) {
		for (int i = 0; i < listenerList.size(); i++) {
			((IUniversalMessageListener) listenerList.get(i)).notifyMessage(msg);
		}
		// systemListener.notifyMessage(msg);
	}
}
