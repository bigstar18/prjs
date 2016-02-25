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
	<ec:table items="holdList" var="hold" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/tradeCtl.do?funcflg=listHoldPosition"	
			xlsFileName="HoldList.xls" 
			csvFileName="HoldList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"
			retrieveRowsCallback="limit"
	>
		<ec:row>	
		<ec:column property="firmID" title="交易商代码" width="80" style="text-align:center;"/>	
		 <ec:column property="firmName" title="交易商名称" width="80" style="text-align:center;"/>
		 <ec:column property="firmCategoryId" title="交易商类别"   width="160" style="text-align:center;">
				<c:forEach items="${firmCategoryList }" var = "firmCategory">
					<c:if test="${firmCategory.id == hold.firmCategoryId}">
						${firmCategory.name }
					</c:if>
				</c:forEach>
  			</ec:column>
		    <ec:column property="customerID" title="二级代码" width="90" style="text-align:center;"/>		
			
			<ec:column property="commodityid" title="商品代码" width="70" style="text-align:center;"/>
			<ec:column property="bS_Flag" title="买卖" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
			<ec:column property="price" title="价格" cell="currency" width="100" style="text-align:right;"/>			
			<ec:column property="dh" title="订货数量" width="100" style="text-align:right;"/>
			<ec:column property="gageQty" title="抵顶数量" width="100" style="text-align:right;"/>
			<ec:column property="openQty" title="订立数量" width="100" style="text-align:right;"/>
			<ec:column property="holdTime" title="订立日期" cell="date" format="date" width="90" style="text-align:center;"/>
			<ec:column property="deadLine" title="到期日期" cell="date" format="date" width="90" style="text-align:center;"/>
			<ec:column property="remainDay" title="到期天数" width="100" style="text-align:right;"/>
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
<%
  if(request.getAttribute("holdList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.tradeCtlForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
<!--
  var cll;
  for (var i=0;i<ec_table.rows.length;i++) 
  {
    /*cll = ec_table.rows(i).cells(4);
    var bs_Flag = cll.innerHTML;
    if (bs_Flag == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.BS_Flag.option.b1"/>";
    }
    else if (bs_Flag == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.BS_Flag.option.s1"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
    cl2 = ec_table.rows(i).cells(3);
    var uni_Cmdty_Code=cl2.innerHTML;
    cl2.innerHTML =uni_Cmdty_Code.substring(2,uni_Cmdty_Code.length);*/
  }
// -->
</script>
</body>


</html>
