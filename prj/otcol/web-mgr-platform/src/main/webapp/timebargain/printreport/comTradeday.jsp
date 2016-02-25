<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//startCommodityid,endCommodityid,startClearDate,endClearDate
	//get query condition and handle
	String cleardate = request.getParameter("cleardate");
	String commodityid = request.getParameter("commodityID");
	
	String filter = " 1=1 ";
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(commodityid)){
		filter += " and t.commodityid='"+commodityid+"'";
	}

	//query data
	String sql = " select t.firmid firmid,c.name name,sum(case when t.ordertype=1 and t.bs_flag=1 then Quantity else 0 end) buyAgree, "+
				" sum(case when t.ordertype=1 and t.bs_flag=2 then Quantity else 0 end) sellAgree, "+
				" sum(case when t.ordertype=2 and t.bs_flag=1 then Quantity else 0 end) buyTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=2 then Quantity else 0 end) sellTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=1 and t.tradetype=3 then Quantity else 0 end) insteadBuyTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=2 and t.tradetype=3 then Quantity else 0 end) insteadSellTransfer, "+
				" sum(Quantity) allQuantity from t_h_trade t,t_h_commodity c where  "+filter+" and t.commodityid=c.commodityid and t.cleardate=c.cleardate group by t.firmid,c.name order by t.firmid,c.name";
		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
    List list=dao.queryBySQL(sql);     
    	
	  String mark = null;
	    %>
	<br><center class="reportHead">当日分商品成交量统计表</center><br><br>
	<%
	if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">无符合条件信息。</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	
	    if(mark == null){
		%>
	<table align="center" width="600px" border="0">
	<tr>		
		<td class="reportLeft">商品代码:<%=commodityid %>&nbsp;商品名称:<%=turnToStr(innerMap.get("name")) %></td>
		<td class="reportRight">日期:<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">买进订立</td>
	<td class="td_reportMdHead">卖出订立</td>
	<td class="td_reportMdHead">买进转让</td>
	<td class="td_reportMdHead">卖出转让</td>
	<td class="td_reportMdHead">代为买进转让</td>
	<td class="td_reportMdHead">代为卖出转让</td>
	<td class="td_reportRdHead">总量</td>
	</tr>
	<%
    	mark = turnToStr(innerMap.get("firmid")) ;
	}
	%>
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyAgree")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellAgree")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadBuyTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadSellTransfer")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("allQuantity")) %></td>
	</tr>
	<%
	}
	%>
	</table>