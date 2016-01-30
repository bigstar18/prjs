<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	request.setAttribute("readonly", "readonly");
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
		$("#firmId").attr("readOnly","readOnly");
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}
		$("#frm").validationEngine('attach',{relative:true});
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$("#name").val($("#name").val().trim());
					$('#frm').submit();
				}
			}
		});
		});
</script>
	<head>
		<title>交易商修改</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden" onload="getHidden()">
		<iframe id="hiddenframe" name="hiddenframe" width=0 height=0
			style="display: none" src="" application="yes"></iframe>
		<form id="frm" method="post"
			action="${basePath}/trade/mfirm/updateMfirm.action"
			target="hiddenframe">
			<div class="div_cx" style="overflow: auto; height: 480px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :交易商修改
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
											<input type="hidden" id="oldFirmId" name="oldFirmId"
												value="${entity.firmId }" />
											<input type="hidden" id="oldEmail" name="oldEmail"
												value="${entity.email }" />
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.firmId" bundle="${PropsFieldDic}" />
														：
													</td>
													<td title="${entity.firmId}">
														<input type="text" id="id" title="${entity.firmId}" readonly="readonly"
															 class="validate[required,custom[onlyLetterNumber]] input_text"
															name="entity.firmId" value="${entity.firmId}" />
													</td>
													<script>
                                if(${readonly!=null}){
								    frm.id.style.backgroundColor="#d0d0d0";
								}
						</script>
													<td colspan="2">
														<div class="onfocus">
															不能为空！
														</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.name" bundle="${PropsFieldDic}" />
														：
													</td>
													<td>
														<input type="text" id="name"
															class="validate[required,maxSize[<fmt:message key='firmName' bundle='${PropsFieldLength}' />]] input_text"
															name="entity.name" value="${entity.name}" />
													</td>
													<td colspan="2">
														<div class="onfocus">
															不能为空！
														</div>
													</td>
												</tr>
												<tr>
													<td align="right" width="21%">
														<fmt:message key="MFirm.fullName"
															bundle="${PropsFieldDic}" />
														：
													</td>
													<td width="30%">
														<input type="text" id="fullName"
															class="validate[maxSize[<fmt:message key='fullName' bundle='${PropsFieldLength}' />]] input_text"
															name="entity.fullName" value="${entity.fullName}" />
													</td>
													<td align="right" width="20%">
														<fmt:message key="MFirm.type" bundle="${PropsFieldDic}" />
														：
													</td>
													<td width="*">
														<select id="type" class="input_text" name="entity.type"
															onchange="getHidden()" onclick="reduceValue(this.value)">
															<c:forEach items="${mfirmTypeMap}" var="map">
																<option value="${map.key}" title='${map.value}'
																	<c:if test="${entity.type==map.key }">selected="selected"</c:if>>
																	${map.value}
																</option>
															</c:forEach>
														</select>
													</td>
													<script>
							function getHidden(){
								var type=document.getElementById("type").value;
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
						</script>
												</tr>
												<tr>
													<td colspan="4">
														<div id="content" style="display: block;">
															<table border="0" cellspacing="0" cellpadding="0"
																width="100%">
																<tr>
																	<td align="right" width="21%">
																		所属行业 ：&nbsp;
																	</td>
																	<td width="30%">
																		<select id="industryCode" class="input_text"
																			name="entity.industryCode">
																			<option value="">
																				请选择
																			</option>
																			<c:forEach items="${industryList}" var="industry">
																				<c:if test="${industry.isvisibal eq 'Y'}">
																					<option value="${industry.code}"
																						<c:if test="${entity.industryCode eq industry.code}">selected="selected"</c:if>
																						title="${industry.name}">
																						${industry.name}
																					</option>
																				</c:if>
																			</c:forEach>
																		</select>
																	</td>
																	<td align="right" width="21%">
																		所属地域 ：&nbsp;
																	</td>
																	<td width="*">
																		<select id="zoneCode" class="input_text"
																			name="entity.zoneCode">
																			<option value="">
																				请选择
																			</option>
																			<c:forEach items="${zoneList}" var="zone">
																				<c:if test="${zone.isvisibal eq 'Y'}">
																					<option value="${zone.code}" title="${zone.name}"
																						<c:if test="${entity.zoneCode eq zone.code}">selected="selected"</c:if>>
																						${zone.name}
																					</option>
																				</c:if>
																			</c:forEach>
																		</select>
																	</td>
																</tr>
																<tr height="2">
																	<td>
																		&nbsp;
																	</td>
																</tr>
																<tr>
																	<td align="right" width="21%">
																		<span class="required">*</span>
																		<fmt:message key="MFirm.organizationCode"
																			bundle="${PropsFieldDic}" />
																		：&nbsp;
																	</td>
																	<td width="30%">
																		<input type="text" id="organizationCode"
																			class="validate[required,custom[onlyLetterNumber],maxSize[9]] input_text"
																			name="entity.organizationCode"
																			value="${entity.organizationCode}">
																		&nbsp;
																	</td>
																	<td align="right" width="21%">
																		<fmt:message key="MFirm.corporateRepresentative"
																			bundle="${PropsFieldDic}" />
																		：&nbsp;
																	</td>
																	<td width="*">
																		<input type="text" id="corporateRepresentative"
																			class="validate[maxSize[16]] input_text"
																			name="entity.corporateRepresentative"
																			value="${entity.corporateRepresentative}"
																			data-prompt-position="topLeft:0">
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.certificateType"
															bundle="${PropsFieldDic}" />
														：
													</td>
													<td>
														<select id="certificateType" name="entity.certificateType"
															class="input_text">
															<c:forEach items="${certificateTypeList}"
																var="certificateType">
																<c:if test="${certificateType.isvisibal eq 'Y'}">
																	<option value="${certificateType.code}"
																		title='${certificateType.name}'
																		<c:if test="${entity.certificateType==certificateType.code }">selected="selected"</c:if>>
																		${certificateType.name}
																	</option>
																</c:if>
															</c:forEach>
														</select>
													</td>

													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.certificateNO"
															bundle="${PropsFieldDic}" />
														：
													</td>
													<td>
														<input type="text" id="certificateNO"
															class="validate[required,maxSize[<fmt:message key='certificateNO' bundle='${PropsFieldLength}'/>],custom[onlyLetterNumber]] input_text"
															name="entity.certificateNO"
															value="${entity.certificateNO}"
															data-prompt-position="topLeft:30,0" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span> 交易商类别：
													</td>
													<td>
														<select id="firmCategoryId" name="entity.firmCategoryId"
															class="validate[required] input_text">
															<option value="${entity.firmCategoryId}"
																selected="selected">
																<c:if test="${entity.firmCategoryId eq -1}">未分类</c:if>
																<c:forEach var="firmCategory"
																	items="${firmCategoryList}">
																	<c:if test="${firmCategory.isvisibal eq 'Y'}">
																		<c:if
																			test="${firmCategory.id eq entity.firmCategoryId}">${firmCategory.name}</c:if>
																	</c:if>
																</c:forEach>
															</option>
															<c:if test="${entity.firmCategoryId != -1}">
																<option value="-1">
																	未分类
																</option>
															</c:if>
															<c:forEach var="firmCategory" items="${firmCategoryList}">
																<c:if test="${firmCategory.id  !=entity.firmCategoryId}">
																	<c:if test="${firmCategory.isvisibal eq 'Y'}">
																		<option value="${firmCategory.id}"
																			title="${firmCategory.name}">
																			${firmCategory.name}
																		</option>
																	</c:if>
																</c:if>
															</c:forEach>
														</select>
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
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.contactMan"
															bundle="${PropsFieldDic}" />
														：
													</td>
													<td>
														<input type="text" id="contactMan"
															class="validate[required,maxSize[<fmt:message key='contactMan' bundle='${PropsFieldLength}' />]] input_text"
															name="entity.contactMan" value="${entity.contactMan}" />
													</td>
													<td align="right">
														<fmt:message key="MFirm.phone" bundle="${PropsFieldDic}" />
														：
													</td>
													<td>
														<input type="text" id="phone"
															class="validate[maxSize[<fmt:message key='phone' bundle='${PropsFieldLength}' />]] input_text"
															name="entity.phone" value="${entity.phone}"
															data-prompt-position="bottomLeft:0" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.mobile" bundle="${PropsFieldDic}" />
														：
													</td>
													<td width="*">
														<input type="text" id="mobile"
															class="validate[required,maxSize[<fmt:message key='mobile' bundle='${PropsFieldLength}' />],custom[mobile]] input_text"
															name="entity.mobile" value="${entity.mobile}"
															data-prompt-position="bottomLeft:0" />
													</td>
													<td align="right">
														<fmt:message key="MFirm.fax" bundle="${PropsFieldDic}" />
														：
													</td>
													<td>
														<input type="text" id="fax"
															class="validate[maxSize[<fmt:message key='fax' bundle='${PropsFieldLength}' />],custom[fax]] input_text"
															name="entity.fax" value="${entity.fax}" />
													</td>
												</tr>
												<tr>
													<td align="right" width="21%">
														<span class="required">*</span>
														<fmt:message key="MFirm.email" bundle="${PropsFieldDic}" />
														：
													</td>
													<td width="30%">
														<input type="text" id="email"
															class="validate[required,maxSize[<fmt:message key='email' bundle='${PropsFieldLength}' />],custom[email]] input_text"
															name="entity.email" value="${entity.email}" />
													</td>
													<td align="right" width="20%">
														<fmt:message key="MFirm.postCode"
															bundle="${PropsFieldDic}" />
														：
													</td>
													<td width="*">
														<input type="text" id="postCode"
															class="validate[maxSize[<fmt:message key='postcode' bundle='${PropsFieldLength}' />],custom[onlyNumberSp]] input_text"
															name="entity.postCode" value="${entity.postCode}" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.address" bundle="${PropsFieldDic}" />
														：
													</td>
													<td colspan="3" align="left">
														<input type="text" id="address" style="width: 310px;"
															class="validate[required,maxSize[<fmt:message key='address' bundle='${PropsFieldLength}' />]] input_text"
															name="entity.address" value="${entity.address}"
															data-prompt-position="topLeft:250,8" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="MFirm.note" bundle="${PropsFieldDic}" />
														：
													</td>
													<td colspan="3">
														<textarea rows="3" cols="48" name="entity.note" id="note"
															class="validate[maxSize[<fmt:message key='note' bundle='${PropsFieldLength}' />]] ">${entity.note }</textarea>
													</td>
												</tr>
												<tr>
													<td align="right">
														权限：
													</td>
													<td colspan="3" id="moduleIdtd">
														<c:forEach var="map" items="${tradeModuleMap}">
															<c:if test="${map.value.isFirmSet eq 'Y'}">
																<input type="checkbox" id="ch${map.key}"
																	name="firmModules" value="${map.key}"
																	<c:if test="${firmModuleMap[map.key]=='Y'}">checked="checked"</c:if> />
																<label onclick="ch${map.key}.click();">
																	${map.value.shortName}
																</label>
															</c:if>
														</c:forEach>

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
			<div class="div_cx">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="修改" onclick="" className="btn_sec"
								action="/trade/mfirm/updateMfirm.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick=window.close();>
								关闭
							</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>