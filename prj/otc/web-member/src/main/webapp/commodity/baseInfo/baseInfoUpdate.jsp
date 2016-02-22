<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>商品信息</title>
		<%request.setAttribute("applyType","commodity_baseCommodity"); %>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form name="frm" action="${basePath}/commodity/baseInfo/update.action"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;详细信息
							</div>
							<%@ include file="/commodity/baseInfo/baseInfoTable.jsp"%>
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
							<button class="btn_sec" onClick="updateCommodity()" id="update">
								保存
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
function updateCommodity() {
	var vaild = window.confirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>