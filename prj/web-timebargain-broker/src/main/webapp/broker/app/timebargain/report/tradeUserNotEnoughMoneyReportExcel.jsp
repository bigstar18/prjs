<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<%@ include file="common/excelExpFile.jsp"%>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:m="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
</head>
<body>
<table align="center">
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
 <%
    String startFirmID = request.getParameter("startFirmID");
    String endFirmID = request.getParameter("endFirmID");
    String cleardate = request.getParameter("cleardate");
    String firmcategory = request.getParameter("firmcategory");
    String brokerageID = request.getParameter("brokerageId");
    User user=(User)request.getSession().getAttribute("CurrentUser");
	
    String filter = " 1=1 and t.firmid in ("+ user.getSql() +") ";
    
    if(chcekNull(startFirmID)){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(cleardate)){
		 // filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(firmcategory)){
		filter += " and m.firmcategoryid = '"+firmcategory+"'";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//会员添加居间条件
		filter += " and t.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}

	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
		String sql = "select t.firmid firmID,m.name firmName,t.todaybalance todaybalance,f.mincleardeposit mincleardeposit,f.mincleardeposit-t.todaybalance addMoney,f.MaxOverdraft from f_firmbalance t,t_h_firm f,m_firm m where "+filter+" and t.firmid=f.firmid and t.firmid=m.firmid and t.b_date=f.cleardate and t.b_date=to_date('"+cleardate+"','yyyy-MM-dd') and t.todaybalance<f.mincleardeposit order by t.firmId";
		List list=dao.queryBySQL(sql);
		%>
	 <br><center class="reportHead">资金不足交易商情况表</center>	
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportRight" colspan="6">日期:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">交易商代码</td>
	<td class="td_reportMdHead">交易商名称</td>
	<td class="td_reportMdHead">本日资金余额</td>
	<td class="td_reportMdHead">结算准备金最低限额</td>
	<td class="td_reportMdHead">质押资金</td>
	<td class="td_reportRdHead">需追加资金</td>
	</tr>
		<%
		Object todaybalance=new BigDecimal(0);
		Object MaxOverdraft=new BigDecimal(0);
		Object MinClearDeposit=new BigDecimal(0);
		
		if (list != null && list.size() > 0) {
    		for(int b = 0 ; b < list.size() ; b++){
    			Map innerMap = (Map)list.get(b);
    			MinClearDeposit=innerMap.get("MinClearDeposit");
          		MaxOverdraft=innerMap.get("MaxOverdraft");
          		todaybalance=innerMap.get("todaybalance");
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmID")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmName")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("todaybalance")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("mincleardeposit")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("MaxOverdraft")) %></td>
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
	<td class="td_reportRd1">&nbsp;<fmt:formatNumber value="<%=superAdd %>" pattern="#,##0.00"/></td>
	</tr>
	<%
		}
	}
	if(list == null || list.size() <= 0){
	%>
	<tr>
	<td class="td_reportRd" colspan="6">无符合条件信息。</td>
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
			</td>	
		</tr>
</table>
</body>
</html>