<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>默认保证金</title>
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
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;详细信息
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
								修改
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
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
function updateMargin() {
	if (frm.tradeMargin.value == "") {
		alert('即市占用不允许为空');
		frm.tradeMargin.focus();
		return false;
	} else if ((isNaN(frm.tradeMargin.value))) {
		alert("即市占用必须位数字");
		frm.tradeMargin.value = "";
		frm.tradeMargin.focus();
		return false;
	}
	if (frm.settleMargin.value == "") {
		alert('结算维持不允许为空');
		frm.settleMargin.focus();
		return false;
	} else if ((isNaN(frm.settleMargin.value))) {
		alert("结算维持必须位数字");
		frm.settleMargin.value = "";
		frm.settleMargin.focus();
		return false;
	}
	if (frm.holidayMargin.value == "") {
		alert('假日维持不允许为空');
		frm.holidayMargin.focus();
		return false;
	} else if ((isNaN(frm.holidayMargin.value))) {
		alert("假日维持必须位数字");
		frm.holidayMargin.value = "";
		frm.holidayMargin.focus();
		return false;
	}
	var vaild = window.confirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>