<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript">
	
</script>
<script
	src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="GBK">
	
</script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" charset="GBK">
	
</script>
<script>
	jQuery(document).ready(function() {
		//mgrIsNeedKey：Y表示后台管理启用key 
		<%if(Global.getMarketInfoMap().get("mgrIsNeedKey").equals("Y") 
				&& Global.getMarketInfoMap().get("marketNO") != null 
				&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){%>
			$("#mgrIsNeedKeyCode").css("display","block");
		<%}%>
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		jQuery("#frm").validationEngine( {
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../ajaxcheck/checkUserForm.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		function beforeCall(form, options) {
			return true;
		}
		// Called once the server replies to the ajax form validation request
			function ajaxValidationCallback(status, form, json, options) {
				if (status === true) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						form.validationEngine('detach');
						//$('#frm').attr('action', 'action');
						$("#name").val($("#name").val().trim());
						$("#userId").val($("#userId").val().trim());
						$('#frm').submit();
					}
				}
			}

			$("#add").click(function(check) {
				if ($("#frm").validationEngine('validateform')) {

				}
			});
		});
	function checkKey(inp){
		if(inp.checked){
			document.getElementById("showkey").style.display="";
			var m = initKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("userId").value,frm);
			if(!m.passed){
				alert(m.msg);
				document.getElementById("kcodech").checked = false;
				checkKey(document.getElementById("kcodech"));
			}
		}else{
			document.getElementById("kcode").value="0123456789ABCDE";
			document.getElementById("showkey").style.display="none";
		}
	}
</script>
<head>
	<title>添加系统管理员</title>
</head>
<body style="overflow-y: hidden">
	<form id="frm" name="frm" method="post" targetType="hidden"
		action="${basePath }/user/add.action">
		<div class="div_tj">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div  class="content">
								温馨提示 :添加系统管理员
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_cxtj">
							<div class="div_cxtjL"></div>
							<div class="div_cxtjC">
								管理员信息
							</div>
							<div class="div_cxtjR"></div>
						</div>
						<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							align="center" class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 管理员代码 ：
								</td>
								<td align="left" width="45%">
									<input id="userId" style="width: 160px;" name="entity.userId"
										type="text"
										class="validate[required,maxSize[<fmt:message key='userID' bundle='${PropsFieldLength}'/>],custom[onlyLetterNumber],ajax[checkUserByUserId]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 管理员名称 ：
								</td>
								<td align="left" width="45%">
									<input id="name" style="width: 160px;" name="entity.name"
										type="text" class="validate[required,maxSize[<fmt:message key='userName' bundle='${PropsFieldLength}'/>]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<c:if
								test="${sessionScope.CurrentUser.type=='DEFAULT_SUPER_ADMIN'}">
								<tr height="35">
									<td align="right" class="td_size" width="20%">
										管理员类型 ：
									</td>
									<td align="left" width="45%">
										<select id="type" name="entity.type">
											<option value="ADMIN" selected="selected">
												普通管理员
											</option>
											<option value="DEFAULT_ADMIN">
												高级管理员
											</option>
										</select>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
							</c:if>
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 管理员密码 ：
								</td>
								<td align="left" width="45%">
									<input id="password" style="width: 160px;"
										name="entity.password" type="password"
										class="validate[required,custom[password],maxSize[<fmt:message key='userPassword' bundle='${PropsFieldLength}'/>]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>

							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 确认密码 ：
								</td>
								<td align="left" width="45%">
									<input id="password1" style="width: 160px;" name="password1"
										type="password"
										class="validate[required,equals[password]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>

							<tr height="35" id="mgrIsNeedKeyCode"  style="display: none;">
								<td align="right" class="td_size" width="20%">
									是否启用 KEY ：
								</td>
								<td align="left" colspan="2"><input id="kcodech" type="checkbox" onclick="checkKey(this)"/>
									<span id="showkey" style="display: none;"><span class="required">*</span>
										<input id="kcode" name="entity.keyCode" style="width: 122px;" type="text" value="0123456789ABCDE" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
									</span>
								</td>
							</tr>

							<tr height="35">
								<td align="right" class="td_size">
									管理员描述 ：
								</td>
								<td align="left">
									<textarea id="description" name="entity.description" cols="20"
										rows="5" class="validate[maxSize[<fmt:message key='userDescription' bundle='${PropsFieldLength}'/>]]"></textarea>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3" height="5"></td>
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
					<td align="right">
						<rightButton:rightButton name="添加" onclick=" " className="btn_sec"
							action="/user/add.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick=
	window.close();
>
							关闭
						</button>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>

	</form>
</body>
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>