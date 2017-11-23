<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:html>
<head><title><fmt:message key="product.search.title.search"/></title>
    <script language="javascript">
        function submitSelection(level){
            document.forms[0].level.value = level;
            document.forms[0].action= "<html:rewrite page="/secure/loadHierarchySearch.do"/>";
            document.forms[0].submit();
        }
        
    function resetAll(){
        window.location ="<html:rewrite page='/secure/resetAll.do'/>";
    }
    </script>
</head>
<body>

<html:form action="/secure/searchProducts.do" method="post">
    <html:hidden property="level" value="" />
    <table>
        <tr>
            <td>
                <table class="data">
                    <caption><fmt:message key="product.search.label.generalsearch"/></caption>
                    <c:if test="${not empty sessionScope.productSearchBean.caps}">
                    	<tr>
                        <td class="label">
                            <fmt:message key="form.storeList.label.banner"/>:
                        </td>
                        <td>
                            <html:select property="capSelection">
                            	<html:optionsCollection property="caps" label="capName" value="capId"/>
                            </html:select>
                        </td>
                    </tr>
                    </c:if>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.vpn"/>:
                        </td>
                        <td>
                            <html:text property="vpn"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.description"/>:
                        </td>
                        <td>
                            <html:text property="styleDescription"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.stylenumber"/>:
                        </td>
                        <td>
                            <html:text property="styleNumber"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.brand"/>:
                        </td>
                        <td>
	                        <html:text property="brand"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.department"/>:
                        </td>
                        <td>
                            <html:select property="parentSelection[0]" onchange="submitSelection(1);">
                                <option value="0"></option>
                                <html:optionsCollection property="departmentOptions" label="description" value="id"/>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.subdepartment"/>:
                        </td>
                        <td>
                            <c:if test="${not empty productSearchBean.subDeptOptions}">
                                <html:select property="parentSelection[1]" onchange="submitSelection(2);">
                                    <option value="0"></option>
                                    <html:optionsCollection property="subDeptOptions" label="description" value="id"/>
                                </html:select>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.class"/>:
                        </td>
                        <td>
                            <c:if test="${not empty productSearchBean.classOptions}">
                                <html:select property="parentSelection[2]" onchange="submitSelection(3);">
                                    <option value="0"></option>
                                    <html:optionsCollection property="classOptions" label="description" value="id"/>
                                </html:select>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.subclass"/>:
                        </td>
                        <td>
                            <c:if test="${not empty productSearchBean.subClassOptions}">
                                <html:select property="parentSelection[3]" onchange="javascript:submit();">
                                    <option value="0"></option>
                                    <html:optionsCollection property="subClassOptions" label="description" value="id"/>
                                </html:select>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table class="data" style="width:100%;">
                    <caption><fmt:message key="product.search.label.upcsearch"/></caption>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.upc"/>:
                        </td>
                        <td>
                            <html:text property="upc" maxlength="18"/>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2"><fmt:message key="product.search.label.wildcardsearch"/></th>
                    </tr>
                    <tr>
                        <td class="label">
                            <fmt:message key="product.search.label.wildcard"/>:
                        </td>
                        <td>
                            <html:text property="wildCard"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="button" onclick="return(resetAll());" value="<fmt:message key='product.search.button.reset'/>"/>

                            </input>
                            <html:submit>
                                <fmt:message key="product.search.button.search"/>
                            </html:submit>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

    <c:if test="${productSearchBean.action=='search'}">
        <display:table name="${productSearchBean.styleResults}"
                id="style"
                class="data"
                requestURIcontext="true"
                requestURI="/secure/searchProducts.do"
                style="width:100%">
            <display:column
                    property="number"
                    sortable="true"
                    titleKey="product.search.htmltable.column.id"
                    href="${pageContext.request.contextPath}/secure/loadProductDetails.do"
                    paramId="productID"
                    paramProperty="id"/>
            <display:column
                    property="vpn"
                    sortable="true"
                    titleKey="product.search.htmltable.column.vpn"/>
            <display:column
                    property="description"
                    sortable="true"
                    titleKey="product.search.htmltable.column.description"
                    style="width:200px"/>
            <display:column
                    property="hierarchy.deptDescription"
                    titleKey="product.search.htmltable.column.dept"/>
            <display:column
                    property="hierarchy.subDeptDescription"
                    titleKey="product.search.htmltable.column.subdept"/>
            <display:column
                    property="hierarchy.classDescription"
                    titleKey="product.search.htmltable.column.class"/>
            <display:column
                    property="hierarchy.subClassDescription"
                    titleKey="product.search.htmltable.column.subclass"/>
            <display:column
                    property="receiptlessPrice"
                    titleKey="product.search.htmltable.column.returnPrice"
                    style="text-align:center;vertical-align:middle"/>
            <c:if test="${not empty loginStore}">
            	<display:column
                    property="price"
                    titleKey="admin.pricingissues.label.currentprice"
										style="text-align:center;vertical-align:middle"/>
            </c:if>
            <display:column
                    property="costPrice"
                    titleKey="product.search.htmltable.column.cost"
                    style="text-align:center;vertical-align:middle"/>
                    
            <display:column
                    property="conceptShop.code"
                    titleKey="product.search.htmltable.column.conceptShop"
                    style="text-align:center;vertical-align:middle"/>
        </display:table>
    </c:if>
</html:form>
</body>
</html:html>

