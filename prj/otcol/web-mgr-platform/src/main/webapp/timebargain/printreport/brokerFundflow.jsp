<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle
	String startBrokerID = request.getParameter("startBrokerID");
    String endBrokerID = request.getParameter("endBrokerID");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	
	//query data
	String sql = "select b.brokerid brokerid, " +
	                   "(select name from m_b_broker where brokerid = b.brokerid) brokerName, " +
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

	                "(select brokerid,firmid from m_b_firmandbroker " +
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
	       "(select c.firmid firmid,r.todayBalance balance, (r.todayBalance + (f.RuntimeMargin - f.RuntimeAssure) +  f.RuntimeFL + f.runtimesettlemargin + " +
               "(select nvl(sum(floatingloss),0) from T_H_FirmHoldSum where firmid=c.firmid and ClearDate = c.mindate)) relRight " +
	       " from  " +
	          "(select firmid, min(b_date) mindate " +
	           "from f_firmbalance " +
	          " where b_date >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	              "and b_date <= to_date('" + endDate + "','yyyy-MM-dd') " +
	           "group by firmid " + 
	          ") c,f_firmbalance r, T_H_Firm f " +
	        "where c.mindate = r.b_date and c.firmid=r.firmid " +
	            "and c.mindate = f.cleardate and c.firmid= f.firmid) b) x2, " +

	  "(select firmid, " +
	         " nvl(sum(case oprcode when '101' then amount when '103' then amount else 0 end),0) deposit, " +
	          "nvl(sum(case oprcode when '102' then amount when '104' then amount else 0 end),0) fetch " +
	   "from f_h_fundflow " + 
	   "where b_date >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	        "and b_date <= to_date('" + endDate + "','yyyy-MM-dd') " +
	   "group by firmid " +
	    ") x3, " +
	    
	  "(select firmid, " +
	         "nvl(sum(case code when 'TradeFee' then value else 0 end),0) tradeFee, " + 
	        " nvl(sum(case code when 'SettleFee' then value else 0 end),0) settleFee,  " +
	         "nvl(sum(case code when 'TradePL' then value else 0 end),0) tradePL " +
	   "from f_clientledger " +
	   "where code in (select code from f_ledgerfield where name in('交易手续费','交收手续费','转让盈亏')) " +
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
	   
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
    List list=dao.queryBySQL(sql);
    
    %><br><center class="reportHead">加盟商资金报表</center><br><br>
	<table align="center" width="100%">
	<tr><td colspan="11"></td></tr>
	<tr>
	    <td colspan="9" >&nbsp;</td>
		<td  class="reportRight">起始加盟商:<%=startBrokerID %>&nbsp;结束加盟商:<%=endBrokerID %></td>
		&nbsp;&nbsp;
		<td  class="reportRight">开始日期:<%=beginDate %>&nbsp;结束日期:<%=endDate %></td>
		
	</tr>
	</table>
	<table align="center" class="reportTemp" width="1200px">
	<tr>
	  <td class="td_reportMdHead">会员编号</td>
	  <td class="td_reportMdHead">会员名称</td>
	  <td class="td_reportMdHead">总可用</td>
	  <td class="td_reportMdHead">总权益</td>
	  <td class="td_reportMdHead">期初资金</td>
	  <td class="td_reportMdHead">入金</td>
	  <td class="td_reportMdHead">出金</td>
	  <td class="td_reportMdHead">交易手续费</td>
	  <td class="td_reportMdHead">交收手续费</td>
	  <td class="td_reportMdHead">转让盈亏</td>
	  <td class="td_reportMdHead">成交量</td>
	</tr> <% 
    	BigDecimal totalBalance =new BigDecimal("0");
    	BigDecimal totalRelRight = new BigDecimal("0");
    	BigDecimal totalLastBalance =new BigDecimal("0");
    	BigDecimal totalDeposit = new BigDecimal("0");
    	BigDecimal totalFetch =new BigDecimal("0");
    	BigDecimal totalTradeFee =new BigDecimal("0");
    	BigDecimal totalSettleFee = new BigDecimal("0");
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
			totalSettleFee=totalSettleFee.add(turnToNum(innerMap.get("settleFee")));
			totalTradePL=totalTradePL.add(turnToNum(innerMap.get("tradePL")));
			totalQuantity=totalQuantity.add(turnToNum(innerMap.get("quantity")));
		%><tr>
	  <td class="td_reportMd"><%=turnToStr(innerMap.get("brokerid")) %></td>
	  <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("brokerName")) %></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("balance")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("relRight")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("lastbalance")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("deposit")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("fetch")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("tradeFee")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("settleFee")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value="<%=turnToNum(innerMap.get("tradePL")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd"><fmt:formatNumber value="<%=turnToNum(innerMap.get("quantity")) %>" pattern="#,###"/></td>
	</tr><%
		}
	%><tr>
	  <td class="td_reportMd" colspan="2">&nbsp;合&nbsp;计</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalBalance %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalRelRight %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalLastBalance %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalDeposit %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalFetch %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalTradeFee %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSettleFee %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalTradePL %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd">&nbsp;<fmt:formatNumber value="<%=totalQuantity %>" pattern="#,###"/></td>
	</tr>
</table>