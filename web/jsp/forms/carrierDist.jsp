<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.carrierdist.title"/></title>
    </head>
    <body>
    <html:form action="/secure/forms/submitCarrierDist.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/carrierDist.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.carrierdist.title'/>"/>
        <input type="hidden" name="subject" value="<fmt:message key='form.label.storenum'/>: <c:out value='${loginStore.number}'/> - <fmt:message key='form.carrierdist.title'/>"/>
        
        <table class="data pdfform">
            <caption><fmt:message key="form.carrierdist.title"/></caption>
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
                <td colspan="2"><input type="text" value="<fmt:formatNumber value='${carrierDistBean.storeNum}' groupingUsed='false' maxIntegerDigits='4' minIntegerDigits='4'/>" readonly class="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label">
                    <input type="checkbox" disabled/><fmt:message key="form.label.closed"/>:
                </td>
                <td colspan="2"><input type="text" disabled class="disabledInput"/></td>
            </tr>
            <tr><th colspan="7"><fmt:message key="form.carrierdist.header"/></th></tr>
            <tr>
                <th><fmt:message key="form.label.cartonnum"/></th>
                <th><fmt:message key="form.label.ddsnum"/></th>
                <th><fmt:message key="form.label.shipdate"/></th>
                <th><fmt:message key="form.label.carriernum"/></th>
                <th><fmt:message key="form.label.manifestnum"/></th>
                <th><fmt:message key="form.label.whnum"/></th>
                <th><fmt:message key="form.label.status"/></th>
            </tr>
            <!--Display all carton/dds-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
                <td class="inputfield"><html:text name="carrierDistBean" property="carton[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="carrierDistBean" property="dds[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:70px"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:70px"/></td>
                <td class="inputfield"><input type="text" class="disabledInput" disabled style="width:140px"/></td>
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
                            <td style="width:450px"><fmt:message key="form.carrierdist.q1"/></td>
                            <td style="width:50px"><html:radio property="answer1" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td style="width:50px"><html:radio property="answer1" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr><tr>
                            <td style="width:20px"></td>
                            <td style="width:450px"><fmt:message key="form.carrierdist.q1a"/></td>
                            <td style="width:50px"><html:radio property="answer1a" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td style="width:50px"><html:radio property="answer1a" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">2.</td>
                        <td style="width:450px"><fmt:message key="form.carrierdist.q2"/></td>
                        <td style="width:50px"><html:radio property="answer2" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer2" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr>
            
            <tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">3.</td>
                        <td style="width:450px"><fmt:message key="form.carrierdist.q3"/></td>
                        <td style="width:50px"><html:radio property="answer3" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer3" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr>
            
            <tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">4.</td>
                        <td style="width:450px"><fmt:message key="form.carrierdist.q4"/></td>
                        <td style="width:50px"><html:radio property="answer4" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer4" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr>
            
            <tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">&nbsp;</td>
                            <td><fmt:message key="form.carrierdist.q4a"/></td>
                        </tr><tr>
                            <td style="width:20px">&nbsp;</td>
                            <td><html:textarea property="answer4a" rows="3" cols="85"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">5.</td>
                        <td style="width:450px"><fmt:message key="form.carrierdist.q5"/></td>
                        <td style="width:50px"><html:radio property="answer5" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer5" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr></table>
                </td>
            </tr>
            <tr>
                <td colspan="7">
                    <table style="width:100%;">
                    <tr>
                        <td style="width:20px">6.</td>
                        <td style="width:450px"><fmt:message key="form.carrierdist.q6"/></td>
                        <td style="width:50px"><html:radio property="answer6" value="true"/><fmt:message key="form.label.yes"/></td>
                        <td style="width:50px"><html:radio property="answer6" value="false"/><fmt:message key="form.label.no"/></td>
                    </tr><tr>
                        <td style="width:20px">&nbsp;</td>
                        <td colspan="3">
                            <fmt:message key="form.carrierdist.q6a"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <fmt:message key="form.label.reportedto"/>:
                            <html:text property="reportedTo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <fmt:message key="form.label.datereported"/>: <html:text property="dateReported" readonly="true" styleId="dateReported"/>
                            <html:img styleId="dateReportedCal" page="/images/cal.gif" style="cursor: pointer;"/>
                            <jsp:include page="/jsp/includes/calendarSetup.jsp">
                                <jsp:param name="inputField" value="dateReported"/>
                                <jsp:param name="button" value="dateReportedCal"/>
                            </jsp:include>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7"><fmt:message key="form.carrierdist.comments"/>:</td>
            </tr><tr>
                <td colspan="7">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">&nbsp;</td>
                        <td><html:textarea property="comments" rows="5" cols="85"></html:textarea></td>
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
                <td colspan="7" style="text-align:right;"><input type="submit" value="<fmt:message key='form.button.label.sendemail'/>"/></td>
            </tr>
        </table>
    </html:form>
    </body>
</html>
