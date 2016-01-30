<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<%@ include file="/mgr/app/timebargain/printReport/excelExpFile.jsp"%>


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
	String startCleardate = request.getParameter("startClearDate");
	String endCleardate = request.getParameter("endClearDate");
	String startCommodityID = request.getParameter("startCommodityID");
	String endCommodityID = request.getParameter("endCommodityID");
	String breedId =request.getParameter("breedId");
	String brokerId =request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	
	String filter = " 1=1 ";
	String filter1 = " 1=1 ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null")){
			filter += " and t.firmid>='"+startFirmID +"'";
			filter1 += " and s.firmid>='"+startFirmID +"'";
		}
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null")){
			filter += " and t.firmid<='"+endFirmID +"'";
			filter1 += " and s.firmid<='"+endFirmID +"'";
		}
	}
	if(chcekNull(startCleardate)){
		filter += " and t.cleardate>=to_date('"+startCleardate +"','yyyy-MM-dd')";
		filter1 += " and s.settleprocessdate>=to_date('"+startCleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endCleardate)){
		filter += " and t.cleardate<=to_date('"+endCleardate +"','yyyy-MM-dd')";
		filter1 += " and s.settleprocessdate<=to_date('"+endCleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(startCommodityID)){
		if(!startCommodityID.equals("null")){
			filter += " and t.commodityid>='"+startCommodityID+"'";
			filter1 += " and s.commodityid>='"+startCommodityID +"'";
		}
	}
	if(chcekNull(endCommodityID)){
		if(!endCommodityID.equals("null")){
			filter += " and t.commodityid<='"+endCommodityID+"'";
			filter1 += " and s.commodityid<='"+endCommodityID +"'";
		}
	}
	if(chcekNull(breedId)){
		if(!breedId.equals("null")){
			filter+=" and t.commodityID in (select commodityId from t_commodity where breedId='"+breedId+"') ";
			filter1+=" and s.commodityID in (select commodityId from t_commodity where breedId='"+breedId+"') ";
		}
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
			filter1+=" and s.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
		}
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
		    filter1 +=" and s.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
	}
	String sql = "select u.firmID,u.commodityID,nvl(t.sumQuantity,0) sumQuantity,nvl(t.sumClose_PL,0) sumClose_PL,nvl(s.tranAmount,0) sumTranAmount, nvl(s.settle_pl,0) sumSettle_PL from (select firmid,commodityid from (select distinct s.firmid,s.commodityid from T_SettleHoldPosition s where "+filter1+" group by s.firmid,s.commodityid union select distinct t.firmid, t.commodityid from T_H_Trade t where t.ordertype = 2 and "+filter+" group by t.firmid,t.commodityid)) u, (select t.firmid, t.commodityid, nvl(sum(t.quantity),0) sumQuantity,nvl(sum(t.Close_PL),0) sumClose_PL from T_H_Trade t where t.ordertype = 2 and "+filter+" group by t.firmid,t.commodityid) t, (select s.firmid,s.commodityid,nvl(sum(s.holdqty+s.gageqty),0) tranAmount, nvl(sum(s.settle_pl),0) settle_pl from T_SettleHoldPosition s where "+filter1+" group by s.firmid,s.commodityid) s where u.firmID = t.firmID(+) and u.firmID = s.firmID(+) and u.commodityID = t.commodityID(+) and u.commodityID = s.commodityID(+)";
			DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
		    List list=dao.queryBySQL(sql);     	
 %>
	<br>
	<center class="reportHead">ת�úͽ���ӯ��ͳ�Ʊ�</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft">
		<%if(!("null".equals(startFirmID) || "null".equals(endFirmID))){ %>
		��ʼ������:<%=startFirmID%>&nbsp;����������:&nbsp;<%=endFirmID%>&nbsp;
		<%} %>&nbsp;
		</td>
		<td class="reportRight">��ʼ����:<%=startCleardate %>&nbsp;��������:<%=endCleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">ת������</td>
	<td class="td_reportMdHead">ת��ӯ��</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportRdHead">����ӯ��</td>
	</tr>
		<%
	   	BigDecimal sumQuantity = new BigDecimal(0);//С����
	   	BigDecimal sumClose_PL = new BigDecimal(0);	   	
	   	BigDecimal sumTranAmount = new BigDecimal(0);
	   	BigDecimal sumSettle_PL = new BigDecimal(0);
	   	
	   	BigDecimal countQuantity = new BigDecimal(0);//�ܼ���
	   	BigDecimal countClose_PL = new BigDecimal(0);	   	
	   	BigDecimal countTranAmount = new BigDecimal(0);
	   	BigDecimal countSettle_PL = new BigDecimal(0);
	   	
	   	String mark = null;//���
	   	int marknum = 0;
		int size = list.size()-1;
		for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a); 
		marknum = a;
	   	
		if(!turnToStr(innerMap.get("firmId")).equals(mark)){
			if(mark != null){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>������С��</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumClose_PL %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTranAmount %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumSettle_PL %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%	   	
				sumQuantity = new BigDecimal(0);
	   		sumClose_PL = new BigDecimal(0);	   	
	   		sumTranAmount = new BigDecimal(0);
	   		sumSettle_PL = new BigDecimal(0);
		}
	}
			mark = turnToStr(innerMap.get("firmId"));
	%>	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmId")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumQuantity")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("sumClose_PL")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sumTranAmount")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("sumSettle_PL")) %></td>
	</tr>
	<%
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("sumQuantity")));
		sumClose_PL = sumClose_PL.add(turnToNum(innerMap.get("sumClose_PL")));
		sumTranAmount = sumTranAmount.add(turnToNum(innerMap.get("sumTranAmount")));
		sumSettle_PL = sumSettle_PL.add(turnToNum(innerMap.get("sumSettle_PL")));
		
		countQuantity = countQuantity.add(turnToNum(innerMap.get("sumQuantity")));
   	countClose_PL = countClose_PL.add(turnToNum(innerMap.get("sumClose_PL")));  	
   	countTranAmount = countTranAmount.add(turnToNum(innerMap.get("sumTranAmount")));
   	countSettle_PL = countSettle_PL.add(turnToNum(innerMap.get("sumSettle_PL")));	
	}
	if(size == marknum){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>������С��</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumClose_PL %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumTranAmount %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumSettle_PL %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}
	if(list.size()>0){
	%>
	<tr>
	<td class="td_reportMd" colspan="2"><b>�ϼ�</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countQuantity %></b></td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=countClose_PL %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countTranAmount %></b></td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=countSettle_PL %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%}else{%>
		<tr>
			<td class="td_reportRd" colspan="6">
				�޷���������Ϣ��
			</td>
		</tr>
		<%}%>
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