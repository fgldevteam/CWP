package com.fgl.cwp.struts;

//todo: changed NestedRuntimeException to RuntimeException without looking into consequences!!!!

/**
 * This exception is thrown internally by BeanAction and
 * can also be used by bean action methods as a general
 * or base exception.
 * <p/>
 * Date: Mar 13, 2004 8:17:00 PM
 * @author Clinton Begin
 */
public class BeanActionException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * BeanActionException
     *
     */
	public BeanActionException() {
		super();
	}
    /**
     * BeanActionException
     * @param s
     */
	public BeanActionException(String s) {
		super(s);
	}
    /**
     * BeanActionException
     * @param throwable
     */
	public BeanActionException(Throwable throwable) {
		super(throwable);
	}
	/**
     * BeanActionException
     * @param s
     * @param throwable
	 */
	public BeanActionException(String s, Throwable throwable) {
		super(s, throwable);
	}

}
