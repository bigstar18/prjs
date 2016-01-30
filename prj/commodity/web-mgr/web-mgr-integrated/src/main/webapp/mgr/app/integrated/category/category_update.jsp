<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/app/integrated/commodity.css"
		type="text/css" />
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
	var content="";
	
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
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
					var modules="";
					var oldModules='${entity.belongModule}';
					$("input[name='belongModule']:checked").each(function(){
							if(modules!=""){
								modules=modules+"/";
							}
							modules=modules+$(this).val();
						});
					getInfoByCategoryId(modules);
					var vaild = window.confirm("��ȷ��Ҫ������");
					if (vaild == true) {
						if(modules!=oldModules && modules.indexOf(oldModules)<0){
							if(content!=""&&window.confirm(content)){
								form.validationEngine('detach');
								//$('#frm').attr('action', 'action');
								$('#frm').submit();
							}
							if(content==""){
								form.validationEngine('detach');
								//$('#frm').attr('action', 'action');
								$('#frm').submit();
							}	
						}else{
							form.validationEngine('detach');
							//$('#frm').attr('action', 'action');
							$('#frm').submit();
						}
						
					}
				}
			}

			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validateform')) {

				}
			});
		});

	function getContent(){
		if(content==""){
			getContent(content);
		}else{
			return content;
		}
	}
	function getInfoByCategoryId(modules) {
		var url = "${basePath}/commoditymanage/jsonForCommodity/getCommodityInfoByCategoryId.action?categoryId=${entity.categoryId}"
				+ "&module="+modules+"&" + Math.random();//����·��
		$.ajaxSettings.async = false;		
		$.getJSON(url,null,function call(result) {//ajax��ȡ�����·����Ʒ��������json��
			var categorySize=result[0];
			var breedSize=result[1];
			if(${entity.parentCategory.categoryId==-1}){
					if(categorySize!=0&&breedSize!=0){
						content="ͬ������������µ�"+categorySize+"���ӷ���ģ��Լ�"+breedSize+"��Ʒ���Ľ���Ȩ�ޣ���ȷ��Ҫ������";
					}else if(categorySize!=0&&breedSize==0){
						content="ͬ������������µ�"+categorySize+"���ӷ���Ľ���Ȩ�ޣ���ȷ��Ҫ������";
					}else if(categorySize==0&&breedSize!=0){
						content="ͬ������������µ�"+breedSize+"��Ʒ���Ľ���Ȩ�ޣ���ȷ��Ҫ������";
					}else if(categorySize==0&&breedSize==0){
						content="";
					}
				}else{
					if(breedSize!=0){
						content="ͬ������������µ�"+breedSize+"��Ʒ���Ľ���Ȩ�ޣ���ȷ��Ҫ������";
					}else{
						content="";	
					}
			}
		});
	}
