/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Apr 18, 2005
 */
package com.fgl.cwp.exception;

import javax.servlet.ServletException;

/**
 * @author bting
 */
public class InvalidDirectoryException extends ServletException {
	private static final long serialVersionUID = 1L;	
    /**
     * 
     */
    public InvalidDirectoryException() {
        super();
    }

    /**
     * @param arg0
     */
    public InvalidDirectoryException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public InvalidDirectoryException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public InvalidDirectoryException(Throwable arg0) {
        super(arg0);
    }

}
