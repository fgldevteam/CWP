<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:html>
<head>
<title><fmt:message key="reports.menu.eLearning"/></title></head>
<body>
<html:form method="POST" action="/report/ELearningReport.do">
	<table class="data">
  	<caption><fmt:message key="report.elearning.title"/></caption>
  	
    	<%-- Store List --%>
		<tr>
			<td class="label"><fmt:message key="report.queue.store"/></td>
			<c:choose>
			    <c:when test="${!empty eLearningReportBean.storeList}">
			    	<td>
			        <html:select property="selectedStoreIds" multiple="true" styleId="storeList" size="5">
			            <html:optionsCollection property="storeList" label="fullName" value="number"/>
			        </html:select>
			     	</td>
			     <%--	<html:hidden property="allStores" value="${eLearningReportBean.storeList}"/> --%>
			    </c:when>
			    <c:when test="${empty eLearningReportBean.storeList}">
			    	<td>
			        <c:out value="${eLearningReportBean.store.fullName}"/>
			      </td>
			      <html:hidden property="aStore" value="${eLearningReportBean.store}"/>			      
			    </c:when>
			</c:choose>
		</tr>
		
		<%-- department --%>
		<tr>
			<td class="label"><fmt:message key="form.label.dept"/></td>
			<td>
				<html:select property="selectedDepartments" multiple="true" styleId="deptList" size="5">
			     <html:optionsCollection property="departments" label="departmentDesc" value="departmentAbbrv"/>
			  </html:select>
			</td>
		</tr>
		<%-- Start Date --%>
        <tr>
            <td class="label"><fmt:message key="report.pricechange.startdate"/></td>
            <td>
                <html:text property="startDate" readonly="true" styleId="startDate"/>
                <html:img styleId="startDateCal" page="/images/cal.gif" style="cursor: pointer;"/>
                <jsp:include page="/jsp/includes/calendarSetup.jsp">
                    <jsp:param name="inputField" value="startDate"/>
                    <jsp:param name="button" value="startDateCal"/>
                </jsp:include>
            </td>
        </tr>
        
        <%-- End Date --%>
        <tr>
            <td class="label"><fmt:message key="report.pricechange.enddate"/></td>
            <td>
                <html:text property="endDate" readonly="true" styleId="endDate"/>
                <html:img styleId="endDateCal" page="/images/cal.gif" style="cursor: pointer;"/>
                <jsp:include page="/jsp/includes/calendarSetup.jsp">
                    <jsp:param name="inputField" value="endDate"/>
                    <jsp:param name="button" value="endDateCal"/>
                </jsp:include>
            </td>
        </tr>
        
        <%-- PDF / Excel format radio buttons--%>
        <tr>            
            <td class="label"><input type="radio" name="method" value="pdf" <%= ( (request.getAttribute("pdf") == null) && (request.getAttribute("excel") == null) ) ? "checked" : request.getAttribute("pdf") %> ><fmt:message key="report.elearning.pdf"/></input></td>
            <td class="label"><input type="radio" name="method" value="excel" <%=  request.getAttribute("excel") %> ><fmt:message key="report.elearning.excel"/></input></td>
        </tr>
     
		<%-- Course List --%>
		<tr>
			<td class="label"><fmt:message key="report.elearning.course"/></td>
	    	<td>
	        <html:select property="selectedCourseIds" multiple="true" styleId="courseList" size="5">
	            <html:optionsCollection property="courses" label="displayName" value="courseId"/>
	        </html:select>
	       <%-- <html:hidden property="coursesList" value="${eLearningReportBean.courses}"/> --%>
	     	</td>  
		</tr>

        <%-- Submit --%>
        <tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key='report.pricechange.button.submit'/>"/>


            </td>
        </tr>
    </table>
</html:form>
<body>
</html:html>

