<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<script type="text/javascript">
function getStoreDetails(storeNumber) {
  var url = "<html:rewrite page='/secure/getStoreDetails.do'/>" + "?storeNumber=" + storeNumber;
  imgWindow = window.open(url,'_blank','scrollbars=yes,resizable=yes,height=225px,width=425px');
  imgWindow.opener = window;
  imgWindow.focus();
}
function reloadDetails(){
	window.location="<html:rewrite page='/jsp/products/productDetails.jsp'/>";
	return false;
}
</script>
