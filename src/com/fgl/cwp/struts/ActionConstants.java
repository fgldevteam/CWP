package com.fgl.cwp.struts;

/**
 * @author Jessica Wong
 */
public class ActionConstants {
	/** do not construct this class! */
	private ActionConstants() {
	    //empty constructor
	    // do NOT construct
	}

	/**
	 * Comment for <code>SUCCESS</code>
	 */
	public static final String SUCCESS = "success";
	/**
	 * Comment for <code>FAILURE</code>
	 */
	public static final String FAILURE = "failure";
    
    /**
     * Request attribute key used to store a dynamic action forward
     */
    public static final String DYNAMIC_FORWARD = "dynamicFwd";

}
