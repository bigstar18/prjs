<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>手续费统计表 </title>
</head>
<body>
	<table align="center" width="600px" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="打印">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="600px">
					<tr>
						<td>
					  </td>
					 </tr>
				</table>
			</td>
			</tr>
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
 <%
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	
	String filter = " 1=1 ";
	String unionFilter = " 1=1 ";	
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
		unionFilter += " and s.settleprocessdate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
		unionFilter += " and s.settleprocessdate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	
	String sql = "select x.commodityid,x.agreeAmount,x.TransferAmount,x.TradeFee,x.ding," +
					"nvl(c.tranAmount,0) tranAmount,nvl(c.SettleFee,0) SettleFee " +
					"from (select d.commodityid,sum(case when OrderType = 1 then Quantity else 0 end) agreeAmount," +
					"sum(case when OrderType = 2 then Quantity else 0 end) TransferAmount, sum(TradeFee) TradeFee," +
					"sum(ding) ding from (select a.commodityid,nvl(Quantity, 0) Quantity,nvl(OrderType, 0) OrderType," +
					"nvl(TradeFee, 0) TradeFee,nvl(ding, 0) ding from (select t.commodityid from t_h_trade t " +
					"where " +filter+ 
					"group by t.commodityid union select s.commodityid from T_SettleHoldPosition s where " +unionFilter+
					" group by s.commodityid) a,(select commodityid,sum(Quantity) Quantity,OrderType,sum(TradeFee) TradeFee," +
					"sum((case when OrderType = 2 and to_char(TradeAtClearDate, 'yyyy-MM-dd') = to_char(AtClearDate, 'yyyy-MM-dd') then Quantity else 0 end)) ding " +
					"from t_h_trade t where "+filter+" group by commodityid,OrderType) b  " +
					"where a.commodityid = b.commodityid(+) order by a.commodityid) d group by d.commodityid order by d.commodityid) x," +
					"(select s.commodityid,sum(HoldQty + GageQty) tranAmount,sum(SettleFee) SettleFee from T_SettleHoldPosition s " +
					"where "+unionFilter+" group by s.commodityid) c " +
					"where x.commodityid = c.commodityid(+) order by x.commodityid";
						
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">手续费统计表</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportRight" colspan="7">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">订立量</td>
	<td class="td_reportMdHead">转让量</td>
	<td class="td_reportMdHead">交收量</td>
	<td class="td_reportMdHead">交易手续费</td>
	<td class="td_reportMdHead">交收手续费</td>
	<td class="td_reportRdHead">当订当转量</td>
	</tr>
    <%    	
		BigDecimal sumSettleFee = new BigDecimal("0.00");
		BigDecimal sumTradeFee = new BigDecimal("0.00");
		BigDecimal sumTranAmount = new BigDecimal(0);
		BigDecimal sumTransferAmount = new BigDecimal(0);
		BigDecimal sumAgreeAmount = new BigDecimal(0);
		BigDecimal sumDing = new BigDecimal(0);
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("agreeAmount")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("TransferAmount")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("tranAmount")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("TradeFee")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("SettleFee")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("ding")) %></td>
	</tr>
	<%
		sumAgreeAmount = sumAgreeAmount.add(turnToNum(innerMap.get("agreeAmount")));
		sumTransferAmount = sumTransferAmount.add(turnToNum(innerMap.get("TransferAmount")));
		sumTranAmount = sumTranAmount.add(turnToNum(innerMap.get("tranAmount")));
		sumTradeFee = sumTradeFee.add(turnToNum(innerMap.get("TradeFee")));
		sumSettleFee = sumSettleFee.add(turnToNum(innerMap.get("SettleFee")));
		sumDing = sumDing.add(turnToNum(innerMap.get("ding")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumAgreeAmount %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTransferAmount %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTranAmount %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradeFee %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumSettleFee %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=sumDing %></b></td>
	</tr>
	<%}else{%>
		<tr>
			<td class="td_reportRd" colspan="7">
				无符合条件信息。
			</td>
		</tr>	
		<%}%>
	</table>
	
					  </td>
					 </tr>
					 <tr><td></td></tr>
				</table>
				</div>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="打印"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>