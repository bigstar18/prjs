<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>Ĭ�����ڷ�</title>
		<%request.setAttribute("applyType","commodity_tcDelayFee"); %>
	</head>
	<body class="st_body" style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/commodity/tcDelayFee/update.action" method="post"
			targetType="hidden">
			<div class="div_scro1">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;��ϸ��Ϣ
						</div>
						<%@ include file="/commodity/tcDelayFee/tcDelayFeeTable.jsp"%>
					</td>
				</tr>
			</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="90%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" id="update" onclick="updateDelayFee()">
								����
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
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
function updateDelayFee() {
	if (!myblur("all")) {
		return false;
	}
	if (!isFormChanged(null, null)) {
		alert("û���޸�����");
		return false;
	}
	var delayFee = document.getElementsByName("delayFee");
	for ( var i = 0; i < delayFee.length; i++) {
		if (delayFee[i].value == '') {
			var num = i + 1;
			alert('��' + num + '����ֵ������Ϊ��');
			delayFee[i].focus();
			return false;
		}
		if (isNaN(delayFee[i].value)) {
			var num = i + 1;
			alert('��' + num + '����ֵӦΪ����');
			delayFee[i].focus();
			return false;
		}
	}
	var flag = myblur("all");
	if (flag) {
		var vaild = window.confirm("��ȷ��Ҫ������");
		if (vaild == true) {
			frm.submit();
		} else {
			return false;
		}
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>