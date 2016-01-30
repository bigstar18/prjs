<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>订货明细表</title>
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
	
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
	String startCommodityID = request.getParameter("startCommodityID");
	String endCommodityID = request.getParameter("endCommodityID");
	String bs = request.getParameter("bs");
	String breedId=request.getParameter("breedId");
	String brokerId=request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	
	String filter = " 1=1 ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(firmID)){
		filter += " and t.FirmID='"+firmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startCommodityID)){
		if(!startCommodityID.equals("null"))
		filter += " and t.commodityid>='"+startCommodityID+"'";
	}
	if(chcekNull(endCommodityID)){
		if(!endFirmID.equals("null"))
		filter += " and t.commodityid<='"+endCommodityID+"'";
	}
	if (!"3".equals(bs)) {
		if(chcekNull(bs)){
			filter += " and t.bs_flag="+Integer.parseInt(bs)+" ";
		}
	}
	if( chcekNull(breedId)){
		if(!breedId.equals("null")){
			filter +=" and t.commodityId in (select commodityId from t_commodity where breedId = '"+breedId+"')";
		}
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter += "and t.firmId in (select firmId from BR_FirmAndBroker where brokerId = '"+brokerId+"')";
		}
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null")){
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
		}
	}
	//query data
	String sql = " select t.commodityid commodityid from t_h_holdposition t where "+filter+" group by commodityid order by commodityid";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    System.out.println("q"+sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">无符合条件信息。</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map firmIDMap = (Map)list.get(a);
    	String getCommodityid = (String)firmIDMap.get("commodityid");
    	String innerSql = " select to_char(t.holdtime,'yyyy-MM-dd') openDate,to_char(t.holdtime,'hh24:mi:ss') openTime, "+
    	" t.firmid firmid,t.customerid customerid,t.commodityid commodityid,(case when t.bs_flag=1 then '买' else '卖' end) type,t.price price,t.openqty openqty,t.holdqty+t.gageqty useqty "+
		" from t_h_holdposition t where "+filter+" and t.commodityid='"+getCommodityid+"' order by t.firmid";
	   	BigDecimal sumOpenqty = new BigDecimal(0);
	   	BigDecimal sumUseqty = new BigDecimal(0); 	
		System.out.println("h"+sql);
		%>
	<br><center class="reportHead">订货明细表</center><br><br>
	<table align="center" width="700px" border="0">
	<tr>
		<td class="reportLeft">商品代码:&nbsp;<%=turnToStr(firmIDMap.get("commodityid")) %></td>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="700px">
	<tr>
	<td class="td_reportMdHead">订立日期</td>
	<td class="td_reportMdHead">时间</td>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">买/卖</td>
	<td class="td_reportMdHead">成交价</td>
	<td class="td_reportMdHead">订立数量</td>
	<td class="td_reportRdHead">可用数量</td>
	</tr>
		<%
    	List innerlist=dao.queryBySQL(innerSql);
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("openDate")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("openTime")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("openqty")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("useqty")) %></td>
	</tr>
	<%
		sumOpenqty = sumOpenqty.add(turnToNum(innerMap.get("openqty")));
		sumUseqty = sumUseqty.add(turnToNum(innerMap.get("useqty")));
	}
	if(innerlist.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumOpenqty %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=sumUseqty %></b></td>
	</tr>
	<%}else{%>
		<tr>
		<td class="td_reportRd" colspan="9">
			无符合条件信息。
		</td>
	</tr>
		<%}%>
	</table>
	<br><br><br><br><br>
	<%
	}
	%>
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