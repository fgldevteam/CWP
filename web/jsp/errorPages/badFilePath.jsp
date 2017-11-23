<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% response.setHeader( "Cache-control", "no-store" ); %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<page:applyDecorator name="error">
	<html>
	<body>
	<p><fmt:message key="error.badFilePath"/></p>
	<p><fmt:message key="error.generic.helpfulblurb"/></p>
	</body>
	</html>
</page:applyDecorator>