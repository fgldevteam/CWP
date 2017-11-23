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
        <c:set var="title" value="form.carrierdist.title"/>
        <c:set var="today" value="${carrierDistBean.today}"/>
        <c:set var="storeNum" value="${carrierDistBean.storeNum}"/>
        <%@ include file="claimHeader.jspf"%>        
        
        <!-- BODY //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.carrierdist.header"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.cartonnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.ddsnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.shipdate"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.carriernum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.manifestnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.whnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.status"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty carrierDistBean.carton[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${carrierDistBean.carton[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty carrierDistBean.dds[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${carrierDistBean.dds[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <td>____________</td>
                    <td class="spacer">&#160;</td>
                    <td>____________</td>
                    <td class="spacer">&#160;</td>
                    <td>____________</td>
                    <td class="spacer">&#160;</td>
                    <td>____________</td>
                    <td class="spacer">&#160;</td>
                    <td>____________</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <!-- QUESTIONS //-->
        <p/>
        <table class="smalltext" style="width:95%;margin-left:10">
            <tbody>
                <tr><td class="emtext" colspan="6"><fmt:message key="form.claim.answerallcaption"/></td></tr>
                <tr><td class="emsmalltext" colspan="6"><fmt:message key="form.claim.answerallnote"/></td></tr>
                <tr><td colspan="6">&#160;</td></tr>
                <tr>
                    <td style="width:10px">1.</td>
                    <td style="width:490px"><fmt:message key="form.carrierdist.q1"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer1}">
                            <td style="width:25px"><img src="../images/checked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.yes"/></td>
                            <td style="width:25px"><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td style="width:25px"><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.yes"/></td>
                            <td style="width:25px"><img src="../images/checked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td>&#160;</td>
                    <td><fmt:message key="form.carrierdist.q1a"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer1a}">
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                	<td colspan="6">&#160;</td>
                </tr>
                <tr>
                    <td>2.</td>
                    <td><fmt:message key="form.carrierdist.q2"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer2}">
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                	<td colspan="6">&#160;</td>
                </tr>
                <tr>
                    <td>3.</td>
                    <td><fmt:message key="form.carrierdist.q3"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer3}">
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                	<td colspan="6">&#160;</td>
                </tr>
                <tr>
                    <td>4.</td>
                    <td><fmt:message key="form.carrierdist.q4"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer4}">
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                 <tr>
                    <td>&#160;</td>
                    <td colspan="5"><fmt:message key="form.carrierdist.q4a"/></td>
                </tr>
                <tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${carrierDistBean.answer4a}"/></td>
                </tr>
                <tr>
                	<td colspan="6">&#160;</td>
                </tr>
                <tr>
                    <td>5.</td>
                    <td><fmt:message key="form.carrierdist.q5"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer5}">
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                	<td colspan="6">&#160;</td>
                </tr>
                <tr>
                    <td>6.</td>
                    <td><fmt:message key="form.carrierdist.q6"/></td>
                    <c:choose>
                        <c:when test="${carrierDistBean.answer6}">
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.yes"/></td>
                            <td><img src="../images/checked.gif" class="checkbox"/></td>
                            <td><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td>&#160;</td>
                    <td colspan="5">
                    <fmt:message key="form.carrierdist.q6a"/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<fmt:message key="form.label.reportedto"/>:&#160;<span class="userinput"><c:out value="${carrierDistBean.reportedTo}"/></span>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<fmt:message key="form.label.datereported"/>:&#160;<span class="userinput"><c:out value="${carrierDistBean.dateReported}"/></span>
                    </td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td colspan="6"><fmt:message key="form.label.comments"/>:</td>
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${fn:substring(carrierDistBean.comments,0,300)}"/></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>
                        <c:choose>
                            <c:when test="${carrierDistBean.reviewedByStoreMgr}">
                                <img src="../images/checked.gif" class="checkbox"/>
                            </c:when><c:otherwise>
                                <img src="../images/unchecked.gif" class="checkbox"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:message key="form.label.reviewbystoremgr"/></td>
                </tr><tr>
                    <td colspan="6">
                    <table><tr>
                    <td align="left"><fmt:message key="form.label.storemgrname"/>:&#160;<span class="userinput"><c:out value="${carrierDistBean.storeMgrName}"/></span></td>
                    <td align="right"><fmt:message key="form.label.claimsubmittedby"/>:&#160;<span class="userinput"><c:out value="${carrierDistBean.submittedBy}"/></span></td>
                    </tr></table>
                    </td>
                </tr>
            </tbody>
        </table>


        <!-- EMAIL DISCLAIMER //-->
        <p/>
        <c:set var="claimEmail" value="${carrierDistBean.document.emailAddress}"/>
        <%@ include file="claimEmailDisclaimerHeader.jspf"%>


        <!-- OFFICE USE //-->
        <p/>
        <%@ include file="claimOfficeUseHeader.jspf"%>
        <p class="linebreak"/>
        <table class="smalltext" style="width:95%;align:center;">
            <tr>
                <td class="label" align="left"><fmt:message key="form.carrierdist.claimstatuswithcarrier"/>:</td>
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.accepted"/></td>
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.declined"/></td>
                <td class="label"><fmt:message key="form.label.chargeto"/>:</td>
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.dc"/>__________</td>        
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.store"/>__________</td>
            <c:forEach begin="0" end="4">
            </tr><tr>
                <td colspan="10">________________________________________________________________________________________________________________</td>
            </c:forEach>
            </tr>
        </table>
        <p class="linebreak"/>
        <p class="linebreak"/>


        <!-- AUTHORIZATION //-->
        <%@ include file="claimAuthorization.jspf"%>
    </body>
</pdf>
