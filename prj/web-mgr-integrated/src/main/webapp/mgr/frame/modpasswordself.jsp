<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<head>
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript">
</script>
<script
	src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="GBK">
</script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" charset="GBK">
</script>
	<script>
	jQuery(document).ready(function() {

		$("#frm").validationEngine('attach');
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					//$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					//$('#frm').submit 与 frm.submit 不同 因为$('#frm')与 frm不是同一个对象
					//如果使用$('#frm')提交则需要调用form.validationEngine('detach')取消绑定；因为在validationEngine
					//中注册了提交时间（form.bind("submit",methods._onSubmitEvent);）所以不取消绑定则会出现循环调用ajaxValidationCallback
					frm.submit();
			}
		}
	}	);
	});
</script>
	<title>密码修改</title>
</head>
<body>

	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/self/passwordSelfSave.action"
		targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="div_cxtj">
    	<div class="div_cxtjL"></div>
        <div class="div_cxtjC">密码修改</div>
        <div class="div_cxtjR"></div>
    </div>
			<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									管理员代码 ：
								</td>
								<td align="left">
									<input name="entity.userId" type="text" class="input_text_pwd"
										value="${entity.userId }" readonly="readonly">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									管理员名称 ：
								</td>
								<td align="left">
									<input name="entity.name" type="text" class="input_text_pwd"
										value="${entity.name }" readonly="readonly">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									原密码 ：
								</td>
								<td align="left">
									<input id="old" size="28" onkeydown="if(event.keyCode==32) return false"  name="oldPassword" type="password"
										class="validate[required,maxSize[16]] input_text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									新密码 ：
								</td>
								<td align="left">
									<input id="password" onkeydown="if(event.keyCode==32) return false"  size="28" name="entity.password" type="password"
										class="validate[required,maxSize[16],custom[password]] input_text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									新确认密码 ：
								</td>
								<td align="left">
									<input size="28" onkeydown="if(event.keyCode==32) return false"  name="specialforAudit.password1" type="password"
										id="password1" class="validate[required,equals[password]] input_text">
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
						<button class="btn_sec" id="update">
							保存
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick=
	window.close();
>
							关闭
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<%@ include file="../public/jsp/footinc.jsp"%>