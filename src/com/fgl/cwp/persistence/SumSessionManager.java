package com.fgl.cwp.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.Serializable;
import net.sf.hibernate.HibernateException;
//import org.hibernate.HibernateException;
import net.sf.hibernate.SessionFactory;
//import org.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
//import org.hibernate.cfg.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.Session;
import net.sf.hibernate.Session;

/**
 * A simple class to store the Hibernate configuration and session factory.  
 * @author jmbarga
 */
public class SumSessionManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String DBCP_PROPERTIES = "/sumtotal-dbcp.properties";
    private static final String JNDI_PROPERTIES = "/sumtotal-jndi.properties";
    
    private static Configuration config;
    private static SessionFactory factory;
    private static final Log log = LogFactory.getLog(SumSessionManager.class);
    
    /**
     * 
     */
    private static final ThreadLocal threadLocalSession = new ThreadLocal ();
    
    /**
     * Returns the Hibernate Session Factory.
    * @return 
    * @throws HibernateException
    */
   public static Session currentSession() throws HibernateException {
      Session session = (Session) threadLocalSession.get();
      if (session == null) {
         throw new HibernateException("Session not initialized with openSession.  Should be done in facade class.");
    	  //session = openSession();
      }
      return session;
    }
   
   /**
    * Open a session and associate it with this thread.
    * @return session
    * @throws HibernateException
    */
   public static Session openSession() throws HibernateException{
       Session session = (Session) threadLocalSession.get();
       if (session != null){
           throw new HibernateException("A session already exists for this thread.");
       }
       session = getSessionFactory().openSession();       
       threadLocalSession.set(session);
       return session;
   }
   
   /**
    * @throws HibernateException
    */
   public static void closeSession() throws HibernateException {
       Session session = (Session) threadLocalSession.get();
       threadLocalSession.set(null);
       if (session != null){
           session.close();
       } else {
           throw new HibernateException( "No session to close." );
       }
    }
    /**
     * Returns the Hibernate Session Factory.
     * @return
     * @throws HibernateException
     */
    public static SessionFactory getSessionFactory() throws HibernateException{
        if (factory == null){
            //create the configuration from the hibernate.cfg.xml
            config = new Configuration();
            config.configure("/sumtotal.cfg.xml");
            
            try{
                //try connecting to JNDI for a datasource first
                loadConfigurationProperties( config, JNDI_PROPERTIES ); 
                
                factory = config.buildSessionFactory();
                log.info("Using JNDI connection pool");
            }catch (IOException e) {
                log.fatal("Cannot load " + JNDI_PROPERTIES, e);
            }catch (HibernateException e){ 
                String fallback = System.getProperty("dbcp_fallback");
                if ("TRUE".equals(fallback)){
                    fallbackToDBCP( DBCP_PROPERTIES);
                } else {
                    throw e;
                }
            }
        }
        return factory;
    }
    
    public static Class getDialect() throws ClassNotFoundException{
    	String dialect = config.getProperty("hibernate.dialect");
		return Class.forName(dialect);
    }

    /**
     * @param propertiesFile
     * @throws HibernateException
     */
    private static void fallbackToDBCP(String propertiesFile) throws HibernateException {
        log.info("Using DBCP fallback connection pool using properties: " + propertiesFile);
        try {
            loadConfigurationProperties(config, propertiesFile );
        } catch (IOException e1) {
            log.fatal("Cannot load " + propertiesFile, e1);
        }
        factory = config.buildSessionFactory();
    }

    private static void loadConfigurationProperties(Configuration config, String resourceName) throws IOException {
        Properties props = new Properties();
        InputStream stream = SumSessionManager.class.getResourceAsStream(resourceName); 
        props.load(stream);
        config.setProperties(props);
    }
      
    
    /**
     * @return Returns the config.
     * @throws HibernateException
     */
    public static Configuration getConfig() throws HibernateException {
        if( config == null ) {
            getSessionFactory();
        }
        return config;
    }
    
}
