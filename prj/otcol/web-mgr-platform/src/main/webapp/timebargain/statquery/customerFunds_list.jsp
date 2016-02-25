<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
资金情况报表
</title>
<script type="text/javascript"> 
function dy_onclick1(FirmID,date)
{
  //alert(date);
  ec.action = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=editCustomerFund"/>&CustomerID="+FirmID+"&date="+date;
  ec.target = "_blank";
  ec.submit();   
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listCustomerFunds"/>";
  //p("<c:url value="/statquery/statQuery.do?method=editCustomerFund"/>&CustomerID="+CustomerID+"&date="+date,600,700,'no');
}

function customerFunds_table(firmID){
	pTop("<c:url value="/timebargain/statquery/statQuery.do?funcflg=editCustomerFundsTable&firmID="/>" + firmID + "&id=" + Date(),900,700);
}
</script>
</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="customerFundsList" var="customerFunds" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listCustomerFunds"	
			xlsFileName="CustomerFundsList.xls" 
			csvFileName="CustomerFundsList.csv"
			showPrint="true"
			doPreload="false" 	
			rowsDisplayed="20"
			listWidth="100%"
			minHeight="300"	
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"	  
	>
		<ec:row>	
			<ec:column property="FirmID" title="交易商代码" width="80" style="text-align:center;">
				<a href="#" onclick="customerFunds_table('<c:out value="${customerFunds.FirmID}"/>')"><c:out value="${customerFunds.FirmID}"/></a>
            </ec:column>
			<ec:column property="FirmName" title="交易商名称" width="80" style="text-align:center;"/>
			<ec:column property="firmCategoryId" title="交易商类别"   width="160" style="text-align:center;">
				<c:forEach items="${firmCategoryList }" var = "firmCategory">
					<c:if test="${firmCategory.id == customerFunds.firmCategoryId}">
						${firmCategory.name }
					</c:if>
				</c:forEach>
  			</ec:column>
			
			<ec:column property="nowLeaveBalance" title="当前可用资金" cell="currency" calcTitle= "合计" calc="total" width="120" style="text-align:right;"/>
			
			<ec:column property="RuntimeFL" title="当前浮亏" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="RuntimeMargin" title="当前保证金" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="RuntimeAssure" title="当前担保金" cell="currency" calc="total" width="120" style="text-align:right;"/>
					
			
			<ec:column property="LastBalance" title="上日资金余额" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="ClearFL" title="上日浮亏" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="ClearMargin" title="上日保证金" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="ClearAssure" title="上日担保金" cell="currency" calc="total" width="120" style="text-align:right;"/>
			
			<ec:column property="TradeFee" title="交易手续费" cell="currency" calc="total" width="120" style="text-align:right;"/>			
			<ec:column property="MaxOverdraft" title="质押资金" cell="currency" calc="total" width="120" style="text-align:right;"/>	
			<!--后台管理：订单交易：数据查询：资金情况页面：虚拟资金列无意义(隐藏字段显示)  -->
			<!--ec:column property="VirtualFunds" title="虚拟资金" cell="currency" calc="total" width="120" style="text-align:right;"/> -->	
			<ec:column property="close_PL" title="当日转让盈亏" cell="currency" calc="total" width="120" style="text-align:right;"/>
			
			<%-- <ec:column property="settle_PL" title="当日交收盈亏" cell="currency" calc="total" width="120" style="text-align:right;"/>--%>
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
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("customerFundsList") != null)
  {
%>	if (parent.TopFrame) {
		parent.TopFrame.statQueryForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
</body>
</html>
