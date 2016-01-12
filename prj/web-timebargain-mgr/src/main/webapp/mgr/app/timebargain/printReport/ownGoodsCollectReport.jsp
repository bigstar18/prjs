<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>订货汇总（市值）表</title>
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
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-04
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
	String brokerId=request.getParameter("brokerId");	
	String cateGoryId=request.getParameter("cateGoryId");
	
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
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null")){
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
			filter_m +=" and m.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
		}
	}
	//query data
	String sql = " select firmId,name from m_firm m where "+filter_m+" order by firmId";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    for(int a = 0 ; a < list.size() ; a ++){
    	Map firmIDMap = (Map)list.get(a);
    	String getFirmId = (String)firmIDMap.get("firmId");
    	String innerSql = " select commodityid,sum(case when BS_Flag=1 then HoldQty else 0 end) buyCount, "+
    	" max(case when BS_Flag=1 then EvenPrice else 0 end) buyEvenPrice, "+
		" sum(case when BS_Flag=2 then HoldQty else 0 end) sellCount, "+
		" max(case when BS_Flag=2 then EvenPrice else 0 end) sellEvenPrice, "+
		" max(price) clearPrice,sum(FloatingLoss) FloatingLoss,sum(HoldMargin) ding "+
		" from (select t.*,q.price from t_h_firmholdsum t,T_H_Quotation q "+
		" where t.cleardate=q.cleardate and t.commodityid=q.commodityid and "+
		filter+" and t.firmid='"+getFirmId +"') group by commodityid order by commodityid";
    	
	   	BigDecimal sumBuyCount = new BigDecimal(0);
	   	BigDecimal sumSellCount = new BigDecimal(0);
	   	BigDecimal sumFloatingLoss = new BigDecimal("0.00");
	   	BigDecimal sumDing = new BigDecimal("0.00");   
	   	String brokerSql="select brokerId from BR_FirmAndBroker where firmId='"+getFirmId+"'";
		List brokerList=dao.queryBySQL(brokerSql);
		String brokerId2=null;
		if(brokerList.size()>0){
			Map broker=(Map)brokerList.get(0);
			brokerId2=(String)broker.get("brokerId");
		}
		%>
    <br>
	<center class="reportHead">订货汇总表(市值)</center><br><br>	
	<table align="center" width="600px" border="0">
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
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买订量</td>
	<td class="td_reportMdHead">买均价</td>
	<td class="td_reportMdHead">卖订量</td>
	<td class="td_reportMdHead">卖均价</td>
	<td class="td_reportMdHead">今结算价</td>
	<td class="td_reportMdHead">浮动盈亏</td>
	<td class="td_reportRdHead">占用保证金</td>
	</tr>
		<%
    	List innerlist=dao.queryBySQL(innerSql);
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyCount")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("buyEvenPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellCount")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sellEvenPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("clearPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("FloatingLoss")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("ding")) %></td>
	</tr>
	<%
		sumBuyCount = sumBuyCount.add(turnToNum(innerMap.get("buyCount")));
		sumSellCount = sumSellCount.add(turnToNum(innerMap.get("sellCount")));
		sumFloatingLoss = sumFloatingLoss.add(turnToNum(innerMap.get("FloatingLoss")));
		sumDing = sumDing.add(turnToNum(innerMap.get("ding")));
	}
	if(innerlist.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumBuyCount %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumSellCount %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumFloatingLoss %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumDing %>" pattern="#,##0.00"/></b></td>
	</tr>
<%}else{%>
	<tr>
			<td class="td_reportRd" colspan="8">
				无符合条件信息。
			</td>
		</tr>
	<%}%>
	</table>
	<br><br><br><br><br>
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