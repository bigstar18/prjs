<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.math.*" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/timebargain/printreport/report.css" type="text/css"/>
<%@ page import="java.util.*"  %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>


<title>
�ʽ��������
</title>
<script type="text/javascript"> 

</script>
</head>
<body leftmargin="2" topmargin="0">
	<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
						</div>
					</td>
					</tr>
	</table>
	<br><center class="reportHead">�ʽ������</center><br>

	<table align="center" class="reportTemp" width="600">

	<%
		System.out.println("q");
		List list = (List)request.getAttribute("CustomerFundsTable");
		System.out.println("h");

		if (list != null && list.size() > 0) {
		

			Map map = (Map)list.get(0);
			//String firmID = "//";
			String lastbalance = "";
			BigDecimal in_out_Amount = new BigDecimal(0);
			String close_pl = "";
			String clearMargin = "";
			String clearfl = "";
			String runtimemargin = "";
			String runtimefl = "";
			String orderFrozen = "";
			String otherFrozen = "";
			String usefulFund = "";
			String tradefee = "";
			BigDecimal ot_change = new BigDecimal(0);
			BigDecimal right = new BigDecimal(0);
			String maxOverdraft = "";
			 
			 
			String PL = "";
			//if (map.get("firmid") != null) {
				//firmID = map.get("firmid").toString();
			 
			//} 
			if (map.get("lastbalance") != null) {
				lastbalance = map.get("lastbalance").toString();
			}
			String relIn_out_Amount = "";
			if (map.get("inAmount") != null && map.get("outAmount") != null) {
				in_out_Amount = ((BigDecimal) map.get("inAmount")).subtract((BigDecimal) map.get("outAmount"));
				relIn_out_Amount = in_out_Amount.toString();
			}
			if (map.get("close_pl") != null) {
				close_pl = map.get("close_pl").toString();
			}
			if (map.get("ClearMargin") != null) {
				clearMargin = map.get("ClearMargin").toString();
			}
			if (map.get("clearfl") != null) {
				clearfl = map.get("clearfl").toString();
			}
			if (map.get("runtimemargin") != null) {
				runtimemargin = map.get("runtimemargin").toString();
			}
			if (map.get("runtimefl") != null) {
				runtimefl = map.get("runtimefl").toString();
			}
			if (map.get("orderFrozen") != null) {
				orderFrozen = map.get("orderFrozen").toString();
			}
			if (map.get("otherFrozen") != null) {
				otherFrozen = map.get("otherFrozen").toString();
			}
			if (map.get("usefulFund") != null) {
				usefulFund = map.get("usefulFund").toString();
			}
			if (map.get("tradefee") != null) {
				tradefee = map.get("tradefee").toString();
			}
			String relOt_change = "";
			if (map.get("usefulFund") != null && map.get("lastbalance") != null && map.get("ClearMargin") != null && map.get("ClearFL") != null && map.get("close_pl") != null && map.get("runtimemargin") != null && map.get("runtimefl") != null && map.get("orderFrozen") != null && map.get("tradefee") != null && map.get("otherFrozen") != null) {
				ot_change = (((BigDecimal)map.get("usefulFund")).subtract((BigDecimal)map.get("lastbalance")))
					.subtract(((BigDecimal)map.get("ClearMargin")).add((BigDecimal)map.get("ClearFL")).add(in_out_Amount).add((BigDecimal) map.get("close_pl"))
                    .subtract((BigDecimal) map.get("runtimemargin")).subtract((BigDecimal) map.get("runtimefl")).subtract((BigDecimal) map.get("orderFrozen"))
                    .subtract((BigDecimal) map.get("tradefee")).subtract((BigDecimal) map.get("otherFrozen")));
				relOt_change = ot_change.toString();
			}

			String relRight = "";
			if (map.get("balance") != null) {
				right = ((BigDecimal)map.get("balance"));
			}
			if (map.get("ClearMargin") != null) {
				right = ((BigDecimal)map.get("balance")).add((BigDecimal)map.get("ClearMargin"));
			}
			if (map.get("ClearAssure") != null) {
				right = right.add((BigDecimal)map.get("ClearAssure"));
			}
			if (map.get("RuntimeSettleMargin") != null) {
				right = right.add((BigDecimal)map.get("RuntimeSettleMargin"));
			}
			if (map.get("clearfl") != null) {
				right = right.add((BigDecimal)map.get("clearfl"));
			}
			if (map.get("PL") != null) {
				right = right.add((BigDecimal)map.get("PL"));
			}
			if (map.get("close_pl") != null) {
				right = right.add((BigDecimal)map.get("close_pl"));
			}
			if (map.get("tradefee") != null) {
				right = right.subtract((BigDecimal)map.get("tradefee"));
			}
			if (right != null) {
				relRight = right.toString();
			}
			
			if (map.get("PL") != null) {
				PL = map.get("PL").toString();
				
			}
			
			if (map.get("MaxOverdraft") != null) {
				maxOverdraft = map.get("MaxOverdraft").toString();
			}
	%>
	
		<tr><td class="td_reportMdHead" >�����̴���</td><td class="td_reportMdHead">&nbsp;<%= request.getParameter("firmID") %>&nbsp;</td><td class="td_reportMdHead">&nbsp;</td><td class="td_reportRdHead">&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">�ڳ����</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=lastbalance%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">&nbsp;</td><td class="td_reportRdHead" align="right">&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">+���ձ�֤��</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=clearMargin%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">-���ձ�֤��</td><td class="td_reportRdHead" align="right"><fmt:formatNumber value="<%=runtimemargin%>" pattern="#,##0.00"/>&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">+���ո���</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=clearfl%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">-���ո���</td><td class="td_reportRdHead" align="right"><fmt:formatNumber value="<%=runtimefl%>" pattern="#,##0.00"/>&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">+�����</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=relIn_out_Amount%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">-ί�ж����ʽ�</td><td class="td_reportRdHead" align="right"><fmt:formatNumber value="<%=orderFrozen%>" pattern="#,##0.00"/>&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">+����ת��ӯ��</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=close_pl%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">-���������ʽ�</td><td class="td_reportRdHead" align="right"><fmt:formatNumber value="<%=otherFrozen%>" pattern="#,##0.00"/>&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">+�����仯</td><td class="td_reportMdHead">&nbsp;<fmt:formatNumber value="<%=relOt_change%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">-����������</td><td class="td_reportRdHead"><fmt:formatNumber value="<%=tradefee%>" pattern="#,##0.00"/>&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">���տ����ʽ�</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=usefulFund%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">��Ѻ�ʽ�</td><td class="td_reportRdHead"><fmt:formatNumber value="<%=maxOverdraft %>" pattern="#,##0.00"/>&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">����ӯ��</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=PL%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">&nbsp;</td><td class="td_reportRdHead">&nbsp;</td></tr>
		<tr><td class="td_reportMdHead">��ǰȨ��</td><td class="td_reportMdHead" align="right">&nbsp;<fmt:formatNumber value="<%=relRight%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">&nbsp;</td><td class="td_reportRdHead">&nbsp;</td></tr>
	<%
		}
	%>
	</table>
	
	<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
						</div>
						</td>
						</tr>
	</table>
<%@ include file="/timebargain/common/messages.jsp" %>

</body>
</html>
