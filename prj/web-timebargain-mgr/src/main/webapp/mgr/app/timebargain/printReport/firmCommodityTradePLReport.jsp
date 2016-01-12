<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>交易商分商品盈亏表</title>
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
	String startFirmID = request.getParameter("startFirmID");
    String endFirmID = request.getParameter("endFirmID");
    String startCommodity = request.getParameter("startCommodityID");
    String endCommodity = request.getParameter("endCommodityID");
    String brokerID = request.getParameter("brokerID");
    String breedId=request.getParameter("breedId");
    
	String beginDate = request.getParameter("startClearDate");
	String endDate = request.getParameter("endClearDate");
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
	if(brokerID != null && !"".equals(brokerID)&& !brokerID.equals("null")){
		broker = " and brokerid = '" + brokerID + "'";
	}
	
	//query data
	String sql = "select * from ( " +
		  "select (select brokerid from BR_FirmAndBroker where firmid = x1.firmid) brokerid, " +
	        "x1.firmid firmid, " +
	        "(select name from m_firm where firmid = x1.firmid) firmName, " +
	        "x1.commodityID commodityID, " +
	        "nvl(x2.sumBuyTransferQuantity, 0) sumBuyTransferQuantity, " +
	        "nvl(x2.sumBuyTransferAvg, 0) sumBuyTransferAvg, " +
	        "nvl(x2.sumBuyTransferClosePL, 0) sumBuyTransferClosePL, " +
	        "nvl(x3.sumSellTransferQuantity, 0) sumSellTransferQuantity, " +
	        "nvl(x3.sumSellTransferAvg, 0) sumSellTransferAvg, " +
	        "nvl(x3.sumSellTransferClosePL, 0) sumSellTransferClosePL, " +      
	        "nvl(x1.sumQuantity, 0) sumQuantity,  " +
	        "nvl(x1.sumClosePL, 0) sumClosePL " +
	  "from " +

	 "(select firmid, " +
	        "commodityID, " +
	        "nvl(sum(Close_PL), 0) sumClosePL, " +
	        "nvl(sum(case BS_Flag when 1 then quantity else -quantity end), 0) sumQuantity " +
	 "from " + table +
	 "where OrderType = 2 " + filter ;
	 if(startCommodity != null && !startCommodity.equals("null") && endCommodity !=null && !endCommodity.equals("null")){
		   sql+=  "and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
		     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
		  }
		     if(breedId != null && !breedId.equals("null")){
				 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
			 }
	 sql+="group by firmid,CommodityID) x1, " +
	  
	 "(select firmid, " +
	        "commodityID, " +
	        "nvl(sum(quantity), 0) sumBuyTransferQuantity, " +
	        "nvl(sum(price * quantity) / sum(quantity), 0) sumBuyTransferAvg, " +
	        "nvl(sum(Close_PL), 0) sumBuyTransferClosePL " +
	 "from " + table +
	 "where BS_Flag = 1 and OrderType = 2 " + filter ;
	 if(startCommodity != null && !startCommodity.equals("null")&& endCommodity != null && !endCommodity.equals("null")){
		 sql+="and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	 }
	 if(breedId != null && !breedId.equals("null")){
		 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
	 }
	 sql+="group by firmid,CommodityID) x2, " +
	 
	  "(select firmid, " +
	        "commodityID, " +
	        "nvl(sum(quantity), 0) sumSellTransferQuantity, " +
	        "nvl(sum(price * quantity) / sum(quantity), 0) sumSellTransferAvg, " +
	        "nvl(sum(Close_PL), 0) sumSellTransferClosePL " +
	 "from " + table +
	 "where BS_Flag = 2 and OrderType = 2 " + filter ;
	  if(startCommodity != null && !startCommodity.equals("null") && endCommodity !=null && !endCommodity.equals("null")){
	   sql+=  "and nls_upper(commodityID) >= nls_upper('" + startCommodity + "') " + 
	     "and nls_upper(commodityID) <= nls_upper('" + endCommodity + "') " ;
	  }
	     if(breedId != null && !breedId.equals("null")){
			 sql+="and nls_upper(commodityID) in (select commodityId from t_commodity where breedId='"+breedId+"') ";
		 }
	 sql+="group by firmid,commodityID) x3 where 1=1 " ;
	if(startFirmID != null && !startFirmID.equals("null") && endFirmID !=null && !endFirmID.equals("null")){
		sql+="and nls_upper(x1.firmid) >= nls_upper('" + startFirmID + "') and nls_upper(x1.firmid) <= nls_upper('" + endFirmID + "') " ;
	}
	sql+=
	  "and x1.firmid = x2.firmid(+) and x1.firmid = x3.firmid(+) " +
	  "and x1.commodityID = x2.commodityID(+)  and x1.commodityID = x3.commodityID(+) " + 
	"order by  nls_upper(x1.commodityID) " + 
	") " +
"where 1=1 " + broker;

	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    if("".equals(broker)){
    	brokerID = "全部";
    }
    	
    %>
    <br><center class="reportHead">交易商分商品盈亏表</center><br><br>
	<table align="center" width="100%">
	<tr><td colspan="12">&nbsp;</td></tr>
	<tr>
		
		<td colspan="5" width="20%">&nbsp;</td>
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
	<table align="center" class="reportTemp" width="1100px">
	<tr>
	  <td class="td_reportMdHead">会员编号</td>
	  <td class="td_reportMdHead">交易商编号</td>
	  <td class="td_reportMdHead">交易商名称</td>
	  <td class="td_reportMdHead">商品名称</td>
	  <td class="td_reportMdHead">转买量</td>
	  <td class="td_reportMdHead">转买均价</td>
	  <td class="td_reportMdHead">转买盈亏</td>
	  <td class="td_reportMdHead">转卖量</td>
	  <td class="td_reportMdHead">转卖均价</td>
	  <td class="td_reportMdHead">转卖盈亏</td>
	  <td class="td_reportMdHead">净转让量</td>
	  <td class="td_reportRdHead">转让盈亏</td>
	</tr>
<% 
    	BigDecimal totalSumBuyTransferQuantity=new BigDecimal("0");
    	BigDecimal totalSumBuyTransferClosePL=new BigDecimal("0");
    	BigDecimal totalSumSellTransferQuantity=new BigDecimal("0");
    	BigDecimal totalSumSellTransferClosePL=new BigDecimal("0");
    	BigDecimal totalSumQuantity=new BigDecimal("0");
    	BigDecimal totalSumClosePL=new BigDecimal("0");
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
    		totalSumBuyTransferQuantity=totalSumBuyTransferQuantity.add(turnToNum(innerMap.get("sumBuyTransferQuantity")));
    		totalSumBuyTransferClosePL=totalSumBuyTransferClosePL.add(turnToNum(innerMap.get("sumBuyTransferClosePL")));
    		totalSumSellTransferQuantity=totalSumSellTransferQuantity.add(turnToNum(innerMap.get("sumSellTransferQuantity")));
    		totalSumSellTransferClosePL=totalSumSellTransferClosePL.add(turnToNum(innerMap.get("sumSellTransferClosePL")));
    		totalSumQuantity=totalSumQuantity.add(turnToNum(innerMap.get("sumQuantity")));
    		totalSumClosePL=totalSumClosePL.add(turnToNum(innerMap.get("sumClosePL")));
		%> 	
	<tr>
	  <td class="td_reportMd">&nbsp;<%if(innerMap.get("brokerid") != null){ %>
		<%=innerMap.get("brokerid") %>
		<%} %>
	  </td>
	  <td class="td_reportMd">&nbsp;<%=innerMap.get("firmid") %></td>
	  <td class="td_reportMd">&nbsp;<%=innerMap.get("firmName") %></td>
	  <td class="td_reportMd">&nbsp;<%=innerMap.get("commodityID") %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumBuyTransferQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumBuyTransferAvg")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumBuyTransferClosePL")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumSellTransferQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumSellTransferAvg")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumSellTransferClosePL")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumClosePL")) %></td>
	</tr>

	<%
		
		}
	%>
	<tr>
	  <td class="td_reportMd">&nbsp;合&nbsp;计</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumBuyTransferQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumBuyTransferClosePL %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumSellTransferQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;</td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumSellTransferClosePL %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumQuantity %>" pattern="#,###"/></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=totalSumClosePL %>" pattern="#,##0.00"/></td>
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