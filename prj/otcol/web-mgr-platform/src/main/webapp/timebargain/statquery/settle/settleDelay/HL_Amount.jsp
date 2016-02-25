<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>����ˮ��Ϣ</title>
<%
	String matchID= (String)request.getParameter("matchID");
	if(matchID==null){
		matchID="ST";
	}
		
	String optValue = (String)request.getParameter("opt");
	String HL_Amount = (String)request.getParameter("HL_Amount");
	if(HL_Amount== null){
		HL_Amount="0";
	}
	double hl_Amount = Double.parseDouble(HL_Amount);
	if(optValue!= null && "HL_Amount".equals(optValue))
	{
		int rep = SettleManageDelay.HLSettle(matchID,hl_Amount,true);
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
	}else if(rep==-3)
	{
	%>
		<script type="text/javascript">
			alert("���ռ�¼���Ͳ��Ϸ���");
		</script>
	<%	
	}else if(rep==-4)
	{
	%>
		<script type="text/javascript">
			alert("�����������Ѵ���ʵ��Ӧ�����������������");
		</script>
	<%
	}else if(rep==-2)
	{
	%>
		<script type="text/javascript">
			alert("�����쳣��");
		</script>
	<%
	}
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);
%>
</head>
<body>
	<form name="frm" method="post" onsubmit="return setHL_Amount();">
		<input type="hidden" name="matchID" value="<%=matchID %>">		
		<%
		if(matchMsg!=null)
		{
		%>
		<fieldset>
			<legend class="common">
				<b>����ˮ��Ϣ</b>
				</legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">�򷽽����̴��룺</td><td align="left" width="50%"><%=matchMsg.get("FirmID_B") %></td>
				</tr>
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
					<td align="right">�򷽻�׼���</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout_Ref")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�х��򷽻��</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">������׼���</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome_Ref") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�Ѹ��������</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>��ǰ����ˮ��</b></td><td align="left"><b><fmt:formatNumber value="<%=matchMsg.get("HL_Amount") %>" pattern="#,##0.00"/></b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">������������ˮ��</td>
					<td align="left" width="50%">
						<input type="text" name="HL_Amount" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="setHL_Amount();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>		
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
	function setHL_Amount()
	{
		var HL_Amount = frm.HL_Amount.value;
		if(HL_Amount.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(HL_Amount) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.HL_Amount.value="";
	        frm.HL_Amount.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ��������ˮ����Ϊ��"+HL_Amount+"��")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="HL_Amount";
	     		submark = true;
	     	}
	     	if(submark){	     		
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>