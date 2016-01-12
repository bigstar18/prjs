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
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String brokerId=request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	
	String filter = " 1=1 ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
		filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(cateGoryId)){
		if(!"null".equals(cateGoryId)){
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
		}
	}
	String sql = " select t.firmid firmid,t.commodityid commodityid,sum(case when t.ordertype=1 and t.bs_flag=1 then Quantity else 0 end) buyAgree, "+
						" sum(case when t.ordertype=1 and t.bs_flag=2 then Quantity else 0 end) sellAgree,"+
						" sum(case when t.ordertype=2 and t.bs_flag=1 then Quantity else 0 end) buyTransfer,"+
						" sum(case when t.ordertype=2 and t.bs_flag=2 then Quantity else 0 end) sellTransfer,"+
						" sum(case when t.ordertype=2 and t.bs_flag=1 and t.tradetype=3 then Quantity else 0 end) insteadBuyTransfer,"+
						" sum(case when t.ordertype=2 and t.bs_flag=2 and t.tradetype=3 then Quantity else 0 end) insteadSellTransfer,"+
						" sum(Quantity) allQuantity,sum(Quantity*price*ContractFactor) tradeMoney from t_h_trade t,t_h_commodity c where "+
						filter+" and t.commodityid=c.commodityid and t.cleardate=c.cleardate group by t.firmid,t.commodityid order by t.firmid,t.commodityid";
						
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">分交易商成交量统计表</center><br><br>
	<table width="700px" align="center">
	<tr>
		<td class="reportLeft">
		<%if(!("null".equals(startFirmID) || "null".equals(endFirmID))){ %>
		起始交易商:<%=startFirmID%>&nbsp;结束交易商:&nbsp;<%=endFirmID%>&nbsp;
		<%} %>
		<%if(!"null".equals(brokerId)){ %>
		加盟商:&nbsp;<%=brokerId %>
		<%} %>
		</td>
		<td class="reportRight">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table width="700px" align="center" class="reportTemp">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买进订立</td>
	<td class="td_reportMdHead">卖出订立</td>
	<td class="td_reportMdHead">买进转让</td>
	<td class="td_reportMdHead">卖出转让</td>
	<td class="td_reportMdHead">代为买进转让</td>
	<td class="td_reportMdHead">代为卖出转让</td>
	<td class="td_reportMdHead">总量</td>
	<td class="td_reportRdHead">成交额</td>
	</tr>
    <%    	
		BigDecimal sumBuyAgree = new BigDecimal(0);//小计用
		BigDecimal sumSellAgree = new BigDecimal(0);
		BigDecimal sumBuyTransfer = new BigDecimal(0);
		BigDecimal sumSellTransfer = new BigDecimal(0);
		BigDecimal sumInsteadBuyTransfer = new BigDecimal(0);
		BigDecimal sumInsteadSellTransfer = new BigDecimal(0);
		BigDecimal sumAllQuantity = new BigDecimal(0);
		BigDecimal sumTradeMoney = new BigDecimal("0.00");
		
		BigDecimal countBuyAgree = new BigDecimal(0);//总计用
		BigDecimal countSellAgree = new BigDecimal(0);
		BigDecimal countBuyTransfer = new BigDecimal(0);
		BigDecimal countSellTransfer = new BigDecimal(0);
		BigDecimal countInsteadBuyTransfer = new BigDecimal(0);
		BigDecimal countInsteadSellTransfer = new BigDecimal(0);
		BigDecimal countAllQuantity = new BigDecimal(0);
		BigDecimal countTradeMoney = new BigDecimal("0.00");
		
		String mark = null;//标记
		int size = list.size()-1;
		int num = 0;
		
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
    		num  =  a;    		
    		
    		if(!turnToStr(innerMap.get("firmid")).equals(mark)){
    		
    			if(mark != null){
		%>
			<tr>
			<td class="td_reportMd" colspan="2"><b>交易商小计</b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumBuyAgree %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumSellAgree %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumBuyTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumSellTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumInsteadBuyTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumInsteadSellTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumAllQuantity %></b></td>
			<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradeMoney %>" pattern="#,##0.00"/></b></td>
			</tr>
				<% 
						sumBuyAgree = new BigDecimal(0);
						sumSellAgree = new BigDecimal(0);
						sumBuyTransfer = new BigDecimal(0);
						sumSellTransfer = new BigDecimal(0);
						sumInsteadBuyTransfer = new BigDecimal(0);
						sumInsteadSellTransfer = new BigDecimal(0);
						sumAllQuantity = new BigDecimal(0);
						sumTradeMoney = new BigDecimal("0.00");
				}				
				mark = turnToStr(innerMap.get("firmid"));
				}
				%>
			<tr>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyAgree")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellAgree")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyTransfer")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellTransfer")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadBuyTransfer")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadSellTransfer")) %></td>
			<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("allQuantity")) %></td>
			<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("tradeMoney")) %></td>
			</tr>
	<%
    			
				sumBuyAgree = sumBuyAgree.add(turnToNum(innerMap.get("buyAgree")));
    		sumSellAgree = sumSellAgree.add(turnToNum(innerMap.get("sellAgree")));
    		sumBuyTransfer = sumBuyTransfer.add(turnToNum(innerMap.get("buyTransfer")));
    		sumSellTransfer = sumSellTransfer.add(turnToNum(innerMap.get("sellTransfer")));
    		sumInsteadBuyTransfer = sumInsteadBuyTransfer.add(turnToNum(innerMap.get("insteadBuyTransfer")));
    		sumInsteadSellTransfer = sumInsteadSellTransfer.add(turnToNum(innerMap.get("insteadSellTransfer")));
    		sumAllQuantity = sumAllQuantity.add(turnToNum(innerMap.get("allQuantity")));
    		sumTradeMoney = sumTradeMoney.add(turnToNum(innerMap.get("tradeMoney")));
    		
				countBuyAgree = countBuyAgree.add(turnToNum(innerMap.get("buyAgree")));
    		countSellAgree = countSellAgree.add(turnToNum(innerMap.get("sellAgree")));
    		countBuyTransfer = countBuyTransfer.add(turnToNum(innerMap.get("buyTransfer")));
    		countSellTransfer = countSellTransfer.add(turnToNum(innerMap.get("sellTransfer")));
    		countInsteadBuyTransfer = countInsteadBuyTransfer.add(turnToNum(innerMap.get("insteadBuyTransfer")));
    		countInsteadSellTransfer = countInsteadSellTransfer.add(turnToNum(innerMap.get("insteadSellTransfer")));
    		countAllQuantity = countAllQuantity.add(turnToNum(innerMap.get("allQuantity")));
    		countTradeMoney = countTradeMoney.add(turnToNum(innerMap.get("tradeMoney")));
	}
	if(num == size ){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>交易商小计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumBuyAgree %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumSellAgree %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumBuyTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumSellTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumInsteadBuyTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumInsteadSellTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumAllQuantity %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradeMoney %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countBuyAgree %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countSellAgree %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countBuyTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countSellTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countInsteadBuyTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countInsteadSellTransfer %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countAllQuantity %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=countTradeMoney %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}else{
		%>
		<tr>
		<td class="td_reportRd" colspan="10">
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