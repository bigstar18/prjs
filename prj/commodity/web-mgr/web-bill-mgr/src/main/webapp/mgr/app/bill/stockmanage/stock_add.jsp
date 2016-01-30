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
				var vaild = window.confirm("��ȷ��Ҫ������");
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
	//ͨ����Ʒ������ajax��ȡƷ���б�
	function getBreedByCategoryID(select) {
		select.disabled = true;//��ֹ���
		var value = select.options(select.selectedIndex).value;//ѡ�������
		$("#breedID").html("");//���Ʒ��ѡ����е�����
		$("#propertydiv").html("");//���������Ϣ
		$("#propertytitle").css("display", "none");//��������չʾ��
		if (value) {
			var url = "${basePath}/stock/addStock/jsonForStock/getBreedByCategoryID.action?categoryId="
					+ value + "&" + Math.random();//����·��
			$.getJSON(url, null, function call(result) {//ajax��ȡƷ���б�json��
						$.each(result, function(i, field) {//��������Ʒ���б�
									var option = document
											.createElement("option");//�����µ�Ʒ��ѡ��
								$.each(field, function(j, text) {//����Ʒ����Ϣ
											if (j == 0) {
												option.value = text;//Ʒ��ֵ
									} else if (j == 1) {
										option.innerText = text;//Ʒ����ʾ��Ϣ
										option.title = text;
									}else{
										option.unit=text;//title���Ա��浥λ
									}
								});
								$("#breedID").append(option);//����Ʒ��ѡ����
							});
					});
		}
		select.disabled = false;//�ָ��������
	}

	//ͨ��Ʒ����ȡ��Ʒ������Ϣ
	function getPropertyByBreedID(select){
		select.disabled=true;//���õ������
		$("#content").html("");//�������div����
		var selectedoption=select.options(select.selectedIndex);//Ʒ��ѡ����ѡ�е�Ʒ��
		var value = selectedoption.value;//Ʒ��ѡ����ѡ�е�Ʒ��ֵ
		$("#unit").attr("value",selectedoption.unit);    //������Ʒ��λֵ
		if(value){
			var url = "${basePath}/mgr/app/bill/checkneedless/getPropertyValueByBreedID.action?breedId="+value+"&"+Math.random();//����·��
			var frame = document.getElementById("hiddenframe");
			frame.src=url;
			if (!/*@cc_on!@*/0) {//��IE�����
				frame.onload = function(){
					var w = frame.contentWindow.document;
					var html = w.getElementById("modulebase").innerHTML;
					$("#content").append(html);
					$("#frm").validationEngine("attach");
				};
			} else {//IE�����
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
		select.disabled=false;//�ָ��������
	}
</script>
	<head>
		<title>�������</title>
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
												��ܰ��ʾ :¼��ֵ���Ϣ
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												¼��ֵ���Ϣ
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
														<span class='required'>*</span>�ֿ�ԭʼƾ֤��:
													</td>
													<td width="28%">
														<input type="text" id="realStockCode" onkeydown="if(event.keyCode==32) return false;"
															class="validate[required,custom[onlyLetterNumber],maxSize[30]]" data-prompt-position="bottomRight:1" name="entity.realStockCode"
															value="" />
													</td>
													<td width="*">
														<div class="onfocus">
															ע�����ݵĺ�����,ֻ�������ֺ���ĸ��
														</div>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class='required'>*</span>����������:
													</td>
													<td width="28%">
														<input type="text" id="ownerFirm"
															class="validate[required,ajax[checkStockByFirmId]] datepicker" name="entity.ownerFirm"  
															value=""  data-prompt-position="topLeft:1"/>
													</td>
													<td width="*">
														<div class="onfocus">
															��������ȷ�����������̴��룡
														</div>
													</td>
												</tr>
												<tr>
													<td align="right" width="30%">
														<span class='required'>*</span>��Ʒ��������:
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
														<span class='required'>*</span>�ֿ���:
													</td>
													<td width="28%">
														<select name="warehouseId" class="validate[required] input_text" id="warehouseId"  data-prompt-position="topLeft:1">
															<option value="">
																��ѡ��
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
																	return "*���������0��ֵ";
																}
																if(!flote(quantity,2)){
																		return "*���Ϊ2λС��";
																	}
																<%--if(intByNum(quantity,13)){
																	return "*�������������������Ϊ13λ";
																}--%>
																}
												</script>
												<tr height="20">
													<td align="right" width="30%">
														<span class='required'>*</span>��Ʒ����:
													</td>
													<td width="28%">
														<input type="text" id="quantity" 
															class="validate[required,custom[number],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}'/>]],funcCall[checkNum]]"
															name="entity.quantity" value=""  data-prompt-position="topRight:1"/>
													</td>
													<td width="*">
														<div class="onfocus">
															���������2λС�������֣�
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
							<rightButton:rightButton name="���" onclick="" className="btn_sec"
								action="${basePath}/stock/list/addStock.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick=
	window.close();;
>
								�ر�
							</button>
						</td>
					</tr>
				</table>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>