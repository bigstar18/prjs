<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--

// -->
</script>
</head>
<body>
<div id="content">
     <table width="900">
    <tr><td>
    <ec:table items="tradePropsList" var="tradeProps" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeProps.do?funcflg=searchTradeProps"	
			xlsFileName="tradePropsList.xls" 
			csvFileName="tradePropsList.csv"
			filterable="true"
			showPrint="true" 
			rowsDisplayed="20"
			listWidth="100%"
	>
    
    <ec:row  >
            <ec:column property="firmID" title="交易商ID" width="70" style="text-align:center;"/>
            <ec:column property="firmName" title="交易商名称" width="90" style="text-align:center;"/>	
            <ec:column property="useMaxHoldQty" title="使用最大持仓量"   width="120" style="text-align:center;"/>
            <ec:column property="useMinClearDeposit" title="使用最低结算保证金"   width="150" style="text-align:right;"/>
            <ec:column property="useMaxOverdraft" title="使用最大透支额度"   width="120" style="text-align:right;"/>
            <ec:column property="customerMaxHoldQty" title="客户最大持仓量" width="120" style="text-align:center;">
            <c:choose> 
			<c:when test="${pageScope.tradeProps.customerMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.customerMaxHoldQty}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column> 
            <ec:column property="customerMinClearDeposit" title="客户最低结算保证金" width="150" style="text-align:right;">
             <c:choose> 
			<c:when test="${pageScope.tradeProps.customerMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.customerMinClearDeposit}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column> 
            <ec:column property="customerMaxOverdraft" title="客户最大透支额度" width="120" style="text-align:right;">	
             <c:choose> 
			<c:when test="${pageScope.tradeProps.customerMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.customerMaxOverdraft}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column> 	
			<ec:column property="groupMaxHoldQty" title="组最大持仓量" width="150" style="text-align:center;">
			<c:choose> 
			<c:when test="${(pageScope.tradeProps.customerMaxHoldQty<0||pageScope.tradeProps.customerMaxHoldQty==null)&&pageScope.tradeProps.groupMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.groupMaxHoldQty}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column> 							
	        <ec:column property="groupMinClearDeposit" title="组最低结算保证金" width="150" style="text-align:right;">	
	        <c:choose> 
			<c:when test="${(pageScope.tradeProps.customerMaxHoldQty<0||pageScope.tradeProps.customerMaxHoldQty==null)&&pageScope.tradeProps.groupMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.groupMinClearDeposit}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column>						
	        <ec:column property="groupMaxOverdraft" title="组最大透支额度" width="150" style="text-align:right;">
	        <c:choose> 
			<c:when test="${(pageScope.tradeProps.customerMaxHoldQty<0||pageScope.tradeProps.customerMaxHoldQty==null)&&pageScope.tradeProps.groupMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.groupMaxOverdraft}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column>
	        <ec:column property="defMaxHoldQty" title="缺省最大持仓量" width="150" style="text-align:center;">	
	        <c:choose> 
			<c:when test="${(pageScope.tradeProps.customerMaxHoldQty<0||pageScope.tradeProps.customerMaxHoldQty==null)&&(pageScope.tradeProps.groupMaxHoldQty<0||pageScope.tradeProps.groupMaxHoldQty==null)&&pageScope.tradeProps.defMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.defMaxHoldQty}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column>						
	        <ec:column property="defMinClearDeposit" title="缺省最低结算保证金" width="150" style="text-align:right;">
	         <c:choose> 
			<c:when test="${(pageScope.tradeProps.customerMaxHoldQty<0||pageScope.tradeProps.customerMaxHoldQty==null)&&(pageScope.tradeProps.groupMaxHoldQty<0||pageScope.tradeProps.groupMaxHoldQty==null)&&pageScope.tradeProps.defMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.defMinClearDeposit}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
			</ec:column>							
	        <ec:column property="defMaxOverdraft" title="缺省最大透支额度" width="150" style="text-align:right;">		
	      <c:choose> 
			<c:when test="${(pageScope.tradeProps.customerMaxHoldQty<0||pageScope.tradeProps.customerMaxHoldQty==null)&&(pageScope.tradeProps.groupMaxHoldQty<0||pageScope.tradeProps.groupMaxHoldQty==null)&&pageScope.tradeProps.defMaxHoldQty>=0}"> 
			<font color=red> 
			<c:out value="${pageScope.tradeProps.defMaxOverdraft}"/> 
			</font> 
			</c:when> 
			<c:otherwise> 
			</c:otherwise> 
			</c:choose> 
	    </ec:column>    					
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
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	
</div>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
<script type="text/javascript">
<!--
  
  /*var cll;
  for (var i=0;i<ec_table.rows.length;i++)                
  {
    //alert(i);
    ec_table.rows(i).cells(5).style.display = "none";		
    ec_table.rows(i).cells(6).style.display = "none";		
    ec_table.rows(i).cells(7).style.display = "none";		
    ec_table.rows(i).cells(8).style.display = "none";		
    ec_table.rows(i).cells(9).style.display = "none";		
    ec_table.rows(i).cells(10).style.display = "none";		
    ec_table.rows(i).cells(11).style.display = "none";		
    ec_table.rows(i).cells(12).style.display = "none";		
    ec_table.rows(i).cells(13).style.display = "none";		
  }
  
  function row_show()
{
   if(ec_table.rows(0).cells(5).style.display== "none")
   {
    alert(ec_table_head.rows(0).cells(5).value);
    ec_table_head.rows(0).cells(5).style.display = "";		
    ec_table_head.rows(0).cells(6).style.display = "";		
    ec_table_head.rows(0).cells(7).style.display = "";		
    ec_table_head.rows(0).cells(8).style.display = "";		
    ec_table_head.rows(0).cells(9).style.display = "";		
    ec_table_head.rows(0).cells(10).style.display = "";		
    ec_table_head.rows(0).cells(11).style.display = "";		
    ec_table_head.rows(0).cells(12).style.display = "";		
    ec_table_head.rows(0).cells(13).style.display = "";
    ec_table_head.rows(0).cells(5).width = "50";		
    ec_table_head.rows(0).cells(6).width = "50";		
    ec_table_head.rows(0).cells(7).width = "50";		
    ec_table_head.rows(0).cells(8).width = "50";		
    ec_table_head.rows(0).cells(9).width = "50";		
    ec_table_head.rows(0).cells(10).width = "50";		
    ec_table_head.rows(0).cells(11).width = "50";		
    ec_table_head.rows(0).cells(12).width = "50";		
    ec_table_head.rows(0).cells(13).width = "50";
      var cll;
  for (var i=0;i<ec_table.rows.length;i++)                
  {
    //alert(i);
    ec_table.rows(i).cells(5).style.display = "";		
    ec_table.rows(i).cells(6).style.display = "";		
    ec_table.rows(i).cells(7).style.display = "";		
    ec_table.rows(i).cells(8).style.display = "";		
    ec_table.rows(i).cells(9).style.display = "";		
    ec_table.rows(i).cells(10).style.display = "";		
    ec_table.rows(i).cells(11).style.display = "";		
    ec_table.rows(i).cells(12).style.display = "";		
    ec_table.rows(i).cells(13).style.display = "";		
  }
   }
   else
   {
     var cll;
  for (var i=0;i<ec_table.rows.length;i++)                
  {
    //alert(i);
    ec_table.rows(i).cells(5).style.display = "none";		
    ec_table.rows(i).cells(6).style.display = "none";		
    ec_table.rows(i).cells(7).style.display = "none";		
    ec_table.rows(i).cells(8).style.display = "none";		
    ec_table.rows(i).cells(9).style.display = "none";		
    ec_table.rows(i).cells(10).style.display = "none";		
    ec_table.rows(i).cells(11).style.display = "none";		
    ec_table.rows(i).cells(12).style.display = "none";		
    ec_table.rows(i).cells(13).style.display = "none";		
  }
   }
}*/
// -->
</script>
