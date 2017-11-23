package com.fgl.cwp.reporter.message;

import java.io.Serializable;
/** 
 * @author jmbarga 
 */
public interface IUniversalMessageListener extends Serializable {
	public void notifyMessage(UniversalMessageBean msg);
}
