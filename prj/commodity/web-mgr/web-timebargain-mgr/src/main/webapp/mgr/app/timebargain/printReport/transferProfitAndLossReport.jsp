<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp"%>

<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>转让盈亏明细</title>
</head>
<body>
	<table align="center" width="600px" border="0">
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
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
<%
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-02
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
	String brokerId=request.getParameter("brokerId");
	
	String filter = " 1=1 ";
	String filter_m = " 1=1 ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null")){
			filter += " and t.firmid>='"+startFirmID +"'";
			filter_m += " and m.firmid>='"+startFirmID +"'";
		}
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null")){
			filter += " and t.firmid<='"+endFirmID +"'";
			filter_m += " and m.firmid<='"+endFirmID +"'";
		}
	}
	if(chcekNull(firmID)){
		filter += " and t.FirmID='"+firmID +"'";
		filter_m += " and m.FirmID='"+firmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
			filter_m+=" and m.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
			
		}
	}
	//query data
	//String sql = "select firmId from t_h_trade t where "+filter+" and t.ordertype=2 group by firmId order by firmId";
	String sql = " select m.firmId,m.name from m_firm m where "+filter_m+" order by firmId";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    for(int a = 0 ; a < list.size() ; a ++){
    	Map firmIDMap = (Map)list.get(a);
    	String getFirmId = (String)firmIDMap.get("firmId");
    	String innerSql = " select customerid,commodityid, "+
    	" (case when bs_flag=1 then '买进' else '卖出' end)||(case when ordertype=1 then '订立' else '转让' end) type, "+
		" quantity,price,holdprice,to_char(holdtime,'yyyy-MM-dd') holdtime,close_pl, "+
		" (case when to_char(TradeAtClearDate,'yyyy-MM-dd')=to_char(AtClearDate,'yyyy-MM-dd') then quantity else 0 end) ding from "+
		" (select t.customerid,t.commodityid,t.bs_flag,t.ordertype,t.quantity,t.price,t.holdprice,t.holdtime,t.close_pl,t.tradetime,t.atcleardate,t.tradeatcleardate "+
		" from t_h_trade t where t.ordertype=2 and t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+getFirmId +"' order by customerid,commodityid )";
    
   	BigDecimal sumQuantity = new BigDecimal(0);
   	BigDecimal sumClose_pl = new BigDecimal("0.00");
   	BigDecimal sumDing = new BigDecimal(0);
   	String brokerSql="select brokerId from BR_FirmAndBroker where firmId='"+getFirmId+"'";
	List brokerList=dao.queryBySQL(brokerSql);
	String brokerId2=null;
	if(brokerList.size()>0){
		Map broker=(Map)brokerList.get(0);
		brokerId2=(String)broker.get("brokerId");
	}
		%>
		<br><center class="reportHead">转让盈亏明细表</center><br><br>
	<table align="center" width="700px" border="0">
	<tr>
		<td class="reportLeft">
			交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %>&nbsp;&nbsp;
			交易商名称:&nbsp;<%=turnToStr(firmIDMap.get("name")) %>
			<c:if test="<%=brokerId2 != null%>">
		加盟商:&nbsp;<%=brokerId2%></c:if>
		</td>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" height="100px" width="700px">
	<tr>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">交易类型</td>
	<td class="td_reportMdHead">数量(批)</td>
	<td class="td_reportMdHead">转让价格</td>
	<td class="td_reportMdHead">订立价格</td>
	<td class="td_reportMdHead">订立日期</td>
	<td class="td_reportMdHead">转让盈亏</td>
	<td class="td_reportRdHead">当订当转量</td>
	</tr>
		<%
    	List innerlist=dao.queryBySQL(innerSql);
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("holdprice")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("holdtime")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("close_pl")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("ding")) %></td>
	</tr>
	<%
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("quantity")));
		sumClose_pl = sumClose_pl.add(turnToNum(innerMap.get("close_pl")));
		sumDing = sumDing.add(turnToNum(innerMap.get("ding")));
	}
	if(innerlist.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumClose_pl %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=sumDing %></b></td>
	</tr>
<%}else{%>
	<tr>
			<td class="td_reportRd" colspan="9">
				无符合条件信息。
			</td>
		</tr>
	<%}%>
	</table>
<br>
<br>
<br>
<br>
<br>
<br>
	<%
	}
	%>
					  </td>
					 </tr>
					 <tr><td></td></tr>
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
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>