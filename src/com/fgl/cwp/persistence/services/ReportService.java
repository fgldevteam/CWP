package com.fgl.cwp.persistence.services;

import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.ItemPriceChange;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.model.sumtotal.eLearnReport.Department;
import com.fgl.cwp.persistence.SessionManager;


/**
 * @author Jessica Wong
 */
public class ReportService {
    private static final ReportService instance = new ReportService();
    private static final Log log = LogFactory.getLog(ReportService.class);

    /**
     * @return the singleton instance of the report service
     */
    public static ReportService getInstance() {
        return instance;
    }

    /**
     * Returns the number of rows that meet the user's parameters for the item price change report
     * @param storeNumber
     * @param priceDirection
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public int checkForItemPriceChangeReportData(int storeNumber, int priceDirection, Date startDate, Date endDate)
            throws Exception {
        int count = 0;
        Session session = SessionManager.getSessionFactory().openSession();
        
        try {
            Integer result = (Integer)session.getNamedQuery("countItemPriceChangeReport")
                    .setInteger("storeNumber", storeNumber)
                    .setInteger("priceDirection", priceDirection)
                    .setDate("startDate", startDate)
                    .setDate("endDate", endDate)
                    .uniqueResult();
            if (result != null) {
                count = result.intValue();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }

    /**
     * Returns the number of rows that would be returned for the package report with a given set of
     * parameter values
     * @param storeNumber
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public int checkForPackageReportData(int storeNumber, Date startDate, Date endDate) throws Exception {
        int count = 0;
        Session session = SessionManager.getSessionFactory().openSession();

        try {
            Integer result = (Integer) session.getNamedQuery("countPackageReport")
                    .setInteger("storeNumber", storeNumber)
                    .setDate("startDate", startDate)
                    .setDate("endDate", endDate)
                    .setInteger( "priceDirection", ItemPriceChange.DEAL_GROUP )
                    .uniqueResult();
            if (result != null) {
                count = result.intValue();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }

    /**
     * 
     * @param storeNumber
     * @param serverName
     * @return
     * @throws HibernateException
     */
    public List getReportQueueAtStore(int storeNumber, String serverName) throws HibernateException {
        Session session = SessionManager.getSessionFactory().openSession();
        try{
            return session.createQuery(
                    "select report " +
                    "from Report as report " +
                    "where report.storeId = :storeNumber " +
                    "and report.server = :server " +
                    "order by report.modifiedDate desc")
                .setInteger( "storeNumber", storeNumber )
                .setString( "server", serverName )
                .list();
        } finally {
            session.close();
        }
    }

    /**
     * @param report
     * @throws HibernateException
     */
    public void submitReport(Report report) throws HibernateException {
        Session session = SessionManager.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            try {
                session.save(report);
                tx.commit();
            } catch (HibernateException e) {
                tx.rollback();
            }

        } finally {
            session.close();
        }
    }
    
    /**
     * returns ItemPriceChangeData objects for the Item Price Change Report
     * @param storeNumber
     * @param priceDirection
     * @param startDate
     * @param endDate
     * @return
     * @throws HibernateException
     */
    public List fetchItemPriceChangeReportData( int storeNumber, List priceDirectionList, Date startDate, Date endDate) throws HibernateException{
        Session session = SessionManager.getSessionFactory().openSession();
        List results = null;
        try {
            results = session.getNamedQuery("itemPriceChangeReport")
                .setInteger("storeNumber", storeNumber)
                .setParameterList("priceDirectionList", priceDirectionList)
                .setDate("startDate", startDate)
                .setDate("endDate", endDate)
                .list();
        } finally {
            session.close();
        }
        log.info("Number of item price change rows: " + results.size());
        return results;
    }
    
    /**
     * Retrieves the reportable courses
     * @return
     * @throws HibernateException
     */
    public List fetchReportableCourses() throws HibernateException{
    	Session session = SessionManager.getSessionFactory().openSession();
    	List courseList = null;
    	try {
    		courseList = session.getNamedQuery("reportableCourses").list();	
    	} finally {
    		session.close();
    	}
    	return courseList;
    }
    
    /**
     * Retrieves the departments for the report
     * @return
     * @throws HibernateException
     */
    public List<Department> fetchDepartments() throws HibernateException{
    	Session session = SessionManager.getSessionFactory().openSession();
    	List <Department>departments = null;
    	try {
    		departments = session.getNamedQuery("allDepartments").list();	
    	} finally {
    		session.close();
    	}
    	return departments;
    }

    /**
     * returns PackageData objects for the Package Price Change Report
 * 
     * @param storeNumber
     * @param startDate
     * @param endDate
     * @return
     * @throws HibernateException
     */
    public List fetchPackageReportData(int storeNumber, Date startDate, Date endDate) throws HibernateException {
        Session session = SessionManager.getSessionFactory().openSession();
        List results = null;
        try {
            results = session.getNamedQuery("packageReport")
            .setInteger("storeNumber", storeNumber)
            .setDate("startDate", startDate)
            .setDate("endDate", endDate)
            .setInteger( "priceDirection", ItemPriceChange.DEAL_GROUP )
            .list();
        } finally {
            session.close();
        }
        log.info("Number of package price change rows: " + results.size());
        return results;
    }

}

