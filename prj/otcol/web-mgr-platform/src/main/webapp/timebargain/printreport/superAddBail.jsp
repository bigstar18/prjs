<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle 
	String clearDate    = request.getParameter("cleardate");
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String brokerId =request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	String filter = " 1=1 ";
	if(chcekNull(clearDate)){   

		filter += " and t.cleardate=to_date('"+clearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
		filter += " and t.firmId>='"+startFirmID +"' ";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and t.firmId<='"+endFirmID +"' ";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
		}
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	}
	
	//query data
	String sql = "select t.firmid,t.commodityid,sum(t.holdqty+t.gageqty) amount,sum(t.gageqty) gageqty, "+
					" sum((case when FloatingLoss>=0 then 0 else -FloatingLoss end )) FloatingLoss "+
					" from t_h_firmholdsum t where " + filter + " group by t.firmid,t.commodityid  order by t.firmid,t.commodityid ";	
		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	    List list=dao.queryBySQL(sql);    
    	%>    		   
	<br><center class="reportHead">浮动亏损统计表</center> 
	<table align="center" width="600px">
	<tr>
		<td class="reportRight" colspan="4">日期:<%=clearDate%></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">加盟商</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">数量</td>
	<td class="td_reportMdHead">抵顶数量</td>
	<td class="td_reportRdHead">浮动亏损</td>
	</tr>
	<%
	String firmId="";
	double totalAmount=0;
	double totaldiAmount=0;
	double totalMoney=0;
	double firmAmount=0;
	double firmDi=0;
	double firmMoney=0;
	for(int i=0;i<list.size();i++)
	{
	  Map map=(Map)list.get(i);
	  String f=(String)map.get("firmId");
	  String commodityid=(String)map.get("commodityid");
	  double amount=((BigDecimal)map.get("amount")).doubleValue();
	  double gageqty=((BigDecimal)map.get("gageqty")).doubleValue();
	  double FloatingLoss=((BigDecimal)map.get("FloatingLoss")).doubleValue();
		String brokerSql="select brokerId from m_b_broker where firmId='"+f+"'";
		List brokerList=dao.queryBySQL(brokerSql);
		String brokerId2=null;
		if(brokerList.size()>0){
			Map broker=(Map)brokerList.get(0);
			brokerId2=(String)broker.get("brokerId");
		}
	  if(!firmId.equals(f)&&!"".equals(firmId))
	  {
	    %>
	    <tr>
		<td class="td_reportMd" colspan="3"><b>交易商小计:</b></td>
		<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=firmAmount+""%>" pattern="#,##0.00"/></b></td>
		<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=firmDi+""%>" pattern="#,##0.00"/></b></td>
		<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=firmMoney+""%>" pattern="#,##0.00"/></b></td>
	    </tr>
	    <%
	    firmAmount=0;
	    firmDi=0;
	    firmMoney=0;
	    firmId=f;
	  }
	  else if("".equals(firmId))
	  {
	    firmId=f;
	  }
	  %>
	   <tr>
		<td class="td_reportMd" >&nbsp;<%=f%></td>
		<td class="td_reportMd" ><c:if test="<%=brokerId2 != null%>">&nbsp;<%=brokerId2%></c:if>&nbsp;</td>
		<td class="td_reportMd" >&nbsp;<%=commodityid%></td>
		<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=amount+""%>" pattern="#,##0.00"/></td>
		<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=gageqty+""%>" pattern="#,##0.00"/></td>
		<td class="td_reportRd1">&nbsp;<fmt:formatNumber value="<%=FloatingLoss+""%>" pattern="#,##0.00"/></td>
	    </tr>
	  <%
	    totalAmount+=amount;
	    totaldiAmount+=gageqty;
	    totalMoney+=FloatingLoss;
	    firmAmount+=amount;
	    firmDi+=gageqty;
	    firmMoney+=FloatingLoss;
	}
	 
	 if(list.size()>0){
	 
	 %>
	 <tr>
		<td class="td_reportMd" colspan="3"><b>交易商小计:</b></td>
		<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=firmAmount+""%>" pattern="#,##0.00"/></b></td>
		<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=firmDi+""%>" pattern="#,##0.00"/></b></td>
		<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=firmMoney+""%>" pattern="#,##0.00"/></b></td>
	    </tr>
	<tr>
	<td class="td_reportMd" colspan="3"><b>合计</b></td>
		<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=totalAmount+""%>" pattern="#,##0.00"/></b></td>
		<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=totaldiAmount+""%>" pattern="#,##0.00"/></b></td>
		<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=totalMoney+""%>" pattern="#,##0.00"/></b></td>
	</tr>
<%}else{%>
	<tr>
			<td class="td_reportRd" colspan="6">
				无符合条件信息。
			</td>
		</tr>
	<%}%>
</table>