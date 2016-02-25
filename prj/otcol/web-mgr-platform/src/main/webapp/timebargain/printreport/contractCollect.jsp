<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle
	String startCommodity = request.getParameter("startCommodity");
    String endCommodity = request.getParameter("endCommodity");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String breedId = request.getParameter("breedId");
	String type = request.getParameter("type");
	pageContext.setAttribute("type",type);
	//query data
	String sql = "";
	 if("d".equals(type)){
		 sql = "select commodityid, " +
         "nvl(sum(Close_PL), 0) sumClosePL, " +
         "count(distinct firmid) firmCount, " +
         "nvl(sum(quantity), 0) sumQuantity, " +
         "nvl(sum(tradeFee), 0) sumTradeFee " +
 "from t_trade  " +
 "where 1=1 " ; 
		 if(breedId!=null && !breedId.equals("null")){
			 sql+="and nls_upper(commodityid) in (select e.commodityid from t_commodity e where breedId="+breedId+") ";
		 }
 	if(!startCommodity.equals("null")&& !endCommodity.equals("null")){
      sql+="and nls_upper(commodityid) >= nls_upper('" + startCommodity + "') " +
      "and nls_upper(commodityid) <= nls_upper('" + endCommodity + "') " ;
 	}
     sql+= "group by commodityid " +
      "order by nls_upper(commodityid)";
	              		
	 }else if("h".equals(type)){
		 sql = "select to_char(cleardate, 'yyyy-MM-dd') cleardate, commodityid, " +
                       "nvl(sum(Close_PL), 0) sumClosePL, " +
                       "count(distinct firmid) firmCount, " +
                       "nvl(sum(quantity), 0) sumQuantity, " +
                       "nvl(sum(tradeFee), 0) sumTradeFee " +
               "from t_h_trade  " +
               "where 1=1 "  ; 
			 if(breedId!=null && !breedId.equals("null")){
				 sql+="and nls_upper(commodityid) in (select e.commodityid from t_commodity e where breedId="+breedId+") ";
			 }
		 		if(!startCommodity.equals("null")&& !endCommodity.equals("null")){
		 	      sql+="and nls_upper(commodityid) >= nls_upper('" + startCommodity + "') " +
		 	      "and nls_upper(commodityid) <= nls_upper('" + endCommodity + "') " ;
		 	 	}
		 	     sql+= 
                    "and cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " +
                    "and cleardate <= to_date('" + endDate + "','yyyy-MM-dd') " +
                    "group by cleardate,commodityid ";
                    
	              		
	 }
	  System.out.print(sql);        
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">主力合约汇总表</center><br><br>
	<table align="center" width="600px">
	<tr><td colspan="6">&nbsp;</td></tr>
	<tr>
		
		<td colspan="2">&nbsp;</td>
		<td  class="reportRight">
		<%if(!startCommodity.equals("null")) {%>
			起始商品:<%=startCommodity %>
		<%}
		if(!endCommodity.equals("null")){
		%>
		&nbsp;结束商品:<%=endCommodity %>
		<%} %>
		</td>
	    <c:if test="${type == 'h'}">
		  <td  class="reportRight">开始日期:<%=beginDate %>&nbsp;结束日期:<%=endDate %></td>
		</c:if>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	  <c:if test="${type == 'h'}">
	    <td class="td_reportMdHead">结算日期</td>
	  </c:if> 
	  <td class="td_reportMdHead">合约代码</td>
	  <td class="td_reportMdHead">盈亏</td>
	  <td class="td_reportMdHead">在线人数</td>
	  <td class="td_reportMdHead">总成交量</td>
	  <td class="td_reportMdHead">总手续费</td>
	</tr>
    <% 
    	
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
		
		%> 	
	<tr>
	  <c:if test="${type == 'h'}">
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	  </c:if>
	  <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("sumClosePL")) %>" pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("firmCount")) %></td>
	  <td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("sumQuantity")) %></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("sumTradeFee")) %>" pattern="#,##0.00"/></td>
	</tr>

	<%
		}
	%>
</table>