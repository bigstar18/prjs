<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>��Ʒ�ۺϻ��ܱ�</title>
</head>
<body>
	<table align="center"  border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
		     		 
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
<%
	//get query condition and handle
	String startCommodity = request.getParameter("startCommodityID");
    String endCommodity = request.getParameter("endCommodityID");
	String beginDate = request.getParameter("startClearDate");
	String endDate = request.getParameter("endClearDate");
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
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">��Ʒ�ۺϻ��ܱ�</center><br><br>
	<table align="center" width="600px">
	<tr><td colspan="6">&nbsp;</td></tr>
	<tr>
		
		<td colspan="2">&nbsp;</td>
		<td  class="reportRight">
		<%if(!("null".equals(startCommodity) || "null".equals(endCommodity))){ %>
		<td  class="reportRight">��ʼ��Ʒ:<%=startCommodity %>&nbsp;������Ʒ:<%=endCommodity %></td>
		<%} %>
		</td>
	    <c:if test="${type == 'h'}">
		  <td  class="reportRight">��ʼ����:<%=beginDate %>&nbsp;��������:<%=endDate %></td>
		</c:if>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	  <c:if test="${type == 'h'}">
	    <td class="td_reportMdHead">��������</td>
	  </c:if> 
	  <td class="td_reportMdHead">��Ʒ����</td>
	  <td class="td_reportMdHead">ӯ��</td>
	  <td class="td_reportMdHead">��������</td>
	  <td class="td_reportMdHead">�ܳɽ���</td>
	  <td class="td_reportRdHead">��������</td>
	</tr>
    <% 
    	
    		BigDecimal countSumClosePL = new BigDecimal(0);//�ܼ�
	   		BigDecimal countFirmCount = new BigDecimal(0);
	   		BigDecimal countSumQuantity = new BigDecimal(0);
	   		BigDecimal countSumTradeFee = new BigDecimal(0);
	   	
	   	
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
		
		%> 	
	<tr>
	  <c:if test="${type == 'h'}">
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	  </c:if>
	  <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	  <td class="td_reportMd1">&nbsp;<fmt:formatNumber value='<%=turnToNum(innerMap.get("sumClosePL")) %>' pattern="#,##0.00"/></td>
	  <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("firmCount")) %></td>
	  <td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("sumQuantity")) %></td>
	  <td class="td_reportRd1">&nbsp;<fmt:formatNumber value='<%=turnToNum(innerMap.get("sumTradeFee")) %>' pattern='#,##0.00'/></td>
	</tr>

	<%
	
	   countSumClosePL = countSumClosePL.add(turnToNum(innerMap.get("sumClosePL")));
   		countFirmCount = countFirmCount.add(turnToNum(innerMap.get("firmCount")));
   		countSumQuantity = countSumQuantity.add(turnToNum(innerMap.get("sumQuantity")));
   		countSumTradeFee = countSumTradeFee.add(turnToNum(innerMap.get("sumTradeFee")));
	} 
		
	%>
	<tr>
	<td class="td_reportMd"  <c:if test="${type == 'h'}"> colspan="2"</c:if>><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value='<%=countSumClosePL %>' pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countFirmCount %></b></td>
	<td class="td_reportMd1" style="text-align: center;"><b>&nbsp;<%=countSumQuantity %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value='<%=countSumTradeFee %>' pattern="#,##0.00"/></b></td>
	</tr> 
</table>
</div>
<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>