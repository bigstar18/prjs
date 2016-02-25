<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="listDelayTrade" var="trade" 
			  action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listTrade"	
			xlsFileName="delayTradeList.xls" 
			csvFileName="delayTradeList.csv"
			showPrint="true" 		
			listWidth="100%"		
			rowsDisplayed="20"
			minHeight="300"		  
	>
		<ec:row>	
		    <ec:column property="FirmID" title="交易商代码" width="90" style="text-align:center;"/>
            <ec:column property="CustomerID" title="二级代码" width="100" style="text-align:center;"/>
            <ec:column property="A_TradeNo" title="成交号"  width="75" style="text-align:center;"/>
			<ec:column property="A_OrderNo" title="委托单号"  width="80" style="text-align:center;"/>
			<ec:column property="TradeTime" title="成交时间" cell="date" format="datetime" width="150" style="text-align:center;"/>
			<ec:column property="commodityid" title="商品代码" width="90" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="买卖" width="60" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>									
			<ec:column property="DelayOrderType" title="延期委托类型" width="90" editTemplate="ecs_t_DelayOrderType" mappingItem="DELAYORDERTYPE" style="text-align:center;"/>
			<ec:column property="Quantity" title="成交数量" cell="number" format="quantity" calc="total" calcTitle= "合计" width="65" style="text-align:right;"/>								
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
	<textarea id="ecs_t_DelayOrderType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="DelayOrderType" >
			<ec:options items="DELAYORDERTYPE" />
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
  if(request.getAttribute("listDelayTrade") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.delayForm.query.disabled = false;
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
// -->
</script>
</html>
