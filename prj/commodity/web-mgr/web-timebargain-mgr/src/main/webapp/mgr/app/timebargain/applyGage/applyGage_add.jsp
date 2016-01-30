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
			return "��������Ӧ����0��";
			}
		
	}
	jQuery(document).ready(function() {

		//ajax��֤�����̴����Ƿ����
		jQuery("#frm").validationEngine( {
			relative:true,
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/applyGage/formCheck.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		//�ύǰ�¼�
		function beforeCall(form, options) {
			return true;
		}

		//�ύ���¼�
		function ajaxValidationCallback(status, form, json, options) {
			//������سɹ�
			if (status === true) {
				var vaild = affirm("��ȷ��Ҫ������");
				if(vaild){
					form.validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$('#frm').submit();
					document.getElementById("add").disabled=true;
				}
			}
		}

		//��Ӱ�ťע�����¼�
		$("#add").click(function(){
			//��֤��Ϣ
			if ($("#frm").validationEngine('validateform')) {
			}
		});
	});
</script>
	<head>
		<title>�ֶ�ҵ������</title>
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
												��ܰ��ʾ :�ֶ�ҵ������<br>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�ֶ�������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" height="150px" align="center" class="table2_style">
												<tr height="40">
													<td align="right" width="20%">
														��Ʒ����:
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
														<span style="color: red">*&nbsp;</span>��������:
													</td>
													<td align="left" width="30%">
														<input id="customerId" name="entity.customerId" value="${entity.customerId }" style="width: 120px"
															class="validate[required] "></input>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>��������:
													</td>
													<td align="left" width="30%">
														<input id="quantity" name="entity.quantity" value="${entity.quantity }" style="width: 120px"
															class="validate[required,custom[integer],funcCall[checkQty]] "></input></input>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														��������:
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
														��ע:
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
						<rightButton:rightButton name="����" onclick="" className="btn_sec"
							action="/timebargain/applyGage/addApplyGage.action" id="add" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>
