package com.fgl.cwp.reporter;

import java.io.Serializable;
import java.sql.Timestamp;
import com.fgl.cwp.reporter.message.UniversalMessageBean;

/**
 * @author jmbarga
 */
public class ReportMessageHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static UniversalMessageBean buildNotifyBean(String task_key,int flag,String title,String content){
        UniversalMessageBean bean=new UniversalMessageBean(
                1,
                1,
                1,
                flag,
                title,
                content,
                new Timestamp(System.currentTimeMillis()));       
        return bean;
    }
    public static UniversalMessageBean buildWarningBean(String task_key,String title,String content ){
        UniversalMessageBean bean=new UniversalMessageBean(
                1,
                1,
                1,
                -1,title,content,new Timestamp(System.currentTimeMillis()));        
        return bean;
    }

}
