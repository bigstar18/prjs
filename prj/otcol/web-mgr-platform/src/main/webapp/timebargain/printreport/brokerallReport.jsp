<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle 
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	
	String startBrokerID = request.getParameter("startFirmID");
	String endBrokerID = request.getParameter("endFirmID");
	
	String sql_1="nvl(sum(case when oprcode = '219' then amount else 0 end),0) Income";
	sql_1+=",nvl(sum(case when oprcode in('101','103') then amount else 0 end),0) inmoney";
	sql_1+=",nvl(sum(case when oprcode in('102','104') then amount else 0 end),0) outmoney";
	sql_1+=",nvl(sum(case when oprcode ='201' then amount else 0 end),0) fee ,nvl(sum(case when oprcode='220' then -1*amount when oprcode='221' then amount else 0 end),0) yanqi ";
	
	String sql1="select a.brokerid,name,a.marketmanager, nvl(sum(Income), 0) Income,nvl(sum(inmoney), 0) inmoney, nvl(sum(outmoney),0) outmoney, nvl(sum(fee),0) fee, nvl(sum(yanqi),0) yanqi ";
	   sql1+=" , nvl(sum(d.tradeqty),0) tradeqty,nvl(sum(d.close_pl),0) close_pl";
	   String sql_2= " select firmid,"+sql_1+" from f_h_Fundflow where b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd') group by  firmid ";
	   String sql_3="select firmid,nvl(sum(quantity),0) tradeqty,nvl(sum(close_pl),0)close_pl from  t_h_trade where cleardate>=to_date('"+startClearDate+"','yyyy-MM-dd') and  cleardate<=to_date('"+endClearDate+"','yyyy-MM-dd') group by  firmid ";
	   sql1+=" from m_b_broker a,m_b_firmandbroker b,("+sql_2+") c,("+sql_3+") d where a.brokerid=b.brokerid and b.firmid=c.firmid(+)  and b.firmid=d.firmid(+) group by a.brokerid,name,a.marketmanager";
	
	String t_h_firm = "select * from t_h_firm where cleardate=(select max(b_date) from f_firmbalance where  b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd'))";
	String t_h_firmholdsum = "select firmid,sum(floatingloss) floatingloss,sum(holdqty) holdqty from t_h_firmholdsum where cleardate=(select max(b_date) from f_firmbalance where  b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd')) group by firmid ";
     String sql2="select h.brokerid bkid,sum(floatingloss) floatingloss ,sum(holdqty) holdqty ,sum(e.todaybalance) todaybalance,sum(runtimemargin) runtimemargin ,sum(runtimefl) runtimefl from m_b_firmandbroker h, f_firmbalance e,("+t_h_firm+") f,("+t_h_firmholdsum+") g where";
     sql2+="  h.firmid = e.firmid and h.firmid=f.firmid and h.firmid=g.firmid(+)  and e.b_date=(select max(b_date) from f_firmbalance where  b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd')) group by h.brokerid";
	
     String sql=" select t.*,t1.*,(nvl(t1.todaybalance,0) + nvl(t1.runtimemargin,0) + nvl(t1.floatingloss,0) +nvl(t1.runtimefl,0)) quanyi from ("+sql1+") t,("+sql2+") t1 where t.brokerid=t1.bkid and t.brokerid>='"+startBrokerID+"' and  t.brokerid<='"+endBrokerID+"' order by t.brokerid ";
	//System.out.println(sql);					
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    %>
    <br><center class="reportHead">分加盟商综合统计表</center><br><br>
	<table align="center" width="900px" border="0">
	<tr>
		<td class="reportRight" colspan="9">起始日期:<%=startClearDate %>&nbsp;起始日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="900px">
	<tr>
	<td class="td_reportMdHead">会员账号</td>
	<td class="td_reportMdHead">会员名称</td>
	<td class="td_reportMdHead">开发人员</td>
	<td class="td_reportMdHead">佣金</td>
	<td class="td_reportMdHead">入金</td>
	<td class="td_reportMdHead">出金</td>
	<td class="td_reportMdHead">成交量</td>
	<td class="td_reportMdHead">手续费</td>
	<td class="td_reportMdHead">转让盈亏</td>
	<td class="td_reportMdHead">可用资金</td>
	<td class="td_reportMdHead">保证金</td>
	<td class="td_reportMdHead">订货数量</td>
	<td class="td_reportMdHead">浮动盈亏</td>
	<td class="td_reportMdHead">权益</td>
	<td class="td_reportRdHead"> 补偿金</td>
	</tr><%    
		//合计
		BigDecimal sumIncome=new BigDecimal("0.00");
		BigDecimal sumInmoney=new BigDecimal("0.00");
		BigDecimal sumOutmoney=new BigDecimal("0.00");
		BigDecimal sumTradeqty=new BigDecimal(0);
		BigDecimal sumFee =new BigDecimal("0.00");
		BigDecimal sumClose_pl=new BigDecimal("0.00");
		BigDecimal sumTodaybalance=new BigDecimal("0.00");
		BigDecimal sumRuntimemargin=new BigDecimal("0.00");
		BigDecimal sumHoldqty=new BigDecimal(0);
		BigDecimal sumFloatingloss=new BigDecimal("0.00");
		BigDecimal sumQuanyi=new BigDecimal("0.00");
		BigDecimal sumYanyi=new BigDecimal("0.00");
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);    		
			sumIncome=sumIncome.add(turnToNum(innerMap.get("Income")));
			sumInmoney=sumInmoney.add(turnToNum(innerMap.get("inmoney")));
			sumOutmoney=sumOutmoney.add(turnToNum(innerMap.get("outmoney")));
			sumTradeqty=sumTradeqty.add(turnToNum(innerMap.get("tradeqty")));
			sumFee=sumFee.add(turnToNum(innerMap.get("fee")));
			sumClose_pl=sumClose_pl.add(turnToNum(innerMap.get("close_pl")));
			sumTodaybalance=sumTodaybalance.add(turnToNum(innerMap.get("todaybalance")));
			sumRuntimemargin=sumRuntimemargin.add(turnToNum(innerMap.get("runtimemargin")));
			sumHoldqty=sumHoldqty.add(turnToNum(innerMap.get("holdqty")));
			sumFloatingloss=sumFloatingloss.add(turnToNum(innerMap.get("floatingloss")));
			sumQuanyi=sumQuanyi.add(turnToNum(innerMap.get("quanyi")));
			sumYanyi=sumYanyi.add(turnToNum(innerMap.get("yanqi")));
		%><tr>
		<td class="td_reportMd"><%=turnToStr(innerMap.get("brokerid")) %></td>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
		<td class="td_reportMd"><%--<%
		if(innerMap.get("note")!=null){
			String note=turnToStr(innerMap.get("note"));
			if(note.split(",").length>0 && note.split(",")[note.split(",").length-1].startsWith("市场") && note.split(",")[note.split(",").length-1].split(":").length>1){
				out.print(note.split(",")[note.split(",").length-1].split(":")[1]);
			}
		}
		
		
		%>--%>&nbsp;<%=turnToStr(innerMap.get("marketmanager")) %></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("Income")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("inmoney")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("outmoney")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("tradeqty")) %>" pattern="#,##0"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("fee")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("close_pl")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("todaybalance")) %>" pattern="#,##0.00"/></td>
		
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("runtimemargin")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("holdqty")) %>" pattern="#,##0"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("floatingloss")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("quanyi")) %>" pattern="#,##0.00"/></td>
		<td class="td_reportRd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("yanqi")) %>" pattern="#,##0.00"/></td>
		
		
		</tr><%
		
	}
	%>
	<tr>
	<td class="td_reportMd" colspan="3" style="text-align:center;">合计</td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumIncome %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumInmoney %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumOutmoney %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumTradeqty %>" pattern="#,##0"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumFee %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumClose_pl %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumTodaybalance %>" pattern="#,##0.00"/></td>
		
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumRuntimemargin %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumHoldqty %>" pattern="#,##0"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumFloatingloss %>" pattern="#,##0.00"/></td>
		<td class="td_reportMd"><fmt:formatNumber value="<%=sumQuanyi %>" pattern="#,##0.00"/></td>
		<td class="td_reportRd1"><fmt:formatNumber value="<%=sumYanyi %>" pattern="#,##0.00"/></td>
		
	</tr>
	
	<%
	if(list.size()>0){
	%><%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="15">
			无符合条件信息。
		</td>
	</tr>
	<%}%>
	</table>