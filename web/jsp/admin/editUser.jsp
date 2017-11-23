<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.users"/></title>
    <script type="text/javascript">
    function addManagedStore() {
        document.forms[0].action="<html:rewrite page='/secure/admin/addManagedStoreToUser.do'/>";
        document.forms[0].submit();
        return false;
    }
    function removeManagedStore() {
        document.forms[0].action="<html:rewrite page='/secure/admin/removeManagedStoreFromUser.do'/>";
        document.forms[0].submit();
        return false;
    }
    </script>
</head>
<body>
<html:form action="/secure/admin/saveUser.do" method="post" enctype="multipart/form-data">
    <table class="data">
        <tr>
            <th colspan="2">
                <c:choose>
                <c:when test="${empty manageUserBean.user.id}">
                    <fmt:message key="admin.user.label.addUser"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="admin.user.label.editUser"/>
                </c:otherwise>
                </c:choose>
            </th>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.id"/>:</td>
            <td><c:out value="${manageUserBean.user.id}"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.firstName"/>:</td>
            <td><html:text property="user.firstName" size="50" maxlength="50"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.lastName"/>:</td>
            <td><html:text property="user.lastName" size="50" maxlength="50"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.username"/>:</td>
            <td>
            	<c:choose>
            	<c:when test="${manageUserBean.user.new == true}">
	            	<html:text property="user.username" size="30" maxlength="30"/>
	            </c:when><c:otherwise>
                	<html:text styleClass="disabledInput" property="user.username" disabled="true" readonly="true" size="30" maxlength="30"/>
	            </c:otherwise>
	            </c:choose>
            </td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.password"/>:</td>
            <td>
                <c:choose>
                <c:when test="${manageUserBean.user.new == true}">
                <html:password property="user.plainTextPassword" size="30" maxlength="30"/>
                </c:when><c:otherwise>
                <input class="disabledInput" value="********" disabled="true" readonly="true" size="30" maxlength="30"/>
                </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <c:if test="${manageUserBean.user.new == false}">
	        <tr>
	        	<td class="label"><fmt:message key="admin.user.label.newPassword"/>:</td>
	        	<td><html:password property="user.newPassword" size="30" maxlength="30"/></td>
	        </tr><tr>
	        	<td class="label"><fmt:message key="admin.user.label.confirmPassword"/>:</td>
	        	<td><html:password property="user.confirmPassword" size="30" maxlength="30"/></td>
	        </tr>
	    </c:if>    
        <tr>
            <td class="label"><fmt:message key="admin.user.label.email"/>:</td>
            <td><html:text property="user.email" size="50" maxlength="50"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.phone"/>:</td>
            <td><html:text property="user.phone" size="12" maxlength="12"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="admin.user.label.fax"/>:</td>
            <td><html:text property="user.fax" size="12" maxlength="12"/></td>
        </tr>

		<!-- Disable elements that the user should never be able to modify for him/herself -->        
        <c:set var="disableSelf" value="false"/>
        <c:if test="${!manageUserBean.user.new && manageUserBean.user.id == user.id && !manageUserBean.user.administrator}">
	        <c:set var="disableSelf" value="true"/>
	    </c:if>
        
        <c:if test="${disableSelf}">
        	<!-- The values of disabled checkboxes are never sent back to the server so force them to be sent back -->
            <html:hidden property="roleAdministrator" value="${manageUserBean.roleAdministrator}"/>
			<html:hidden property="roleDocumentManager" value="${manageUserBean.roleDocumentManager}"/>
			<html:hidden property="roleNoticeManager" value="${manageUserBean.roleNoticeManager}"/>
			<html:hidden property="roleUserManager" value="${manageUserBean.roleUserManager}"/>
			<html:hidden property="roleStoreManager" value="${manageUserBean.roleStoreManager}"/>
			<html:hidden property="roleRegionalManager" value="${manageUserBean.roleRegionalManager}"/>
			<html:hidden property="roleReadOnlyUser" value="${manageUserBean.roleReadOnlyUser}"/>
			<html:hidden property="rolePricingIssuesManager" value="${manageUserBean.rolePricingIssuesManager}"/>
			<html:hidden property="roleElearningAdministrator" value="${manageUserBean.roleElearningAdministrator}"/>
        </c:if>
        
        <tr>
            <td class="label"><fmt:message key="admin.user.label.role"/>:</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleAdministrator" value="true"><fmt:message key="admin.user.label.role.administrator"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleDocumentManager" value="true"><fmt:message key="admin.user.label.role.documentManager"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleNoticeManager" value="true"><fmt:message key="admin.user.label.role.noticeManager"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleUserManager" value="true"><fmt:message key="admin.user.label.role.userManager"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
        	<td><html:checkbox disabled="${disableSelf}" property="roleStoreManager" value="true"><fmt:message key="admin.user.label.role.storeManager"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleRegionalManager" value="true"><fmt:message key="admin.user.label.role.regionalGeneralManager"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleReadOnlyUser" value="true"><fmt:message key="admin.user.label.role.readOnlyUser"/></html:checkbox></td>
        </tr><tr>
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="rolePricingIssuesManager" value="true"><fmt:message key="admin.user.label.role.pricingIssuesManager"/></html:checkbox></td>
        </tr><tr>         
        	<td>&nbsp;</td>
            <td><html:checkbox disabled="${disableSelf}" property="roleElearningAdministrator" value="true"><fmt:message key="admin.user.label.role.roleElearningAdministrator"/></html:checkbox></td>
        </tr><tr>          
            <td colspan="2">
                <table>
                    <tr>
                        <td class="label"><fmt:message key="admin.user.label.unmanagedStores"/>:</td>
                        <td>&nbsp;</td>
                        <td class="label"><fmt:message key="admin.user.label.managedStores"/>:</td>
                    </tr><tr>
                        <td>
                            <html:select disabled="${disableSelf}" multiple="true" size="10" property="selectedUnmanagedStores" style="width:250px;">
                                <html:optionsCollection property="unmanagedStores" value="number" label="fullName"/>
                            </html:select>
                        </td>
                        <td style="vertical-align:middle;">
                        	<c:set var="disableInput" value=""/>
                        	<c:if test="${disableSelf}">
                        		<c:set var="disableInput" value="disabled"/>
                        	</c:if>
                            <input type="button" <c:out value="${disableInput}"/> value="<fmt:message key='admin.user.edit.button.addManagedStore'/>" onclick="addManagedStore();"/><br/><br/>
                            <input type="button" <c:out value="${disableInput}"/> value="<fmt:message key='admin.user.edit.button.removedManagedStore'/>" onclick="removeManagedStore();"/>
                        </td>
                        <td>
                            <html:select disabled="${disableSelf}" multiple="true" size="10" property="selectedManagedStores" style="width:250px;">
                                <html:optionsCollection property="user.stores" value="number" label="fullName"/>
                            </html:select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr><tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key='admin.user.edit.button.save'/>"/>
                &nbsp;
                <input type="button" value="<fmt:message key='admin.user.edit.button.cancel'/>" onclick="window.location='<html:rewrite page="/secure/admin/loadUsers.do"/>'"/>
            </td>
        </tr>
    </table>
</html:form>
</body>
</html:html>

