<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="quotationList" var="quotation" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listQuotation"	
			xlsFileName="quotationList.xls" 
			csvFileName="quotationList.csv"
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
			
			<ec:column property="Commodityid" title="商品代码" width="90" style="text-align:center;"/>
			<ec:column property="price" title="平均价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="yesterBalancePrice" title="昨日结算价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="openPrice" title="今开市价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="highPrice" title="最高价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="lowPrice" title="最低价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="curPrice" title="最新价" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="reserveCount" title="订货量" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="totalAmount" title="总成交量" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="spreadNow" title="涨跌" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="createTime" title="创建时间" cell="date" format="date" width="90" style="text-align:center;"/>
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
  if(request.getAttribute("quotationList") != null)
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
</html>
