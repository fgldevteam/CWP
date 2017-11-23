<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${!empty reportBean.storeList}">
        <html:select property="storeId" styleId="storeList">
            <html:optionsCollection property="storeList" label="fullName" value="number"/>
        </html:select>
    </c:when>
    <c:when test="${empty reportBean.storeList}">
        <c:out value="${reportBean.store.fullName}"/>
    </c:when>
</c:choose>
