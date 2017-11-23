package com.fgl.cwp.persistence.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.model.Document;
import com.fgl.cwp.persistence.SessionManager;
import com.fgl.cwp.struts.ActionContext;

/**
 * @author Jessica Wong
 */
public class DocumentService {
    private static final DocumentService instance = new DocumentService();
    private static final Log log = LogFactory.getLog(DocumentService.class);
    private static final String KEY_DOCUMENT_FILEPATH = "documents.filepath";
    

    /**
     * @return the singleton instance of the DocumentService
     */
    public static DocumentService getInstance() {
        return instance;
    }

    /**
     * @return
     * @throws Exception
     */
    public List getFeaturedDocuments() throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        List documents = new ArrayList();

        try {
            documents = session.getNamedQuery("getFeaturedDocuments").list();
        } catch (Exception e) {
            log.error("Exception in getFeaturedDocuments.  Cause: " + e);
            throw e;
        } finally {
            session.close();
        }

        return documents;
    }

    /**
     * @param category
     * @return
     * @throws Exception
     */
    public List getDocuments(String category) throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        List documents = new ArrayList();

        try {
            documents = session.getNamedQuery("getDocuments")
                .setParameter("category", category)
                .list();
        } catch (Exception e) {
            log.error("Exception in getDocuments.  Cause: " + e);
            throw e;
        } finally {
            session.close();
        }

        return documents;
    }
    

    /**
     * Initialize the Document's child header and child document collections
     * 
     * @param doc
     * @throws Exception
     */
    public void loadDocumentChildren(Document doc) throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        try {
            session.lock(doc, LockMode.NONE);
            Hibernate.initialize(doc.getChildrenDocuments());
            Hibernate.initialize(doc.getChildrenHeaders());
        } finally {
            session.close();
        }
    }

    /**
     * @param category
     * @param description
     * @param date
     * @return
     * @throws Exception
     */
    public List searchDocuments(String category, String description, String date) throws Exception {
        Session session = SessionManager.getSessionFactory().openSession();
        List documents = new ArrayList();
        log.debug("\nTitle: " + description + "   Date: " + date);
        try {
            documents = session.getNamedQuery("getDocumentsSearch")
                .setParameter("category", category)
                .setParameter("title", "%" + description + "%")
                .setParameter("modifiedDate", date)
                .list();
        } catch (Exception e) {
            log.error("Exception in searchDocuments.  Cause: " + e);
            throw e;
        } finally {
            session.close();
        }
        return buildDocumentHierarchy(documents, null);
        //return documents;
    }
    
    /**
     * Return all documents and headers.
     * @return
     * @throws Exception
     */
    public List getAllDocuments() throws Exception {
        List <Document>documents = null;
        List <Document>headerDocs = null;
        Session session = null;
        
        try {
            session = SessionManager.getSessionFactory().openSession();
            documents = session.getNamedQuery("getAllDocuments").list();
            headerDocs = session.getNamedQuery("getAllHeaders").list();
        } catch (Exception e) {
            log.error("Failed to get all documents", e);
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (documents == null) {
            documents = new ArrayList<Document>();
        }
        initializeHeaderSets(headerDocs);
        Map headers = buildHeaderMap(headerDocs);
        //initialize the header doc sets
        return buildDocumentHierarchy(documents, headers);
    }
    
    /**
     * Get all document headers for a particular category. This method will
     * exclude the given document if provided.
     * @param document 
     * @param category
     * @return
     * @throws Exception
     */
    public List getAllHeaders(Document document, String category) throws Exception {
        List headers = null;
        Session session = null;
        
        try {
            session = SessionManager.getSessionFactory().openSession();
            
            if (document != null && document.getId() != null) {
                List<Integer> exceptionIdList = new ArrayList<Integer>();
                exceptionIdList.add(document.getId());
                headers = session.getNamedQuery("getAllHeadersExcept")
                        .setString("category", category)
                        .setParameterList("documentIdList", exceptionIdList)
                        .list();
            } else {
                headers = session.getNamedQuery("getAllHeadersByCategory")
                        .setString("category", category)
                        .list();
            }
        } catch (Exception e) {
            log.error("Failed to get all document headers", e);
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (headers == null) {
            headers = new ArrayList();
        }
        return headers;
    }
    
    
    
    /**
     * Save a document to the database.
     * @param document
     * @return
     * @throws Exception
     */
    public boolean saveDocument(Document document) throws Exception {
        boolean success = false;
        Session session = null;
        Transaction tx = null;
        try {
            if (!"D".equals(document.getType()) || validateSave(document)) {
                session = SessionManager.getSessionFactory().openSession();
                log.debug("saving document: " + document.toString());
                
                tx = session.beginTransaction();
                session.saveOrUpdate(document);
                tx.commit();
                
                // TODO: BYT - Figure out why we have to refresh this object???
                // We get a StaleObjectException when saving a document more than once.
                session.refresh(document);
                
                success = true;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("Failed to save document", e);
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return success;
    }
    
    /**
     * Validate the notice before saving.
     * @param document
     * @return boolean
     * @throws Exception
     */
    private boolean validateSave(Document document) throws Exception {
    	String newFileName = document.getAttachment().getFileName();
        StringUtils.trim(newFileName);

        // Has the user uploaded a new file?
        boolean doDelete = false;
        boolean doSave = false;
        if (document.getType().equals(Constants.DOCUMENT_TYPE)) {
            if (document.isNew()) {
                doSave = true;
            } else {
                if (StringUtils.isNotEmpty(newFileName)) {
                    doDelete = true;
                    doSave = true;
                } else if (StringUtils.isEmpty(document.getFileName())) {
                    doSave = true;
                }
            }
        }
            
        if (doSave) {
            String directory = ActionContext.getActionContext()
                    .getResources()
                    .getMessage(KEY_DOCUMENT_FILEPATH);

            List errors = document.saveAttachment(directory, newFileName, doDelete, document.getFileName()/*old filename*/);
            if (errors.isEmpty()) {
                // Update with the new file name
                document.setFileName(newFileName);
                return true;
            }
            
            for (Iterator iter = errors.iterator(); iter.hasNext(); ){
                ActionContext.getActionContext().addError("error", (String)iter.next());
            }
            return false;
        }
        return true;
    }
    
    /**
     * Delete a document from the database
     * @param document
     * @throws Exception
     */
    public void deleteDocument(Document document) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(document);
            tx.commit();
            
            // clean up file system
            if (StringUtils.isNotEmpty(document.getFileName())) {
                String directory = ActionContext.getActionContext()
                        .getResources()
                        .getMessage(KEY_DOCUMENT_FILEPATH);
                
                document.deleteFromDisk(directory, document.getFileName());
            }            
            log.debug("deleteDocument():successful");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("Failed to delete document", e);
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Get document given id.
     * @param id
     * @return
     * @throws Exception
     */
    public Document getDocumentById(int id) throws Exception {
        Document document = null;
        Session session = null;
        
        try {
            session = SessionManager.getSessionFactory().openSession();
            document = (Document)session.getNamedQuery("getDocumentById")
                    .setInteger("id", id)
                    .uniqueResult();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return document;
    }
    
    private List buildDocumentHierarchy(List <Document>documents, Map headers) throws Exception{
    	documents = populateDocumentCollection(documents, headers);
    	while (!verifyTopLevelParents(documents)){
    		// now assign the non-header documents 
    		documents = populateDocumentCollection(documents, null);
    	}
    	
    	int orphanIndex = -1;
    	int count = 0;
    	for (Document doc : documents){
    		if (doc.getId() == Constants.ORPHAN_GROUP){
    			orphanIndex = count;
    		}
    		count++;
    	}
    	Document orphans = null;
    	if (orphanIndex > -1) {
    		orphans = documents.remove(orphanIndex);
    	}
    	Collections.sort(documents);
    	
    	if (orphans != null) {
    		documents.add(0, orphans);
    	}
    	
    	return documents;
    }
    
    private List populateDocumentCollection(List <Document> documents, Map <Integer,Document> parents) throws Exception{
    	if(parents == null){
    		parents = new HashMap<Integer, Document>();
    	} 
    	//document type will determine if adding to the child docs or headers set
    	Integer parentId = null;
    	String type = null;
    	Document parentDoc = null;
    	for(Document document : documents){
    		if(document.getId().intValue() == 832){
    			System.out.println();
    		}
    		parentId = document.getParentDocument();
    		if (parentId != null && parentId.intValue() != 0) {
	    		type = document.getType();
	    		if(parents.containsKey(parentId)){
	    			if ("H".equals(type)){
	    				parents.get(parentId).getChildrenHeaders().add(document);
	    			} else {
	    				parents.get(parentId).getChildrenDocuments().add(document);
	    			}
	    		} else {
    				// get the parent doc
    				parentDoc = getDocumentById(parentId.intValue());
    				// initialize the header and child doc sets
	    			if (parentDoc != null){
	    				parentDoc.setChildrenHeaders(new TreeSet<Document>());
	    				parentDoc.setChildrenDocuments(new TreeSet<Document>());
		    			if ("H".equals(type)){
		    				parentDoc.getChildrenHeaders().add(document);
		    			} else {
		    				parentDoc.getChildrenDocuments().add(document);
		    			}
		    			parents.put(parentId, parentDoc);
	    			} else {
	    				// parent doc not found with the given id
	    				log.warn("Orphan document record found ID: "+
	    						document.getId()+" Parent ID: "+
	    						document.getParentDocument());
	    				
    					//create the orphan group if it does not exist
    					parentDoc = parents.get(Constants.ORPHAN_GROUP);
    					if (parentDoc == null){
    						parentDoc = createOrphanGroup(document.getCategory());
    	    				parents.put(Constants.ORPHAN_GROUP, parentDoc);
    					}
    					if ("H".equals(document.getType())){
    						parentDoc.getChildrenHeaders().add(document);
    					} else {
    						parentDoc.getChildrenDocuments().add(document);
    					}
	    			}
	    		}
    		} else {
    			//add document as a root level parent if type is H 
    			// otherwise its an orphan document
    			if("H".equals(document.getType())){
    				parents.put(document.getId(), document);
    			}else {
    				log.warn("Non-Header type document found Document ID: "+
    						document.getId()+" without parentdoc ID");
    				parentDoc = parents.get(Constants.ORPHAN_GROUP);
					if (parentDoc == null){
						parentDoc = createOrphanGroup(document.getCategory());
	    				parents.put(Constants.ORPHAN_GROUP, parentDoc);
					}
					parentDoc.getChildrenDocuments().add(document);
    			}
    		}
    	}//end for loop
    	//now associate any parents
    	return associateParents(parents);
    }
    
    private boolean verifyTopLevelParents(List <Document> documents){
    	for (Document document : documents) {
    		if(document.getParentDocument() != null && document.getParentDocument().intValue() != 0 ){
    			return false;
    		}
    	}
    	return true;
    }
    private List associateParents(Map <Integer, Document>parentMap) {
    	List <Integer>topParentKeys = new ArrayList<Integer>();
    	Set <Integer>keys = parentMap.keySet();
    	topParentKeys.addAll(keys);
    	List <Document> parents = new ArrayList<Document>();
   
    	Iterator it = keys.iterator();
    	Integer docId = null;
    	Integer parentId = null;
    	while(it.hasNext()){
    		try{
    		docId = (Integer)it.next();
    		parentId = parentMap.get(docId).getParentDocument();
    		if(parentId != null && parentMap.get(parentId) != null){
    			//associate the child with the parent
    			parentMap.get(parentId).getChildrenHeaders().add(parentMap.get(docId));
    			//remove the child from the topParentKeys Set
    			topParentKeys.remove(docId);
    		}
    		}catch (NullPointerException npe){
    			log.error(npe);
    		}
    	}
    	//now the topParentKeys set should only contain the legitimate top parents
    	for (Integer key : topParentKeys){
    		parents.add(parentMap.get(key));
    	}
    	return parents;
    }
    
    private void initializeHeaderSets(List <Document> documents){
    	for(Document document : documents){
    		if("H".equals(document.getType())){
    			document.setChildrenHeaders(new TreeSet<Document>());
    			document.setChildrenDocuments(new TreeSet<Document>());
    		}
    	}
    }
    
    private Map buildHeaderMap(List <Document> headers){
    	Map <Integer, Document> headerMap = new HashMap<Integer,Document>();
    	for (Document document : headers){
    		if ("H".equals(document.getType())){
    			headerMap.put(document.getId(), document);
    		}
    	}
    	return headerMap;
    }
    
    private Document createOrphanGroup(String searchType){
    	Document orphanGroup = new Document();
		orphanGroup.setTitle("Orphan Records");
		orphanGroup.setCategory(searchType);
		orphanGroup.setType("H");
		orphanGroup.setId(Constants.ORPHAN_GROUP);
		orphanGroup.setChildrenHeaders(new TreeSet<Document>());
		orphanGroup.setChildrenDocuments(new TreeSet<Document>());
    	return orphanGroup;
    }
    
    /**
     * Used to determine if a duplicate document exists. Duplicate document
     * exists if record with different id, but matches on the other criteria is found.
     * @param title
     * @param id
     * @param parentId
     * @param type
     * @param category
     * @return
     * @throws Exception
     */
    public Document findDuplicateDocument(String title, Integer id, Integer parentId, 
    		String type, String category) throws Exception {
    	Session session = null;
    	Document document = null;
    	try {
    		session = SessionManager.getSessionFactory().openSession();
    		document = (Document)session.getNamedQuery("findDuplicateDocument")
    		.setParameter("title", title)
            .setInteger("id", id)
            .setInteger("parentId", parentId)
            .setParameter("type", type)
            .setParameter("category", category)
            .uniqueResult();
    	} finally {
    		if (session != null) {
    			session.close();
    		}
    	}
    	return document;
    }
}