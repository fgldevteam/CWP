<?xml version="1.0"?>
<%@ page contentType="text/xml; charset=utf-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">
<pdf>
    <jsp:useBean id="today" class="java.util.Date"/>
    <fmt:formatDate var="today" value="${today}" pattern="MM/dd/yyyy"/>
    <%-- header section --%>
    <head>
        <%@ include file="report_style.jspf" %>
    </head>
    <%-- report body --%>
    <body size="Letter-landscape">
        <c:set var="category" value="none"/>
        <c:set var="startDate" value="none"/>
        <c:set var="endDate" value="none"/>
        <c:set var="classCode" value="none"/>
        <c:set var="brandCode" value="none"/>
        <c:set var="deptCode" value="none"/>
        <c:set var="priorityName" value="none"/>
        <c:set var="groupLevel" value="0"/>
        <table class="container">
            <c:forEach items="${itemPriceChangeReportBean.results}" var="row">
                <c:choose>
                    <%-- if division, startDate, endDate or priorityName changes force a pagebreak and the printing of headers --%>
                    <c:when test="${row.categoryName != category || row.startDate != startDate || row.endDate != endDate || row.priorityName != priorityName}">
                        <c:set var="groupLevel" value="4"/>
                        <c:if test="${category != 'none'}">
                            </table><pbr/><table class="container">
                        </c:if>
                        <%-- PAGE HEADER --%>
                        <thead><tr>
                            <td colspan="10">
                                <table class="pageHeader">
                                    <tr>
                                        <td width="15%">
                                                <fmt:message key="report.pricechange.date"/> ${today}
                                        </td>
                                        <td width="70%" rowspan="2" class="pageTitle">
                                                <fmt:message key="report.pricechange.pageTitle"/> - ${itemPriceChangeReportBean.markDescription}
                                        </td>
                                        <td width="15%">
                                                <fmt:message key="report.pricechange.pageNumber"/> <pagenumber />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="20%">
                                                <%-- space --%></td>
                                        <td width="20%">
                                                <fmt:message key="report.pricechange.store"/> ${row.storeNumber}
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <span style="font-weight:bold;">
                                    <fmt:message key="report.pricechange.dates">
                                        <fmt:param value="${itemPriceChangeReportBean.startDateValue}"/>
                                        <fmt:param value="${itemPriceChangeReportBean.endDateValue}"/>
                                    </fmt:message>
                                </span>
                            </td>
                        </tr>
                        <%-- Division Header --%>
                        <tr>
                            <td colspan="10">
                                <table width="100%" cellpadding="0" class="divisionHeader">
                                    <tr>
                                        <td width="40%">
                                            <fmt:message key="report.pricechange.pageStartDate">
                                                <fmt:param value="${row.startDate}"/>
                                            </fmt:message>
                                        </td>
                                        <td width="30%"><c:out value="${row.categoryName}"/></td>
                                        <td width="30%" rowspan="2" font-size="12" font-weight="bold" valign="middle"><c:out value="${row.priorityName}"/></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <fmt:message key="report.pricechange.pageEndDate">
                                                <fmt:param value="${row.endDate}"/>
                                            </fmt:message>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        </thead>
                        <c:set var="category" value="${row.categoryName}"/>
                        <c:set var="startDate" value="${row.startDate}"/>
                        <c:set var="endDate" value="${row.endDate}"/>
                        <c:set var="priorityName" value="${row.priorityName}"/>
                        <c:set var="classCode" value="${row.subDeptName}"/>
                        <c:set var="brandCode" value="${row.brandCode}"/>
                        <c:set var="deptCode" value="${row.deptName}"/>
                    </c:when>
                </c:choose>
                <%-- When vendor or class code changes save the values & set the group Level flag to 2 --%>
                <c:if test="${brandCode != row.brandCode || classCode != row.subDeptName}">
                    <c:set var="classCode" value="${row.subDeptName}"/>
                    <c:set var="brandCode" value="${row.brandCode}"/>
                    <c:set var="groupLevel" value="2"/>
                </c:if>
                <%-- If the group level is 2 or 4 then Print out the Vendor Header --%>
                <c:if test="${groupLevel == 2 || groupLevel == 4}">
                    <%-- Print out a blank line when the department code changes --%>
                    <c:if test="${deptCode != row.deptName}">
                        <tr>
                            <td colspan="10"><%-- space --%></td>
                        </tr>
                        <c:set var="deptCode" value="${row.deptName}"/>
                    </c:if>
                    <tr>
                        <td colspan="10" font-weight="bold">
                            <table width="100%" border="0" cellpadding="0" style="background-color: #efefef;">
                                <tr>
                                    <td colspan="6">
                                        <table width="100%" cellpadding="0">
                                            <tr>
                                                <td width="26%" align="left">
                                                    <c:out value="${row.brandName}"/>
                                                </td>
                                                <td width="37%" align="center">
                                                    <c:out value="${row.deptName}"/>
                                                </td>
                                                <td width="37%" align="right">
                                                    <c:out value="${row.subDeptName}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr class="heading">
                        <td align="left">
                            <fmt:message key="report.pricechange.pmmStyleNum"/>
                        </td>
                        <td align="left">
                            <fmt:message key="report.pricechange.r12StyleNum"/>
                        </td>
                        <td align="left">
                            <fmt:message key="report.pricechange.vpn"/>
                        </td>
                        <td align="left" style="padding-right:20px">
                            <fmt:message key="report.pricechange.desription"/>
                        </td>
                        <td align="right">
                            <fmt:message key="report.pricechange.listPrice"/>
                        </td>
                        <td align="right">
                            <fmt:message key="report.pricechange.previousPrice"/>
                        </td>
                        <td align="right">
                            <fmt:message key="report.pricechange.eventPrice"/>
                        </td>
                        <td align="right">
                            <fmt:message key="report.pricechange.quantity"/>
                        </td>
                        <td align="right">
                            <fmt:message key="report.pricechange.quantityintransit"/>
                        </td>
                        <td align="center">
                        	<fmt:message key="report.pricechange.barcode"/>
                        </td>
                    </tr>
                    <c:set var="groupLevel" value="0"/>
                </c:if>
                <%-- Table Data --%>
                <tr height="100">
                    <td align="left">
                        <c:out value="${row.styleNumber}"/>
                    </td>
                    <td align="left">
                        <c:out value="${row.r12StyleNumber}"/>
                    </td>
                    <td align="left">
                        <c:out value="${row.vpn}"/>
                    </td>
                    <td align="left">
                        <c:out value="${row.styleDescription}"/>
                    </td>
                    <td align="right">
	                    <fmt:message key="report.pricechange.currency">
                        	<fmt:param value="${row.listPrice}"/>
                        </fmt:message>
                    </td>
                    <td align="right">
	                    <fmt:message key="report.pricechange.currency">
                        	<fmt:param value="${row.previousPrice}"/>
                        </fmt:message>
                    </td>
                    <td align="right">
	                    <fmt:message key="report.pricechange.currency">
                        	<fmt:param value="${row.eventPrice}"/>
                        </fmt:message>
                    </td>
                    <td align="right">
                        <c:out value="${row.quantity}"/>
                    </td>
                    <td align="right">
                        <c:out value="${row.quantityInTransit}"/>
                    </td>
                	<c:choose>
                    	<c:when test="${empty row.upc}">
		                    <td align="left" style="padding-left:10px">
		                    	<fmt:message key="report.pricechange.n_a"/>
		                    </td>
                    	</c:when>
                    	<c:otherwise>
                    		<!-- td align="right" style="padding-bottom:10px;padding-left:10px;" -->
                		    <td align="right" style="padding-bottom:10px;padding-left:5px;">
		                    	<barcode codetype="code128" showtext="true" bar-width="0.75" value="<c:out value='${row.upc}'/>"/>
		                    </td>
                    	</c:otherwise>
                	</c:choose>
                </tr>
            </c:forEach>
        </table>
    </body>
</pdf>
<%out.flush();%>
