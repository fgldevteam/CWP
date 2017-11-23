<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:html>
<head><title><fmt:message key="product.details.title"/></title>
    <script type="text/javascript">
    function reloadSearch(){
      window.location="<html:rewrite page='/secure/searchProducts.do'/>";
      return false;
    }
    function storeSkuInventory(){
        document.forms[0].action="<html:rewrite page='/secure/storeSkuInventory.do'/>";
        setAction("");
        document.forms[0].submit();
        return false;
    }
    function storeStyleInventory(){
        document.forms[0].action="<html:rewrite page='/secure/storeStyleInventory.do'/>";
        setAction("");
        document.forms[0].submit();
        return false;
    }
    function productSkuNotes(){
		setAction("notes");
    	document.forms[0].submit();
    	return false;
    }
    function productSkuUPC(){
    	setAction("upc");
    	document.forms[0].submit();
    	return false;
    }
    function setAction(newAction){
    	var buttonAction = document.getElementById("buttonAction");
    	if (buttonAction != null){
	    	buttonAction.value=newAction;
	    }
    }
    function selectColumn(state,c){
      var cols=<c:out value="${fn:length(productDetailsBean.product.sizes)}"/>;
      var rows=<c:out value="${fn:length(sessionScope.productDetailsBean.product.colours)}"/>;
      for(var i=0;i<rows;++i){
        var cb=document.getElementById("skuColourSizes["+c+"]");
        if(cb!=null){
          cb.checked=state;
        }
        c+=cols;
      }
    }
    function selectRow(state,r){
      var cols=<c:out value="${fn:length(productDetailsBean.product.sizes)}"/>;
      var rows=<c:out value="${fn:length(sessionScope.productDetailsBean.product.colours)}"/>;
      r=r*cols;
      for(var i=0;i<cols;++i){
        var cb=document.getElementById("skuColourSizes["+r+"]");
        if(cb!=null){
          cb.checked=state;
        }
        r+=1;
      }
    }
    function selectAll(state){
      var cols=<c:out value="${fn:length(productDetailsBean.product.sizes)}"/>;
      var rows=<c:out value="${fn:length(sessionScope.productDetailsBean.product.colours)}"/>;
      
      var i=0;
      //check/uncheck all column checkboxes
      for(i=0;i<cols;++i){
        colCb=document.getElementById("sizeColumn["+i+"]");
        if(colCb!=null){
          colCb.checked=state;
        }
      }
      //check/uncheck all row checkboxes
      for(i=0;i<rows;++i){
        rowCb=document.getElementById("colourRow["+i+"]");
        if(rowCb!=null){
          rowCb.checked=state;
        }
      }
      //check/uncheck all size/colour checkboxes
      for(i=0;i<rows*cols;++i){
        var cb=document.getElementById("skuColourSizes["+i+"]");
        if(cb!=null){
          cb.checked=state;
        }
      }
    }
    </script>
