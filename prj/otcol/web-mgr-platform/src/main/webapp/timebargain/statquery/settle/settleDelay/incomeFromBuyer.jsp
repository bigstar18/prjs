<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>����ΥԼ����Ϣ</title>
<%
	String matchID= (String)request.getParameter("matchID");
	if(matchID==null){
		matchID="ST";
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);	
	String optValue = (String)request.getParameter("opt");
	String incomeFromBuyer = (String)request.getParameter("incomeFromBuyer");
	if(incomeFromBuyer== null){
		incomeFromBuyer="0";
	}
	double incomeFromBuyerMoney = Double.parseDouble(incomeFromBuyer);
	if(optValue!= null && "incomeFromBuyer".equals(optValue))
	{
		int rep = SettleManageDelay.receiveOrPayPenalty(matchID,incomeFromBuyerMoney,true,true);
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
	}else if(rep==-4)
	{
	%>
		<script type="text/javascript">
			alert("��ΥԼ����������Ϊ����");
		</script>
	<%
	}
	}
%>
</head>
<body>
	<form name="frm" method="post" onsubmit="return incomeFromBuyer1();">
		<input type="hidden" name="matchID" value="<%=matchID %>">		
		<%
		if(matchMsg!=null)
		{
		%>
		<br>	
		<fieldset>
			<legend class="common"><b>����ΥԼ����Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="right" width="50%">�򷽽����̴��룺</td><td align="left" width="50%"><%=matchMsg.get("FirmID_B") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">��Ʒ���룺</td><td align="left" width="50%"><%=matchMsg.get("CommodityID") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����������</td><td align="left" width="50%"><%=matchMsg.get("Quantity") %></td>
				</tr>
				
				<tr class="common">	
					<td align="right" width="50%">����ΥԼ��</td>
					<td align="left" width="50%">
						<input type="text" name="incomeFromBuyer" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="incomeFromBuyer1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
			</table>
			<br>
			</span>
		</fieldset>
		<%
		}
		%>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function incomeFromBuyer1()
	{
		var incomeFromBuyer = frm.incomeFromBuyer.value;
		if(incomeFromBuyer.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(incomeFromBuyer) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.incomeFromBuyer.value="";
	        frm.incomeFromBuyer.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ������ΥԼ��Ϊ��"+incomeFromBuyer+"��")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="incomeFromBuyer";
	     		submark = true;
	     	}
	     	if(submark){
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>