<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	
	String filter = " 1=1 ";	
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select to_char(t.cleardate,'yyyy-MM-dd') cleardate,t.firmid firmid,t.customerid customerid, "+
						" t.commodityid commodityid,'��Ϊ'||(case when t.BS_Flag=1 then '���' else '����' end )||'ת��' type, "+
						" t.price price,t.quantity quantity,trunc(o.ordertime) ordertime,trunc(t.tradetime) tradetime "+
						" from t_h_trade t,t_h_orders o where t.a_orderno=o.a_orderno and "+filter+
						" and t.tradetype=6 and t.ordertype=2 order by to_char(t.cleardate,'yyyy-MM-dd'),t.firmid,t.commodityid";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">��Ϊת�ü�¼��</center>			
	<table align="center" width="600px">
	<tr><td colspan="9"></td></tr>
	<tr>
		<td class="reportRight" colspan="9">��ʼ����:<%=startClearDate %>&nbsp;��������:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">���״���</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">�ɽ���</td>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportMdHead">ί��ʱ��</td>
	<td class="td_reportRdHead">�ɽ�ʱ��</td>
	</tr>
    <% 
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("price")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("ordertime")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("tradetime")) %></td>
	</tr>
	<%
	}
	%>
</table>