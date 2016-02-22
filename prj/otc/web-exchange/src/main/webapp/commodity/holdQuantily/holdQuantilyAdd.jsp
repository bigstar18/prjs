<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>会员持仓数量</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm"
			action="${basePath}/commodity/holdQuantily/update.action"
			method="post" targetType="hidden">
			<div>
				<%@include file="/commodity/holdQuantily/holdQuantilyAddTable.jsp"%>
			</div>
			<div class="tab_pad">

				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="addMargin()" id="update">
								添加
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
function pro() {
	var fee = frm.delayFeeAlgr.value;
	if (fee == '1') {
		document.getElementById("fee1").innerHTML = "%";
		document.getElementById("fee2").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("fee1").innerHTML = "";
		document.getElementById("fee2").innerHTML = "";
	}
}
function addMargin() {
	var memberNo = document.getElementById('memberNo').value;
	var commodityNo = document.getElementById('commodityId').value;
	if (memberNo == "") {
		alert('请选择会员名称!');
		return false;
	}
	if (commodityNo == "") {
		alert('请选择商品名称!');
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