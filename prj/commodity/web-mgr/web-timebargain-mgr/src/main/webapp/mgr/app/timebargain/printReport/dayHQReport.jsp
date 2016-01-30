<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>每日收市行情表</title>
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
	//get query condition and handle
	String cleardate = request.getParameter("cleardate");
	
	String filter = " 1=1 ";
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select t.commodityid commodityid,t.ReserveCount ReserveCount,t.openprice openprice,t.highprice highprice,"+
				" t.lowprice lowprice,t.price price,t.curprice curprice,t.totalamount totalamount,t.spread spread "+
				"  from t_h_quotation t where "+filter+" order by t.commodityid";
						
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">每日收市行情表</center>
	<table align="center" width="600px" border="0">
	<tr><td colspan="9"></td></tr>
	<tr>
		<td class="reportRight" colspan="9">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">订货量</td>
	<td class="td_reportMdHead">开市</td>
	<td class="td_reportMdHead">最高</td>
	<td class="td_reportMdHead">最低</td>
	<td class="td_reportMdHead">平均</td>
	<td class="td_reportMdHead">收市</td>
	<td class="td_reportMdHead">成交量</td>
	<td class="td_reportRdHead">涨跌</td>
	</tr>
    <%    	
		BigDecimal sumReserveCount = new BigDecimal(0);
		BigDecimal sumTotalamount = new BigDecimal(0);
		
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("ReserveCount")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("openprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("highprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("lowprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("curprice")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("totalamount")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("spread")) %></td>
	</tr>
	<%
		sumReserveCount = sumReserveCount.add(turnToNum(innerMap.get("ReserveCount")));
		sumTotalamount = sumTotalamount.add(turnToNum(innerMap.get("totalamount")));
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumReserveCount %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTotalamount %></b></td>
	<td class="td_reportRd">&nbsp;</td>
	</tr>
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="9">
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