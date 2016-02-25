<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>收买方违约金信息</title>
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
			<legend class="common"><b>收买方违约金信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
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
					<td align="right" width="50%">收买方违约金：</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="incomeFromBuyer1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
			</table>
			<br>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function incomeFromBuyer1()
	{
		var incomeFromBuyer = frm.thisPayMent.value;
		if(incomeFromBuyer.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(incomeFromBuyer) == parseFloat(0)){
	        alert("请输入一个非0数字!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("确定收买方违约金为："+incomeFromBuyer+"？")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="incomeFromBuyer";
	     		submark = true;
	     	}
	     	if(submark){
	     		frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleIncomeFromBuyer&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>