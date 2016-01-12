<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>��������ʷ�ɽ�����</title>
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
	String startBrokerID = request.getParameter("startBrokerID");
    String endBrokerID = request.getParameter("endBrokerID");
    String startFirmID = request.getParameter("startFirmID");
    String endFirmID = request.getParameter("endFirmID");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String cateGoryId = request.getParameter("cateGoryId");

	String brokerFilter = "";
	if (!"".equals(startBrokerID)&&!"".equals(endBrokerID)){
		brokerFilter = " and b.brokerid >= '" + startBrokerID  + "' and b.brokerid <= '" + endBrokerID + "' ";
	}

	String firmFilter = "";
	if (!"".equals(startFirmID)&&!"".equals(endFirmID)){
		firmFilter = " and t.firmid >= '" + startFirmID  + "' and t.firmid <= '" + endFirmID + "' ";
	}

	String firmCategorynameFilter = "";
	if (!"".equals(cateGoryId)){
		firmCategorynameFilter = " and m.firmCategoryid = " + cateGoryId + " ";
	}

	String sql =
"select nvl(b.brokerid,'-') brokerid," +
"       t.firmid firmid," +
"       t.commodityid commodityid," +
"       sum(t.close_pl) close_pl," +
"       sum(t.tradefee) tradefee," +
"       sum(t.quantity) quantity," +
"       m.firmCategoryid firmCategoryid" +
"  from t_h_trade t, m_firm m, BR_FirmAndBroker b" +
" where t.firmid = m.firmid" +
"   and t.firmid = b.firmid(+)" +
brokerFilter + firmFilter + firmCategorynameFilter +
"   and t.cleardate >= to_date('" + beginDate + "','YYYY-MM-DD')" +
"   and t.cleardate <= to_date('" + endDate + "','YYYY-MM-DD')" +
" group by nvl(b.brokerid,'-'), t.firmid, t.commodityid, m.firmCategoryid" +
" order by nvl(b.brokerid,'-'), t.firmid, t.commodityid" ;


    System.out.println("sql="+sql);
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    System.out.println("list :"+ list.size());
    List fl =dao.queryBySQL("select id,name from m_firmcategory");
    %>
    <center class="reportHead">��������ʷ�ɽ�����</center>
	<table align="center" width="100%">
	<tr><td colspan="7"></td></tr>
	<tr>
		<td colspan="7" class="reportRight">
		<%if (!"".equals(startBrokerID)&&!"".equals(endBrokerID)){ %>
		��ʼ������:<%=startBrokerID %>����������:<%=endBrokerID %>;<%} %>
		<%if (!"".equals(startFirmID)&&!"".equals(endFirmID)){ %>
		��ʼ������:<%=startFirmID %>����������:<%=endFirmID %>;
		<%} %>
		��ʼ����:<%=beginDate %>��������:<%=endDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="1000px">
	<tr>
	  <td class="td_reportMdHead">��Ա���</td>
	  <td class="td_reportMdHead">�����̴���</td>
	  <td class="td_reportMdHead">��Ʒ����</td>
	  <td class="td_reportMdHead">ת��ӯ��</td>
	  <td class="td_reportMdHead">������</td>
	  <td class="td_reportMdHead">�ɽ���</td>
	  <td class="td_reportMdHead">���������</td>
	</tr>
    <%
    
    		BigDecimal countCLOSE_PL = new BigDecimal(0);//�ܼ�
	   		BigDecimal countTRADEFEE = new BigDecimal(0);
	   		BigDecimal countQUANTITY = new BigDecimal(0);
	   		
	   		
    	for(int i = 0 ; i < list.size() ; i++){
    		Map innerMap = (Map)list.get(i);
	%>
	<tr>
	  <td class="td_reportMd"><%=turnToStr(innerMap.get("BROKERID")) %></td>
	  <td class="td_reportMd"><%=turnToStr(innerMap.get("FIRMID")) %></td>
	  <td class="td_reportMd"><%=turnToStr(innerMap.get("COMMODITYID")) %></td>
	  <td class="td_reportMd1"><fmt:formatNumber value='<%=turnToNum(innerMap.get("CLOSE_PL")) %>' pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value='<%=turnToNum(innerMap.get("TRADEFEE")) %>' pattern="#,##0.00"/></td>
	  <td class="td_reportMd1"><fmt:formatNumber value='<%=turnToNum(innerMap.get("QUANTITY")) %>' pattern="#,##0"/></td>
	  <%
	  if(innerMap.get("FIRMCATEGORYID")!=null){
	  	String fid = innerMap.get("FIRMCATEGORYID").toString(); 
	  	for(int j=0;j<fl.size();j++){
	  		Map map = (Map)fl.get(j);
	  		String id = map.get("ID").toString();
	  			if(fid.equals(id)){
	  		%>
	  			<td class="td_reportMd"><%=map.get("NAME")%></td>
			<%}
	  	 }
	  	if(fid.equals("-1")){
	  	%>
	  	 <td class="td_reportMd">&nbsp;</td>
	  	<%
	  	}
	  }%>
	
	  </tr>
	 <%
	 	countCLOSE_PL = countCLOSE_PL.add(turnToNum(innerMap.get("CLOSE_PL")));
   		countTRADEFEE = countTRADEFEE.add(turnToNum(innerMap.get("TRADEFEE")));
   		countQUANTITY = countQUANTITY.add(turnToNum(innerMap.get("QUANTITY")));
	 
	 }%>
	 
	 <tr>
	<td class="td_reportMd" colspan="3"><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value='<%=countCLOSE_PL %>' pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value='<%=countTRADEFEE %>' pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value='<%=countQUANTITY %>' pattern="#,##0"/></b></b></td>
	<td class="td_reportMd1"><b>&nbsp;</b></td>
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
