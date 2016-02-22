<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户激活</title>
	</head>
	<body>
		<br />
		<form name="frm"
			action="${basePath}/account/customerActiveStatus/update.action"
			method="post" targetType="hidden">
		<div class="div_scromin">
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;客户激活</div>
			<table border="0" width="90%" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right">
							交易账号:
						</td>
						<td>
							<input type="text" id="id" name="obj.id" value="${obj.id}"
								style="background-color: #bebebe" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户名称:
						</td>
						<td>
							<input type="text" id="name" name="obj.name" value="${obj.name}"
								style="background-color: #bebebe" readonly="readonly"">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户状态:
						</td>
						<td>
							<input type="hidden" id="status" name="obj.customerStatus.status" value="N">
							<input type="text" id="customerStatus" name="customerStatus" value="${firmStatusMap[obj.customerStatus.status]}" style="background-color: #bebebe" readonly="readonly">
						</td>
					</tr>
				</table>
			</td>
			</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="90%"
	align="center">
	<tr>
		<td align="center">
			<button onclick="updateCustomerStatus()">
				激活
			</button>
		</td>
		<td align="center">
			<button onclick="window.close()">
				关闭
			</button>
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
function updateCustomerStatus() {
	frm.submit();
}
</script>
		</form>

	</body>
</html>

<%@ include file="/public/footInc.jsp"%>