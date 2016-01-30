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
					var vaild = affirm("您确定要操作吗？");
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
				//当交易商类型为个人时，防止之前输入的域编号、行业编号、组织结构代码和法人代表的值录入
				$("#zoneCode").attr("value","");
				$("#industryCode").attr("value","");
				$("#organizationCode").attr("value","");
				$("#corporateRepresentative").attr("value","");
			}
		}
		function checkUserId(){
			var userId =$("#username").val();
			if(!isStr(userId,true)){
				return "*包含非法字符";
			}
		}
</script>
	<head>
		<title>交易商开户申请</title>
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
									温馨提示 :添加开户申请
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
												基本信息
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
														用户名：
													</td>
													<td  width="30%">
														<input type="text" id="userId" class="validate[required,funcCall[checkUserId],maxSize[16] input_text" name="entity.userId"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														交易商名称：
													</td>
													<td>
														<input type="text" id="name"
															class="validate[required,maxSize[15]] input_text"
															name="entity.name" value="${entity.name}" />
													</td>
													<td align="right">
														交易商全称：
													</td>
													<td>
														<input type="text" id="fullName"
															class="validate[maxSize[30]] input_text" name="entity.fullName"
															value="${entity.fullName}" />
													</td>
												</tr>
												<tr>
													<td align="right">
														所属居间商：
													</td>
													<td>
														<select id="type" class="input_text" name="entity.brFirmApply.brokerAgeId"  data-prompt-position="topLeft:0">
															<option value="">请选择</option>
															<c:forEach items="${list}" var="brokerAge">
																<option value="${brokerAge['BROKERAGEID']}">${brokerAge['NAME']}</option>
															</c:forEach>
														</select>
													</td>
													<td align="right">
														<span class="required">*</span>
														交易商类型：
													</td>
													<td align="left">
														<select id="type" class="validate[required] input_text" name="entity.type" onchange="getHidden(this.value)" onclick="reduceValue(this.value)" data-prompt-position="topLeft:0">
															<option value="">请选择</option>
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
																		所属行业编码 ：&nbsp;
																	</td>
																	<td width="30%">
																		<input type="text" id="industryCode" class="validate[custom[onlyLetterNumber],maxSize[16]] input_text" name="entity.industryCode" 
																			value="${entity.industryCode}" data-prompt-position="topLeft:0">
																	</td>
																	<td align="right" width="21%">
																		所属区域编码 ：
																	</td>
																	<td width="*">
																		&nbsp;<input type="text" id="zoneCode" class="validate[custom[onlyLetterNumber],maxSize[16]] input_text" name="entity.zoneCode" 
																			value="${entity.zoneCode}" data-prompt-position="topLeft:0">
																	</td>
																</tr> 
																<tr>
																	<td align="right" width="20%">
																		<span class="required">*</span>组织结构代码 ：&nbsp;
																	</td>
																	<td width="30%">
																		<input type="text" id="organizationCode" class="validate[required,custom[onlyLetterNumber],maxSize[9]] input_text" name="entity.organizationCode" 
																			value="${entity.organizationCode}" data-prompt-position="topLeft:0">
																	</td>
																	<td align="right" width="21%">
																		法人代表 ：
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
													证件类型：
												</td>
												<td width="30%">
													<select id="certificateType" name="entity.certificateType" class="validate[required] input_text">
														<option value="">请选择</option>
														<c:forEach items="${certificateTypeMap}" var="map">
															<option value="${map.key}" title='${map.value}'>${map.value}</option>
														</c:forEach>
													</select>
												</td>
							
												<td align="right" width="21%">
													<span class="required">*</span>
													证件编号：
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
												联系信息
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
														联系人：
													</td>
													<td width="30%">
														<input type="text" id="contactMan"
															class="validate[required,maxSize[30]] input_text"
															name="entity.contactMan" value="${entity.contactMan}" />
													</td>
													<td align="right" width="20%">
														电话：
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
														手机号码：
													</td>
													<td >
														<input type="text" id="mobile"
															class="validate[required,maxSize[12],custom[phone]] input_text"
															name="entity.mobile" value="${entity.mobile}" data-prompt-position="topLeft:0 "/>
													</td>
												
													<td align="right">
														传真：
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
														电子邮箱：
													</td>
													<td>
														<input type="text" id="email"
															class="validate[required,maxSize[30] input_text"
															name="entity.email" value="${entity.email}" />
													</td>
													<td align="right">
														邮编：
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
														联系地址：
													</td>
													<td colspan="3" align="left">
														<input type="text" id="address" style="width: 300px;"
															class="validate[required,maxSize[50] input_text" 
															name="entity.address" value="${entity.address}" />
													</td>
												</tr>
												<tr>
													<td align="right">
														备注：
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
												密码信息
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
														用户密码 ：
													</td>
													<td width="30%">
														<input type="password" id="password" class="validate[required,minSize[6]] input_text" name="entity.password" data-prompt-position="topRight:25,30"/>
													</td>
													<td colspan="2" width="*">
														<div class="onfocus">
															不能为空！
														</div>
													</td>
												</tr>
												<tr>
													<td align="right"  >
														<span class="required">*</span>
														确认密码 ：
													</td>
													<td  >
														<input type="password" id="password1" class="validate[required,equals[password]] input_text" name="password1" data-prompt-position="topRight:25,25"/>
													</td>
													<td colspan="2">
														<div class="onfocus">
															不能为空！
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
														<rightButton:rightButton name="申请" onclick="" className="btn_sec"
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