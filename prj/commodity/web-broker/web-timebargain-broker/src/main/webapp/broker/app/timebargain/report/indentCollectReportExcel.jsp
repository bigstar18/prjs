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
	String filter = " 1=1 and firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(cleardate)){
		//filter += " and t.createtime=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(firmcategory)){
		filter += " and t.firmcategoryid = '"+firmcategory+"'";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//会员添加居间条件
		filter += " and t.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}
	//query data
	String sql = " select firmId,name from m_firm t where "+filter+" order by firmId";
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
    	String innerSql = " select customerid,commodityid,(case when bs_flag=1 then '买' else '卖' end ) bs, "+
    	" evenprice,price marketClearPrice,HoldQty,HoldMargin,FloatingLoss,GageQty "+
		" from (select t.customerid,t.commodityid,t.bs_flag,t.evenprice,q.price,HoldQty,t.HoldMargin ,FloatingLoss,GageQty "+
		" from t_h_customerholdsum t,T_H_Quotation q where "+
		" t.cleardate=q.cleardate and t.commodityid=q.commodityid and t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+getFirmId +"' order by customerid,commodityid )";
		%>	
    <br><center class="reportHead">订货汇总表</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft">
			交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %>&nbsp;&nbsp;
			交易商名称:&nbsp;<%=turnToStr(firmIDMap.get("name")) %>
		</td>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">二级代码</td>
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
    	List innerlist=dao.queryBySQL(innerSql);
    	BigDecimal sumHoldQty = new BigDecimal("0");
    	BigDecimal sumHoldMargin = new BigDecimal("0.00");
    	BigDecimal sumFloatingLoss = new BigDecimal("0.00");
    	BigDecimal sumGageQty = new BigDecimal("0");
    	int mark = 0 ;
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
    	mark++;
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("bs")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("evenprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("marketClearPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("HoldQty")).add(turnToNum(innerMap.get("GageQty")))%></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("HoldMargin")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("FloatingLoss")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("GageQty")) %></td>
	</tr>
	<%
			sumHoldQty = sumHoldQty.add(turnToNum(innerMap.get("HoldQty")));
			sumHoldMargin = sumHoldMargin.add(turnToNum(innerMap.get("HoldMargin")));
			sumFloatingLoss = sumFloatingLoss.add(turnToNum(innerMap.get("FloatingLoss")));
			sumGageQty = sumGageQty.add(turnToNum(innerMap.get("GageQty")));
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