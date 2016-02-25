<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
try{
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-04
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String filter = "";
	if(chcekNull(startFirmID)){
		filter += " and b.brokerid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and b.brokerid<='"+endFirmID +"'";
	}
	
	if(chcekNull(startClearDate)){
		filter += " and a.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and a.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	//query data
	String sql = " select brokerid,name,marketmanager from m_b_broker where brokerid>='"+startFirmID +"' and brokerid<='"+endFirmID +"' order by brokerid";
    List list=dao.queryBySQL(sql);
    List breedList=dao.queryBySQL("select breedid,breedname from t_a_breed order by SortID, breedid");
    String sqlList="select b.brokerid,c.breedid, sum(a.Quantity) Quantity,sum(Close_PL) Close_PL,sum(TradeFee) TradeFee from t_h_trade a,M_B_FirmAndBroker b,(select commodityid,breedid from T_H_Commodity group by commodityid,breedid) c where a.firmid = b.firmid and a.commodityid=c.commodityid "+filter+" group by b.brokerid,c.breedid order by b.brokerid,c.breedid";
	List tradeList= dao.queryBySQL(sqlList); 
	String inoutSql="select a.brokerid,nvl(sum(b.inAmount),0) inAmount,nvl(sum(c.outAmount),0) outAmount from M_B_FirmAndBroker a,";
	inoutSql+=" (select FirmID,sum(Amount) inAmount from f_H_fundflow where oprcode in ('101','103') and B_Date>=to_date('"+startClearDate+"','yyyy-MM-dd') and B_Date<=to_date('"+endClearDate+"','yyyy-MM-dd') group by FirmID) b,";
	inoutSql+=" (select FirmID,sum(Amount) outAmount from f_H_fundflow where oprcode in ('102','104') and B_Date>=to_date('"+startClearDate+"','yyyy-MM-dd') and B_Date<=to_date('"+endClearDate+"','yyyy-MM-dd') group by FirmID) c";
	inoutSql+=" where a.firmid=b.firmid(+) and a.firmid=c.firmid(+) group by a.brokerid order by brokerid";
	List inoutList=dao.queryBySQL(inoutSql);
	String quanyiSql="select e.brokerid, sum(nvl(a.TodayBalance,0)+nvl(b.Margin,0)+nvl(b.SettleMargin,0)+nvl(c.floatingloss,0)) quanyi from ";
	quanyiSql+="(select firmid,nvl(TodayBalance,0) TodayBalance from F_FirmBalance where b_date=(select max(b_date) from F_FirmBalance where b_date <= to_date('"+endClearDate+"','yyyy-MM-dd'))) a,";
	quanyiSql+="(select firmid, nvl(RuntimeMargin-RuntimeAssure+RuntimeFL,0) Margin,nvl(RuntimeSettleMargin,0) SettleMargin from t_h_firm  where cleardate=(select max(cleardate) from t_h_firm where cleardate <= to_date('"+endClearDate+"','yyyy-MM-dd'))) b,";
	quanyiSql+="(select firmid, (nvl(sum(floatingloss),0)) floatingloss  from t_h_firmholdsum where cleardate=(select max(cleardate) from t_h_firmholdsum where cleardate <= to_date('"+endClearDate+"','yyyy-MM-dd')) group by firmid) c,";
	quanyiSql+=" M_B_FirmAndBroker e";
	quanyiSql+=" where e.firmid=a.firmid and e.firmid=b.firmid(+) and e.firmid=c.firmid(+)  group by e.brokerid";
	//System.out.println(quanyiSql);
	List quanyiList=dao.queryBySQL(quanyiSql);
	BigDecimal sumOpenqty = new BigDecimal(0);
	BigDecimal sumUseqty = new BigDecimal(0);
	//合计
	int countLo=0;
	double countPL=0.0;
	double countFee=0.0;
	double countInAmount=0.0;
	double countOutAmount=0.0;
	double countQuanyi=0.0;
	int[]tradeSumBybreeds=new int[breedList.size()];
		%>
	<br><center class="reportHead">分加盟商成交统计表</center><br><br>
	<table align="center" width="900px" border="0">
	<tr>
		
		<td class="reportRight" colspan="6">日期:&nbsp;<%=startClearDate %> &nbsp; 至&nbsp;<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="900px">
	<tr>
	<td class="td_reportMdHead">会员名称</td>
	<td class="td_reportMdHead">会员账号</td>
	<td class="td_reportMdHead">市场开发人员</td>
	<td class="td_reportMdHead">总批量</td>
	<td class="td_reportMdHead">手续费</td>
	<td class="td_reportMdHead">转让盈亏</td><% for(int i=0;i<breedList.size();i++){
		Map map = (Map)breedList.get(i);
		%><td class="td_reportMdHead">&nbsp;<%=turnToStr(map.get("breedname")) %></td>
	<% } %>
	<td class="td_reportMdHead">入金</td>
	<td class="td_reportMdHead">出金</td>
	<td class="td_reportRdHead">总权益</td>
	</tr><%
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
		%> 	
	<tr>
	<td class="td_reportMd" align="left">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
	<td class="td_reportMd"><%=turnToStr(innerMap.get("brokerid")) %></td>
	<td class="td_reportMd"><%--<%
		if(innerMap.get("note")!=null){
			String note=turnToStr(innerMap.get("note"));
			if(note.split(",").length>0 && note.split(",")[note.split(",").length-1].startsWith("市场") && note.split(",")[note.split(",").length-1].split(":").length>1){
				out.print(note.split(",")[note.split(",").length-1].split(":")[1]);
			}
		}
	%>--%>&nbsp;<%=turnToStr(innerMap.get("marketmanager")) %></td><%
	  int lo=0;
	  double pl=0;
	  double fee=0;
	 for(int k=0;k<tradeList.size();k++){
		 if(turnToStr(innerMap.get("brokerid")).equals( turnToStr(((Map)tradeList.get(k)).get("brokerid")))){
			 lo+=Integer.parseInt(((Map)tradeList.get(k)).get("Quantity")+"");
			 pl+=Double.parseDouble(((Map)tradeList.get(k)).get("Close_PL")+"");
			 fee+=Double.parseDouble(((Map)tradeList.get(k)).get("TradeFee")+"");
			 countLo+=Integer.parseInt(((Map)tradeList.get(k)).get("Quantity")+"");
			 countPL+=Double.parseDouble(((Map)tradeList.get(k)).get("Close_PL")+"");
			 countFee+=Double.parseDouble(((Map)tradeList.get(k)).get("TradeFee")+"");
		 }
	 }
	%>
	<td class="td_reportMd1"><fmt:formatNumber value="<%=lo %>" pattern="#,##0"/></td>
	<td class="td_reportMd1"><fmt:formatNumber value="<%=fee %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1"><fmt:formatNumber value="<%=pl %>" pattern="#,##0.00"/></td>
	<% for(int j=0;j<breedList.size();j++){
		   Map map = (Map)breedList.get(j);
		   int tradeSumBybreed=0;
		   double plsum=0;
			double feesum=0;
		   for(int k=0;k<tradeList.size();k++){
			   if( turnToStr(innerMap.get("brokerid")).equals(turnToStr(((Map)tradeList.get(k)).get("brokerid"))) && (map.get("breedid")+"").equals((((Map)tradeList.get(k)).get("breedid"))+"")){
				   tradeSumBybreed=Integer.parseInt(((Map)tradeList.get(k)).get("Quantity")+"");
				   tradeSumBybreeds[j]+=Integer.parseInt(((Map)tradeList.get(k)).get("Quantity")+"");
				   plsum=Double.parseDouble(((Map)tradeList.get(k)).get("Close_PL")+"");
				   feesum=Double.parseDouble(((Map)tradeList.get(k)).get("TradeFee")+"");
				  break;
			   }
		   }
		%><td class="td_reportMd1"><fmt:formatNumber value="<%=tradeSumBybreed %>" pattern="#,##0"/></td><% } %><%
		boolean b=false;
		for(int k=0;k<inoutList.size();k++){
			 if(turnToStr(innerMap.get("brokerid")).equals(turnToStr(((Map)inoutList.get(k)).get("brokerid")))){
					b=true;
				 %><td class="td_reportMd1"><%=((Map)inoutList.get(k)).get("inAmount")+"" %></td>
				 <td class="td_reportMd1"><%=((Map)inoutList.get(k)).get("outAmount")+"" %></td><%
				 countInAmount+=Double.parseDouble(((Map)inoutList.get(k)).get("inAmount")+"");
				 countOutAmount+=Double.parseDouble(((Map)inoutList.get(k)).get("outAmount")+"");
				 break;
			 }
		 }
		if(!b){
		%><td class="td_reportMd1">0</td>
		<td class="td_reportMd1">0</td><%
		
		}
		%><%
			boolean ba=false;
		for(int k=0;k<quanyiList.size();k++){
			 if(turnToStr(innerMap.get("brokerid")).equals(turnToStr(((Map)quanyiList.get(k)).get("brokerid")))){
				 ba=true;
				 %><td class="td_reportRd1"><%=((Map)quanyiList.get(k)).get("quanyi")+"" %></td><%
				 countQuanyi+=Double.parseDouble(((Map)quanyiList.get(k)).get("quanyi")+"");
				 break;
			 }
		 }
		if(!ba){
		%><td class="td_reportRd1">0</td><%
		
		}
		%></tr><% } %><tr>
			<td colspan="3" class="td_reportMd1" style="text-align:center;">合计</td>
			<td class="td_reportMd1"><fmt:formatNumber value="<%=countLo %>" pattern="#,##0"/></td>
			<td class="td_reportMd1"><fmt:formatNumber value="<%=countFee %>" pattern="#,##0.00"/></td>
			<td class="td_reportMd1"><fmt:formatNumber value="<%=countPL %>" pattern="#,##0.00"/></td><% 
				for(int m=0;m< breedList.size(); m++){
			%><td class="td_reportMd1"><fmt:formatNumber value="<%=tradeSumBybreeds[m] %>" pattern="#,##0"/></td><%} %>
			<td class="td_reportMd1"><%=countInAmount %></td>
			<td class="td_reportMd1"><%=countOutAmount %></td>
			<td class="td_reportRd1"><%=countQuanyi %></td>
		</tr>
		</table>
	<br><br><br><br><br><% }catch(Exception e){
		e.printStackTrace();
		
	} %>
	

	