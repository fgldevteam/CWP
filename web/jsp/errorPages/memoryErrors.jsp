<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<% response.setHeader( "Cache-control", "no-store" ); %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<page:applyDecorator name="error">
	<html>
	<body>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td align="center" bgcolor="#eeeeee" width="100%">
				<font color="#666666" face="Arial" size="1">				
					Please narrow down your search criteria as the system can not handle the request.<br> 
					If you continue to have issues please email the site administrator.<br> 
					Or just call the IT support center for assistance.<br>
					<a href="mailto:supportlink@forzani.com">administrator</a>.
					<br>								
				&nbsp;
				</font>
			</td>
        </tr>
     </table>
	</body>
	</html>
</page:applyDecorator>

