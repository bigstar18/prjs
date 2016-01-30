<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${basePath }/broker/app/integrated/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function() {

		jQuery("#frm").validationEngine( {
			relative:true,
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/ajaxFirmApply/checkFirmApplyForm.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});
	
	
		function beforeCall(form, options) {
			return true;
		}
	
		function ajaxValidationCallback(status, form, json, options) {
			if (status === true) {
					var vaild = affirm("��ȷ��Ҫ������");
					if (vaild == true) {
						form.validationEngine('detach');
						//$('#frm').attr('action', 'action');
						$('#frm').submit();
						document.getElementById("add").disabled=true;
					}
				}
		}
	
		$("#add").click(function(check) {
			if ($("#frm").validationEngine('validateform')) {
				}
			});
		});
		function getHidden(type){
			if(type !=3){
				$("#content").css("display","block");
			}else{
				$("#content").css("display","none");
			}
		}
		function reduceValue(type){
			if(type==3){
				//������������Ϊ����ʱ����ֹ֮ǰ��������š���ҵ��š���֯�ṹ����ͷ��˴����ֵ¼��
				$("#zoneCode").attr("value","");
				$("#industryCode").attr("value","");
				$("#organizationCode").attr("value","");
				$("#corporateRepresentative").attr("value","");
			}
		}
		function checkUserId(){
			var userId =$("#username").val();
			if(!isStr(userId,true)){
				return "*�����Ƿ��ַ�";
			}
		}
