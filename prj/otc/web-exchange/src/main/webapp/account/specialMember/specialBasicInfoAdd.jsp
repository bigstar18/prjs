<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<html>
	<style>
<!--
.signNoHidden {
	display: none;
}
-->
</style>
	<head>
		<title>特别会员信息添加</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form action="${basePath}/account/specialMemberInfo/add.action"
			name="frm" method="post" targetType="hidden">
			<div style="overflow: auto; height: 540px;">
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;特别会员信息添加
							</div>
							<%@include file="commonSpecialMemberTable.jsp"%>
							<!-- 另一个开始-->
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="2">
								<tr>
									<td colspan="4">
										<div class="div_cxtj">
											<img src="<%=skinPath%>/cssimg/13.gif" />
											&nbsp;管理员信息
										</div>
										<div class="div_tj">
											<table width="90%" border="0" class="table2_style">
												<tr height="30">
													<td align="right" width="20%">
														管理员账号:
													</td>
													<td width="45%">
														<label>
															<span id="userID"></span>
														</label>
													</td>
													<td style="padding-right: 10px;">
													</td>
												</tr>
												<tr height="30">
													<td align="right">
														管理员密码:
													</td>
													<td>
														<input id="password" type="password"
															class="input_text_mid" name="obj.password"
															onblur="myblur('password')">
														<strong class="check_input">&nbsp;*</strong>
													</td>
													<td style="padding-right: 10px;" width="40%">
														<div id="password_vTip">
															&nbsp;
														</div>
													</td>
												</tr>
												<tr height="30">
													<td align="right">
														管理员密码确认:
													</td>
													<td>
														<input id="password1" type="password"
															class="input_text_mid" name="password1"
															onblur="myblur('password1')">
														<strong class="check_input">&nbsp;*</strong>
													</td>
													<td style="padding-right: 10px;" width="40%">
														<div id="password1_vTip">
															&nbsp;
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<!-- 另一个结束-->
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" align="center" cellpadding="0" border="0"
					width="100%">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="addSpecialMemberInfo()" id="add">
								添加
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
	function addSpecialMemberInfo() {
		if (!myblur("all")) {
			return false;
		}
		var vaild = affirm("您确定要操作吗？");
		if (vaild == true) {
			frm.submit();
			//return true;
		} else {
			return false;
		}
	}

	function checkSpecialMemberId(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		} else if (isNaN(user.value)) {
			innerHTML = "不合法的数字";
		} else {
			if (trim1(user.value).length != 3||trim1(user.value).length!=user.value.length) {
				innerHTML = "应为3位,且不含空格";
			} else {
				flag = true;
			}
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}

		var id = document.getElementById("id").value;
		checkAction.existId(id, function(isExist) {
			if (isExist) {
				alert('会员编号已存在，请重新添加');
				document.getElementById("id").value = "";
				document.getElementById("id").focus();
			}
		});
		return flag;
	}

	function changeUserId(userID) {
		var userId = document.getElementById('userID');
		if (checkSpecialMemberId(userID)) {
			var user = document.getElementById(userID);
			userId.innerHTML = user.value + "_admin";
		} else {
			userId.innerHTML = "";
		}
	}
	function password(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (user != null) {
			var vTip = document.getElementById(userID + "_vTip");
			var flag = false;
			if (checkPassword(user.value) != "") {
				innerHTML = checkPassword(user.value);
			} else {
				flag = true;
			}
			vTip.innerHTML = innerHTML;
			if (flag) {
				vTip.className = "";
			} else {
				vTip.className = "onError";
			}
			return flag;
		} else {
			return true;
		}
	}
	
	function passwordcompare(userID, compareuserID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (user != null) {
			var comparevalue = document.getElementById(compareuserID).value;
			var vTip = document.getElementById(userID + "_vTip");
			var flag = false;
			if (checkPassword(user.value) != "") {
				innerHTML = checkPassword(user.value);
			} else {
				if (user.value != comparevalue) {
					innerHTML = "密码与确认密码不一致！";
				} else {
					flag = true;
				}
			}
			vTip.innerHTML = innerHTML;
			if (flag) {
				vTip.className = "";
			} else {
				vTip.className = "onError";
			}
			return flag;
		} else {
			return true;
		}
	}
</script>
<%@ include file="/public/footInc.jsp"%>