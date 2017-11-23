<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.inventoryadj.title"/></title>
    </head>
    <body>
    <html:form action="/secure/forms/submitInventoryAdj.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/inventoryAdj.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.inventoryadj.title'/>"/>
        <input type="hidden" name="subject" value="<fmt:message key='form.label.storenum'/>: <c:out value='${loginStore.number}'/> - <fmt:message key='form.inventoryadj.title'/>"/>
        
        <table class="data pdfform">
            <caption><fmt:message key="form.inventoryadj.title"/></caption>
            <!-- HEADER //-->
            <tr>
                <td colspan="2" class="label"><fmt:message key="form.label.date"/>:</td>
                <td colspan="2"><html:text property="today" readonly="true" styleClass="disabledInput"/></td>
            </tr>
            <tr>
                <td colspan="2" class="label"><fmt:message key="form.label.storenum"/>:</td>
                <td colspan="2"><input type="text" value="<fmt:formatNumber value='${inventoryAdjBean.storeNum}' groupingUsed='false' maxIntegerDigits='4' minIntegerDigits='4'/>" readonly class="disabledInput"/></td>
            </tr>
            <tr><th colspan="8"><fmt:message key="form.inventoryadj.header"/></th></tr>
            <tr>
                <th colspan="2"><fmt:message key="form.label.upc"/></th>
                <th><fmt:message key="form.label.quantity"/></th>
                <th><fmt:message key="form.label.price"/></th>
                <th colspan="4"><fmt:message key="form.label.description"/></th>
            </tr>
            <!--Display all inventory adjustments-->
            <c:forEach begin="0" end="19" varStatus="i">
            <tr>
                <td colspan="2" class="inputfield"><html:text name="inventoryAdjBean" property="upc[${i.index}]" style="width:140px;"/></td>
                <td class="inputfield"><html:text name="inventoryAdjBean" property="quantity[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="inventoryAdjBean" property="price[${i.index}]" style="width:70px;"/></td>
                <td colspan="4" class="inputfield"><html:text name="inventoryAdjBean" property="description[${i.index}]" style="width:280px;"/></td>
            </tr>
            </c:forEach>
            <tr><td colspan="8" class="note"><fmt:message key="form.inventoryadj.note"/></td></tr>                
			<tr>
                <td colspan="8"><fmt:message key="form.label.explanation"/>:</td>
            </tr>
            <tr>
                <td colspan="8">
                    <table style="width:100%;"><tr>
                        <td style="width:20px">&nbsp;</td>
                        <td><html:textarea property="explanation" rows="5" cols="60"></html:textarea></td>
                    </tr></table>
                </td>
            </tr>
            <tr>
                <td colspan="8"><html:checkbox property="reviewedByStoreMgr" value="true"/><fmt:message key="form.label.inv.reviewbystoremgr"/></td>
            </tr>
            <tr>
                <td colspan="8">
                <table style="width:100%;"><tr>
                <td align="left"><fmt:message key="form.label.storemgrname"/>: <html:text property="storeMgrName"/></td>
                <td align="right"><fmt:message key="form.label.submittedby"/>: <html:text property="submittedBy"/></td>
                </tr></table>
                </td>
            </tr>
			<tr>
                <td colspan="8" style="text-align:right;"><input type="submit" value="<fmt:message key='form.button.label.sendemail'/>"/></td>
            </tr>
        </table>
    </html:form>
    </body>
</html>

