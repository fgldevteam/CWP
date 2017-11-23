<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.damagedcartons.title"/></title>
    </head>
    <body>
    <html:form action="/secure/forms/submitDamagedCarton.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/damagedCartons.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.damagedcartons.title'/>"/>
        <input type="hidden" name="subject" value="<fmt:message key='form.label.storenum'/>: <c:out value='${loginStore.number}'/> - <fmt:message key='form.damagedcartons.title'/>"/>
        <table class="data pdfform">
            <caption><fmt:message key="form.damagedcartons.title"/></caption>
            <!-- HEADER //-->
            <tr>
                <td class="label"><fmt:message key="form.label.date"/>:</td>
                <td colspan="2"><html:text property="today" readonly="true" styleClass="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.claimnum"/>:</td>
                <td colspan="2"><input type="text" disabled class="disabledInput"/></td>
            </tr><tr>
                <td colspan="4">&nbsp;</td>
                <td class="label"><fmt:message key="form.label.duedate"/>:</td>
                <td colspan="2"><input type="text" disabled class="disabledInput"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.storenum"/>:</td>
                <td colspan="2"><input type="text" value="<fmt:formatNumber value='${damagedCartonBean.storeNum}' groupingUsed='false' maxIntegerDigits='4' minIntegerDigits='4'/>" readonly class="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label">
                    <input type="checkbox" disabled/><fmt:message key="form.label.closed"/>:
                </td>
                <td colspan="2"><input type="text" disabled class="disabledInput"/></td>
            </tr>
            <tr><th colspan="7"><fmt:message key="form.damagedcartons.header"/></th></tr>
            <tr>
                <th><fmt:message key="form.label.cartonnum"/></th>
                <th><fmt:message key="form.label.ddsnum"/></th>
                <th><fmt:message key="form.label.carriernum"/></th>
                <th><fmt:message key="form.label.manifestnum"/></th>
                <th><fmt:message key="form.label.shipdate"/></th>
                <th><fmt:message key="form.label.whnum"/></th>
                <th><fmt:message key="form.label.status"/></th>
            </tr>
            <!--Display all carton/dds-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
                <td class="inputfield"><html:text name="damagedCartonBean" property="carton[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="damagedCartonBean" property="dds[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:70px"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:140px"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:70px"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:70px"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:70px"/></td>
            </tr>
            </c:forEach>
            <tr><td colspan="7" class="note"><fmt:message key="form.claim.answerallcaption"/></td></tr>
            <tr><td colspan="7" class="note"><fmt:message key="form.claim.answerallnote"/></td></tr>                
            <tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">1.</td>
                            <td style="width:450px"><fmt:message key="form.damagedcartons.q1"/></td>
                            <td style="width:50px"><html:radio property="answer1" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td style="width:50px"><html:radio property="answer1" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td colspan="3"><fmt:message key="form.damagedcartons.q1a"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td colspan="3"><html:text property="answer1a" size="82"/></td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">2.</td>
                        <td style="width:450px"><fmt:message key="form.damagedcartons.q2"/></td>
                        <td style="width:50px"><html:radio property="answer2" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer2" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">3.</td>
                            <td style="width:450px"><fmt:message key="form.damagedcartons.q3"/></td>
                            <td style="width:50px"><html:radio property="answer3" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td style="width:50px"><html:radio property="answer3" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td><fmt:message key="form.damagedcartons.q3a"/></td>
                            <td><html:radio property="answer3a" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td><html:radio property="answer3a" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">4.</td>
                            <td style="width:550px"><fmt:message key="form.damagedcartons.q4"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td><html:text property="answer4" size="82"/></td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">5.</td>
                            <td style="width:550px"><fmt:message key="form.damagedcartons.q5"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td><html:text property="answer5" size="82"/></td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">6.</td>
                        <td style="width:450px"><fmt:message key="form.damagedcartons.q6"/></td>
                        <td style="width:50px"><html:radio property="answer6" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer6" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">7.</td>
                            <td style="width:450px"><fmt:message key="form.damagedcartons.q7"/></td>
                            <td style="width:50px"><html:radio property="answer7" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td style="width:50px"><html:radio property="answer7" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td colspan="3">
                            <table>
                            <tr><td><fmt:message key="form.damagedcartons.q7a"/>&nbsp;&nbsp;<fmt:message key="form.label.reportedto"/>:<html:text property="reportedTo"/></td>
                            <td style="width:300px">
                                <fmt:message key="form.label.datereported"/>:<html:text property="dateReported" readonly="true" styleId="dateReported"/>
                                <html:img styleId="dateReportedCal" page="/images/cal.gif" style="cursor: pointer;"/>
                                <jsp:include page="/jsp/includes/calendarSetup.jsp">
                                    <jsp:param name="inputField" value="dateReported"/>
                                    <jsp:param name="button" value="dateReportedCal"/>
                                </jsp:include>
                            </td>
                            </tr>
                            </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">8.</td>
                        <td style="width:450px"><fmt:message key="form.damagedcartons.q8"/></td>
                        <td style="width:50px"><html:radio property="answer8" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer8" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">9.</td>
                        <td style="width:550px"><fmt:message key="form.damagedcartons.q9"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">10.</td>
                        <td style="width:450px"><fmt:message key="form.damagedcartons.q10"/></td>
                        <td style="width:50px"><html:radio property="answer10" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer10" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">11.</td>
                        <td style="width:450px"><fmt:message key="form.damagedcartons.q11"/></td>
                        <td style="width:50px"><html:radio property="answer11" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer11" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">&nbsp;</td>
                            <td style="width:550px"><fmt:message key="form.damagedcartons.q11a"/></td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td><html:text property="answer11a" size="82"/></td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7"><fmt:message key="form.label.comments"/>:</td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">&nbsp;</td>
                        <td><html:textarea property="comments" rows="5" cols="60"></html:textarea></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7"><html:checkbox property="reviewedByStoreMgr" value="true"/><fmt:message key="form.label.reviewbystoremgr"/></td>
            </tr><tr>
                <td colspan="7">
                <table style="width:100%;"><tr>
                <td align="left"><fmt:message key="form.label.storemgrname"/>:<html:text property="storeMgrName"/></td>
                <td align="right"><fmt:message key="form.label.claimsubmittedby"/>:<html:text property="submittedBy"/></td>
                </tr></table>
                </td>
            </tr><tr>
                <td colspan="7" class="note">
                    <fmt:message var="claimFax" key="form.claim.fax"/>
                    <fmt:message key="form.damagedcartons.faxbilloflading">
                        <fmt:param value="${claimFax}"/>
                    </fmt:message>
                </td>
            </tr><tr>
                <td colspan="7" style="text-align:right;"><input type="submit" value="<fmt:message key='form.button.label.sendemail'/>"/></td>
            </tr>
        </table>
    </html:form>
    </body>
</html>