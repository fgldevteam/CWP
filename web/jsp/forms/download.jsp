<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.label.formdownload"/></title>
	    <c:url var="downloadURL" value="/FileDownload/${param.file}">
            <c:param name="inline" value="${param.inline}"/>
            <c:param name="dir" value="${param.dir}"/>
            <c:param name="contentType" value="${param.contentType}"/>
            <c:param name="file" value="${param.file}"/>
        </c:url>
    	<META HTTP-EQUIV="refresh" content="5;URL=<c:out value='${downloadURL}'/>"> 
    </head>
    <body>
    <table class="status">
    <tr><td class="status"><fmt:message key="form.status.sentemail"/></td></tr>
    <tr><td class="status">
    	<fmt:message key="form.status.downloadblurb">
	    	<fmt:param value="${param.file}"/>
    	</fmt:message>
    </td></tr>
    </table>
    </body>
</html>
