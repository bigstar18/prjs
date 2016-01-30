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
	<table align="center"  border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		 
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
<%
	//get query condition and handle 
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	
	String startBrokerID = request.getParameter("startBrokerID");
	String endBrokerID = request.getParameter("endBrokerID");
	
	String sql_1="nvl(sum(case when oprcode = '15019' then amount else 0 end),0) Income";
	sql_1+=",nvl(sum(case when oprcode in('11001','11003') then amount else 0 end),0) inmoney";
	sql_1+=",nvl(sum(case when oprcode in('11002','11004') then amount else 0 end),0) outmoney";
	sql_1+=",nvl(sum(case when oprcode ='15001' then amount else 0 end),0) fee ,nvl(sum(case when oprcode='15020' then -1*amount when oprcode='15021' then amount else 0 end),0) yanqi ";
	
	String sql1="select a.brokerid,name,a.marketmanager, nvl(sum(Income), 0) Income,nvl(sum(inmoney), 0) inmoney, nvl(sum(outmoney),0) outmoney, nvl(sum(fee),0) fee, nvl(sum(yanqi),0) yanqi ";
	   sql1+=" , nvl(sum(d.tradeqty),0) tradeqty,nvl(sum(d.close_pl),0) close_pl";
	   String sql_2= " select firmid,"+sql_1+" from f_h_Fundflow where b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd') group by  firmid ";
	   String sql_3="select firmid,nvl(sum(quantity),0) tradeqty,nvl(sum(close_pl),0)close_pl from  t_h_trade where cleardate>=to_date('"+startClearDate+"','yyyy-MM-dd') and  cleardate<=to_date('"+endClearDate+"','yyyy-MM-dd') group by  firmid ";
	   sql1+=" from br_broker a,BR_FirmAndBroker b,("+sql_2+") c,("+sql_3+") d where a.brokerid=b.brokerid and b.firmid=c.firmid(+)  and b.firmid=d.firmid(+) group by a.brokerid,name,a.marketmanager";
	
	String t_h_firm = "select * from t_h_firm where cleardate=(select max(b_date) from f_firmbalance where  b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd'))";
	String t_h_firmholdsum = "select firmid,sum(floatingloss) floatingloss,sum(holdqty) holdqty from t_h_firmholdsum where cleardate=(select max(b_date) from f_firmbalance where  b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd')) group by firmid ";
     String sql2="select h.brokerid bkid,sum(floatingloss) floatingloss ,sum(holdqty) holdqty ,sum(e.todaybalance) todaybalance,sum(runtimemargin) runtimemargin ,sum(runtimefl) runtimefl from BR_FirmAndBroker h, f_firmbalance e,("+t_h_firm+") f,("+t_h_firmholdsum+") g where";
     sql2+="  h.firmid = e.firmid and h.firmid=f.firmid and h.firmid=g.firmid(+)  and e.b_date=(select max(b_date) from f_firmbalance where  b_date>=to_date('"+startClearDate+"','yyyy-MM-dd') and  b_date<=to_date('"+endClearDate+"','yyyy-MM-dd')) group by h.brokerid";
	
     String sql=" select t.*,t1.*,(nvl(t1.todaybalance,0) + nvl(t1.runtimemargin,0) + nvl(t1.floatingloss,0) +nvl(t1.runtimefl,0)) quanyi from ("+sql1+") t,("+sql2+") t1 where t.brokerid=t1.bkid and t.brokerid>='"+startBrokerID+"' and  t.brokerid<='"+endBrokerID+"' order by t.brokerid ";
	System.out.println(sql);					
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    %>
    <br><center class="reportHead">分加盟商综合统计表</center><br><br>
	<table align="center" width="1300px" border="0">
	<tr>
		<td class="reportRight" colspan="9">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="1300px">
	<tr>
	<td class="td_reportMdHead">会员账号</td>
	<td class="td_reportMdHead">会员名称</td>
	<td class="td_reportMdHead">开发人</td>
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
	</tr>
    <%    
		//总计
	   		BigDecimal countIncome = new BigDecimal(0);
	   		BigDecimal countnmoney = new BigDecimal(0);
	   		BigDecimal countoOutmoney = new BigDecimal(0);
	   		BigDecimal countradeqty = new BigDecimal(0);
	   		BigDecimal countfee = new BigDecimal(0);
	   		BigDecimal countlose_pl = new BigDecimal(0);
	   		BigDecimal counttodaybalance = new BigDecimal(0);
	   		BigDecimal countruntimemargin = new BigDecimal(0);
	   		BigDecimal countholdqty = new BigDecimal(0);
	   		BigDecimal countfloatingloss = new BigDecimal(0);
	   		BigDecimal countquanyi = new BigDecimal(0);
	   		BigDecimal countyYanqi = new BigDecimal(0);
		
		
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);    		

		%> 	
		<tr>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("brokerid")) %></td>
		<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
		<td class="td_reportMd"><%-- <%
		if(innerMap.get("note")!=null){
			String note=turnToStr(innerMap.get("note"));
			if(note.split(",").length>0 && note.split(",")[note.split(",").length-1].startsWith("市场") && note.split(",")[note.split(",").length-1].split(":").length>1){
				out.print(note.split(",")[note.split(",").length-1].split(":")[1]);
			}
		}
		
		
		%>--%>&nbsp;<%=turnToStr(innerMap.get("marketmanager")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("Income")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("inmoney")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("outmoney")) %></td>
		<td class="td_reportMd"><%=turnToNum(innerMap.get("tradeqty")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("fee")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("close_pl")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("todaybalance")) %></td>
		
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("runtimemargin")) %></td>
		<td class="td_reportMd"><%=turnToNum(innerMap.get("holdqty")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("floatingloss")) %></td>
		<td class="td_reportMd"><%=turnToNum2(innerMap.get("quanyi")) %></td>
		<td class="td_reportRd1"><%=turnToNum2(innerMap.get("yanqi")) %></td>
		
		
		</tr>
		 
	<%
		countIncome = countIncome.add(turnToNum(innerMap.get("Income")));
   		countnmoney = countnmoney.add(turnToNum(innerMap.get("inmoney")));
   		countoOutmoney = countoOutmoney.add(turnToNum(innerMap.get("outmoney")));
   		countradeqty = countradeqty.add(turnToNum(innerMap.get("tradeqty")));
   		countfee = countfee.add(turnToNum(innerMap.get("fee")));
   		countlose_pl = countlose_pl.add(turnToNum(innerMap.get("close_pl")));
   		counttodaybalance = counttodaybalance.add(turnToNum(innerMap.get("todaybalance")));
   		countruntimemargin = countruntimemargin.add(turnToNum(innerMap.get("runtimemargin")));
   		countholdqty = countholdqty.add(turnToNum(innerMap.get("holdqty")));
   		countfloatingloss = countfloatingloss.add(turnToNum(innerMap.get("floatingloss")));
   		countquanyi = countquanyi.add(turnToNum(innerMap.get("quanyi")));
   		countyYanqi = countyYanqi.add(turnToNum(innerMap.get("yanqi")));
	}
	
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="3"><b>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countIncome %></b></td>
	<td class="td_reportMd1" style="text-align: center;"><b>&nbsp;<fmt:formatNumber value='<%=countnmoney %>' pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" style="text-align: center;"><b>&nbsp;<fmt:formatNumber value='<%=countoOutmoney %>' pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" style="text-align: center;"><b>&nbsp;<%=countradeqty %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countfee %></b></td>
	<td class="td_reportMd1" style="text-align: center;"><b>&nbsp;<fmt:formatNumber value='<%=countlose_pl %>' pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=counttodaybalance %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countruntimemargin %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countholdqty %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countfloatingloss %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countquanyi %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countyYanqi %></b></td>
	</tr> 
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="15">
			无符合条件信息。
		</td>
	</tr>
	<%
	}
	%>
		 
	 
	</table>
	</div>
</table>
 
	</table>
</body>
</html>