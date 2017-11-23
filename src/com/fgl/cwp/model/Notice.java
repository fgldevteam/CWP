package com.fgl.cwp.model;

import javax.activation.MimetypesFileTypeMap;



/**
 * @author Jessica Wong
 * @hibernate.class
 *      table = "notice"
 *      proxy = "com.fgl.cwp.model.Notice"
 * 
 * @hibernate.query 
 *      name = "getNotices"
 *      query = "SELECT    notice
 *               FROM      Notice notice
 *               ORDER BY  notice.name"
 */
public class Notice extends FileAttachment {
    
    private Integer id;
    private String name;
    private String description;
    private String fileName;
    private String username;
    
    /**
     * Is this a new notice? (i.e. has not been saved to the database yet)
     * @return boolean
     */
    public boolean isNew() {
        return id == null ? true : false;
    }
    
    
    /**
     * Return the mime type for the file.
     * @return
     */
    public String getContentType() {
        if (fileName != null && !fileName.trim().equals("")) {
            MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap();
            return mimeMap.getContentType(fileName);
        } 
        return "";
    }
    
    /**
     * @return
     * 
     * @hibernate.id 
     *      column = "id"
     *      generator-class = "native"
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "name"
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "description"
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "document"
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * 
     * @return
     * 
     * @hibernate.property
     * 		column = "username"
     */
    public String getUsername(){
    	return this.username;
    }

    /**
     * @param username
     */
    public void setUsername(String name){
    	this.username=name;
    }
}
