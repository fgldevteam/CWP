<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% response.setHeader( "Cache-control", "no-store" ); %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>    
    <title><decorator:title/></title>
    <html:base/>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/cwp.css'/>"/>
    <link rel="SHORTCUT ICON" href="<html:rewrite page='/favicon.ico'/>"> 
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="-1"/>
<body>
<table class="popup">
    <tr><td align="center" style="vertical-align:middle;padding:10px;">
        <decorator:body/>
    </td></tr>
</table>
</body>
</html>
