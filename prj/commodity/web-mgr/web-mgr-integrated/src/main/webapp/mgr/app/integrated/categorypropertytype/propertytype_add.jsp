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
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		jQuery("#frm").validationEngine( {
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/commodity/checkCategoryPropTypeForm.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		function beforeCall(form, options) {
			return true;
		}
		// Called once the server replies to the ajax form validation request
			function ajaxValidationCallback(status, form, json, options) {
				if (status === true) {
					var vaild = affirm("��ȷ��Ҫ������");
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
</script>
<head>
	<title>�����Ʒ������������</title>
</head>
<body style="overflow-y: hidden">
	<form id="frm" name="frm" method="post" targetType="hidden"
		action="${basePath }/category/propertytype/addPropertyType.action">
		<div class="div_tj">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div  class="content">
								��ܰ��ʾ :�����Ʒ������������
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_cxtj">
							<div class="div_cxtjL"></div>
							<div class="div_cxtjC">
								��Ʒ��������������Ϣ
							</div>
							<div class="div_cxtjR"></div>
						</div>
						<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							align="center" class="st_bor">
							<input type="hidden" id="oldSortNo" name="oldSortNo" value="${entity.sortNo }"/>
							<input type="hidden" id="oldName" name="oldName" value="${entity.name }">
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> ������������ ��
								</td>
								<td align="left" width="45%">
									<input id="name" style="width: 160px;" name="entity.name"
										type="text" class="validate[required,maxSize[32],ajax[checkCategoryPropTypeByName]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										����Ϊ�գ�
									</div>
								</td>
							</tr><input type="hidden" name="entity.status" value="0"/>
							<%/* 
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> ��������״̬ ��
								</td>
								<td align="left" width="45%">
										<select id="status" name="entity.status" class="validate[required]" style="width: 160px;">
											<option value="0">�ɼ�</option>
											<option value="1">���ɼ�</option>
										</select>
								</td>
								<td align="left">
									<div class="onfocus">
										����Ϊ�գ�
									</div>
								</td>
							</tr>*/%>
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> ����� ��
								</td>
								<td align="left" width="45%">
									<input id="sortNo" style="width: 160px;" name="entity.sortNo"
										type="text" class="validate[required,maxSize[2],ajax[checkCategoryPropTypeBySortNo]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										����Ϊ�գ�
									</div>
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
					<td align="right">
						<rightButton:rightButton name="���" onclick=" " className="btn_sec"
							action="/category/propertytype/addPropertyType.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick=
	window.close();
>
							�ر�
						</button>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>

	</form>
</body>

<%@ include file="/mgr/public/jsp/footinc.jsp"%>