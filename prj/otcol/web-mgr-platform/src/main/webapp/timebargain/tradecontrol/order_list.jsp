<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--

// -->
</script>
</head>
<body>
<table width="600">
  <tr><td>
	<ec:table items="orderList" var="order" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/tradeCtl.do?funcflg=searchOrder"	
			height="390px" 
			xlsFileName="OrderList.xls" 
			csvFileName="OrderList.csv"
			showPrint="true" 
	>
		<ec:row>	
            <ec:column property="A_OrderNo" title="ordersForm.A_OrderNo" width="130" style="text-align:right;"/>
            <ec:column property="M_OrderNo" title="ordersForm.M_OrderNo" width="130" style="text-align:right;"/>
            <ec:column property="customerID" title="ordersForm.CustomerID" width="90" style="text-align:right;"/>			
			<ec:column property="Uni_Cmdty_Code" title="ordersForm.Uni_Cmdty_Code" width="90" style="text-align:right;"/>
			<ec:column property="BS_Flag" title="ordersForm.BS_Flag" width="90" style="text-align:right;"/>
			<ec:column property="OrderType" title="ordersForm.OrderType" width="90" style="text-align:right;"/>
			<ec:column property="Status" title="ordersForm.Status" width="90" style="text-align:right;"/>
			<ec:column property="Quantity" title="ordersForm.Quantity" width="90" style="text-align:right;"/>
			<ec:column property="Price" title="ordersForm.Price" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="CloseMode" title="ordersForm.CloseMode" width="90" style="text-align:right;"/>
			<ec:column property="SpecPrice" title="ordersForm.SpecPrice" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="TimeFlag" title="ordersForm.TimeFlag" width="90" style="text-align:right;"/>
			<ec:column property="CloseFlag" title="ordersForm.CloseFlag" width="90" style="text-align:right;"/>
			<ec:column property="TradeQty" title="ordersForm.TradeQty" width="90" style="text-align:right;"/>
			<ec:column property="FrozenFunds" title="ordersForm.FrozenFunds" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="UnfrozenFunds" title="ordersForm.UnfrozenFunds" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="OrderTime" title="ordersForm.OrderTime" cell="date" format="time" width="90" style="text-align:right;"/>
			<ec:column property="WithdrawTime" title="ordersForm.WithdrawTime" cell="date" format="time" width="90" style="text-align:right;"/>			
			<ec:column property="A_OrderNo_W" title="ordersForm.A_OrderNo_W" width="90" style="text-align:right;"/>
			<ec:column property="TraderID" title="ordersForm.TraderID" width="90" style="text-align:right;"/>
			<ec:column property="m_CustomerID" title="cM_CustomerMapForm.m_CustomerID" width="90" style="text-align:right;"/>
			<ec:column property="marketName" title="marketForm.marketName" width="90" style="text-align:right;"/>
		</ec:row>
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

<script type="text/javascript">
<!--
  var cll;
  for (var i=0;i<ec_table.rows.length;i++) 
  {
    cll = ec_table.rows(i).cells(4);
    var bs_Flag = cll.innerHTML;
    if (bs_Flag == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.BS_Flag.option.b"/>";
    }
    else if (bs_Flag == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.BS_Flag.option.s"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
        
    cll = ec_table.rows(i).cells(5);
    var OrderType = cll.innerHTML;
    if (OrderType == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.OrderType.option.kc"/>";
    }
    else if (OrderType == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.OrderType.option.pc"/>";
    }
    else if (OrderType == "4") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.OrderType.option.cd"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
    
    cll = ec_table.rows(i).cells(6);
    var Status = cll.innerHTML;
    if (Status == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option1"/>";
    }
    else if (Status == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option2"/>";
    }
    else if (Status == "3") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option3"/>";
    }
    else if (Status == "4") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option4"/>";
    }
    else if (Status == "5") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option5"/>";
    }
    else if (Status == "6") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option6"/>";
    }
    else if (Status == "7") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option7"/>";
    }
    else if (Status == "8") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.Status.option8"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
    
    cll = ec_table.rows(i).cells(9);
    var CloseMode = cll.innerHTML;
    if (CloseMode == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.CloseMode.none"/>";
    }
    else if (CloseMode == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.CloseMode.time"/>";
    }
    else if (CloseMode == "3") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.CloseMode.price"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
    
    cll = ec_table.rows(i).cells(11);
    var TimeFlag = cll.innerHTML;
    if (TimeFlag == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.TimeFlag.today"/>";
    }
    else if (TimeFlag == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.TimeFlag.history"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
    
    cll = ec_table.rows(i).cells(12);
    var CloseFlag = cll.innerHTML;
    if (CloseFlag == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.CloseFlag2"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }    
  }
// -->
</script>
</html>
