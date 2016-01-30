<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>行情统计表 </title>
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
				" t.price avgPrice "+
				" from t_h_quotation t,t_commodity c where t.commodityid = c.commodityid and "+filter+
				" group by t.commodityid,c.contractfactor,t.price) a ,(select commodityid,cleardate,CurPrice from t_h_quotation) b, "+
				" (select commodityid,cleardate,OpenPrice,YesterBalancePrice from t_h_quotation) c "+
				" where  b.cleardate=a.maxDate and a.minDate=c.cleardate "+
				" and a.commodityid=b.commodityid and a.commodityid=c.commodityid order by a.commodityid";

		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
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
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("openprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("HighPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("LowPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("avgPrice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("curprice")) %></td>
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