</script>
	<head>
		<title>�����̿�������</title>
	</head>
		<body>
		<form id="frm" method="post"
			action="${basePath}/broker/firmManager/addFirm.action">
			<div class="div_cx"  style="overflow:auto;height:590px;" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :��ӿ�������
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="center" class="table2_style">
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														�û�����
													</td>
													<td  width="30%">
														<input type="text" id="userId" class="validate[required,funcCall[checkUserId],maxSize[16] input_text" name="entity.userId"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														���������ƣ�
													</td>
													<td>
														<input type="text" id="name"
															class="validate[required,maxSize[15]] input_text"
															name="entity.name" value="${entity.name}" />
													</td>
													<td align="right">
														������ȫ�ƣ�
													</td>
													<td>
														<input type="text" id="fullName"
															class="validate[maxSize[30]] input_text" name="entity.fullName"
															value="${entity.fullName}" />
													</td>
												</tr>
												<tr>
													<td align="right">
														�����Ӽ��̣�
													</td>
													<td>
														<select id="type" class="input_text" name="entity.brFirmApply.brokerAgeId"  data-prompt-position="topLeft:0">
															<option value="">��ѡ��</option>
															<c:forEach items="${list}" var="brokerAge">
																<option value="${brokerAge['BROKERAGEID']}">${brokerAge['NAME']}</option>
															</c:forEach>
														</select>
													</td>
													<td align="right">
														<span class="required">*</span>
														���������ͣ�
													</td>
													<td align="left">
														<select id="type" class="validate[required] input_text" name="entity.type" onchange="getHidden(this.value)" onclick="reduceValue(this.value)" data-prompt-position="topLeft:0">
															<option value="">��ѡ��</option>
															<c:forEach items="${mfirmTypeMap}" var="map">
																<option value="${map.key}" title='${map.value}'
																	<c:if test="${entity.type==map.key }">selected="selected"</c:if>>
																	${map.value}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td colspan="4">
														<div id="content" style="display: block;">
															<table border="0" cellspacing="0" cellpadding="1"
																width="100%" align="center" height="60px;">
																<tr>
																	<td align="right" width="20%">
																		������ҵ���� ��&nbsp;
																	</td>
																	<td width="30%">
																		<input type="text" id="industryCode" class="validate[custom[onlyLetterNumber],maxSize[16]] input_text" name="entity.industryCode" 
																			value="${entity.industryCode}" data-prompt-position="topLeft:0">
																	</td>
																	<td align="right" width="21%">
																		����������� ��
																	</td>
																	<td width="*">
																		&nbsp;<input type="text" id="zoneCode" class="validate[custom[onlyLetterNumber],maxSize[16]] input_text" name="entity.zoneCode" 
																			value="${entity.zoneCode}" data-prompt-position="topLeft:0">
																	</td>
																</tr> 
																<tr>
																	<td align="right" width="20%">
																		<span class="required">*</span>��֯�ṹ���� ��&nbsp;
																	</td>
																	<td width="30%">
																		<input type="text" id="organizationCode" class="validate[required,custom[onlyLetterNumber],maxSize[9]] input_text" name="entity.organizationCode" 
																			value="${entity.organizationCode}" data-prompt-position="topLeft:0">
																	</td>
																	<td align="right" width="21%">
																		���˴��� ��
																	</td>
																	<td width="*">
																		&nbsp;<input type="text" id="corporateRepresentative" class="validate[maxSize[16]] input_text" name="entity.corporateRepresentative" 
																			value="${entity.corporateRepresentative}" data-prompt-position="topLeft:0">
																	</td>
																</tr> 
																
															</table>
													</td>
												</tr>
												</div>
												</td>
											</tr>
											<tr>
												<td align="right" width="20%">
													<span class="required">*</span>
													֤�����ͣ�
												</td>
												<td width="30%">
													<select id="certificateType" name="entity.certificateType" class="validate[required] input_text">
														<option value="">��ѡ��</option>
														<c:forEach items="${certificateTypeMap}" var="map">
															<option value="${map.key}" title='${map.value}'>${map.value}</option>
														</c:forEach>
													</select>
												</td>
							
												<td align="right" width="21%">
													<span class="required">*</span>
													֤����ţ�
												</td>
												<td width="*">
													<input type="text" id="certificateNO"
														class="validate[required,maxSize[20],custom[onlyLetterNumber]] input_text"
														name="entity.certificateNO" value="${entity.certificateNO}" data-prompt-position="topLeft:0"/>
												</td>
											</tr> 
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												��ϵ��Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="center" class="table2_style">
												
												<tr>
												    <td align="right" width="20%">
														<span class="required">*</span>
														��ϵ�ˣ�
													</td>
													<td width="30%">
														<input type="text" id="contactMan"
															class="validate[required,maxSize[30]] input_text"
															name="entity.contactMan" value="${entity.contactMan}" />
													</td>
													<td align="right" width="20%">
														�绰��
													</td>
													<td width="*">
														<input type="text" id="phone"
															class="validate[maxSize[20]] input_text"
															name="entity.phone" value="${entity.phone}"
															data-prompt-position="bottomLeft:0"/>
													</td>
												</tr>
												<tr>
												    <td align="right">
														<span class="required">*</span>
														�ֻ����룺
													</td>
													<td >
														<input type="text" id="mobile"
															class="validate[required,maxSize[12],custom[phone]] input_text"
															name="entity.mobile" value="${entity.mobile}" data-prompt-position="topLeft:0 "/>
													</td>
												
													<td align="right">
														���棺
													</td>
													<td>
														<input type="text" id="fax"
															class="validate[maxSize[16],custom[fax]] input_text"
															name="entity.fax" value="${entity.fax}" />
													</td>
												</tr>
												<tr>
												    <td align="right">
													<span class="required">*</span>
														�������䣺
													</td>
													<td>
														<input type="text" id="email"
															class="validate[required,maxSize[30] input_text"
															name="entity.email" value="${entity.email}" />
													</td>
													<td align="right">
														�ʱࣺ
													</td>
													<td>
														<input type="text" id="postCode"
															class="validate[maxSize[6] input_text" name="entity.postCode"
															value="${entity.postCode}" />
													</td>
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														��ϵ��ַ��
													</td>
													<td colspan="3" align="left">
														<input type="text" id="address" style="width: 300px;"
															class="validate[required,maxSize[50] input_text" 
															name="entity.address" value="${entity.address}" />
													</td>
												</tr>
												<tr>
													<td align="right">
														��ע��
													</td>
													<td colspan="3">
														<textarea rows="3" cols="64" name="entity.note" id="note"
															class="validate[maxSize[500] ">${entity.note }</textarea>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="center" class="table2_style">
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														�û����� ��
													</td>
													<td width="30%">
														<input type="password" id="password" class="validate[required,minSize[6]] input_text" name="entity.password" data-prompt-position="topRight:25,30"/>
													</td>
													<td colspan="2" width="*">
														<div class="onfocus">
															����Ϊ�գ�
														</div>
													</td>
												</tr>
												<tr>
													<td align="right"  >
														<span class="required">*</span>
														ȷ������ ��
													</td>
													<td  >
														<input type="password" id="password1" class="validate[required,equals[password]] input_text" name="password1" data-prompt-position="topRight:25,25"/>
													</td>
													<td colspan="2">
														<div class="onfocus">
															����Ϊ�գ�
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div >
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="center">
												<tr>
													<td align="right">
														<rightButton:rightButton name="����" onclick="" className="btn_sec"
															action="/broker/firmManager/addFirm.action" id="add"></rightButton:rightButton>
												
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
			
			<script type="text/javascript">
	
</script>
		</form>

	</body>
</html>
<%@ include file="/broker/public/jsp/footinc.jsp"%>