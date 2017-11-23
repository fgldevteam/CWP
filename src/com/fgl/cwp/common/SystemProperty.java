/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Jul 7, 2005
 */
package com.fgl.cwp.common;

/**
 * Converts System properties to primitive object types.
 * 
 * @author bting
 */
public final class SystemProperty {
    
    /**
     * System property used to control whether the Quartz scheduler service should be started
     */
    public static final String KEY_SYSTEM_PROPERTY_START_SCHEDULER = "cwp.quartz.startScheduler";

    /**
     * Convert the system property to a boolean value
     * @param key
     * @return
     */
    public static Boolean toBooleanValue(String key) {

        String s = System.getProperty(key);
        if (s!=null) {
            s = s.trim();
            if (s.equalsIgnoreCase("true")) {
                return new Boolean(true);
            }
            if (s.equalsIgnoreCase("false")) {
                return new Boolean(false);
            }
            if (s.equals("1")) {
                return new Boolean(true);
            }
            if (s.equals("0")) {
                return new Boolean(false);
            }
            if (s.equalsIgnoreCase("yes")) {
                return new Boolean(true);
            }
            if (s.equalsIgnoreCase("no")) {
                return new Boolean(false);
            }
        }
        return null;
    }
    
    /**
     * convert the system property to a string value
     * @param key
     * @return
     */
    public static String toStringValue(String key) {
        String value = System.getProperty(key);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }
    
    /**
     * Convert the system property to an integer value
     * @param key
     * @return
     */
    public static Integer toIntegerValue(String key) {
        String s = System.getProperty(key);
        if (s!=null) {
            s = s.trim();
            return Integer.valueOf(s);
        }
        return null;
    }
}