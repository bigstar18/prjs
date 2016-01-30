<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
		String tradeMode=ApplicationContextInit.getConfig("tradeMode");

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
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/commodity/checkBreedForm.action",
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

			/*
			 * ѡ��ͼƬ
			 */
			$("#picture").change(function(){
				var value = $(this).attr("value");
				if(value){
					var exp = /.\.jpg|.\.gif|.\.jpeg|.\.bmp/i; 
					if (!exp.test(value.toLocaleLowerCase())) {//��֤��ʽ 
						$(this).attr("value","");
						alert("���ϴ��� .jpg��.gif��.jpeg��.bmp��β���ļ�");
					}
				}
			});
	});
</script>
	<head>
		<title>����Ʒ�����</title>
	</head>
	<body style="overflow-y: auto">
		<form id="frm" name="frm"  enctype="multipart/form-data"
			action="${basePath}/category/breed/addBreed.action?parentId=${parentId }" method="post"
			targetType="hidden">
			<input type="hidden" id="categoryId" name="entity.category.categoryId"
				value="${parentId }">
			<input type="hidden" name="entity.status" value="1">
			<input type="hidden" id="oldSortNo" name="oldSortNo" value="${entity.sortNo }">
			<input type="hidden" name="entity.tradeMode" value="<%=tradeMode %>">
			<div class="div_cx">
				<table border="0" align="left" class="table_left_style">
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												��ܰ��ʾ :Ʒ���������
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												Ʒ��������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="10"
												width="100%" height="100px" align="center"
												class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
													<span class="required">*</span>
														Ʒ��:
													</td>
													<td width="*">
														<input type="text" id="name"
															class="validate[required,maxSize[<fmt:message key='breedName' bundle='${PropsFieldLength}' />]] text-input"
															name="entity.breedName" value="${entity.breedName}">
														<span class="onFocus">&nbsp;���Ʒ��</span>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
													<span class="required">*</span>
														Ʒ����λ:
													</td>
													<td width="*">
														<input type="text" id="unit"
															class="validate[required,maxSize[<fmt:message key='unit' bundle='${PropsFieldLength}' />]] text-input"
															name="entity.unit" value="${entity.unit}">
														<span class="onFocus">&nbsp;���Ʒ����λ</span>
													</td>
												</tr>
												<c:if test="${fn:length(belongModuleMap)!=0}">
												<tr>
													<td align="right" width="25%">
														����Ȩ�ޣ�
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
														����ţ�
													</td>
													<td width="*">
														<input type="text" id="sortNo"
															onkeydown="if(event.keyCode==32) return false;"
															class="validate[required,maxSize[<fmt:message key='sortNo' bundle='${PropsFieldLength}' />],custom[onlyNumberSp],min[1],ajax[checkBreedBySortNo]] text-input"
															name="entity.sortNo" value="${entity.sortNo}">
															<span class="onFocus">&nbsp;����Ų����ظ���</span>
													</td>
												</tr>
												<!-- <tr height="20">
													<td align="right" width="25%">
														����ģʽ:
													</td>
													<td width="*">
														<select id="tradeMode" name="entity.tradeMode" class="validate[required] text-input">
															<option value="">��ѡ��</option>
															<c:forEach var="map" items="${breedModeMap}">
																<option value="${map.key }">${map.value }</option>
															</c:forEach>
														</select>
														<span class="onFocus">&nbsp;��ӽ���ģʽ</span>
													</td>
												</tr> -->
												<tr height="18">
												<td align="right" width="25%">
													ͼƬ·��:
												</td>
												<td width="*">
													<input type="file" name="picture" id="picture" />
													<span class="onFocus">��ѡ��ͼƬ�ļ�</span>
												</td>
											</tr>
												<c:if test="${entity.breedId==null}">
													<tr>
														<td align="center">
															&nbsp;
														</td>
														<td align="center">
															<rightButton:rightButton name="���" onclick=""
																className="btn_sec"
																action="/category/breed/addBreed.action" id="add"></rightButton:rightButton>
														</td>
													</tr>
												</c:if>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
