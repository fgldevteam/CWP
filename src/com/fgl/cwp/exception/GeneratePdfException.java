package com.fgl.cwp.exception;
/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 16, 2005
 */

/**
 * Exception if there are problems generating a pdf file.
 * @author bting
 */
public class GeneratePdfException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public GeneratePdfException() {
        super();
    }

    /**
     * @param arg0
     */
    public GeneratePdfException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public GeneratePdfException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public GeneratePdfException(Throwable arg0) {
        super(arg0);
    }

}
