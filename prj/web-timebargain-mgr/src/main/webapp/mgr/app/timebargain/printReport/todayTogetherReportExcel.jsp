<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<%@ include file="/mgr/app/timebargain/printReport/excelExpFile.jsp"%>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/report.css" type="text/css"/>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:m="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
</head>
<body>
<table align="center">
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
<%
    String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
	String brokerId=request.getParameter("brokerId");
	String queryType = request.getParameter("queryType");//查询类型  1 全部 2 仅有资金余额  3 仅当日有资金变化

	String filter = " ";
	String filter1= " ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null")){
			filter += " and t.firmid>='"+startFirmID +"'";
			filter1 += " and t.firmid>='"+startFirmID +"'";
		}
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null")){

			filter += " and t.firmid<='"+endFirmID +"'";
			filter1 += " and t.firmid<='"+endFirmID +"'";
		}
	}
	if(chcekNull(firmID)){
		filter += " and t.FirmID='"+firmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
			filter1+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
		}
	}
	String sql ="select t.firmId,m.name from t_firm t,m_firm m where t.firmId=m.firmId "+filter1 +" order by t.firmid";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    for(int i=0;i<list.size();i++)
    {
         Map firmIDMap = (Map)list.get(i);
    	String firmId = (String)firmIDMap.get("firmId");
    	String name = (String)firmIDMap.get("name");

		//成交记录
		String innerSqlBargainResult = " select a_tradeno,customerid,commodityid, "+
    	" (case when bs_flag=1 then '买进' else '卖出' end)||(case when ordertype=1 then '订立' else '转让' end) type, "+
		" price,quantity,to_char(ordertime,'hh24:mi:ss') orderTime,to_char(tradetime,'hh24:mi:ss') tradetime, "+
		" tradefee from "+
		" (select t.a_tradeno,t.customerid,t.commodityid,t.bs_flag,t.ordertype,t.price,t.quantity,o.ordertime,t.tradetime,t.tradefee "+
		" from t_h_trade t,T_H_Orders o where o.a_orderno=t.a_orderno and t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+firmId +"' order by t.commodityid,a_tradeno )";
    	
	   	BigDecimal sumQuantityBargainResult = new BigDecimal(0);
	   	BigDecimal sumTradefeeBargainResult = new BigDecimal(0.0);

		//转让盈亏
		String innerSql = " select customerid,commodityid, "+
    	" (case when bs_flag=1 then '买进' else '卖出' end)||(case when ordertype=1 then '订立' else '转让' end) type, "+
		" quantity,price,holdprice,to_char(holdtime,'yyyy-MM-dd') holdtime,close_pl, "+
		" (case when to_char(TradeAtClearDate,'yyyy-MM-dd')=to_char(AtClearDate,'yyyy-MM-dd') then quantity else 0 end) ding from "+
		" (select t.customerid,t.commodityid,t.bs_flag,t.ordertype,t.quantity,t.price,t.holdprice,t.holdtime,t.close_pl,t.tradetime,t.atcleardate,t.tradeatcleardate "+
		" from t_h_trade t where t.ordertype=2 and t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+firmId +"' order by customerid,commodityid )";
    
		BigDecimal sumQuantity = new BigDecimal(0);
		BigDecimal sumClose_pl = new BigDecimal("0.00");
		BigDecimal sumDing = new BigDecimal(0);
		String brokerSql="select brokerId from BR_FirmAndBroker where firmId='"+firmId+"'";
		List brokerList=dao.queryBySQL(brokerSql);
		String brokerId2=null;
		if(brokerList.size()>0){
			Map broker=(Map)brokerList.get(0);
			brokerId2=(String)broker.get("brokerId");
		}


		//订货汇总
		String innerSqlIndentCollect = " select customerid,commodityid,(case when bs_flag=1 then '买' else '卖' end ) bs, "+
    	" evenprice,price marketClearPrice,HoldQty,HoldMargin,FloatingLoss,GageQty "+
		" from (select t.customerid,t.commodityid,t.bs_flag,t.evenprice,q.price,HoldQty,t.HoldMargin ,FloatingLoss,GageQty "+
		" from t_h_customerholdsum t,T_H_Quotation q where "+
		" t.cleardate=q.cleardate and t.commodityid=q.commodityid and t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+firmId +"' order by customerid,commodityid )";
%>
<br><center class="reportHead">资金结算表</center><br>	
<table width="800px" align="center" >
	<tr>
		<td class="reportLeft">交易商代码:<%=firmId %></td>
		<td class="reportLeft" >交易商名称:<%=name%></td>
		<td class="reportLeft" >
		<c:if test="<%=brokerId2 != null%>">
		加盟商:&nbsp;<%=brokerId2%></c:if>
		</td>
		<td class="reportLeft" width="100px">&nbsp;</td>
		<td class="reportRight">单位:元</td>
		<td class="reportRight">日期:<%=cleardate %></td>
	</tr>
</table>
    <%
       sql="select t.lastbalance,t.todaybalance from f_firmbalance t where firmId='"+firmId+"' "+filter;
       sql=sql.replaceAll("cleardate","b_date");
       List firmbalanceList=dao.queryBySQL(sql);
       Object lastbalance=new BigDecimal(0);
       Object todaybalance=new BigDecimal(0);
       if(firmbalanceList!=null&&firmbalanceList.size()>0)
       {
            Map map=(Map)firmbalanceList.get(0);
            lastbalance=map.get("lastbalance");
            todaybalance=map.get("todaybalance");
       }
       sql="select ClearMargin-ClearAssure ClearAssure,ClearFL,ClearSettleMargin,RuntimeMargin-RuntimeAssure RuntimeMargin,"+
       "RuntimeFL,RuntimeSettleMargin,MinClearDeposit,MaxOverdraft from t_h_firm t where firmId='"+firmId+"' "+filter;
       List thFirmList=dao.queryBySQL(sql);
       Object ClearAssure=new BigDecimal(0);
       Object ClearFL=new BigDecimal(0);
       Object ClearSettleMargin=new BigDecimal(0);
       Object RuntimeMargin=new BigDecimal(0);
       Object RuntimeFL=new BigDecimal(0);
       Object RuntimeSettleMargin=new BigDecimal(0);
       Object MinClearDeposit=new BigDecimal(0);
       Object MaxOverdraft=new BigDecimal(0);
       if(thFirmList!=null&&thFirmList.size()>0)
       {
          Map map=(Map)thFirmList.get(0);
          ClearAssure=map.get("ClearAssure");
          ClearFL=map.get("ClearFL");
          ClearSettleMargin=map.get("ClearSettleMargin");
          RuntimeMargin=map.get("RuntimeMargin");
          RuntimeFL=map.get("RuntimeFL");
          RuntimeSettleMargin=map.get("RuntimeSettleMargin");
          MinClearDeposit=map.get("MinClearDeposit");
          MaxOverdraft=map.get("MaxOverdraft");
       }
       sql = "select nvl(sum(t.floatingloss),0) floatingloss from t_h_firmholdsum t where t.firmid = '" + firmId + "'" + filter;
       List floatingLossList = dao.queryBySQL(sql);
       Object floatingloss = new BigDecimal(0);
       if (floatingLossList != null && floatingLossList.size() > 0) {
       		Map map = (Map)floatingLossList.get(0);
       		floatingloss = map.get("floatingloss");
       }
       double runtimeRight = ((BigDecimal)todaybalance).doubleValue() + ((BigDecimal)RuntimeMargin).doubleValue() + ((BigDecimal)RuntimeFL).doubleValue() + ((BigDecimal)RuntimeSettleMargin).doubleValue() + ((BigDecimal)floatingloss).doubleValue();
       filter=filter.replaceAll("t\\.","");
       sql="select '('||(case when f.fieldsign>0 then '+' else '-' end)||')'||f.name name,nvl(t.value,0) value  from (select * from f_clientledger where  firmId='"+firmId+"' "+filter+""+  
           " ) t,f_ledgerfield f "+
           "where f.code=t.code(+) and f.moduleid in (11,15) order by f.moduleid,f.ordernum";
       sql=sql.replaceAll("cleardate","b_date");
       List clientledgerList=dao.queryBySQL(sql);
       int change = 0;//资金改变量
       String lastbalanceString = lastbalance.toString();
       String todaybalanceString = todaybalance.toString();
       Double lastbalances = Double.parseDouble(lastbalanceString.trim());//上日资金余额
       Double todaybalances = Double.parseDouble(todaybalanceString.trim());//当日资金余额
       if (  ("1".equals(queryType.trim())) || ("2".equals(queryType.trim()) && ( lastbalances >0 || todaybalances >0 || change > 0 ) ) || ( ("3".equals(queryType.trim())) && change > 0  ) ){
    %>
<table width="800px" height="200px" align="center" border="0" class="reportTemp">
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日资金余额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=lastbalance.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<%
	  if(clientledgerList!=null&&clientledgerList.size()>0)
	  {
	    for(int a=0;a<clientledgerList.size();a++)
	    {
	      Map map=(Map)clientledgerList.get(a);
	      %>
	      <tr>
		<td class="td_reportMdHead_Right" align="right" width="50%"><%=map.get("name").toString()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><%=turnToNum2(map.get("value"))%></td>
	     </tr>
	      <%
	    }
	  }
          sql="select nvl(sum(case when f.fieldsign>0 then t.value else -t.value end),0) value  from (select * from f_clientledger where  firmId='"+firmId+"' "+filter+""+  
           " ) t,f_ledgerfield f "+
           "where f.code=t.code(+) and f.moduleid not in (11,15) order by f.moduleid,f.ordernum";
          sql=sql.replaceAll("cleardate","b_date");
          List clientledgerOtherList=dao.queryBySQL(sql);
	  if(clientledgerOtherList!=null&&clientledgerOtherList.size()>0)
	  {
              
	      Map map=(Map)clientledgerOtherList.get(0);
	      %>
	      <tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)其他交易系统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><%=turnToNum2(map.get("value"))%></td>
	     </tr>
	      <%	    
	  }
	 %>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">当日资金余额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=todaybalance.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)当日保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=RuntimeMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)当日浮亏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=RuntimeFL.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)当日交收保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=RuntimeSettleMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)浮动盈亏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=floatingloss.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">当日权益&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><%=turnToNum2(runtimeRight)%></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=ClearAssure.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日浮亏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=ClearFL.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日交收保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=ClearSettleMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">结算准备金最低限额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=MinClearDeposit.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">需追加资金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<%
		  double superAdd=0;
		   if(((BigDecimal)todaybalance).doubleValue()+((BigDecimal)MaxOverdraft).doubleValue()-((BigDecimal)MinClearDeposit).doubleValue()>=0)
		   {
		     
		   }
		   else
		   {
		      superAdd=((BigDecimal)MinClearDeposit).doubleValue()-((BigDecimal)todaybalance).doubleValue()-((BigDecimal)MaxOverdraft).doubleValue();
		   }
		%>
		<td class="td_reportRd1" width="50%"><%=turnToNum2(superAdd)%></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">质押资金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%"><fmt:formatNumber value="<%=MaxOverdraft.toString()%>" pattern="#,##0.00"/></td>
	</tr>
</table>
<%
		}else{
			%>
			<table width="800px"  align="center" border="0" class="reportTemp">
				<tr>
					<td class="td_reportRd">无符合条件信息。</td>
				</tr>
			</table>
			<%
		}
