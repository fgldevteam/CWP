package com.fgl.cwp.model.sumtotal.eLearnReport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fgl.cwp.common.util.ExcelFormatter;
import com.fgl.cwp.common.util.SumMultiMap;
import com.fgl.cwp.model.Store;
import com.fgl.cwp.model.SumTotalCourse;
import com.fgl.cwp.model.sumtotal.ReportData;

/**
 * Top Level data container used for the PDF version of the ELearning report.
 * Parses the {@link SumMultiMap} report data into data container objects that
 * are then used for building the PDF version of the E-Learning report.
 * 
 * @author gdelve
 */
public class ELearnReportData {

	private List<ELearnStore> storeData;

	private List<ELearnCourse> courses;

	private Map<String, Integer> employeeCourseCount;

	private Map<String, String> reportCourseSaturation;

	private Date startDate;

	private Date endDate;

	private int totalEmployeeCount;

	private DecimalFormat decimalFormat;
	
	private ExcelFormatter excelFormatter;

	public ELearnReportData(SumMultiMap dataMap, List<SumTotalCourse> courses,
			List stores) {
		this.courses = convertCourses(courses);
		this.decimalFormat = new DecimalFormat("#0.0");
		storeData = new ArrayList<ELearnStore>();
		employeeCourseCount = new HashMap<String, Integer>();
		reportCourseSaturation = new HashMap<String, String>();
		excelFormatter = new ExcelFormatter();
		populateStoreData(dataMap, stores);
		Collections.sort(storeData);
	}

	/**
	 * Builds and populates the {@link ELearnStore} objects for the report
	 * 
	 * @param dataMap
	 *            The store data
	 */
	private void populateStoreData(SumMultiMap dataMap, List stores) {
		Map map = dataMap.getMap();
		Iterator iterValues = map.entrySet().iterator();
		Entry mapEntry = null;
		Integer multiKey = null;
		Collection values = null;
		Object[] objects = null;
		List storeInfoData = null;
		ELearnStore store = null;
		List storeDataList = null;
		SumMultiMap combinedMap = null;
		
		while (iterValues.hasNext()) {
			mapEntry = (Entry) iterValues.next();
			multiKey = (Integer) mapEntry.getKey();
			store = new ELearnStore(this, courses, this.decimalFormat);
			store.setStoreNumber(multiKey);
			values = (Collection) map.get(multiKey);
			objects = values.toArray();
			storeDataList = (List) objects[0];
			storeInfoData = (List) storeDataList.get(1);
			combinedMap = excelFormatter.consolidateMap((Map) storeDataList.get(0),
					(Map) storeDataList.get(2));
			if (storeInfoData.size() > 0) {
				populateStore((ReportData) storeInfoData.get(0), store);
				store.populateDepartmentData(combinedMap, multiKey.toString()); 
			} else {
				store.setTotalEmployeeCount(calculateTotalEmployeeCount(combinedMap));
				setEmptyStoreName(store, stores, multiKey.intValue());
				if(store.getTotalEmployeeCount() > 0){
					store.populateDepartmentData(combinedMap, multiKey.toString());
				}
			}
			storeData.add(store);
		}
		// now calculate the saturation for each course and total of all store
		// employees
		calculateSaturation();
	}

	private void populateStore(ReportData reportData, ELearnStore store) {
		store.setStoreName(reportData.getStore());
		store.setTotalEmployeeCount(reportData.getNumberEmp());
		store.setDistrictMgr(reportData.getDistManager());
		store.setRegionalMgr(reportData.getRegManager());
		store.setVicePres(reportData.getVicePresident());
	}

	private List<ELearnCourse> convertCourses(List<SumTotalCourse> sumCourses) {
		List<ELearnCourse> eLearnCourses = new ArrayList<ELearnCourse>();
		ELearnCourse selectedCourse = null;
		SumTotalCourse course = null;
		String courseName = null;
		for (int i = 0; i < sumCourses.size(); i++) {
			course = (SumTotalCourse) sumCourses.get(i);
			courseName = course.getShortName();
			// if no shortname specified use substring of the display name
			if (courseName == null || courseName.length() == 0) {
				courseName = course.getDisplayName().substring(0, 10);
			}
			else {
				if(courseName.length() > 10){
					courseName = courseName.substring(0, 10);
				}	
			}
			selectedCourse = new ELearnCourse("" + course.getCourseId(),
					courseName);
			eLearnCourses.add(selectedCourse);
		}
		Collections.sort(eLearnCourses);
		return eLearnCourses;
	}

