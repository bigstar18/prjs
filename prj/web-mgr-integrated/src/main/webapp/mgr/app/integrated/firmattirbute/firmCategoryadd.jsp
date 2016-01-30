<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
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
	if ("" != '${ReturnValue.info}' + "") {
		parent.document.frames('leftFrame').location.reload();
	}

	jQuery("#frm").validationEngine( {
		relative:true,
		ajaxFormValidation : true,
		ajaxFormValidationURL : "../../ajaxcheck/checkAddFirmCategoryForm.action",
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
					$('#frm').submit();
				}
			}
		}

		$("#add").click(function(check) {
			if ($("#frm").validationEngine('validateform')) {
			}
		});
	});
function pimg(id) {
	return document.getElementById(id);
}
function onfo(id) {
	pimg(id).className = "";
}
function none(id) {
	pimg(id).className = "";
}
</script>
	<head>
		<title>交易商类别添加</title>
	</head>
		<body>
		<form id="frm" method="post"
			action="${basePath}/mfirmAttribute/mainTenance/addFirmCategory.action" targetType="hidden">
			<div class="div_cx"  style="overflow-y:auto;height:330px;" >
				<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :交易商类别添加
							</div>
						</div>
					</td>
				</tr>
					<tr>
						<td>
							<%@include file="firmCategorycommontable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="添加" onclick="" className="btn_sec"
								action="/mfirmAttribute/mainTenance/addFirmCategory.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick=
	window.close();
>
								关闭
							</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<script type="text/javascript">
	
</script>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>