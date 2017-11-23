package com.fgl.cwp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.activation.MimetypesFileTypeMap;

/**
 * @author jwong
 * @hibernate.class 
 *      table = "document"
 *      proxy = "com.fgl.cwp.model.Document"
 *      dynamic-insert = "true"
 *      dynamic-update = "true"
 * 
 * @hibernate.query 
 *      name="getFeaturedDocuments"
 *      query = "SELECT    document FROM Document document
 *               WHERE     document.expiryDate >= getDate()
 *               ORDER BY  document.title"
 * 
 * @hibernate.query
 *      name ="getDocuments"
 *      query = "SELECT    document
 *               FROM      Document document
 *               WHERE     document.parentDocument = 0
 *               AND       document.category = :category
 *               AND       document.type = 'H'
 *               ORDER BY  document.title"
 * 
 * @hibernate.query
 *      name="getDocumentsSearch"
 *      query = "SELECT    document FROM Document document
 *               WHERE     document.category = :category
 *               AND       document.type in ('D', 'F', 'L')
 *               AND       document.title like :title
 *               AND       document.dateModified >= :modifiedDate
 *               ORDER BY  document.title"
 *               
 * @hibernate.query
 *      name="getAllDocuments"
 *      query = "SELECT    document
 *               FROM      Document document
 *               WHERE     document.type in ('D','F','L')
 *               ORDER BY  document.title"
 *               
 * @hibernate.query
 *      name="getAllHeadersByCategory"
 *      query = "SELECT    document
 *               FROM      Document document
 *               WHERE     document.type = 'H'
 *               AND       document.category = :category
 *               ORDER BY  document.title"
 *               
 * @hibernate.query
 *      name="getAllHeaders"
 *      query = "SELECT    document
 *               FROM      Document document
 *               WHERE     document.type = 'H'
 *               ORDER BY  document.title"
 *               
 * @hibernate.query
 *      name="getAllHeadersExcept"
 *      query = "SELECT    document
 *               FROM      Document document
 *               WHERE     document.type = 'H'
 *               AND       document.category = :category
 *               AND       document.id not in (:documentIdList)
 *               ORDER BY  document.title"
 *               
 * @hibernate.query
 *      name="getDocumentById"
 *      query = "SELECT document
 *               FROM Document document
 *               WHERE document.id = :id"
 *               
 * @hibernate.query
 *      name="findDuplicateDocument"
 *      query = "SELECT document
 *               FROM Document document
 *               WHERE document.title = :title
 *               AND   document.id != :id
 *               AND   document.parentDocument = :parentId
 *               AND   document.type = :type
 *               AND   document.category = :category"
 */
public class Document extends FileAttachment implements Serializable, Comparable{
    
    private static final long serialVersionUID = -4323594638234600011L;
    
    private Integer id;
    private String title;
    private String fileName;
    private Integer parentDocument;
    private Set childrenHeaders = new TreeSet();
    private Set childrenDocuments = new TreeSet();
    private String type;
    private String emailAddress;
    private Date dateModified;
    private Date expiryDate;
    private String category;
    private String username;
    
    /**
     * Is this document new?
     * @return
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
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("\nid: " + id);
        buf.append("\ntitle: " + title);
        buf.append("\nfileName: " + fileName);
        buf.append("\nparentDocument: " + parentDocument);
        buf.append("\ntype: " + type);
        buf.append("\nemailAddress: " + emailAddress);
        buf.append("\nexpiryDate: " + expiryDate);
        buf.append("\ncategory: " + category);
        return buf.toString();
    }
    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "title"
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param fileName
     */
    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * @return
     * 
     * @hibernate.property
     *      column = "filename"
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * @param parentDocument
     */
    public void setParentDocument(Integer parentDocument) {
        this.parentDocument = parentDocument;
    }
    
    /**
     * @return
     * 
     * @hibernate.property
     *      column = "parentdoc"
     * 
     * TODO: this looks like it should be an object reference not an identifier value
     */
    public Integer getParentDocument() {
        return parentDocument;
    }
    
    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return
     * 
     * @hibernate.property
     *      column = "type"
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    /**
     * @return
     * 
     * @hibernate.property
     *      column = "emailto"
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    
    /**
     * @param dateModified
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
    
    /**
     * @return
     * 
     * @hibernate.timestamp
     *      column = "mod_date"
     */
    public Date getDateModified() {
        return dateModified;
    }
    
    /**
     * @param expiryDate
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "expiry_date"
     */
    public Date getExpiryDate() {
        return expiryDate;
    }
    
    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * @return
     * 
     * @hibernate.property 
     *      column = "category"
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * @return
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
     * @hibernate.set
     *      lazy = "true"
     *      order-by = "title"
     *      where = " type = 'H' "
     * @hibernate.collection-key 
     *      column = "parentdoc"
     * @hibernate.collection-one-to-many 
     *      class = "com.fgl.cwp.model.Document"
     */
    public Set getChildrenHeaders() {
        return childrenHeaders;
    }
    
    /**
     * @param childrenHeaders
     */
    public void setChildrenHeaders(Set childrenHeaders) {
        this.childrenHeaders = childrenHeaders;
    }
    /**
     * @return
     * 
     * @hibernate.set
     *      lazy = "true"
     *      order-by = "title"
     *      where = " type in ('D','F','L') "
     * @hibernate.collection-key 
     *      column = "parentdoc"
     * @hibernate.collection-one-to-many 
     *      class = "com.fgl.cwp.model.Document"
     */
    public Set getChildrenDocuments() {
        return childrenDocuments;
    }
    
    /**
     * @param childrenDocuments
     */
    public void setChildrenDocuments(Set childrenDocuments) {
        this.childrenDocuments = childrenDocuments;
    }

    /**
     * @return
     * @hibernate.property
     * 		column = "username"
     */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean equals(Object o){
		if(o instanceof Document && this.id.equals(((Document)o).getId()) ){
			return true;
		}
		return false;
	}

	public int compareTo(Object o) {
		int result = 0;
		if (o instanceof Document){
			result = this.title.trim().compareTo(((Document)o).getTitle().trim());
			if (result == 0) {
				result = this.id.compareTo(((Document)o).getId());
			}
		}
		return result;
	}
	
	public String getEscapedTitle(){
		if(title != null){
			String escapedTitle = title;
			if (title.indexOf("'") != -1){
				escapedTitle = title.replaceAll("'", "&#146;");
			}
			if (title.indexOf('"') != -1){
				escapedTitle = title.replaceAll("\"", "&#148;");
			}
			return escapedTitle;
		} else {
			return title;
		}
	}
}