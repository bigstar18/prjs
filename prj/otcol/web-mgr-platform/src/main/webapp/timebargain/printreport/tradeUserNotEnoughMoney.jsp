<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	
	String cleardate = request.getParameter("cleardate");
	
	String filter = " 1=1 ";

	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}


	//query data

	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
		String sql = "select t.firmid firmID,m.name firmName,t.todaybalance todaybalance,f.mincleardeposit mincleardeposit,f.mincleardeposit-t.todaybalance addMoney,f.MaxOverdraft from f_firmbalance t,t_h_firm f,m_firm m where t.firmid=f.firmid and t.firmid=m.firmid and t.b_date=f.cleardate and t.b_date=to_date('"+cleardate+"','yyyy-MM-dd') and t.todaybalance<f.mincleardeposit order by t.firmId";
		List list=dao.queryBySQL(sql);
		%>
	 <br><center class="reportHead">�ʽ��㽻���������</center>	
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportRight" colspan="6">����:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">����������</td>
	<td class="td_reportMdHead">�����ʽ����</td>
	<td class="td_reportMdHead">����׼��������޶�</td>
	<td class="td_reportMdHead">��Ѻ�ʽ�</td>
	<td class="td_reportRdHead">��׷���ʽ�</td>
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
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("todaybalance")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("mincleardeposit")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("MaxOverdraft")) %>" pattern="#,##0.00"/></td>
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
	<td class="td_reportRd1">&nbsp;<fmt:formatNumber value="<%=superAdd+"" %>" pattern="#,##0.00"/></td>
	</tr>
	<%
		}
	}
	if(list == null || list.size() <= 0){
	%>
	<tr>
	<td class="td_reportRd" colspan="6">�޷���������Ϣ��</td>
	</tr>
	<%
	}
	%>
	</table>
