<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="com.fgl.cwp.common.Constants"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.documents"/></title>
    <script type="text/javascript">
    function assignType() {
    	document.forms[0].action="<html:rewrite page='/secure/admin/assignType.do'/>";
      document.forms[0].submit();
      return false;
    }
    
    function assignGroup() {
    	document.forms[0].action="<html:rewrite page='/secure/admin/assignGroup.do'/>";
      document.forms[0].submit();
      return false;
    }
    
    function assignSubGroup() {
    	document.forms[0].action="<html:rewrite page='/secure/admin/assignSubGroup.do'/>";
      document.forms[0].submit();
      return false;
    }
    
    function assignCategory() {
    	document.forms[0].action="<html:rewrite page='/secure/admin/assignCategory.do'/>";
      document.forms[0].submit();
      return false;
    }
    
    function loadParents() {
      document.forms[0].action="<html:rewrite page='/secure/admin/loadHeaders.do'/>";
      document.forms[0].submit();
      return false;
    }
    </script>
</head>
<body>
<html:form action="/secure/admin/saveDocument.do" method="post" enctype="multipart/form-data">
	<c:set var="hideCategory" value="true"/>
	<c:choose>
		<c:when test="${manageDocumentBean.document.type != 'H'}">
			<c:set var="hideCategory" value="false"/>
		</c:when>
		<c:otherwise/>
	</c:choose>
	
    <table class="data">
        <tr>
            <th colspan="2">
                <c:choose>
                <c:when test="${empty sessionScope.manageDocumentBean.document.id}">
                    <fmt:message key="admin.document.label.addDocument"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="admin.document.label.editDocument"/>
                </c:otherwise>
                </c:choose>
            </th>
        </tr><tr>
            <td class="label"><fmt:message key="admin.document.label.id"/>:</td>
            <td><c:out value="${sessionScope.manageDocumentBean.document.id}"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.document.label.title"/>:</td>
            <td><html:text name="manageDocumentBean" property="document.title" size="50" maxlength="50"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.document.label.type"/>:</td>
            <td>
                <html:radio name="manageDocumentBean" property="document.type" value="<%=Constants.HEADER_TYPE%>" onclick="assignType()"><fmt:message key="admin.document.label.type.header"/></html:radio>
                <html:radio name="manageDocumentBean" property="document.type" value="<%=Constants.DOCUMENT_TYPE%>" onclick="assignType()"><fmt:message key="admin.document.label.type.document"/></html:radio>
                <html:radio name="manageDocumentBean" property="document.type" value="<%=Constants.LINK_TYPE%>" onclick="assignType()"><fmt:message key="admin.document.label.type.link"/></html:radio>
            </td>
            
        </tr><tr>
            <td class="label"><fmt:message key="admin.document.label.searchType"/>:</td>
            <td>
                <html:radio name="manageDocumentBean" property="document.category" value="<%=Constants.DOCUMENT%>" onclick="return(loadParents());"><fmt:message key="admin.document.label.category.document"/></html:radio>
                <html:radio name="manageDocumentBean" property="document.category" value="<%=Constants.TROUBLESHOOT%>" onclick="return(loadParents());"><fmt:message key="admin.document.label.category.troubleshoot"/></html:radio>
            </td>
        </tr>
        <tr>
            <td class="label"><fmt:message key="admin.document.label.group"/>:</td>
            <td>
                <html:select name="manageDocumentBean" property="groupId" onchange="return(assignGroup());">
                	<c:if test="${manageDocumentBean.document.type == 'H'}">
                	<html:option value="0"><fmt:message key="admin.document.label.none"/></html:option>
                	</c:if>
                  <html:optionsCollection name="manageDocumentBean" property="headers" value="id" label="title"/>
                </html:select>
            </td>
        </tr>
        <c:if test="${not empty manageDocumentBean.groupParent.childrenHeaders}">
        <tr>
            <td class="label"><fmt:message key="admin.document.label.subGroup"/>:</td>
            <td>
                <html:select name="manageDocumentBean" property="subGroupId" onchange="return(assignSubGroup());">
                    <html:option value="0"><fmt:message key="admin.document.label.none"/></html:option>
                    <html:optionsCollection name="manageDocumentBean" property="groupParent.childrenHeaders" value="id" label="title"/>
                </html:select>
            </td>
        </tr>
        </c:if>
        <c:if test="${not empty manageDocumentBean.subGroupParent.childrenHeaders && !hideCategory }">
        <tr>
            <td class="label"><fmt:message key="admin.document.label.category"/>:</td>
            <td>
                <html:select name="manageDocumentBean" property="categoryId" onchange="return(assignCategory());">
                    <html:option value="0"><fmt:message key="admin.document.label.none"/></html:option>
                    <html:optionsCollection name="manageDocumentBean" property="subGroupParent.childrenHeaders" value="id" label="title"/>
                </html:select>
            </td>
        </tr>
        </c:if>
        <c:if test="${manageDocumentBean.document.type == 'D'}">
	        <tr>
	            <td class="label"><fmt:message key="admin.document.label.email"/>:</td>
	            <td><html:text styleId="emailField" name="manageDocumentBean" property="document.emailAddress" size="50" maxlength="100"/></td>
	        </tr>
        </c:if>
        <c:if test="${manageDocumentBean.document.type != 'H'}">
			    <tr>
			        <td class="label"><fmt:message key="admin.document.label.featureDocumentExpiryDate"/>:</td>
			        <td>
			          <html:text name="manageDocumentBean" property="featureExpiryDate" styleId="expiryDate" size="12" maxlength="12"/>
			          <html:img styleId="expiryDateCal" page="/images/cal.gif" style="cursor: pointer;"/>
			          <jsp:include page="/jsp/includes/calendarSetup.jsp">
						    	<jsp:param name="inputField" value="expiryDate"/>
						    	<jsp:param name="button" value="expiryDateCal"/>
								</jsp:include>
			        </td>
			    </tr>
        </c:if>
        <c:if test="${manageDocumentBean.document.type == 'L'}">
	        <tr>
	        	<td class="label"><fmt:message key="admin.document.label.link"/>:</td>
	        	<td><html:text styleId="linkField" property="link" size="50" maxlength="100"/></td>
	        </tr>
        </c:if>
        <c:if test="${manageDocumentBean.document.type == 'D'}">
	        <c:if test="${not empty sessionScope.manageDocumentBean.document.id && manageDocumentBean.document.type == 'D'}">
	            <tr>
	                <td class="label"><fmt:message key="admin.document.label.currentFile"/>:</td>
	                <td><c:out value="${sessionScope.manageDocumentBean.document.fileName}"/></td>
	            </tr>
	        </c:if>        
	        <tr>
	            <td class="label"><fmt:message key="admin.document.label.newFile"/>:</td>
	            <td><html:file name="manageDocumentBean" styleId="formFile" property="document.attachment" size="50"/></td>
	        </tr>
	      </c:if>
	      <tr>
	        	<td class="label"><fmt:message key="admin.document.label.addedEditedBy"/>:</td>
	        	<td><c:out value="${sessionScope.manageDocumentBean.document.username}"/></td>
	        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key='admin.document.edit.button.save'/>"/>
                &nbsp;
                <input type="button" value="<fmt:message key='admin.document.edit.button.cancel'/>" onclick="window.location='<html:rewrite page="/secure/admin/loadDocuments.do"/>'"/>
            </td>
        </tr>
    </table>
    
</html:form>
</body>
</html:html>

