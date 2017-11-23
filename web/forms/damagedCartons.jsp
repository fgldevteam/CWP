<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">
<pdf>
    <head>
        <link type="stylesheet" src="../styles/pdf.css"/>
        <macrolist>
            <macro id="myfooter">
            <p align="center">Page <pagenumber/> of <totalpages/></p>
            </macro>
        </macrolist>
    </head>    
    <body footer="myfooter" footer-height="20mm">
        <!-- HEADER //-->
        <c:set var="title" value="form.damagedcartons.title"/>
        <c:set var="today" value="${damagedCartonBean.today}"/>
        <c:set var="storeNum" value="${damagedCartonBean.storeNum}"/>
        <%@ include file="claimHeader.jspf"%>        
        
        <!-- BODY //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.damagedcartons.header"/></th></tr>
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
                    <th class="subth"><fmt:message key="form.label.carriernum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.manifestnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.shipdate"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.whnum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.status"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty damagedCartonBean.carton[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${damagedCartonBean.carton[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty damagedCartonBean.dds[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${damagedCartonBean.dds[i.index]}"/></td>
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
                    <td style="width:490px"><fmt:message key="form.damagedcartons.q1"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer1}">
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
                    <td colspan="5"><fmt:message key="form.damagedcartons.q1a"/></td>
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${damagedCartonBean.answer1a}"/></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>2.</td>
                    <td><fmt:message key="form.damagedcartons.q2"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer2}">
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
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>3.</td>
                    <td><fmt:message key="form.damagedcartons.q3"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer3}">
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
                </tr><tr>
                    <td>&#160;</td>
                    <td><fmt:message key="form.damagedcartons.q3a"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer3a}">
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
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>4.</td>
                    <td colspan="5"><fmt:message key="form.damagedcartons.q4"/></td>
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${damagedCartonBean.answer4}"/></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>5.</td>
                    <td colspan="5"><fmt:message key="form.damagedcartons.q5"/></td>
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${damagedCartonBean.answer5}"/></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>6.</td>
                    <td><fmt:message key="form.damagedcartons.q6"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer6}">
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
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>7.</td>
                    <td><fmt:message key="form.damagedcartons.q7"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer7}">
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
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5">
                    <fmt:message key="form.damagedcartons.q7a"/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<fmt:message key="form.label.reportedto"/>:&#160;<span class="userinput"><c:out value="${damagedCartonBean.reportedTo}"/></span>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<fmt:message key="form.label.datereported"/>:&#160;<span class="userinput"><c:out value="${damagedCartonBean.dateReported}"/></span>
                    </td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>8.</td>
                    <td><fmt:message key="form.damagedcartons.q8"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer8}">
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
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>9.</td>
                    <td colspan="5"><fmt:message key="form.damagedcartons.q9"/></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>10.</td>
                    <td><fmt:message key="form.damagedcartons.q10"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer10}">
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
            </tbody>
        </table>
        <pbr/>
        <table class="smalltext" style="width:95%;margin-left:10">
            <tbody>
                <tr>
                    <td>11.</td>
                    <td><fmt:message key="form.damagedcartons.q11"/></td>
                    <c:choose>
                        <c:when test="${damagedCartonBean.answer11}">
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
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5">
                    <fmt:message key="form.damagedcartons.q11a"/><span class="userinput"><c:out value="${damagedCartonBean.answer11a}"/></span>
                    </td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td colspan="6"><fmt:message key="form.label.comments"/>:</td>
                </tr><tr>
                    <td>&#160;</td>
                    <td colspan="5" class="userinput"><c:out value="${fn:substring(damagedCartonBean.comments,0,300)}"/></td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td>
                        <c:choose>
                            <c:when test="${damagedCartonBean.reviewedByStoreMgr}">
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
                    <td align="left"><fmt:message key="form.label.storemgrname"/>:&#160;<span class="userinput"><c:out value="${damagedCartonBean.storeMgrName}"/></span></td>
                    <td align="right"><fmt:message key="form.label.claimsubmittedby"/>:&#160;<span class="userinput"><c:out value="${damagedCartonBean.submittedBy}"/></span></td>
                    </tr></table>
                    </td>
                </tr><tr><td colspan="6">&#160;</td>
                </tr><tr>
                    <td colspan="6">
                    <fmt:message var="claimFax" key="form.claim.fax"/>
                    <fmt:message key="form.damagedcartons.faxbilloflading">
                        <fmt:param value="${claimFax}"/>
                    </fmt:message>
                    </td>
                </tr>
            </tbody>
        </table>


        <!-- EMAIL DISCLAIMER //-->
        <p/>
        <c:set var="claimEmail" value="${damagedCartonBean.document.emailAddress}"/>
        <%@ include file="claimEmailDisclaimerHeader.jspf"%>


        <!-- OFFICE USE //-->
        <p/>
        <%@ include file="claimOfficeUseHeader.jspf"%>
        <p class="linebreak"/>
        <table class="smalltext" style="width:95%;align:center;">
            <tr>
                <td class="label" align="left"><fmt:message key="form.damagedcartons.claimstatuswithcarrier"/>:</td>
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
