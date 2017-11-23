/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 28, 2005
 */
package com.fgl.cwp.presentation.forms;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import com.fgl.cwp.common.Constants;
import com.fgl.cwp.common.ParameterizedActionForward;
import com.fgl.cwp.common.Utils;
import com.fgl.cwp.exception.GeneratePdfException;
import com.fgl.cwp.model.Document;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.persistence.services.DocumentService;
import com.fgl.cwp.persistence.services.PDFService;
import com.fgl.cwp.persistence.services.PostalService;
import com.fgl.cwp.struts.ActionConstants;
import com.fgl.cwp.struts.ActionContext;
import com.fgl.cwp.struts.BaseBean;

/**
 * @author bting
 */
public class GenericFormBean extends BaseBean {
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(GenericFormBean.class);

    private String fileName;
    private String xmlTemplateURL;
    private int documentId = 0;
    private Document document;
    private String to;
    private String subject;
    

    public void reset() {
        fileName = null;
        xmlTemplateURL = null;        
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */    
    public String create() throws Exception {

        log.debug("Loading document.id: " + documentId);
        this.setDocument(DocumentService.getInstance().getDocumentById(documentId));
        
        // clean up
        documentId = 0;
        return Constants.SUCCESS;        
    }
    
    
    protected String[] getEmailTo() {
        ArrayList<String> toList = new ArrayList<String>();
        if( document != null){
	        if (document.getEmailAddress() != null) {
	            String[] dbEmails = document.getEmailAddress().split(";");
	            if (dbEmails != null) {
	                for (int i=0; i<dbEmails.length; ++i) {
	                    if (!dbEmails[i].trim().equals("")) {
	                        toList.add(dbEmails[i].trim());
	                    }
	                }
	            }
	        }
	        
	        if (to != null && !to.trim().equals("")) {
	            toList.add(to);
	        }
        }  
	    String[] tos = new String[toList.size()];        
	    toList.toArray(tos);
        return tos;
    }
    
    /**
     * Submit form. Generate the pdf file and send it to claims department.
     * @return
     */
    public String doSubmit() {
        
        String fwd = Constants.FAILURE;

        log.debug(this.toString());
        
        try {
            ActionContext actionCtxt = ActionContext.getActionContext();

            HttpServletRequest request = actionCtxt.getRequest();
            HttpServletResponse response = actionCtxt.getResponse();
            
            // Since the results are store in session, be sure to pass the session id to the xml request
            xmlTemplateURL = response.encodeURL(xmlTemplateURL);
            if (!xmlTemplateURL.contains("jsessionid")) {
                xmlTemplateURL =  request.getScheme()
                        + "://"
                        + request.getServerName()
                        + ":"
                        + request.getServerPort()
                        + request.getContextPath()
                        + xmlTemplateURL
                        + ";jsessionid="
                        + request.getSession().getId();
            }
                        
            String dir = actionCtxt.getResources().getMessage("forms.filepath");
 
            
            // Generate the pdf file
            File pdfFile = PDFService.getInstance()
                    .generatePdf(xmlTemplateURL, dir, fileName);
            
            // Configure the subject line of the email
            String emailSubject = subject;
            if (emailSubject == null || emailSubject.trim().equals("")) {
                emailSubject = fileName;
            }
            
            // Configure the CC of the email
            String cc = null;
            Store store = Utils.getStoreFromSession();
            if (store != null) {
                cc = store.getEmailAddress();
            }

            String body = "";
            boolean testing = isTesting(actionCtxt.getResources());
            if (testing) {
                // only cc the store if we are on the production environment
                body = "On the production environment, the store will be CCed a copy of this email using address: " + cc;
                cc = null;
            }
            log.debug("cc address: " + cc);
            

            boolean msgSent = PostalService.getInstance()
                    .sendMessage(getEmailTo(),
                            cc == null? null : new String[] {cc},
                            null/*null=use default from*/,
                            emailSubject, body, pdfFile);
            
            if (!msgSent) {
                actionCtxt.addError("error", "form.error.couldnotsendemail");
                fwd = Constants.FAILURE; 
            } else {   
                
                // Create an dynamic action forward
                ParameterizedActionForward downloadFwd = new ParameterizedActionForward(
                        actionCtxt.getMapping().findForward(Constants.SUCCESS));
                downloadFwd.addParameter("inline", "false");
                downloadFwd.addParameter("contentType", "application/pdf");
                downloadFwd.addParameter("dir", pdfFile.getParentFile().getAbsolutePath());
                downloadFwd.addParameter("file", pdfFile.getName());
                downloadFwd.setRedirect(true);
                
                request.setAttribute(ActionConstants.DYNAMIC_FORWARD, downloadFwd);
                fwd = null;
            }
        } catch (GeneratePdfException gpe) {
            fwd = Constants.FAILURE;
            log.error(gpe.getMessage(), gpe);
        }
        
        return fwd;        
    }
    
    
    private boolean isTesting(MessageResources msgResource) {
        String testing = msgResource.getMessage("testing");
        if (testing.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }
    
    /**
     * @return Returns the document.
     */
    public Document getDocument() {
        return document;
    }
    /**
     * @param document The document to set.
     */
    public void setDocument(Document document) {
        this.document = document;
    }
    /**
     * @return Returns the documentId.
     */
    public int getDocumentId() {
        return documentId;
    }
    /**
     * @param documentId The documentId to set.
     */
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
    /**
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return Returns the subject.
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subject The subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * @return Returns the to.
     */
    public String getTo() {
        return to;
    }
    /**
     * @param to The to to set.
     */
    public void setTo(String to) {
        this.to = to;
    }
    /**
     * @return Returns the xmlTemplateURL.
     */
    public String getXmlTemplateURL() {
        return xmlTemplateURL;
    }
    /**
     * @param xmlTemplateURL The xmlTemplateURL to set.
     */
    public void setXmlTemplateURL(String xmlTemplateURL) {
        this.xmlTemplateURL = xmlTemplateURL;
    }
}
