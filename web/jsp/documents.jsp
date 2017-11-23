<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head>
	<title>
        <c:if test="${param.category=='documents'}">
            <fmt:message key="documents.label.title"/>
        </c:if>
        <c:if test="${param.category=='troubleshoot'}">
            <fmt:message key="documents.troubleshooting.label.title"/>
        </c:if>
    </title>
    <script language="javascript">
    function submitSelection(level){
      document.forms[1].level.value = level;
      document.forms[1].submit();
    }
    </script>
</head>
<body>
<table><tr>
    <%-- Search Form --%>
    <td>
        <html:form action="/secure/searchDocument.do">
            <table class="data">
                <caption><fmt:message key="documents.label.search"/></caption>
                <tr>
                    <td class="label"><fmt:message key="documents.label.description"/></td>
                    <td><html:text property="searchDescription" size="35"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="documents.label.createddate"/></td>
                    <td>
                        <html:text property="searchDate" styleId="searchDate"/>
                        <html:img
                            styleId="searchDateCal"
                            page="/images/cal.gif"
                            style="cursor: pointer;"/>
                            
                        <jsp:include page="/jsp/includes/calendarSetup.jsp">
                            <jsp:param name="inputField" value="searchDate"/>
                            <jsp:param name="button" value="searchDateCal"/>
                        </jsp:include>
                    </td>
                </tr>
                <tr><td colspan="2" align="right"><input type="submit" value="<fmt:message key="button.search"/>"/></td></tr>
            </table>
        </html:form>
    </td>

    <td style="vertical-align:middle; padding-left:5px; padding-right:5px;"><fmt:message key="documents.label.or"/></td>
    <%-- Selection Form --%>
    <td>
        <html:form action="/secure/loadChildDocuments.do">
            <html:hidden property="level" value="" />
            <table class="data">
                <caption><fmt:message key="documents.label.selection"/></caption>
                <tr>
                    <td class="label"><fmt:message key="documents.label.header1"/></td>
                    <td>
                        <html:select property="parentSelection[0]" onchange="submitSelection(1);" >
                            <option value="0"></option>
                            <html:optionsCollection property="documentHeaders1" label="title" value="id"/>
                        </html:select>
                    </td>
                </tr>

                <c:if test="${!empty documentsBean.documentHeaders2}">
                    <tr>
                        <td class="label"><fmt:message key="documents.label.header2"/></td>
                        <td>
                            <html:select property="parentSelection[1]" onchange="submitSelection(2);" >
                                <option value="0">(Please select an option to see more documents)</option>
                                <html:optionsCollection property="documentHeaders2" label="title" value="id"/>
                            </html:select>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${!empty documentsBean.documentHeaders3}">
                    <tr>
                        <td class="label"><fmt:message key="documents.label.header3"/></td>
                        <td>
                            <html:select property="parentSelection[2]" onchange="submitSelection(3);" >
                                <option value="0">(Please select an option to see more documents)</option>
                                <html:optionsCollection property="documentHeaders3" label="title" value="id"/>
                            </html:select>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${!empty documentsBean.documentHeaders4}">
                    <tr>
                        <td class="label"><fmt:message key="documents.label.header4"/></td>
                        <td>
                            <html:select property="parentSelection[3]" onchange="submitSelection(4);" >
                                <option value="0"></option>
                                <html:optionsCollection property="documentHeaders4" label="title" value="id"/>
                            </html:select>
                        </td>
                    </tr>
                </c:if>
            </table>
        </html:form>
    </td>
</tr>
<tr><td colspan="3">
</td></tr>
</table>
    <%-- Results --%>
    <c:if test="${!empty documentsBean.documents}">
    	<c:choose>
    	<c:when test="${!documentsBean.search}">
        	<display:table name="${documentsBean.documents}" id="doc" class="data" style="width:100%;">
            <display:caption><fmt:message key="documents.label.availToDownload"/></display:caption>
            <display:column titleKey="documents.table.title">
                <c:choose>
                    <c:when test="${doc.type=='D'}">
                        <fmt:message var="docDir" key='documents.filepath'/>
                        <c:url var="docURL" value="/FileDownload/${doc.fileName}">
                            <c:param name="inline" value="false"/>
                            <c:param name="dir" value="${docDir}"/>
                            <c:param name="contentType" value="${doc.contentType}"/>
                            <c:param name="file" value="${doc.fileName}"/>
                        </c:url>
                        <a href="<c:out value='${docURL}'/>">
                        <c:out value="${doc.title}"/>
                        </a>
                    </c:when>
                    <c:when test="${doc.type=='F'}">
                        <a href="<html:rewrite page='${doc.fileName}'/>?documentId=<c:out value='${doc.id}'/>">
                        <c:out value="${doc.title}"/>
                        </a>
                    </c:when>
                    <c:when test="${doc.type=='L'}">
                    	<a href="#" onclick="openLink('<c:out value='${doc.fileName}'/>','w','<fmt:message key='documents.link.confirm'/>');">
                    		<c:out value="${doc.title}"/>
                    	</a>
                    </c:when>
                    <c:otherwise>
                    <c:out value="${doc.title}"/>
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="documents.table.dateModified">
                <fmt:message var="dateFormat" key="default.dateformat"/>
                <fmt:formatDate value="${doc.dateModified}" pattern="${dateFormat}"/>
            </display:column>.
        	</display:table>
    	</c:when>
    	<c:otherwise>
    	
    	
    	<table class="docs" align="center" bordercolor="black">
    		<tbody>
    		<tr>
    			<td id="header" class="headerRow" align="center" colspan="2">
    				<fmt:message key="documents.label.availToDownload"/>
    			</td>
    		</tr>
    		
    		<c:forEach items="${documentsBean.documents}" var="group">
	    		<tr>
	    			<td id="group" class="headerRow" colspan="2">
	    				<c:out value="${group.title} (Group)"/>
	    			</td>
	    		</tr>
    				<!-- check for group child docs -->
    				<c:forEach items="${group.childrenDocuments}" var="doc">
    					<%@ include file="documentEntry.jspf" %>
    				</c:forEach>
    				
    				<!-- now for each sub group -->
    				<c:forEach items="${group.childrenHeaders}" var="subGroup">
    					<tr>
    						<td id="subGroup" class="headerRow" colspan="2">
    							<c:out value="${subGroup.title} (Sub Group)"/>
    						</td>
    					</tr>
    					
    						<!-- check for  sub group child docs -->
		    				<c:forEach items="${subGroup.childrenDocuments}" var="doc">
		    					<%@ include file="documentEntry.jspf" %>
    						</c:forEach>
		    				
    						<!-- now for each category -->
    						<c:forEach items="${subGroup.childrenHeaders}" var="category">
    							<tr>
    								<td id="category" class="headerRow" colspan="2">
    									<c:out value="${category.title} (Category)"/>
    								</td>
    							</tr>
    							<!-- should all be  category child docs -->
    							<c:forEach items="${category.childrenDocuments}" var="doc">
    								<%@ include file="documentEntry.jspf" %>
    							</c:forEach><!-- end category doc iteration-->
    							
    						</c:forEach><!-- end category iteration-->
    					
    				</c:forEach><!-- end sub group iteration -->
    				
    		</c:forEach><!-- end group iteration-->
    		</tbody>
    		</table>
    	</c:otherwise>
    	</c:choose>
    </c:if>

</body>
</html:html>
