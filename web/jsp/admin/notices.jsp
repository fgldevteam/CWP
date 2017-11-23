<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.notices"/></title>
    <script type="text/javascript">
    function confirmDelete() {
        var answer = confirm("<fmt:message key='admin.notice.confirm'/>");
        return answer;
    }
    </script>
</head>
<body>
<c:if test="${!empty manageNoticeBean.notices}">
<%int idx=0;%>
<display:table name="${manageNoticeBean.notices}" id="notice" class="data">
    <display:column property="id" titleKey="admin.notice.label.id"/>        
    <display:column property="name" titleKey="admin.notice.label.name"/>
    <display:column titleKey="admin.notice.label.action">
        <%
        String editAction="/secure/admin/editNotice.do?noticeIndex="+idx;
        %>
        <html:link page="<%=editAction%>"><fmt:message key="admin.notice.edit"/></html:link>&nbsp;|&nbsp;
        <%
        String deleteAction="/secure/admin/deleteNotice.do?noticeIndex="+idx;
        %>
        <html:link page="<%=deleteAction%>" onclick="return(confirmDelete())"><fmt:message key="admin.notice.delete"/></html:link>
        <%
        // Only display the add link on the first record
        if (idx++==0) {
        %>
        &nbsp;|&nbsp;
        <html:link page="/secure/admin/addNotice.do"><fmt:message key="admin.notice.add"/></html:link>...
        <%}%>
    </display:column>
</display:table>
</c:if>
</body>
</html:html>




