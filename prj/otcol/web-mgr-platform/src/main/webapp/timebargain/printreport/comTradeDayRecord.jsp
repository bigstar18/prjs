<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-02
	//get query condition and handle 
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
		filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(cateGoryId)){
		if(!"null".equals(cateGoryId)){
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
		}
	}
	//query data
	String sql = " select t.firmid firmid,m.name name,t.commodityid commodityid,sum(case when t.ordertype=1 and t.bs_flag=1 then Quantity else 0 end) buyAgree, "+
						" sum(case when t.ordertype=1 and t.bs_flag=2 then Quantity else 0 end) sellAgree,"+
						" sum(case when t.ordertype=2 and t.bs_flag=1 then Quantity else 0 end) buyTransfer,"+
						" sum(case when t.ordertype=2 and t.bs_flag=2 then Quantity else 0 end) sellTransfer,"+
						" sum(case when t.ordertype=2 and t.bs_flag=1 and t.tradetype=3 then Quantity else 0 end) insteadBuyTransfer,"+
						" sum(case when t.ordertype=2 and t.bs_flag=2 and t.tradetype=3 then Quantity else 0 end) insteadSellTransfer,"+
						"sum(t.close_PL) allClose_PL,sum(t.tradeFee) allTradeFee,"+
						" sum(Quantity) allQuantity,sum(Quantity*price*ContractFactor) tradeMoney from t_h_trade t,t_h_commodity c,m_firm m where "+
						filter+" and t.commodityid=c.commodityid and t.cleardate=c.cleardate and t.firmId = m.firmId group by t.firmid,t.commodityid,m.name order by t.firmid,t.commodityid";
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	//System.out.println(sql);
    List list=dao.queryBySQL(sql);
    
    %><br><center class="reportHead">分交易商成交量统计表</center><br><br>
	<table width="850px" align="center">
	<tr>
		<td class="reportLeft"><%if(!("null".equals(startFirmID) || "null".equals(endFirmID))){ %>起始交易商:<%=startFirmID%>&nbsp;结束交易商:&nbsp;<%=endFirmID%>&nbsp;
		<%} %><%if(!"null".equals(brokerId)){ %>加盟商:&nbsp;<%=brokerId %><%} %></td>
		<td class="reportRight">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table width="850px" align="center" class="reportTemp">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">交易商名称</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买进订立</td>
	<td class="td_reportMdHead">卖出订立</td>
	<td class="td_reportMdHead">买进转让</td>
	<td class="td_reportMdHead">卖出转让</td>
	<td class="td_reportMdHead">代为买进转让</td>
	<td class="td_reportMdHead">代为卖出转让</td>
	<td class="td_reportMdHead">总量</td>
	<td class="td_reportMdHead">转让盈亏</td>
	<td class="td_reportMdHead">手续费</td>
	<td class="td_reportRdHead">成交额</td>
	</tr><%    	
		BigDecimal sumBuyAgree = new BigDecimal(0);//小计用
		BigDecimal sumSellAgree = new BigDecimal(0);
		BigDecimal sumBuyTransfer = new BigDecimal(0);
		BigDecimal sumSellTransfer = new BigDecimal(0);
		BigDecimal sumInsteadBuyTransfer = new BigDecimal(0);
		BigDecimal sumInsteadSellTransfer = new BigDecimal(0);
		BigDecimal sumAllQuantity = new BigDecimal(0);
		BigDecimal sumAllClose_PL=new BigDecimal("0.00");
		BigDecimal sumAllTradeFee=new BigDecimal("0.00");
		BigDecimal sumTradeMoney = new BigDecimal("0.00");
		
		BigDecimal countBuyAgree = new BigDecimal(0);//总计用
		BigDecimal countSellAgree = new BigDecimal(0);
		BigDecimal countBuyTransfer = new BigDecimal(0);
		BigDecimal countSellTransfer = new BigDecimal(0);
		BigDecimal countInsteadBuyTransfer = new BigDecimal(0);
		BigDecimal countInsteadSellTransfer = new BigDecimal(0);
		BigDecimal countAllQuantity = new BigDecimal(0);
		BigDecimal countAllClose_PL = new BigDecimal("0.00");
		BigDecimal countAllTradeFee = new BigDecimal("0.00");
		BigDecimal countTradeMoney = new BigDecimal("0.00");
		
		String mark = null;//标记
		int size = list.size()-1;
		int num = 0;
		
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
    		num  =  a;    		
    		
    		if(!turnToStr(innerMap.get("firmid")).equals(mark)){
    		
    			if(mark != null){
		%><tr>
			<td class="td_reportMd" colspan="3"><b>交易商小计</b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumBuyAgree %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumSellAgree %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumBuyTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumSellTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumInsteadBuyTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumInsteadSellTransfer %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumAllQuantity %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumAllClose_PL %></b></td>
			<td class="td_reportMd1"><b>&nbsp;<%=sumAllTradeFee %></b></td>
			<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradeMoney %>" pattern="#,##0.00"/></b></td>
			</tr><% 
						sumBuyAgree = new BigDecimal(0);
						sumSellAgree = new BigDecimal(0);
						sumBuyTransfer = new BigDecimal(0);
						sumSellTransfer = new BigDecimal(0);
						sumInsteadBuyTransfer = new BigDecimal(0);
						sumInsteadSellTransfer = new BigDecimal(0);
						sumAllQuantity = new BigDecimal(0);
						sumTradeMoney = new BigDecimal("0.00");
						sumAllClose_PL=new BigDecimal("0.00");
						sumAllTradeFee=new BigDecimal("0.00");
				}				
				mark = turnToStr(innerMap.get("firmid"));
				}
				%><tr>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("buyAgree")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("sellAgree")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("buyTransfer")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("sellTransfer")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("insteadBuyTransfer")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("insteadSellTransfer")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("allQuantity")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("allClose_PL")) %></td>
			<td class="td_reportMd1"><%=turnToNum(innerMap.get("allTradeFee")) %></td>
			<td class="td_reportRd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("tradeMoney")) %>" pattern="#,##0.00"/></td>
			</tr><%
    			
				sumBuyAgree = sumBuyAgree.add(turnToNum(innerMap.get("buyAgree")));
    		sumSellAgree = sumSellAgree.add(turnToNum(innerMap.get("sellAgree")));
    		sumBuyTransfer = sumBuyTransfer.add(turnToNum(innerMap.get("buyTransfer")));
    		sumSellTransfer = sumSellTransfer.add(turnToNum(innerMap.get("sellTransfer")));
    		sumInsteadBuyTransfer = sumInsteadBuyTransfer.add(turnToNum(innerMap.get("insteadBuyTransfer")));
    		sumInsteadSellTransfer = sumInsteadSellTransfer.add(turnToNum(innerMap.get("insteadSellTransfer")));
    		sumAllQuantity = sumAllQuantity.add(turnToNum(innerMap.get("allQuantity")));
    		sumAllClose_PL=sumAllClose_PL.add(turnToNum(innerMap.get("allClose_PL")));
    		sumAllTradeFee=sumAllTradeFee.add(turnToNum(innerMap.get("allTradeFee")));
    		sumTradeMoney = sumTradeMoney.add(turnToNum(innerMap.get("tradeMoney")));
    		
			countBuyAgree = countBuyAgree.add(turnToNum(innerMap.get("buyAgree")));
    		countSellAgree = countSellAgree.add(turnToNum(innerMap.get("sellAgree")));
    		countBuyTransfer = countBuyTransfer.add(turnToNum(innerMap.get("buyTransfer")));
    		countSellTransfer = countSellTransfer.add(turnToNum(innerMap.get("sellTransfer")));
    		countInsteadBuyTransfer = countInsteadBuyTransfer.add(turnToNum(innerMap.get("insteadBuyTransfer")));
    		countInsteadSellTransfer = countInsteadSellTransfer.add(turnToNum(innerMap.get("insteadSellTransfer")));
    		countAllQuantity = countAllQuantity.add(turnToNum(innerMap.get("allQuantity")));
    		countAllClose_PL = countAllClose_PL.add(turnToNum(innerMap.get("allClose_PL")));
    		countAllTradeFee = countAllTradeFee.add(turnToNum(innerMap.get("allTradeFee")));
    		countTradeMoney = countTradeMoney.add(turnToNum(innerMap.get("tradeMoney")));
	}
	if(num == size ){
	%><tr>
	<td class="td_reportMd" colspan="3"><b>交易商小计</b></td>
	<td class="td_reportMd1"><b><%=sumBuyAgree %></b></td>
	<td class="td_reportMd1"><b><%=sumSellAgree %></b></td>
	<td class="td_reportMd1"><b><%=sumBuyTransfer %></b></td>
	<td class="td_reportMd1"><b><%=sumSellTransfer %></b></td>
	<td class="td_reportMd1"><b><%=sumInsteadBuyTransfer %></b></td>
	<td class="td_reportMd1"><b><%=sumInsteadSellTransfer %></b></td>
	<td class="td_reportMd1"><b><%=sumAllQuantity %></b></td>
	<td class="td_reportMd1"><b><%=sumAllClose_PL %></b></td>
	<td class="td_reportMd1"><b><%=sumAllTradeFee%></b></td>
	<td class="td_reportRd1"><b><fmt:formatNumber value="<%=sumTradeMoney %>" pattern="#,##0.00"/></b></td>
	</tr><%
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="3"><b>合计</b></td>
	<td class="td_reportMd1"><b><%=countBuyAgree %></b></td>
	<td class="td_reportMd1"><b><%=countSellAgree %></b></td>
	<td class="td_reportMd1"><b><%=countBuyTransfer %></b></td>
	<td class="td_reportMd1"><b><%=countSellTransfer %></b></td>
	<td class="td_reportMd1"><b><%=countInsteadBuyTransfer %></b></td>
	<td class="td_reportMd1"><b><%=countInsteadSellTransfer %></b></td>
	<td class="td_reportMd1"><b><%=countAllQuantity %></b></td>
	<td class="td_reportMd1"><b><%=countAllClose_PL %></b></td>
	<td class="td_reportMd1"><b><%=countAllTradeFee %></b></td>
	<td class="td_reportRd1"><b><fmt:formatNumber value="<%=countTradeMoney %>" pattern="#,##0.00"/></b></td>
	</tr><%}else{%>
		<tr>
		<td class="td_reportRd" colspan="13">
			无符合条件信息。
		</td>
	</tr><%}%>
	</table>