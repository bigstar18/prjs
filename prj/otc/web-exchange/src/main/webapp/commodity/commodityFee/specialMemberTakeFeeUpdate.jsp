<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员收取手续费修改</title>
	</head>
	<body class="st_body">
		<form name="frm"
			action="${basePath}/commodity/specialTakeFee/update.action"
			method="post" targetType="hidden">
			<div>
				<%@ include
					file="/commodity/commodityFee/specialMemberTakeFeeUpdateTable.jsp"%>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateTakeFeeStatus()"
								id="update">
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
function updateTakeFeeStatus() {
	if (parseFloat(frm.feeRate.value) < parseFloat(frm.mkt_FeeRate.value)) {
		alert("交易所收取手续费必须小于手续费系数");
		return false;
	}
	var vaild = window.confirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}

function feeAlgrChange() {
	var fee = frm.feeAlgr.value;
	if (fee == '1') {
		document.getElementById("fee1").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("fee1").innerHTML = "";
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>