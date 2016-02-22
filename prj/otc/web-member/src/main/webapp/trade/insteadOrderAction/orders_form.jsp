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
//ҳ��װ��ʱ���� 
function window_onload() {
	ordersForm.customerID.focus();
}

//�µ��ύ
function order_onclick() {

		if (ordersForm.commodityID.value == "") {
			alert("��Ʒ���벻��Ϊ�գ�");
			ordersForm.commodityID.focus();
			return false;
		}
		if (ordersForm.buyOrSell.value == "") {
			alert("������־����Ϊ�գ�");
			ordersForm.buyOrSell.focus();
			return false;
		}
		if (ordersForm.orderType.value == "") {
			alert("���Ͳ���Ϊ�գ�");
			ordersForm.orderType.focus();
			return false;
		}
		if (ordersForm.quantity.value == "") {
			alert("��������Ϊ�գ�");
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
								<b>��Ϊί�� </b>
							</legend>
							<table border="0" align="center" cellpadding="2" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
										<label>
											�ͻ����룺
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
											���ͣ�
										</label>
									</td>
									<td>
										<select name="ordersForm.orderType" id="orderType" style="width:105" onchange="price_onChange()">
											<option value="">��ѡ��</option>
											<option value="1">�м�</option>
											<option value="2">ָ��</option>
										</select>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<label>
											��Ʒ���룺
										</label>
									</td>
									<td>
										<select name="ordersForm.commodityID" id="commodityID" style="width:105">
											<option value="">��ѡ��</option>
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
											����/������
										</label>
									</td>
									<td>
										<select name="ordersForm.buyOrSell" id="buyOrSell" style="width:105">
											<option value="">��ѡ��</option>
											<option value="1">����</option>
											<option value="2">����</option>
										</select>
										<span class="req">*</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										<label id="priceColor">
											�۸�
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
											������
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
										<input type="button"  class="btn_sec" onclick="order_onclick();" value="ȷ��" />
										<input type="button"  class="btn_sec" onclick="logoff_onclick();" value="ע��" />
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