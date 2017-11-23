<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% response.setHeader( "Cache-control", "no-store" ); %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> --%>
<%--
some reason the struts-menu does not like the DOCTYPE specification
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
--%>
<%--!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN"--%>

<html:html locale="true" xhtml="true">
<head>
    <title><decorator:title default="Corporate Web Portal" /></title>
    <decorator:head/>
    <!--html:base/-->
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/cwp.css'/>"/>
    <link rel="SHORTCUT ICON" href="<html:rewrite page='/favicon.ico'/>"> 
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="-1"/>

    <%-- Popup Calendar Components --%>
    <script type="text/javascript" src="<html:rewrite page='/scripts/calendar.js'/>"></script>
    <script type="text/javascript" src="<html:rewrite page='/scripts/calendar-en.js'/>"></script>
    <script type="text/javascript" src="<html:rewrite page='/scripts/calendar-setup.js'/>"></script>
    <link href="<html:rewrite page='/styles/skins/aqua/theme.css'/>" rel="stylesheet" type="text/css" media="all" title="aqua">

    <script src="<html:rewrite page='/scripts/cwp.js'/>"></script>
</head>
<body onload="setInitialFocus();">
<%@ include file="/includes/header.jsp"%>
<%@ include file="/includes/loginInfo.jsp"%>
<span id="menu"></span>
<jsp:include page="/includes/menu.jsp" flush="false"/>
<table class="result">
    <tr><td align="center" style="padding:10px;">
    	<html:errors/>
	    <html:messages id="msg"
				message="true"
				header="status.header"
				footer="status.footer">
			<tr><td class="status"><c:out value="${msg}"/></td></tr>
		</html:messages>
        <decorator:body />
		<jsp:include page="/includes/footer.jsp" flush="false"/>
    </td></tr>
</table>
</body>
</html:html>

