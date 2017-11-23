package com.fgl.cwp.persistence.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.Notice;
import com.fgl.cwp.persistence.SessionManager;
import com.fgl.cwp.struts.ActionContext;

/**
 * @author Jessica Wong
 */
public class NoticeService {
    private static final NoticeService instance = new NoticeService();
    private static final Log log = LogFactory.getLog(NoticeService.class);    
    private static final String KEY_NOTICE_FILEPATH = "notices.filepath";


    /**
     * @return
     */
    public static NoticeService getInstance() {
        return instance;
    }

    /**
     * @return
     * @throws Exception
     */
    public List getNotices() throws Exception {
        Session session = null;
        List notices = null;

        try {
            session = SessionManager.getSessionFactory().openSession();
            notices = session.getNamedQuery("getNotices").list();
        } catch (Exception e) {
            log.error("Exception in getNotices.  Cause: " + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if (notices == null) {
            notices = new ArrayList();
        }

        return notices;
    }
    
    /**
     * Save a notice to the database.
     * @param notice
     * @return boolean
     * @throws Exception
     */
    public boolean saveNotice(Notice notice) throws Exception {
        
        boolean success = false;
        Session session = null;
        Transaction tx = null;
        
        try {
            if (validateSave(notice)) {
                session = SessionManager.getSessionFactory().openSession();
                tx = session.beginTransaction();
                session.saveOrUpdate(notice);
                tx.commit();
                success = true;
            }

        } catch (Exception e) {
            log.error("Failed to save notice", e);
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return success;
    }
    
    /**
     * Validate the notice before saving.
     * @param notice
     * @return
     * @throws Exception
     */
    private boolean validateSave(Notice notice) throws Exception {
        String newFileName = notice.getAttachment().getFileName();
        if (newFileName != null) {
            newFileName = newFileName.trim();
        }

        // Has the user uploaded a new file?
        boolean doDelete = false;
        boolean doSave = false;
        if (notice.isNew()) {
            doSave = true;
        } else {
            if (StringUtils.isNotEmpty(newFileName)) {
                doDelete = true;
                doSave = true;
            }
        }
            
        if (doSave) {
            String directory = ActionContext.getActionContext()
                    .getResources()
                    .getMessage(KEY_NOTICE_FILEPATH);

            List errors = notice.saveAttachment(directory, newFileName, doDelete, notice.getFileName()/*old filename*/);
            if (errors.isEmpty()) {
                // Update with the new file name
                notice.setFileName(newFileName);
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
     * Delete a notice from the database.
     * @param notice
     * @throws HibernateException
     */
    public void deleteNotice(Notice notice) throws HibernateException {
        Session session = null;
        Transaction tx = null;
        try {
            session = SessionManager.getSessionFactory().openSession();
            tx = session.beginTransaction();
            log.debug("deleteNotice():deleting notice id:" + notice.getId());
            session.delete(notice);
            tx.commit();            

            // clean up file system
            String directory = ActionContext.getActionContext()
                    .getResources()
                    .getMessage(KEY_NOTICE_FILEPATH);
            
            notice.deleteFromDisk(directory, notice.getFileName());
            log.debug("deleteNotice():successful");
        } catch (Exception e) {
            log.error("Failed to delete notice", e);
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}