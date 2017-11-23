<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE pdf PUBLIC "-//big.faceless.org//report" "report-1.1.dtd">
<pdf>
    <head>
        <link type="stylesheet" src="../styles/pdf.css"/>
    </head>    
    <body>
        <!-- HEADER //-->
        <table style="width:520px;align:center;">
            <tbody>
                <tr><td colspan="5"><h1><fmt:message key="form.repairandmaintain.title"/></h1></td></tr>
                <tr><td colspan="5">&#160;</td></tr>
                <tr>
                    <td class="label" style="width:125px"><fmt:message key="form.label.date"/>:</td>
                    <td style="width:200px" class="userinput"><c:out value="${repairAndMaintenanceBean.today}"/></td>
                    <td style="width:20px">&#160;</td>
                    <td class="label" style="width:75px"><fmt:message key="form.label.manager"/>:</td>
                    <td style="width:100px" class="userinput"><c:out value="${repairAndMaintenanceBean.manager}"/></td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.store"/>:</td>
                    <td colspan="4" class="userinput"><c:out value="${repairAndMaintenanceBean.store.number}"/> (<c:out value="${repairAndMaintenanceBean.store.name}"/>)</td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.city"/>:</td>
                    <td class="userinput"><c:out value="${repairAndMaintenanceBean.store.city}"/></td>
                    <td colspan="3">&#160;</td>
                </tr><tr>
                    <td class="label"><fmt:message key="form.label.telephone"/>:</td>
                    <td class="userinput"><c:out value="${repairAndMaintenanceBean.store.phoneNumber}"/></td>
                    <td>&#160;</td>
                    <td class="label"><fmt:message key="form.label.fax"/>:</td>
                    <td class="userinput"><c:out value="${repairAndMaintenanceBean.fax}"/></td>
                </tr>
            </tbody>
        </table>
        <!-- BODY //-->
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.repairandmaintain.issue.maintenance"/></th></tr>
            </tbody>
        </table>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <td><img src="../images/checked.gif" class="checkbox"/></td>
                    <td><fmt:message key="form.repairandmaintain.maintenance.desc"/></td>
                </tr><tr>
                    <td>&#160;</td>
                    <td>
                        <fmt:message key="form.repairandmaintain.emailrequest"/>:<br/><br/>
                        <fmt:message key="form.repairandmaintain.maintenance.person"/><br/>
                        <fmt:message key="form.repairandmaintain.maintenance.person.title"/><br/>
                        <fmt:message key="form.label.telephone"/>:&#160;<fmt:message key="form.repairandmaintain.maintenance.person.phone"/><br/>
                        <fmt:message key="form.label.email"/>:&#160;<fmt:message key="form.repairandmaintain.maintenance.person.email"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.repairandmaintain.issue.lossprevention"/></th></tr>
            </tbody>
        </table>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <td colspan="2"><fmt:message key="form.repairandmaintain.lossprevention.desc"/></td>
                </tr><tr>
                    <td colspan="2"><fmt:message key="form.repairandmaintain.lossprevention.desc2"/></td>
                </tr>
            </tbody>
        </table>
        <p/>
        <table>
            <tbody>
                <tr><th><fmt:message key="form.repairandmaintain.workDetail"/></th></tr>
            </tbody>
        </table>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <td>
                        <span class="userinput"><c:out value="${repairAndMaintenanceBean.workDetail}"/></span>
                    </td>
                </tr>
            </tbody>
        </table>
        <p/>
        <table style="width:95%;align:center;">
            <tbody>
                <tr>
                    <td>
                    <fmt:message var="person" key="form.repairandmaintain.maintenance.person"/>
                    <fmt:message key="form.repairandmaintain.questions">
                        <fmt:param value="${person}"/>
                    </fmt:message>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</pdf>
