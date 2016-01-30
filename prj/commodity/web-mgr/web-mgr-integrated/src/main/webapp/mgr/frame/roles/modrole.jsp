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
	type="text/javascript" charset="GBK"></script>
<script>
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		jQuery("#frm").validationEngine({
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../ajaxcheck/checkRoleForm.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		function beforeCall(form, options) {
			return true;
		}

		function ajaxValidationCallback(status, form, json, options) {
			if (status === true) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					form.validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$("#name").val($("#name").val().trim());
					$('#frm').submit();
				}
			}
		}

		//$("#frm").validationEngine('attach');
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validateform')) {

			}
			/*if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$("#name").val($("#name").val().trim());
					$('#frm').submit();
				}
			}*/
		});
	});
</script>
<head>
	<title>系统角色修改</title>
<meta http-equiv="Pragma" content="no-cache">
</head>
<body style="overflow-y: hidden">

	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/role/updateRole.action" targetType="hidden"
		onkeypress="
	if (event.keyCode == 13 || event.which == 13) {
		return false;
	};"
>
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :系统角色修改
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_cxtj">
							<div class="div_cxtjL"></div>
							<div class="div_cxtjC">
								系统角色信息
							</div>
							<div class="div_cxtjR"></div>
						</div>
						<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							align="center" class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="25%">
									角色代码 ：
								</td>
								<td align="left">
									${entity.id }
									<input type="hidden" name="entity.id" id="id"
										value="${entity.id }">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									<span class="required">*</span> 角色名称 ：
									<input type="hidden" id="oldName" name="oldName" value="${entity.name }"/>
								</td>
								<td align="left">
									<input name="entity.name" type="text" id="name"
										value="${entity.name }"
										class="validate[required,maxSize[<fmt:message key='roleName' bundle='${PropsFieldLength}'/>],ajax[checkRoleByName]] input_text"/>
								</td>
								<td>
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<tr>
								<td align="right" class="td_size">
									角色描述 ：
								</td>
								<td align="left">
									<textarea name="entity.description" rows="5" cols="21"
										id="description" class="validate[maxSize[<fmt:message key='roleDescription' bundle='${PropsFieldLength}'/>]] input-text">${entity.description }</textarea>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3" height="3">
									&nbsp;
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
				<tr>
					<td height="45">
						&nbsp;
					</td>
					<td align="right" id="tdId">
						<rightButton:rightButton name="修改" onclick="" className="btn_sec"
							action="/role/updateRole.action" id="update"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick=
	window.close();
>
							关闭
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>