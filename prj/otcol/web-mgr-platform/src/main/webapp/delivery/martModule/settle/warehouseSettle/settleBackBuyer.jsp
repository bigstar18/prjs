<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>退买方保证金</title>
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
			<legend class="common"><b>退买方保证金</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				<tr class="common">
					<td align="right" width="50%">配对编号：</td>
					<td align="left" width="50%">${settleMatch.matchId }</td>
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
					<td align="right" width="50%">买方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">已收买方保证金：</td>
					<td align="left" width="50%">${settleMatch.buyMargin }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">退买方保证金金额：</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
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
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function payToBuyer1()
	{
		var payToBuyer = frm.thisPayMent.value;
		if(payToBuyer.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(payToBuyer) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定退买方保证金金额为："+payToBuyer+"？")){
	     		submark = true;
	     		frm.opt.value="settleBackBuyer";
	     		frm.subbtn.disabled=true;
	     	}
	     	if(submark){
	     		frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=returnMoneyForBuy&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>