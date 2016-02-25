<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>���򷽻�����Ϣ</title>
<%
	//�ύ
	String money = (String)request.getParameter("thisPayMent");
	if(money == null){
		money = "0";
	}
	double thisMoney = Double.parseDouble(money);
	String matchID= (String)request.getParameter("matchID");
	String optvalue = (String)request.getParameter("opt");
	int rep = 0;
	if(optvalue != null && "submit".equals(optvalue.trim()))
	{
		rep = SettleManageDelay.inoutSettleMoney(matchID,thisMoney,true);
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
			alert("�򷽻���С�ڸ��������");
		</script>
	<%	
	}else if(rep==-5)
	{
	%>
		<script type="text/javascript">
			alert("�����������ʵ�ʿɸ��������");
		</script>
	<%
	}else if(rep==-6)
	{
	%>
		<script type="text/javascript">
			alert("�ջ�����������Ϊ����");
		</script>
	<%
	}
	}
	//�״η���
	if(matchID==null){
		matchID="ST";
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);	
%>
<title>�ջ���</title>
</head>
<body> 
	<form name="frm" method="post" onsubmit="return thisPay();">
		<input type="hidden" name="matchID" value="<%=matchID%>">
		<%
		if(matchMsg!=null)
		{
		%>
		<fieldset>
			<br>
			<legend class="common"><b>���򷽻�����Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%">
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
					<td align="right" width="50%">�򷽽��ռۣ�</td><td align="left" width="50%"><fmt:formatNumber value="<%=matchMsg.get("BuyPrice") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�򷽻�׼���</td><td align="left" width="50%"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout_Ref")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%"><b>�򷽼�����ˮ����:</b></td><td align="left" width="50%"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("BuyPayout_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount")))%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�х��򷽻��</td><td align="left" width="50%"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout") %>" pattern="#,##0.00"/></td>
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
					<td align="right" width="50%">�����ջ��</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px" onblur="checkMoneyForSettle('thisPayMent')">
						<font color="red">*</font>
					</td
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="thisPay();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
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
	function thisPay()
	{
		var thisPayment = frm.thisPayMent.value;
		if(frm.percent.value == "" && frm.thisPayMent.value == "" || frm.thisPayMent.value == ""){
            alert("�������0��һ����");
         }
	    else if(thisPayment.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(thisPayment) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("�����տ���Ϊ��"+thisPayment+"��")){
	     		frm.subbtn.disabled = true;
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