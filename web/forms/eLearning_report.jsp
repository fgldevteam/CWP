<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">
<pdf>
	<jsp:useBean id="today" class="java.util.Date"/>
    <fmt:formatDate var="today" value="${today}" pattern="MM/dd/yyyy"/>
    
    <head>
    	<%--link type="stylesheet" src="../styles/pdf.css"/--%>
      <%@ include file="../pdf/report_style.jspf" %>
    </head>
        
    <body size="letter-landscape">
	    <c:forEach items="${eLearnReportData.storeData}" var="store"><%--begin store loop--%>		
				<%-- course headings and score / date taken --%>
				<table class="container" colspan="26">
				
					<thead>
						<%-- row for table for the main heading --%>
						<tr>
							<td colspan="26">
								<table class="pageHeader">
									<tr>
										<td width="20%">
											<fmt:message key="report.pricechange.date"/> ${today}
										</td>
										<td width="60%" rowspan="2" class="pageTitle" align="center">
											<fmt:message key="report.elearning.title"/>
										</td>
										<td width="20%" align="right">
											<fmt:message key="report.pricechange.pageNumber"/> <pagenumber />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					
						<%-- row for table for the dates --%>
						<tr>
							<td colspan="26">
								<table class="container"><%-- the table for the report from and to date --%>
									<tr>
										<td>
											<span style="font-weight:bold;">
												<c:choose>
													<c:when test="${!empty eLearnReportData.startDate && !empty eLearnReportData.endDate}">
														<fmt:message key="report.pricechange.dates">
															<fmt:param value="${eLearnReportData.startDate}"/>
															<fmt:param value="${eLearnReportData.endDate}"/>
														</fmt:message>
													</c:when>
													<c:when test="${empty eLearnReportData.startDate && !empty eLearnReportData.endDate}">
														<fmt:message key="report.package.endDateOnly">
															<fmt:param value="${eLearnReportData.endDate}"/>
														</fmt:message>
													</c:when>
													<c:when test="${!empty eLearnReportData.startDate && empty eLearnReportData.endDate}">
														<fmt:message key="report.package.startDateOnly">
															<fmt:param value="${eLearnReportData.startDate}"/>
														</fmt:message>
													</c:when>
													<c:otherwise>
														<fmt:message key="report.package.noDates"/>
													</c:otherwise>
												</c:choose>
											</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
	
						<%-- row table for the store info --%>
						<tr>
							<td colspan="26">
								<table width="100%" colspan="13" class="divisionHeader">
									<tr>
										<td colspan="1" font-size="12" align="left">
											<fmt:message key="form.storeList.label.name"/>
										</td>
										<td colspan="3" font-size="12">
											<c:out value="${store.storeName}"/>
										</td>
										<td colspan="9" rowspan="2" font-size="12" font-weight="bold" valign="middle" align="center">
											<c:out value="Courses"/>
										</td>
									</tr>
									<tr>
										<td colspan="1" font-size="12" align="left">
											<fmt:message key="admin.pricingissues.label.storenumber"/>
										</td>
										<td colspan="3" font-size="12">
											<c:out value="${store.storeNumber}"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<%-- row for the course name / score / date taken header --%>
						<tr>
							<td align="center" colspan="6" rowspan="2">&nbsp;</td>
							<c:forEach items="${eLearnReportData.courses}" var="course">
								<td class="courseHeading" align="center" colspan="2">
									<c:out value="${course.courseName}"/>
								</td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach items="${eLearnReportData.courses}" var="course">
								<td border="1" colspan="1" align="center">
									<c:out value="%"/>
								</td>
								<td border="1" colspan="1" align="center">
									<c:out value="Date"/>
								</td>
							</c:forEach>
						</tr>
					<%-- empty row for spacing purposes --%>
						<tr>
							<td colspan="13">&nbsp;</td>
						</tr>
						
					</thead>
					
					<tbody>
					<c:choose>
						<c:when test="${empty store.departments}">
							<tr>
								<td colspan="26">
									<c:out value="No data found for selected courses for this store"/>
								</td>
							</tr>
						</c:when>
						<c:otherwise><%-- this is where the rows will be created when data found --%>
							<c:forEach items="${store.departments}" var="dept"><%--begin department loop--%>
								<tr>
									<td colspan="26" class="department">
										<c:out value="${dept.departmentName}"/>
									</td>
								</tr>
								<c:set var="rowClass" value="deptCourseCount"/>
								<c:forEach items="${dept.elearnEmployees}" var="employee"><%--begin employee loop--%>
									<tr class="${rowClass}">
										<td colspan="6">
											<c:out value="${employee.employeeName}"/>
										</td>
										<c:forEach items="${eLearnReportData.courses}" var="course"><%-- begin course loop --%>
											<c:set var="employeeCourse" value="${employee.courses[course.courseId]}"/>
											<c:choose>
												<c:when test="${empty employeeCourse}">
													<td colspan="1" align="center">&nbsp;</td>
													<td colspan="1" align="center">&nbsp;</td>
												</c:when>
												<c:otherwise>
													<td colspan="1" align="center">
														<c:out value="${employeeCourse.score}"/>
													</td>
													<td colspan="1" align="center">
														<c:out value="${employeeCourse.dateTaken}"/>
													</td>
												</c:otherwise>
											</c:choose>
										</c:forEach><%-- end course loop --%>
									</tr>
									<c:choose>
										<c:when test="${rowClass == 'deptCourseCount'}">
											<c:set var="rowClass" value="empRow"/>
										</c:when>
										<c:otherwise>
											<c:set var="rowClass" value="deptCourseCount"/>
										</c:otherwise>
									</c:choose>
								</c:forEach><%--end employee loop--%>
								<%-- now do the summary info for the department --%>
								<tr>
									<td class="deptTotal" colspan="26">
										<c:out value="Total Employees in Department: ${dept.deptEmployeeCount}"/>
									</td>
								</tr>
								<tr class="deptCourseCount"><%-- row for the dept/employee course taken count --%>
									<td class="deptCourseTotal" colspan="6">
										<c:out value="Total Employees for Course:"/>
									</td>
									<c:forEach items="${eLearnReportData.courses}" var="course"><%-- begin dept/course loop --%>
										<c:set var="deptCourseCount" value="${dept.employeeCourseCount[course.courseId]}"/>
										<c:choose>
											<c:when test="${empty deptCourseCount}">
												<td colspan="2" align="center">0</td>
											</c:when>
											<c:otherwise>
												<td class="deptCourseTotal" colspan="2" align="center">
													<c:out value="${deptCourseCount}"/>
												</td>
											</c:otherwise>
										</c:choose>
									</c:forEach><%-- end dept/course loop --%>
								</tr>
								
								<tr><%-- row for the department saturation totals --%>
									<td class="deptSaturation" colspan="6">
										<c:out value="Saturation for Course (%):"/>
									</td>
									<c:forEach items="${eLearnReportData.courses}" var="course"><%-- begin dept/saturation loop --%>
										<c:set var="deptSatValue" value="${dept.deptCourseSaturation[course.courseId]}"/>
										<c:choose>
											<c:when test="${empty deptSatValue}">
												<td colspan="2" align="center">0.0</td>
											</c:when>
											<c:otherwise>
												<td class="deptSaturation" colspan="2" align="center">
													<c:out value="${deptSatValue}"/>
												</td>
											</c:otherwise>
										</c:choose>
									</c:forEach><%-- end dept/saturation loop --%>
								</tr>
							</c:forEach><%--end department loop--%>
							<tr><%-- now create the store total rows --%>
								<td class="storeTotal" colspan="26">
									<c:out value="Store Totals"/>
								</td>
							</tr>
							<tr>
								<td class="deptCourseTotal" colspan="26">
									<c:out value="Total Employees in Store: ${store.totalEmployeeCount}"/>
								</td>
							</tr>
							<tr class="deptCourseCount">
								<td class="deptCourseTotal" colspan="6">
									<c:out value="Total Employees for Course:"/>
								</td>
								<c:forEach items="${eLearnReportData.courses}" var="course"><%-- begin store/course loop --%>
									<c:set var="storeCourseCount" value="${store.employeeCourseCount[course.courseId]}"/>
									<c:choose>
										<c:when test="${empty storeCourseCount}">
											<td colspan="2" align="center">0</td>
										</c:when>
										<c:otherwise>
											<td class="deptCourseTotal" colspan="2" align="center">
												<c:out value="${storeCourseCount}"/>
											</td>
										</c:otherwise>
									</c:choose>
								</c:forEach><%-- end store/course loop --%>
							</tr>
							<tr><%-- row for the store saturation totals --%>
								<td class="deptSaturation" colspan="6">
									<c:out value="Saturation for Course (%):"/>
								</td>
								<c:forEach items="${eLearnReportData.courses}" var="course"><%-- begin store/saturation loop --%>
									<c:set var="storeSatValue" value="${store.storeCourseSaturation[course.courseId]}"/>
									<c:choose>
										<c:when test="${empty storeSatValue}">
											<td colspan="2" align="center">0.0</td>
										</c:when>
										<c:otherwise>
											<td class="deptSaturation" colspan="2" align="center">
												<c:out value="${storeSatValue}"/>
											</td>
										</c:otherwise>
									</c:choose>
								</c:forEach><%-- end store/saturation loop --%>
							</tr>
						</c:otherwise><%-- end of the rows when data found --%>
					</c:choose>
					</tbody>
				</table><%-- end of the course/dept/employee table --%>
			
				<c:choose>
					<c:when test="${eLearnReportData.totalStoreCount > 1}">
						<pbr/>
					</c:when>
					<c:otherwise/>
				</c:choose>
			</c:forEach><%--end store loop--%>
			
			<%-- now build the store summary page if more than one store--%>
			<c:choose>
				<c:when test="${eLearnReportData.totalStoreCount > 1}">
				
				<table class="container" colspan="26">
					<thead>
					
						<%-- row for table for the main heading --%>
						<tr>
							<td colspan="26">
								<table class="pageHeader">
									<tr>
										<td width="20%">
											<fmt:message key="report.pricechange.date"/> ${today}
										</td>
										<td width="60%" rowspan="2" class="pageTitle" align="center">
											<fmt:message key="report.elearning.title"/>
										</td>
										<td width="20%" align="right">
											<fmt:message key="report.pricechange.pageNumber"/> <pagenumber />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					
						<tr>
							<td colspan="26">
								<table class="container"><%-- the table for the report from and to date --%>
										<tr>
											<td colspan="26">
												<span style="font-weight:bold;">
													<c:choose>
														<c:when test="${!empty eLearnReportData.startDate && !empty eLearnReportData.endDate}">
															<fmt:message key="report.pricechange.dates">
																<fmt:param value="${eLearnReportData.startDate}"/>
																<fmt:param value="${eLearnReportData.endDate}"/>
															</fmt:message>
														</c:when>
														<c:when test="${empty eLearnReportData.startDate && !empty eLearnReportData.endDate}">
															<fmt:message key="report.package.endDateOnly">
																<fmt:param value="${eLearnReportData.endDate}"/>
															</fmt:message>
														</c:when>
														<c:when test="${!empty eLearnReportData.startDate && empty eLearnReportData.endDate}">
															<fmt:message key="report.package.startDateOnly">
																<fmt:param value="${eLearnReportData.startDate}"/>
															</fmt:message>
														</c:when>
														<c:otherwise>
															<fmt:message key="report.package.noDates"/>
														</c:otherwise>
													</c:choose>
												</span>
											</td>
										</tr>
									</table><%-- the table for the report from and to date --%>
								</td>
							</tr>
								
							<tr>
								<td colspan="26">	
									<%-- table for the storename and course header --%>
									<table width="100%" colspan="13" class="divisionHeader">
										<tr>
											<td rowspan ="2" font-size="12" padding="5px">
												<c:out value="E-Learning Report Store Summary"/>
											</td>
										</tr>
									</table><%-- end of the store / course header --%>
								</td>
							</tr>
						
							<%--table class="container" colspan="26"--%>
							<tr>
								<td class="courseHeading" align="center" colspan="4" rowspan="2">
									<c:out value="Store"/>
								</td>
								<td class="courseHeading" align="center" colspan="2" rowspan="2">
									<c:out value="Total Employees"/>
								</td>
								<c:forEach items="${eLearnReportData.courses}" var="course">
									<td class="courseHeading" align="center" colspan="2">
										<c:out value="${course.courseName}"/>
									</td>
								</c:forEach>
							</tr>
							<tr>
								<c:forEach items="${eLearnReportData.courses}" var="course">
									<td border="1" colspan="1" align="center">
										<c:out value="%"/>
									</td>
									<td border="1" colspan="1" align="center">
										<c:out value="#"/>
									</td>
								</c:forEach>
							</tr>
							
						</thead>
						<tbody>
							
							<tr>
								<td colspan="26"></td>
							</tr>
							<%-- one row for each store --%>
							<c:set var="rowClass" value="deptCourseCount"/>
							<c:forEach items="${eLearnReportData.storeData}" var="store"><%--begin store loop --%>
								<tr class="${rowClass}">
									<td class="store" colspan="4">
										<c:out value="${store.storeName}"/>
									</td>
									<td class="store" align="center" colspan="2">
										<c:out value="${store.totalEmployeeCount}"/>
									</td>
									<c:forEach items="${eLearnReportData.courses}" var="course"><%-- begin course loop --%>
										<c:set var="storeCourseSat" value="${store.storeCourseSaturation[course.courseId]}"/>
											<c:choose>
												<c:when test="${empty storeCourseSat}">
													<td class="store" colspan="1" align="center">0.0</td>
													<td class="store" colspan="1" align="center">0</td>
												</c:when>
												<c:otherwise>
													<td class="store" colspan="1" align="center">
														<c:out value="${storeCourseSat}"/>
													</td>
													<td class="store" colspan="1" align="center">
														<c:out value="${store.employeeCourseCount[course.courseId]}"/>
													</td>
												</c:otherwise>
											</c:choose>
									</c:forEach><%-- end course loop --%>
								</tr>
								<c:choose>
									<c:when test="${rowClass == 'deptCourseCount'}">
										<c:set var="rowClass" value="empRow"/>
									</c:when>
									<c:otherwise>
										<c:set var="rowClass" value="deptCourseCount"/>
									</c:otherwise>
								</c:choose>
							</c:forEach><%--end store loop --%>
							<%-- totals for all stores --%>
							<tr>
								<td class="storeSumTotal" colspan="4">
									<c:out value="Totals for all Stores:"/>
								</td>
								<td class="storeSumTotal" colspan="2" align="center">
									<c:out value="${eLearnReportData.totalEmployeeCount}"/>
								</td>
								<c:forEach items="${eLearnReportData.courses}" var="course">
									<c:set var="repCourseSat" value="${eLearnReportData.reportCourseSaturation[course.courseId]}"/>
									<c:choose>
										<c:when test="${empty repCourseSat}">
											<td class="storeSumTotal" colspan="1" align="center">0.0</td>
											<td class="storeSumTotal" colspan="1" align="center">0</td>
										</c:when>
										<c:otherwise>
											<td class="storeSumTotal" colspan="1" align="center">
												<c:out value="${repCourseSat}"/>
											</td>
											<td class="storeSumTotal" colspan="1" align="center">
												<c:out value="${eLearnReportData.employeeCourseCount[course.courseId]}"/>
											</td>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
							</tbody>
						</table>
					</c:when>
					<c:otherwise/>
				</c:choose>
		</body>
</pdf>