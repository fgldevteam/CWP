package com.fgl.cwp.reporter.message;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;
/** 
 * @author jmbarga 
 */
public class UniversalMessageBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int channel;
    private int topic;
    private int type;
    private int flag;
    private String title;
    private String content;
    private Timestamp timestamp;
    private Map<Object,Object> extraMap=new TreeMap<Object,Object>();

    public UniversalMessageBean(int channel,int topic, int type, int flag, String title, String content,Timestamp event_time){
        this.channel=channel;
        this.topic=topic;
        this.type=type;
        this.flag=flag;
        this.title=title;
        this.content=content;
        this.timestamp=event_time;
    }

    public int getChannel() {
        return channel;
    }
    public String getContent() {
        return content;
    }

    public Map getExtraMap() {
        return extraMap;
    }
    public int getFlag() {
        return flag;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public String getTitle() {
        return title;
    }

    public int getTopic() {
        return topic;
    }
    public int getType() {
        return type;
    }
    
    public void attachExtraMessage(Object key, Object obj){
        extraMap.put(key,obj);
    }
    
}