	/**
	 * Updates the count of the number of employees that have taken the
	 * particular course
	 * 
	 * @param courseId
	 */
	void updateCoursesTaken(String courseId) {
		Integer count = (Integer) this.employeeCourseCount.get(courseId);
		if (count == null) {
			this.employeeCourseCount.put(courseId, new Integer(1));
		} else {
			this.employeeCourseCount.put(courseId, new Integer(
					count.intValue() + 1));
		}
	}

	/**
	 * Calculates the saturation value for each course, that at least one
	 * employee has taken, for this store. Number of employees who took the
	 * course (for all departments) / the total number of employees in the store
	 */
	private void calculateSaturation() {
		Integer storeCount = null;
		for (int i = 0; i < storeData.size(); i++) {
			storeCount = ((ELearnStore) storeData.get(i))
					.getTotalEmployeeCount();
			if (storeCount != null) {
				totalEmployeeCount += storeCount.intValue();
			}
		}

		String courseId = null;
		Integer employeeCourseCount = null;
		for (int i = 0; i < courses.size(); i++) {
			courseId = ((ELearnCourse) courses.get(i)).getCourseId();
			employeeCourseCount = (Integer) this.employeeCourseCount
					.get(courseId);
			if (employeeCourseCount == null) {
				// no employees passed this course
				this.employeeCourseCount.put(courseId, new Integer(0));
				this.reportCourseSaturation.put(courseId, "0.0");
			} else {
				if (totalEmployeeCount > 0) {
					this.reportCourseSaturation.put(courseId, decimalFormat
							.format(employeeCourseCount.doubleValue()
									/ totalEmployeeCount * 100));
				}
			}
		}
	}

	public List<ELearnStore> getStoreData() {
		return storeData;
	}

	public void setStoreData(List<ELearnStore> storeData) {
		this.storeData = storeData;
	}

	public List<ELearnCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<ELearnCourse> selectedCourses) {
		this.courses = selectedCourses;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Map<String, Integer> getEmployeeCourseCount() {
		return employeeCourseCount;
	}

	public void setEmployeeCourseCount(Map<String, Integer> employeeCourseCount) {
		this.employeeCourseCount = employeeCourseCount;
	}

	public Map<String, String> getReportCourseSaturation() {
		return reportCourseSaturation;
	}

	public void setReportCourseSaturation(
			Map<String, String> reportCourseSaturation) {
		this.reportCourseSaturation = reportCourseSaturation;
	}

	public int getTotalEmployeeCount() {
		return totalEmployeeCount;
	}

	public void setTotalEmployeeCount(int totalEmployeeCount) {
		this.totalEmployeeCount = totalEmployeeCount;
	}
	
	public int getTotalStoreCount(){
		return this.storeData.size();
	}
	
	private void setEmptyStoreName(ELearnStore emptyStore, List stores,
			int storeNumber) {
		Store noDataStore = null;
		for (int i = 0; i < stores.size(); i++) {
			noDataStore = (Store) stores.get(i);
			if (noDataStore.getNumber() == storeNumber) {
				emptyStore.setStoreName(storeNumber + " "
						+ noDataStore.getName());
				break;
			}
		}
	}
	
	private Integer calculateTotalEmployeeCount(SumMultiMap combinedDeptMap){
		Set depts = combinedDeptMap.getMap().entrySet();
		Set deptValue = null;
		Iterator deptIterator = depts.iterator();
		Entry entry = null;
		int employeeCount = 0;
		while(deptIterator.hasNext()){
			entry = (Entry)deptIterator.next();
			deptValue = (Set)entry.getValue();
			employeeCount += deptValue.size();
		}
		return new Integer(employeeCount);
	}
}
