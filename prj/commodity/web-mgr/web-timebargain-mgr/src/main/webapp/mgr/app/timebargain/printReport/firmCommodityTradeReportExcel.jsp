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
<%
	//get query condition and handle
	String startFirmID = request.getParameter("startFirmID");
    String endFirmID = request.getParameter("endFirmID");
    String startCommodity = request.getParameter("startCommodityID");
    String endCommodity = request.getParameter("endCommodityID");
    String brokerID = request.getParameter("brokerID");
    
	String beginDate = request.getParameter("startClearDate");
	String endDate = request.getParameter("endClearDate");
	String breedId=request.getParameter("breedId");
	String cateGoryId=request.getParameter("cateGoryId");
	String type = request.getParameter("type");
	pageContext.setAttribute("type",type);
	
	String table = "";
	String filter = "";
	if("d".equals(type)){
		table = " t_trade ";
		
	}else if("h".equals(type)){
		table = " t_h_trade ";
		filter =  " and clearDate >= to_date('" + beginDate + "','yyyy-MM-dd') " +
	              " and clearDate <= to_date('" + endDate + "','yyyy-MM-dd') ";
	}
	
	String broker = "";
	if(brokerID != null && !"".equals(brokerID) && !brokerID.equals("null")){
		broker = " and brokerid = '" + brokerID + "'";
	}
	
	//query data
	String sql = "select * from ( " +
		  "select (select brokerid from BR_FirmAndBroker where firmid = x1.firmid) brokerid, " +
	        "x1.firmid firmid, " +
	        "(select name from m_firm where firmid = x1.firmid) firmName, " +
	        "x1.commodityID commodityID, " +
	        "nvl(x2.sumBuyAgreeQuantity, 0) sumBuyAgreeQuantity, " +
	        "nvl(x2.sumBuyAgreeAvg, 0) sumBuyAgreeAvg, " +
	        "nvl(x3.sumBuyTransferQuantity, 0) sumBuyTransferQuantity, " +
	        "nvl(x3.sumBuyTransferAvg, 0) sumBuyTransferAvg, " +
	        "nvl(x1.sumBuyTradeFee, 0) sumBuyTradeFee, " +
	        "nvl(x4.sumSellAgreeQuantity, 0) sumSellAgreeQuantity, " +
	        "nvl(x4.sumSellAgreeAvg, 0) sumSellAgreeAvg, " +
	        "nvl(x5.sumSellTransferQuantity, 0) sumSellTransferQuantity, " +
	        "nvl(x5.sumSellTransferAvg, 0) sumSellTransferAvg, " +
	        "nvl(x1.sumSellTradeFee, 0) sumSellTradeFee, " +      
	        "nvl(x1.sumQuantity, 0) sumQuantity, " + 
	        "nvl(x1.sumTradeFee, 0) sumTradeFee " +
	  "from " +

	 "(select firmid, " +
	        "commodityID, " +
	        "nvl(sum(Quantity), 0) sumQuantity, " +
	        "nvl(sum(TradeFee), 0) sumTradeFee, " +
	        "nvl(sum(case BS_Flag when 1 then TradeFee end), 0) sumBuyTradeFee, " +
	        "nvl(sum(case BS_Flag when 2 then TradeFee end), 0) sumSellTradeFee " +
	 "from " + table +
	 "where 1=1 " + filter ;
	 if(startCommodity !=null && !startCommodity.equals("null") && endCommodity!=null && !endCommodity.equals("null")){
		 sql+="and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	 }
	 if(breedId != null && !breedId.equals("null")){
		 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
	 }
	 if(cateGoryId !=null && !"null".equals(cateGoryId)){
		 sql+="and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	 }
	 sql+="group by firmid, commodityID) x1, " +
	  
	 "(select firmid, " +
	        "CommodityID, " +
	        "nvl(sum(quantity), 0) sumBuyAgreeQuantity, " +
	        "nvl(sum(price * quantity) / sum(quantity), 0) sumBuyAgreeAvg " +
	 "from " + table +
	 "where BS_Flag = 1 and OrderType = 1 " + filter ;
	 if(startCommodity !=null && !startCommodity.equals("null") && endCommodity!=null && !endCommodity.equals("null")){
		 sql+="and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	 }
	 if(breedId != null && !breedId.equals("null")){
		 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
	 }
	 if(cateGoryId !=null && !"null".equals(cateGoryId)){
		 sql+="and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	 }
	sql+= "group by firmid,commodityID) x2, " +
	  
	 "(select firmid, " +
	        "CommodityID, " +
	        "nvl(sum(quantity), 0) sumBuyTransferQuantity, " +
	        "nvl(sum(price * quantity) / sum(quantity), 0) sumBuyTransferAvg " +
	 "from " + table +
	 "where BS_Flag = 1 and OrderType = 2 " + filter ;
	 if(startCommodity !=null && !startCommodity.equals("null") && endCommodity!=null && !endCommodity.equals("null")){
		 sql+="and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	 }
	 if(breedId != null && !breedId.equals("null")){
		 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
	 }
	 if(cateGoryId !=null && !"null".equals(cateGoryId)){
		 sql+="and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	 }
	 sql+="group by firmid,commodityID) x3, " +
	 
	 "(select firmid, " +
	        "CommodityID, " +
	        "nvl(sum(quantity), 0) sumSellAgreeQuantity, " +
	        "nvl(sum(price * quantity) / sum(quantity), 0) sumSellAgreeAvg " +
	 "from " + table +
	 "where BS_Flag = 2 and OrderType = 1 " + filter ;
	 if(startCommodity !=null && !startCommodity.equals("null") && endCommodity!=null && !endCommodity.equals("null")){
		 sql+="and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	 }
	 if(breedId != null && !breedId.equals("null")){
		 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
	 }
	 if(cateGoryId !=null && !"null".equals(cateGoryId)){
		 sql+="and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	 }
	 sql+="group by firmid,commodityID) x4, " +
	 
	 "(select firmid, " +
	        "CommodityID, " +
	        "nvl(sum(quantity), 0) sumSellTransferQuantity, " +
	        "nvl(sum(price * quantity) / sum(quantity), 0) sumSellTransferAvg " +
	"from " + table +
	 "where BS_Flag = 2 and OrderType = 2 " + filter ;
	 if(startCommodity !=null && !startCommodity.equals("null") && endCommodity!=null && !endCommodity.equals("null")){
		 sql+="and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	 }
	 if(breedId != null && !breedId.equals("null")){
		 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
	 }
	 if(cateGoryId !=null && !"null".equals(cateGoryId)){
		 sql+="and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	 }
	 sql+="group by firmid,commodityID) x5 " +
	 
  "where 1=1 ";
	
	if(startFirmID !=null && !startFirmID.equals("null") && endFirmID !=null && !endFirmID.equals("null")){
	sql+="and nls_upper(x1.firmid) >= nls_upper('" + startFirmID + "') and nls_upper(x1.firmid) <= nls_upper('" + endFirmID + "') " ;
	}
	 sql+="and x1.firmid = x2.firmid(+) and x1.firmid = x3.firmid(+) and x1.firmid = x4.firmid(+) and x1.firmid = x5.firmid(+) " +
	 "and x1.commodityID = x2.commodityID(+)  and x1.commodityID = x3.commodityID(+) " + 
	 "and x1.commodityID = x4.commodityID(+) and x1.commodityID = x5.commodityID(+) " +
	 "order by nls_upper(x1.commodityID) " +
	") " +
"where 1=1 " + broker;
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    if("".equals(broker)){
    	brokerID = "全部";
    }
    
    %>
    <br><center class="reportHead">交易商分商品成交表</center><br><br>
	<table align="center" width="100%">
	<tr><td colspan="16">&nbsp;</td></tr>
	<tr>
	    <td colspan="9" width="20%">&nbsp;</td>
		<td  class="reportRight">
		<%if(!("null".equals(startFirmID) || "null".equals(endFirmID))){ %>
		起始交易商:<%=startFirmID%>&nbsp;结束交易商:&nbsp;<%=endFirmID%>&nbsp;
		<%} %>
		</td>
		<td  class="reportRight">
		<%if(!("null".equals(startCommodity) || "null".equals(endCommodity))){ %>
		<td  class="reportRight">起始商品:<%=startCommodity %>&nbsp;结束商品:<%=endCommodity %></td>
		<%} %>
		</td>
		<td  class="reportRight">
		<%if(!"null".equals(brokerID)){ %>
		加盟商:&nbsp;<%=brokerID %>
		<%} %>
		</td>
		<c:if test="${type == 'h'}">
		  <td  class="reportRight">开始日期:<%=beginDate %>&nbsp;结束日期:<%=endDate %></td>
		</c:if>
		
	</tr>
	</table>
	<table align="center" class="reportTemp" width="1300px">
	<tr>
	  <td class="td_reportMdHead">会员编号</td>
	  <td class="td_reportMdHead">交易商编号</td>
	  <td class="td_reportMdHead">交易商名称</td>
	  <td class="td_reportMdHead">商品名称</td>
	  <td class="td_reportMdHead">买订货量</td>
	  <td class="td_reportMdHead">买均价</td>
	  <td class="td_reportMdHead">转买量</td>
	  <td class="td_reportMdHead">转买均价</td>
	  <td class="td_reportMdHead">买手续费</td>
	  <td class="td_reportMdHead">卖订货量</td>
	  <td class="td_reportMdHead">卖均价</td>
	  <td class="td_reportMdHead">转卖量</td>
	  <td class="td_reportMdHead">转卖均价</td>
	  <td class="td_reportMdHead">卖手续费</td>
	  <td class="td_reportMdHead">总成交量</td>
	  <td class="td_reportRdHead">总手续费</td>
	</tr>
   <% 
    	BigDecimal totalSumBuyAgreeQuantity=new BigDecimal("0");
    	BigDecimal totalSumBuyTransferQuantity=new BigDecimal("0");
    	BigDecimal totalSumBuyTradeFee=new BigDecimal("0");
    	BigDecimal totalSumSellAgreeQuantity=new BigDecimal("0");
    	
    	BigDecimal totalSumSellTransferQuantity =new BigDecimal("0");
    	BigDecimal totalSumSellTradeFee=new BigDecimal("0");
    	BigDecimal totalSumQuantity = new BigDecimal("0"); 
    	BigDecimal totalSumTradeFee = new BigDecimal("0");
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
    		totalSumBuyAgreeQuantity=totalSumBuyAgreeQuantity.add(turnToNum(innerMap.get("sumBuyAgreeQuantity")));
    		totalSumBuyTransferQuantity=totalSumBuyTransferQuantity.add(turnToNum(innerMap.get("sumBuyTransferQuantity")));
    		totalSumBuyTradeFee=totalSumBuyTradeFee.add(turnToNum(innerMap.get("sumBuyTradeFee")));
    		totalSumSellAgreeQuantity=totalSumSellAgreeQuantity.add(turnToNum(innerMap.get("sumSellAgreeQuantity")));
    		totalSumSellTransferQuantity=totalSumSellTransferQuantity.add(turnToNum(innerMap.get("sumSellTransferQuantity")));
    		totalSumSellTradeFee=totalSumSellTradeFee.add(turnToNum(innerMap.get("sumSellTradeFee")));
    		totalSumQuantity=totalSumQuantity.add(turnToNum(innerMap.get("sumQuantity")));
    		totalSumTradeFee=totalSumTradeFee.add(turnToNum(innerMap.get("sumTradeFee")));
		%> 	
	<tr>
	  <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("brokerid")) %></td>
	  <td class="td_reportMd">&nbsp;<%=innerMap.get("firmid") %></td>
	  <td class="td_reportMd">&nbsp;<%=innerMap.get("firmName") %></td>
	  <td class="td_reportMd">&nbsp;<%=innerMap.get("commodityID") %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumBuyAgreeQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumBuyAgreeAvg")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumBuyTransferQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumBuyTransferAvg")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumBuyTradeFee")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumSellAgreeQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumSellAgreeAvg")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumSellTransferQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumSellTransferAvg")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumSellTradeFee")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumTradeFee")) %></td>
	</tr>

	<%
		}
	%>
	<tr>
	  <td class="td_reportMd">&nbsp;合&nbsp;计</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumBuyAgreeQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumBuyTransferQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumBuyTradeFee %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumSellAgreeQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumSellTransferQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumSellTradeFee %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumTradeFee %>" pattern="#,##0.00"/></td>
	</tr>
</table>
</body>
</html>