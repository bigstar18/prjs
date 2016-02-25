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
	<ec:table items="orderList" var="order" 
			action="${pageContext.request.contextPath}/timebargain/statquery/conditionOrder.do?funcflg=listConditionOrder"	
			xlsFileName="ConditionOrderList.xls" 
			csvFileName="ConditionOrderList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"			  
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"		  
	>
		<ec:row>	
		    <ec:column property="A_OrderNo" title="条件单号" width="70" style="text-align:center;"/>
		    <ec:column property="FirmID" title="交易商代码" width="90" style="text-align:center;"/>
		    <ec:column property="firmName" title="交易商名称" width="100" style="text-align:center;"/>
            <ec:column property="CustomerID" title="二级代码" width="100" style="text-align:center;"/> 
			<ec:column property="commodityID" title="委托商品代码" width="95" style="text-align:center;"/>
			<ec:column property="TraderID" title="交易员代码" width="90" style="text-align:center;"/>
			
			<ec:column property="BS_Flag" title="买卖" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG" style="text-align:center;"/>
			<ec:column property="OrderType" title="订立/转让" width="80" editTemplate="ecs_t_status1" mappingItem="ORDERTYPE" style="text-align:center;"/>
			<ec:column property="sendStatu" title="委托状态" width="100" style="text-align:center;"/>
			<ec:column property="Quantity" title="委托数量" cell="number" format="quantity" calc="total" calcTitle= "合计"  width="65" style="text-align:right;"/>
			<ec:column property="Price" title="委托价格" cell="number"  calc="total" calcTitle= "合计" width="120" style="text-align:right;"/>
			<ec:column property="ConditionCommodityID" title="条件商品代码" width="95" style="text-align:center;"/>
			<ec:column property="ctype" title="条件类型" width="95" style="text-align:center;"/>
			<ec:column property="ConditionOperation" title="条件操作符" editTemplate="ecs_t_status2" mappingItem="CONDITIONOPERATION" width="95" style="text-align:center;"/>
			<ec:column property="ConditionPrice" title="条件价格" cell="number"  calc="total" calcTitle= "合计" width="95" style="text-align:right;"/>
			
			<ec:column property="UpdateTime" title="设定时间" cell="date" format="datetime" width="150" style="text-align:center;"/>		
			<ec:column property="ValidDate" title="到期日期" cell="date" format="date" width="150" style="text-align:center;"/>	
			<ec:column property="SuccessTime" title="委托时间" cell="date" format="datetime" width="150" style="text-align:center;"/>

		</ec:row>
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG" />
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
			<ec:options items="CONDITIONOPERATION" />
		</select>
	</textarea>	
	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("orderList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.conditionOrderForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
  </body>
</html>
