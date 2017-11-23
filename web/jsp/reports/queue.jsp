<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:html>
<head><title><fmt:message key="reports.menu.queue"/></title></head>
<body>
<%-- Store Name or Store Drop Down List --%>
<html:form action="/secure/loadReportQueue">
    <table class="data">
        <caption><fmt:message key="report.queue.title"/></caption>
        <tr>
            <td class="label"><fmt:message key="report.queue.store"/></td>
            <td>
                <jsp:include page="storeList.jsp" flush="true"/>
                <%-- <script>
                    document.forms[0].storeList.onchange=submit();"
                </script> --%>
            </td>
        </tr>
        <tr><td colspan="50" align="right">
            <input type="submit" value="<fmt:message key="report.queue.button.refresh"/>" />
        </td></tr>
    </table>
</html:form>

<%-- Queued Reports --%>
<c:if test="${!empty reportBean.queuedReports}">
    <display:table name="${reportBean.queuedReports}" id="report" class="data" requestURI="/secure/loadReportQueue.do">
        <display:column titleKey="report.queue.reportname">
            <c:choose>
                <c:when test="${report.status == 'P'}">
                    <html:link href="${report.generatedPdfUrl}"><c:out value="${report.name}"/></html:link>
                </c:when>
                <c:otherwise>
                    <c:out value="${report.name}"/>
                </c:otherwise>
            </c:choose>
        </display:column>
        <display:column property="parameters" titleKey="report.queue.reportparams"/>
        <display:column titleKey="report.queue.reportdate" sortable="true" style="width:100px">
            <fmt:message var="dateFormat" key="default.dateformat"/>
            <fmt:formatDate value="${report.modifiedDate}" pattern="${dateFormat}"/>
        </display:column>
        <display:column property="status" titleKey="report.queue.reportstatus"/>
    </display:table>
</c:if>
</body>
</html:html>
