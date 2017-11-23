<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">

<jsp:useBean id="storeListBean" scope="request" class="com.fgl.cwp.presentation.forms.StoreListBean"/>
<pdf>
    <head>
		<style type="text/css">
		body {
	        size:Letter;
	        padding:0.5in;
	        font-family:Helvetica;
	        font-size:6;
    	}

    	h1 {
	        font-family:Helvetica;
	        font-size:12;
	        align:center;
    	}
    	
    	table {
    		border: 1px solid #efefef;
    		border-collapse: collapse;
    	}
    	
    	th {
    		background-color: #cccccc;
    		whitespace: nowrap;
    		align: left;
    		font-size:8;
    		font-weight:bold;
    	}
    	
    	tr {
    		border-bottom: 1px solid #efefef;
    	}
    	
    	td {
    		text-align: left;
    	}
		</style>
	    <macrolist>
	        <macro id="myfooter">
	            <p align="center">
	            <fmt:message key="form.storeList.label.page"/>&#160;<pagenumber/>&#160;<fmt:message key="form.storeList.label.of"/>&#160;<totalpages/>
	            </p>
	        </macro>
	    </macrolist>
    </head>
    <body footer="myfooter">
    <h1>Store List</h1>
    <jsp:useBean id="today" scope="request" class="java.util.Date"/>
    <c:out value="${today}"/>
    <c:forEach items="${storeListBean.stores}" var="store" varStatus="status">
	    <c:if test="${status.first}">
		    <table>
		    	<thead>
		    		<tr>
		    			<th style="width:50px"><fmt:message key="form.storeList.label.storeNumber"/></th>
		    			<th style="width:50px"><fmt:message key="form.storeList.label.banner"/></th>
		    			<th style="width:50px"><fmt:message key="form.storeList.label.province"/></th>
		    			<th><fmt:message key="form.storeList.label.city"/></th>
		    			<th><fmt:message key="form.storeList.label.name"/></th>
		    			<th><fmt:message key="form.storeList.label.address"/></th>
		    			<th style="width:70px;"><fmt:message key="form.storeList.label.postalCode"/></th>
		    			<th><fmt:message key="form.storeList.label.phone"/></th>
		    		</tr>
		    	</thead>
		    	<tbody>
	    </c:if>

		<tr>
			<td><c:out value="${store.number}"/></td>
			<td><c:out value="${fn:trim(store.banner)}"/></td>
			<td><c:out value="${fn:trim(store.province)}"/></td>
			<td><c:out value="${fn:trim(store.city)}"/></td>
			<td><c:out value="${fn:trim(store.name)}"/></td>
			<td><c:out value="${fn:trim(store.address)}"/></td>
			<td align="center" style="whitespace:nowrap;"><c:out value="${fn:trim(store.postalCode)}"/></td>
			<td align="center" style="whitespace:nowrap;">
				<c:if test="${not empty (fn:trim(store.phoneNumber))}">
					<c:if test="${not empty (fn:trim(store.areaCode))}">
						<c:out value="(${fn:trim(store.areaCode)})"/>
					</c:if>
					<c:out value="${fn:trim(store.phoneNumber)}"/>
				</c:if>
			</td>
		</tr>
				    
	    <c:if test="${status.last}">
		    </tbody>
		    </table>
	    </c:if>
    </c:forEach>
    </body>
</pdf>
<%out.flush();%>