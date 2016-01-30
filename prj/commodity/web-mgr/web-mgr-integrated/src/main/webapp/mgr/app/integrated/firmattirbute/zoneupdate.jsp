<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	request.setAttribute("readonly","readonly");
%>
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
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}
		jQuery("#frm").validationEngine( {
		relative:true,
		ajaxFormValidation : true,
		ajaxFormValidationURL : "../../ajaxcheck/checkAddZoneForm.action",
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

		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validateform')) {
			}
		});
	});
</script>
	<head>
		<title>地域修改</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden">
	<iframe id="hiddenframe" name="hiddenframe" width=0 height=0 style="display:none" src="" application="yes"></iframe>
		<form id="frm" method="post"
			action="${basePath}/mfirmAttribute/mainTenance/updateZone.action" target="hiddenframe">
			<div class="div_cx"  style="overflow:auto;height:290px;" >
				<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :地域修改
							</div>
						</div>
					</td>
				</tr>
				<tr>
						<td>
							<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					基本信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<input type="hidden" id="oldSortNo" name="oldSortNo" value="${entity.sortNo}"/>
					<tr>
						<td align="right">
							<span class="required">*</span>
							地域编号
							：
						</td>
						<td>
							<input type="text" id="id"
								${readonly}
								class="validate[required,custom[onlyLetterNumber]] input_text"
								name="entity.code" value="${entity.code}" />
						</td>
						<script>
						if(${readonly!=null}){
								 frm.id.style.backgroundColor="#d0d0d0";
								}
						</script>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							地域名称 ：
						</td>
						<td  width="30%">
							<input type="text" id="name" class="validate[required,,maxSize[<fmt:message key='name_q' bundle='${PropsFieldLength}' />]] input_text" name="entity.name" value="${entity.name}"/>
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							是否可选择 ：
						</td>
						<td  width="30%">
							<select id="isvisibal" name="entity.isvisibal" class="input_text" style="width: 120px;" value="${entity.isvisibal}">
								<option value="Y" <c:if test="${entity.isvisibal eq 'Y'}">selected="selected"</c:if>>可选择</option>
								<option value="N" <c:if test="${entity.isvisibal eq 'N'}">selected="selected"</c:if>>不可选择</option>
							</select>
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							排序号：
						</td>
						<td  width="30%">
							<input type="text" id="sortNo"   class="validate[required,custom[onlyNumberSp],maxSize[2],ajax[checkZoneBySortNo]] input_text" name="entity.sortNo" value="${entity.sortNo}"/>
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					
				</tr> 
				</table>
			</div>
		</td>
	</tr>

</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>


						</td>
					</tr>
				</table>
			</div>
			<div class="div_cx">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="修改" onclick="" className="btn_sec"
								action="/mfirmAttribute/mainTenance/updateZone.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;<button class="btn_sec" onClick=window.close();>
								关闭
							</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>