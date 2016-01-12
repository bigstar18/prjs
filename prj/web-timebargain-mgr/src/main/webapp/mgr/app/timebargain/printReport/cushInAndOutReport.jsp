<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>交易商入金出金记录表</title>
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
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String brokerId=request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");

	String filter = "";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
		filter += " and firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and firmid<='"+endFirmID +"'";
	}
	if(chcekNull(firmID)){
		filter += " and FirmID='"+firmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and b_date=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startClearDate)){
		filter += " and b_date>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and b_date<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
			filter+=" and firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
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
	String sql = "select c.firmid,c.b_date,outMoney,inMoney,nvl(bankInMoney,0) bankInMoney,nvl(bankOutMoney,0) bankOutMoney from " + 
		"(select firmid, " +
			       "to_char(b_date, 'yyyy-MM-dd') b_date, " +
			       "sum(case when code = 'Fetch' then value else 0 end) outMoney, " +
			       "sum(case when code = 'Deposit' then value else 0 end) inMoney " +			    
			  "from f_clientledger " + 
			 "where (code = 'Fetch' or code = 'Deposit' ) "+filter+
			 "group by b_date, firmid) c, " +
			 "(select  firmid, " +
			       "to_char(b_date, 'yyyy-MM-dd') b_date, " +
			  "sum(case when oprcode = '11003' then amount else 0 end) bankInMoney, " +
			       "sum(case when oprcode = '11004' then amount else 0 end) bankOutMoney " +
			 "from f_h_fundflow " + 
			 "where (oprcode = '11003' or oprcode = '11004') "+filter+
			 "group by b_date, firmid) f " +			 
			 "where c.firmid = f.firmid(+) and c.b_date = f.b_date(+) order by c.firmid";
			
		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
		
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
	<td class="td_reportMd1" width="15%">&nbsp;<%=turnToNum2(innerMap.get("inMoney")) %></td>
	<td class="td_reportMd1" width="15%">&nbsp;<%=turnToNum2(innerMap.get("outMoney")) %></td>
	<td class="td_reportMd1" width="15%">&nbsp;<%=turnToNum2(innerMap.get("bankInMoney")) %></td>
	<td class="td_reportRd1" width="15%">&nbsp;<%=turnToNum2(innerMap.get("bankOutMoney")) %></td>
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