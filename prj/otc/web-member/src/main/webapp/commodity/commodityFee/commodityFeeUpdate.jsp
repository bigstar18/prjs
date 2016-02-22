<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>默认手续费</title>
		<%request.setAttribute("applyType","commodity_commodityFee"); %>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form name="myForm"
			action="${basePath}/commodity/commodityFee/update.action"
			method="post" targetType="hidden">
			<div class="div_scro">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;详细信息
							</div>
						<%@ include file="/commodity/commodityFee/commodityFeeInfoTable.jsp"%>
						</td>
						</tr>
						<tr>
						<td>
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
function feeAlgrChange() {
	var fee = myForm.feeAlgr.value;
	if (fee == '1') {
		document.getElementById("feeRate_span").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("feeRate_span").innerHTML = "";
	}
}

function updateMargin() {
	if (myForm.feeAlgr.value == "") {
		alert('即手续费算法不允许为空');
		myForm.tradeMargin.focus();
		return false;
	}
	if (myForm.feeMode.value == "") {
		alert('收费方式不允许为空');
		myForm.settleMargin.focus();
		return false;
	}
	var vaild = window.confirm("您确定要操作吗？");
	if (vaild == true) {
		myForm.submit();
	} else {
		return false;
	}
}
</script>
<script type="text/javascript">
document.getElementById("feeAlgr").value = '${obj.feeAlgr}';
feeAlgrChange('${obj.feeAlgr}');
</script>
<%@ include file="/public/footInc.jsp"%>