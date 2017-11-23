/*
 * Copyright Forzani Group Ltd. 2005
 * Created on May 9, 2005
 */
package com.fgl.cwp.exception;

/**
 * @author bting
 */
public class InsufficientPrivilege extends Exception {
	private static final long serialVersionUID = 1L;
    /**
     * 
     */
    public InsufficientPrivilege() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public InsufficientPrivilege(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public InsufficientPrivilege(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public InsufficientPrivilege(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

}
