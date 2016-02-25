<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//startCommodityid,endCommodityid,startClearDate,endClearDate
	//get query condition and handle
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String startCommodityID = request.getParameter("startCommodityID");
	String endCommodityID = request.getParameter("endCommodityID");
	String breedId = request.getParameter("breedId");
	String filter = " 1=1 ";
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startCommodityID)){
		if(!startCommodityID.equals("null")){
			filter += " and t.commodityid>='"+startCommodityID+"'";
		}else{
			startCommodityID=null;
		}
	}
	if(chcekNull(endCommodityID)){
		if(!endCommodityID.equals("null")){
			filter += " and t.commodityid<='"+endCommodityID+"'";
		}
		else{
			endCommodityID=null;
		}
	}
	if(chcekNull(breedId)){
		if(!breedId.equals("null")){
			filter +="and t.commodityid in (select e.commodityid from t_commodity e where breedId="+breedId+") ";
		}
	}

	//query data
	String sql = " select t.commodityid,sum(case when t.ordertype=1 and t.bs_flag=1 then Quantity else 0 end) buyAgree, "+
				" sum(case when t.ordertype=1 and t.bs_flag=2 then Quantity else 0 end) sellAgree, "+
				" sum(case when t.ordertype=2 and t.bs_flag=1 then Quantity else 0 end) buyTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=2 then Quantity else 0 end) sellTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=1 and t.tradetype=3 then Quantity else 0 end) insteadBuyTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=2 and t.tradetype=3 then Quantity else 0 end) insteadSellTransfer, "+
				" sum(Quantity) allQuantity from t_h_trade t where  "+filter+" group by t.commodityid order by t.commodityid";
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
    List list=dao.queryBySQL(sql);
    
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">无符合条件信息。</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
		%>
	<br><center class="reportHead">分商品成交量统计表</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>		
		<td class="reportLeft"><c:if test="<%=startCommodityID != null%>">起始商品：<%=startCommodityID %>&nbsp;</c:if>
			<c:if test="<%=endCommodityID !=null%>">结束商品：&nbsp;<%=endCommodityID %></c:if></td>
		<td class="reportRight" colspan="6">起始日期:<%=startClearDate %>&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买进订立</td>
	<td class="td_reportMdHead">卖出订立</td>
	<td class="td_reportMdHead">买进转让</td>
	<td class="td_reportMdHead">卖出转让</td>
	<td class="td_reportMdHead">代为买进转让</td>
	<td class="td_reportMdHead">代为卖出转让</td>
	<td class="td_reportRdHead">总量</td>
	</tr>	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyAgree")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellAgree")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadBuyTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadSellTransfer")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("allQuantity")) %></td>
	</tr>
	</table>
	<%
	}
	%>