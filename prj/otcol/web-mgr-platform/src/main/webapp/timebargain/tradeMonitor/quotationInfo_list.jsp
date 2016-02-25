<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script>
		function runTime() {window.setInterval("ECSideUtil.reload('ec')",1000);}
		runTime();
</script>
<title>
default
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="600">
  <tr><td>
	<ec:table items="quotationInfoList" var="quotationInfo" 
			action="${pageContext.request.contextPath}/timebargain/tradeMonitor/tradeMonitor.do?funcflg=listQuotationInfo"
			xlsFileName="quotationInfoList.xls" 
			csvFileName="quotationInfoList.csv"
			showPrint="true" 			
			rowsDisplayed="20"
			listWidth="100%"	  
	>
		<ec:row>	
			
			<ec:column property="Commodityid" title="quotation.CommodityID" width="90" style="text-align:center;"/>
			<ec:column property="price" title="quotation.Price" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="yesterBalancePrice" title="昨日结算价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="openPrice" title="今开市价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="highPrice" title="最高价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="lowPrice" title="最低价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="curPrice" title="最新价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="reserveCount" title="订货量" cell="currency" width="90" style="text-align:center;"/>
			<ec:column property="totalAmount" title="总成交量" cell="currency" width="90" style="text-align:center;"/>
			<ec:column property="spread" title="涨跌" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="createTime" title="quotation.CreateTime" cell="date" format="date" width="90" style="text-align:center;"/>
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
</body>
</html>