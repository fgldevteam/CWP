package com.fgl.cwp.reporter;

import java.io.Serializable;
/**
 * @author jmbarga
 */
public interface ReportInterface extends Serializable {
	public static final long serialVersionUID = 1L;
	
	 public void doReport();
}
