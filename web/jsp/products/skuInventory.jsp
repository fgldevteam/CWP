<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
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
		<c:if test="${not empty productDetailsBean.skuInventoryList}">
		    <display:table name="${productDetailsBean.skuInventoryList}" id="result" class="data" style="width:100%">
		        <display:caption><fmt:message key="product.details.label.sku.inventory"/></display:caption>
		        <display:column
		        		titleKey="product.details.label.sku.inventory.storenumber"
		        		sortable="true">        		
		        	<a href="javascript:getStoreDetails(<c:out value='${result.storeNumber}'/>);"><c:out value="${result.storeNumber}"/></a>
		        </display:column>
				<display:column
						property="name"        		
						titleKey="product.details.label.sku.inventory.storedesc"
						sortable="true"/>
				<display:column
						property="city"
						titleKey="product.details.label.sku.inventory.storecity"
						sortable="true"/>
				<display:column titleKey="product.details.label.sku.inventory.storephone">
		        	<c:if test="${not empty (fn:trim(result.areaCode))}">(<c:out value="${fn:trim(result.areaCode)}"/>)&nbsp;</c:if>
		            <c:if test="${not empty result.phoneNumber}"><c:out value="${result.phoneNumber}"/></c:if>
				</display:column>
				<display:column
						property="colour"
						titleKey="product.details.label.sku.inventory.colour"
						sortable="true"/>
				<display:column
						property="size"
						titleKey="product.details.label.sku.inventory.size"
						sortable="true"/>
				<display:column
						property="quantity"
						titleKey="product.details.label.sku.inventory.quantity"/>
				<display:column
						property="quantityInTransit"
						titleKey="product.details.label.style.inventory.quantityintransit"/>		
		    </display:table>
		</c:if>
	</body>
</html:html>



