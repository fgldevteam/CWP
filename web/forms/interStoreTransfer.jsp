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
        <c:set var="title" value="form.interstoretransfer.title"/>
        <c:set var="today" value="${interStoreTransferBean.today}"/>
        <c:set var="storeNum" value="${interStoreTransferBean.storeNum}"/>
        <%@ include file="claimHeader.jspf"%>        
        
        <!-- BODY //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.interstoretransfer.header"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr><td class="emtext" colspan="9"><fmt:message key="form.interstoretransfer.note.followupwithsendingstore"/></td></tr>
                <tr><td class="emtext" colspan="9"><fmt:message key="form.interstoretransfer.note.claimontransfer"/></td></tr>
                <tr>
                    <th class="subth"><fmt:message key="form.label.shipdate"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.transfernumber"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.horefnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.sendingstore"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.courierpinnum"/></th>
                </tr>
                <!--Display shipdate, jdanum, horefnum, sendingstore, courierpinnum-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty interStoreTransferBean.shipDate[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${interStoreTransferBean.shipDate[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty interStoreTransferBean.jda[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${interStoreTransferBean.jda[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty interStoreTransferBean.hoRef[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${interStoreTransferBean.hoRef[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty interStoreTransferBean.sendingStore[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${interStoreTransferBean.sendingStore[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty interStoreTransferBean.courierPIN[i.index]}">
                            <td>________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${interStoreTransferBean.courierPIN[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
                <tr><td colspan="9" class="smalltext"><fmt:message key="form.label.sendingstorecontact"/>:<span class="userinput"><c:out value="${interStoreTransferBean.sendingStoreContact}"/></span></td></tr>
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
                    <td style="width:490px"><fmt:message key="form.interstoretransfer.q1"/></td>
                    <c:choose>
                        <c:when test="${interStoreTransferBean.answer1}">
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
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5">
                    <fmt:message key="form.interstoretransfer.q1a"/>
                    &#160;&#160;&#160;&#160;&#160;
                    <fmt:message key="form.label.tracenum"/>:&#160;
                    <span class="userinput"><c:out value="${interStoreTransferBean.traceNum}"/></span>
                    &#160;&#160;&#160;&#160;&#160;
                    <fmt:message key="form.label.contact"/>:&#160;
                    <span class="userinput"><c:out value="${interStoreTransferBean.contact}"/></span>
                    &#160;&#160;&#160;&#160;&#160;
                    <fmt:message key="form.label.datetraceopened"/>:&#160;
                    <span class="userinput"><c:out value="${interStoreTransferBean.dateTraceOpened}"/></span>
                    </td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td colspan="6"><fmt:message key="form.label.comments"/>:</td>
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${fn:substring(interStoreTransferBean.comments,0,300)}"/></td>
                </tr>
            </tbody>
        </table>
        
        <table>
            <tbody>
                <tr><td style="border-bottom:1 solid black;align:center;">&#160;</td></tr>
            </tbody>
        </table>
        
        <table class="smalltext" style="width:95%;margin-left:10">
            <tbody>
                <tr><td colspan="6"><fmt:message key="form.interstoretransfer.note.checkappropriatebox"/></td></tr>
                <tr><td colspan="6"><fmt:message key="form.label.fax"/>:<fmt:message key="form.claim.fax"/></td></tr>
                <tr><td colspan="6"><fmt:message key="form.interstoretransfer.note.useclaimascoverpage"/></td></tr>
                <tr>
                    <td style="width:10px;align:left;">
                        <c:choose>
                            <c:when test="${interStoreTransferBean.puroBol}">
                                <img src="../images/checked.gif" class="checkbox"/>
                            </c:when><c:otherwise>
                                <img src="../images/unchecked.gif" class="checkbox"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td colspan="5" style="width:620px"><fmt:message key="form.label.purobol"/></td>
                </tr><tr>
                    <td style="width:10px;align:left;">
                        <c:choose>
                            <c:when test="${interStoreTransferBean.transferSlip}">
                                <img src="../images/checked.gif" class="checkbox"/>
                            </c:when><c:otherwise>
                                <img src="../images/unchecked.gif" class="checkbox"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td colspan="5"><fmt:message key="form.label.transferslip"/></td>
                </tr><tr>
                    <td colspan="6"><fmt:message key="form.label.totalpagessent"/>:<span class="userinput"><c:out value="${interStoreTransferBean.totalPagesSent}"/></span></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td style="width:10px;align:left;">
                        <c:choose>
                            <c:when test="${interStoreTransferBean.reviewedByStoreMgr}">
                                <img src="../images/checked.gif" class="checkbox"/>
                            </c:when><c:otherwise>
                                <img src="../images/unchecked.gif" class="checkbox"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td colspan="5"><fmt:message key="form.label.reviewbystoremgr"/></td>
                </tr><tr>
                    <td colspan="6">
                    <table><tr>
                    <td align="left"><fmt:message key="form.label.storemgrname"/>:&#160;<span class="userinput"><c:out value="${interStoreTransferBean.storeMgrName}"/></span></td>
                    <td align="right"><fmt:message key="form.label.claimsubmittedby"/>:&#160;<span class="userinput"><c:out value="${interStoreTransferBean.submittedBy}"/></span></td>
                    </tr></table>
                    </td>
                </tr>
            </tbody>
        </table>


        <!-- EMAIL DISCLAIMER //-->
        <p class="linebreak"/>
        <c:set var="claimEmail" value="${interStoreTransferBean.document.emailAddress}"/>
        <%@ include file="claimEmailDisclaimerHeader.jspf"%>


        <!-- OFFICE USE //-->
        <p/>
        <%@ include file="claimOfficeUseHeader.jspf"%>
        <p class="linebreak"/>
        <table class="smalltext" style="width:95%;align:center;">
            <tr>
                <td class="label" align="left"><fmt:message key="form.interstoretransfer.claimstatuswithcarrier"/>:</td>
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.accepted"/></td>
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.declined"/></td>
                <td class="label"><fmt:message key="form.label.chargeto"/>:</td>
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.sendingstore"/>__________</td>        
                <td><img src="../images/unchecked.gif" class="checkbox"/></td>
                <td><fmt:message key="form.label.receivingstore"/>__________</td>
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
