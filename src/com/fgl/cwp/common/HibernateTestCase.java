/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Jun 3, 2005
 */
package com.fgl.cwp.common;

import com.fgl.cwp.persistence.SessionManager;

import net.sf.hibernate.Session;
import junit.framework.TestCase;

/**
 * @author bting
 */
public class HibernateTestCase extends TestCase {

    protected Session session = null;
    
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dbcp_fallback", "TRUE");
        session = SessionManager.getSessionFactory().openSession();
    }
    
    protected void tearDown() throws Exception {
        session.close();
        super.tearDown();
    }
}
