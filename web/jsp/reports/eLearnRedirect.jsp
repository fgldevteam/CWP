<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:html>
	<head>
		<title>
			<fmt:message key="report.elearning.title"/>
		</title>
	</head>
	<body>
		<br/>
		<c:choose>
			<%-- if PDF then open in new browser --%>
			<c:when test="${requestScope.reportType == 2}">
				<html:link href="${requestScope.eLearnFileName}" target="_blank">
					<c:out value="Click here to open the generated E-Learning Report"/>
				</html:link>
			</c:when>
			<c:otherwise>
				<html:link href="${requestScope.eLearnFileName}">
					<c:out value="Click here to open the generated E-Learning Report"/>
				</html:link>
			</c:otherwise>
		</c:choose>
		<br/><br/>
		<c:out value="Note: Please save a copy of the report to your local hard drive when opened."/>
		<br/>
		<c:out value="The directory that this report exists in is emptied nightly."/>
	<body>
</html:html>

