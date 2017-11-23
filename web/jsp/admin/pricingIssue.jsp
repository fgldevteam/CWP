<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.pricingissues"/></title>
</head>
<body>
<fmt:message key="default.dateformat" var="dateFormat"/>
<c:if test="${!empty managePricingIssueBean.pricingIssues}">
<%int idx=0;%>
<display:table name="${managePricingIssueBean.pricingIssues}" id="pricingIssues" class="data"  style="width:600px;">
    <display:column property="id" titleKey="admin.pricingissues.label.id" sortable="true"/>
    <display:column titleKey="admin.pricingissues.label.openeddate" sortable="true">
    	<fmt:formatDate value="${pricingIssues.openedDate}" type="date" pattern="${dateFormat}"/>
    </display:column>        
    <display:column property="styleNum" titleKey="admin.pricingissues.label.stylenumber" sortable="true" />
    <display:column property="upc" titleKey="admin.pricingissues.label.upc" sortable="true" />
    <display:column titleKey="admin.pricingissues.label.currentprice">
    	<fmt:formatNumber value="${pricingIssues.currentPrice}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2"/>
    </display:column>
    <display:column titleKey="admin.pricingissues.label.closeddate" sortable="true">    
    	<fmt:formatDate value="${pricingIssues.closedDate}" type="date" pattern="${dateFormat}"/>
    </display:column>
    <display:column titleKey="admin.pricingissues.label.action">
	<!-- Only display Close link if open -->
	<c:if test="${pricingIssues.closedDate == null}">
	<%
	String closeAction="/secure/admin/closePricingIssue.do?pricingIssueIndex="+idx;
	%>
	<html:link page="<%=closeAction%>"><fmt:message key="admin.pricingissues.label.close"/></html:link>        
	</c:if>
	</display:column>
	<%idx++;%>
</display:table>
</c:if>
</body>
</html:html>