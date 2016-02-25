<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String brokerId =request.getParameter("brokerId");
	String filter = " 1=1 ";	
	if(chcekNull(startClearDate)){
		filter += " and t.SettleProcessDate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.SettleProcessDate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startFirmID)){ 
		if(!startFirmID.equals("null"))
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
			filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	}
	//query data
	String sql = "select t.firmid, to_char(t.settleprocessdate,'yyyy-MM-dd') settleprocessdate, t.commodityid, t.price, t.bs_flag, t.settleprice, t.settle_pl, t.payout, t.settlefee from T_SettleHoldPosition t where " + filter;
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
    List list=dao.queryBySQL(sql);
    
    %>
    <br><center class="reportHead">�����̽��������</center><br><br>
	<table align="center" width="600px">
	<tr><td colspan="9"></td></tr>
	<tr>
		<td class="reportLeft">
		<%if(!("null".equals(startFirmID) || "null".equals(endFirmID))){ %>
		��ʼ������:<%=startFirmID%>&nbsp;����������:&nbsp;<%=endFirmID%>&nbsp;
		<%} %>
		<%if(!"null".equals(brokerId)){ %>
		������:&nbsp;<%=brokerId %>
		<%} %>
		</td>
		<td colspan="5" width="10%">&nbsp;&nbsp;</td>
		<td class="reportRight">��ʼ����:<%=startClearDate %>&nbsp;��������:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="700px">
	<tr>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">������</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">������</td>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportMdHead">���ս����</td>
	<td class="td_reportMdHead">����ӯ��</td>
	<td class="td_reportMdHead">���ջ���</td>
	<td class="td_reportRdHead">����������</td>
	</tr>
    <% 
    	BigDecimal sumSettle_pl = new BigDecimal("0");
    	BigDecimal sumPayout = new BigDecimal("0");
    	BigDecimal sumSettlefee = new BigDecimal("0");
    	for(int a = 0 ; a < list.size() ; a ++){
    		Map innerMap = (Map)list.get(a);
    		String getFirmId=(String)innerMap.get("firmId");
			String relBS_flag = "";
			if (innerMap.get("bs_flag") != null) {
				if ("1".equals(innerMap.get("bs_flag").toString())) {
					relBS_flag = "��";
				}else if ("2".equals(innerMap.get("bs_flag").toString())) {
					relBS_flag = "��";
				}
			}
			String brokerSql="select brokerId from M_B_FIRMANDBROKER where firmId='"+getFirmId+"'";
			List brokerList=dao.queryBySQL(brokerSql);
			String brokerId2=null;
			if(brokerList.size()>0){
				Map broker=(Map)brokerList.get(0);
				brokerId2=(String)broker.get("brokerId");
			}
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmID")) %></td>
	<td class="td_reportMd">&nbsp;
	<% if(brokerId2 !=null) {%>
	<%=brokerId2 %>
	<%} %>
	</td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("settleprocessdate")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("price")) %></td>
	<td class="td_reportMd">&nbsp;<%=relBS_flag%></td>
	<td class="td_reportMd1">&nbsp;<fmt:formatNumber value="<%=turnToNum(innerMap.get("settleprice")) %>" pattern="#,##0.00"/></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("settle_pl")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToNum(innerMap.get("payout")) %></td>
	<td class="td_reportRd">&nbsp;<%=turnToNum(innerMap.get("settlefee")) %></td>
	</tr>
	<%
		sumSettle_pl = sumSettle_pl.add(turnToNum(innerMap.get("settle_pl")));
		sumPayout = sumPayout.add(turnToNum(innerMap.get("payout")));
		sumSettlefee = sumSettlefee.add(turnToNum(innerMap.get("settlefee")));
	}
		if (list.size() > 0) {
	%>
	<tr>
		<td class="td_reportMd" colspan="2"><b>�ϼ�</b></td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd">&nbsp;<%=sumSettle_pl%></td>
		<td class="td_reportMd">&nbsp;<%=sumPayout%></td>
		<td class="td_reportRd">&nbsp;<%=sumSettlefee%></td>
	</tr>
	<%
		}
	%>
</table>