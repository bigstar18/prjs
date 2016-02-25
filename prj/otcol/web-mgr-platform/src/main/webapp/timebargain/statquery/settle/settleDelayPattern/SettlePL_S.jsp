<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
<head>
<%
	String matchID= (String)request.getParameter("matchID");
	if(matchID==null){
		matchID="ST";
	}
	String optValue = (String)request.getParameter("opt");
	String SettlePL_S = (String)request.getParameter("SettlePL_S");
	if(SettlePL_S== null){
		SettlePL_S="0";
	}
	double SettlePL_SMoney = Double.parseDouble(SettlePL_S);
	if(optValue!= null && "SettlePL_S".equals(optValue))
	{
		int rep = SettleManageDelay.addSettlePL(matchID,SettlePL_SMoney,false);
		if(rep==1)
	{
	%>
		<script type="text/javascript">
			alert("�����ɹ���");
			window.returnValue="-1";
			window.close();
		</script>
	<%	
	}else if(rep==-1)
	{
	%>
		<script type="text/javascript">
			alert("���ռ�¼״̬���Ϸ���");
		</script>
	<%	
	}else if(rep==-2)
	{
	%>
		<script type="text/javascript">
			alert("�����쳣��");
		</script>
	<%
	}else if(rep==-3)
	{
	%>
		<script type="text/javascript">
			alert("���ռ�¼���Ͳ��Ϸ���");
		</script>
	<%
	}
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);	
%>
</head>
<body>
	<form name="frm" method="post" onsubmit="return SettlePL_S1();">
		<input type="hidden" name="matchID" value="<%=matchID %>">		
		<%
		if(matchMsg!=null)
		{
		%>	
		<br>
		<fieldset>
			<legend class="common"><b>��������ӯ����Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">���������̴��룺</td><td align="left" width="50%"><%=matchMsg.get("FirmID_S") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">��Ʒ���룺</td><td align="left" width="50%"><%=matchMsg.get("CommodityID") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����������</td><td align="left" width="50%"><%=matchMsg.get("Quantity") %></td>
				</tr>
				<tr class="common">
					<td align="right">������׼���</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome_Ref") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">����������ˮ����:</td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("SellIncome_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount"))) %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�Ѹ��������</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">��������ӯ����</td>
					<td align="left" width="50%">
						<input type="text" name="SettlePL_S" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
					
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="SettlePL_S1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
			</table>
			</span>
		</fieldset>
		<%
		}
		%>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function SettlePL_S1()
	{
		var SettlePL_S = frm.SettlePL_S.value;
		if(SettlePL_S.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(SettlePL_S) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.SettlePL_S.value="";
	        frm.SettlePL_S.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ���ո���������ӯ��Ϊ��"+SettlePL_S+"��")){
	     		frm.subbtn.disabled=true;
	     		frm.opt.value="SettlePL_S";
	     		submark = true;
	     	}
	     	if(submark){	     		
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>