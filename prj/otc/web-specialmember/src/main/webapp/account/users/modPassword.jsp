<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
var rightMap = ${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
	defer="defer">
</script>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js">
</script>
<head>
	<title>密码修改</title>
</head>
<body>
	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/user/updatePassword.action" targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<div class="st_title">
					<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
					&nbsp;&nbsp;密码修改
				</div>
				<tr>
					<td>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="50%">
									用户代码 ：
								</td>
								<td align="left">
									<input name="obj.userId" type="text" class="input_text_pwd"
										value="${obj.userId }" readonly="readonly">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="50%">
									用户名称 ：
								</td>
								<td align="left">
									<input name="obj.name" type="text" class="input_text_pwd"
										value="${obj.name }" readonly="readonly">
								</td>
							</tr>
							<!-- <tr height="35">
								<td align="right" class="td_size">
									原密码 ：
								</td>
								<td align="left">
								 -->
									<input id="old" name="obj.old" type="hidden"
										class="input_text_mid">
								<!--</td>
										<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							 -->
							<tr height="35">
								<td align="right" class="td_size" width="50%">
									新密码 ：
								</td>
								<td align="left">
									<input id="password" name="obj.password" type="password"
										class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="50%">
									新确认密码 ：
								</td>
								<td align="left">
									<input name="specialforAudit.password1" id="password1" type="password"
										class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="90%"
				align="center">
				<tr height="35">
					<td align="center" id="tdId">
						<button class="btn_sec" onClick="frmChk()" id="update">
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
<SCRIPT LANGUAGE="JavaScript">
var userId='${obj.userId}';
if(userId==currenUserId){
	var tdId=document.getElementById("tdId");
	var regexTests = document.getElementsByTagName("input");
	var len = regexTests.length;
	for (i = 0; i < len; i++) {
		regexTests[i].readOnly = 'readOnly';
	}
	tdId.style.display='none';
}
	function frmChk(){ 
	var password1=frm.password.value;
	var password2=frm.password1.value;
	checkPW(password1,password2);
}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>