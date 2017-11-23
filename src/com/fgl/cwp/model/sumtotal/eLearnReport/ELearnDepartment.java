package com.fgl.cwp.model.sumtotal.eLearnReport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fgl.cwp.persistence.services.ElearningReportService.DepartmentInStoreData;

/**
 * Data container for each department for the PDF E-Learning report.
 * 
 * @author gdelve
 * 
 */
public class ELearnDepartment implements Comparable {

	public static final String OTHER_DEPT = "Other";

	private ELearnStore store;

	private Integer deptEmployeeCount;

	private String departmentName;

	private List<ELearnEmployee> elearnEmployees;

	private Map<String, Integer> employeeCourseCount;

	private Map<String, String> deptCourseSaturation;

	private DecimalFormat decimalFormat;

	/**
	 * Constructor
	 * 
	 * @param store
	 * @param decimalFormat
	 */
	public ELearnDepartment(ELearnStore store, DecimalFormat decimalFormat) {
		this.store = store;
		this.decimalFormat = decimalFormat;
		this.elearnEmployees = new ArrayList<ELearnEmployee>();
		employeeCourseCount = new HashMap<String, Integer>();
		this.deptCourseSaturation = new HashMap<String, String>();
	}

	/**
	 * Builds and populates the {@link ELearnEmployee} objects for this
	 * department
	 * 
	 * @param employees
	 *            The employee data
	 */
	public void populateDeptEmployees(Object[] employees) {
		// for each object create an ELearnEmployee
		Map<String, Integer> employeeIndexes = new HashMap<String, Integer>();
		DepartmentInStoreData empData = null;
		ELearnEmployee employee = null;
		String employeeId = null;
		Integer index = null;
		for (int i = 0; i < employees.length; i++) {
			empData = (DepartmentInStoreData) employees[i];
			employeeId = empData.getEmpId();
			index = (Integer) employeeIndexes.get(employeeId);
			if (index == null) {
				// create new ELearnEmployee
				employee = new ELearnEmployee(empData, this.decimalFormat);
				elearnEmployees.add(employee);
				employeeIndexes.put(employeeId, new Integer(elearnEmployees
						.size() - 1));
			} else {
				// get exisiting ELearnEmployee
				employee = elearnEmployees.get(index.intValue());
			}
			// only add the course if the courseID is not null and not empty
			// String
			if (empData.getCourseId() != null
					&& empData.getCourseId().length() != 0) {
				employee.addCourseRecord(empData);
				// as each course is added update the running count
				updateCoursesTaken(empData.getCourseId());
			}
		}
		this.deptEmployeeCount = new Integer(elearnEmployees.size());
		// now sort the employees by name
		Collections.sort(elearnEmployees);
	}

	/**
	 * Updates the count of the number of employees that have taken the
	 * particular course
	 * 
	 * @param courseId
	 */
	private void updateCoursesTaken(String courseId) {
		Integer count = (Integer) this.employeeCourseCount.get(courseId);
		if (count == null) {
			this.employeeCourseCount.put(courseId, new Integer(1));
		} else {
			this.employeeCourseCount.put(courseId, new Integer(
					count.intValue() + 1));
		}
		// notify the store to increment its count for the course
		this.store.updateCoursesTaken(courseId);
	}

	/**
	 * Calculates the saturation value for each course, that at least one
	 * employee has taken, for this department. Number of employees who took the
	 * course / the total number of employees in the department
	 */
	void calculateSaturation() {
		Set entries = this.employeeCourseCount.entrySet();
		Iterator iterator = entries.iterator();
		Entry entry = null;
		Integer courseCount = null;
		float saturationValue = -1;
		int deptCount = 0;
		if (this.deptEmployeeCount != null) {
			deptCount = this.deptEmployeeCount.intValue();
		}
		while (iterator.hasNext()) {
			entry = (Entry) iterator.next();
			courseCount = (Integer) entry.getValue();
			if (deptCount > 0) {
				saturationValue = courseCount.floatValue()
						/ this.deptEmployeeCount * 100;
			} else {
				saturationValue = 100;
			}
			deptCourseSaturation.put((String) entry.getKey(), decimalFormat
					.format(saturationValue));
		}
	}

	public Map<String, Integer> getEmployeeCourseCount() {
		return employeeCourseCount;
	}

	public void setEmployeeCourseCount(Map<String, Integer> employeeCourseCount) {
		this.employeeCourseCount = employeeCourseCount;
	}

	public Map<String, String> getDeptCourseSaturation() {
		return deptCourseSaturation;
	}

	public void setDeptCourseSaturation(Map<String, String> deptCourseSaturation) {
		this.deptCourseSaturation = deptCourseSaturation;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List getElearnEmployees() {
		return elearnEmployees;
	}

	public void setElearnEmployees(List<ELearnEmployee> eLearnEmployees) {
		this.elearnEmployees = eLearnEmployees;
	}

	public Integer getDeptEmployeeCount() {
		return deptEmployeeCount;
	}

	public void setDeptEmployeeCount(Integer deptEmployeeCount) {
		this.deptEmployeeCount = deptEmployeeCount;
	}

	public String toString() {
		return this.departmentName;
	}

	public int compareTo(Object o) {
		if (o != null && o instanceof ELearnDepartment) {
			String deptName = ((ELearnDepartment) o).getDepartmentName();
			// the Other department should always be last
			if (this.departmentName.equalsIgnoreCase(OTHER_DEPT)
					&& !deptName.equalsIgnoreCase(OTHER_DEPT)) {
				return 1;
			} else if (!this.departmentName.equalsIgnoreCase(OTHER_DEPT)
					&& deptName.equalsIgnoreCase(OTHER_DEPT)) {
				return -1;
			}

			return this.departmentName.compareTo(deptName);
		}
		return 0;
	}
}
