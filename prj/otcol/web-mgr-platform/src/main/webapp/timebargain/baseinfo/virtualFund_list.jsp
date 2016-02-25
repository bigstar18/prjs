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

   <table width="100%">
    <tr><td>
    <ec:table items="virtualFundList" var="virtualFund" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/virtualFund.do?funcflg=searchVirtualFund"	
			xlsFileName="virtualFundList.xls" 
			csvFileName="virtualFundList.csv"
			showPrint="true" 
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			title="查询虚拟资金"
	>
	 <ec:row>
            <ec:column property="firmID" title="交易商ID" width="100" style="text-align:center;"/>
            <ec:column property="VirtualFunds" title="虚拟资金" style="text-align:right;"/>			
			<ec:column property="Balance" title="资金余额" width="100" style="text-align:right;"/>							
			<ec:column property="FrozenFunds" title="冻结资金" width="130" style="text-align:right;" />
			<ec:column property="RuntimeFL" title="临时浮亏" style="text-align:right;"/>
			<ec:column property="ClearFL" title="结算浮亏" style="text-align:right;"/>
			<ec:column property="RuntimeMargin" title="临时保证金" style="text-align:right;"/>
			<ec:column property="ClearMargin" title="结算保证金" style="text-align:right;"/>				
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
