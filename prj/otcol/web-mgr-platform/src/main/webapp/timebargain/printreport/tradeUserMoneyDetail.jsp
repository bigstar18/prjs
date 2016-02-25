<%@ page contentType="text/html;charset=GBK" %>
<%
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String cleardate = request.getParameter("cleardate");
	String order = request.getParameter("order");
	String brokerId=request.getParameter("brokerId");
	String filter = " and 1=1 ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
		filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(order)){
		if("firmid".equals(order)){
			order = " t."+order;
		}
		if("todaybalance".equals(order)){
			order = " f."+order;
		}
		if("allMoney".equals(order)){
			order = " t.clearmargin+t.RuntimeFL+f.todaybalance ";
		}
		filter += " order by "+order+" asc ";
	}
	//query data
	String sql = " select t.firmid firmid,m.name name,t.clearmargin clearmargin, "+
				" t.RuntimeFL RuntimeFL,t.RuntimeSettleMargin RuntimeSettleMargin, "+
				" f.todaybalance todaybalance,t.clearmargin+t.RuntimeFL+f.todaybalance allMoney "+
				" from t_h_firm t,m_firm m,f_firmbalance f "+
				" where t.firmid=m.firmid and t.firmid=f.firmid and t.cleardate=f.b_date "+filter;
		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);    
    	
	   	BigDecimal sumclearmargin = new BigDecimal(0);
	   	BigDecimal sumRuntimeFL = new BigDecimal(0);
	   	BigDecimal sumRuntimeSettleMargin = new BigDecimal(0);
	   	BigDecimal sumTodaybalance = new BigDecimal(0); 
	   	BigDecimal sumAllMoney = new BigDecimal(0); 
	   %>
	   
	<br>
	<center class="reportHead">当日交易商资金情况表</center>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportRight" colspan="7">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">交易商名称</td>
	<td class="td_reportMdHead">加盟商</td>
	<td class="td_reportMdHead">本日初始交易订金</td>
	<td class="td_reportMdHead">本日追加交易订金</td>
	<td class="td_reportMdHead">本日交收交易订金</td>
	<td class="td_reportMdHead">可用资金</td>
	<td class="td_reportRdHead">本次余额</td>
	</tr>	
	   <%		
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	String getFirmId = (String)innerMap.get("firmId");
    	String brokerSql="select brokerId from m_b_broker where firmId='"+getFirmId+"'";
		List brokerList=dao.queryBySQL(brokerSql);
		String brokerId2=null;
		if(brokerList.size()>0){
			Map broker=(Map)brokerList.get(0);
			brokerId2=(String)broker.get("brokerId");
		}
		%>
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
	<td class="td_reportMd"><c:if test="<%=brokerId2 != null %>">&nbsp;<%=brokerId2 %></c:if>&nbsp;</td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("clearmargin")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("RuntimeFL")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("RuntimeSettleMargin")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("todaybalance")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportRd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("allMoney")) %>" pattern="#,##0.00"/></td>
	</tr>
	<%
		sumclearmargin = sumclearmargin.add(turnToNum(innerMap.get("clearmargin")));
	   	sumRuntimeFL = sumRuntimeFL.add(turnToNum(innerMap.get("RuntimeFL")));
	   	sumRuntimeSettleMargin = sumRuntimeSettleMargin.add(turnToNum(innerMap.get("RuntimeSettleMargin")));
	   	sumTodaybalance = sumTodaybalance.add(turnToNum(innerMap.get("todaybalance")));
	   	sumAllMoney = sumAllMoney.add(turnToNum(innerMap.get("allMoney")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumclearmargin %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumRuntimeFL %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumRuntimeSettleMargin %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTodaybalance %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumAllMoney %>" pattern="#,##0.00"/></b></td>
	</tr>
<%}else{%>
	<tr>
			<td class="td_reportRd" colspan="7">
				无符合条件信息。
			</td>
		</tr>
	<%}%>
	</table>