</script>
	<head>
		<title>��Ʒ�޸�</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: auto">

		<div class="div_cx">
			<table border="0" width="80%" align="center" class="table_out_style">
				<form id="frm" method="post"
					action="${basePath}/category/commodity/updateCategory.action"
					targetType="hidden">
					<input type="hidden" id="parentCategoryId"
						name="entity.parentCategory.categoryId"
						value="${entity.parentCategory.categoryId}">
					<input type="hidden" name="entity.type" value="${entity.type }">
					<input type="hidden" name="entity.categoryId"
						value="${entity.categoryId }">
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="warning">
										<div class="content">
											��ܰ��ʾ :��Ʒ�������
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											���������Ϣ
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div class="div_tj">
										<table border="0" cellspacing="0" cellpadding="4" width="100%"
											align="center" class="table2_style">
											<input type="hidden" id="oldSortNo" name="oldSortNo"
												value="${entity.sortNo }" />
											<c:if test="${entity.categoryId!=null }">
												<tr height="20">
													<td align="right" width="25%">
														�������:
													</td>
													<td width="*">
														<input type="hidden" id="cateogryId" name="cateogryId"
															value="${entity.categoryId}" readonly="readonly">${entity.categoryId}
													</td>
												</tr>
											</c:if>
											<tr height="20">
												<td align="right">
												<span class="required">*</span>
													��������:
												</td>
												<td>
													<input type="text" id="name"
														class="validate[required,maxSize[<fmt:message key='categoryName' bundle='${PropsFieldLength}' />]] text-input"
														name="entity.categoryName" value="${entity.categoryName}">
												</td>
											</tr>
												<input id="isOffSet" type="hidden" name="entity.isOffSet" value="${entity.isOffSet }">
												<c:if test="${entity.isOffSet=='Y'&&entity.categoryId!=-1&&entity.type!='category' }">
													<tr>
														<td colspan="3">
															<div id="content">
																<table border="0" cellspacing="0" cellpadding="4"
																	width="100%" align="center">
																	<tr height="20">
																		<td align="right" width="23%">
																		<span class="required">*</span>
																			��̻��������
																		</td>
																		<td width="*">
																			<input id="offSetRate" name="entity.offSetRate_v" value="${entity.offSetRate_v }"
																				class="validate[required,min[0.01],custom[number],funcCall[checkRate]] text-input">%
																		</td>
																</table>
															</div>
														</td>
													</tr>
													</c:if>
													<script>
													function checkRate(){
														var rate = document.getElementById("offSetRate").value;
														if(!flote(rate,2)){
															return "*��̻��������С������Ϊ2λ";
															}
														else if(rate>${OffSet}||rate<0){
															return "*��̻�������ʱ������0��С�ڵ���${OffSet}";
															}

														
													}
													</script>
												<c:if test="${fn:length(belongModuleMap)!=0}">
													<tr>
														<td align="right" width="25%">
															����Ȩ�ޣ�
														</td>
														<td width="*" colspan="2">
															<c:forEach items="${belongModuleMap}" var="map">
																<input type="checkbox" id="belongModule" name="belongModule"
																	<c:if test="${fn:contains(entity.belongModule,map.key)}">checked="checked"</c:if>
																	value="${map.key }" />${map.value.cnName }
															</c:forEach>
														</td>
	
													</tr>
												</c:if>
											<c:if test="${entity.categoryId==-1}">
												<input type="hidden" id="sortNo" name="entity.sortNo" value="${entity.sortNo}">
											</c:if>
												
											<c:if test="${entity.categoryId!=-1}">
												<tr>
													<td align="right" width="25%">
													<span class="required">*</span>
														�����:
													</td>
													<td width="15%">
														<input type="text" id="sortNo" onkeydown="if(event.keyCode==32) return false;"
															class="validate[required,maxSize[<fmt:message key='sortNo' bundle='${PropsFieldLength}' />],custom[onlyNumberSp],min[1],ajax[checkCategoryBySortNo]] text-input"
															name="entity.sortNo" value="${entity.sortNo}">
													</td>
													<td width="*">
														<div class="onfocus">
															ͬ������������Ų����ظ���
														</div>
													</td>
												</tr>
											</c:if>
											<tr>
												<td align="right">
													˵��:
												</td>
												<td>
													<textarea rows="4" cols="20" class="validate[maxSize[<fmt:message key='cateogryNote' bundle='${PropsFieldLength}' />]]" name="entity.note" id="note">${entity.note }</textarea>
												</td>
											</tr>
											<tr>
												<td align="right" colspan="3">
													<rightButton:rightButton name="�޸�" onclick=""
														className="btn_sec"
														action="/category/commodity/updateCategory.action"
														id="update"></rightButton:rightButton>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</form>
				<tr>
						<td align="right">&nbsp;
						</td></tr>
				<c:if test="${(entity.parentCategory.categoryId==-1&&entity.type=='category')||entity.categoryId==-1}">
					<tr>
						<td align="right"><%@include file="category_add_list.jsp"%></td>
					</tr>
				</c:if>
				<c:if
					test="${(entity.parentCategory.categoryId==-1&&(fn:length(categoryList))==0)||(entity.type=='leaf'&&entity.parentCategory.categoryId!=-1)}">
					<tr>
						<td>
							<%@include file="property_list.jsp"%>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</body>
</html>
