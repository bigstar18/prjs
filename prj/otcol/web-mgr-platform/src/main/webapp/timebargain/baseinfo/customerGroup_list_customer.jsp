<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
<fmt:message key="customerGroupForm.getCustomer.title"/>
</title>
    <%@ include file="/timebargain/common/meta.jsp" %>

    <link href="<c:url value="/timebargain/styles/common.css"/>" type="text/css" rel=stylesheet>    
    
<script type="text/javascript">
<!--



// -->
</script>    
</head>

<body>

    <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="80%">
        <caption><b><fmt:message key="customerGroupForm.getCustomer.title"/></b></caption>
        <tr>
            <td class="left"><fmt:message key="customerForm.CustomerID"/></td>
            <td class="right"><c:out value="${customer.customerID}" escapeXml="false"/>&nbsp;</td>
            <td class="left" ><fmt:message key="customerForm.CustomerName"/></td>
            <td class="right"><c:out value="${customer.customerName}" escapeXml="false"/>&nbsp;</td>
        </tr>
        <tr>
            <td class="left"><fmt:message key="customerForm.GroupID"/></td>
            <td class="right"><c:out value="${customer.groupID}" escapeXml="false"/>&nbsp;</td>
                        <td class="left" ><fmt:message key="customerForm.Status"/></td>
            <td class="right">
            	<c:choose>
            		<c:when test="${customer.status == 0}"><fmt:message key="customerForm.Status.option.zc"/></c:when>
            		<c:when test="${customer.status == 1}"><fmt:message key="customerForm.Status.option.jzjy"/></c:when>
            		<c:when test="${customer.status == 2}"><fmt:message key="customerForm.Status.option.ts"/></c:when>
            	</c:choose>
            &nbsp;</td>
        </tr>
        <tr>
            <td class="left" ><fmt:message key="customerForm.Phone"/></td>
            <td class="right"><c:out value="${customer.phone}" escapeXml="false"/>&nbsp;</td>
            <td class="left"><fmt:message key="customerForm.Address"/></td>
            <td class="right"><c:out value="${customer.address}" escapeXml="false"/>&nbsp;</td>
        </tr>
        <tr>
            <td class="left"><fmt:message key="customerForm.Note"/></td>
            <td class="right"><c:out value="${customer.note}" escapeXml="false"/>&nbsp;</td>
            <td class="left" ><fmt:message key="customerForm.CreateTime"/></td>
            <td class="right"><c:out value="${customer.createTime}" escapeXml="false"/>&nbsp;</td>
        </tr>
        <tr>
            <td class="left"><fmt:message key="customerForm.ModifyTime"/></td>
            <td class="right"><c:out value="${customer.modifyTime}" escapeXml="false"/>&nbsp;</td>
            <td class="left" >&nbsp;</td>
            <td class="right">&nbsp;</td>
        </tr>  
    </table>
</body>
</html>
<%@ include file="/timebargain/common/messages.jsp" %>