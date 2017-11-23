package com.fgl.cwp.persistence.db;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * 
 * @author jmbarga
 * Customized db connection manager wrapper class
 *
 */

public class DBConnectionManager {
	static private DBConnectionManager instance; // The single instance
	
//	private Date checkDate = null; // Check date for the status.
	 //private int counter = 5*60*1000; // Every 5 minutes;
	 private Vector<Driver> drivers = new Vector<Driver>();
	 static private PrintWriter log;
	 static private Properties dbProps;
	 
	 /**
	  * Returns the single instance, creating one if it's the first time this
	  * method is called.
	  * 
	  * @return DBConnectionManager The single instance.
	  */
	 static synchronized public DBConnectionManager getInstance() throws Exception {
	  if (instance == null) {
	   instance = new DBConnectionManager();
	  }	  
	  return instance;
	 }
	 /**
	  * A private constructor since this is a Singleton
	  */
	 private DBConnectionManager() throws Exception {
	  init();
	 }
	 
	 /**
	  * Returns a connection to the named pool.
	  * 
	  * @param name
	  *  The pool name as defined in the properties file
	  * @param con
	  *  The Connection
	  */
	 public void freeConnection(String name, Connection con) {
	  try {
	   if ( !con.isClosed() ){
	    con.close();
	   }
	  } catch (SQLException e) {
	   // Do nothing
	  }
	 }
	 /**
	  * Returns an open connection. If no one is available, and the max number of
	  * connections has not been reached, a new connection is created.
	  * 
	  * @param name
	  *  The pool name as defined in the properties file
	  * @return Connection The connection or null
	  */
	 public Connection getConnection(String name) {
	  try {
	   return DriverManager.getConnection("jdbc:apache:commons:dbcp:"+name);
	  } catch (SQLException e) {
	   e.printStackTrace();
	   return null;
	  }
	 }
	 /**
	  * Creates instances of DBConnectionPool based on the properties. A
	  * DBConnectionPool can be defined with the following properties:
	  * 
	  * <PRE>
	  * 
	  * &lt;poolname&gt;.url The JDBC URL for the database &lt;poolname&gt;.user A
	  * database user (optional) &lt;poolname&gt;.password A database user password
	  * (if user specified) &lt;poolname&gt;.maxconn The maximal number of
	  * connections (optional)
	  * 
	  * </PRE>
	  * 
	  * @param props
	  *  The connection pool properties
	  */
	 private void createPools(Properties props) {
	  Enumeration propNames = props.propertyNames();
	  
	    //
	    // Finally, we create the PoolingDriver itself...
	    //
	  PoolingDriver driver = null;
	  try {
	   Class.forName("org.apache.commons.dbcp.PoolingDriver");
	     driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	   } catch (Exception e) {
	     e.printStackTrace();
	  }
	  while (propNames.hasMoreElements()) {
	   String name = (String) propNames.nextElement();
	   if (name.endsWith(".url")) {
	    String poolName = name.substring(0, name.lastIndexOf("."));
	    String url = props.getProperty(poolName + ".url");
	    if (url == null) {
	     log("No URL specified for " + poolName);
	     continue;
	    }
	    String maxActive = props.getProperty(poolName + ".maxActive", "0");
	    String maxIdle = props.getProperty(poolName + ".maxIdle", "0");
	    String maxWait = props.getProperty(poolName + ".maxWait", "-1");
	    String initialSize = props.getProperty(poolName + ".initialSize", "10");
	    String uname = props.getProperty(poolName + ".user");
	    String passwd = props.getProperty(poolName + ".password");	    
	    int maxActiveInt;
	    int maxIdleInt;
	    int maxWaitInt;
	    int initialSizeInt;	    
	    try {
	     maxActiveInt = Integer.valueOf(maxActive).intValue();
	    } catch (NumberFormatException e) {
	     log("Invalid maxActive value " + maxActive + " for " + poolName);
	     maxActiveInt = 0;
	    }
	    try {
	     maxIdleInt = Integer.valueOf(maxIdle).intValue();
	    } catch (NumberFormatException e) {
	     log("Invalid maxIdle value " + maxIdle + " for " + poolName);
	     maxIdleInt = 0;
	    }
	    try {
	     maxWaitInt = Integer.valueOf(maxWait).intValue();
	    } catch (NumberFormatException e) {
	     log("Invalid maxWait value " + maxWait + " for " + poolName);
	     maxWaitInt = -1;
	    }
	    try {
	     initialSizeInt = Integer.valueOf(initialSize).intValue();
	    } catch (NumberFormatException e) {
	     log("Invalid maxWait value " + initialSize + " for " + poolName);
	     initialSizeInt = -1;
	    }
	   
	    try{
		    GenericObjectPool connectionPool = new GenericObjectPool(null);
		    connectionPool.setMaxActive( maxActiveInt );
		    connectionPool.setMaxIdle( maxIdleInt );
		    connectionPool.setMaxWait( maxWaitInt ); 
		    connectionPool.setMinIdle( initialSizeInt );
		    connectionPool.setTestWhileIdle( true );
		    connectionPool.setNumTestsPerEvictionRun( 5 );
		    connectionPool.setMinEvictableIdleTimeMillis( 100000 );
		    connectionPool.setTimeBetweenEvictionRunsMillis( 10000 );		    
		    
		    
		    ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url,uname,passwd);
		    new PoolableConnectionFactory(connectionFactory,connectionPool,null,"Select 1;",false,true);
	      //
	      // ...and register our pool with it.
	      //
	        driver.registerPool(poolName,connectionPool);
	    }catch(NullPointerException npe){
	    	
	    }catch(Exception e){
	    	System.err.println("");
	    }
	    log("Initialized pool " + poolName);
	   }
	  }
	 }
	 
	  public static void printDriverStats() throws Exception { 
	    PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	    String [] poolNames = driver.getPoolNames();
	    log("------------------");
	  for (int i = 0; i < poolNames.length; i++) {
	      GenericObjectPool connectionPool = (GenericObjectPool)driver.getConnectionPool( poolNames[i] );
	      log("Pool Statistics:" + poolNames[i] );
	      log("Max Active: " + connectionPool.getMaxActive() );
	      log("Num Active: " + connectionPool.getNumActive());
	      log("Max Idle: " + connectionPool.getMaxIdle() );
	      log("Num Idle: " + connectionPool.getNumIdle());
	      log("Max Wait: " + connectionPool.getMaxWait() );
	  }
	  } 
	  
	  public static void shutdownDriver() throws Exception {
	    PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	    String [] poolNames = driver.getPoolNames();
	  for (int i = 0; i < poolNames.length; i++) {
	    driver.closePool( poolNames[i] );
	    log("Shutting Down:" + poolNames[i] );
	  }
	  log("All Drivers Shutdown");
	  instance = null;
	 }  
	 /**
	  * Loads properties and initializes the instance with its values.
	  */
	 private void init() throws Exception {	
	  InputStream file  = this.getClass().getResourceAsStream("/dbpool.properties");	
	  dbProps = new Properties();
	  try {	 
	   dbProps.load(file);
	   file.close();  
	  } catch (Exception e) {
	   log("Can't read the properties file. "  + "Make sure  it exists.");
	   return;
	  }
	  String logFile = dbProps.getProperty("logfile", "DBConnectionManager.log");
	  try {
	   log = new PrintWriter(new FileWriter(logFile, true), true);
	  } catch (IOException e) {
	   System.err.println("Can't open the log file: " + logFile);
	   log = new PrintWriter(System.err);
	  }
	  loadDrivers(dbProps);
	  createPools(dbProps);	
	 }
	 /**
	  * Loads and registers all JDBC drivers. This is done by the
	  * DBConnectionManager, as opposed to the DBConnectionPool, since many pools
	  * may share the same driver.
	  * 
	  * @param props
	  *          The connection pool properties
	  */
	 private void loadDrivers(Properties props) {
	  String driverClasses = props.getProperty("drivers");
	  StringTokenizer st = new StringTokenizer(driverClasses);
	  while (st.hasMoreElements()) {
	   String driverClassName = st.nextToken().trim();
	   try {
	    Driver driver = (Driver) Class.forName(driverClassName).newInstance();
	    DriverManager.registerDriver(driver);
	    drivers.addElement(driver);
	    log("Registered JDBC driver " + driverClassName);
	   } catch (Exception e) {
	    log("Can't register JDBC driver: " + driverClassName + ", Exception: "
	      + e);
	   }
	  }
	 }
	 /**
	  * Writes a message to the log file.
	  */
	 protected static void log(String msg) {
	  log.println(new Date() + ": " + msg);
	 }
	 /**
	  * Writes a message with an Exception to the log file.
	  */
	 protected static void log(Throwable e, String msg) {
	  log.println(new Date() + ": " + msg);
	  e.printStackTrace(log);
	 }
	 


}
