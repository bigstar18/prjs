<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/report.css" type="text/css"/>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>资金结算表</title>
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
	String brokerId = request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	
	String filter = " ";
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
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
		}
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
	}
	String sql ="select t.firmId,m.name from t_h_firm t,m_firm m where t.firmId=m.firmId "+filter+" order by t.firmid";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">无符合条件信息。</font></b></div>
    <%
    }
    for(int i=0;i<list.size();i++)
    {
         Map firmIDMap = (Map)list.get(i);
    	String firmId = (String)firmIDMap.get("firmId");
    	String name = (String)firmIDMap.get("name");
    	String brokerSql="select brokerId from BR_FirmAndBroker where firmId='"+firmId+"'";
    	List brokerList=dao.queryBySQL(brokerSql);
    	String brokerId2=null;
    	if(brokerList.size()>0){
    		Map broker=(Map)brokerList.get(0);
    		brokerId2=(String)broker.get("brokerId");
    	}
%>
<br><center class="reportHead">资金结算表</center><br>	
<table width="600px" align="center" >
	<tr>
		<td class="reportLeft">交易商代码:<%=firmId %></td>
		<td class="reportLeft" >交易商名称:<%=name%></td>
		<td class="reportLeft" >
		<c:if test="<%=brokerId2 != null%>">
		加盟商:&nbsp;<%=brokerId2%></c:if>
		</td>
		<td class="reportLeft" width="100px">&nbsp;</td>
		<td class="reportRight">单位:元</td>
		<td class="reportRight">日期:<%=cleardate %></td>
	</tr>
</table>
    <%
       sql="select t.lastbalance,t.todaybalance from f_firmbalance t where firmId='"+firmId+"' "+filter;
       sql=sql.replaceAll("cleardate","b_date");
       List firmbalanceList=dao.queryBySQL(sql);
       Object lastbalance=new BigDecimal(0);
       Object todaybalance=new BigDecimal(0);
       if(firmbalanceList!=null&&firmbalanceList.size()>0)
       {
            Map map=(Map)firmbalanceList.get(0);
            lastbalance=map.get("lastbalance");
            todaybalance=map.get("todaybalance");
       }
       sql="select ClearMargin-ClearAssure ClearAssure,ClearFL,ClearSettleMargin,RuntimeMargin-RuntimeAssure RuntimeMargin,"+
       "RuntimeFL,RuntimeSettleMargin,MinClearDeposit,MaxOverdraft from t_h_firm t where firmId='"+firmId+"' "+filter;
       List thFirmList=dao.queryBySQL(sql);
       Object ClearAssure=new BigDecimal(0);
       Object ClearFL=new BigDecimal(0);
       Object ClearSettleMargin=new BigDecimal(0);
       Object RuntimeMargin=new BigDecimal(0);
       Object RuntimeFL=new BigDecimal(0);
       Object RuntimeSettleMargin=new BigDecimal(0);
       Object MinClearDeposit=new BigDecimal(0);
       Object MaxOverdraft=new BigDecimal(0);
       if(thFirmList!=null&&thFirmList.size()>0)
       {
          Map map=(Map)thFirmList.get(0);
          ClearAssure=map.get("ClearAssure");
          ClearFL=map.get("ClearFL");
          ClearSettleMargin=map.get("ClearSettleMargin");
          RuntimeMargin=map.get("RuntimeMargin");
          RuntimeFL=map.get("RuntimeFL");
          RuntimeSettleMargin=map.get("RuntimeSettleMargin");
          MinClearDeposit=map.get("MinClearDeposit");
          MaxOverdraft=map.get("MaxOverdraft");
       }
       sql = "select nvl(sum(t.floatingloss),0) floatingloss from t_h_firmholdsum t where t.firmid = '" + firmId + "'" + filter;
       List floatingLossList = dao.queryBySQL(sql);
       Object floatingloss = new BigDecimal(0);
       if (floatingLossList != null && floatingLossList.size() > 0) {
       		Map map = (Map)floatingLossList.get(0);
       		floatingloss = map.get("floatingloss");
       }
       double runtimeRight = ((BigDecimal)todaybalance).doubleValue() + ((BigDecimal)RuntimeMargin).doubleValue() + ((BigDecimal)RuntimeFL).doubleValue() + ((BigDecimal)RuntimeSettleMargin).doubleValue() + ((BigDecimal)floatingloss).doubleValue();
       filter=filter.replaceAll("t\\.","");
       sql="select '('||(case when f.fieldsign>0 then '+' else '-' end)||')'||f.name name,nvl(t.value,0) value  from (select * from f_clientledger where  firmId='"+firmId+"' "+filter+""+  
           " ) t,f_ledgerfield f "+
           "where f.code=t.code(+) and f.moduleid in (11,15) order by f.moduleid,f.ordernum";
       sql=sql.replaceAll("cleardate","b_date");
       List clientledgerList=dao.queryBySQL(sql);

    %>
<table width="600px" height="200px" align="center" border="0" class="reportTemp">
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日资金余额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="600px">&nbsp;<fmt:formatNumber value="<%=lastbalance.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<%
	  if(clientledgerList!=null&&clientledgerList.size()>0)
	  {
	    for(int a=0;a<clientledgerList.size();a++)
	    {
	      Map map=(Map)clientledgerList.get(a);
	      %>
	      <tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">&nbsp;<%=map.get("name").toString()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(map.get("value"))%></td>
	     </tr>
	      <%
	    }
	  }
          sql="select nvl(sum(case when f.fieldsign>0 then t.value else -t.value end),0) value  from (select * from f_clientledger where  firmId='"+firmId+"' "+filter+""+  
           " ) t,f_ledgerfield f "+
           "where f.code=t.code(+) and f.moduleid not in (11,15) order by f.moduleid,f.ordernum";
          sql=sql.replaceAll("cleardate","b_date");
          List clientledgerOtherList=dao.queryBySQL(sql);
	  if(clientledgerOtherList!=null&&clientledgerOtherList.size()>0)
	  {
              
	      Map map=(Map)clientledgerOtherList.get(0);
	      %>
	      <tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)其他交易系统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum(map.get("value"))%></td>
	     </tr>
	      <%
	    
	  }


	 %>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">当日资金余额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=todaybalance.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)当日保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=RuntimeMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)当日浮亏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=RuntimeFL.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)当日交收保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=RuntimeSettleMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)浮动盈亏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=floatingloss.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">当日权益&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(runtimeRight)%></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=ClearAssure.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日浮亏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=ClearFL.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">上日交收保证金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=ClearSettleMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">结算准备金最低限额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=MinClearDeposit.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">需追加资金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<%
		  double superAdd=0;
		   if(((BigDecimal)todaybalance).doubleValue()+((BigDecimal)MaxOverdraft).doubleValue()-((BigDecimal)MinClearDeposit).doubleValue()>=0)
		   {
		     
		   }
		   else
		   {
		      superAdd=((BigDecimal)MinClearDeposit).doubleValue()-((BigDecimal)todaybalance).doubleValue()-((BigDecimal)MaxOverdraft).doubleValue();
		   }
		%>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(superAdd)%></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">质押资金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=MaxOverdraft.toString()%>" pattern="#,##0.00"/></td>
	</tr>
</table>
<br>
<br>
<br>
<br>
<br>
<br>
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