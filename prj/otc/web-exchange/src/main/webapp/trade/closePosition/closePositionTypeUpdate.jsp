<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>ǿƽ����</title>
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
								&nbsp;&nbsp;�Զ�ǿƽ����
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="300px"
								height="100" align="center" class="st_bor">
								<input type="hidden" name="obj.c_WarnTh" value="${obj.c_WarnTh}">
								<tr height="40">
									<td align="right" width="25%">
										�Զ�ǿƽ����:
									</td>
									<td width="30%">
										<select name="obj.oc_ForceClose" size="1" id="oc_ForceClose"
											style="width: 100">

											<c:if test="${obj.oc_ForceClose=='O'}">
												<option value="O" selected="selected">
													��
												</option>
												<option value="C">
													��
												</option>
											</c:if>
											<c:if test="${obj.oc_ForceClose=='C'}">
												<option value="C" selected="selected">
													��
												</option>
												<option value="O">
													��
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
							�أ���ͣϵͳ�Զ�ǿƽ
							���������Զ�ǿƽ����
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
								�޸�
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
			alert("û���޸�����");
			return false;}
	var vaild = affirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>