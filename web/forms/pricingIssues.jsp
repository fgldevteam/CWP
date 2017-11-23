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
		        <tr><td colspan="8"><h1><fmt:message key="form.pricingissues.title"/></h1></td></tr>
		        <tr><td colspan="8">&#160;</td></tr>
		        <tr>
		            <td class="label" style="width:50px"><fmt:message key="form.label.date"/>:</td>
		            <td style="width:75px" class="userinput"><c:out value="${pricingIssuesBean.today}"/></td>
		            <td style="width:10px">&#160;</td>
		            <td class="label" style="width:75px"><fmt:message key="form.label.storenum"/>:</td>
		            <td style="width:50px" class="userinput"><c:out value="${pricingIssuesBean.storeNum}"/></td>
		            <td style="width:10px">&#160;</td>
		            <td class="label" style="width:100px"><fmt:message key="form.label.submittedby"/>:</td>
		            <td style="width:150px" class="userinput"><c:out value="${pricingIssuesBean.submittedBy}"/></td>
		        </tr>
		    </tbody>
		</table>
        
        <!-- BODY //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.ispheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.stylenum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.upc"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.currentprice"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.correctprice"/></th>
                </tr>
                <!--Display all Incorrect System Price items-->
                <c:forEach begin="0" end="9" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.ispDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.ispDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.ispStyle[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.ispStyle[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.ispUpc[i.index]}">
                            <td>__________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.ispUpc[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.ispCurrentPrice[i.index]}">
                            <td>__________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.ispCurrentPrice[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.ispCorrectPrice[i.index]}">
                            <td>__________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.ispCorrectPrice[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.pnwheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th colspan="9" class="subth"><fmt:message key="form.label.stylescanned"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.correctprice"/></th>
                </tr>
                <!--Display all Promotion or Package Not Working items-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwStyle1[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwStyle1[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwStyle2[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwStyle2[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwStyle3[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwStyle3[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwStyle4[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwStyle4[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwStyle5[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwStyle5[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.pnwCorrectPrice[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.pnwCorrectPrice[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.isdheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.stylenum"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.comments"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.isdDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.isdDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.isdStyle[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.isdStyle[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.isdComment[i.index]}">
                            <td>_______________________________________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.isdComment[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.upczeroheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.upc"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.upczeroDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.upczeroDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.upczeroUpc[i.index]}">
                            <td>____________________________________________________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.upczeroUpc[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.upcnsheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.upc"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.upcnsDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.upcnsDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.upcnsUpc[i.index]}">
                            <td>____________________________________________________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.upcnsUpc[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.itpheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.currenttag"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.correcttag"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.itpDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.itpDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.itpCurrentTag[i.index]}">
                            <td>_____________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.itpCurrentTag[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.itpCorrectTag[i.index]}">
                            <td>_____________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.itpCorrectTag[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>


        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.pricingissues.otherheader"/></th></tr>
            </tbody>
        </table>
        <p class="linebreak"/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <th class="subth"><fmt:message key="form.label.dept"/></th>
                    <td class="spacer">&#160;</td>
                    <th class="subth"><fmt:message key="form.label.description"/></th>
                </tr>
                <!--Display all carton/dds-->
                <c:forEach begin="0" end="3" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.otherDept[i.index]}">
                            <td>____________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.otherDept[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="spacer">&#160;</td>
                    <c:choose>
                        <c:when test="${empty pricingIssuesBean.otherComment[i.index]}">
                            <td>_____________________________________________________________________________</td>
                        </c:when><c:otherwise>
                            <td class="smalltext userinput"><c:out value="${pricingIssuesBean.otherComment[i.index]}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
            </tbody>
        </table>



        <!-- EMAIL DISCLAIMER //-->
        <p/>
		<table>
		    <tbody>
		        <tr><th>
		            <fmt:message key="form.pricingissues.emailtodisclaimer">
		                <fmt:param value="${pricingIssuesBean.document.emailAddress}"/>
		            </fmt:message>
		        </th></tr>
		    </tbody>
		</table>
		
        <table class="smalltext" style="width:95%;margin-left:10">
            <tbody>
                <tr><td class="emsmalltext" colspan="6"><fmt:message key="form.pricingissues.note"/></td></tr>
            </tbody>
        </table>

    </body>
</pdf>
