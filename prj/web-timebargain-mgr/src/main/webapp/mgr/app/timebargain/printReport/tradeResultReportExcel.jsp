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
		filter += " and t.settleprocessdate>=to_date('"+startClearDate+"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.settleprocessdate<=to_date('"+endClearDate+"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select to_char(t.settleprocessdate,'yyyy-MM-dd') settleprocessdate,t.commodityid commodityid,(case when t.bs_flag=1 then '买' else '卖' end) bs,"+
				" t.firmid firmid,t.customerid customerid,(t.holdqty+t.gageqty) amount,t.price price,settlePrice,t.payout payout "+
				" from t_settleholdposition t where "+filter+
				" order by t.settleprocessdate,t.commodityid,t.bs_flag,t.firmid ";
						
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">交收记录表</center><br><br>
	<table align="center" width="700px" border="0">
	<tr>
		<td class="reportRight" colspan="9">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="700px">
	<tr>
	<td class="td_reportMdHead">交收日期</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买方/卖方</td>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">数量</td>
	<td class="td_reportMdHead">平均价</td>
	<td class="td_reportMdHead">交收价</td>
	<td class="td_reportRdHead">货款</td>
	</tr>
    <%    	
		BigDecimal sumAmount = new BigDecimal(0);
		BigDecimal sumPrice = new BigDecimal(0);
		BigDecimal sumSettlePrice = new BigDecimal(0);
		BigDecimal sumPayout = new BigDecimal(0);
		
		BigDecimal countAmount = new BigDecimal(0);
		BigDecimal countPrice = new BigDecimal(0);
		BigDecimal countSettlePrice = new BigDecimal(0);
		BigDecimal countPayout = new BigDecimal(0);
		
		String outermark = null;
		String innermark = null;
		
		int mark = 0;
		int listsize = list.size() - 1;
		
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);    		
    		mark=a;
    		
    		innermark = turnToStr(innerMap.get("settleprocessdate"))+
    				turnToStr(innerMap.get("commodityid"))+turnToStr(innerMap.get("bs"));
    		if(!innermark.equals(outermark)){
    			if(outermark != null){
    			%>
    			<tr>
				<td class="td_reportMd" colspan="3"><b>小计</b></td>
				<td class="td_reportMd">&nbsp;</td>
				<td class="td_reportMd">&nbsp;</td>
				<td class="td_reportMd1"><b>&nbsp;<%=sumAmount %></b></td>
				<td class="td_reportMd1"><b>&nbsp;</b></td>
				<td class="td_reportMd1"><b>&nbsp;</b></td>
				<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumPayout %>" pattern="#,##0.00"/></b></td>
				</tr>    			
    			<%
    			sumAmount = new BigDecimal(0);
				sumPrice = new BigDecimal(0);
				sumSettlePrice = new BigDecimal(0);
				sumPayout = new BigDecimal(0);
    			}
    		outermark = innermark;
    		}	
		%> 	
		<tr>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("settleprocessdate")) %></td>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("bs")) %></td>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
		<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("amount")) %></td>
		<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
		<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("settlePrice")) %></td>
		<td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("payout")) %></td>
		</tr>
	<%
		sumAmount = sumAmount.add(turnToNum(innerMap.get("amount")));
		sumPrice = sumPrice.add(turnToNum(innerMap.get("price")));
		sumSettlePrice = sumSettlePrice.add(turnToNum(innerMap.get("settlePrice")));
		sumPayout = sumPayout.add(turnToNum(innerMap.get("payout")));
		
		countAmount = countAmount.add(turnToNum(innerMap.get("amount")));
		countPrice = countPrice.add(turnToNum(innerMap.get("price")));
		countSettlePrice = countSettlePrice.add(turnToNum(innerMap.get("settlePrice")));
		countPayout = countPayout.add(turnToNum(innerMap.get("payout")));
	}
	if(mark == listsize){
		%>
		<tr>
		<td class="td_reportMd" colspan="3"><b>小计</b></td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd1"><b>&nbsp;<%=sumAmount %></b></td>
		<td class="td_reportMd1"><b>&nbsp;</b></td>
		<td class="td_reportMd1"><b>&nbsp;</b></td>
		<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumPayout %>" pattern="#,##0.00"/></b></td>
		</tr>
		<%
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="3"><b>合计</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=countAmount %></b></td>
	<td class="td_reportMd1"><b>&nbsp;</b></td>
	<td class="td_reportMd1"><b>&nbsp;</b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=countPayout %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="9">
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