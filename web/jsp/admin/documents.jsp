<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.documents"/></title>
    <script type="text/javascript">
    function confirmDelete(title) {
        var answer = confirm("<fmt:message key='admin.document.confirm'/>"+title+"?");
        return answer;
    }
    </script>
</head>
<body>
<c:if test="${!empty manageDocumentBean.documents}">
	<c:set var="editAction" value="/secure/admin/editDocument.do?"/>
	<c:set var="deleteAction" value="/secure/admin/deleteDocument.do?"/>
	<c:set var="firstRow" value="true"/>
	<table class="docs" align="center" bordercolor="black">
    	<tbody>
    		<tr>
    			<td id="header" class="headerRow" align="center">
    				Title
    			</td>
    			<td id="header" class="headerRow" align="center">
    				Type
    			</td>
    			<td id="header" class="headerRow" align="center">
    				Search
    			</td>
    			<td id="header" class="headerRow" align="center">
    				Action
    			</td>
    		</tr>
    		
    		<c:forEach items="${manageDocumentBean.documents}" var="group">
	    			<!-- clear the subGroup and category vars each iteration -->
	    			<c:set value="" var="subGroup"/>
	    			<c:set value="" var="category"/>
	    			<c:set value="" var="doc"/>
	    			
	    			<c:set value="group" var="docType"/>
	    			<%@ include file="documentEntry.jspf" %>
	    			<c:set var="firstRow" value="false"/>	
    				<!-- check for group child docs -->
    				<c:forEach items="${group.childrenDocuments}" var="doc">
    					<c:set value="document" var="docType"/>
	    				<%@ include file="documentEntry.jspf" %>
    				</c:forEach>
    				
    				<!-- now for each sub group -->
    				<c:forEach items="${group.childrenHeaders}" var="subGroup">
    					<c:set value="subGroup" var="docType"/>
	    				<%@ include file="documentEntry.jspf" %>
    					
    						<!-- check for  sub group child docs -->
		    				<c:forEach items="${subGroup.childrenDocuments}" var="doc">
		    					<c:set value="document" var="docType"/>
	    						<%@ include file="documentEntry.jspf" %>
    						</c:forEach>
		    				
    						<!-- now for each category -->
    						<c:forEach items="${subGroup.childrenHeaders}" var="category">
    							<c:set value="category" var="docType"/>
	    						<%@ include file="documentEntry.jspf" %>
	    						
    							<!-- should all be  category child docs -->
    							<c:forEach items="${category.childrenDocuments}" var="doc">
    								<c:set value="document" var="docType"/>
	    							<%@ include file="documentEntry.jspf" %>
	    							
    							</c:forEach><!-- end category doc iteration-->
    							
    						</c:forEach><!-- end category iteration-->
    					
    				</c:forEach><!-- end sub group iteration -->
    				
    		</c:forEach><!-- end group iteration-->
    	</tbody>
   </table>
</c:if>
</body>
</html:html>