</head>
<body>
<html:form action="/secure/loadSkuDetails.do" method="POST">
	
	<html:hidden property="buttonAction" value="upc" styleId="buttonAction"/>
	
    <table class="data">
        <caption><fmt:message key="product.details.title"/></caption>
		<c:if test="${!empty productDetailsBean.storeList}">
	        <tr>
		        <td class="label"><fmt:message key="product.details.label.store"/>:</td>
				<td colspan="5">
			       	<c:choose>
	    				<c:when test="${fn:length(productDetailsBean.storeList)==1}">
	        				<c:out value="${productDetailsBean.store.fullName}"/>
	        			</c:when>
	    				<c:otherwise>
			        		<html:select property="storeNumber">
	        				    <html:optionsCollection property="storeList" label="fullName" value="number"/>
	        				</html:select>
	    				</c:otherwise>
					</c:choose>
		        </td>
	        </tr>
        </c:if>
        
        <tr>
            <td class="label"><fmt:message key="product.details.label.stylenumber"/>:</td>
            <td><c:out value="${productDetailsBean.product.number}"/></td>
            <td colspan="2">&nbsp;</td>
            <td class="label"><fmt:message key="product.details.label.vpn"/>:</td>
            <td><c:out value="${productDetailsBean.product.vpn}"/></td>
        </tr><tr>
        	<td class="label"><fmt:message key="product.details.label.r12number"/>:</td>
        	<td><c:out value="${productDetailsBean.product.r12Number}"/></td>
        	<td colspan="2">&nbsp;</td>
            <td class="label"><fmt:message key="product.details.label.description"/>:</td>
            <td><c:out value="${productDetailsBean.product.description}"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="product.details.label.department"/>:</td>
            <td><c:out value="${productDetailsBean.product.hierarchy.deptDescription}"/></td>
            <td colspan="2">&nbsp;</td>
            <td class="label"><fmt:message key="product.details.label.subdepartment"/>:</td>
            <td><c:out value="${productDetailsBean.product.hierarchy.subDeptDescription}"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="product.details.label.class"/>:</td>
            <td><c:out value="${productDetailsBean.product.hierarchy.classDescription}"/></td>
            <td colspan="2">&nbsp;</td>
            <td class="label"><fmt:message key="product.details.label.subclass"/>:</td>
            <td><c:out value="${productDetailsBean.product.hierarchy.subClassDescription}"/></td>
        </tr><tr>
            <td class="label"><fmt:message key="product.details.label.brand"/>:</td>
            <td ><c:out value="${productDetailsBean.product.brand.name}"/></td>
            <td colspan="2">&nbsp;</td>
            <td class="label"><fmt:message key="product.details.label.code"/>:</td>
            <td><c:out value="${productDetailsBean.product.conceptShop.code}"/></td>
        </tr>
		
		
		<tr>
            <td><input type="button" value="<fmt:message key="product.button.back"/>" onclick="return(reloadSearch());"/></td>
            <td colspan="5" style="text-align: right;">
                <input type="button" value="<fmt:message key='product.details.label.style.inventory'/>" onclick="return(storeStyleInventory());"/>
            </td>
        </tr>
    </table>
    <br/>
    
    <!-- Display colour/size grid //-->
    <c:set var="index" value="0"/>
    <c:forEach items="${productDetailsBean.product.colours}" var="colour" varStatus="i">
    
        <!-- Display size headers //-->
        <c:if test="${i.first}">
            <table class="data" style="border-style:solid;border-width:1px;">
                <tr>
                    <th colspan="2" rowspan="2">
                        <input type="checkbox" onclick="selectAll(this.checked);"/><br/>
                        <fmt:message key="product.details.label.colourSize"/>
                    </th>
                    <c:forEach items="${productDetailsBean.product.sizes}" var="size" varStatus="j">
                        <th><input id="sizeColumn[${j.index}]" type="checkbox" onclick="selectColumn(this.checked,<c:out value='${j.index}'/>);"/></th>
                    </c:forEach>
                </tr><tr>
                    <c:forEach items="${productDetailsBean.product.sizes}" var="size" varStatus="j">
                        <th><c:out value="${size.codiDescription}"/></th>
                    </c:forEach>
                </tr>
        </c:if>
        <tr>
            <th><input id="colourRow[${i.index}]" type="checkbox" onclick="selectRow(this.checked,<c:out value='${i.index}'/>);"/></th>
            <th><c:out value="${colour.codiDescription}"/></th>
            <c:forEach items="${productDetailsBean.product.sizes}" var="size" varStatus="j">
                <c:choose>
                <c:when test="${j.last}">
                    <td class="colorSizeLastEl">
                </c:when>
                <c:otherwise>
                    <td class="colorSize">
                </c:otherwise>
                </c:choose>
                <html:checkbox styleId="skuColourSizes[${index}]" name="productDetailsBean" property="skuColourSizes[${index}]" value="${colour.codiCode}:${size.codiCode}"/></td>
                <c:set var="index" value="${index+1}"/>
            </c:forEach>
        </tr>
        <c:if test="${i.last}">
            <tr>
            <c:set var="colLength" value="${fn:length(productDetailsBean.product.sizes)+2}"/>
            <td colspan="<c:out value='${colLength}'/>" style="text-align:right;">
                <input type="button" value="<fmt:message key='product.details.label.sku.upc'/>" onclick="return(productSkuUPC());"/>
                <input type="button" value="<fmt:message key='product.details.label.sku.notes'/>" onclick="return(productSkuNotes());"/>
                <input type="button" value="<fmt:message key='product.details.label.sku.inventory'/>" onclick="return(storeSkuInventory());"/>
            </td>
            </tr>
            </table>
        </c:if>
    </c:forEach>
    <br/>
	<!--Display upcs //-->
    <c:if test="${productDetailsBean.buttonAction=='upc' && not empty productDetailsBean.product.skus}">
        <table class="data">
            <caption><fmt:message key="product.details.label.sku.details"/></caption>
            <tr>
                <th><fmt:message key="product.details.label.sku.description"/></th>
                <th><fmt:message key="product.details.label.sku.colour"/></th>
                <th><fmt:message key="product.details.label.sku.size"/></th>
                <th><fmt:message key="product.details.label.sku.upc"/></th>
                <th><fmt:message key="product.details.label.sku.upctype"/></th>
                <th><fmt:message key="product.search.htmltable.column.returnPrice"/></th>
                <th><fmt:message key="admin.pricingissues.label.currentprice"/></th>
                <th><fmt:message key="product.search.htmltable.column.cost"/></th>
            </tr>
            <c:set var="count" value="0"/>
            <c:forEach items="${productDetailsBean.product.skus}" var="sku" varStatus="i">
                <c:set var="newSku" value="true"/>
                <c:forEach items="${sku.upcs}" var="upc" varStatus="j">
                    <c:choose>
                        <c:when test="${count%2==0}">
                            <tr class="evenRow">
                        </c:when><c:otherwise>
                            <tr class="oddRow">
                        </c:otherwise>
                    </c:choose>                    
                    <c:choose>
                        <c:when test="${newSku}">
                            <td><c:out value="${sku.description}"/></td>
                            <td><c:out value="${sku.colour.codiDescription}"/></td>
                            <td><c:out value="${sku.size.codiDescription}"/></td>
                            <c:set var="newSku" value="false"/>
                        </c:when><c:otherwise>
                            <td colspan="3">&nbsp;</td>
                        </c:otherwise>
                    </c:choose>
                    <td><c:out value="${upc.upc}"/></td>
                    <td>
                        <c:choose>
                        <c:when test="${upc.type}">
                            (<fmt:message key="product.details.label.sku.upctype.internal"/>)
                        </c:when><c:otherwise>
                            (<fmt:message key="product.details.label.sku.upctype.external"/>)
                        </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align:center;vertical-align:middle"><c:out value="${productDetailsBean.product.receiptlessPrice}"/></td>
                    <td style="text-align:center;vertical-align:middle"><c:out value="${productDetailsBean.product.price}"/></td>
                    <td style="text-align:center;vertical-align:middle"><c:out value="${productDetailsBean.product.costPrice}"/></td>
                    </tr>
                    <c:set var="count" value="${count+1}"/>
                </c:forEach>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${productDetailsBean.buttonAction=='notes' && not empty productDetailsBean.product.skus}">
    	<display:table name="${productDetailsBean.product.skus}" uid="sku" class="data">
    		<display:caption><fmt:message key="product.details.label.sku.details"/></display:caption>
    		<display:column property="description"
	                titleKey="product.details.label.sku.description"
	                sortable="false"/>
	        <display:column titleKey="product.details.label.sku.notes">
	        	<c:choose>
	        		<c:when test="${empty sku.notes}">
		        		<fmt:message key="product.details.label.sku.notes.none"/>
	        		</c:when>
	        		<c:otherwise>
		        		<c:out value="${sku.notes}"/>
	        		</c:otherwise>
        		</c:choose>
	        </display:column>
        </display:table>
    </c:if>
</html:form>
</body>
</html:html>
