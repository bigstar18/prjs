<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>保证金转货款信息</title>
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
			alert("操作成功！");
			window.returnValue="-1";
			window.close();
		</script>
	<%	
	}else if(rep==-1)
	{
	%>
		<script type="text/javascript">
			alert("交收记录状态不合法！");
		</script>
	<%	
	}else if(rep==-2)
	{
	%>
		<script type="text/javascript">
			alert("操作异常！");
		</script>
	<%
	}else if(rep==-3)
	{
	%>
		<script type="text/javascript">
			alert("交收记录类型不合法！");
		</script>
	<%	
	}else if(rep==-4)
	{
	%>
		<script type="text/javascript">
			alert("买方保证金不能小于0！");
		</script>
	<%	
	}else if(rep==-5)
	{
	%>
		<script type="text/javascript">
			alert("收买方货款比例不能小于付卖方比例！");
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
			<legend class="common"><b>保证金转货款信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="right" width="50%">买方交易商代码：</td><td align="left" width="50%"><%=matchMsg.get("FirmID_B") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">商品代码：</td><td align="left" width="50%"><%=matchMsg.get("CommodityID") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">交收数量：</td><td align="left" width="50%"><%=matchMsg.get("Quantity") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">买方交收保证金：</td><td align="left" width="50%"><%=matchMsg.get("BuyMargin") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%"><b>已収买方货款：</b></td><td align="left" width="50%"><b><%=matchMsg.get("BuyPayout") %></b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">升贴水后划转金额百分比：</td>
					<td align="left" width="50%">
						<input type="text" name="percent" id="percent" style="width: 100px" onblur="checkMoneyForSettle('percent','确认提交的保证金转货款金额')">
						<font color="red">%</font>
						<input type="hidden" name="totalMoney" value="<%=((BigDecimal)matchMsg.get("BuyPayout_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount")))%>">
					</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">本次划转金额：</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px" onblur="checkMoneyForSettle('thisPayMent')">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbutton" class="button" value="提交" onclick="bailToPayment1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
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
            alert("请输入非0的一个数");
         }
		else if(bailToPayment.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(bailToPayment) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确认提交的保证金转货款金额为："+bailToPayment+"？")){
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