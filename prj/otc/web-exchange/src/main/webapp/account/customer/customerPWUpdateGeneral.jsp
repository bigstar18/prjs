<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
	defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<head>
		<title>修改交易密码</title>
	</head>
<body>
	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/account/customerGeneral/updatePassword.action"
		targetType="hidden">
		<input type="hidden" name="obj.traderID" value="${obj.traderID}" />
		<div class="div_scromin">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
						<div class="st_title">
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="30"
								height="28" align="absmiddle" />
							&nbsp;&nbsp;修改交易密码
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									交易账号:
								</td>
								<td align="left">
									<input name="customerNo" type="text" class="input_text_pwd"
										value="${obj.traderID }" readonly="readonly">
								</td>
							</tr>
							<!--<tr height="35">
        	<td align="right" class="td_size" width="35%"> 客户名称:</td>
            <td align="left">
            	<input name="obj.name" type="text" class="input_text_pwd" value="${obj.name }" readonly="readonly">
            </td>
        </tr>
        
        <tr height="35">
            <td align="right"> 原密码 ：</td>
            <td align="left">
            	<input name="oldpass" type="password" class="text" style="width: 180px;">
            </td>
        </tr>
         -->
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									新密码:
								</td>
								<td align="left">
									<input id="password" name="obj.password" type="password"
										id="password" class="input_text_mid">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									新密码确认:
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
					<td align="center">
						<button class="btn_sec" onClick="frmChk()" id="update">
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
<SCRIPT LANGUAGE="JavaScript">
	function frmChk() {
		var password1 = document.getElementById("password").value;
		var password2 = document.getElementById("password1").value;
		checkPW(password1, password2);
	}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>




