<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:html>
<head><title><fmt:message key="store.title"/></title></head>
<body>
<table class="data">
    <caption><fmt:message key="store.title"/></caption>
    <tr>
        <td class="label"><fmt:message key="store.label.store"/>:</td>
        <td style="white-space:nowrap;"><c:out value="${storeDetailsBean.store.fullName}"/></td>
    </tr><tr>
        <td class="label"><fmt:message key="store.label.mall"/>:</td>
        <td style="white-space:nowrap;"><c:out value="${storeDetailsBean.store.description}"/></td>
    </tr><tr>
        <td class="label"><fmt:message key="store.label.address"/>:</td>
        <td style="white-space:nowrap;"><c:out value="${fn:trim(storeDetailsBean.store.address)}"/></td>
    </tr><tr>
        <td>&nbsp;</td>
        <td style="white-space:nowrap;"><c:out value="${fn:trim(storeDetailsBean.store.city)}"/>, <c:out value="${fn:trim(storeDetailsBean.store.province)}"/></td>
    </tr><tr>
        <td class="label"><fmt:message key="store.label.phone"/>:</td>
        <td style="white-space:nowrap;">
            <c:if test="${not empty storeDetailsBean.store.areaCode}">(<c:out value="${fn:trim(storeDetailsBean.store.areaCode)}"/>)&nbsp;</c:if>
            <c:if test="${not empty storeDetailsBean.store.phoneNumber}"><c:out value="${fn:trim(storeDetailsBean.store.phoneNumber)}"/></c:if>
        </td>
    </tr><tr>
        <td class="label"><fmt:message key="store.label.fax"/>:</td>
        <td style="white-space:nowrap;">
            <c:if test="${not empty storeDetailsBean.store.faxAreaCode}">(<c:out value="${fn:trim(storeDetailsBean.store.faxAreaCode)}"/>)&nbsp;</c:if>
            <c:if test="${not empty storeDetailsBean.store.faxNumber}"><c:out value="${fn:trim(storeDetailsBean.store.faxNumber)}"/></c:if>
        </td>
   </tr>
</table>
</body>
</html:html>
