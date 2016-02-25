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
	String incomeFromSeller = (String)request.getParameter("incomeFromSeller");
	if(incomeFromSeller== null){
		incomeFromSeller="0";
	}
	double incomeFromSellerMoney = Double.parseDouble(incomeFromSeller);
	if(optValue!= null && "incomeFromSeller".equals(optValue))
	{
		int rep = SettleManageDelay.receiveOrPayPenalty(matchID,incomeFromSellerMoney,true,false);
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
			alert("收违约金总数不能为负！");
		</script>
	<%
	}
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);	
%>
</head>
<body>
	<form name="frm" method="post" onsubmit="return incomeFromSeller1();">
		<input type="hidden" name="matchID" value="<%=matchID %>">		
		<%
		if(matchMsg!=null)
		{
		%>	
		<br>	
		<fieldset>
			<legend class="common"><b>收卖方违约金信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr>
					<td align="right" width="50%">卖方交易商代码：</td><td align="left" width="50%"><%=matchMsg.get("FirmID_S") %></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">商品代码：</td><td align="left" width="50%"><%=matchMsg.get("CommodityID") %></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">交收数量：</td><td align="left" width="50%"><%=matchMsg.get("Quantity") %></td>
				</tr>
				
				<tr class="common">	
					<td align="right" width="50%">收卖方违约金：</td>
					<td align="left" width="50%">
						<input type="text" name="incomeFromSeller" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="incomeFromSeller1();">&nbsp;&nbsp;&nbsp;&nbsp;
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
	function incomeFromSeller1()
	{
		var incomeFromSeller = frm.incomeFromSeller.value;
		if(incomeFromSeller.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(incomeFromSeller) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.incomeFromSeller.value="";
	        frm.incomeFromSeller.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定收卖方违约金为："+incomeFromSeller+"？")){
	     		submark = true;
	     		frm.opt.value="incomeFromSeller";
	     		frm.subbtn.disabled = true;
	     	}
	     	if(submark){
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>