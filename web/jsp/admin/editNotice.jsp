<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.notices"/></title></head>
<body>
<html:form action="/secure/admin/saveNotice.do" method="post" enctype="multipart/form-data">
    <table class="data">
        <tr>
            <th colspan="2">
                <c:choose>
                <c:when test="${empty sessionScope.manageNoticeBean.notice.id}">
                    <fmt:message key="admin.notice.label.addNotice"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="admin.notice.label.editNotice"/>
                </c:otherwise>
                </c:choose>
            </th>
        </tr><tr>
            <td class="label"><fmt:message key="admin.notice.label.id"/>:</td>
            <td><c:out value="${sessionScope.manageNoticeBean.notice.id}"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.notice.label.name"/>:</td>
            <td><html:text name="manageNoticeBean" property="notice.name" size="50" maxlength="50"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.notice.label.description"/>:</td>
            <td><html:text name="manageNoticeBean" property="notice.description" size="50" maxlength="200"/></td>
        </tr>
        <c:if test="${not empty sessionScope.manageNoticeBean.notice.id}">
            <tr>
                <td class="label"><fmt:message key="admin.notice.label.currentFile"/>:</td>
                <td><c:out value="${sessionScope.manageNoticeBean.notice.fileName}"/></td>
            </tr>
        </c:if>        
        <tr>
            <td class="label"><fmt:message key="admin.notice.label.newFile"/>:</td>
            <td><html:file name="manageNoticeBean" property="notice.attachment" size="50"/></td>
        </tr><tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key='admin.notice.edit.button.save'/>"/>
                &nbsp;
                <input type="button" value="<fmt:message key='admin.notice.edit.button.cancel'/>" onclick="window.location='<html:rewrite page="/secure/admin/loadNotices.do"/>'"/>
            </td>
        </tr>
    </table>
</html:form>
</body>
</html:html>

