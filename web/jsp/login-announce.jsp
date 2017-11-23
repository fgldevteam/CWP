<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:html>
<head>
    <title><fmt:message key="header.title"/></title>
</head>
<body>
    <html:form action="/login.do?action=loginByUsername" method="post">
        <html:hidden property="action" value=""/>
        <table class="data" style="width:250px;">
            <caption><fmt:message key="login.title"/></caption>
            <tr>
                <td class="label"><fmt:message key="login.label.username"/></td>
                <td style="width:75%;"><html:text styleId="username" property="username" style="width:100%;"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="login.label.password"/></td>
                <td style="width:75%;"><html:password styleId="password" property="password" style="width:100%;"/></td>
            </tr><tr>
                <td colspan="2" style="text-align:center;">
                    <input type="submit" value="<fmt:message key='login.button.submit'/>"/>
                    <input type="reset" value="<fmt:message key='login.button.reset'/>"/>
                </td>
            </tr>
        </table>       
        <br>
        <br>
		<TABLE CELLSPACING=0 CELLPADDING=0>
			<TR>	   				
				<TD colspan=2 BGCOLOR=red align=center>
					The Corporate Web Portal Will be on Maintenance from date to date
				</TD>
			</TR>				
		 </TABLE>        
    </html:form>
</body>
</html:html>
