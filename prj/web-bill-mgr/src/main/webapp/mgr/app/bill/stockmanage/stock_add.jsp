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
	<link rel="stylesheet"
		href="${skinPath }/css/autocomplete/jquery.autocomplete.css"
		type="text/css" />
	<script type='text/javascript' src='${publicPath }/js/autocomplete/jquery.autocomplete.js'></script>
	<script
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	
	jQuery(document).ready(function() {
		<c:if test="${not empty json}">
		var cities =${json};
		$("#ownerFirm").change().autocomplete(cities);
		</c:if>
		
	jQuery("#frm").validationEngine( {
		relative: true,
		ajaxFormValidation : true,
		ajaxFormValidationURL : "../../ajaxcheck/checkStockFirmIdForm.action",
		onAjaxFormComplete : ajaxValidationCallback,
		onBeforeAjaxFormValidation : beforeCall
	});
	
	function beforeCall(form, options) {
		return true;
	}

	// Called once the server replies to the ajax form validation request
		function ajaxValidationCallback(status, form, json, options) {
			if (status === true) {
				var vaild = window.confirm("您确定要操作吗？");
				if (vaild == true) {
					//form.validationEngine('detach');
					//$('#frm').attr('action', 'action');
					//$('#frm').submit();
					frm.submit();
				}
			}
		}
		$("#add").click(function(check) {
			$("#frm").validationEngine('validateform');
		});
	});
	//通过商品分类编号ajax获取品名列表
	function getBreedByCategoryID(select) {
		select.disabled = true;//禁止点击
		var value = select.options(select.selectedIndex).value;//选择分类编号
		$("#breedID").html("");//清空品名选择框中的数据
		$("#propertydiv").html("");//清空属性信息
		$("#propertytitle").css("display", "none");//隐藏属性展示区
		if (value) {
			var url = "${basePath}/stock/addStock/jsonForStock/getBreedByCategoryID.action?categoryId="
					+ value + "&" + Math.random();//访问路径
			$.getJSON(url, null, function call(result) {//ajax获取品名列表json串
						$.each(result, function(i, field) {//遍历各个品名列表
									var option = document
											.createElement("option");//生成新的品名选项
								$.each(field, function(j, text) {//遍历品名信息
											if (j == 0) {
												option.value = text;//品名值
									} else if (j == 1) {
										option.innerText = text;//品名显示信息
										option.title = text;
									}else{
										option.unit=text;//title属性保存单位
									}
								});
								$("#breedID").append(option);//加入品名选择器
							});
					});
		}
		select.disabled = false;//恢复点击功能
	}

	//通过品名获取商品属性信息
	function getPropertyByBreedID(select){
		select.disabled=true;//禁用点击功能
		$("#content").html("");//清空属性div内容
		var selectedoption=select.options(select.selectedIndex);//品名选择器选中的品名
		var value = selectedoption.value;//品名选择器选中的品名值
		$("#unit").attr("value",selectedoption.unit);    //设置商品单位值
		if(value){
			var url = "${basePath}/mgr/app/bill/checkneedless/getPropertyValueByBreedID.action?breedId="+value+"&"+Math.random();//访问路径
			var frame = document.getElementById("hiddenframe");
			frame.src=url;
			if (!/*@cc_on!@*/0) {//非IE浏览器
				frame.onload = function(){
					var w = frame.contentWindow.document;
					var html = w.getElementById("modulebase").innerHTML;
					$("#content").append(html);
					$("#frm").validationEngine("attach");
				};
			} else {//IE浏览器
				frame.onreadystatechange = function(){
					if (frame.readyState == "complete"){
						var w = frame.contentWindow.document;
						var html = w.getElementById("modulebase").innerHTML;
						$("#content").append(html);
						$("#frm").validationEngine("attach");
					}
				};
			}
		}
		select.disabled=false;//恢复点击功能
	}
