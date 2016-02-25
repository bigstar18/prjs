<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="settleList" var="settle" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listSettle"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="settleProcessDate" title="交收日期" cell="date" width="100" style="text-align:center;"/> 
  			<ec:column property="firmID" title="交易商代码" width="80" style="text-align:center;"/> 
  			<ec:column property="commodityID" title="商品代码" width="80" style="text-align:center;"/> 
  			<ec:column property="bS_Flag" title="买卖标志" width="80" style="text-align:center;" editTemplate="ecs_t_bS_Flag" mappingItem="BS_FLAG2"/>	
  			
  			<ec:column property="settleQty" title="交收数量" width="80" format="quantity" calcTitle= "合计" calc="total" style="text-align:right;"/> 
  			<ec:column property="a_HoldNo" title="订货单号"  width="70" format="quantity" style="text-align:right;"/>
  			<ec:column property="Price" title="订立价" width="70" cell="currency" style="text-align:right;"/>
  			<ec:column property="SettlePrice" title="交收价" width="70" cell="currency" style="text-align:right;"/>
  			<ec:column property="settleMargin" title="交收保证金" calc="total" width="100" cell="currency" style="text-align:right;"/>
  			<ec:column property="payout" title="交收货款" calc="total" width="100" cell="currency" style="text-align:right;"/>
  			<ec:column property="settleFee" title="交收手续费" calc="total" width="80" cell="currency" style="text-align:right;"/>
  			<ec:column property="settle_PL" title="交收盈亏" width="70" cell="currency" calcTitle= "合计" calc="total" style="text-align:right;"/> 
  			<ec:column property="SettleAddedTax" title="增值税扣补" cell="currency" calc="total" width="80" style="text-align:right;"/>
  			<ec:column property="_1" title="交收类型"   width="80" style="text-align:right;">
  					<c:choose>
  						<c:when test="${settle.SettleType==0}">
  							自动交收
  						</c:when>
  						<c:when test="${settle.SettleType==1}">
  							手动交收
  						</c:when>
  						<c:when test="${settle.SettleType==2}">
  							提前交收
  						</c:when>
  						<c:otherwise>
  							延期交收
  						</c:otherwise>
  					</c:choose>
  			</ec:column>
  			
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>

	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="Qty" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
	<textarea id="ecs_t_bS_Flag" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="bS_Flag" >
			<ec:options items="BS_FLAG2" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("settleList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.statQueryForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>


  for (var i=0;i<ec_table.rows.length;i++)                
  {
    //alert(i);
    //ec_table.rows(i).cells(7).style.display = "none";	
  }
// -->
</script>

  </body>
</html>
