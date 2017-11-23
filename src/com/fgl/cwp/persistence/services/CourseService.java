package com.fgl.cwp.persistence.services;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.persistence.SessionManager;
import com.fgl.cwp.persistence.db.Constants;
import com.fgl.cwp.persistence.db.DBConnectionManager;

/**
 * @author jmbarga
 */

public class CourseService implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final int SAVE = 1;
	private static final int UPDATE = 2;
	private static final int DELETE = 3;
	private static final String DEFAULT_SHORTNAME = "insert_name_here";

	private static final Log log = LogFactory.getLog(CourseService.class);

	private static boolean persistCourse(SumTotalCourse course, int action) throws Exception{
		boolean success = false;
		Session session = null;
		Transaction tx = null;
		try{
			session = SessionManager.getSessionFactory().openSession();
			tx = session.beginTransaction();
			if (action == DELETE){
				session.delete(course);
			} else if (action == UPDATE) {
				session.update(course);
			} else {
				session.save(course);
			}
			tx.commit();
			success = true;
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
	
	public static void persistCourses(List<SumTotalCourse> courses, int action) {
		for (SumTotalCourse course : courses) {
			try {
				persistCourse(course, action);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
	
	/**
	 * returns the list of all available courses
	 * 
	 * @return list
	 * @throws HibernateException
	 */
	public static List<SumTotalCourse> getAllCourses()
			throws HibernateException {

		Session session = SessionManager.getSessionFactory().openSession();
		Query query = session.getNamedQuery("allCourses");
		List<SumTotalCourse> list = query.list();
		transportCourses(list);		
		return query.list();
	}

	private static void transportCourses(List<SumTotalCourse> existingCwpList) throws HibernateException {
		DBConnectionManager pool = null;
		Connection sum_realCon = null;		
		PreparedStatement sum_pstmt = null;
		ResultSet sumResults = null;

		try {
			pool = DBConnectionManager.getInstance();
			sum_realCon = pool.getConnection(Constants.SUMTOTAL_DB);
			sum_pstmt = sum_realCon.prepareStatement(Constants.SUMTOTAL_SELECTOR);
			sumResults = sum_pstmt.executeQuery();
			SumTotalCourse newCourse = null;
			
			List<SumTotalCourse> updatedCoursesList = new ArrayList<SumTotalCourse>();
			List<SumTotalCourse> newCoursesList = new ArrayList<SumTotalCourse>();
			
			int sumCourseId = -1;
			String courseName = null;
			String code = null;
			while (sumResults.next()) {
				boolean found = false;
				sumCourseId = sumResults.getInt("courseId");
				courseName = sumResults.getString("courseName");
				// original_name field in CWP DB is 255 chars and display_name
				// is 50. Take substring of courseName if necessary
				if (courseName.length() > 255) {
					courseName = courseName.substring(0, 255);
				}
				for(SumTotalCourse cwpCourse : existingCwpList) {
					if (cwpCourse.getCourseId() == sumCourseId){
						if (!cwpCourse.getOriginalName().equals(courseName)){
							cwpCourse.setOriginalName(courseName);
							updatedCoursesList.add(cwpCourse);
						} 
						existingCwpList.remove(cwpCourse);
						found = true;
						break;
					}
				}
				if (!found) {
					//create new SumTotalCourse, add to final list
					newCourse = new SumTotalCourse();
					newCourse.setCourseId(sumCourseId);
					newCourse.setOriginalName(courseName);
					//default display name to be course name for now
					if (courseName.length() <= 50) {
						newCourse.setDisplayName(courseName);
					} else {
						newCourse.setDisplayName(courseName.substring(0, 50));
					}
					// code is 255 chars in SumTotal but only 20 in CWP
					// substring if necessary
					code = sumResults.getString("code");
					if(code != null){
						if (code.length() <= 20){
							newCourse.setShortName(code);
						} else {
							newCourse.setShortName(code.substring(0, 20));
						}
					}else {
						newCourse.setShortName(DEFAULT_SHORTNAME);
					}
					newCoursesList.add(newCourse);
				}
			}//end results while loop
			//now need to save new courses and delete old ones
			if (newCoursesList.size() > 0) {
				persistCourses(newCoursesList, SAVE);
			}
			if (updatedCoursesList.size() > 0) {
				persistCourses(updatedCoursesList, UPDATE);
			}
			
			if (existingCwpList.size() > 0) {
				//delete these as they are no longer part of the Sum Total course results
				persistCourses(existingCwpList, DELETE);
			}
			
		} catch (SQLException sqle) {
			if (null != sum_realCon) {
				try {
					sum_realCon.rollback();
				} catch (SQLException inSqle) {
					log.error("Unable to rollback : " + inSqle, inSqle);
				}
			}
		} catch (Exception e) {
			if (null != sum_realCon) {
				try {
					sum_realCon.rollback();
				} catch (SQLException inSqle) {
					log.error("Unable to rollback : " + inSqle, inSqle);
				}
			}
		} finally {
			if (sum_realCon != null) {
				pool.freeConnection(Constants.SUMTOTAL_DB, sum_realCon);
			}
		}
	}

	public static boolean saveCourses(List courseCodes, List originalNames, List displayNames,
			List shortNames, List reportables) throws HibernateException,
			SQLException {
		Session session = SessionManager.getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn
					.prepareStatement("update sum_total_course set display_name = ? , course_shortname = ? , web_reportable = ? where course_id  = ? ");
			conn.setAutoCommit(false);
			int i = 0;
			for (; i < courseCodes.size(); i++) {
				String courseCode = (String) courseCodes.get(i);
				
				String displayName = (String) displayNames.get(i);
				if(displayName.length() > 50) {
					displayName = displayName.substring(0, 50);
				}
				String shortName = (String) shortNames.get(i);
				if(shortName.length() > 20) {
					shortName = shortName.substring(0, 20);
				}
				
				if (displayName == null || displayName == ""
						|| displayName.equalsIgnoreCase("null")
						|| displayName.equals(null)) {
					displayName = (String) originalNames.get(i);
				}
				pstmt.setString(1, displayName);
				pstmt.setString(2, shortName);
				int ck = check(courseCode, reportables);
				pstmt.setInt(3, ck);
				pstmt.setString(4, (String) courseCodes.get(i));
				pstmt.executeUpdate();
				conn.commit();
			}
		} catch (BatchUpdateException b) {
			log.error(" BatchUpdateException : ", b);
		} catch (SQLException sqle) {
			log.error("SQLException : ", sqle);
		} catch (Exception e) {
			log.error("Exception : ", e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error("SQLException : " + e);
				}
			}
			if (session != null)
				session.close();
		}
		return true;
	}

	private static int check(String course, List courses) {
		if (courses != null) {
			for (int i = 0; i < courses.size(); i++) {
				if (((String) courses.get(i)).equalsIgnoreCase(course))
					return 1;
			}
		}
		return 0;
	}
}
