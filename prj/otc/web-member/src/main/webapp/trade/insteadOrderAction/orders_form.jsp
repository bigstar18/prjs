<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<script language="JavaScript" src="../../public/global.js">
</script>
		<title></title>
		<style type="text/css">
<!--
.yin {
	visibility: hidden;
	position: absolute;
}

.xian {
	visibility: visible;
}
-->
</style>
		<script type="text/javascript">
//页面装载时调用 
function window_onload() {
	ordersForm.customerID.focus();
}

//下单提交
function order_onclick() {

		if (ordersForm.commodityID.value == "") {
			alert("商品代码不能为空！");
			ordersForm.commodityID.focus();
			return false;
		}
		if (ordersForm.buyOrSell.value == "") {
			alert("买卖标志不能为空！");
			ordersForm.buyOrSell.focus();
			return false;
		}
		if (ordersForm.orderType.value == "") {
			alert("类型不能为空！");
			ordersForm.orderType.focus();
			return false;
		}
		if (ordersForm.quantity.value == "") {
			alert("数量不能为空！");
			ordersForm.quantity.focus();
			return false;
		}
		ordersForm.submit();

}

function logoff_onclick()
{
	ordersForm.action = "${basePath}/tradeManage/insteadOrder/logoff.action";
	ordersForm.submit();
}

function price_onChange() {
	if(ordersForm.orderType.value == 1) {
		ordersForm.price.disabled = "disabled";
		ordersForm.price.value = "";
		document.getElementById('priceColor').style.color='grey';
	} else if (ordersForm.orderType.value == 2) {
		ordersForm.price.disabled = "";
		document.getElementById('priceColor').style.color='';
	}
}
</script>
	</head>
	<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
		<table border="0" height="400" width="700" align="center">
			<tr>
				<td>
					<form action="${basePath}/tradeManage/insteadOrder/edit.action" method="post" name="ordersForm">
						<fieldset>
							<legend class="common">
								<b>代为委托 </b>
							</legend>
							<table border="0" align="center" cellpadding="2" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
										<label>
											客户代码：
										</label>
									</td>
									<td>
										<input type="text" name="ordersForm.traderID" value="<%=request.getSession().getAttribute("traderID") %>" maxlength="16" class="text" style="width:105;" readonly="readonly" >
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<label>
											类型：
										</label>
									</td>
									<td>
										<select name="ordersForm.orderType" id="orderType" style="width:105" onchange="price_onChange()">
											<option value="">请选择</option>
											<option value="1">市价</option>
											<option value="2">指价</option>
										</select>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<label>
											商品代码：
										</label>
									</td>
									<td>
										<select name="ordersForm.commodityID" id="commodityID" style="width:105">
											<option value="">请选择</option>
											<c:forEach items="${commodityList }" var="commodity">
												<option value="${commodity.id }">${commodity.id }</option>
											</c:forEach>
										</select>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<label>
											买入/卖出：
										</label>
									</td>
									<td>
										<select name="ordersForm.buyOrSell" id="buyOrSell" style="width:105">
											<option value="">请选择</option>
											<option value="1">买入</option>
											<option value="2">卖出</option>
										</select>
										<span class="req">*</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										<label id="priceColor">
											价格：
										</label>
									</td>
									<td>
										<input type="text" name="ordersForm.price" id="price" maxlength="16" onkeypress="return numberPass()"
											style="width:105">
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<label>
											数量：
										</label>
									</td>
									<td>
										<input type="text" name="ordersForm.quantity" id="quantity" maxlength="16" onkeypress="return numberPass()"
											style="width:105">
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="10"></td>
								</tr>
								<tr>
									<td colspan="4" align="center">
										<input type="button"  class="btn_sec" onclick="order_onclick();" value="确认" />
										<input type="button"  class="btn_sec" onclick="logoff_onclick();" value="注销" />
									</td>
								</tr>
								<tr>
									<td colspan="2" height="10"></td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="type">
					</form>
				</td>
			</tr>
		</table>
		<%--<html:javascript formName="ordersForm" cdata="false" dynamicJavascript="true" staticJavascript="false" />--%>
	</body>
</html>
<%@ include file="/public/footInc.jsp"%>