<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
</title>
<script type="text/javascript">
<!--
//logoff
function logoff_onclick()
{
	parent.HiddFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=logoff&mkName=noTrade"/>";
}

function batch_do2(entityName, action)
{
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一条记录！');
            return;
        }
        var form = document.forms.ec;
        form.action = action + '&autoInc=false';
        form.submit();
    }
}
// -->
</script>
</head>
<body >
<fieldset class="pickList" >
<legend class="common"><b>查询条件</b></legend>
<form action="${pageContext.request.contextPath}/timebargain/order/orders.do?funcflg=noTradeList"
						method="POST" >
<table border="0" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
<tr>
	<td style="text-align:right;">
		交易节：
	</td>
	<td>
		<select name="sectionId">
			<option value="">全部</option>
			<c:forEach items="${tradeTimeList}" var="tradeTime">
			<c:if test="${sectionId !=null && sectionId == tradeTime.sectionId}">
				<option value="${tradeTime.sectionId}" selected="selected">${tradeTime.name}</option>
			</c:if>
			<c:if test="${sectionId ==null || sectionId != tradeTime.sectionId}">
				<option value="${tradeTime.sectionId}" >${tradeTime.name}</option>
			</c:if>
			</c:forEach>
		</select>
	</td>
	<td align="right">交易员代码：</td>
	<td>
		<input type="" name = "traderId" id="traderId" value="<c:out value='${traderId}'/>"/>
	</td>
	<td aling="left"><input type="submit" value="查询" /></td>
	</tr>
</table>
</form>
</fieldset>
<table width="100%">
  <tr><td>
	<ec:table items="noTradeList" var="noTrade" 
			action="${pageContext.request.contextPath}/timebargain/order/orders.do?funcflg=noTradeList"	
			autoIncludeParameters="${empty param.autoInc}"
			height="500px" 
			xlsFileName="NoTradeList.xls" 
			csvFileName="NoTradeList.csv"
			showPrint="true" 
			listWidth="100%"
			minHeight="300"
			title=""
			rowsDisplayed="20"
	>
		<ec:row>
            <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${noTrade.A_OrderNo};${noTrade.CommodityID};${noTrade.CustomerID}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:30;"/>		
            <ec:column property="CustomerID" title="二级代码" width="80" style="text-align:center;"/>		
            <ec:column property="A_OrderNo" title="委托单号" width="110" style="text-align:center;"/>
            <ec:column property="CommodityID" title="商品代码" width="100" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="买卖" width="55" editTemplate="ecs_t_BS_Flag" mappingItem="BS_FLAG" style="text-align:center;"/>
			<ec:column property="OrderType" title="委托类型" width="65" editTemplate="ecs_t_OrderType" mappingItem="ORDERTYPE" style="text-align:center;"/>
			<ec:column property="Price" title="委托价格" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="Quantity" title="委托数量" width="80" style="text-align:center;"/>
			<ec:column property="TradeQty" title="已成交数量" width="80" style="text-align:center;"/>			
			<ec:column property="Status" title="状态" width="80" editTemplate="ecs_t_Status" mappingItem="ORDER_STATUS" style="text-align:center;"/>
			<ec:column property="OrderTime" title="委托时间"  width="150" cell="date" format="datetime" style="text-align:center;"/>
			<ec:column property="CloseFlag" title="转让标志" width="80" editTemplate="ecs_t_CloseFlag" mappingItem="CLOSEFLAG" style="text-align:center;"/>
			<ec:column property="TraderID" title="交易员代码" width="80" style="text-align:center;"/>
			<ec:column property="ConsignerID" title="代为委托员" width="80" style="text-align:center;"/>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:batch_do2('撤单','<c:url value="/timebargain/order/orders.do?funcflg=withdrawOrder"/>');">撤单</a>
			&nbsp;|&nbsp;&nbsp;<a href="#" onclick="logoff_onclick()">注销登录</a>
		</ec:extend>		
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_BS_Flag" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="BS_Flag" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>	
	<textarea id="ecs_t_OrderType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="OrderType" >
			<ec:options items="ORDERTYPE" />
		</select>
	</textarea>
	<textarea id="ecs_t_Status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="Status" >
			<ec:options items="ORDER_STATUS" />
		</select>
	</textarea>
	<textarea id="ecs_t_CloseFlag" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="CloseFlag" >
			<ec:options items="CLOSEFLAG" />
		</select>
	</textarea>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
