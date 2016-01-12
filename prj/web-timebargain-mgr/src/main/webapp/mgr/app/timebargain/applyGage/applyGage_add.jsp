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
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	
	function checkQty(){
		var quantity = document.getElementById("quantity").value;
		if(quantity==0){
			return "申请数量应大于0！";
			}
		
	}
	jQuery(document).ready(function() {

		//ajax验证交易商代码是否存在
		jQuery("#frm").validationEngine( {
			relative:true,
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/applyGage/formCheck.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		//提交前事件
		function beforeCall(form, options) {
			return true;
		}

		//提交后事件
		function ajaxValidationCallback(status, form, json, options) {
			//如果返回成功
			if (status === true) {
				var vaild = affirm("您确定要操作吗？");
				if(vaild){
					form.validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$('#frm').submit();
					document.getElementById("add").disabled=true;
				}
			}
		}

		//添加按钮注册点击事件
		$("#add").click(function(){
			//验证信息
			if ($("#frm").validationEngine('validateform')) {
			}
		});
	});
</script>
	<head>
		<title>抵顶业务申请</title>
	</head>
	<body>
		<form id="frm" action="${basePath}/timebargain/applyGage/addApplyGage.action" method="post" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="left"  class="table1_style">
					<tr>
						<td>
							<table border="0"  width="100%" align="left">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												温馨提示 :抵顶业务申请<br>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												抵顶申请信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" height="150px" align="center" class="table2_style">
												<tr height="40">
													<td align="right" width="20%">
														商品代码:
													</td>
													<td align="left" width="30%">
														<select name="entity.commodityId" id="com" 
															class="input_text_pwdmin">
															<c:forEach items="${list}" var="props">
																<option value="${props['COMMODITYID']}" >
																	
																	${props['NAME']}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>二级代码:
													</td>
													<td align="left" width="30%">
														<input id="customerId" name="entity.customerId" value="${entity.customerId }" style="width: 120px"
															class="validate[required] "></input>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>申请数量:
													</td>
													<td align="left" width="30%">
														<input id="quantity" name="entity.quantity" value="${entity.quantity }" style="width: 120px"
															class="validate[required,custom[integer],funcCall[checkQty]] "></input></input>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														申请类型:
													</td>
													<td align="left" width="30%">
														<select name="entity.applyType" id="applyType" 
															class="input_text_pwdmin">
															<c:forEach items="${applyGage_typeMap}" var="props">
																<option value="${props.key}" title='${props.value}'>
																	${props.value}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														备注:
													</td>
													<td colspan="3">
														<textarea name="entity.remark1" rows="3" cols="40"  style="width:450" styleClass="text" ></textarea>
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
			<table border="0" cellspacing="0" cellpadding="4" width="600px"
				align="left">
				<tr>
					<td align="center">
						<rightButton:rightButton name="保存" onclick="" className="btn_sec"
							action="/timebargain/applyGage/addApplyGage.action" id="add" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>
