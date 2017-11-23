<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:html>
<head><title><fmt:message key="header.title"/></title></head>
<body>
<table>
    <tr>
        <td>
            <table class="data">
                <caption><fmt:message key="main.label.documents"/></caption>
               	<c:choose>
               		<c:when test="${empty mainBean.featuredDocuments}">
               			<tr><td><fmt:message key="main.msg.noDocumentsToday"/></td></tr>
               		</c:when>
	               	<c:otherwise>
		                <c:forEach items="${mainBean.featuredDocuments}" var="element">
		                    <tr><td>

			                <c:choose>
			                    <c:when test="${element.type=='D'}">
                                    <fmt:message var="docDir" key='documents.filepath'/>
                                    <c:url var="docURL" value="/FileDownload/${element.fileName}">
                                        <c:param name="inline" value="false"/>
                                        <c:param name="dir" value="${docDir}"/>
                                        <c:param name="contentType" value="${element.contentType}"/>
                                        <c:param name="file" value="${element.fileName}"/>
                                    </c:url>
                                    <a href="<c:out value='${docURL}'/>">
			                            <c:out value="${element.title}"/>
			                        </a>
			                    </c:when>
			                    <c:when test="${element.type=='F'}">
			                        <a href="<html:rewrite page='${element.fileName}'/>?documentId=<c:out value='${element.id}'/>">
			                        <c:out value="${element.title}"/>
			                        </a>
			                    </c:when>
			                    <c:when test="${element.type=='L'}">
			                    	<a href="#" onclick="openLink('<c:out value='${element.fileName}'/>','w','<fmt:message key='documents.link.confirm'/>');">
			                    		<c:out value="${element.title}"/>
			                    	</a>
			                    </c:when>
			                    <c:otherwise>
				                    <c:out value="${element.title}"/>
			                    </c:otherwise>
			                </c:choose>


		                    </td></tr>
		                </c:forEach>
		            </c:otherwise>
		        </c:choose>
            </table>
        </td>
        <td style="padding-left: 50px;">
            <table class="data">
                <caption><fmt:message key="main.label.notices"/></caption>
                <c:choose>
               		<c:when test="${empty mainBean.notices}">
               			<tr><td><fmt:message key="main.msg.noNoticesToday"/></td></tr>
               		</c:when>
	               	<c:otherwise>
		                <c:forEach items="${mainBean.notices}" var="element">
		                    <tr><td>
		                        <a href="<html:rewrite page='/FileDownload/'/><c:out value='${element.fileName}'/>?inline=false&dir=<fmt:message key='notices.filepath'/>&contentType=<c:out value='${element.contentType}'/>&file=<c:out value='${element.fileName}'/>">
		                            <c:out value="${element.name}"/>
		                        </a>
		                    </td></tr>
		                </c:forEach>
		            </c:otherwise>
		        </c:choose>
            </table>
        </td>
    </tr>
</table>
<p/>
<fmt:message key="default.dateformat" var="dateFormat"/>

<display:table name="${sessionScope.mainBean.pricingIssues}" id="issue" style="width:600px;">
	<display:setProperty name="css.table" value="pricingData"/>
    <display:caption><fmt:message key="pricingIssues.label.currentPricingIssues"/></display:caption>
    
    <display:column titleKey="pricingIssues.label.style" sortable="true" property="styleNum">
    </display:column>
    <display:column titleKey="pricingIssues.label.upc" sortable="true" property="upc">
    </display:column>
    <display:column titleKey="pricingIssues.label.incorrectPrice" style="text-align: right">
    	<fmt:formatNumber value="${issue.currentPrice}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2"/>
    </display:column>
    <display:column titleKey="pricingIssues.label.openedDate" sortable="true" style="text-align: right">
    	<fmt:formatDate value="${issue.openedDate}" type="date" pattern="${dateFormat}"/>
    </display:column>
    <display:column titleKey="pricingIssues.label.status" sortable="true" style="text-align: right; width: 50px">
    	<c:choose>
    		<c:when test="${issue.closedDate == null}">
    			<fmt:message key="pricingIssues.label.status.open"/>
    		</c:when>
    		<c:otherwise>
    			<fmt:message key="pricingIssues.label.status.closed"/> (<fmt:formatDate value="${issue.closedDate}" type="date" pattern="${dateFormat}"/>)
    		</c:otherwise>
    	</c:choose>
    </display:column>
</display:table>

</body>
</html:html>
