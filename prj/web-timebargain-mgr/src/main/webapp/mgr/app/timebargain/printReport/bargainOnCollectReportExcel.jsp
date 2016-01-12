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
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	
	String filter = " 1=1 ";	
	if(chcekNull(startClearDate)){
		filter += " and t.settleprocessdate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.settleprocessdate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select t.commodityid commodityid,case when t.bs_flag=1 then '买' else '卖' end bs, "+
						" t.firmid firmid,m.name name,sum(t.holdqty+t.gageqty) qty, "+
						" trunc(sum((t.holdqty+t.gageqty)*t.price)/sum(t.holdqty+t.gageqty),2) avgPrice, "+
						" max(settlePrice) settlePrice,sum(t.payout) payout,sum(t.SettleMargin) SettleMargin "+
						" from t_settleholdposition t,m_firm m where t.firmid=m.firmid and "+filter+
						" group by t.commodityid,t.bs_flag,t.firmid,m.name order by t.commodityid,t.bs_flag,t.firmid,m.name";
						
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">交收汇总表</center><br><br>			
	<table align="center" width="600px">
	<tr><td colspan="7"></td></tr>
	<tr>
		<td class="reportRight" colspan="7">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买方/卖方</td>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">数量</td>
	<td class="td_reportMdHead">平均价</td>
	<td class="td_reportMdHead">交收价</td>
	<td class="td_reportMdHead">交收保证金(交收前)</td>
	<td class="td_reportRdHead">货款</td>
	</tr>
    <% 
    	BigDecimal sumQty = new BigDecimal(0);
    	BigDecimal sumPayout = new BigDecimal("0.00");
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
		%> 	
	<tr>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("bs")) %></td>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	    <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("qty")) %></td>
	    <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("avgPrice")) %></td>
	    <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("settlePrice")) %></td>
	    <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("SettleMargin")) %></td>
	    <td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("payout")) %></td>
	</tr>
	<%
		sumQty = sumQty.add(turnToNum(innerMap.get("qty")));
		sumPayout = sumPayout.add(turnToNum(innerMap.get("payout")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>总计</b></td>
	<td class="td_reportMd" colspan="2">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQty %></b></td>
	<td class="td_reportMd" colspan="3">&nbsp;</td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumPayout %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="8" align="left">
			无符合条件信息。
		</td>
	</tr>
	<%
	}
	%>
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