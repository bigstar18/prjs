<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>ϵͳ�û����</title>
		<script language="JavaScript" src="../../public/global.js">
</script>
	</head>
	<body onload="window_onload()">
		<table width="50%" border="0" cellspacing="0"
				cellpadding="0" align="center">
				<tr>
					<td>
            <div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				�����µ�
			</div>
			<div class="div_tj">
			<form name="ordersForm" action="${basePath}/tradeManage/insteadOrder/login.action"
						method="post" onLoad="return window_onload()">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					align="center" class="table2_style">
					           <tr><td height="5">&nbsp;</td></tr>
								<tr>
									<td align="center">�ͻ����룺&nbsp;
										<input type="text" name="traderID" maxlength="15" class="text" />
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td align="center">�绰���&nbsp;
										<input type="password" name="password" maxlength="64"
											style="width: 153; height: 20" class="text" />
										<font color="red">*</font>
									</td>
								</tr>
								<tr><td height="5">&nbsp;</td></tr>
								<tr>
									<td align="center">
										<button class="btn_sec" onclick="ok_onclick();" id="ok" name="ok">
											��¼
										</button>
									</td>
								</tr>
							</table>
					</form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
<script type="text/javascript">
function ok_onclick() {
	if (trim(ordersForm.traderID.value) == "") {
		alert("�ͻ����벻��Ϊ�գ�");
		ordersForm.traderID.focus();
		return false;
	}
	if (trim(ordersForm.password.value) == "") {
		alert("�绰�����Ϊ�գ�");
		ordersForm.password.focus();
		return false;
	}
	ordersForm.submit();
	ordersForm.ok.disabled = true;
}
function window_onload() {
	ordersForm.traderID.focus();
}
</script>
