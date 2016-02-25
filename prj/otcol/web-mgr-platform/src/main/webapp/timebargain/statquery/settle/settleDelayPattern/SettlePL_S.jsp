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
			<legend class="common"><b>卖方交收盈亏信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
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
					<td align="right">卖方基准货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome_Ref") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">卖方加升贴水货款:</td><td align="left"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("SellIncome_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount"))) %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已付卖方货款：</td><td align="left"><fmt:formatNumber value="<%=matchMsg.get("SellIncome") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">卖方交收盈亏：</td>
					<td align="left" width="50%">
						<input type="text" name="SettlePL_S" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
					
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="SettlePL_S1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
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
	        alert("请输入一个非0数字!");
	        frm.SettlePL_S.value="";
	        frm.SettlePL_S.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定收付卖方交收盈亏为："+SettlePL_S+"？")){
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