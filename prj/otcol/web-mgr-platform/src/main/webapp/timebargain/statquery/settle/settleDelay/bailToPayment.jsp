<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>��֤��ת������Ϣ</title>
<%
	String matchID= (String)request.getParameter("matchID");
	if(matchID==null){
		matchID="ST";
	}	
	String optValue = (String)request.getParameter("opt");
	String bailToPayment = (String)request.getParameter("thisPayMent");
	if(bailToPayment== null){
		bailToPayment="0";
	}
	double bailToPaymentMoney = Double.parseDouble(bailToPayment);
	if(optValue!= null && "submit".equals(optValue))
	{
		int rep = SettleManageDelay.marginTurnGoodsPayment(matchID,bailToPaymentMoney);
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
			alert("�򷽱�֤����С��0��");
		</script>
	<%	
	}else if(rep==-5)
	{
	%>
		<script type="text/javascript">
			alert("���򷽻����������С�ڸ�����������");
		</script>
	<%
	}
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);
%>
</head>
<body>
	<form name="frm" method="post" onsubmit="return bailToPayment1();">
		<input type="hidden" name="matchID" value="<%=matchID %>">		
		<%
		if(matchMsg!=null)
		{
		%>		
		<br>
		<fieldset>
			<legend class="common"><b>��֤��ת������Ϣ</b></legend>
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
					<td align="right" width="50%">�򷽽��ձ�֤��</td><td align="left" width="50%"><%=matchMsg.get("BuyMargin") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%"><b>�х��򷽻��</b></td><td align="left" width="50%"><b><%=matchMsg.get("BuyPayout") %></b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����ˮ��ת���ٷֱȣ�</td>
					<td align="left" width="50%">
						<input type="text" name="percent" id="percent" style="width: 100px" onblur="checkMoneyForSettle('percent','ȷ���ύ�ı�֤��ת������')">
						<font color="red">%</font>
						<input type="hidden" name="totalMoney" value="<%=((BigDecimal)matchMsg.get("BuyPayout_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount")))%>">
					</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">���λ�ת��</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px" onblur="checkMoneyForSettle('thisPayMent')">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbutton" class="button" value="�ύ" onclick="bailToPayment1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
				<tr class="common">
					<td align="left" width="100%" colspan="2">
						&nbsp;&nbsp;&nbsp;&nbsp;
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
	function bailToPayment1()
	{
		var bailToPayment = frm.thisPayMent.value;
		if(frm.percent.value == "" && frm.thisPayMent.value == "" || frm.thisPayMent.value == ""){
            alert("�������0��һ����");
         }
		else if(bailToPayment.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(bailToPayment) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ���ύ�ı�֤��ת������Ϊ��"+bailToPayment+"��")){
	     		frm.subbutton.disabled = true;
	     		frm.opt.value="submit";
	     		submark = true;
	     	}
	     	if(submark){
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>