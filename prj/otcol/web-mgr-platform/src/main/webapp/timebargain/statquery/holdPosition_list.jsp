<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/scripts/global.js"/>"></script>
<title>
default
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="holdPositionList" var="holdPosition" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listHoldPosition"	
			xlsFileName="DailyMoneyList.xls" 
			csvFileName="DailyMoneyList.csv"
			showPrint="true"
			doPreload="false"
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"	
	>
		<ec:row>	
            <ec:column property="CustomerID" title="二级代码" width="80" style="text-align:center;"/>
            <ec:column property="firmID" title="交易商代码" width="90" style="text-align:center;"/>	
            <ec:column property="firmCategoryId" title="交易商类别"   width="160" style="text-align:center;">
				<c:forEach items="${firmCategoryList }" var = "firmCategory">
					<c:if test="${firmCategory.id == holdPosition.firmCategoryId}">
						${firmCategory.name }
					</c:if>
				</c:forEach>
  			</ec:column>
            <ec:column property="commodityid" title="商品代码" width="90" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="买卖" width="60" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
			<ec:column property="HoldQtyGageQty" title="订货数量" cell="number" format="quantity" calc="total" calcTitle= "合计" width="120" style="text-align:right;"/>			
			<ec:column property="GageQty" title="抵顶数量" cell="number" format="quantity" calc="total" calcTitle= "合计" width="120" style="text-align:right;"/>			
			
			<ec:column property="EvenPrice" title="订货均价"  width="120" style="text-align:right;">
			   <fmt:formatNumber value="${holdPosition.EvenPrice}" pattern="#,######0.000000"/>
			</ec:column>
			<ec:column property="HoldMargin" title="订货保证金" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="FrozenQty" title="冻结数量" cell="number" format="quantity" calc="total" width="120" style="text-align:right;"/>	
			<ec:column property="GageFrozenQty" title="抵顶冻结数量" cell="number" format="quantity" calc="total" width="120" style="text-align:right;"/>	
			
			<ec:column property="ClearDate" title="结算日期" cell="date" format="date" width="85" style="text-align:center;"/>	
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
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("holdPositionList") != null)
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
    /*cll = ec_table.rows(i).cells(3);
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
  }
// -->
</script>
</html>
