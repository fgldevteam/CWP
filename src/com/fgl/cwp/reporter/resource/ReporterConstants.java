package com.fgl.cwp.reporter.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 * 
 * @author jmbarga
 *
 */
public class ReporterConstants {

	 /**
     * Reporter configurations
     */
    public static final String CONFIG_FILE = "/reporter.properties";
    /**
     * Key to configure timer task interval
     */
    public static final String KEY_THREAD_SLEEP_TIME = "thread.sleep.time";
    /**
     * Key to configure email errors
     */
    public static final String KEY_EMAIL_ERRORS = "email.errors";
    /**
     * Key to configure email errors to email address
     */
    public static final String KEY_EMAIL_ERRORS_TO = "email.errors.to";
    /**
     * Key to configure email errors from email address
     */
    public static final String KEY_EMAIL_ERRORS_FROM = "email.errors.from";
    /**
     * Key to configure emailing a report
     */
    public static final String KEY_EMAIL_REPORT = "email.report";
    /**
     * Key to configure emailing a report from email address
     */
    public static final String KEY_EMAIL_REPORT_FROM = "email.report.from";
    /**
     * Key to configure emailing a report java mail body
     */
    public static final String KEY_EMAIL_REPORT_BODY = "email.report.body";
    /**
     * Key to configure connection timeout
     */
    public static final String KEY_CONNECTION_TIMEOUT = "connection.timeout";
    /**
     * Key to configure the pdf server
     */
    public static final String KEY_PDF_SERVER = "pdf.server";
    /**
     * Key to configure the pdf server protocol
     */
    public static final String KEY_PDF_SERVER_PROTOCOL = "pdf.server.protocol";
    /**
     * Key to configure the pdf server port
     */
    public static final String KEY_PDF_SERVER_PORT = "pdf.server.port";
    /**
     * Key to configure the directory where pdf files are stored
     */
    public static final String KEY_PDF_DIRECTORY = "pdf.dir";
    /**
     * Key to configure the virtual directory of where the pdf files are served from (http url)
     */
    public static final String KEY_PDF_VIRTUAL_DIRECTORY = "pdf.virtual.dir";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE = "pdf.dir.mapped.drive";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE_DEVICE = "pdf.dir.mapped.drive.device";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE_COMPUTER = "pdf.dir.mapped.drive.computer";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE_SHARE = "pdf.dir.mapped.drive.share";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE_DOMAIN = "pdf.dir.mapped.drive.domain";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE_USER = "pdf.dir.mapped.drive.user";
    public static final String KEY_PDF_SERVER_MAPPED_DRIVE_PASSWORD = "pdf.dir.mapped.drive.password";
    /**
     * Key to configure the big faceless report generator license
     */
    public static final String KEY_BIG_FACELESS_LICENSE = "license";

    
    /**
     * Date format used for appending a unique value to end of pdf file
     */
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
    
    
    /**
     * Token replacement prefix value
     */
    public static final String TOKEN_PREFIX  ="${";
    /**
     * Token replacement suffix value
     */
    public static final String TOKEN_SUFFIX = "}";
    /**
     * Token used for runtime string substitution
     */
    public static final String TOKEN_FILENAME = TOKEN_PREFIX+"filename"+TOKEN_SUFFIX;
    
    /**
     * Delimeters used to seperate tokenated strings
     */
    public static final String STRING_DELIMETER = ",;";
}
