<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
	var rightMap = ${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
	defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<head>
		<title>�޸ĵ绰����</title>
	</head>
<body>
	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/account/customer/updatePasswordPhone.action"
		targetType="hidden">
		<div class="div_scromin">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
						<input type="hidden" name="obj.memberNo" value="${obj.memberNo}" />
						<div class="st_title">
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="30"
								height="28" align="absmiddle" />
							&nbsp;&nbsp;�޸ĵ绰����
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									�����˺� :
								</td>
								<td align="left">
									<input name="obj.customerNo" type="text" class="input_text_pwd"
										value="${obj.customerNo }" readonly="readonly">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									�ͻ����� :
								</td>
								<td align="left">
									<input name="obj.name" type="text" class="input_text_pwd"
										value="${obj.name }" readonly="readonly">
								</td>
							</tr>
							<!-- 
        <tr height="35">
            <td align="right"> ԭ���� ��</td>
            <td align="left">
            	<input name="oldpass" type="password" class="text" style="width: 180px;">
            </td>
        </tr>
         -->
							<tr height="35">
								<td align="right" class="td_size">
									������:
								</td>
								<td align="left">
									<input id="password" name="obj.phonePWD" type="password"
										id="password" class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									��ȷ������:
								</td>
								<td align="left">
									<input name="specialforAudit.password1" type="password"
										id="password1" class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center">
				<tr height="35">
					<td>
						<div align="center">
							<button class="btn_sec" onClick="frmChk()" id="update">
								����
							</button>
					</td>
					<td>
						<div align="center">
							<button class="btn_sec" onClick="window.close()">
								�ر�
							</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	function frmChk() {
		var password1 = document.getElementById("password").value;
		var password2 = document.getElementById("password1").value;
		checkPW(password1, password2);
	}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>




