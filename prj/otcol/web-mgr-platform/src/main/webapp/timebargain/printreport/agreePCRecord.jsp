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
	String sql = " select to_char(t.cleardate,'yyyy-MM-dd') cleardate,t.commodityid commodityid,t.bs_flag bs_flag, "+
				" t.firmid firmid,t.customerid customerid,t.quantity quantity,t.holdprice holdprice, "+
				" t.price price,t.Close_PL Close_PL from t_h_trade t where "+filter+
				" and t.ordertype=2 and t.tradetype=6 order by to_char(t.cleardate,'yyyy-MM-dd'),t.commodityid,t.bs_flag,t.firmid";
	
		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	    List list=dao.queryBySQL(sql);    
    	%>    
	<br><center class="reportHead">协议交收记录表</center><br><br>
	<table align="center" width="600px">
	<tr>
		<td class="reportRight" colspan="9">起始日期:<%=startClearDate %>&nbsp;&nbsp;结束日期:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">协议交收日期</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买方/卖方</td>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">数量</td>
	<td class="td_reportMdHead">订立价</td>
	<td class="td_reportMdHead">转让价</td>
	<td class="td_reportRdHead">平仓盈亏</td>
	</tr>	
    	<%
	   	BigDecimal sumQuantity = new BigDecimal(0);//小计
	   	
	   	BigDecimal countQuantity = new BigDecimal(0);//总计
		
		String strMark = null;//标记
		String mark = null;
		int num = 0;
		int size = list.size()-1;
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	
    	String bs_flag = innerMap.get("bs_flag").toString();
    	String relBS = "";
    	if ("1".equals(bs_flag)) {
    		relBS = "买";
    	}else if ("2".equals(bs_flag)) {
    		relBS = "卖";
    	}
    	
    	num = a;
    	mark = turnToStr(innerMap.get("cleardate"))+turnToStr(innerMap.get("commodityid"))+turnToNum(innerMap.get("bs_flag"));
    	if(!mark.equals(strMark)){
    		if(strMark != null){
    		%>	
				<tr>
					<td class="td_reportMd" colspan="3">&nbsp;</td>
					<td class="td_reportMd"><b>合计:</b></td>
					<td class="td_reportMd">&nbsp;</td>
					<td class="td_reportMd1"><b><%=sumQuantity %></b></td>
					<td class="td_reportRd" colspan="3">&nbsp;</td>
				</tr>
    		<%
	    		sumQuantity = new BigDecimal(0);
    		}
   			strMark = mark;
    	}
		%>	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=relBS %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>	
	<td class="td_reportMd1">&nbsp;
		<fmt:formatNumber value="<%=turnToNum(innerMap.get("holdprice")) %>" pattern="#,##0.00"/>
		</td>	
	<td class="td_reportMd1">&nbsp;
		<fmt:formatNumber value="<%=turnToNum(innerMap.get("price")) %>" pattern="#,##0.00"/>
		</td>
	<td class="td_reportRd1">&nbsp;
		<fmt:formatNumber value="<%=turnToNum(innerMap.get("Close_PL")) %>" pattern="#,##0.00"/>
		</td>
	</tr>
	<%   		
   		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("quantity")));   		
   		countQuantity = countQuantity.add(turnToNum(innerMap.get("quantity")));
	}
	if(size == num){
	%>	
	<tr>
		<td class="td_reportMd" colspan="3">&nbsp;</td>
		<td class="td_reportMd"><b>合计:</b></td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
		<td class="td_reportRd" colspan="3">&nbsp;</td>
	</tr>
	<%
	}
	if(list.size()>0){
	%>	
	<tr>
	<td class="td_reportMd" colspan="5"><b>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countQuantity %></b></td>
	<td class="td_reportRd1" colspan="3">&nbsp;</td>
	</tr>
	<%	
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="9" align="left">
			无符合条件信息。
		</td>
	</tr>
	<%
	}
	%>
</table>