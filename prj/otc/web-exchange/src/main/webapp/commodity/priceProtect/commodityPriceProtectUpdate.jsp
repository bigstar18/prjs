<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>��Ʒ���鱣������</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm"
			action="${basePath}/commodity/commodityPriceProtect/update.action"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;��ϸ��Ϣ
							</div>
							<%@ include
								file="/commodity/priceProtect/commodityPriceProtectTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">

					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateCommodityPriceProtect()"
								id="update">
								�޸�
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
								�ر�
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function updateCommodityPriceProtect() {
	if (document.getElementById("timeoutInterval").value == '') {
		alert('�����볬ʱ���');
		document.getElementById("timeoutInterval").value = '';
		document.getElementById("timeoutInterval").focus();
		return false;
	} else if (isNaN(document.getElementById("timeoutInterval").value)) {
		alert('��ʱ�������Ϊ����');
		document.getElementById("timeoutInterval").value = '';
		document.getElementById("timeoutInterval").focus();
		return false;
	} else if (document.getElementById("timeoutInterval").value > 1000) {
		alert('��ʱ�������С��1000');
		document.getElementById("timeoutInterval").value = '';
		document.getElementById("timeoutInterval").focus();
		return false;
	}
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>