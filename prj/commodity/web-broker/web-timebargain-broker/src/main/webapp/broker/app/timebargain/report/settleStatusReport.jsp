<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>������̩ - ���������</title>
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
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
<%
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startdate = request.getParameter("startdate");
	String enddate = request.getParameter("enddate");
	String firmcategory = request.getParameter("firmcategory");
	String brokerageID = request.getParameter("brokerageId");
	User user=(User)request.getSession().getAttribute("CurrentUser");
	String filter = " and t.firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(startdate)){
		filter += " and t.settleprocessdate>=to_date('"+startdate +"','yyyy-MM-dd')";
	}
	if(chcekNull(enddate)){
		filter += " and t.settleprocessdate<=to_date('"+enddate +"','yyyy-MM-dd')";
	}
	if(chcekNull(firmcategory)){
		filter += " and t.firmid in ( select f.firmid from m_firm f where f.firmcategoryid = '"+firmcategory+"')";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//��Ա��ӾӼ�����
		filter += " and t.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}

    	String innerSql = "select to_char(t.settleprocessdate,'yyyy-MM-dd') settleprocessdate,"+
	       "t.commodityid commodityid,"+
	       "decode(t.bs_flag, 1, '��', 2, '����') bs_flag,"+
	       "t.firmid firmid,"+
	       "sum(t.holdqty + t.gageqty) quantity,"+
	       "sum(t.price * (t.holdqty + t.gageqty)) / sum(t.holdqty + t.gageqty) avgprice,"+
	       "max(t.settleprice) settleprice,"+
	      "sum(t.settleprice *(t.holdqty + t.gageqty)*(select c.contractfactor from t_commodity c where c.commodityid=t.commodityid)) payout,"+
	       "sum(t.settlemargin) settlemargin"+
		" from t_settleholdposition t where 1=1 "+filter+" group by t.settleprocessdate, t.commodityid, t.bs_flag, t.firmid order by t.settleprocessdate, t.commodityid, t.bs_flag, t.firmid asc";
    	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
        List list=dao.queryBySQL(innerSql);

	   	
		%>      
	<br/><center class="reportHead">���������</center><br/>
	<table align="center" width="800px" border="0">
		<tr><td colspan="7"></td></tr>
		<tr>
			<td  class="reportLeft">��ʼ�����̴���:&nbsp;<%=startFirmID %></td>
			<td  class="reportLeft">���������̴���:&nbsp;<%=endFirmID %></td>
			<td  class="reportRight" colspan="5">��ʼ����:&nbsp;<%=startdate %></td>
			<td  class="reportRight" colspan="5">��������:&nbsp;<%=enddate %></td>
		</tr>
	</table>
	<table align="center" class="reportTemp" width="800px" border="0">
	<tr>
	<td class="td_reportMdHead">������</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">��/����</td>
	<td class="td_reportMdHead">���״���</td>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportMdHead">ƽ����</td>
	<td class="td_reportMdHead">�����</td>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportRdHead">���֤��</td>
	</tr>
	<c:set var="sumQuantity" value="0"/>
	<c:set var="sumPayout" value="0"/>
	<c:set var="sumSettlemargin" value="0"/>
	<c:forEach items="${list}" var="resultValue">	
	<tr>
	<td class="td_reportMd1">${resultValue.SETTLEPROCESSDATE}</td>
	<td class="td_reportMd">${resultValue.COMMODITYID}</td>
	<td class="td_reportMd">${resultValue.BS_FLAG}</td>
	<td class="td_reportMd">${resultValue.FIRMID}</td>
	<td class="td_reportMd1"><fmt:formatNumber value="${resultValue.QUANTITY}" pattern="#,##0.00"/></td>
	<td class="td_reportMd1"><fmt:formatNumber value="${resultValue.AVGPRICE}" pattern="#,##0.00"/></td>
	<td class="td_reportMd"><fmt:formatNumber value="${resultValue.SETTLEPRICE}" pattern="#,##0.00"/></td>
	<td class="td_reportMd"><fmt:formatNumber value="${resultValue.PAYOUT}" pattern="#,##0.00"/></td>
	<td class="td_reportRd1"><fmt:formatNumber value="${resultValue.SETTLEMARGIN}" pattern="#,##0.00"/></td>
	</tr>
	<tr>
	<td class="td_reportMd1" >&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">С��:</td>
	<td class="td_reportMd1"><fmt:formatNumber value="${resultValue.QUANTITY}" pattern="#,##0.00"/></td>
	<td class="td_reportMd1" colspan=2>&nbsp;</td>
	
	<td class="td_reportMd"><fmt:formatNumber value="${resultValue.PAYOUT}" pattern="#,##0.00"/></td>
	<td class="td_reportRd1"><fmt:formatNumber value="${resultValue.SETTLEMARGIN}" pattern="#,##0.00"/></td>
	</tr>
	<c:set var="sumQuantity" value="${sumQuantity+resultValue.QUANTITY}"/>
	<c:set var="sumPayout" value="${sumPayout+resultValue.PAYOUT}"/>
	<c:set var="sumSettlemargin" value="${sumSettlemargin+resultValue.SETTLEMARGIN}"/>
	</c:forEach>
	
	<tr>
	<td class="td_reportMd" colspan=4><b>�ϼ�</b></td>

	<td class="td_reportMd"><b><fmt:formatNumber value="${sumQuantity}" pattern="#,##0.00"/></b></td>
	<td class="td_reportMd1" colspan=2><b>&nbsp;</b></td>

	<td class="td_reportMd"><b><fmt:formatNumber value="${sumPayout}" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b><fmt:formatNumber value="${sumSettlemargin}" pattern="#,##0.00"/></b></td>
	</tr>
	

	</table>
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
						     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