%>
<br>
<br>



<br><center class="reportHead">成交记录表</center><br>
	<table align="center" width="800px" border="0">
	<tr><td colspan="7"></td></tr>
	<tr>
		<td  class="reportLeft">交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %></td>
		<td  class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="800px">
	<tr>
	<td class="td_reportMdHead">成交序号</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">交易类型</td>
	<td class="td_reportMdHead">成交价格</td>
	<td class="td_reportMdHead">数量(批)</td>
	<td class="td_reportMdHead">委托时间</td>
	<td class="td_reportMdHead">成交时间</td>
	<td class="td_reportRdHead">交易手续费</td>
	</tr>
		<%
    	List innerlistBargainResult=dao.queryBySQL(innerSqlBargainResult);
    	for(int b = 0 ; b < innerlistBargainResult.size() ; b ++){
    	Map innerMapBargainResult = (Map)innerlistBargainResult.get(b);
		%> 	
	<tr>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMapBargainResult.get("a_tradeno")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapBargainResult.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapBargainResult.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapBargainResult.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMapBargainResult.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMapBargainResult.get("quantity")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapBargainResult.get("orderTime")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapBargainResult.get("tradetime")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMapBargainResult.get("tradefee")) %></td>
	</tr>
	<%
		sumQuantityBargainResult = sumQuantityBargainResult.add(turnToNum(innerMapBargainResult.get("quantity")));
		sumTradefeeBargainResult = sumTradefeeBargainResult.add(turnToNum(innerMapBargainResult.get("tradefee")));
	}
	
	if(innerlistBargainResult.size()>0){
	
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantityBargainResult %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradefeeBargainResult %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}else{
	%>
		<tr>
			<td class="td_reportRd" colspan="9">
				无符合条件信息。
			</td>
		</tr>
	<%
	}
	%>
	</table>
