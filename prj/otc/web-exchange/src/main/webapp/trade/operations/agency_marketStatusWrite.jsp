<%@ page pageEncoding="GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>���ûָ�����ʱ��</title>
		<script type="text/javascript">
function save_onclick() {
	var str = document.getElementById("tradeManageVo.recoverTime").value;
	var elem = document.getElementById("tradeManageVo.recoverTime");
	if (str == "") {
		alert("�ָ�ʱ�䲻��Ϊ�գ�");
		elem.focus();
		return false;
	}

	var a = str.match(/^(\d{1,2})(:)(\d{1,2})(:)(\d{1,2})$/);

	if (a == null) {
		alert('����Ĳ�������ʱ���ʽ');
		elem.focus();
		return false;
	}
	if (a[1] > 24 || a[3] > 60 || a[5] > 60) {

		alert("����Ĳ�������ʱ���ʽ");
		elem.focus();
		return false
	}
	frm.submit();
}
//ʱ���ж�
function isTime() {
	var str = document.getElementById("tradeManageVo.recoverTime").value;
	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
	if (a == null) {
		alert('����Ĳ�������ʱ���ʽ');
		return false;
	}
	if (a[1] > 24 || a[3] > 60 || a[4] > 60) {
		alert("ʱ���ʽ����");
		return false
	}
	return true;
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<div style="overflow: auto; height: 300px;">
			<form
				action="${basePath}/tradeManage/tradeStatusManage/updateRecoverTime.action"
				method="POST" name="frm" targetType="hidden">
				<table border="0" height="300" width="90%" align="center"
					valign="top">
					<tr>
						<td>
							<div class="st_title">
								<br>
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="30" align="absmiddle" />
								&nbsp;&nbsp;�趨�ָ�ʱ��
							</div>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								width="100%" class="st_bor" valign="top">
								<input type="hidden" name="tradeManageVo.status" value="99">
								<tr height="35">
									<td align="center">
										&nbsp;�ָ�ʱ�䣺
										<input type="input_text" name="tradeManageVo.recoverTime"
											id="tradeManageVo.recoverTime">
										<span class="req"><font color="red">*</font></span>
									</td>
								</tr>
								<tr height="35">
									<td align="center">
										<span class="req">&nbsp;��ʱ����д��ʽΪ��24hours HH:MM:SS��</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="90%"
				align="center">
				<tr height="35">
					<td align="center">
						<button class="btn_sec" id="update"
							onclick="javascript:return save_onclick();">
							�ύ
						</button>&nbsp;&nbsp;
						<button class="btn_sec" id="update"
							onclick="window.close();">
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</div>


	</body>
</html>
<%@ include file="/public/footInc.jsp"%>