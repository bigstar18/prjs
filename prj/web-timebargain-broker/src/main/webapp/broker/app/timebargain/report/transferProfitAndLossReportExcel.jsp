<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<%@ include file="common/excelExpFile.jsp"%>


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
	String cleardate = request.getParameter("cleardate");
	String firmcategory = request.getParameter("firmcategory");
	String brokerageID = request.getParameter("brokerageId");
	User user=(User)request.getSession().getAttribute("CurrentUser");
	String filter_m = " 1=1 and m.firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter_m += " and m.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter_m += " and m.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(cleardate)){
		//filter += " and m.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//会员添加居间条件
		filter_m += " and m.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}
	if(chcekNull(firmcategory)){
		filter_m += " and m.firmcategoryid = '"+firmcategory+"'";
	}
	//query data
	String sql = " select m.firmId,m.name from m_firm m where "+filter_m+" order by m.firmId";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">无符合条件信息。</font></b></div>
    <%
    }
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
		%>
		<br><center class="reportHead">转让盈亏明细表</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft">
			交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %>&nbsp;&nbsp;
			交易商名称:&nbsp;<%=turnToStr(firmIDMap.get("name")) %>
		</td>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" height="100px" width="600px">
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
			</td>	
		</tr>
</table>
</body>
</html>