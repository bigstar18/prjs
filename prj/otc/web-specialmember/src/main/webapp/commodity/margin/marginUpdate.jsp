<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>Ĭ�ϱ�֤��</title>
		<%request.setAttribute("applyType","commodity_margin"); %>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form name="frm" action="${basePath}/commodity/margin/update.action"
			method="post" targetType="hidden">
			<div class="div_scro1">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;��ϸ��Ϣ
						</div>
						<%@ include file="/commodity/margin/marginInfoTable.jsp"%>
						<%@ include file="/applyAndAudit/public/applyedNote.jsp"%>
					</td>
				</tr>
			</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateMargin()" id="update">
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
function updateMargin() {
	if (frm.tradeMargin.value == "") {
		alert('����ռ�ò�����Ϊ��');
		frm.tradeMargin.focus();
		return false;
	} else if ((isNaN(frm.tradeMargin.value))) {
		alert("����ռ�ñ���λ����");
		frm.tradeMargin.value = "";
		frm.tradeMargin.focus();
		return false;
	}
	if (frm.settleMargin.value == "") {
		alert('����ά�ֲ�����Ϊ��');
		frm.settleMargin.focus();
		return false;
	} else if ((isNaN(frm.settleMargin.value))) {
		alert("����ά�ֱ���λ����");
		frm.settleMargin.value = "";
		frm.settleMargin.focus();
		return false;
	}
	if (frm.holidayMargin.value == "") {
		alert('����ά�ֲ�����Ϊ��');
		frm.holidayMargin.focus();
		return false;
	} else if ((isNaN(frm.holidayMargin.value))) {
		alert("����ά�ֱ���λ����");
		frm.holidayMargin.value = "";
		frm.holidayMargin.focus();
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