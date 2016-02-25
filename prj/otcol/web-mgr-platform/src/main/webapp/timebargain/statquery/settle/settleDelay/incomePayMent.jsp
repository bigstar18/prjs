<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelay" %>
<base target="_self">
<html>
  <head>
    <title>收买方货款信息</title>
<%
	//提交
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
			alert("买方货款小于付卖方货款！");
		</script>
	<%	
	}else if(rep==-5)
	{
	%>
		<script type="text/javascript">
			alert("卖方货款大于实际可付卖方货款！");
		</script>
	<%
	}else if(rep==-6)
	{
	%>
		<script type="text/javascript">
			alert("收货款总数不能为负！");
		</script>
	<%
	}
	}
	//首次访问
	if(matchID==null){
		matchID="ST";
	}
	Map matchMsg = SettleManageDelay.getSettle(matchID);	
%>
<title>收货款</title>
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
			<legend class="common"><b>收买方货款信息</b></legend>
			<span>
			<table class="common" align="center" width="100%">
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
					<td align="right" width="50%">买方交收价：</td><td align="left" width="50%"><fmt:formatNumber value="<%=matchMsg.get("BuyPrice") %>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">买方基准货款：</td><td align="left" width="50%"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout_Ref")%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%"><b>买方加升贴水货款:</b></td><td align="left" width="50%"><fmt:formatNumber value="<%=((BigDecimal)matchMsg.get("BuyPayout_Ref")).add(((BigDecimal)matchMsg.get("HL_Amount")))%>" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">已収买方货款：</td><td align="left" width="50%"><fmt:formatNumber value="<%=matchMsg.get("BuyPayout") %>" pattern="#,##0.00"/></td>
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
					<td align="right" width="50%">本次收货款：</td>
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
						<input type="button" name="subbtn" class="button" value="提交" onclick="thisPay();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
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
            alert("请输入非0的一个数");
         }
	    else if(thisPayment.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(thisPayment) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("本次收款金额为："+thisPayment+"？")){
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