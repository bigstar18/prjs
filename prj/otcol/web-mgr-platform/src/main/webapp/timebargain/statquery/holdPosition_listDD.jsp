<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<SCRIPT language="javascript">


function modifyStatus(customerID,firmID,uni_Cmdty_Code,bS_Flag,rowid){
	if (confirm("您确定要提交吗！")) {
	
		var statushtml = ec_table.rows(rowid).cells(6).innerHTML;
		var texthtml = ec_table.rows(rowid).cells(7).innerHTML;
		//alert(statushtml);
		//alert(texthtml);
	//alert(statushtml);
	//alert(texthtml);
		var status;
		if (statushtml == "抵顶") {
			status = 0;
		}else if (statushtml == "取消抵顶") {
			status = 1;
		}
	
	 	parent.HiddFrame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=modifyStatusAndQuantity&customerID="/>" + customerID + "&firmID=" + firmID + "&uni_Cmdty_Code=" + uni_Cmdty_Code + "&bS_Flag=" + bS_Flag + "&status=" + status + "&texthtml=" + texthtml;
	}
	
}
</SCRIPT>
</head>
<body leftmargin="2" topmargin="0" >
<table width="100%">
  <tr><td>
	<ec:table items="holdPositionList" var="holdPosition" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listHoldPositionDD"	
			xlsFileName="DailyMoneyList.xls" 
			csvFileName="DailyMoneyList.csv"
			showPrint="true"
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"
	>
		<ec:row>	
			<ec:column property="firmID" title="交易商代码" width="15%" style="text-align:center;"/>
			
            <ec:column property="CustomerID" title="二级代码" width="15%" style="text-align:center;"/>
            <ec:column property="commodityid" title="商品代码" width="15%" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="买卖" width="15%" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
			<ec:column property="HoldQty" title="订货数量" cell="number" format="quantity" calc="total" calcTitle= "合计" width="20%" style="text-align:right;"/>			
			<ec:column property="GageQty" title="抵顶数量" cell="number" format="quantity" calc="total"  width="20%" style="text-align:right;"/>			
			
					
		</ec:row>
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="quantity" />
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
		
	</textarea>	
	<textarea id="ecs_t_DIDINGTYPE" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="didingType" >
			<ec:options items="DIDING_TYPE" />
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
