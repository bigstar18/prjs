<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>买方交收盈亏信息</title>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
		window.returnValue="-1";
		window.close();
	</script>
</c:if>
  </head>
  <body>
	<form name="frm" method="post">
		<input type="hidden" name="matchId" value="${settleMatch.matchId }">	
		<br>	
		<fieldset>
			<legend class="common"><b>买方交收盈亏信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">买方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">商品代码：</td>
					<td align="left" width="50%">${settleMatch.commodityId }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">交收数量：</td>
					<td align="left" width="50%">${settleMatch.weight }</td>
				</tr>
				<tr class="common">
					<td align="right">买方基准货款：</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">买方加升贴水货款：</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已收买方货款：</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">买方交收盈亏：</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="SettlePL_B1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
				  <td width="100%" colspan="2">
				    <font color="red">注：交收盈亏是针对订立价收取的。</font>
				  </td>
				</tr>
				
			</table>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function SettlePL_B1()
	{
		var SettlePL_B = frm.thisPayMent.value;
		if(SettlePL_B.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(SettlePL_B) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定买方交收盈亏为："+SettlePL_B+"？")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="SettlePL_B";
	     		submark = true;
	     	}
	     	if(submark){
				frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleSettlePL_B&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>