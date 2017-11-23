<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
<c:when test="${not empty loginStore}">
	<span class="loginid"/><fmt:message key="header.label.store"/>: <c:out value="${loginStore.number}"/></span>
</c:when>
<c:when test="${not empty user}">
	<span class="loginid"/><c:out value="${user.firstName}"/>&nbsp;<c:out value="${user.lastName}"/></span>
</c:when>
</c:choose>

