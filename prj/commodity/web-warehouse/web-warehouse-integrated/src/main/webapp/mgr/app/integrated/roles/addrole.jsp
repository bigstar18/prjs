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
<head>
<script>
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		jQuery("#frm").validationEngine( {
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../ajaxcheck/warehouse/checkRoleForm.action",
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
						$('#frm').submit();
					}
				}
			}

			$("#addrole").click(function(check) {
				if ($("#frm").validationEngine('validateform')) {

				}
			});
		});
</script>
<title>角色添加</title>
</head>
<body style="overflow-y: hidden">
	<form id="frm" name="frm" method="post" targetType="hidden"
		action="${basePath }/role/addRole.action">
		<div class="div_cx">

			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :系统角色添加
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
							<tr height="30">
								<td>
									&nbsp;
								</td>
							</tr>
							<tr height="35">
								<td align="right" width="25%">
									<span class="required">*</span> 角色名称 ：
								</td>
								<td align="left" width="45%">
									<input name="entity.name" id="name" type="text"
										class="validate[required,maxSize[<fmt:message key='roleName' bundle='${PropsFieldLength}'/>],ajax[checkRoleByName]] input_text" />
								</td>
								<td>
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<tr height="35">
								<td align="right" width="25%">
									仓库编号:
								</td>
								<td align="left" width="45%">
									<input name="entity.warehouseID" readonly="readonly" class="validate[required] input_text" value="${wareHouseID }" id="warehouseID"  data-prompt-position="topLeft:1" />
									<!-- 
									<select>
										<option value="">
											请选择
										</option>
										<c:forEach var="wareHouse" items="${warehouseList}">
											<option title="${wareHouse.warehouseID}-${wareHouse.warehouseName}" value="${wareHouse.warehouseID}">${wareHouse.warehouseID}-${wareHouse.warehouseName}</option>
										</c:forEach>
									</select>
									 -->
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr height="35">
								<td align="right">
									角色描述 ：
								</td>
								<td align="left">

									<textarea name="entity.description" id="description" cols="21" rows="5"
										class="validate[maxSize[<fmt:message key='roleDescription' bundle='${PropsFieldLength}'/>]] input-text"></textarea>
								</td>
								<td>
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
					<td align="right">
						<rightButton:rightButton name="添加" onclick="" className="btn_sec"
							action="/role/addRole.action" id="addrole"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">
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