<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"
	defer="defer">
</script>
<head>
	<title>�����޸�</title>
</head>
<body>

	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/slbj/self/selfPasswordSave.action" targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;�����޸�
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									�û����� ��
								</td>
								<td align="left">
									<input name="obj.userId" type="text" class="input_text_pwd"
										value="${obj.userId }" readonly="readonly">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									�û����� ��
								</td>
								<td align="left">
									<input name="obj.name" type="text" class="input_text_pwd"
										value="${obj.name }" readonly="readonly">
								</td>
							</tr>
							 <tr height="35">
								<td align="right" class="td_size">
									ԭ���� ��
								</td>
								<td align="left">
									<input id="old" name="obj.old" type="password"
										class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr> 
							<tr height="35">
								<td align="right" class="td_size">
									������ ��
								</td>
								<td align="left">
									<input id="password" name="obj.password" type="password"
										class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									��ȷ������ ��
								</td>
								<td align="left">
									<input name="specialforAudit.password1" type="password"  id="password1" class="input_text_mid">
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
					<td align="center">
						<button class="btn_sec" onClick="frmChk()">
							����
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
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	var password1=document.getElementById("password").value;
	var password2=document.getElementById("password1").value;
	checkPW(password1,password2);
	}
//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>