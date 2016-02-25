<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>升贴水信息</title>
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
			alert("付卖方货款已大于实际应付，需先退卖方货款！");
		</script>
	<%
	}else if(rep==-2)
	{
	%>
		<script type="text/javascript">
			alert("操作异常！");
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
				<b>升贴水信息</b>
				</legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">买方交易商代码：</td><td align="left" width="50%"><%=matchMsg.get("FirmID_B") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">卖方交易商代码：</td><td align="left" width="50%"><%=matchMsg.get("FirmID_S") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">商品代码：</td><td align="left" width="50%"><%=matchMsg.get("CommodityID") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">交收数量：</td><td align="left" width="50%"><%=matchMsg.get("Quantity") %></td>
				</tr>
				<tr class="common">
					<td align="right">买方基准货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout_Ref")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已収买方货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">卖方基准货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome_Ref") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已付卖方货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>当前升贴水：</b></td><td align="left"><b><fmt:formatNumber value="<%=matchMsg.get("HL_Amount") %>" pattern="#,##0.00"/></b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">本次增减升贴水：</td>
					<td align="left" width="50%">
						<input type="text" name="HL_Amount" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="setHL_Amount();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
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
	        alert("请输入一个非0数字!");
	        frm.HL_Amount.value="";
	        frm.HL_Amount.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定将升贴水设置为："+HL_Amount+"？")){
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