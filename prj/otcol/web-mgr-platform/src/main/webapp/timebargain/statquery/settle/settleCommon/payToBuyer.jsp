<%@ include file="/timebargain/common/taglibs.jsp"%> 
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<base target="_self">
<html>
  <head>
    <title>付买方违约金信息</title>
<% 
	String matchID= (String)request.getParameter("matchID");
	if(matchID==null||matchID==""){
		matchID="ST";
	}	
	String optValue = (String)request.getParameter("opt");
	String payToBuyer = (String)request.getParameter("payToBuyer");
	if(payToBuyer== null){
		payToBuyer="0";
	}
	double payToBuyerMoney = Double.parseDouble(payToBuyer);
	if(optValue!= null && "payToBuyer".equals(optValue))
	{
		int rep = SettleManage.receiveOrPayPenalty(matchID,payToBuyerMoney,false,true);
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
			alert("付违约金总数不能为负！");
		</script>
	<%
	}
	}
	Map matchMsg = SettleManage.getSettle(matchID);
%>
</head>
<body>
	<form name="frm" method="post" onsubmit="return payToBuyer1();">
		<input type="hidden" name="matchID" value="<%=matchID %>">		
		<%
		if(matchMsg!=null)
		{
		%>		
		<br>
		<fieldset>
			<legend class="common"><b>付买方违约金信息</b></legend>
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
					<td align="right" width="50%">付买方违约金：</td>
					<td align="left" width="50%">
						<input type="text" name="payToBuyer" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="payToBuyer1();">&nbsp;&nbsp;&nbsp;&nbsp;
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
	function payToBuyer1()
	{
		var payToBuyer = frm.payToBuyer.value;
		if(payToBuyer.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(payToBuyer) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.payToBuyer.value="";
	        frm.payToBuyer.focus();
	        return false;
	     }else{
	     		var submark = false;
	     	if(confirm("确定付买方违约金为："+payToBuyer+"？")){
	     		frm.subbtn.disabled=true;
	     		frm.opt.value="payToBuyer";
	     		submark = true;
	     	}
	     	if(submark){
	     		frm.submit();	     		
	     		}
	     }
	}
</script>
</html>