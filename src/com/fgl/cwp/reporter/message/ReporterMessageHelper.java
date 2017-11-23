package com.fgl.cwp.reporter.message;

import java.io.Serializable;
/** 
 * @author jmbarga 
 */
public class ReporterMessageHelper implements Serializable {
	private static final long serialVersionUID=1L;
	
	public static UniversalMessageBean buildNotifyBean(String task_key,int flag,String title,String content){
		UniversalMessageBean bean=new UniversalMessageBean(
				UniversalMessageBeanConstant.CHANNEL_PDFREPORTER,
                UniversalMessageBeanConstant.REPORT_TOPIC_TASK,
                1,
                1,
                "",
                "",
                null
				);	
		return bean;
	}
}
