<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>加盟商资金报表</title>
</head>
<body>
	<table align="center"  border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="打印">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
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
	String startBrokerID = request.getParameter("startBrokerID");
    String endBrokerID = request.getParameter("endBrokerID");
	String beginDate = request.getParameter("startClearDate");
	String endDate = request.getParameter("endClearDate");
	System.out.println(startBrokerID);
	System.out.println(endBrokerID);
	
	//query data
	String sql = "select b.brokerid brokerid, " +
	                   "(select name from br_broker where brokerid = b.brokerid) brokerName, " +
	                   "nvl(sum(x2.balance), 0) balance, " +
	                   "nvl(sum(x2.relRight), 0) relRight, " +
	                   "nvl(sum(x1.lastbalance), 0) lastbalance, " +
	                   "nvl(sum(x3.deposit), 0) deposit, " +
	                   "nvl(sum(x3.fetch), 0) fetch, " +
	                   "nvl(sum(x4.tradeFee),0) tradeFee, " +
	                   "nvl(sum(x4.settleFee), 0) settleFee, " +
	                   "nvl(sum(x4.tradePL), 0) tradePL, " +
	                   "nvl(sum(x5.quantity), 0) quantity " +
	             "from " +

	                "(select brokerid,firmid from br_firmandbroker " +
	                 "where nls_upper(brokerid) >= nls_upper('" + startBrokerID  + "') and nls_upper(brokerid) <= nls_upper('" + endBrokerID + "') " +
	                ") b, " +
	  
	                "(select c.firmid firmid, r.lastbalance lastbalance   " +
	                 "from  " +
	                   "(select firmid, min(b_date) mindate " +
	       "from f_firmbalance " +
	       "where b_date >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	          "and b_date <= to_date('" + endDate + "','yyyy-MM-dd') " +
	       "group by firmid  " +
	      ") c,f_firmbalance r " + 
	    "where c.mindate = r.b_date and c.firmid=r.firmid) x1, " +
	    
	    "(select b.firmid, b.balance, b.relRight " +
	     "from " +
	       "(select c.firmid firmid,r.todayBalance balance, (r.todayBalance + (f.RuntimeMargin - f.RuntimeAssure) + f.runtimesettlemargin ) relRight " +
	       " from  " +
	          "(select firmid, max(b_date) maxdate " +
	           "from f_firmbalance " +
	          " where b_date >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	              "and b_date <= to_date('" + endDate + "','yyyy-MM-dd') " +
	           "group by firmid " + 
	          ") c,f_firmbalance r, T_H_Firm f " +
	        "where c.maxdate = r.b_date and c.firmid=r.firmid " +
	            "and c.maxdate = f.cleardate and c.firmid= f.firmid) b) x2, " +

	  "(select firmid, " +
	         " nvl(sum(case oprcode when '11001' then amount when '11003' then amount else 0 end),0) deposit, " +
	          "nvl(sum(case oprcode when '11002' then amount when '11004' then amount else 0 end),0) fetch " +
	   "from f_h_fundflow " + 
	   "where b_date >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	        "and b_date <= to_date('" + endDate + "','yyyy-MM-dd') " +
	   "group by firmid " +
	    ") x3, " +
	    
	  "(select firmid, " +
	         "nvl(sum(case code when 'TradeFee_T' then value else 0 end),0) tradeFee, " + 
	        " nvl(sum(case code when 'SettleFee_T' then value else 0 end),0) settleFee,  " +
	         "nvl(sum(case code when 'TradePL_T' then value else 0 end),0) tradePL " +
	   "from f_clientledger " +
	   "where code in (select code from f_ledgerfield where name in('订单交易手续费','订单交收手续费','订单转让盈亏')) " +
	     "and b_date >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	     "and b_date <= to_date('" + endDate + "','yyyy-MM-dd') " +
	   "group by firmid) x4, " +
	   
	   "(select firmid, " +
                "nvl(sum(Quantity), 0) quantity " +
         "from t_h_trade " +
         "where  clearDate >= to_date('" + beginDate + "','yyyy-MM-dd') " +
            "and clearDate <= to_date('" + endDate + "','yyyy-MM-dd') " +
         "group by firmid) x5 " +

	"where b.firmid = x1.firmid(+) and b.firmid = x2.firmid(+) and b.firmid = x3.firmid(+) and b.firmid = x4.firmid(+) and b.firmid = x5.firmid(+) " +
	"group by b.brokerid";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">加盟商资金报表</center><br><br>
	<table align="center" width="100%">
	<tr><td colspan="10">&nbsp;</td></tr>
	<tr>
	    <td colspan="8" width="20%">&nbsp;</td>
		<td  class="reportRight">起始加盟商:<%=startBrokerID %>&nbsp;结束加盟商:<%=endBrokerID %></td>
		&nbsp;&nbsp;
		<td  class="reportRight">开始日期:<%=beginDate %>&nbsp;结束日期:<%=endDate %></td>
		
	</tr>
	</table>
	<table align="center" class="reportTemp" width="1000px">
	<tr>
	  <td class="td_reportMdHead">会员编号</td>
	  <td class="td_reportMdHead">会员名称</td>
	  <td class="td_reportMdHead">期初余额</td>
	  <td class="td_reportMdHead">期末余额</td>
	  <td class="td_reportMdHead">总权益</td>
	  <td class="td_reportMdHead">入金</td>
	  <td class="td_reportMdHead">出金</td>
	  <td class="td_reportMdHead">交易手续费</td>
	  <%-- <td class="td_reportMdHead">交收手续费</td>--%>
	  <td class="td_reportMdHead">转让盈亏</td>
	  <td class="td_reportRdHead">成交量</td>
	</tr>
    <% 
    	BigDecimal totalBalance =new BigDecimal("0");
    	BigDecimal totalRelRight = new BigDecimal("0");
    	BigDecimal totalLastBalance =new BigDecimal("0");
    	BigDecimal totalDeposit = new BigDecimal("0");
    	BigDecimal totalFetch =new BigDecimal("0");
    	BigDecimal totalTradeFee =new BigDecimal("0");
    	//BigDecimal totalSettleFee = new BigDecimal("0");
    	BigDecimal totalTradePL = new BigDecimal("0");
    	BigDecimal totalQuantity=new BigDecimal("0");
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
			totalBalance=totalBalance.add(turnToNum(innerMap.get("balance")));
			totalRelRight=totalRelRight.add(turnToNum(innerMap.get("relRight")));
			totalLastBalance=totalLastBalance.add(turnToNum(innerMap.get("lastBalance")));
			totalDeposit=totalDeposit.add(turnToNum(innerMap.get("deposit")));
			totalFetch=totalFetch.add(turnToNum(innerMap.get("fetch")));
			totalTradeFee=totalTradeFee.add(turnToNum(innerMap.get("tradeFee")));
			//totalSettleFee=totalSettleFee.add(turnToNum(innerMap.get("settleFee")));
			totalTradePL=totalTradePL.add(turnToNum(innerMap.get("tradePL")));
			totalQuantity=totalQuantity.add(turnToNum(innerMap.get("quantity")));
		%> 	
	<tr>
	  <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("brokerid")) %></td>
	  <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("brokerName")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("lastbalance")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("balance")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("relRight")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("deposit")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("fetch")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("tradeFee")) %></td>
	  <%--<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("settleFee")) %></td>--%>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("tradePL")) %></td>
	  <td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	</tr>

	<%
		}
	%>
	<tr>
	  <td class="td_reportMdHead">&nbsp;合&nbsp;计</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalLastBalance %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalBalance %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalRelRight %>" pattern="#,##0.00"/></td>  
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalDeposit %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalFetch %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalTradeFee %>" pattern="#,##0.00"/></td>
	  <%--<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSettleFee %>" pattern="#,##0.00"/></td>--%>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalTradePL %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd">&nbsp;<fmt:formatNumber value="<%=totalQuantity %>" pattern="#,###"/></td>
	</tr>
</table>
</div>
<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="打印"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>