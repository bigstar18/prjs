<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	//frontIsNeedKey：Y表示前台管理启用key 
	<%if(Global.getMarketInfoMap().get("frontIsNeedKey").equals("Y") 
			&& Global.getMarketInfoMap().get("marketNO") != null 
			&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){%>
		$("#frontIsNeedKeyCode").css("display","block");
	<%}%>
	if ("" != '${ReturnValue.info}' + "") {
		//parent.document.frames.item('leftFrame').location.reload();
	}

	jQuery("#frm").validationEngine( {
		relative:true,
		ajaxFormValidation : true,
		ajaxFormValidationURL : "../../ajaxcheck/checkFirmForm.action",
		onAjaxFormComplete : ajaxValidationCallback,
		onBeforeAjaxFormValidation : beforeCall
	});
	function beforeCall(form, options) {
		return true;
	}

	// Called once the server replies to the ajax form validation request
		function ajaxValidationCallback(status, form, json, options) {
		if (status === true) {
				var vaild = window.confirm("你确定要操作吗？");
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
function checkUserId(){
	var userId =$("#username").val();
	if(!isStr(userId,true)){
		return "*包含非法字符";
	}
	
}
function checkKey(inp){
	if(inp.checked){
		document.getElementById("showkey").style.display="";
		var m = initKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("id").value,frm);
		if(!m.passed){
			alert(m.msg);
			document.getElementById("kcodech").checked = false;
			checkKey(document.getElementById("kcodech"));
		}
		document.getElementById("enableKey").value="Y";
	}else{
		document.getElementById("kcode").value="0123456789ABCDE";
		document.getElementById("showkey").style.display="none";
		document.getElementById("enableKey").value="N";
	}
}
</script>
	<head>
		<title>交易商添加</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
		<body onload="getFocus('id');">
			<div class="div_cx">
		<form id="frm" name="frm" method="post" 
			action="${basePath}/trade/mfirm/addDirectMfirm.action" targetType="hidden">
				<table border="0" width="100%" align="center">
					<div class="warning">
						<div class="content">
							温馨提示 :交易商添加
						</div>
					</div>
					<tr>
						<td>
							<%@include file="dfirm_commont.jsp" %>
						</td>
					</tr>
					<tr>
						<td align="right">
							<rightButton:rightButton name="添加" onclick="" className="btn_sec"
								action="/trade/mfirm/addDirectMfirm.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center" colspan="4">&nbsp;
						</td>
					</tr>
				</table>
		</form>
			</div>

	</body>
</html>
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
