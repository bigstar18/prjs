<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>���۵������</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm"
			action="${basePath}/commodity/quotePoint/update.action" method="post"
			targetType="hidden">
			<div>
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��ϸ��Ϣ
							</div>
							<%@ include file="/commodity/quotePoint/quotePointInfoTable.jsp"%>
						</td>
						<td align="right">
						</td>
						<td>
						</td>
					</tr>

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
function quotePointAlgrChange(quotePointAlgr) {
	if (quotePointAlgr == '1') {
		document.getElementById("quotePointB_span").innerHTML = "%";
		//document.getElementById("quotePointS_span").innerHTML = "%";
	}
	if (quotePointAlgr == '2') {
		document.getElementById("quotePointB_span").innerHTML = "";
		//document.getElementById("quotePointS_span").innerHTML = "";
	}
}
function updateMargin() {
	if (frm.quotePointB.value == "") {
		alert("�򱨼۵���Ϊ�գ�");
		frm.quotePointB.focus();
		return false;
	}
	if (frm.quotePoint_S.value == "") {
		alert("�����۵���Ϊ�գ�");
		frm.quotePoint_S.focus();
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
<script type="text/javascript">
document.getElementById("delayFeeAlgr").value = '${obj.quotePointAlgr}';
quotePointAlgrChange('${obj.quotePointAlgr}');
</script>
<%@ include file="/public/footInc.jsp"%>