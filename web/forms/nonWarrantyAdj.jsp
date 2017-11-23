<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">
<pdf>
    <head>
    	<link type="stylesheet" src="../styles/pdf.css"/>
    </head>
    <body>
        <!-- HEADER //-->
		<table style="width:520px;align:center;">
		    <tbody>
		        <tr><td colspan="8"><h1><fmt:message key="form.inventoryadj.nonwarranty.title"/></h1></td></tr>
		        <tr><td colspan="8">&#160;</td></tr>
		        <tr>
		            <td class="label" style="width:50px"><fmt:message key="form.label.date"/>:</td>
		            <td style="width:75px" class="userinput"><c:out value="${inventoryAdjBean.today}"/></td>
		            <td style="width:10px">&#160;</td>
		            <td class="label" style="width:75px"><fmt:message key="form.label.storenum"/>:</td>
		            <td style="width:50px" class="userinput"><c:out value="${inventoryAdjBean.storeNum}"/></td>
		            <td style="width:10px">&#160;</td>
		            <td class="label" style="width:100px"><fmt:message key="form.label.submittedby"/>:</td>
		            <td style="width:150px" class="userinput"><c:out value="${inventoryAdjBean.submittedBy}"/></td>
		        </tr>
		    </tbody>
		</table>

        <!-- BODY //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.inventoryadj.header"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.upc"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.quantity"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.price"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.description"/></th>
                    <td class="spacer">&#160;</td>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="19" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty inventoryAdjBean.upc[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${inventoryAdjBean.upc[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty inventoryAdjBean.quantity[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${inventoryAdjBean.quantity[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty inventoryAdjBean.price[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${inventoryAdjBean.price[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty inventoryAdjBean.description[i.index]}">
                            <td>________________________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${inventoryAdjBean.description[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        
                <!-- EXPLANATION //-->
        <p/>
        <table class="smalltext" style="width:95%;margin-left:10">
            <tbody>
                <tr><td class="emsmalltext" colspan="6"><fmt:message key="form.inventoryadj.nonwarranty.note"/></td></tr>
                <tr><td colspan="6">&#160;</td></tr>
                <tr>
                    <td colspan="6"><fmt:message key="form.label.explanation"/>:</td>
                </tr>
                <tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${fn:substring(inventoryAdjBean.explanation,0,300)}"/></td>
                </tr>
                <tr><td colspan="6">&#160;</td></tr>
				<tr>
                    <td>
                        <c:choose>
                            <c:when test="${inventoryAdjBean.reviewedByStoreMgr}">
                                <img src="../images/checked.gif" class="checkbox"/>
                            </c:when>
                            <c:otherwise>
                                <img src="../images/unchecked.gif" class="checkbox"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td colspan="5"><fmt:message key="form.label.inv.reviewbystoremgr"/></td>
                </tr>
                <tr>
                    <td colspan="6">
                    <table><tr>
                    <td align="left"><fmt:message key="form.label.storemgrname"/>:&#160;<span class="userinput"><c:out value="${inventoryAdjBean.storeMgrName}"/></span></td>
                    <td align="right"><fmt:message key="form.label.submittedby"/>:&#160;<span class="userinput"><c:out value="${inventoryAdjBean.submittedBy}"/></span></td>
                    </tr></table>
                    </td>
                </tr>
            </tbody>
        </table>


        <!-- EMAIL DISCLAIMER //-->
        <p/>
        <c:set var="claimEmail" value="${inventoryAdjBean.document.emailAddress}"/>
		<table>
		    <tbody>
		        <tr><th>
		            <fmt:message key="form.inventoryadj.emailtodisclaimer">
		                <fmt:param value="${inventoryAdjBean.document.emailAddress}"/>
		            </fmt:message>
		        </th></tr>
		    </tbody>
		</table>

    </body>
</pdf>
