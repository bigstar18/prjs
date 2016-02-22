<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>强平设置</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/customerClosePosition/customerCloseUpdate/update.action"
			method="post">
			<div>
				<table border="0" width="50%" align="center">
					<tr>
						<td height="100">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;自动强平设置
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="300px"
								height="100" align="center" class="st_bor">
								<input type="hidden" name="obj.c_WarnTh" value="${obj.c_WarnTh}">
								<tr height="40">
									<td align="right" width="25%">
										自动强平开关:
									</td>
									<td width="30%">
										<select name="obj.oc_ForceClose" size="1" id="oc_ForceClose"
											style="width: 100">

											<c:if test="${obj.oc_ForceClose=='O'}">
												<option value="O" selected="selected">
													开
												</option>
												<option value="C">
													关
												</option>
											</c:if>
											<c:if test="${obj.oc_ForceClose=='C'}">
												<option value="C" selected="selected">
													关
												</option>
												<option value="O">
													开
												</option>
											</c:if>
											</option>
										</select>
									</td>
								</tr>
							</table>
							
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" align="center" >
						<span style="color: red" >
							关：暂停系统自动强平
							开：启用自动强平功能
						</span>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table cellspacing="0" cellpadding="0" border="0" width="80%"
					align="center">
					<tr>
						<td align="center" height="20">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateForceClose()" id="update">
								修改
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function updateForceClose() {
	if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
	var vaild = affirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>