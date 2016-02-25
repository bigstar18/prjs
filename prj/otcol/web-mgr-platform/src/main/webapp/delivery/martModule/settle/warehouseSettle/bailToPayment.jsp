<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>保证金转货款信息</title>
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
			<legend class="common"><b>保证金转货款信息</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="right" width="50%">买方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
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
					<td align="right" width="50%">买方交收保证金：</td>
					<td align="left" width="50%">${settleMatch.buyMargin }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%"><b>已收买方货款：</b></td>
					<td align="left" width="50%"><b>${settleMatch.buyPayout }</b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">升贴水后划转金额百分比：</td>
					<td align="left" width="50%">
						<input type="text" name="percent" id="percent" style="width: 100px" onblur="checkMoneyForSettle('percent','确认提交的保证金转货款金额')">
						<font color="red">%</font>
						<input type="hidden" name="totalMoney" value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount }">
					</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">本次划转金额：</td>
					<td align="left" width="50%">
						<input type="text" id="thisPayMent" name="thisPayMent" style="width: 100px" onblur="checkMoneyForSettle('thisPayMent')">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="pay()">&nbsp;&nbsp;&nbsp;&nbsp;
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
		<input type="hidden" name="opt">
	</form>
  </body>
</html>

<script type="text/javascript">
function pay()
{ 
  if(frm.percent.value=="" && frm.thisPayMent.value=="" || frm.thisPayMent.value ==""){
       alert("请输入非零的一个数");
  }
  else { 
     thisPay('确认提交的保证金转货款金额为：','<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleBailToPayment&dd=');
  }
}
</script>