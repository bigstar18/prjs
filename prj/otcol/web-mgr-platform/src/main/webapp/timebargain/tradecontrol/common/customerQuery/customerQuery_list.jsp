<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<link href="<c:url value="/timebargain/widgets/extremecomponents/extremecomponents.css"/>" type="text/css" rel="stylesheet">
<title>
content
</title>
<script language="JavaScript">
function row_onclick(customerID,customerName)
{
  //alert("hello"+customerID);
  var targetFrame = window.dialogArguments;
  targetFrame.setCustomer(customerID,customerName);
  top.window.close();
}  
</script>
</head>
<body topMargin="0" leftMargin="0">
<div id="content">
	<ec:table items="customerList" var="customer" 
			  action="${pageContext.request.contextPath}/timebargain/common/customerQuery/customerQuery.do?funcflg=customerQuery"	
			  filterable="false"
	>
		<ec:exportXls fileName="CustomerList.xls" tooltip="ec.export.tooltip"/>
		<ec:row onclick="javascript:row_onclick(cells(0).innerHTML,cells(2).innerHTML);">		
            <ec:column property="firmID" title="交易商ID"/>
			<ec:column property="groupID" title="customerForm.GroupID"/>
			<ec:column property="firmName" title="交易商名称"/>
			<ec:column property="status" title="customerForm.Status"/>
			<ec:column property="balance" title="customerFunds.Balance" cell="currency"/>
			<ec:column property="frozenFunds" title="customerFunds.FrozenFunds" cell="currency"/>
			<ec:column property="virtualFunds" title="customerFunds.VirtualFunds" cell="currency"/>							
		</ec:row>
	</ec:table>
</div>
<c:import url="/timebargain/common/messages.jsp" charEncoding="GBK"/>
</body>
</html>