</script>
	<head>
		<title>属性添加</title>
	</head>
	<body style="overflow-y: hidden">
		<iframe id="hiddenframe" name="hiddenframe" widht="0" height="0" style="display:none;"></iframe>
		<form id="frm" name="frm" method="post"
			action="${basePath}/stock/list/addStock.action" targetType="hidden">
			<input type="hidden" id="categoryId" name="categoryId"
				value="${categoryId }">
			<input id="oldFirmId" name="oldFirmId" value="" type="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<table border="0" width="80%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												温馨提示 :录入仓单信息
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												录入仓单信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div
											style="overflow-x: hidden; position: absolute; z-index: 5; overflow: auto; height: 300px; width: 100%;">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="80">
													<td align="right" width="25%">
														<span class='required'>*</span>仓库原始凭证号:
													</td>
													<td width="28%">
														<input type="text" id="realStockCode" onkeydown="if(event.keyCode==32) return false;"
															class="validate[required,custom[onlyLetterNumber],maxSize[30]]" data-prompt-position="bottomRight:1" name="entity.realStockCode"
															value="" />
													</td>
													<td width="*">
														<div class="onfocus">
															注意数据的合理性,只能是数字和字母！
														</div>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class='required'>*</span>所属交易商:
													</td>
													<td width="28%">
														<input type="text" id="ownerFirm"
															class="validate[required,ajax[checkStockByFirmId]] datepicker" name="entity.ownerFirm"  
															value=""  data-prompt-position="topLeft:1"/>
													</td>
													<td width="*">
														<div class="onfocus">
															请输入正确的所属交易商代码！
														</div>
													</td>
												</tr>
												<tr>
													<td align="right" width="30%">
														<span class='required'>*</span>商品所属分类:
													</td>
													<td colspan="2">
														<select id="categoryID" name="categoryId" size="8"
															class="select validate[required]" style="width: 150px;"
															onchange="getBreedByCategoryID(this)"  data-prompt-position="topLeft:1">
															<c:forEach var="category" items="${categoryList}">
																<option value="${category.categoryId}" title="${category.categoryName}">
																	${category.categoryName}
																</option>
															</c:forEach>
														</select>
														<select id="breedID" name="entity.breed.breedId" size="8"
															class="select validate[required]" style="width: 150px;"
															onchange="getPropertyByBreedID(this)"></select>
													</td>
												</tr>
												<tr>
													<td id="content" colspan="3">
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="30%">
														<span class='required'>*</span>仓库编号:
													</td>
													<td width="28%">
														<select name="warehouseId" class="validate[required] input_text" id="warehouseId"  data-prompt-position="topLeft:1">
															<option value="">
																请选择
															</option>
															<c:forEach var="wareHouse" items="${warehouseList}">
																<option title="${wareHouse.warehouseId}-${wareHouse.warehouseName}" value="${wareHouse.warehouseId}">${wareHouse.warehouseId}-${wareHouse.warehouseName}</option>
															</c:forEach>
														</select>
													</td>
													<td width="*">
														&nbsp;
													</td>
												</tr>
												<script >
													function checkNum(){
																var quantity=document.getElementById("quantity").value;
																if(quantity <=0 ){
																	return "*请输入大于0的值";
																}
																if(!flote(quantity,2)){
																		return "*最多为2位小数";
																	}
																<%--if(intByNum(quantity,13)){
																	return "*输入数量整数部分最多为13位";
																}--%>
																}
												</script>
												<tr height="20">
													<td align="right" width="30%">
														<span class='required'>*</span>商品数量:
													</td>
													<td width="28%">
														<input type="text" id="quantity" 
															class="validate[required,custom[number],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}'/>]],funcCall[checkNum]]"
															name="entity.quantity" value=""  data-prompt-position="topRight:1"/>
													</td>
													<td width="*">
														<div class="onfocus">
															请输入最多2位小数的数字！
														</div>
													</td>
												</tr>
												<tr height="20">
														<input type="hidden" id="unit" name="entity.unit" value="" />
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
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="60%"
					align="right">
					<tr>
						<td align="center">
							<rightButton:rightButton name="添加" onclick="" className="btn_sec"
								action="${basePath}/stock/list/addStock.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;&nbsp;
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