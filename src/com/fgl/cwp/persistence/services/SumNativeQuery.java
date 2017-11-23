package com.fgl.cwp.persistence.services;

import java.io.Serializable;
import java.util.regex.Pattern;
/**
 * 
 * @author jmbarga
 *
 */

public class SumNativeQuery implements Serializable {
	private static final long serialVersionUID = 1L;
    private String syntax;
    private String name;
    
    /**
     * Constructor
     * @param name
     * @param syntax
     */
    public SumNativeQuery(String name, String syntax){
        this.name = name;
        this.syntax = syntax;
    }
    
    /**
     * @return syntax
     */
    public String getSyntax(){
        return this.syntax;
    }
    
    /**
     * Replaces all occurrences of #{variable} with the supplied replacement value 
     * 
     * @param variable
     * @param value
     * @return a new instance of the query with the substituted values
     */
    public SumNativeQuery replace( String variable, String value ){
        Pattern pattern = Pattern.compile("#\\{" + variable + "\\}");
        return new SumNativeQuery(this.name, pattern.matcher(this.syntax).replaceAll(value));
    }
    
    /**
     * Replaces all occurrences of :variable with the supplied replacement value surrounded by single quotes
     * 
     * @param variable
     * @param value
     * @return a new instance of the query with the substituted values
     */
    public SumNativeQuery setString( String variable, String value ){
        Pattern pattern = Pattern.compile(":" + variable);
        return new SumNativeQuery(this.name, pattern.matcher(this.syntax).replaceAll("'" + value + "'"));
    }
    
    public SumNativeQuery setInternString( String variable, String value ){
        Pattern pattern = Pattern.compile(variable);
        return new SumNativeQuery(this.name, pattern.matcher(this.syntax).replaceAll( value ));
    }
    
    /**
     * Replaces all occurrences of :variable with the supplied replacement value
     * 
     * @param variable
     * @param value
     * @return a new instance of the query with the substituted values
     */
    public SumNativeQuery setInt( String variable, int value ){
        Pattern pattern = Pattern.compile(":" + variable);
        return new SumNativeQuery(this.name, pattern.matcher(this.syntax).replaceAll(Integer.toString(value)));
    }
}
