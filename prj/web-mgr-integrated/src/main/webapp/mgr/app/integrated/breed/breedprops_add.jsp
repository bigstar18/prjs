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
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document)
			.ready(
					function() {

						jQuery("#frm")
								.validationEngine(
										{
											ajaxFormValidation : true,
											ajaxFormValidationURL : "../../ajaxcheck/commodity/checkPropertyPropForm.action",
											onAjaxFormComplete : ajaxValidationCallback,
											onBeforeAjaxFormValidation : beforeCall
										});

						jQuery("input").change(
								function() {
									jQuery(this).attr("value",
											jQuery(this).val().trim());
								});

						function beforeCall(form, options) {
							return true;
						}

						// Called once the server replies to the ajax form validation request
						function ajaxValidationCallback(status, form, json,
								options) {
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

	function getBreedPropsValueType(select) {
		var value = select.options(select.selectedIndex).value;//选择属性ID
		if (value) {
			var url = "${basePath}/stock/addBreedProps/jsonForProps/getBreedPropsValueType.action?propertyId="
					+ value + "&" + Math.random();//访问路径
			$
					.getJSON(url,
							null,
							function call(result) {//ajax获取品名列表json串
								if (result == 1) {
									$("#propertyValue")
											.removeClass(
													"validate[required,maxSize[64],ajax[checkBreedPropByValue]] text-input")
											.removeClass(
													"validate[required,maxSize[32],custom[number],ajax[checkBreedPropsBySortNo]] text-input")
											.addClass(
													"validate[required,maxSize[64],custom[number],ajax[checkBreedPropByValue]] text-input");
								} else {
									$("#propertyValue")
											.removeClass(
													"validate[required,maxSize[64],ajax[checkBreedPropByValue]] text-input")
											.removeClass(
													"validate[required,maxSize[64],custom[number],ajax[checkBreedPropByValue]] text-input")
											.removeClass(
													"validate[required,maxSize[64],custom[number],ajax[checkBreedPropByValue]] text-input")
											.addClass(
													"validate[required,maxSize[64],ajax[checkBreedPropByValue]] text-input");

								}
							});

		}
	}
</script>
	<head>
		<title>属性添加</title>
	</head>
	<body style="overflow-y: auto">
		<form name="frm" id="frm"
			action="${basePath}/category/breed/addProps.action" method="post"
			targetType="hidden">
			<div class="div_cx">
				<table border="0" width="80%" align="center">
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												温馨提示 :品名下的属性管理
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												属性信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<input type="hidden" id="oldPropValue" name="oldPropValue"
													value="${entity.propertyValue }" />
												<input type="hidden" id="oldSortNo" name="oldSortNo"
													value="${entity.sortNo }" />
												<tr height="20">
													<td align="right" width="25%">
														品名代码:
													</td>
													<td width="*">
														<input type="text" id="breedId"
															name="entity.breed.breedId" value="${breedId}"
															readonly="readonly">
													</td>
												</tr>
												<tr height="20">
													<td align="right">
													<span class="required">*</span>
														属性名称:
													</td>
													<td>
														<select name="entity.categoryProperty.propertyId"
															class="validate[required]" id="propertyId"
															onchange="getBreedPropsValueType(this)" style="width:350px;" data-prompt-position="topRight:-50,3">
															<option value="">
																请选择
															</option>
															<c:forEach items="${propSet}" var="res">
																<option value="${res.propertyId}"
																	title='${res.propertyName}'
																	<c:if test="${entity.categoryProperty.propertyId==res.propertyId }">selected="selected"</c:if>>
																	${res.propertyName}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														属性值:
													</td>
													<td>
														<input type="text" id="propertyValue"
															class="validate[required,maxSize[64],ajax[checkBreedPropByValue]] text-input"
															name="entity.propertyValue"
															value="${entity.propertyValue}">
													</td>
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														排序号:
													</td>
													<td>
														<input type="text" id="sortNo" name="entity.sortNo"
															class="validate[required,maxSize[<fmt:message key='sortNo' bundle='${PropsFieldLength}' />],custom[onlyNumberSp],min[1],ajax[checkBreedPropsBySortNo]] text-input"
															value="${entity.sortNo}">
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
			<table border="0" cellspacing="0" cellpadding="4" width="50%"
				align="right">
				<tr>
					<td align="center">
						<rightButton:rightButton name="添加" onclick="" className="btn_sec"
							action="/category/breed/addProps.action" id="add"></rightButton:rightButton>
					</td>
					<td align="left">
						<button class="btn_sec" onClick=
	window.close();;
>
							关闭
						</button>
					</td>
				</tr>
			</table>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>