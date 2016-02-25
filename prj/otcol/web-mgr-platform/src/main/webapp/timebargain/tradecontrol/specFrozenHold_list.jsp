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
<table width="100%">
  <tr><td>
	<ec:table items="specFrozenHoldList" var="specFrozenHold" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/tradeCtl.do?funcflg=searchSpecFrozenHold"	
			xlsFileName="SpecFrozenHoldList.xls" 
			csvFileName="SpecFrozenHoldList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="10"	
			minHeight="300"
			retrieveRowsCallback="limit"   
	>
		<ec:row>	
			<ec:column property="consignerID" title="代为委托员代码" width="15%"  style="text-align:center;" />
			<ec:column property="a_HoldNo" title="订货单号" width="15%" style="text-align:center;"/>	
			<ec:column property="a_OrderNo" title="委托单号" width="15%" style="text-align:center;"/>
			<ec:column property="commodityid" title="商品代码" width="15%" style="text-align:center;"/>
			<ec:column property="price" title="委托价格" width="20%" style="text-align:center;"/>
			<ec:column property="FrozenQty" title="冻结数量" width="20%" style="text-align:center;"/>
			
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
			<ec:options items="consignerID" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="a_HoldNo" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status3" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="a_OrderNo" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status4" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="commodityid" />
		</select>
	</textarea>	
		<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status4" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="price" />
		</select>
	</textarea>	
		<textarea id="ecs_t_status4" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="FrozenQty" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
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
    
    /*cll = ec_table.rows(i).cells(4);
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
    
    /*cll = ec_table.rows(i).cells(6);
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
    } */ 
  }
// -->
</script>
</html>
