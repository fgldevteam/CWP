package com.fgl.cwp.persistence.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import net.sf.hibernate.HibernateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.common.util.SumMultiMap;
import com.fgl.cwp.model.QueryParameters;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.model.sumtotal.ReportData;
import com.fgl.cwp.persistence.SumSessionManager;

/**
 * 
 * @author jmbarga
 * 
 */

public class ElearningReportService implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ElearningReportService.class);

	public static SumMultiMap getElearnReportData(QueryParameters params)throws HibernateException {
		List<SumTotalCourse> courses = params.getCourses(); 
		List stores = params.getStores();		
		SumNativeQuery query = null; 
		List<ReportData>  storeData = null; 
		TreeMap depData=null, extra_employess=null;
		SumMultiMap container = new SumMultiMap(new HashMap());
		String jobIds = null;
		try{
			Connection conn = SumSessionManager.openSession().connection();
	        ArrayList<Object> storeInfos = null;     
	       
	        	for(int i=0; stores !=null && i <stores.size(); i++){
	        		storeInfos = new ArrayList<Object>();
	        		
	        		// determine if individual departments have been selected
	        		if (params.getDepartments() != null && params.getDepartments().length > 0){
	        			jobIds = executeJobIdQuery(params.getDepartments(), conn);
	        		}
	        		
	        		if (jobIds == null) {
	        			query = SumQueryService.getQuery("DateRangeDataByCourseAndStore");
	        		} else {
	        			query = SumQueryService.getQuery("DateRangeDataByCourseAndStoreFilteredByJob");
	        			query = query.setInternString(":jobs", jobIds);
	        		}
	        		
	        		Store store = (Store)stores.get(i);
	        		// if the store number is only 3 digits, prefix with a zero for the queries
	        		String storeNumber = ""+store.getNumber();
	        		if(storeNumber.length() == 3){
	        			storeNumber = "0"+storeNumber;
	        		}
	        		
		            query = query.setInternString(":storeNum", "('"+storeNumber+"')");
		         	query = query.setInternString(":courses", setCourses(courses));
		         	query = query.setInternString(":date", setDates(params.getStartDate(), params.getEndDate()));
		         	storeData = executeReportDataQuery(query,conn);	query = null;
		         	
		         	if (jobIds == null) {
	        			query = SumQueryService.getQuery("DateRangeDataByCourseAndDepartInStore");
	        		} else {
	        			query = SumQueryService.getQuery("DateRangeDataByCourseAndDepartInStoreFilteredByJob");
	        			query = query.setInternString(":jobs", jobIds);
	        		}
		         	
		         	query = query.setInternString(":storeNum", "('"+storeNumber+"')");
		         	query = query.setInternString(":courses", setCourses(courses));
		         	query = query.setInternString(":date", setDates(params.getStartDate(), params.getEndDate()));
		         	depData = executeRepByDepartInStore(""+store.getNumber(), query,conn);query=null;
		         	
		         	if (jobIds == null){
		         		query = SumQueryService.getQuery("getAllEmployesInStoreWithoutChosenCourse");
		         	} else {
		         		query = SumQueryService.getQuery("getAllEmployesInStoreWithoutChosenCourseFilteredByJob");
		         		query = query.setInternString(":jobs", jobIds);
		         	}
		         	
		         	query = query.setInternString(":storeNum", "('"+storeNumber+"')");
		         	query = query.setInternString(":courses", setCourses(courses));
		         	query = query.setInternString(":date", setDates(params.getStartDate(), params.getEndDate()));
		         	extra_employess = executeExtraInfos(""+store.getNumber(), query,conn);
		         	storeInfos.add(depData);         	
		         	storeInfos.add(storeData);
		         	storeInfos.add(extra_employess);
		         	container.add(store.getNumber(),storeInfos);
	        	}         
		}finally{
			try{
				SumSessionManager.closeSession();
			}catch(HibernateException he){
				log.error(he);
			}
		}
        return container;		
	}

	public static List<ReportData> executeReportDataQuery(SumNativeQuery query,Connection conn) {
		List<ReportData> results = Collections.synchronizedList(new ArrayList<ReportData>());
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query.getSyntax());
			while (rs.next() && rs != null) {
				ReportData row = new ReportData(rs.getString("empLName"),
												rs.getString("empFName"),
												rs.getString("empId"),
								                rs.getString("vicePresident"),
								                rs.getString("regManager"),
								                rs.getString("districtManager"),
								                rs.getString("courseName"), 
								                rs.getString("department"),
								                rs.getString("score"),
								                rs.getDate("date_taken"),
								                rs.getString("storeName"),
								                rs.getString("numEmp") 
								                );
				results.add(row);
			}	
		} catch (SQLException e) {
			log.error(e);
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException sqle) {
				log.error(e);
			}
		}
		return results;
	} 
	
	private static TreeMap executeRepByDepartInStore(String number,SumNativeQuery query,Connection conn){
		SumMultiMap depMap = new SumMultiMap(Collections.synchronizedMap(new TreeMap<String,DepartmentInStoreData>()));		
		TreeMap<String,SumMultiMap> depContainer = new TreeMap<String,SumMultiMap>();
		Statement stmt = null;	
		try{
			stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery(query.getSyntax());
			ElearningReportService self = new ElearningReportService();
			while(rs.next() && rs != null){ 				
				DepartmentInStoreData data = self.new DepartmentInStoreData(
								rs.getString("department"),
								rs.getString("deptSymbol"),
								rs.getString("empLName"),
								rs.getString("empFName"),
								rs.getString("empId"),
								rs.getString("score"),
								rs.getString("date_taken"),
								rs.getString("courseName"),
								rs.getString("courseId"),
								rs.getString("storeName"),
								executedepSubQuery(number,rs.getString("deptSymbol"),conn)				
							    );				
				depMap.add(data.getDepartment(),data);
			}
			depContainer.put(number,depMap);	
		} catch (SQLException e) {
			log.error(e);
			try {
				if (stmt != null) stmt.close();			
			} catch (SQLException sqle) {
				log.error(e);
			}
		}
		return depContainer;
	}
	
	/**
	 * Retrieves the job ID's of the SumTotal Job records that end with the 
	 * abbreviated department names
	 * @param depts
	 * @return
	 * @throws HibernateException
	 */
	private static String executeJobIdQuery(String[] depts, Connection conn) throws HibernateException {
		Statement stmt = null;
		ResultSet results = null;
		try {
			SumNativeQuery query = SumQueryService.getQuery("getJobData");
			query = query.setInternString(":jobs", setJobs(depts));
			stmt = conn.createStatement();
			results = stmt.executeQuery(query.getSyntax());
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			while (results.next()) {
				sb.append((results.getInt("Job_PK")+","));
			}
			sb.replace((sb.length()-1), sb.length(), ")");
			return sb.toString();
		}  catch (SQLException e) {
			log.error(e);
			
		} finally {
			try {
				if (stmt != null) stmt.close();			
			} catch (SQLException sqle) {
				log.error(sqle);
			}
		}
		return null;
	}
	
	private static String executedepSubQuery(String number,String department,Connection conn){
		SumNativeQuery query = SumQueryService.getQuery("EmployeesInDepartment");
		query = query.setInternString(":storeNumber","('"+number+"')");
		query = query.setInternString(":department","'"+department+"'");
		Statement stmt=null;	String numOfEmployees="0";	
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query.getSyntax());
			while(rs.next() && rs != null){
				numOfEmployees = rs.getString("counter");
			}
		}catch (SQLException e) {
			try{
				if(stmt!=null)stmt.close();
			}catch (SQLException sqle) {
				log.error(e);
			}
			log.error(e);
		}
		return numOfEmployees;
	}
	
	private static String setCourses(List<SumTotalCourse> courses){
		StringBuffer sqlAdOn = new StringBuffer();
		if( courses.size() !=0 ){
			sqlAdOn.append("(");
			for(SumTotalCourse course : courses){
				sqlAdOn = sqlAdOn.append("'"+course.getCourseId()+"',");
			}
			sqlAdOn.setLength(sqlAdOn.length() -1);
			sqlAdOn.append(")");
		}
		return sqlAdOn.toString();
	}

	private static String setDates(GregorianCalendar startDate,
			GregorianCalendar endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuffer sqlAdOn = new StringBuffer();
		if (null != startDate || null != endDate) {
			if (null == startDate) {
				sqlAdOn = sqlAdOn.append(" where EndDt <= DATEADD(DAY,1,'")
				                 .append(sdf.format(endDate.getTime()))
				                 .append("')");
			} else if (null == endDate) {
				sqlAdOn = sqlAdOn.append(" where EndDt >= '")
				                 .append(sdf.format(startDate.getTime()))
				                 .append("'");
			} else {
				if( startDate.equals(endDate) ){
					sqlAdOn = sqlAdOn.append(" where CAST(FLOOR(CAST(EndDt AS float)) AS datetime) = '")
					                 .append(sdf.format(startDate.getTime()))
					                 .append("'");					
				}else{
				    sqlAdOn = sqlAdOn.append(" where EndDt between '")
						         .append(sdf.format(startDate.getTime()))
						         .append("' and ")
						         .append("DATEADD(DAY,1,'"+sdf.format(endDate.getTime()))
						         .append("')");
				}
			}
		}
		return sqlAdOn.toString();
	}		
	
	/**
	 * to get the other employees in store who didn't take the course
	 */
	private static TreeMap executeExtraInfos(String storeNumber, SumNativeQuery query, Connection conn){
		SumMultiMap other_map = new SumMultiMap(Collections.synchronizedMap(new TreeMap<String,DepartmentInStoreData>()));
		TreeMap<String,SumMultiMap> extra_Container = new TreeMap<String,SumMultiMap>();
		Statement stmt = null;	
		try{
			stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery(query.getSyntax());
			ElearningReportService self = new ElearningReportService();
			while(rs.next() && rs != null){ 				
				DepartmentInStoreData data = self.new DepartmentInStoreData(
						       rs.getString("department"),
						       "",
						       rs.getString("empLName"),
						       rs.getString("empFName"),
							   rs.getString("empId"),
							   "",
							   "",
							   "",
							   "",
							   "",
							   ""
							    );				
				other_map.add(data.getDepartment(),data);
			}
			extra_Container.put(storeNumber,other_map);
		}catch (SQLException e) {
			log.error(e);
			try {
				if (stmt != null) stmt.close();			
			} catch (SQLException sqle) {
				log.error(e);
			}
		}
		return extra_Container;
	}
	/**
	 * Data container for the employee department data
	 */
	public class DepartmentInStoreData implements Comparable{
		
		private String department;
		private String depSymbol;
		private String empName;
		private String score;
		private String dateTaken;
		private String courseName;
		private String courseId;
		private String storeName;
		private String numOfEmployees="0";
		private String empLastName;
		private String empFirstName;
		private String empId;
		
		private DepartmentInStoreData(String department, String depSymbol,
				                     String empLastName, String empFirstName,
				                     String empId, String score, String dateTaken,
				                     String courseName, String courseId,
				                     String storeName, String numOfEmployees) {
			this.department = department;
			this.depSymbol = depSymbol;
			this.empLastName = empLastName ;
			this.empFirstName = empFirstName;
			this.empId = empId;
			this.score = score;
			this.dateTaken = dateTaken;
			this.courseName = courseName;
			this.courseId = courseId;
			this.storeName = storeName;
			this.numOfEmployees = numOfEmployees;
			this.empName = this.empLastName+", "+this.empFirstName+" "+this.empId;
		}

		public String getDepartment(){return this.department !=null?this.department:"";}
		public String getDepSymbol(){return this.depSymbol !=null?this.depSymbol:"";}
		public String getEmpName(){return this.empName !=null?this.empName:"";}
		public String getScore(){return this.score != null?this.score:"";}
		public String getDateTaken(){return this.dateTaken!=null?this.dateTaken:"";}
		public String getCourseName(){return this.courseName!=null?this.courseName:"";}
		public String getCourseId(){return this.courseId !=null?this.courseId:"";}
		public String getStoreName(){return this.storeName !=null?this.storeName:"";}
		public String getNumOfEmployees(){return this.numOfEmployees !=null?this.numOfEmployees:"";}
		public String getEmpFirstName() {return this.empFirstName !=null?this.empFirstName:"";}
		public void setEmpFirstName(String empFirstName) {this.empFirstName = empFirstName;}
		public String getEmpId() {return this.empId !=null?this.empId:"";}
		public void setEmpId(String empId) {this.empId = empId;	}
		public String getEmpLastName() {return this.empLastName != null?this.empLastName:"";}
		public void setEmpLastName(String empLastName) {this.empLastName = empLastName;	}
		
		public String toString(){
			return this.empName+" course: "+this.courseName+" dept: "+this.department;
		}
		
		public int compareTo(Object o) {
			int result = 0;
			if (o != null && o instanceof DepartmentInStoreData) {
				DepartmentInStoreData empDeptData = (DepartmentInStoreData) o;
				result = this.empLastName.compareTo(empDeptData.getEmpLastName());
				if (result == 0) {// same last name, compare first names
					result = this.empFirstName.compareTo(empDeptData
							.getEmpFirstName());
					if (result == 0) {// same first names, compare ID's
						result = this.empId.compareTo(empDeptData.getEmpId());
					}
				}
			}
			return result;
		}			
	}
	
	/** creates the String for querying the SumTotal DB for the Jobs that end with
	 *  the given job abbreviations 
	 * @param jobs
	 * @return
	 */
	private static String setJobs(String[] jobs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < jobs.length ; i++) {
			sb.append("Job_Name LIKE '% "+jobs[i]+"'");
			if (i != (jobs.length-1)) {
				sb.append(" OR ");
			}
		}
		return sb.toString();
	}
}
