<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html>
    <head>
    	<title><fmt:message key="form.pricingissues.title"/></title>
    </head>
    <body>
    <html:form action="/secure/forms/submitPricingIssues.do" method="POST">
        <!-- path to pdf xml template //-->
        <html:hidden property="xmlTemplateURL" value="/forms/pricingIssues.jsp"/>
        <input type="hidden" name="fileName" value="<fmt:message key='form.pricingissues.title'/>"/>
        <input type="hidden" name="subject" value="<fmt:message key='form.label.storenum'/>: <c:out value='${loginStore.number}'/> - <fmt:message key='form.pricingissues.title'/>"/>
        
        <table class="data pdfform">
            <caption><fmt:message key="form.pricingissues.title"/></caption>
            <!-- HEADER //-->
            <tr>
                <td class="label"><fmt:message key="form.label.storenum"/>:</td>
                <td colspan="2"><input type="text" value="<fmt:formatNumber value='${pricingIssuesBean.storeNum}' groupingUsed='false' maxIntegerDigits='4' minIntegerDigits='4'/>" readonly class="disabledInput"/></td>
                <td>&nbsp;</td>
                <td class="label"><fmt:message key="form.label.date"/>:</td>
                <td colspan="3"><html:text property="today" readonly="true" styleClass="disabledInput"/></td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="form.label.submittedby"/>:</td>
				<td colspan="2"><html:text property="submittedBy"/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
				<td colspan="8"><fmt:message key="form.pricingissues.label.requiredfields"/></td>
            </tr>
            <tr>
				<td colspan="8"><fmt:message key="form.pricingissues.label.styleorupcrequired"/></td>
            </tr>
            <tr>
				<td colspan="8"><fmt:message key="form.pricingissues.label.stylenote"/></td>
            </tr>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.ispheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th><fmt:message key="form.pricingissues.label.stylenum2"/></th>
                <th colspan="2"><fmt:message key="form.pricingissues.label.upc2"/></th>
                <th colspan="2"><fmt:message key="form.pricingissues.label.currentprice"/></th>
                <th colspan="2"><fmt:message key="form.label.correctprice"/></th>
            </tr>
            <!--Display all incorrect system prices (isp)-->
            <c:forEach begin="0" end="9" varStatus="i">
            <tr>
				<td class="inputfield">
	        		<html:select name="pricingIssuesBean" property="ispDept[${i.index}]">
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
    				</html:select>
				</td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="ispStyle[${i.index}]" style="width:70px;"/></td>
                <td colspan="2" class="inputfield"><html:text name="pricingIssuesBean" property="ispUpc[${i.index}]" style="width:140px;"/></td>
                <td colspan="2" class="inputfield"><html:text name="pricingIssuesBean" property="ispCurrentPrice[${i.index}]" style="width:140px;"/></td>
                <td colspan="2" class="inputfield"><html:text name="pricingIssuesBean" property="ispCorrectPrice[${i.index}]" style="width:140px;"/></td>
            </tr>
            </c:forEach>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.pnwheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th colspan="5"><fmt:message key="form.pricingissues.label.stylescanned"/></th>
                <th colspan="2"><fmt:message key="form.label.correctprice"/></th>
            </tr>
            <!--Display all promotion or packaging not working (pnw)-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
				<td class="inputfield">
					<html:select name="pricingIssuesBean" property="pnwDept[${i.index}]" >
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
                    </html:select>
				</td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="pnwStyle1[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="pnwStyle2[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="pnwStyle3[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="pnwStyle4[${i.index}]" style="width:70px;"/></td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="pnwStyle5[${i.index}]" style="width:70px;"/></td>
                <td colspan="2" class="inputfield"><html:text name="pricingIssuesBean" property="pnwCorrectPrice[${i.index}]" style="width:140px;"/></td>
            </tr>
            </c:forEach>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.isdheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th><fmt:message key="form.pricingissues.label.stylenum"/></th>
                <th colspan="6"><fmt:message key="form.label.comments"/></th>
            </tr>
            <!--Display all inaccurate or incomplete sign description (isd)-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
				<td class="inputfield">
					<html:select name="pricingIssuesBean" property="isdDept[${i.index}]" >
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
                    </html:select>
				</td>
                <td class="inputfield"><html:text name="pricingIssuesBean" property="isdStyle[${i.index}]" style="width:70px;"/></td>
                <td colspan="6" class="inputfield"><html:text name="pricingIssuesBean" property="isdComment[${i.index}]" style="width:445px;"/></td>
            </tr>
            </c:forEach>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.upczeroheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th colspan="7"><fmt:message key="form.pricingissues.label.upc"/></th>
            </tr>
            <!--Display all upc ringing up at $0.00 (upczero)-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
				<td class="inputfield">
					<html:select name="pricingIssuesBean" property="upczeroDept[${i.index}]" >
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
                    </html:select>
				</td>
                <td colspan="7" class="inputfield"><html:text name="pricingIssuesBean" property="upczeroUpc[${i.index}]" style="width:525px;"/></td>
            </tr>
            </c:forEach>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.upcnsheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th colspan="7"><fmt:message key="form.pricingissues.label.upcanddescription"/></th>
            </tr>
            <!--Display all upc not scanning (upcns)-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
				<td class="inputfield">
					<html:select name="pricingIssuesBean" property="upcnsDept[${i.index}]" >
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
                    </html:select>
				</td>
                <td colspan="7" class="inputfield"><html:text name="pricingIssuesBean" property="upcnsUpc[${i.index}]" style="width:525px;"/></td>
            </tr>
            </c:forEach>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.itpheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th colspan="3"><fmt:message key="form.label.currenttag"/></th>
                <th colspan="4"><fmt:message key="form.label.correcttag"/></th>
            </tr>
            <!--Display all incorrectly tagged product (itp)-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
				<td class="inputfield">
					<html:select name="pricingIssuesBean" property="itpDept[${i.index}]" >
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
                    </html:select>
				</td>
                <td colspan="3" class="inputfield"><html:text name="pricingIssuesBean" property="itpCurrentTag[${i.index}]" style="width:220px;"/></td>
                <td colspan="4" class="inputfield"><html:text name="pricingIssuesBean" property="itpCorrectTag[${i.index}]" style="width:290px;"/></td>
            </tr>
            </c:forEach>
            <tr><th colspan="8"><fmt:message key="form.pricingissues.otherheader"/></th></tr>
            <tr>
                <th><fmt:message key="form.pricingissues.label.dept"/></th>
                <th colspan="7"><fmt:message key="form.label.comments"/></th>
            </tr>
            <!--Display all other (other)-->
            <c:forEach begin="0" end="3" varStatus="i">
            <tr>
				<td class="inputfield">
					<html:select name="pricingIssuesBean" property="otherDept[${i.index}]" >
			            <c:forEach var="item" items="${pricingIssuesBean.deptList}">
    				    <html:option value="${item}"/>
    				    </c:forEach>
                    </html:select>
				</td>
                <td colspan="7" class="inputfield"><html:text name="pricingIssuesBean" property="otherComment[${i.index}]" style="width:525px;"/></td>
            </tr>
            </c:forEach>
            <tr>
            	<td colspan="8" class="note"><fmt:message key="form.pricingissues.note"/></td>
            </tr>
			<tr>
                <td colspan="8" style="text-align:right;"><input type="submit" value="<fmt:message key='form.button.label.sendemail'/>"/></td>
            </tr>
        </table>
    </html:form>
    </body>
</html>
