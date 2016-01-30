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
	String brokerId = request.getParameter("brokerId");
	
	String filter = " 1=1 ";	
	if(chcekNull(cleardate)){
		filter += " and t.OrderTime>=to_date('"+cleardate +" 00:00:00','yyyy-MM-dd hh24:mi:ss') and t.OrderTime<=to_date('"+cleardate +" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
	}

	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
		}
	}
	String sql = "select t.firmid, t.customerid, t.a_orderno, t.commodityid, t.bs_flag, t.ordertype, t.status, t.quantity,t.price,t.tradeqty,to_char(t.ordertime,'yyyy-MM-dd') ordertime,t.traderid from T_H_Orders t where " + filter;
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    %>
    <br><center class="reportHead">委托情况表</center><br><br>
	<table align="center" width="800px">
	<tr><td colspan="9"></td></tr>
	<tr>
		<td class="reportLeft">
		<%if(!("null".equals(startFirmID) || "null".equals(endFirmID))){ %>
		起始交易商:<%=startFirmID%>&nbsp;结束交易商:&nbsp;<%=endFirmID%>&nbsp;
		<%} %>
		<%if(!"null".equals(brokerId)){ %>
		加盟商:&nbsp;<%=brokerId %>
		<%} %>
		</td>
		<td colspan="5" width="20%">&nbsp;&nbsp;</td>
		<td class="reportRight">日期:<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="1100px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">加盟商</td>
	<td class="td_reportMdHead">委托单号</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买/卖</td>
	<td class="td_reportMdHead">委托类型</td>
	<td class="td_reportMdHead">状态</td>
	<td class="td_reportMdHead">委托数量</td>
	<td class="td_reportMdHead">委托价格</td>
	<td class="td_reportMdHead">已成交数量</td>
	<td class="td_reportMdHead">委托时间</td>
	<td class="td_reportRdHead">交易员代码</td>
	</tr>
    <% 
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
			String relBS_flag = "";
			if (innerMap.get("bs_flag") != null) {
				if ("1".equals(innerMap.get("bs_flag").toString())) {
					relBS_flag = "买";
				}else if ("2".equals(innerMap.get("bs_flag").toString())) {
					relBS_flag = "卖";
				}
			}
			String relOrdertype = "";
			if (innerMap.get("ordertype") != null) {
				if ("1".equals(innerMap.get("ordertype").toString())) {
					relOrdertype = "订立";
				}else if ("2".equals(innerMap.get("ordertype").toString())) {
					relOrdertype = "转让";
				}
			}
			String relStatus = "";
			if (innerMap.get("status") != null) {
				if ("1".equals(innerMap.get("status").toString())) {
					relStatus = "已委托";
				}else if ("2".equals(innerMap.get("status").toString())) {
					relStatus = "部分成交";
				}else if ("3".equals(innerMap.get("status").toString())) {
					relStatus = "全部成交";
				}else if ("5".equals(innerMap.get("status").toString())) {
					relStatus = "全部撤单";
				}else if ("6".equals(innerMap.get("status").toString())) {
					relStatus = "部分成交后撤单";
				}
			}
			String getFirmId=(String)innerMap.get("firmId");
			String brokerSql="select brokerId from BR_FirmAndBroker where firmId='"+getFirmId+"'";
			List brokerList=dao.queryBySQL(brokerSql);
			String brokerId2=null;
			if(brokerList.size()>0){
				Map broker=(Map)brokerList.get(0);
				brokerId2=(String)broker.get("brokerId");
			}
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmID")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd"><c:if test="<%=brokerId2 !=null %>">&nbsp;<%=brokerId2 %></c:if>&nbsp;</td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("a_orderno")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=relBS_flag%></td>
	<td class="td_reportMd1">&nbsp;<%=relOrdertype %></td>
	<td class="td_reportMd1">&nbsp;<%=relStatus %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("price")) %></td>

	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("tradeqty")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("ordertime")) %></td>
	<td class="td_reportRd">&nbsp;<%=turnToStr(innerMap.get("traderid")) %></td>
	</tr>
	<%
	}
	if(list.size()<1){
	%>
	<td class="td_reportRd" colspan="13">没有可查询数据。</td>
	<%
	}
	%>
</table>
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