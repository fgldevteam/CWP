<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.users"/></title>
    <script type="text/javascript">
    function confirmDelete() {
        var answer = confirm("<fmt:message key='admin.user.confirm'/>");
        return answer;
    }
    </script>
</head>
<body>
<c:if test="${!empty manageUserBean.users}">
<%int idx=0;%>
<display:table name="${manageUserBean.users}" id="user" class="data">
    <display:column property="id" titleKey="admin.user.label.id"/>
    <display:column property="fullName" titleKey="admin.user.label.name"/>
    <display:column titleKey="admin.user.label.action">
        <%
        String editAction="/secure/admin/editUser.do?userIndex="+idx;
        %>
        <html:link page="<%=editAction%>"><fmt:message key="admin.user.edit"/></html:link>
        <%
        String deleteAction="/secure/admin/deleteUser.do?userIndex="+idx;
        %>
        &nbsp;|&nbsp;<html:link page="<%=deleteAction%>" onclick="return(confirmDelete())"><fmt:message key="admin.user.delete"/></html:link>
        <%
        if (idx++==0) {
        %>
        &nbsp;|&nbsp;<html:link page="/secure/admin/addUser.do"><fmt:message key="admin.user.add"/></html:link>...
        <%
        }%>
    </display:column>
</display:table>
</c:if>
</body>
</html:html>





