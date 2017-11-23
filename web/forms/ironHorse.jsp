<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">
<pdf>
    <head>
        <link type="stylesheet" src="../styles/pdf.css"/>
        <style type="text/css">
            table.data{
            border:1 solid black;
            }
            table.data th{
            background-color: #cccccc;
            border:none;
            vertical-align:middle;
            }
            
            
            table.data td.model, table.data td.cbox{
            border:1 solid silver;
            }
            table.data td.cbox, table.data th.cbox{
            align:center;
            }
        </style>
    </head>
    <body>
        <!-- HEADER //-->
        <table style="width:520px;align:center;">
            <tbody>
                <tr><td colspan="5"><h1><fmt:message key="form.ironhorse.title"/></h1></td></tr>
                <tr><td colspan="5">&#160;</td></tr>
                <tr>
                    <td class="label" style="width:125px"><fmt:message key="form.label.date"/>:</td>
                    <td style="width:125px" class="userinput"><c:out value="${ironHorseBean.today}"/></td>
                    <td style="width:20px">&#160;</td>
                    <td class="label" style="width:125px"><fmt:message key="form.label.manager"/>:</td>
                    <td style="width:125px"><c:out value="${ironHorseBean.manager}"/></td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.store"/>:</td>
                    <td class="userinput" colspan="4"><c:out value="${ironHorseBean.store.number}"/> (<c:out value="${ironHorseBean.store.name}"/>)</td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.address"/>:</td>
                    <td class="userinput" colspan="4"><c:out value="${ironHorseBean.store.address}"/></td>
                </tr><tr>
                    <td class="label" style="width:125px"><fmt:message key="form.label.city"/>:</td>
                    <td style="width:125px" class="userinput"><c:out value="${ironHorseBean.store.city}"/></td>
                    <td style="width:20px">&#160;</td>
                    <td class="label" style="width:125px"><fmt:message key="form.label.province"/>:</td>
                    <td style="width:125px"><c:out value="${ironHorseBean.store.province}"/></td>
                </tr><tr>
                    <td class="label" style="width:125px"><fmt:message key="form.label.postalcode"/>:</td>
                    <td style="width:125px" class="userinput"><c:out value="${ironHorseBean.store.postalCode}"/></td>
                    <td style="width:20px">&#160;</td>
                    <td class="label" style="width:125px"><fmt:message key="form.label.telephone"/>:</td>
                    <td style="width:125px"><c:out value="${ironHorseBean.store.phoneNumber}"/></td>
                </tr>
            </tbody>
        </table>        
        <!-- CUSTOMER INFO //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.ironhorse.customerinfo"/></th></tr>
            </tbody>
        </table>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <td class="label" style="width:125px"><fmt:message key="form.label.name"/>:</td>
                    <td class="userinput" colspan="4"><c:out value="${ironHorseBean.name}"/></td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.address"/>:</td>
                    <td class="userinput" colspan="4"><c:out value="${ironHorseBean.address}"/></td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.city"/>:</td>
                    <td class="userinput"><c:out value="${ironHorseBean.city}"/></td>
                    <td>&#160;</td>
                    <td class="label"><fmt:message key="form.label.province"/>:</td>
                    <td class="userinput"><c:out value="${ironHorseBean.province}"/></td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.postalcode"/>:</td>
                    <td class="userinput"><c:out value="${ironHorseBean.postalCode}"/></td>
                    <td>&#160;</td>
                    <td class="label"><fmt:message key="form.label.telephone"/>:</td>
                    <td class="userinput"><c:out value="${ironHorseBean.phone}"/></td>
                </tr>
            </tbody>
        </table>

        <!-- BIKE INFO //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.ironhorse.bikerequest"/></th></tr>
            </tbody>
        </table>
        
        <p class="linebreak"/>
        <table class="data" style="width:95%;align:center;">
            <fmt:message var="sizes" key="form.ironhorse.bikesizes"/>
        	<c:set var="sizeCount" value="0"/>
	        <c:forTokens items="${sizes}" delims="," var="currSize" varStatus="i">
                <c:set var="sizeCount" value="${sizeCount+1}"/>
            </c:forTokens>
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
                    	<c:choose>
                    		<c:when test="${ironHorseBean.selectedSizes[(i.index*sizeCount)+j.index]}">
	                        	<td class="cbox"><img src="../images/checked.gif" class="checkbox"/></td>
	                       	</c:when><c:otherwise>                	
	                        	<td class="cbox"><img src="../images/unchecked.gif" class="checkbox"/></td>
	                        </c:otherwise>
	                    </c:choose>
                    </c:forTokens>
                </tr>
            </c:forTokens>
        </table>
        
        <p class="linebreak"/>
        <p/>
        <table class="smalltext" style="width:95%;margin-left:10">
            <tbody>
                <tr>
                    <td style="width:10px">1.</td>
                    <td style="width:490px"><fmt:message key="form.ironhorse.q1"/></td>
                    <c:choose>
                        <c:when test="${ironHorseBean.answer1}">
                            <td style="width:25px"><img src="../images/checked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.yes"/></td>
                            <td style="width:25px"><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.no"/></td>
                        </c:when><c:otherwise>
                            <td style="width:25px"><img src="../images/unchecked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.yes"/></td>
                            <td style="width:25px"><img src="../images/checked.gif" class="checkbox"/></td>
                            <td style="width:25px"><fmt:message key="form.label.no"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </tbody>
        </table>
        <p/>
        <table style="width:95%;align:center;" class="emtext">
            <tbody>
                <tr>
                    <td>
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
                    <td><fmt:message key="form.ironhorse.note.customercopy"/></td>
                </tr>
            </tbody>
        </table>

    </body>
</pdf>
