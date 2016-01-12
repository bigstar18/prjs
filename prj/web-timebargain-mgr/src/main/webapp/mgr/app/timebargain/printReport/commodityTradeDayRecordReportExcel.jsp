<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<%@ include file="/mgr/app/timebargain/printReport/excelExpFile.jsp"%>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:m="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
</head>
<body>
<table align="center">
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
 <%
	String commodityID  = request.getParameter("startCommodityID");
	String cleardate = request.getParameter("cleardate");
	
	String filter = " 1=1 ";
	if(chcekNull(commodityID)){
		filter += " and t.commodityid='"+commodityID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select t.a_tradeno a_tradeno,to_char(t.tradetime,'hh24:mi:ss') tradeTime,t.firmid firmid,t.customerid customerid, "+
						" (case when t.bs_flag=1 then '买进' else '卖出' end )||(case when t.OrderType=1 then '订立' else '转让' end) type,"+
						" t.price price,t.Quantity Quantity,to_char(o.ordertime,'hh24:mi:ss') orderTime from t_h_trade t,t_h_orders o"+
						" where t.a_orderno=o.a_orderno and t.cleardate=o.cleardate and "+filter+" order by t.firmid,t.a_tradeno";
						
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><br><center class="reportHead">当日分商品成交记录表</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft">商品代码:<%=commodityID %></td>
		<td class="reportRight" colspan="6">日期:<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">成交号</td>
	<td class="td_reportMdHead">成交时间</td>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">交易类型</td>
	<td class="td_reportMdHead">成交价</td>
	<td class="td_reportMdHead">数量</td>
	<td class="td_reportRdHead">委托时间</td>
	</tr>
    <%    	
		BigDecimal sumQuantity = new BigDecimal(0);
		
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
		%> 	
	<tr>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("a_tradeno")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("tradeTime")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("Quantity")) %></td>
	<td class="td_reportRd">&nbsp;<%=turnToStr(innerMap.get("orderTime")) %></td>
	</tr>
	<%
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("Quantity")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportRd">&nbsp;</td>
	</tr>
<%}else{%>
	<tr>
		<td class="td_reportRd" colspan="8">
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
			</td>	
		</tr>
</table>
</body>
</html>