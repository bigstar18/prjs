<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
委托情况
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="orderList" var="order" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listOrder"	
			xlsFileName="OrderList.xls" 
			csvFileName="OrderList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"			  
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"		  
	>
		<ec:row>	
		    <ec:column property="FirmID" title="交易商代码" width="90" style="text-align:center;"/>
		    <ec:column property="FirmName" title="交易商名称" width="100" style="text-align:center;"/>
            <ec:column property="CustomerID" title="二级代码" width="100" style="text-align:center;"/>
            <ec:column property="A_OrderNo" title="委托单号" width="70" style="text-align:center;"/>
       
			<ec:column property="commodityid" title="商品代码" width="95" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="买卖" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
			<ec:column property="OrderType" title="委托类型" width="80" editTemplate="ecs_t_status1" mappingItem="ORDERTYPE" style="text-align:center;"/>
			<ec:column property="Status" title="状态" width="100" editTemplate="ecs_t_status2" mappingItem="ORDER_STATUS" style="text-align:center;"/>
			<ec:column property="Quantity" title="委托数量" cell="number" format="quantity" calc="total" calcTitle= "当前页面合计"  width="100" style="text-align:right;"/>
			<ec:column property="Price" title="委托价格" cell="currency"  width="120" style="text-align:right;"/>
			<ec:column property="TradeQty" title="已成交数量" cell="number" format="quantity" calc="total" width="90" style="text-align:right;"/>
			<ec:column property="OrderTime" title="委托时间" cell="date" format="datetime" width="150" style="text-align:center;"/>
			<ec:column property="WithdrawTime" title="撤单时间" cell="date" format="datetime" width="150" style="text-align:center;"/>			
			<ec:column property="TraderID" title="交易员代码" width="90" style="text-align:center;"/>
		
			<ec:column property="CloseMode" title="转让方式" width="65" editTemplate="ecs_t_status3" mappingItem="CLOSEMODE" style="text-align:right;"/>
	        <ec:column property="BillTradeType" title="仓单交易类型" width="100"  mappingItem="BILLTRADETYPE" style="text-align:right;"/>
			<ec:column property="SpecPrice" title="指定价格" cell="currency" width="65" style="text-align:right;"/>
			<ec:column property="TimeFlag" title="指定时间标志" width="90" editTemplate="ecs_t_status4" mappingItem="TIMEFLAG" style="text-align:right;"/>
			<ec:column property="CloseFlag" title="转让标志" width="90" editTemplate="ecs_t_status5" mappingItem="CLOSEFlAG2" style="text-align:right;"/>
			<ec:column property="ConsignerID" title="代为委托员" width="90" style="text-align:right;"/>
			<ec:column property="WithdrawerID" title="撤单员代码" width="90" style="text-align:right;"/>
			<ec:column property="FirmCategoryId" title="交易商类别"   width="160" style="text-align:right;">
				<c:forEach items="${resultList }" var = "firmCategory">
					<c:if test="${firmCategory.id == order.FirmCategoryId}">
						${firmCategory.name }
					</c:if>
				</c:forEach>
  			</ec:column>
		</ec:row>
		<ec:extendrow>
			<td style="text-align: center;font-weight: bold;">总计</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;"><fmt:formatNumber value="${sumQuantity}" pattern="#,##0"/></td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;"><fmt:formatNumber value="${sumTradeQty}" pattern="#,##0"/></td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
			<td style="text-align: center;">&nbsp;</td>
		</ec:extendrow>
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
			<ec:options items="ORDER_STATUS" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status3" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CLOSEMODE" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status4" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TIMEFLAG" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status5" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CLOSEFlAG2" />
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
    /*cll = ec_table.rows(i).cells(5);
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
        
    /*cll = ec_table.rows(i).cells(6);
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
    }*/
    
    /*cll = ec_table.rows(i).cells(7);
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
    }*/
    
    /*cll = ec_table.rows(i).cells(10);
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
    }*/
    
    /*cll = ec_table.rows(i).cells(12);
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
    }*/
    
    /*cll = ec_table.rows(i).cells(13);
    var CloseFlag = cll.innerHTML;
    if (CloseFlag == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.CloseFlag2"/>";
    }
    else
    {
    	cll.innerHTML = "";
    } */   
  }
// -->
</script>
</html>
