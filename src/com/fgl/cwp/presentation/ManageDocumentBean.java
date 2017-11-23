/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Feb 11, 2005
 */
package com.fgl.cwp.presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.model.Document;
import com.fgl.cwp.model.Functionality;
import com.fgl.cwp.model.User;
import com.fgl.cwp.persistence.services.DocumentService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class ManageDocumentBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final int ROOT_PARENT = 0;
    
    // for listing all documents/headers
    private List <Document>documents;
    
    // for editing a document/header
    private Document document;
    private List <Document>headers;
    private String featureExpiryDate;
    private String link;
    private String previousDocType;
    private Integer groupId;
    private Integer subGroupId;
    private Integer categoryId;
    private Integer documentId;
    private Document groupParent;
    private Document subGroupParent;
    private Document categoryParent;
    
    public void reset() {
        if (document != null) {
            document.setAttachment(null);
        }
        link = null;
    }
    
    /**
     * Create a new document/header
     * @return
     * @throws Exception
     */
    public String createDocument() throws Exception {
    	clearSelectionData();
        Utils.checkFunction(Functionality.CREATE_DOCUMENTS);
        
        featureExpiryDate = null;
        
        document = new Document();
        // Set some defaults
        document.setType(Constants.DOCUMENT_TYPE);
        document.setCategory(Constants.DOCUMENT);
        document.setUsername(getUserName());
        buildHeaderList();
        return Constants.SUCCESS;
    }
    
    /**
     * Edit an existing notice.
     * @return String
     * @throws Exception
     */
    public String editDocument() throws Exception {
        Utils.checkFunction(Functionality.EDIT_DOCUMENTS);
        featureExpiryDate = null;
        String result = Constants.FAILURE;
        // have to evaluate the various Id's to retrieve the specific document
        if (groupId != null) {
        	//reset the parent docs
        	groupParent = null;
        	subGroupParent = null;
        	categoryParent = null;
        	//find the specific document
            document = findDocument();
            
            // because the group id, subgroup id, and/or the category id are set
            // by the request in order to find the document in the document collection
            // they may actually represent the selected document to be edited. 
            // In this case, reset the appropriate id and parent
            if (document != null) {
            	//start from bottom up
            	if (this.categoryId != null && this.categoryId != 0){
            		if (this.categoryId.equals(document.getId())) {
            			this.categoryId = 0;
            			this.categoryParent = null;
            		}
            	} else if (this.subGroupId != null && this.subGroupId != 0){
            		if (this.subGroupId.equals(document.getId())) {
            			this.subGroupId = 0;
            			this.subGroupParent = null;
            		}
            	} else if (this.groupId != null && this.groupId != 0){
            		if (this.groupId.equals(document.getId())) {
            			this.groupId = 0;
            			this.groupParent = null;
            		}
            	}
            }
            
            // pre-populate feature expiration date
            if (document != null && document.getExpiryDate() != null) {
                ActionContext ctxt = ActionContext.getActionContext();
                String dateFormatString = ctxt.getResources().getMessage("default.dateformat");
                SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);            
                featureExpiryDate = dateFormat.format(document.getExpiryDate());
            }
            // pre-populate link
            if (document.getType().equals(Constants.LINK_TYPE)) {
                link = document.getFileName();
            }
            previousDocType = document.getType();
            buildHeaderList();
            result = Constants.SUCCESS;
        }
        return result;
    }
    
    /**
     * Load existing documents/headers.
     * @return
     * @throws Exception
     */
    public String loadDocuments() throws Exception {
        Utils.checkFunction(Functionality.VIEW_DOCUMENTS);
        documents = DocumentService.getInstance().getAllDocuments();
        if (documents.isEmpty()) {
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }
    
    public String assignType(){
    	// if type is link, set the link variable
    	if (document != null && document.getFileName() != null){
    		this.link = document.getFileName();
    	}
    	return Constants.SUCCESS;
    }
    
    /**
     * Load headers for a given category.
     * @return
     * @throws Exception
     */
    public String loadHeaders() throws Exception {
    	buildHeaderList();
        //header collection changed, reset the parents
        clearSelectionData();
        return Constants.SUCCESS;
    }
    
    private void buildHeaderList(){
    	if (headers == null || !headers.isEmpty()){
        	headers = new ArrayList<Document>();
        }
        for (Document doc : documents){
        	if (doc.getId() != Constants.ORPHAN_GROUP && 
        			doc.getCategory().equals(document.getCategory())){
        		headers.add(doc);
        	}
        }
    }
    
    /**
     * called when the group is changed
     * @return
     * @throws Exception
     */
    public String assignGroup() throws Exception {
    	//reset the sub group and category ID's
    	clearSubGroupData();
    	clearCategoryData();
    	this.documentId = null;
    	this.groupParent = findDocument();
    	//because the user selected a new group, clear the previously selected 
    	//sub group and category
        return Constants.SUCCESS;
    }
    
    /**
     * called when the sub group is changed
     * @return
     * @throws Exception
     */
    public String assignSubGroup() throws Exception {
    	clearCategoryData();
    	this.documentId = null;
    	if(this.subGroupId != null && this.subGroupId != 0){
    		this.subGroupParent = findDocument();
    	} else {
    		this.subGroupParent = null;
    	}
        return Constants.SUCCESS;
    }
    
    /**
     * called when the category is changed
     * @return
     * @throws Exception
     */
    public String assignCategory() throws Exception {
    	this.documentId = null;
    	if(this.categoryId != null && this.categoryId != 0){
    		this.categoryParent = findDocument();
    	} else {
    		this.categoryParent = null;
    	}
        return Constants.SUCCESS;
    }
    
    /**
     * Save a document.
     * @return
     * @throws Exception
     */
    public String saveDocument() throws Exception {
        Utils.checkFunction(Functionality.EDIT_DOCUMENTS);
        if( !validateDocument(document) ){
        	ActionContext.getActionContext().addMessage("error", "status.invalid.document.name");
        	return Constants.FAILURE;
        }
        //set the parent for the document
        assignParentDocument();
        
        if (StringUtils.isNotEmpty(link)) {
            document.setFileName(link); 
            if (document.getAttachment() != null) {
            	document.getAttachment().destroy();
            }
        }
        
        // Before the user made changes, if the document was an external link,
        // and the user changes it to a header or document type, we must
        // clear the filename
        if (previousDocType != null
                && previousDocType.equals(Constants.LINK_TYPE)
                && !document.getType().equals(Constants.LINK_TYPE)) {
            document.setFileName(null);
        }
        
        if (validateSave()) {
        	document.setUsername(getUserName());
            if (DocumentService.getInstance().saveDocument(document)) {
                ActionContext.getActionContext().addMessage("status", "status.writeToDB.success");
                return Constants.SUCCESS;
            }
        }
        return Constants.FAILURE;
    }
    
    /**
     * Validate a document before saving.
     * @return
     */
    private boolean validateSave() throws Exception {
        boolean success = true;
        if (document != null) {
            ActionContext ctxt = ActionContext.getActionContext();
            
            // title is required
            if (StringUtils.isEmpty(document.getTitle())) {
                ctxt.addError("error", "admin.document.edit.error.title.required");
                success = false;
            }
            // unique title for given place in the hierachy is required
            Integer docId = document.getId();
            if (docId == null) {
            	docId = -1;
            }
            String docType = "document";
            Integer parentId = null;
            String parentTitle = null;
            if (categoryParent != null) {
            	parentTitle = "<b>"+categoryParent.getTitle()+"</b> category";
            	parentId = categoryParent.getId();
            } else if (subGroupParent != null) {
            	if ("H".equals(document.getType())) {
            		docType = "category";
            	}
            	parentTitle = "<b>"+subGroupParent.getTitle()+"</b> sub group";
            	parentId = subGroupParent.getId();
            } else if (groupParent != null) {
            	if ("H".equals(document.getType())) {
            		docType = "sub group";
            	}
            	parentTitle = "<b>"+groupParent.getTitle()+"</b> group";
            	parentId = groupParent.getId();
            } else {
            	// its a group
            	if ("H".equals(document.getType())) {
            		docType = "group";
            	}
            	parentTitle = "document hierarchy";
            	parentId = ROOT_PARENT;
            }
            //have to check if this is a new document in order to determine the
            //parent id because the user may not have made a selection for group
            //on the form
            
            if(docId == -1 && groupParent == null && subGroupParent == null
            		&& categoryParent == null){
            	if(document.getParentDocument() != null){
            		parentId = document.getParentDocument();
            	}
            }
			Document duplicateDoc = DocumentService.getInstance()
			.findDuplicateDocument(document.getTitle(), docId, parentId, 
					document.getType(), document.getCategory());
			
    		if (duplicateDoc != null) {
    			ctxt.addError("error", "admin.document.edit.error.uniqueTitle.required",
    					new Object[]{docType, "<b>"+document.getTitle()+"</b>", parentTitle});
                success = false;
    		}
        	
            // validate feature expiry date
            Date expiryDate = null;
            if (StringUtils.isNotEmpty(featureExpiryDate)) {
                String dateFormatString = ctxt.getResources().getMessage("default.dateformat");
                SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);            
                try {
                    expiryDate = dateFormat.parse(featureExpiryDate);
                } catch (Exception e) {
                    // bad date format
                    ctxt.addError("error",
                            "admin.document.edit.error.invalidFeatureExpiryDateFormat",
                            new Object[] {dateFormat.toPattern()});
                    success = false;
                }
            }
            
            if (document.getType().equals(Constants.LINK_TYPE)) {
                if (StringUtils.isEmpty(link)) {
                    ctxt.addError("error",
                            "admin.document.edit.error.link.required");
                    success = false;
                }
            }
            if (success) {
                document.setExpiryDate(expiryDate);
            }
        }
        return success;
    }
    /**
     * Delete a document.
     * @return
     * @throws Exception
     */
    public String deleteDocument() throws Exception {
        Utils.checkFunction(Functionality.DELETE_DOCUMENTS);
        String result = Constants.FAILURE;
        if (groupId != null) {
        	Document docToDelete = findDocument();
            //now check if the doc has any children (headers and / or documents)
            if(docToDelete.getType().equals(Constants.HEADER_TYPE) && (docToDelete.getChildrenDocuments().size() > 0 || docToDelete.getChildrenHeaders().size() > 0)){
            	//error condition here
            	ActionContext.getActionContext().addError("error",
                "admin.document.delete.error.childRecordsPresent",new Object[]{docToDelete.getTitle()});
            } else {
            	DocumentService.getInstance().deleteDocument(docToDelete);
                ActionContext.getActionContext().addMessage("status", "status.deleteFromDB.success");
                result = Constants.SUCCESS;
            }
        }
        return result;
    }
 
    /**
     * @return Returns the document.
     */
    public Document getDocument() {
        return document;
    }
    /**
     * @return Returns the documents.
     */
    public List getDocuments() {
        return documents;
    }
    /**
     * @return Returns the featureExpiryDate.
     */
    public String getFeatureExpiryDate() {
        return featureExpiryDate;
    }
    /**
     * @param featureExpiryDate The featureExpiryDate to set.
     */
    public void setFeatureExpiryDate(String featureExpiryDate) {
        this.featureExpiryDate = featureExpiryDate;
    }
    /**
     * @return Returns the headers.
     */
    public List getHeaders() {
        return headers;
    }

    /**
     * @return Returns the link.
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link to set.
     */
    public void setLink(String link) {
        this.link = link;
    }
    
    private boolean validateDocument(Document document) {
    	boolean isValid = true;
    	if( document != null){
    		FormFile docAttachment = document.getAttachment();
    		if( docAttachment != null ){
    			String fileName = docAttachment.getFileName();
    			if( fileName != null )
    				isValid = containsLegal(fileName);
    		}
    	}
    	return isValid;
    }
    
    private boolean containsLegal(String fileName){
    	if( fileName.indexOf("!") != -1) return false;
    	else if( fileName.indexOf("@") != -1) return false;
    	else if( fileName.indexOf("#") != -1) return false;
    	else if( fileName.indexOf("$") != -1) return false;
    	else if( fileName.indexOf("%") != -1) return false;
    	else if( fileName.indexOf("^") != -1) return false;
    	else if( fileName.indexOf("&") != -1) return false;
    	else if( fileName.indexOf("*") != -1) return false;
    	else if( fileName.indexOf(")") != -1) return false;
    	else if( fileName.indexOf("(") != -1) return false;
    	else if( fileName.indexOf("'") != -1) return false;
    	else if( fileName.indexOf("|") != -1) return false;
    	else if( fileName.indexOf("[") != -1) return false;
    	else if( fileName.indexOf("]") != -1) return false;
    	else if( fileName.indexOf(">") != -1) return false;
    	else if( fileName.indexOf("<") != -1) return false;
    	else if( fileName.indexOf("/") != -1) return false;
    	else if( fileName.indexOf("\\") != -1) return false;
    	else return true;
    }
    
    private String getUserName(){
    	User user = Utils.getUserFromSession();
    	return user.getFirstName()+" "+user.getLastName();
    }

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getSubGroupId() {
		return subGroupId;
	}

	public void setSubGroupId(Integer subGroupId) {
		this.subGroupId = subGroupId;
	}
	
	private Document findDocument(){
		Document currentDoc = null;
		if (groupId != null) {
        	//load the group
        	for(Document doc : documents){
        		if (doc.getId().equals(groupId)){
        			currentDoc = doc;
        			break;
        		}
        	}
		}
		if (currentDoc != null){
			groupParent = currentDoc;
			//found group, now evaluate the other id's
			if(subGroupId == null || subGroupId == 0){
				subGroupParent = null;
				if(documentId == null || documentId == 0){
					return currentDoc;
				} else {
					return findChild(currentDoc.getChildrenDocuments(), documentId);
				}
			} else {
				//current doc set to the sub group
				currentDoc = findChild(currentDoc.getChildrenHeaders(), subGroupId);
				subGroupParent = currentDoc;
				if (categoryId == null || categoryId == 0 ) {
					categoryParent = null;
					if(documentId == null || documentId == 0){
						return currentDoc;
					} else {
						return findChild(currentDoc.getChildrenDocuments(), documentId);
					}
				}
				else {
					// current doc set to the category
					currentDoc = findChild(currentDoc.getChildrenHeaders(), categoryId);
					categoryParent = currentDoc;
					if(documentId == null || documentId == 0 ){
						return currentDoc;
					} else {
						return findChild(currentDoc.getChildrenDocuments(), documentId);
					}
				}
			}
		}
		return currentDoc;
	}
	
	private Document findChild(Set <Document> children, Integer childId ){
		Iterator <Document> it = children.iterator();
		while (it.hasNext()) {
			Document child = it.next();
			if(childId.equals(child.getId())) {
				return child;
			}
		}
		return null;
	}


	public Document getCategoryParent() {
		return categoryParent;
	}

	public void setCategoryParent(Document categoryParent) {
		this.categoryParent = categoryParent;
	}

	public Document getGroupParent() {
		return groupParent;
	}

	public void setGroupParent(Document groupParent) {
		this.groupParent = groupParent;
	}

	public Document getSubGroupParent() {
		return subGroupParent;
	}

	public void setSubGroupParent(Document subgroupParent) {
		this.subGroupParent = subgroupParent;
	}

	private void clearSelectionData(){
		clearCategoryData();
		clearSubGroupData();
		clearGroupData();
	}
	
	private void clearGroupData(){
		this.groupId = null;
		this.groupParent = null;
	}
	
	private void clearSubGroupData(){
		this.subGroupId = null;
		this.subGroupParent = null;
	}
	
	private void clearCategoryData(){
		this.categoryId = null;
		this.categoryParent = null;
	}
	
	private void assignParentDocument(){
		if (this.document != null){
			if (this.categoryId != null && this.categoryId != 0 && !Constants.HEADER_TYPE.equals(document.getType())) {
				document.setParentDocument(this.categoryId);
			} else if (this.subGroupId != null && this.subGroupId != 0) {
				document.setParentDocument(this.subGroupId );
			} else if (this.groupId != null) {
				document.setParentDocument(this.groupId);
			} else {
				document.setParentDocument(ROOT_PARENT);
			}
		}
	}
}