<br>
<br>


<br><center class="reportHead">转让盈亏明细表</center><br>
	<table align="center" width="800px" border="0">
	<tr>
		<td class="reportLeft">交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %></td>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" height="100px" width="800px">
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



    <br><center class="reportHead">订货汇总表</center><br>		
	<table align="center" width="800px" border="0">
	<tr>
		<td class="reportLeft">交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %></td>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="800px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买/卖</td>
	<td class="td_reportMdHead">平均价</td>
	<td class="td_reportMdHead">市场结算价</td>
	<td class="td_reportMdHead">数量(批)</td>
	<td class="td_reportMdHead">履约保证金</td>
	<td class="td_reportMdHead">浮动盈亏</td>
	<td class="td_reportRdHead">抵顶数量(批)</td>
	</tr>
		<%
    	List innerlistIndentCollect=dao.queryBySQL(innerSqlIndentCollect);
    	BigDecimal sumHoldQty = new BigDecimal("0");
    	BigDecimal sumHoldMargin = new BigDecimal("0.00");
    	BigDecimal sumFloatingLoss = new BigDecimal("0.00");
    	BigDecimal sumGageQty = new BigDecimal("0");
    	int mark = 0 ;
    	for(int b = 0 ; b < innerlistIndentCollect.size() ; b ++){
    	Map innerMapIndentCollect = (Map)innerlistIndentCollect.get(b);
    	mark ++;
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapIndentCollect.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapIndentCollect.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMapIndentCollect.get("bs")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMapIndentCollect.get("evenprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMapIndentCollect.get("marketClearPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMapIndentCollect.get("HoldQty")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMapIndentCollect.get("HoldMargin")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMapIndentCollect.get("FloatingLoss")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMapIndentCollect.get("GageQty")) %></td>
	</tr>
	<%
			sumHoldQty = sumHoldQty.add(turnToNum(innerMapIndentCollect.get("HoldQty")));
			sumHoldMargin = sumHoldMargin.add(turnToNum(innerMapIndentCollect.get("HoldMargin")));
			sumFloatingLoss = sumFloatingLoss.add(turnToNum(innerMapIndentCollect.get("FloatingLoss")));
			sumGageQty = sumGageQty.add(turnToNum(innerMapIndentCollect.get("GageQty")));
	}
	if(mark!=0){
	%>	
	<tr>
	<td class="td_reportMd" colspan="5"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumHoldQty %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumHoldMargin %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumFloatingLoss %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=sumGageQty %></b></td>
	</tr>
	<%
	}else{
	%>
	<tr>
	<td class="td_reportRd" colspan="9">没有可查询数据。</td>
	</tr>
	<%	
	}
	%>
	</table>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
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
			</td>	
		</tr>
</table>
</body>
</html>