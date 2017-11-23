<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:html>
	<head>
		<title><fmt:message key="product.details.title"/></title>
		<jsp:include page="/jsp/products/inventory.jsp"/>
	</head>
	<body>
		<table class="data">
        	<caption><fmt:message key="product.details.title"/></caption>
	        <tr>
	            <td class="label"><fmt:message key="product.details.label.stylenumber"/>:</td>
	            <td><c:out value="${productDetailsBean.product.number}"/></td>
	            <td colspan="2">&nbsp;</td>
	            <td class="label"><fmt:message key="product.details.label.vpn"/>:</td>
	            <td><c:out value="${productDetailsBean.product.vpn}"/></td>
	        </tr><tr>
	        	<td class="label"><fmt:message key="product.details.label.r12number"/>:</td>
	        	<td><c:out value="${productDetailsBean.product.r12Number}"/></td>
	        	<td colspan="2">&nbsp;</td>
	            <td class="label"><fmt:message key="product.details.label.description"/>:</td>
	            <td><c:out value="${productDetailsBean.product.description}"/></td>
            </tr><tr>
	            <td colspan="6" style="text-align:left;"><input type="button" value="<fmt:message key="product.button.back"/>" onclick="return(reloadDetails());"/></td>
	        </tr>
	    </table>
    	<br/>

		<%-- Display inventory by style --%>
		<c:if test="${not empty productDetailsBean.product.inventories}">
		    <display:table name="${productDetailsBean.product.inventories}" uid="inv" class="data" style="width:100%">
		        <display:caption><fmt:message key="product.details.label.style.inventory"/></display:caption>
		        <display:column
		        		titleKey="product.details.label.style.inventory.storenumber"
		        		sortable="true">
		            <a href="javascript:getStoreDetails(<c:out value='${inv.id.store.number}'/>);"><c:out value="${inv.id.store.number}"/></a>
		        </display:column>
		        <display:column
		                property="id.store.name"
		                titleKey="product.details.label.style.inventory.storedesc"
		                sortable="true"/>
		        <display:column
		                property="id.store.city"
		                titleKey="product.details.label.style.inventory.storecity"
		                sortable="true"/>
		        <display:column titleKey="product.details.label.style.inventory.storephone">
			        <c:if test="${not empty (fn:trim(inv.id.store.areaCode))}">(<c:out value="${fn:trim(inv.id.store.areaCode)}"/>)&nbsp;</c:if>
			        <c:if test="${not empty inv.id.store.phoneNumber}"><c:out value="${inv.id.store.phoneNumber}"/></c:if>
		        </display:column>
		        <display:column
		                property="quantity"
		                titleKey="product.details.label.style.inventory.quantity"/>
		        <display:column
		                property="quantityInTransit"
		                titleKey="product.details.label.style.inventory.quantityintransit"/>
		    </display:table>
		</c:if>
	</body>
</html:html>

