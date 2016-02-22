<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>商品行情保护设置</title>
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
								&nbsp;&nbsp;详细信息
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
function updateCommodityPriceProtect() {
	if (document.getElementById("timeoutInterval").value == '') {
		alert('请输入超时间隔');
		document.getElementById("timeoutInterval").value = '';
		document.getElementById("timeoutInterval").focus();
		return false;
	} else if (isNaN(document.getElementById("timeoutInterval").value)) {
		alert('超时间隔必须为数字');
		document.getElementById("timeoutInterval").value = '';
		document.getElementById("timeoutInterval").focus();
		return false;
	} else if (document.getElementById("timeoutInterval").value > 1000) {
		alert('超时间隔必须小于1000');
		document.getElementById("timeoutInterval").value = '';
		document.getElementById("timeoutInterval").focus();
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