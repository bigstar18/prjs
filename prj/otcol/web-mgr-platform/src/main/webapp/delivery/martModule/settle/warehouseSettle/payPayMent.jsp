<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>付卖方货款信息</title>
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
			<br>
			<legend class="common"><b>付卖方货款信息</b></legend>
			<span>
			<table class="common" align="center" width="100%">
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
					<td align="right" width="50%">卖方交收价：</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.sellPrice }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">卖方基准货款：</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.sellIncome_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%"><b>卖方加升贴水货款:</b></td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.sellIncome_Ref+settleMatch.HL_Amount }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">已付卖方货款：</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.sellIncome }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">付升贴水后货款百分比：</td>
					<td align="left" width="50%">
						<input type="text" name="percent" id="percent" style="width: 100px" onblur="checkMoneyForSettle('percent','本次付款金额')">
						<font color="red">%</font>
						<input type="hidden" name="totalMoney" value="${settleMatch.sellIncome_Ref+settleMatch.HL_Amount }">
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">本次付货款：</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px" onblur="checkMoneyForSettle('thisPayMent')" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="提交" onclick="pay()">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
					</td>
				</tr>
			</table>
				<br>
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
     thisPay('确定本次收款金额为：','<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settlePayPayMent&dd=');
  }
}
</script>