package com.fgl.cwp.common;

/**
 * @author Jessica Wong
 */
public class Constants {

    /** do not construct this class! */
    private Constants() {
        //empty constructor
        // DO NOT CONSTRUCT!
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
     * Comment for<code>SESSION-TIMEOUT</code>     
     */
    public static final String SESSION_TIMEOUT = "session-timeout";
    
    /**
     * Comment for <code>INSUFFICIENT_PRIVILEGE</code>
     */
    public static final String INSUFFICIENT_PRIVILEGE = "insufficentPrivilege";
    
    /**
     * Comment for <code>ERROR</code>
     */
    public static final String ERROR = "error";

    /**
     * Comment for <code>DOCUMENT</code>
     */
    public static final String DOCUMENT = "documents";
    /**
     * Comment for <code>TROUBLESHOOT</code>
     */
    public static final String TROUBLESHOOT = "troubleshoot";

    /**
     * Comment for <code>HTML_TABLE_SIZE</code>
     */
    public static final int HTML_TABLE_SIZE = 10;
    /**
     * Comment for <code>MAX_RESULTS</code>
     */
    public static final int MAX_RESULTS = 200;

    /**
     * Comment for <code>USER</code>
     */
    public static final String USER = "user";
    /**
     * Comment for <code>STORE</code>
     */
    public static final String STORE = "loginStore";

    /**
     * Comment for <code>ITEM_PRICE_CHANGE_REPORT</code>
     */
    public static final String ITEM_PRICE_CHANGE_REPORT = "ItemPriceChange";
    /**
     * Comment for <code>PACKAGE_PRICE_CHANGE_REPORT</code>
     */
    public static final String PACKAGE_PRICE_CHANGE_REPORT = "PackagePriceChange";
    /**
     * Comment for <code>ELEARNING_REPORT</code>
     */
    public static final String ELEARNING_REPORT = "ELearningReport";
    /**
     * Document type
     */
    public static final String DOCUMENT_TYPE = "D";
    /**
     * Document header type
     */
    public static final String HEADER_TYPE = "H";    
    /**
     * Document internal/external link type
     */
    public static final String LINK_TYPE = "L";
    
    /**
     * Maximum file attachment size (15 MB max)
     */
    public static final int MAX_FILE_SIZE = 15*1024*1024; // in bytes
    
    /**
     * Session key used to store the menu permissions adapter instance
     */
    public static final String KEY_CUSTOM_PERMISSIONS_ADAPTER = "customPermissionsAdapter";
    
    /**
     * Used to identify the Group for orphan documents
     */
    public static final int ORPHAN_GROUP = -1;
}