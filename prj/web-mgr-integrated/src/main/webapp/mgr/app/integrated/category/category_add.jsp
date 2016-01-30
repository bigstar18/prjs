<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/app/integrated/commodity.css" type="text/css" />
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
		var type = document.getElementById("isOffSet").value;
		if (type == "Y") {
			document.getElementById("content").style.display = "block";
		} else {
			document.getElementById("content").style.display = "none";
		}
		jQuery("#frm").validationEngine( {
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/commodity/checkCategoryForm.action",
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
</script>
	<head>
		<title>商品添加</title>
	</head>
	<body style="overflow-y: auto">
		<form id="frm" name="frm" method="post"
			action="${basePath}/category/commodity/addCategory.action"
			targetType="hidden">
			<input type="hidden" id="parentCategoryId"
				name="entity.parentCategory.categoryId" value="${parentId }">
			<input type="hidden" name="entity.status" value="1">
			<input type="hidden" name="entity.type" value="leaf">
			<div class="div_cx">
				<table border="0" width="80%" align="center" class="table_out_style">
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												温馨提示 :商品分类管理
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												分类基本信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<input type="hidden" id="oldSortNo" name="oldSortNo"
													value="${entity.sortNo }" />
												<c:if test="${entity.categoryId!=null }">
													<tr height="20">
														<td align="right" width="25%">
															分类代码：
														</td>
														<td width="*">
															<input type="text" id="cateogryId" name="cateogryId"
																value="${entity.categoryId}" readonly="readonly">
														</td>
													</tr>
												</c:if>
												<tr height="20">
													<td align="right">
													<span class="required">*</span>
														分类名称：
													</td>
													<td>
														<input type="text" id="name"
															class="validate[required,maxSize[<fmt:message key='categoryName' bundle='${PropsFieldLength}' />]] text-input"
															name="entity.categoryName" value="${entity.categoryName}">
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														是否允许溢短货款申请：
													</td>
													<td width="*">
														<select id="isOffSet" name="entity.isOffSet"
															onchange="checkOffSet()"
															class="validate[required,maxSize[10]] text-input">
															<c:forEach items="${isPickOrSubOrderMap}" var="map">
																<option value="${map.key }"
																	<c:if test="${entity.isOffSet==map.key }">selected="selected"</c:if>>
																	${map.value }
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td colspan="3">
														<div id="content" style="display: none;">
															<table border="0" cellspacing="0" cellpadding="4"
																width="100%" align="center">
																<tr height="20">
																	<td align="right" width="23%">
																	<span class="required">*</span>
																		溢短货款比例率：
																	</td>
																	<td width="*">
																		<input id="offSetRate" name="entity.offSetRate_v"
																			value="${entity.offSetRate_v }"
																			class="validate[required,min[0.01],custom[number],funcCall[checkRate]] input_text">
																		%
																	</td>
															</table>
														</div>
													</td>
												</tr>
												<script>
													function checkOffSet() {
														var type = document.getElementById("isOffSet").value;
														if (type == "Y") {
															document.getElementById("content").style.display = "block";
														} else {
															document.getElementById("content").style.display = "none";
														}

													}
													function checkRate(){
														var rate = document.getElementById("offSetRate").value;
														if(!flote(rate,2)){
															return "*溢短货款比例率小数后面为2位";
															}
														else if(rate>${OffSet}||rate<0){
															return "*溢短货款比例率必须大于0且小于等于${OffSet}";
															}

														
													}
													</script>
												<c:if test="${fn:length(belongModuleMap)!=0}">
												<tr>
													<td align="right" width="25%">
														交易权限：
													</td>
													<td width="*" colspan="2">
														<c:forEach items="${belongModuleMap}" var="map">
															<input type="checkbox" name="belongModule"
																<c:if test="${fn:contains(entity.belongModule,map.key)}">checked="checked"</c:if>
																value="${map.key }" />${map.value.cnName }
														</c:forEach>
													</td>
												</tr>
												</c:if>
												<tr>
													<td align="right" width="25%">
													<span class="required">*</span>
														排序号：
													</td>
													<td width="15%">
														<input type="text" id="sortNo"
															onkeydown="if(event.keyCode==32) return false;"
															class="validate[required,maxSize[<fmt:message key='sortNo' bundle='${PropsFieldLength}' />],custom[onlyNumberSp],min[1],ajax[checkCategoryBySortNo]] text-input"
															name="entity.sortNo" value="${entity.sortNo}">
													</td>
													<td width="*">
														<div class="onfocus">
															同父分类下排序号不可重复！
														</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														说明：
													</td>
													<td>
														<textarea rows="4" cols="20"
															class="validate[maxSize[<fmt:message key='cateogryNote' bundle='${PropsFieldLength}' />]]"
															name="entity.note" id="note">${entity.note }</textarea>
													</td>
												</tr>
												<tr>
													<td align="right" colspan="3">
														<c:if test="${entity.categoryId==null}">
															<rightButton:rightButton name="添加" className="btn_sec"
																action="/category/commodity/addCategory.action" id="add"
																onclick=""></rightButton:rightButton>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</c:if>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="right">
							<%@include file="category_add_list.jsp"%>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
