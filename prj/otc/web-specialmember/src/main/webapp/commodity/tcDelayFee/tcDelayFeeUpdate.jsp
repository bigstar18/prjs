<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>默认延期费</title>
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
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;详细信息
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
								保存
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
								关闭
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
		alert("没有修改内容");
		return false;
	}
	var delayFee = document.getElementsByName("delayFee");
	for ( var i = 0; i < delayFee.length; i++) {
		if (delayFee[i].value == '') {
			var num = i + 1;
			alert('第' + num + '阶梯值不允许为空');
			delayFee[i].focus();
			return false;
		}
		if (isNaN(delayFee[i].value)) {
			var num = i + 1;
			alert('第' + num + '阶梯值应为数字');
			delayFee[i].focus();
			return false;
		}
	}
	var flag = myblur("all");
	if (flag) {
		var vaild = window.confirm("您确定要操作吗？");
		if (vaild == true) {
			frm.submit();
		} else {
			return false;
		}
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>