<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html>
<head><title><fmt:message key="header.menu.admin.elearning.title"/></title> </head>
<body>
<html:form method="POST" action="/secure/admin/saveElearningCourses.do?method=saveCourses" >
   	    
   	    <table>  		   
		     </td>      
		     <td colspan="2">
		        <input type="submit" value="<fmt:message key='report.elearning_save.button.submit'/>" />
		     </td>		
	     <table> 
   	    <display:table name="${manageElearningBean.allCourses}" id="course" class="data">   	        
   	        <display:column width="6%" titleKey="admin.user.courselabel.id"  sortable="true" align="center">
		      <input type="text" size="10" name="courses_ids" READONLY value="${course.courseId}" STYLE='color:#999999;background:#DEDEDE'>
		    </display:column>
		    <display:column width="27%" titleKey="admin.user.courselabel.name"  align="center">
		      <input type="text" size="55"  maxlength="50" name="original_names" READONLY value="${course.originalName}" STYLE='color:#999999;background:#DEDEDE'>
		    </display:column>
		    <display:column width="27%" titleKey="admin.user.courselabel.displayname"  align="center">
		      <input type="text" size="55"  maxlength="50" name="displayNames" value="${course.displayName}">
		    </display:column>
		    <display:column width="15%" titleKey="admin.user.courselabel.shortcut"   align="center">
		      <input type="text" size="15" value="${course.shortName}" name="desired_names" maxlength="10">
		    </display:column>
		    <display:column width="25%" titleKey="admin.user.courselabel.reportable" align="center" style="padding-left:28px">  
		         <c:if test="${course.webReportable != 0}" >
		          <input type="checkbox" value="${course.courseId}" name="reportable_courses" checked="true" >         
		         </c:if>
		         <c:if test="${course.webReportable == 0}" >
		          <input type="checkbox" value="${course.courseId}" name="reportable_courses">  	         
		         </c:if>
		    </display:column> 
		 </display:table>
		 <table>  		   
		     </td>      
		     <td colspan="2">
		        <input type="submit" value="<fmt:message key='report.elearning_save.button.submit'/>" />
		     </td>		
	     <table> 
</html:form>
<body>
</html:html>





