<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-04
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String firmID = request.getParameter("firmID");
	String cleardate = request.getParameter("cleardate");
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
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
			filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	}
	//query data
	String sql = " select t.firmId,m.name from t_h_trade t,m_firm m where "+filter+" and t.firmid=m.firmid group by t.firmid,m.name order by t.firmid,m.name";
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	List list=dao.queryBySQL(sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">�޷���������Ϣ��</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map firmIDMap = (Map)list.get(a);
    	String getFirmId = (String)firmIDMap.get("firmId");
    	String innerSql = " select a_tradeno,customerid,commodityid, "+
    	" (case when bs_flag=1 then '���' else '����' end)||(case when ordertype=1 then '����' else 'ת��' end) type, "+
		" price,quantity,to_char(tradetime,'hh24:mi:ss') tradetime, "+
		" tradefee from "+
		" (select t.a_tradeno,t.customerid,t.commodityid,t.bs_flag,t.ordertype,t.price,t.quantity,t.tradetime,t.tradefee "+
		" from t_h_trade t where t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+getFirmId +"' order by t.commodityid,a_tradeno )";
    	
	   	BigDecimal sumQuantity = new BigDecimal(0);
	   	BigDecimal sumTradefee = new BigDecimal(0.0);
	   	String brokerSql="select brokerId from M_B_FIRMANDBROKER where firmId='"+getFirmId+"'";
		List brokerList=dao.queryBySQL(brokerSql);
		String brokerId2=null;
		if(brokerList.size()>0){
			Map broker=(Map)brokerList.get(0);
			brokerId2=(String)broker.get("brokerId");
		}
	   	
		%>      
	<br><center class="reportHead">�ɽ���¼��</center><br>	
	<table align="center" width="600px" border="0">
	<tr><td colspan="7"></td></tr>
	<tr>
		<td  class="reportLeft">�����̴���:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %></td>
		<td  class="reportLeft">����������:&nbsp;<%=turnToStr(firmIDMap.get("name")) %></td>
		<td  class="reportLeft"><c:if test="<%=brokerId2 != null%>">
		������:&nbsp;<%=brokerId2%></c:if></td>
		<td  class="reportRight" colspan="4">����:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px" border="0">
	<tr>
	<td class="td_reportMdHead">�ɽ����</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">�ɽ��۸�</td>
	<td class="td_reportMdHead">����(��)</td>
	<td class="td_reportMdHead">�ɽ�ʱ��</td>
	<td class="td_reportRdHead">����������</td>
	</tr>
		<%
    	List innerlist=dao.queryBySQL(innerSql);
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
		%> 	
	<tr>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("a_tradeno")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("price")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("tradetime")) %></td>
	<td class="td_reportRd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("tradefee")) %>" pattern="#,##0.00"/></td>
	</tr>
	<%
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("quantity")));
		sumTradefee = sumTradefee.add(turnToNum(innerMap.get("tradefee")));
	}
	
	if(innerlist.size()>0){
	
	%>
	<tr>
	<td class="td_reportMd"><b>�ϼ�</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradefee %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}else{
	%>
		<tr>
			<td class="td_reportRd" colspan="8">
				�޷���������Ϣ��
			</td>
		</tr>
	<%
	}
	%>
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