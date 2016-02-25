<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<link href="<c:url value="/timebargain/widgets/extremecomponents/extremecomponents.css"/>" type="text/css" rel="stylesheet">
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
<fmt:message key="customerGroupForm.href.queryCustomer"/>
</title>
<script type="text/javascript">
<!--
function getCustomer(customerID)
{
  pTop("<c:url value="/timebargain/baseinfo/customerGroup.do?funcflg=getCustomer&customerID="/>" + customerID,600,600);
}

// -->
</script>
</head>
<body>
<div id="content">
	<ec:table items="customerList" var="customer" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/customerGroup.do?funcflg=searchCustomer"	
			  autoIncludeParameters="${empty param.autoInc}"
	>
		<ec:exportXls fileName="CustomerList.xls" tooltip="ec.export.tooltip"/>
		<ec:row>		
            <ec:column property="customerID" title="customerForm.CustomerID" style="text-align:right;">
            	<a href="#" onclick="javascript:getCustomer('<c:out value="${customer.customerID}"/>');"><c:out value="${customer.customerID}"/></a> 
            </ec:column>
			<ec:column property="groupName" title="customerGroupForm.groupName" style="text-align:right;"/>
			<ec:column property="customerName" title="customerForm.CustomerName" style="text-align:right;"/>
			<ec:column property="status" title="customerForm.Status" style="text-align:right;"/>
			<ec:column property="phone" title="customerForm.Phone" style="text-align:right;"/>	
			<ec:column property="address" title="customerForm.Address" style="text-align:right;"/>			
			<ec:column property="note" title="customerForm.Note" style="text-align:right;"/>
			<ec:column property="createTime" title="customerForm.CreateTime" cell="date" format="datetime" style="text-align:right;"/>
			<ec:column property="modifyTime" title="customerForm.ModifyTime" cell="date" format="datetime" style="text-align:right;"/>							
		</ec:row>
	</ec:table>
</div>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

<script type="text/javascript">
<!--
  var cll;
  for (var i=3;i<ec_table.rows.length;i++) 
  {
    cll = ec_table.rows(i).cells(3);
    var status = cll.innerHTML;
    if (status == "0") 
    {
       cll.innerHTML = "<fmt:message key="customerForm.Status.option.zc"/>";
    }
    else if (status == "1") 
    {
       cll.innerHTML = "<fmt:message key="customerForm.Status.option.jzjy"/>";
    }
    else if (status == "2") 
    {
       cll.innerHTML = "<fmt:message key="customerForm.Status.option.ts"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
  }
// -->
</script>
</html>
