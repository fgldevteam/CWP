<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.ironhorse.title"/></title>
        <style type="text/css">
            table.data td.model, table.data td.cbox{
            border:1px solid silver;
            }
            table.data td.cbox{
            text-align:center;
            }
        </style>
    </head>
    <body>
    <html:form action="/secure/forms/submitIronHorse.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/ironHorse.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.ironhorse.title'/>"/>
        <input type="hidden" name="emailTo" value="<fmt:message key='form.ironhorse.person.email'/>"/>
        
        <table class="data pdfform">
            <caption><fmt:message key="form.ironhorse.title"/></caption>
            <!-- HEADER //-->
            <tr>
                <td class="label"><fmt:message key="form.label.date"/>:</td>
                <td colspan="2"><html:text property="today" readonly="true" styleClass="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.manager"/>:</td>
                <td colspan="2"><html:text property="manager"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.storenum"/>:</td>
                <td colspan="2"><html:text property="store.number" readonly="true" styleClass="disabledInput"/></td>
                <td colspan="4">&nbsp;</td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.storeName"/>:</td>
                <td colspan="6"><html:text property="store.name" readonly="true" styleClass="disabledInput" style="width:306"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.address"/>:</td>
                <td colspan="6"><html:text property="store.address" readonly="true" styleClass="disabledInput" style="width:306"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.city"/>:</td>
                <td colspan="2"><html:text property="store.city" readonly="true" styleClass="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.province"/>:</td>
                <td colspan="2"><html:text property="store.province" readonly="true" styleClass="disabledInput"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.postalcode"/>:</td>
                <td colspan="2"><html:text property="store.postalCode" readonly="true" styleClass="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.telephone"/>:</td>
                <td colspan="2"><html:text property="store.phoneNumber" readonly="true" styleClass="disabledInput"/></td>
            </tr><tr>
                <th colspan="7"><fmt:message key="form.ironhorse.customerinfo"/></th>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.name"/>:</td>
                <td colspan="6"><html:text property="name" style="width:306"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.address"/>:</td>
                <td colspan="6"><html:text property="address" style="width:306"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.city"/>:</td>
                <td colspan="2"><html:text property="city"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.province"/>:</td>
                <td colspan="2"><html:text property="province"/></td>
            </tr><tr>
                <td class="label"><fmt:message key="form.label.postalcode"/>:</td>
                <td colspan="2"><html:text property="postalCode"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.telephone"/>:</td>
                <td colspan="2"><html:text property="phone"/></td>
            </tr><tr>
                <th colspan="7"><fmt:message key="form.ironhorse.bikerequest"/></th>
            </tr><tr>
                <td colspan="7">&nbsp;</td>
            </tr><tr>
                <td colspan="7" style="text-align:left;">
                    <fmt:message var="sizes" key="form.ironhorse.bikesizes"/>
                   	<c:set var="sizeCount" value="0"/>
	                <c:forTokens items="${sizes}" delims="," var="currSize" varStatus="i">
                    	<c:set var="sizeCount" value="${sizeCount+1}"/>
                    </c:forTokens>                            
                    <table class="data" style="width:100%">
                        <tr>
                            <th rowspan="2"><fmt:message key="form.label.availablemodels"/></th>
                            <th colspan="<c:out value='${sizeCount}'/>"><fmt:message key="form.label.sizes"/></th>
                        </tr>
                        <tr>
                            <c:forTokens items="${sizes}" delims="," var="currSize" varStatus="i">
                                <th><c:out value="${currSize}"/></th>
                            </c:forTokens>
                        </tr>                        
                        <fmt:message var="models" key="form.ironhorse.bikemodels"/>
                        <c:forTokens items="${models}" delims="," var="currModel" varStatus="i">
                            <tr>
                                <td class="model"><c:out value="${currModel}"/></td>
                                <c:forTokens items="${sizes}" delims="," var="currSize" varStatus="j">
                                    <td class="cbox"><html:checkbox property="selectedSizes[${(i.index*sizeCount)+j.index}]" /></td>
                                </c:forTokens>
                            </tr>
                        </c:forTokens>
                    </table>
                </td>
            </tr><tr>
                <td colspan="7">&nbsp;</td>
            </tr><tr>
                <td colspan="4">
                1. <fmt:message key="form.ironhorse.q1"/>
                </td>
                <td colspan="3">
                    <html:radio property="answer1" value="true"><fmt:message key="form.label.yes"/></html:radio>
                    <html:radio property="answer1" value="false"><fmt:message key="form.label.no"/></html:radio>
                </td>
            </tr><tr>
                <td colspan="7">&nbsp;</td>
            </tr><tr>
                <td colspan="7" class="note">
                    <fmt:message var="pName" key="form.ironhorse.person"/>
                    <fmt:message var="pFax" key="form.ironhorse.person.fax"/>
                    <fmt:message var="pEmail" key="form.ironhorse.person.email"/>
                    <fmt:message key="form.ironhorse.note.faxcopy">
                        <fmt:param value="${pName}"/>
                        <fmt:param value="${pFax}"/>
                        <fmt:param value="${pEmail}"/>
                    </fmt:message>
                </td>
            </tr><tr>
                <td colspan="7" class="note"><fmt:message key="form.ironhorse.note.customercopy"/></td>
            </tr><tr>
                <td colspan="7" style="text-align:right;"><input type="submit" value="<fmt:message key='form.button.label.sendemail'/>"/></td>
            </tr>
        </table>
    </html:form>
    </body>
</html>
