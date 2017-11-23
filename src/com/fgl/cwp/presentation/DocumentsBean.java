package com.fgl.cwp.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.Document;
import com.fgl.cwp.persistence.services.DocumentService;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author Jessica Wong
 */
public class DocumentsBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(DocumentsBean.class);

    private List documentHeaders1;
    private List documentHeaders2;
    private List documentHeaders3;
    private List documentHeaders4;

    private List documents = new ArrayList();

    //incoming parameters
    private int[] parentSelection = {0, 0, 0, 0};
    private int level;

    private String searchDescription;
    private String searchDate;
    private boolean search;
    
    private String category;

    /**
     * Clear all set properties
     */
    public void resetAll() {
        setDocumentHeaders1(new ArrayList());
        setDocumentHeaders2(new ArrayList());
        setDocumentHeaders3(new ArrayList());
        setDocumentHeaders4(new ArrayList());
        setDocuments(new ArrayList());

        setParentSelection(new int[]{0, 0, 0, 0});

        setLevel(0);
        
        searchDescription = null;
        searchDate = null;
    }
    
    /**
     * @return
     */
    public String loadDocuments() {
        resetAll();
        ActionContext context = ActionContext.getActionContext();
        try {
            setDocumentHeaders1(DocumentService.getInstance().getDocuments(category));
        } catch (Exception e) {
            context.addError("error", "documents.error.gettingdocs");
            log.error("Error loading documents...", e);
        }
        search = false;
        return category;
    }

    /**
     * @return
     */
    public String searchDocuments() {
        ActionContext context = ActionContext.getActionContext();

        // Validate date format
        if (searchDate != null && !searchDate.trim().equals("")) {
            String dateString = context.getResources().getMessage("default.dateformat");
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateString);
            try {
                dateFormat.parse(searchDate);
            } catch (ParseException pe) {
                context.addError("error", "documents.error.searchdate.invaliddateformat", new Object[] {dateString});
                search = true;
                return category;
            }
        }

        setDocumentHeaders2(new ArrayList());
        setDocumentHeaders3(new ArrayList());
        setDocumentHeaders4(new ArrayList());
        setParentSelection(new int[]{0, 0, 0, 0});

        try {
            setDocuments(DocumentService.getInstance().searchDocuments(category, searchDescription,
                    searchDate));
        } catch (Exception e) {
            context.addError("error", "documents.error.searchingdocs");
            log.error("Error getting the document search results");
        }
        search = true;
        return category;
    }

    /**
     * todo: make this dynamic Load the child document headers and documents when a parent selection has been selected
     * @return
     */
    // this is a test
    public String loadChildDocuments() {
        log.debug("In " + getClass());
        log.debug("level: " + level);
        setDocuments(new ArrayList());
        setSearchDescription(null);
        setSearchDate(null);

        try {
            if (level == 1) { //Set Headers2
                Iterator it = getDocumentHeaders1().iterator();

                while (it.hasNext()) {
                    Document document = (Document) it.next();
                    if (document.getId().intValue() == parentSelection[0]) {
                        DocumentService.getInstance().loadDocumentChildren(document);
                        setDocumentHeaders2(new ArrayList(document.getChildrenHeaders()));
                        setDocumentHeaders3(new ArrayList());
                        setDocumentHeaders4(new ArrayList());
                        setDocuments(new ArrayList(document.getChildrenDocuments()));
                    }
                }
            }
            if (level == 2) { //Set Headers3
            	if(parentSelection[1] != 0){
	                Iterator it = getDocumentHeaders2().iterator();
	                while (it.hasNext()) {
	                    Document document = (Document) it.next();
	                    log.debug("DOC id: " + document.getId().intValue());
	                    if (document.getId().intValue() == parentSelection[1]) {
	                        DocumentService.getInstance().loadDocumentChildren(document);
	                        setDocumentHeaders3(new ArrayList(document.getChildrenHeaders()));
	                        setDocumentHeaders4(new ArrayList());
	                        setDocuments(new ArrayList(document.getChildrenDocuments()));
	                    }
	                }
            	} else {
            		//go back
            		level = 1;
            		loadChildDocuments();
            	}
            }
            if (level == 3) { //Set Headers4
            	if(parentSelection[2] != 0){
	                Iterator it = getDocumentHeaders3().iterator();
	                while (it.hasNext()) {
	                    Document document = (Document) it.next();
	                    if (document.getId().intValue() == parentSelection[2]) {
	                        DocumentService.getInstance().loadDocumentChildren(document);
	                        setDocumentHeaders4(new ArrayList(document.getChildrenHeaders()));
	                        setDocuments(new ArrayList(document.getChildrenDocuments()));
	                    }
	                }
            	} else {
            		// go back
            		level = 2;
            		loadChildDocuments();
            	}
            }
            if (level == 4) {
            	if(parentSelection[3] != 0){
	                Iterator it = getDocumentHeaders4().iterator();
	                while (it.hasNext()) {
	                    Document document = (Document) it.next();
	                    if (document.getId().intValue() == parentSelection[3]) {
	                        DocumentService.getInstance().loadDocumentChildren(document);
	                        setDocuments(new ArrayList(document.getChildrenDocuments()));
	                    }
	                }
            	} else {
            		// go back
            		level = 3;
            		loadChildDocuments();
            	}
            }
        } catch (Exception e) {
            ActionContext.getActionContext().addError("error", "documents.error.gettingdocs");
            log.error("Error loading documents...", e);
        }
        search = false;
        return category;
    }

    /**
     * @return Returns the category.
     */
    public String getCategory() {
        return category;
    }
    /**
     * @param category The category to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * @return
     */
    public int[] getParentSelection() {
        return parentSelection;
    }

    /**
     * @param parentSelection
     */
    public void setParentSelection(int[] parentSelection) {
        this.parentSelection = parentSelection;
    }

    /**
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return
     */
    public List getDocumentHeaders1() {
        return documentHeaders1;
    }

    /**
     * @param documentHeaders1
     */
    public void setDocumentHeaders1(List documentHeaders1) {
        this.documentHeaders1 = documentHeaders1;
    }

    /**
     * @return
     */
    public List getDocumentHeaders2() {
        return documentHeaders2;
    }

    /**
     * @param documentHeaders2
     */
    public void setDocumentHeaders2(List documentHeaders2) {
        this.documentHeaders2 = documentHeaders2;
    }

    /**
     * @return
     */
    public List getDocumentHeaders3() {
        return documentHeaders3;
    }

    /**
     * @param documentHeaders3
     */
    public void setDocumentHeaders3(List documentHeaders3) {
        this.documentHeaders3 = documentHeaders3;
    }

    /**
     * @return
     */
    public List getDocumentHeaders4() {
        return documentHeaders4;
    }

    /**
     * @param documentHeaders4
     */
    public void setDocumentHeaders4(List documentHeaders4) {
        this.documentHeaders4 = documentHeaders4;
    }

    /**
     * @return
     */
    public List getDocuments() {
        return documents;
    }

    /**
     * @param documents
     */
    public void setDocuments(List documents) {
        this.documents = documents;
    }

    /**
     * @return
     */
    public String getSearchDescription() {
        return searchDescription;
    }

    /**
     * @param searchDescription
     */
    public void setSearchDescription(String searchDescription) {
        this.searchDescription = searchDescription;
    }

    /**
     * @return
     */
    public String getSearchDate() {
        return searchDate;
    }

    /**
     * @param searchDate
     */
    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

}