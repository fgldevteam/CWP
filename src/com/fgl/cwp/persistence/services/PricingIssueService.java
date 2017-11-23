/*
 * Created on Nov 21, 2005
 */
package com.fgl.cwp.persistence.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.PricingIssue;
import com.fgl.cwp.persistence.SessionManager;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

/**
 * @author urobertson
 */
public class PricingIssueService {
    private static final Log log = LogFactory.getLog(PricingIssueService.class);
    private static final PricingIssueService instance = new PricingIssueService();

    /**
     * @return A singleton instance of the service.
     */
    public static PricingIssueService getInstance() {

        return instance;
    }

    /**
     * @return A list of open Pricing Issues.
     * @throws Exception
     */
    public List getPricingIssues() throws Exception {

        Session session = null;
        List pricingIssues = null;
        Date currentDate;
        
        log.debug("in getPricingIssues");
        
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DATE,-1);        
        currentDate = new Date(rightNow.getTimeInMillis());
        
        log.debug(currentDate.toString());

        try {
            session = SessionManager.getSessionFactory().openSession();
            pricingIssues = session.getNamedQuery("getOpenPricingIssues")
            	.setParameter("selectedDate", currentDate)
            	.list();
          
            log.debug(String.valueOf(pricingIssues.isEmpty()));

        } catch (Exception e) {
            log.error("Exception in getPricingIssues.  Cause: " + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (pricingIssues == null) {
            pricingIssues = new ArrayList();
        }
        return pricingIssues;

    }

    /**
     * Set the pricing Issue closedDate.
     * @param pricingIssue
     * @return Success or Failure
     * @throws HibernateException
     */
    public boolean closePricingIssue(PricingIssue pricingIssue) throws HibernateException {

        boolean success = false;

        pricingIssue.setClosedDate(new Date());

        success = savePricingIssue(pricingIssue);

        return success;
    }

    /**
     * Save pricing Issue to database.
     * @param pricingIssue
     * @return Success or Failure
     * @throws HibernateException
     */
    public boolean savePricingIssue(PricingIssue pricingIssue) throws HibernateException {

        Session session = null;
        Transaction tx = null;
        boolean success = false;

        try {
            session = SessionManager.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(pricingIssue);
            tx.commit();
            success = true;

        } catch (HibernateException e) {
            log.error("Failed to save pricing issue", e);
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

}
