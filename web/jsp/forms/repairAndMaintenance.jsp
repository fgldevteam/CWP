<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.repairandmaintain.title"/></title>
    </head>
    <body>
    <html:form action="/secure/forms/submitRepairAndMaintain.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/repairAndMaintenance.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.repairandmaintain.title'/>"/>
        <input type="hidden" id="emailTo" name="to" value="<fmt:message key='form.repairandmaintain.maintenance.person.email'/>"/>
        <input type="hidden" name="subject" value="<fmt:message key='form.label.storenum'/>: <c:out value='${loginStore.number}'/> - <fmt:message key='form.repairandmaintain.title'/>"/>

        <table class="data">
            <caption><fmt:message key="form.repairandmaintain.title"/></caption>
            <!-- HEADER //-->
            <tr>
                <td class="label"><fmt:message key="form.label.date"/>:</td>
                <td colspan="2"><html:text property="today" readonly="true" styleClass="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.manager"/>:</td>
                <td colspan="2"><html:text property="manager"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.storeName"/>:</td>
                <td colspan="5"><html:text property="store.name" readonly="true" styleClass="disabledInput" style="width:306"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.city"/>:</td>
                <td colspan="2"><html:text property="store.city" readonly="true" styleClass="disabledInput"/></td>
                <td colspan="3">&nbsp;</td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.telephone"/>:</td>
                <td colspan="2"><html:text property="store.phoneNumber" readonly="true" styleClass="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.fax"/>:</td>
                <td colspan="2"><html:text property="fax"/></td>
            </tr>
            <tr>
                <th colspan="7"><fmt:message key="form.repairandmaintain.issue.maintenance"/></th>
            </tr><tr>
                <td colspan="7" style="text-align:center;">
                    <table>
                        <tr>
                            <td><input type="radio" checked="checked" disabled="disabled" name="requestType"/></td>
                            <td style="width:400px">
                            <fmt:message key="form.repairandmaintain.maintenance.desc"/>
                            </td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td>
                                <fmt:message key="form.repairandmaintain.emailrequest"/>:<br/><br/>
                                <fmt:message key="form.repairandmaintain.maintenance.person"/><br/>
                                <fmt:message key="form.repairandmaintain.maintenance.person.title"/><br/>
                                <fmt:message key="form.label.telephone"/>:&nbsp;<fmt:message key="form.repairandmaintain.maintenance.person.phone"/><br/>
                                <fmt:message key="form.label.email"/>:&nbsp;<fmt:message key="form.repairandmaintain.maintenance.person.email"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <th colspan="7"><fmt:message key="form.repairandmaintain.issue.lossprevention"/></th>
            </tr><tr>
                <td colspan="7" style="text-align:center;">
                    <table>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
	                            <fmt:message key="form.repairandmaintain.lossprevention.desc"/>
                            </td>
                        </tr><tr>
                            <td>&nbsp;</td>
                            <td>
    	                        <fmt:message key="form.repairandmaintain.lossprevention.desc2"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr><tr>
                <th colspan="7"><fmt:message key="form.repairandmaintain.workDetail"/></th>
            </tr><tr>
                <td colspan="7">
                    <html:textarea property="workDetail" rows="5" cols="95"></html:textarea>
                </td>
            </tr><tr>
                <td colspan="7">
                    <fmt:message var="person" key="form.repairandmaintain.maintenance.person"/>
                    <fmt:message key="form.repairandmaintain.questions">
                        <fmt:param value="${person}"/>
                    </fmt:message>
                </td>
            </tr><tr>
                <td colspan="7" style="text-align:right;"><input type="submit" value="<fmt:message key='form.button.label.sendemail'/>"/></td>
            </tr>
        </table>
    </html:form>
    </body>
</html>
