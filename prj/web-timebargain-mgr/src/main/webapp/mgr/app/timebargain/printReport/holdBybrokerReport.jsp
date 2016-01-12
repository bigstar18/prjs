<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>分加盟商订货统计表</title>
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
try{
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-04
	//get query condition and handle 
	String startBrokerID = request.getParameter("startBrokerID");
	String endBrokerID = request.getParameter("endBrokerID");
	String clearDate = request.getParameter("clearDate");
	String filter = "";
	if(chcekNull(startBrokerID)){
		filter += " and a.brokerid>='"+startBrokerID +"'";
	}
	if(chcekNull(endBrokerID)){
		filter += " and a.brokerid<='"+endBrokerID +"'";
	}
	
	if(chcekNull(clearDate)){
		filter += " and c.cleardate=to_date('"+clearDate +"','yyyy-MM-dd')";
	}
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	//query data
//	String sql = " select brokerid,name from m_b_broker where brokerid>='"+startFirmID +"' and brokerid<='"+endFirmID +"' order by brokerid";
//	 List brokerList=dao.queryBySQL(sql);
//	 List breedList=dao.queryBySQL("select breedid,breedname from t_a_breed order by SortID, breedid");
	String sql = " select a.brokerid,a.name,c.commodityid,(case when bs_flag=1 then '买' else '卖' end ) bs_flag,sum(c.HoldQty) HoldQty,sum(c.HoldMargin) HoldMargin,sum(c.FloatingLoss) FloatingLoss";
		   sql+=" from br_broker a,BR_FirmAndBroker b, t_h_firmholdsum c where a.brokerid=b.brokerid and b.firmid=c.firmid ";
		   sql+=filter;
		   sql+=" group by a.brokerid,a.name,c.commodityid,c.bs_flag  order by a.brokerid,c.commodityid,c.bs_flag";   
    List list=dao.queryBySQL(sql);
    BigDecimal countHoldQty = new BigDecimal("0.00");//用于总计
	BigDecimal countHoldMargin = new BigDecimal("0.00");
	BigDecimal countFloatingLoss = new BigDecimal("0.00");

	
	String brokername="";
	BigDecimal sumHoldQty = new BigDecimal("0.00");//用于小计
	BigDecimal sumHoldMargin = new BigDecimal("0.00");
	BigDecimal sumFloatingLoss = new BigDecimal("0.00");
	String mark = null;//用于标记
	int marknum = 0;
	int size = list.size()-1;
		%>
	<br><center class="reportHead">分加盟商订货统计表</center><br><br>
	<table align="center" width="900px" border="0">
	<tr>
		
		<td class="reportRight" colspan="6">日期:&nbsp;<%=clearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="900px">
	<tr>
	<td class="td_reportMdHead">会员名称</td>
	<td class="td_reportMdHead">会员账号</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买卖</td>
	<td class="td_reportMdHead">订货量</td>
	<td class="td_reportMdHead">保证金</td>
	<td class="td_reportRdHead">浮动盈亏</td>
	</tr>
		<%
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
    		marknum = i;
    		if(!turnToStr(innerMap.get("brokerid")).equals(mark)){
    			if( mark != null){
    	 %>
    	<tr>
    	<td class="td_reportMd" colspan="4">&nbsp;加盟商<%=brokername %>小计</td>
    	<td class="td_reportMd1" ><fmt:formatNumber value="<%=sumHoldQty %>" pattern="#,##0"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumHoldMargin %>" pattern="#,##0.00"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumFloatingLoss %>" pattern="#,##0.00"/></td>
    	</tr>
    	 <%						
    				sumHoldQty = new BigDecimal("0");//用于小计
    				sumHoldMargin = new BigDecimal("0.00");
    				sumFloatingLoss = new BigDecimal("0.00");
    	 		}
    		mark = turnToStr(innerMap.get("brokerid"));
    		}		
		%> 	
			<tr>
			<td class="td_reportMd" align="left">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("brokerid")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("bs_flag")) %></td>
			<td class="td_reportMd1"><%=innerMap.get("HoldQty") %></td>
			<td class="td_reportMd1"><%=turnToNum2(innerMap.get("HoldMargin")) %></td>
			<td class="td_reportMd1"><%=turnToNum2(innerMap.get("FloatingLoss")) %></td>
			</tr>
		<%
		sumHoldQty = sumHoldQty.add(turnToNum(innerMap.get("HoldQty")));
		sumHoldMargin = sumHoldMargin.add(turnToNum(innerMap.get("HoldMargin")));
		sumFloatingLoss = sumFloatingLoss.add(turnToNum(innerMap.get("FloatingLoss")));
		
		countHoldQty = countHoldQty.add(turnToNum(innerMap.get("HoldQty")));
		countHoldMargin = countHoldMargin.add(turnToNum(innerMap.get("HoldMargin")));
		countFloatingLoss = countFloatingLoss.add(turnToNum(innerMap.get("FloatingLoss")));
		
		brokername =turnToStr(innerMap.get("name"));
    	}
		if(size == marknum){
			%>	
			<tr>
    	<td class="td_reportMd" colspan="4"">&nbsp;加盟商<%=brokername %>小计</td>
    	<td class="td_reportMd1" ><fmt:formatNumber value="<%=sumHoldQty %>" pattern="#,##0"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumHoldMargin %>" pattern="#,##0.00"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumFloatingLoss %>" pattern="#,##0.00"/></td>
    	</tr>
			<%
			}
		if(list.size()>0){
			%>
			<tr>
    	<td class="td_reportMd" colspan="4"">加盟商总计</td>
    	<td class="td_reportMd1" ><fmt:formatNumber value="<%=countHoldQty %>" pattern="#,##0"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=countHoldMargin %>" pattern="#,##0.00"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=countFloatingLoss %>" pattern="#,##0.00"/></td>
    	</tr>
		<%}%>
	</table>
	<br><br><br><br><br>
	<% }catch(Exception e){
		e.printStackTrace();
		
	} %>
	</div>
</table>

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