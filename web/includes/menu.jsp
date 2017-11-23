<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fgl.cwp.model.Functionality"%>
<%@ page import="com.fgl.cwp.common.Constants"%>
<%@ page import="com.fgl.cwp.model.User"%>
<%@ page import="com.fgl.cwp.model.UserRole"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>

<script type="text/javascript" src="<html:rewrite page='/scripts/coolmenus3.js'/>"></script>
<script type="text/javascript">
<%@ include file="/scripts/coolmenu-config.js"%>
</script>

<%
User user = (User)session.getAttribute(Constants.USER);

// Can user see the report menu?
// Default is allow for auto-login users (i.e. stores)
boolean showMenu = true;
if (user != null) {
    showMenu = false;
    if (user.canUserPerformFunction(Functionality.VIEW_REPORTS) &&
    			user.getStores().size() > 0) {
        showMenu = true;
    }
}
request.setAttribute("showReportMenu", new Boolean(showMenu));

// Can user see the search menu?
// Default is allow for auto-login users (i.e. stores)
showMenu = true;
if (user != null) {
	showMenu = false;
	if ( user.doesUserHaveRole(UserRole.ADMINISTRATOR)
			|| user.doesUserHaveRole(UserRole.DOCUMENT_MANAGER)
			|| user.doesUserHaveRole(UserRole.STORE_MANAGER)
			|| user.doesUserHaveRole(UserRole.REGIONAL_MANAGER)
			|| user.doesUserHaveRole(UserRole.READ_ONLY_USER) ) {
		showMenu = true;
	}
}
request.setAttribute("showSearchMenu", new Boolean(showMenu));

// Can user see the admin menu?
showMenu = false;
if (user != null) {
	if ( user.doesUserHaveRole(UserRole.ADMINISTRATOR)
			|| user.doesUserHaveRole(UserRole.DOCUMENT_MANAGER)
			|| user.doesUserHaveRole(UserRole.NOTICE_MANAGER)
			|| user.doesUserHaveRole(UserRole.USER_MANAGER)
			|| user.doesUserHaveRole(UserRole.PRICING_ISSUES_MANAGER)
			|| user.doesUserHaveRole(UserRole.ELEARNING_ADMINISTRATOR) ) {
		showMenu = true;
	}
}
request.setAttribute("showAdminMenu", new Boolean(showMenu));

%>
<menu:useMenuDisplayer name="CoolMenu" permissions="customPermissionsAdapter" bundle="org.apache.struts.action.MESSAGE">
    <menu:displayMenu name="portalMenu"/>

    <c:if test="${showReportMenu}">
        <menu:displayMenu name="reportMenu"/>
    </c:if>

	<c:if test="${showSearchMenu}">
		<menu:displayMenu name="searchMenu"/>
	</c:if>
    
    <menu:displayMenu name="linksMenu"/>
    
    <c:if test="${showAdminMenu}">
	    <menu:displayMenu name="adminMenu"/>
	</c:if>
	
</menu:useMenuDisplayer>
