package com.fgl.cwp.reporter;

import java.io.Serializable;
/**
 * @author jmbarga
 */
public class ReporterCenter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static TaskEngine _engine=TaskEngine.getInstance();

	public static TaskEngine getTaskEngine(){	
	    return _engine;
    }
}
