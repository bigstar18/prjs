<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import=" gnnt.MEBS.common.security.AclCtrl"%>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
加盟商成交合计
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="listTrade" var="listTrade" 
			  action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listBrokerTrade"	
			xlsFileName="TradeList.xls" 
			csvFileName="TradeList.csv"
			showPrint="true" 		
			listWidth="100%"		
			rowsDisplayed="20"
			minHeight="300"	
			
	>
		<ec:row>	
		    <ec:column property="brokerid" title="加盟商代码" width="10%" style="text-align:center;"/>
		    <ec:column property="name" title="加盟商名称" width="10%" style="text-align:center;" ellipsis="true"/>
			<ec:column property="commodityid" title="商品代码" width="10%" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="买卖" width="10%" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>									
			<ec:column property="OrderType" title="委托类型" width="10%" editTemplate="ecs_t_status1" mappingItem="ORDERTYPE" style="text-align:center;"/>
			<ec:column property="Quantity" title="成交数量" cell="number" format="quantity" calcTitle= "合计" calc="total"  width="10%" style="text-align:right;"/>								
			<ec:column property="TradeType" title="成交类型" width="10%" editTemplate="ecs_t_status2" mappingItem="TRADETYPE" style="text-align:center;"/>
			<ec:column property="Close_PL" title="转让盈亏" width="10%" cell="currency" calc="total"  style="text-align:right;"/>		
			<ec:column property="TradeFee" title="手续费" width="10%" cell="currency" calc="total"  style="text-align:right;"/>	
			<ec:column property="CloseAddedTax" title="转让增值税" width="10%" cell="currency" calc="total"  style="text-align:right;"/>		
		</ec:row>
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDERTYPE" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADETYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("listTrade") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.statQueryForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
    
<%
  }
%>
// -->
</script>
</body>

<script type="text/javascript">
<!--
  var cll;
  for (var i=0;i<ec_table.rows.length;i++) 
  {
    /*cll = ec_table.rows(i).cells(8);
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
    }*/
    
    /*cll = ec_table.rows(i).cells(14);
    var TradeType = cll.innerHTML;
    if (TradeType == "1") 
    {
       cll.innerHTML = "<fmt:message key="trade.TradeType1"/>";
    }
    else if (TradeType == "2") 
    {
       cll.innerHTML = "<fmt:message key="trade.TradeType2"/>";
    }
    else if (TradeType == "3") 
    {
       cll.innerHTML = "<fmt:message key="trade.TradeType3"/>";
    }
    else if (TradeType == "4") 
    {
       cll.innerHTML = "<fmt:message key="trade.TradeType4"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }*/
    
    /*cll = ec_table.rows(i).cells(8);
    var OrderType = cll.innerHTML;
    if (OrderType == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.OrderType.option.kc"/>";
    }
    else if (OrderType == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.OrderType.option.pc"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }*/
  }
// -->
</script>
</html>
