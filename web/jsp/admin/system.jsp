<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.system"/></title>
</head>
<body>
<html:form action="/secure/admin/deleteForms.do" method="POST">
<table class="data">
    <tr>
        <th colspan="2"><fmt:message key="header.menu.admin.system"/></th>
    </tr><tr>
        <td colspan="2"><fmt:message key="admin.system.deletetempfiles"/></td>
    </tr><tr>
        <td><fmt:message key="admin.system.label.date"/>:</td>
        <td>
            <html:text property="date" readonly="true" styleId="date"/>
            <html:img styleId="dateCal" page="/images/cal.gif" style="cursor: pointer;"/>
            <jsp:include page="/jsp/includes/calendarSetup.jsp">
                <jsp:param name="inputField" value="date"/>
                <jsp:param name="button" value="dateCal"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
    <td colspan="2" style="text-align:right;"><html:submit/>
    </tr>
</table>
</html:form>
</body>
</html:html>





