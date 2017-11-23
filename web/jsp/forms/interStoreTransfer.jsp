<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.interstoretransfer.title"/></title>
    </head>
    <body>
    <html:form action="/secure/forms/submitInterStoreTransfer.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/interStoreTransfer.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.interstoretransfer.title'/>"/>
        <input type="hidden" name="subject" value="<fmt:message key='form.label.storenum'/>: <c:out value='${loginStore.number}'/> - <fmt:message key='form.interstoretransfer.title'/>"/>
        <table class="data pdfform">
            <caption><fmt:message key="form.interstoretransfer.title"/></caption>
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
                <td colspan="2"><input type="text" value="<fmt:formatNumber value='${interStoreTransferBean.storeNum}' groupingUsed='false' maxIntegerDigits='4' minIntegerDigits='4'/>" readonly class="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label">
                    <input type="checkbox" disabled/><fmt:message key="form.label.closed"/>:
                </td>
                <td colspan="2"><input type="text" disabled class="disabledInput"/></td>
            </tr>
            <tr><th colspan="7"><fmt:message key="form.interstoretransfer.header"/></th></tr>
            <tr><td colspan="7"><fmt:message key="form.interstoretransfer.note.followupwithsendingstore"/></td></tr>
            <tr><td colspan="7"><fmt:message key="form.interstoretransfer.note.claimontransfer"/></td></tr>
            <tr>
                <th><fmt:message key="form.label.shipdate"/></th>
                <th><fmt:message key="form.label.transfernumber"/></th>
                <th><fmt:message key="form.label.horefnum"/></th>
                <th><fmt:message key="form.label.sendingstore"/></th>
                <th colspan="3"><fmt:message key="form.label.courierpinnum"/></th>
            </tr>
            <!--Display all carton/dds-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
                <td class="inputfield"><html:text name="interStoreTransferBean" property="shipDate[${i.index}]" style="width:100px;"/></td>
                <td class="inputfield"><html:text name="interStoreTransferBean" property="jda[${i.index}]" style="width:100px;"/></td>
                <td class="inputfield"><html:text name="interStoreTransferBean" property="hoRef[${i.index}]" style="width:100px;"/></td>
                <td class="inputfield"><html:text name="interStoreTransferBean" property="sendingStore[${i.index}]" style="width:100px;"/></td>
                <td class="inputfield" colspan="3"><html:text name="interStoreTransferBean" property="courierPIN[${i.index}]" style="width:220px;"/></td>
            </tr>
            </c:forEach>
            <tr>
                <td colspan="7">
                    <fmt:message key="form.label.sendingstorecontact"/>:
                    <html:text name="interStoreTransferBean" property="sendingStoreContact"/>
                </td>
            </tr>
            <tr><td colspan="7" class="note"><fmt:message key="form.claim.answerallcaption"/></td></tr>
            <tr><td colspan="7" class="note"><fmt:message key="form.claim.answerallnote"/></td></tr>                
            <tr>
                <td colspan="7">
                    <table style="width:100%;">
                        <tr>
                            <td style="width:20px">1.</td>
                            <td style="width:450px"><fmt:message key="form.interstoretransfer.q1"/></td>
                            <td style="width:50px"><html:radio property="answer1" value="true"/><fmt:message key="form.label.yes"/></td>
                            <td style="width:50px"><html:radio property="answer1" value="false"/><fmt:message key="form.label.no"/></td>
                        </tr><tr>
                            <td style="width:20px"></td>
                            <td colspan="3">
                                <fmt:message key="form.interstoretransfer.q1a"/>&nbsp;&nbsp;&nbsp;
                                <fmt:message key="form.label.tracenum"/>:
                                <html:text property="traceNum" style="width:80px;"/>&nbsp;&nbsp;&nbsp;
                                <fmt:message key="form.label.contact"/>:
                                <html:text property="contact" style="width:80px;"/>&nbsp;&nbsp;&nbsp;
                                <fmt:message key="form.label.datetraceopened"/>:
                                <html:text property="dateTraceOpened" readonly="true" styleId="dateTraceOpened" style="width:80px"/>
                                <html:img styleId="dateTraceOpenedCal" page="/images/cal.gif" style="cursor: pointer;"/>
                                <jsp:include page="/jsp/includes/calendarSetup.jsp">
                                    <jsp:param name="inputField" value="dateTraceOpened"/>
                                    <jsp:param name="button" value="dateTraceOpenedCal"/>
                                </jsp:include>
                            </td>
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
                <td colspan="7"><hr/></td>
            </tr><tr>
                <td colspan="7" class="note"><fmt:message key="form.interstoretransfer.note.checkappropriatebox"/></td>
            </tr><tr>
                <td colspan="7" class="note"><fmt:message key="form.label.fax"/>: <fmt:message key="form.claim.fax"/></td>
            </tr><tr>
                <td colspan="7" class="note"><fmt:message key="form.interstoretransfer.note.useclaimascoverpage"/></td>
            </tr><tr>
                <td colspan="7">
                    <table><tr>
                    <td><html:checkbox property="puroBol"/></td>
                    <td><fmt:message key="form.label.purobol"/></td>
                    <td><html:checkbox property="transferSlip"/></td>
                    <td><fmt:message key="form.label.transferslip"/></td>
                    </tr></table>
                </td>
            </tr><tr>
                <td colspan="7">
                    <table><tr>
                        <td><fmt:message key="form.label.totalpagessent"/>:</td>
                        <td><html:text property="totalPagesSent" style="width:30px"/></td>
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
