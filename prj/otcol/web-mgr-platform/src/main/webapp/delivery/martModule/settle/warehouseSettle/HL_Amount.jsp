<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../../../public/session.jsp"%>
<base target="_self">
<html>
  <head>
    <title>升贴水信息</title>
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
		<fieldset>
			<legend class="common">
				<b>升贴水信息</b>
				</legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">买方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">卖方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">商品品种代码：</td>
					<td align="left" width="50%">${settleMatch.breedId }</td>
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
					<td align="right">已収买方货款：</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">卖方基准货款：</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">已付卖方货款：</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.sellIncome }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>当前升贴水：</b></td>
					<td align="left"><b><fmt:formatNumber value="${settleMatch.HL_Amount }" pattern="#,##0.00"/></b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">本次增减升贴水：</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="setHL_Amount();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="关闭" onclick="window.close();">
					</td>
				</tr>		
			</table>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function setHL_Amount()
	{
		var HL_Amount = frm.thisPayMent.value;
		if(HL_Amount.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(HL_Amount) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定将升贴水设置为："+HL_Amount+"？")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="HL_Amount";
	     		submark = true;
	     	}
	     	if(submark){
				frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleHL_Amount&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>