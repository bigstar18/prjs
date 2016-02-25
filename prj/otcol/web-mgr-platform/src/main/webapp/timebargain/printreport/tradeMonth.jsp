<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String orderBy = request.getParameter("orderBy");
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
	if(chcekNull(firmID)){
		filter += " and t.FirmID='"+firmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId))
		if(!brokerId.equals("null"))
			filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	String order = "";
	//1按交易商，2按成交量
	if ("1".equals(orderBy)) {
		order = " order by t.firmId asc";
	}else if ("2".equals(orderBy)) {
		order = " order by sumQuanity desc";
	}
	
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	}
	//query data
	String sql = " select t.firmid,sum(t.quantity) sumQuanity,sum(t.tradefee) sumTradeFee, "+
						" sum(case when t.bs_flag=1 and t.ordertype=1 then t.quantity else 0 end) holdbuy,"+
						" sum(case when t.bs_flag=2 and t.ordertype=1 then t.quantity else 0 end) holdsell,"+
						" sum(case when t.bs_flag=1 and t.ordertype=2 then t.quantity else 0 end) tranbuy,"+
						" sum(case when t.bs_flag=2 and t.ordertype=2 then t.quantity else 0 end) transell,"+
						" sum(case when to_char(t.TradeAtClearDate,'yyyy-MM-dd')=to_char(t.AtClearDate,'yyyy-MM-dd') and t.ordertype=2 then t.quantity else 0 end)damount,"+
						" sum(case when to_char(t.TradeAtClearDate,'yyyy-MM-dd')=to_char(t.AtClearDate,'yyyy-MM-dd') and t.ordertype=2 then t.tradefee else 0 end)dfee"+
						" from t_h_trade t where "+filter+" group by t.firmid" + order;
						
		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	    List list=dao.queryBySQL(sql);
    
		BigDecimal sumQuanity = new BigDecimal(0);
		BigDecimal sumTradeFee = new BigDecimal("0.00");
		BigDecimal sumHoldbuy = new BigDecimal(0);
		BigDecimal sumHoldsell = new BigDecimal(0);
		BigDecimal sumTranbuy = new BigDecimal(0);
		BigDecimal sumTransell = new BigDecimal(0);
		BigDecimal sumDamount = new BigDecimal(0);
		BigDecimal sumFee = new BigDecimal(0);
		%>
		
	<br><center class="reportHead">成交量统计表</center>
	<table align="center" width="600px">
	<tr>
		<td class="reportLeft" colspan="5" width="50%">&nbsp;</td>
		<td class="reportRight">起始日期:&nbsp;<%=startClearDate %>&nbsp;&nbsp;</td>
		<td class="reportRight">结束日期:&nbsp;<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead" rowspan="2">交易商代码</td>
	<td class="td_reportMdHead" rowspan="2">加盟商</td>
	<td class="td_reportMdHead" colspan="2">总成交量</td>
	<td class="td_reportMdHead" colspan="2">订货成交量</td>
	<td class="td_reportMdHead" colspan="2">转让成交量</td>
	<td class="td_reportRdHead" colspan="2">当订当转量</td>
	</tr>
	<tr>
	<td class="td_reportMdHead">总成交量</td>
	<td class="td_reportMdHead">手续费</td>
	<td class="td_reportMdHead">买入</td>
	<td class="td_reportMdHead">卖出</td>
	<td class="td_reportMdHead">买入</td>
	<td class="td_reportMdHead">卖出</td>
	<td class="td_reportMdHead">成交量</td>
	<td class="td_reportRdHead">手续费</td>
	</tr>
		<%
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	String getFirmId = (String)innerMap.get("firmId");
    	String brokerSql="select brokerId from M_B_FIRMANDBROKER where firmId='"+getFirmId+"'";
		List brokerList=dao.queryBySQL(brokerSql);
		String brokerId2=null;
		if(brokerList.size()>0){
			Map broker=(Map)brokerList.get(0);
			brokerId2=(String)broker.get("brokerId");
		}
    	%>	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<c:if test="<%=brokerId2 != null%>">
		&nbsp;<%=brokerId2%></c:if></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumQuanity")) %></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("sumTradeFee")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("holdbuy")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("holdsell")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("tranbuy")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("transell")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("damount")) %></td>
	<td class="td_reportRd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("dfee")) %>" pattern="#,##0.00"/></td>
	</tr>
	<%
		sumQuanity = sumQuanity.add(turnToNum(innerMap.get("sumQuanity")));
		sumTradeFee = sumTradeFee.add(turnToNum(innerMap.get("sumTradeFee")));
		sumHoldbuy = sumHoldbuy.add(turnToNum(innerMap.get("holdbuy")));
		sumHoldsell = sumHoldsell.add(turnToNum(innerMap.get("holdsell")));
		sumTranbuy = sumTranbuy.add(turnToNum(innerMap.get("tranbuy")));
		sumTransell = sumTransell.add(turnToNum(innerMap.get("transell")));
		sumDamount = sumDamount.add(turnToNum(innerMap.get("damount")));
		sumFee = sumFee.add(turnToNum(innerMap.get("dfee")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuanity %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradeFee %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumHoldbuy %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumHoldsell %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTranbuy %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTransell %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumDamount %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumFee %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%}else{%>
		<tr>
			<td class="td_reportRd" colspan="10">
				无符合条件信息。
			</td>
		</tr>
		<%}%>
	</table>