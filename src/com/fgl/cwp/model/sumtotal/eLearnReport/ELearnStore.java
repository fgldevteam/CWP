package com.fgl.cwp.model.sumtotal.eLearnReport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fgl.cwp.common.util.SumMultiMap;

/**
 * Contains the data relating to a store such as the store number, number of
 * employess, management information, and data relating to the departments of
 * the store and course saturation numbers.
 * 
 * @author gdelve
 * 
 */
public class ELearnStore implements Comparable {

	private ELearnReportData reportData;

	private Integer storeNumber;

	private String storeName;

	private Integer totalEmployeeCount;

	private String districtMgr;

	private String regionalMgr;

	private String vicePres;

	private List<ELearnDepartment> departments;

	private Map<String, String> storeCourseSaturation;

	private Map<String, Integer> employeeCourseCount;

	private DecimalFormat decimalFormat;

	/**
	 * Constructor
	 * 
	 * @param reportData
	 * @param courses
	 * @param decimalFormat
	 */
	public ELearnStore(ELearnReportData reportData, List courses,
			DecimalFormat decimalFormat) {
		this.reportData = reportData;
		this.decimalFormat = decimalFormat;
		this.departments = new ArrayList<ELearnDepartment>();
		employeeCourseCount = new HashMap<String, Integer>();
		this.storeCourseSaturation = new HashMap<String, String>();
	}

	/**
	 * Builds and populates the {@link ELearnDepartment} objects for this store
	 * 
	 * @param deptData
	 * @param multiKey
	 *            The store number
	 */
	public void populateDepartmentData(SumMultiMap deptData, String multiKey) {
		int deptEmployeeCount = 0;
		ELearnDepartment otherDept = null;
		Map interMap = deptData.getMap();
		Iterator deptIterator = interMap.entrySet().iterator();
		Entry deptEntry = null;
		String deptKey = null;
		ELearnDepartment department = null;
		Collection values = null;
		Object[] objects = null;
		while (deptIterator.hasNext()) {
			department = new ELearnDepartment(this, decimalFormat);
			deptEntry = (Entry) deptIterator.next();
			deptKey = (String) deptEntry.getKey();
			department.setDepartmentName(deptKey);
			values = (Collection) interMap.get(deptKey);
			objects = values.toArray();
			department.populateDeptEmployees(objects);
			this.departments.add(department);
			if (deptKey.equalsIgnoreCase("Other")) {
				otherDept = department;
			} else {
				if(department.getDeptEmployeeCount() != null){
					deptEmployeeCount += department.getDeptEmployeeCount()
						.intValue();
				}
			}
		}
		// set the Other department employee count
		if (otherDept != null) {
			otherDept.setDeptEmployeeCount(new Integer(totalEmployeeCount
					.intValue()
					- deptEmployeeCount));
		}
		// calculate the dept saturation numbers
		ELearnDepartment dept = null;
		for (int i = 0; i < departments.size(); i++) {
			dept = (ELearnDepartment) departments.get(i);
			dept.calculateSaturation();
		}
		// sort the departments by name
		Collections.sort(departments);
		// now calculate the course saturation per store
		calculateSaturation();
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
		reportData.updateCoursesTaken(courseId);
	}

	/**
	 * Calculates the saturation value for each course, that at least one
	 * employee has taken, for this store. Number of employees who took the
	 * course (for all departments) / the total number of employees in the store
	 */
	private void calculateSaturation() {
		Set entries = this.employeeCourseCount.entrySet();
		Iterator iterator = entries.iterator();
		Entry entry = null;
		Integer courseCount = null;
		float saturationValue = -1;
		float storeCount = this.totalEmployeeCount.floatValue();
		while (iterator.hasNext()) {
			entry = (Entry) iterator.next();
			courseCount = (Integer) entry.getValue();
			if (storeCount > 0) {
				saturationValue = courseCount / storeCount * 100;
			} else {
				saturationValue = 100;
			}
			storeCourseSaturation.put((String) entry.getKey(), decimalFormat
					.format(saturationValue));
		}
	}

	public List getDepartments() {
		return departments;
	}

	public void setDepartments(List<ELearnDepartment> departments) {
		this.departments = departments;
	}

	public Integer getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(Integer storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getDistrictMgr() {
		return districtMgr;
	}

	public void setDistrictMgr(String districtMgr) {
		this.districtMgr = districtMgr;
	}

	public String getRegionalMgr() {
		return regionalMgr;
	}

	public void setRegionalMgr(String regionalMgr) {
		this.regionalMgr = regionalMgr;
	}

	public Integer getTotalEmployeeCount() {
		return totalEmployeeCount;
	}

	public void setTotalEmployeeCount(Integer totalEmployeeCount) {
		this.totalEmployeeCount = totalEmployeeCount;
	}

	public String getVicePres() {
		return vicePres;
	}

	public void setVicePres(String vicePres) {
		this.vicePres = vicePres;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Map<String, String> getStoreCourseSaturation() {
		return storeCourseSaturation;
	}

	public void setStoreCourseSaturation(
			Map<String, String> storeCourseSaturation) {
		this.storeCourseSaturation = storeCourseSaturation;
	}

	public Map<String, Integer> getEmployeeCourseCount() {
		return employeeCourseCount;
	}

	public void setEmployeeCourseCount(Map<String, Integer> employeeCourseCount) {
		this.employeeCourseCount = employeeCourseCount;
	}

	public int compareTo(Object o) {
		if (o != null && o instanceof ELearnStore) {
			return this.storeNumber.compareTo(((ELearnStore) o)
					.getStoreNumber());
		}
		return 0;
	}

	public String toString() {
		return this.storeName;
	}
}
