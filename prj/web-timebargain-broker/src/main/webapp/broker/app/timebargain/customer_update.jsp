<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>修改客户信息</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});
		</script>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.customerId" value="${entity.customerId}"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改客户${entity.customerId}信息
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
												客户修改
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<fmt:message key="Customer.customerId" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														${entity.customerId}&nbsp;
													</td>
													<td align="right">
														<fmt:message key="MFirm.name" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														${entity.firm.name}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.status" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<c:forEach items="${customer_statusMap}" var="map">
															<input type="radio" name="entity.status" value="${map.key}" <c:if test="${entity.status==map.key}">checked="checked"</c:if> />${map.value}&nbsp;
														</c:forEach>
													</td>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.type" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<c:forEach items="${customer_typeMap}" var="map" varStatus="status">
															<input type="radio" name="entity.type" value="${map.key}" <c:if test="${entity.type==map.key}">checked="checked"</c:if> />${map.value}&nbsp;
														</c:forEach>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.name" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														<input type="text" id="name" name="entity.name" value="${entity.name}"
															class="validate[required,maxSize[<fmt:message key='Customer.name' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													<td align="right">
														<fmt:message key="Customer.fullName" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<input type="text" id="fullName" name="entity.fullName" value="${entity.fullName}"
															class="validate[maxSize[<fmt:message key='Customer.fullName' bundle='${PropsFieldLength}' />]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.cardType" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<c:forEach items="${customer_cardTypeMap}" var="map">
															<input type="radio" name="entity.cardType" value="${map.key}" <c:if test="${entity.cardType==map.key}">checked="checked"</c:if> />${map.value}&nbsp;
														</c:forEach>
													</td>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.card" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														<input type="text" id="card" name="entity.card" value="${entity.card}"
															class="validate[required,maxSize[<fmt:message key='Customer.card' bundle='${PropsFieldLength}' />]] input_text "/>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.bankCode" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														<select id="bankCode" name="entity.bankCode" class="validate[required]">
														<option value="">请选择</option>
														<c:forEach items="${customer_bankCodeMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("bankCode").value="${entity.bankCode}";
														</script>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													<td align="right">
														<fmt:message key="Customer.bankAccount" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<input type="text" id="bankAccount" name="entity.bankAccount" value="${entity.bankAccount}"
															class="validate[maxSize[<fmt:message key='Customer.bankAccount' bundle='${PropsFieldLength}' />],custom[bankAccount]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.contactMan" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														<input type="text" id="contactMan" name="entity.contactMan" value="${entity.contactMan}"
															class="validate[required,maxSize[<fmt:message key='Customer.contactMan' bundle='${PropsFieldLength}' />]] input_text "/>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													<td align="right">
														<fmt:message key="Customer.phone" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<input type="text" id="phone" name="entity.phone" value="${entity.phone}"
															class="validate[maxSize[<fmt:message key='Customer.phone' bundle='${PropsFieldLength}' />],custom[phone]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.email" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<input type="text" id="email" name="entity.email" value="${entity.email}"
															class="validate[maxSize[<fmt:message key='Customer.email' bundle='${PropsFieldLength}' />],custom[email]] input_text "/>
													</td>
													<td align="right">
														<fmt:message key="Customer.postcode" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														<input type="text" id="postcode" name="entity.postcode" value="${entity.postcode}"
															class="validate[maxSize[<fmt:message key='Customer.postcode' bundle='${PropsFieldLength}' />],custom[integer]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.address" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="4">
														<input type="text" id="address" name="entity.address" value="${entity.address}" size="70"
															class="validate[maxSize[<fmt:message key='Customer.address' bundle='${PropsFieldLength}' />]] "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.note" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="4">
														<textarea id="note" rows="5" cols="70" name="entity.note" class="validate[maxSize[<fmt:message key='Customer.note' bundle='${PropsFieldLength}' />]]">${entity.note}</textarea>
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
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/dem/customerplay/updatecustomer.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/broker/public/jsp/footinc.jsp"%>