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
		filter += " and t.b_date=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startClearDate)){
		filter += " and t.b_date>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.b_date<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}	
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
			filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	}
	%>
	<br><center class="reportHead">交易商入金出金记录表</center><br><br>
	<table align="center" width="600px" border="0">
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
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead" width="20%">交易商代码</td>
	<td class="td_reportMdHead" width="20%">日期</td>
	<td class="td_reportMdHead" width="15%">划入资金</td>
	<td class="td_reportMdHead" width="15%">划出资金</td>
	<td class="td_reportMdHead" width="15%">银转入金</td>
	<td class="td_reportRdHead" width="15%">银转出金</td>
	</tr>
	<%
	//query data
	String sql = " select t.firmid,to_char(t.b_date,'yyyy-MM-dd') b_date,sum(case when t.code='Fetch' then value else 0 end) outMoney,"+
	               "sum(case when t.code='Deposit' then value else 0 end) inMoney, sum(case when t.code='103' then value else 0 end) bankInMoney, sum(case when t.code = '104' then value else 0 end) bankOutMoney from f_clientledger t where (t.code='Fetch' or t.code='Deposit' or t.code = '103' or t.code = '104') and "+
				filter+" group by t.b_date,t.firmid";
		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	    List list=dao.queryBySQL(sql);
	    
	    BigDecimal countInMoney = new BigDecimal("0.00");//用于总计
		BigDecimal countOutMoney = new BigDecimal("0.00");
		BigDecimal countBankInMoney = new BigDecimal("0.00");
		BigDecimal countBankOutMoney = new BigDecimal("0.00");

		BigDecimal sumInMoney = new BigDecimal("0.00");//用于小计
		BigDecimal sumOutMoney = new BigDecimal("0.00");
		BigDecimal sumBankInMoney = new BigDecimal("0.00");
		BigDecimal sumBankOutMoney = new BigDecimal("0.00");
		
		String mark = null;//用于标记
		int marknum = 0;
		int size = list.size()-1;
	    for(int a = 0 ; a < list.size() ; a ++){
	    	Map innerMap = (Map)list.get(a);
	    	marknum = a;	    	

		if(!turnToStr(innerMap.get("firmId")).equals(mark)){
			if( mark != null){
	 %>
	<tr>
	<td class="td_reportMd" colspan="2" width="40%"><b>交易商小计</b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumInMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumOutMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumBankInMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumBankOutMoney %>" pattern="#,##0.00"/></b></td>
	</tr>
	 <%						
		 		sumInMoney = new BigDecimal("0.00");//用于小计
				sumOutMoney = new BigDecimal("0.00");
				sumBankInMoney = new BigDecimal("0.00");
				sumBankOutMoney = new BigDecimal("0.00");
	 		}
		mark = turnToStr(innerMap.get("firmId"));
		}		
	%>	
	<tr>
	<td class="td_reportMd" width="10%">&nbsp;<%=turnToStr(innerMap.get("firmId")) %></td>
	<td class="td_reportMd" width="10%">&nbsp;<%=turnToStr(innerMap.get("b_date")) %></td>
	<td class="td_reportMd1" width="15%">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("inMoney")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1" width="15%">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("outMoney")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1" width="15%">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("bankInMoney")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportRd1" width="15%">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("bankOutMoney")) %>" pattern="#,##0.00"/></td>
	</tr>
	<%
		sumInMoney = sumInMoney.add(turnToNum(innerMap.get("inMoney")));
		sumOutMoney = sumOutMoney.add(turnToNum(innerMap.get("outMoney")));
		sumBankInMoney = sumBankInMoney.add(turnToNum(innerMap.get("bankInMoney")));
		sumBankOutMoney = sumBankOutMoney.add(turnToNum(innerMap.get("sumBankOutMoney")));
		
		countInMoney = countInMoney.add(turnToNum(innerMap.get("inMoney")));
		countOutMoney = countOutMoney.add(turnToNum(innerMap.get("outMoney")));
		countBankInMoney = countBankInMoney.add(turnToNum(innerMap.get("bankInMoney")));
		countBankOutMoney = countBankOutMoney.add(turnToNum(innerMap.get("bankOutMoney")));
	}
	if(size == marknum){
	%>	
	<tr>
	<td class="td_reportMd" colspan="2" width="20%"><b>交易商小计</b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumInMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumOutMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumBankInMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=sumBankOutMoney %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="2" width="40%"><b>合计</b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=countInMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=countOutMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=countBankInMoney %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1" width="15%"><b>&nbsp;<fmt:formatNumber value="<%=countBankOutMoney %>" pattern="#,##0.00"/></b></td>
	</tr>
<%}else{%>
	<tr>
			<td class="td_reportRd" colspan="6">
				无符合条件信息。
			</td>
		</tr>
	<%}%>
	</table>