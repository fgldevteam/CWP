<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% response.setHeader( "Cache-control", "no-store" ); %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:html locale="true" xhtml="true">

<fmt:message var="mainTitle" key="header.title"/>
<head>
	<title>		
		<fmt:message key="error.title">
			<fmt:param value="${mainTitle}"/>
		</fmt:message>
	</title>
    <decorator:head/>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/error.css'/>"/>
    <link rel="SHORTCUT ICON" href="<html:rewrite page='/favicon.ico'/>"> 
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="-1"/>
    <decorator:head/>
</head>
<body>
<h2>
	<fmt:message key="error.title">
		<fmt:param value="${mainTitle}"/>
	</fmt:message>
</h2>
<decorator:body/>
<html:link page="/secure/loadMainPage.do"><fmt:message key="error.label.backtomain"/></html:link>
</body>
</html:html>

