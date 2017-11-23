<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:html>
<head><title><fmt:message key="reports.menu.itemprice"/></title></head>
<body>
<html:form action="/secure/submitItemPriceChangeReport.do">
	<fmt:message var="dateFormat" key="default.dateformat"/>
	<html:hidden property="dateFormat" value="MM/dd/yyyy"/>
    <table class="data">
        <caption><fmt:message key="report.pricechange.title"/></caption>
        <%-- Store List --%>
        <tr>
            <td class="label"><fmt:message key="report.queue.store"/></td>
            <td>
                <jsp:include page="storeList.jsp" flush="true"/>
            </td>
        </tr>
        <%-- Start Date --%>
        <tr>
            <td class="label"><fmt:message key="report.pricechange.startdate"/></td>
            <td>
            	<c:choose>
            		<c:when test="${empty reportBean.startDate}">
            			<c:set var="currentStartDate" value="${reportBean.currentDate}"/>
            		</c:when>
            		<c:otherwise>
            			<c:set var="currentStartDate" value="${reportBean.startDate}"/>
            		</c:otherwise>
            	</c:choose>
            	
	            <html:text property="startDate" readonly="true" styleId="startDate" value="${currentStartDate}"/>
	            <html:img styleId="startDateCal" page="/images/cal.gif" style="cursor: pointer;"/>
	            <jsp:include page="/jsp/includes/calendarSetup.jsp">
	                <jsp:param name="inputField" value="startDate"/>
	                <jsp:param name="button" value="startDateCal"/>
	            </jsp:include>
            </td>
        </tr>
        <%-- End Date --%>
        <tr>
            <td class="label"><fmt:message key="report.pricechange.enddate"/></td>
            <td>
            	<c:choose>
            		<c:when test="${empty reportBean.endDate}">
            			<c:set var="currentEndDate" value="${reportBean.currentDate}"/>
            		</c:when>
            		<c:otherwise>
            			<c:set var="currentEndDate" value="${reportBean.endDate}"/>
            		</c:otherwise>
            	</c:choose>
	            <html:text property="endDate" readonly="true" styleId="endDate" value="${currentEndDate}"/>
	            <html:img styleId="endDateCal" page="/images/cal.gif" style="cursor: pointer;"/>
	            <jsp:include page="/jsp/includes/calendarSetup.jsp">
	                <jsp:param name="inputField" value="endDate"/>
	                <jsp:param name="button" value="endDateCal"/>
	            </jsp:include>
            </td>
        </tr>
        <%-- Mark Up/Down --%>
        <tr>
            <td>
                <table>
                    <tr><td class="label">
                        <html:radio property="mark" value="up"/><fmt:message key="report.pricechange.markup"/>
                    </td></tr>
                    <tr><td class="label">
                        <html:radio property="mark" value="down"/><fmt:message key="report.pricechange.markdown"/>
                    </td></tr>
                </table>
            </td>
        </tr>
        <%-- Submit --%>
        <tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key='report.pricechange.button.submit'/>"/>
            </td>
        </tr>
    </table>
</html:form>
<body>
</html:html>

