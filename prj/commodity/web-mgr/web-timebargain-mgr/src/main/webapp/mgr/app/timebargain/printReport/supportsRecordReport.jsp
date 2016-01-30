<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>抵顶记录表 </title>
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
	String startCommodityID = request.getParameter("startCommodityID");
	String endCommodityID = request.getParameter("endCommodityID");
	String breedId =request.getParameter("breedId");
	
	String filter = " 1=1 ";
	if(chcekNull(startCommodityID)){
		if(!startCommodityID.equals("null"))
		filter += " and t.commodityid>='"+startCommodityID +"'";
	}
	if(chcekNull(endCommodityID)){
		if(!endCommodityID.equals("null"))
		filter += " and t.commodityid<='"+endCommodityID +"'";
	}
	if(chcekNull(breedId)){
		if(!breedId.equals("null"))
			filter += " and t.commodityId in(select commodityId from t_commodity where breedId='"+breedId+"')";
	}
	filter += " order by t.firmid";
	String sql = "select t.firmid,to_char(t.cleardate,'yyyy-MM-dd') cleardate,t.customerid,t.commodityid,(t.HoldQty + t.GageQty) HoldQty,t.gageqty quantity "+
				"from t_h_customerholdsum t where t.BS_Flag=2 and t.GageQty > 0 and"+filter;
		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	    List list=dao.queryBySQL(sql);    
    	%>    		   
	<br><center class="reportHead">抵顶记录表</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft" colspan="6">
		<%if(!("null".equals(startCommodityID) || "null".equals(endCommodityID))){ %>
		起始商品:<%=startCommodityID %>&nbsp;结束商品:&nbsp;<%=endCommodityID%>&nbsp;
		<%} %>
		</td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">抵顶开始日期</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">订货数量</td>
	<td class="td_reportRdHead">抵顶数量</td>
	</tr>	
	<%--
    	<%
	   //BigDecimal sumQuantity = new BigDecimal(0);//小计
	   	//BigDecimal sumHoldqty = new BigDecimal(0);
	   	
	   	//BigDecimal countQuantity = new BigDecimal(0);//总计
	   	//BigDecimal countHoldqty = new BigDecimal(0);
		
		String mark = null;//标记
		int num = 0;
		int size = list.size()-1; --%>
	<% for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	//num = a;
    	//if(!turnToStr(innerMap.get("firmid")).equals(mark)){
    		//if(mark != null){
    		%>
    		<%-- 
		    <tr>	
				<td class="td_reportMd" colspan="4"><b>小计</b></td>
				<td class="td_reportMd1"><b><%=sumQuantity %></b></td>
				<td class="td_reportRd1"><b><%=sumHoldqty %></b></td>
			</tr> --%>
    		<%
    		 // countQuantity = countQuantity.add(sumQuantity);
   		    //countHoldqty = countHoldqty.add(sumHoldqty);
	    		//sumQuantity = new BigDecimal(0);
		   		//sumHoldqty = new BigDecimal(0);
    		//}
   			//mark = turnToStr(innerMap.get("firmid"));
    	//}
		//%>	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("HoldQty")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	</tr>
	<%-- 
	<%   		
   		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("HoldQty")));
   		sumHoldqty = sumHoldqty.add(turnToNum(innerMap.get("quantity")));
	}
	if(size == num){
	%>	
	<tr>	
		<td class="td_reportMd" colspan="4"><b>小计</b></td>
		<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
		<td class="td_reportRd1"><b>&nbsp;<%=sumHoldqty %></b></td>
	</tr>
	<%
	        countQuantity = countQuantity.add(sumQuantity);
   		    countHoldqty = countHoldqty.add(sumHoldqty);
	} --%>
	<%}
	if(list.size()>0){
	%>
	<%-- 	
	<tr>
	<td class="td_reportMd" colspan="4"><b>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countQuantity %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=countHoldqty %></b></td>
	</tr> --%>
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="6">
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