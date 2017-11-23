<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% response.setHeader( "Cache-control", "no-store" ); %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:html locale="true" xhtml="true">
<head>
    <title><decorator:title default="Corporate Web Portal" /></title>
    <decorator:head/>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/cwp.css'/>"/>
    <link rel="SHORTCUT ICON" href="<html:rewrite page='/favicon.ico'/>"> 
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="-1"/>

    <script src="<html:rewrite page='/scripts/cwp.js'/>"></script>
    <script type="text/javascript">
        function setInitialFocus(){
            theForm = document.forms[0];
            if (theForm != null){
                for (var i=0; i<theForm.elements.length; ++i) {
                    var theElement = theForm.elements[i];
                    // set focus on the first form element that is not hidden
                    if (theElement != null && theElement.type != 'hidden') {
                        theElement.focus();
                        break;
                    }
                }
            }
        }
    </script>
</head>
<body onload="setInitialFocus();">
<%@ include file="/includes/header.jsp"%>
<table class="result">
    <tr><td align="center" style="padding:10px;">
    	<html:errors/>
        <decorator:body />
		<jsp:include page="/includes/footer.jsp" flush="false"/>
    </td></tr>
</table>
</body>

</html:html>

