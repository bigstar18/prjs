<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>��Ʒ����</title>
		<%request.setAttribute("applyType","commodity_articleParitiesAgio"); %>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form name="frm"
			action="${basePath}/commodity/articleParitiesAgio/update.action"
			method="post" targetType="hidden">
			<div class="div_scromid1">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;��ϸ��Ϣ
							</div>
							<%@ include
								file="/commodity/articleParitiesAgio/articleParitiesAgioTable.jsp"%>
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
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>