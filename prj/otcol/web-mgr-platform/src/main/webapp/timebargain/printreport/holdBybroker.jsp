<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
try{
	//test filter:startFirmID=1001&endFirmID=1005&cleardate=2009-03-04
	//get query condition and handle 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startClearDate");
	String filter = "";
	if(chcekNull(startFirmID)){
		filter += " and a.brokerid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and a.brokerid<='"+endFirmID +"'";
	}
	
	if(chcekNull(startClearDate)){
		filter += " and c.cleardate=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	//query data
//	String sql = " select brokerid,name from m_b_broker where brokerid>='"+startFirmID +"' and brokerid<='"+endFirmID +"' order by brokerid";
//	 List brokerList=dao.queryBySQL(sql);
//	 List breedList=dao.queryBySQL("select breedid,breedname from t_a_breed order by SortID, breedid");
	String sql = " select a.brokerid,a.name,c.commodityid,(case when bs_flag=1 then '��' else '��' end ) bs_flag,sum(c.HoldQty) HoldQty,sum(c.HoldMargin) HoldMargin,sum(c.FloatingLoss) FloatingLoss";
		   sql+=" from m_b_broker a,m_b_firmandbroker b, t_h_firmholdsum c where a.brokerid=b.brokerid and b.firmid=c.firmid ";
		   sql+=filter;
		   sql+=" group by a.brokerid,a.name,c.commodityid,c.bs_flag  order by a.brokerid,c.commodityid,c.bs_flag";   
    List list=dao.queryBySQL(sql);
    BigDecimal countHoldQty = new BigDecimal("0.00");//�����ܼ�
	BigDecimal countHoldMargin = new BigDecimal("0.00");
	BigDecimal countFloatingLoss = new BigDecimal("0.00");

	
	String brokername="";
	BigDecimal sumHoldQty = new BigDecimal("0.00");//����С��
	BigDecimal sumHoldMargin = new BigDecimal("0.00");
	BigDecimal sumFloatingLoss = new BigDecimal("0.00");
	String mark = null;//���ڱ��
	int marknum = 0;
	int size = list.size()-1;
		%>
	<br><center class="reportHead">�ּ����̶���ͳ�Ʊ�</center><br><br>
	<table align="center" width="900px" border="0">
	<tr>
		
		<td class="reportRight" colspan="6">����:&nbsp;<%=startClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="900px">
	<tr>
	<td class="td_reportMdHead">��Ա����</td>
	<td class="td_reportMdHead">��Ա�˺�</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportMdHead">������</td>
	<td class="td_reportMdHead">��֤��</td>
	<td class="td_reportRdHead">����ӯ��</td>
	</tr>
		<%
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
    		marknum = i;
    		if(!turnToStr(innerMap.get("brokerid")).equals(mark)){
    			if( mark != null){
    	 %>
    	<tr>
    	<td class="td_reportMd" colspan="4">&nbsp;������<%=brokername %>С��</td>
    	<td class="td_reportMd1" ><fmt:formatNumber value="<%=sumHoldQty %>" pattern="#,##0"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumHoldMargin %>" pattern="#,##0.00"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumFloatingLoss %>" pattern="#,##0.00"/></td>
    	</tr>
    	 <%						
    				sumHoldQty = new BigDecimal("0");//����С��
    				sumHoldMargin = new BigDecimal("0.00");
    				sumFloatingLoss = new BigDecimal("0.00");
    	 		}
    		mark = turnToStr(innerMap.get("brokerid"));
    		}		
		%> 	
			<tr>
			<td class="td_reportMd" align="left">&nbsp;<%=turnToStr(innerMap.get("name")) %></td>
			<td class="td_reportMd"><%=turnToStr(innerMap.get("brokerid")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
			<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("bs_flag")) %></td>
			<td class="td_reportMd1"><fmt:formatNumber value="<%=innerMap.get("HoldQty") %>" pattern="#,##0"/></td>
			<td class="td_reportMd1"><fmt:formatNumber value="<%=innerMap.get("HoldMargin") %>" pattern="#,##0.00"/></td>
			<td class="td_reportMd1"><fmt:formatNumber value="<%=innerMap.get("FloatingLoss") %>" pattern="#,##0.00"/></td>
			</tr>
		<%
		sumHoldQty = sumHoldQty.add(turnToNum(innerMap.get("HoldQty")));
		sumHoldMargin = sumHoldMargin.add(turnToNum(innerMap.get("HoldMargin")));
		sumFloatingLoss = sumFloatingLoss.add(turnToNum(innerMap.get("FloatingLoss")));
		
		countHoldQty = countHoldQty.add(turnToNum(innerMap.get("HoldQty")));
		countHoldMargin = countHoldMargin.add(turnToNum(innerMap.get("HoldMargin")));
		countFloatingLoss = countFloatingLoss.add(turnToNum(innerMap.get("FloatingLoss")));
		
		brokername =turnToStr(innerMap.get("name"));
    	}
		if(size == marknum){
			%>	
			<tr>
    	<td class="td_reportMd" colspan="4"">&nbsp;������<%=brokername %>С��</td>
    	<td class="td_reportMd1" ><fmt:formatNumber value="<%=sumHoldQty %>" pattern="#,##0"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumHoldMargin %>" pattern="#,##0.00"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=sumFloatingLoss %>" pattern="#,##0.00"/></td>
    	</tr>
			<%
			}
		if(list.size()>0){
			%>
			<tr>
    	<td class="td_reportMd" colspan="4"">�������ܼ�</td>
    	<td class="td_reportMd1" ><fmt:formatNumber value="<%=countHoldQty %>" pattern="#,##0"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=countHoldMargin %>" pattern="#,##0.00"/></td>
    	<td class="td_reportRd1" ><fmt:formatNumber value="<%=countFloatingLoss %>" pattern="#,##0.00"/></td>
    	</tr>
		<%}%>
	</table>
	<br><br><br><br><br>
	<% }catch(Exception e){
		e.printStackTrace();
		
	} %>
	