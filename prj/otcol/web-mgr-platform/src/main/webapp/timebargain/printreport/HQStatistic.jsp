<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle 
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate    = request.getParameter("endClearDate");
	
	String filter = " 1=1 ";
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select a.commodityid commodityid,c.openprice openprice,a.HighPrice HighPrice, "+
				" a.LowPrice LowPrice,a.avgPrice avgPrice,b.curprice curprice,a.TotalAmount TotalAmount, "+
				" b.curprice-c.yesterbalanceprice fluctuate from "+
				" (select t.commodityid,c.contractfactor contractfactor,max(t.cleardate) maxDate,min(t.cleardate) minDate,max(HighPrice) HighPrice, "+
				" min(LowPrice) LowPrice,sum(TotalAmount) TotalAmount,"+
				" trunc((case when sum(TotalAmount*contractfactor)<>0 then sum(TotalMoney)/sum(TotalAmount*contractfactor) else 0 end),2) avgPrice "+
				" from t_h_quotation t,t_commodity c where t.commodityid = c.commodityid and "+filter+
				" group by t.commodityid,c.contractfactor) a ,(select commodityid,cleardate,CurPrice from t_h_quotation) b, "+
				" (select commodityid,cleardate,OpenPrice,YesterBalancePrice from t_h_quotation) c "+
				" where  b.cleardate=a.maxDate and a.minDate=c.cleardate "+
				" and a.commodityid=b.commodityid and a.commodityid=c.commodityid order by a.commodityid";

		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	    List list=dao.queryBySQL(sql);    
    	%>    		   
	<br><center class="reportHead">行情统计表</center>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportRight" colspan="7">起始日期:<%=startClearDate %>&nbsp;&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">开市</td>
	<td class="td_reportMdHead">最高</td>
	<td class="td_reportMdHead">最低</td>
	<td class="td_reportMdHead">平均</td>
	<td class="td_reportMdHead">收市</td>
	<td class="td_reportRdHead">成交量</td>
	</tr>	
    	<%
	   	BigDecimal sumQuantity = new BigDecimal(0);
	    for(int a = 0 ; a < list.size() ; a ++){
	    	Map innerMap = (Map)list.get(a);
		%>
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("openprice")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("HighPrice")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("LowPrice")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("avgPrice")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("curprice")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("TotalAmount")) %></td>
	</tr>
	<% 
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("TotalAmount")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="6"><b>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=sumQuantity %></b></td>
	</tr>
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="7">
			无符合条件信息。
		</td>
	</tr>
	<%
	}
	%>
</table>