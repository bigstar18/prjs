<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�ͻ���Ϣ���</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				//��ȡ�����̴��������б�
				var firmList = getfirmList();
				$("#firm_firmId").focus().autocomplete(firmList);

				//ajax��֤�����̴����Ƿ����
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../../ajaxcheck/demo/formCheckFirmByFirmId.action",
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
							//�����ϢURL
							var addDemoUrl = $("#add").attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+addDemoUrl;
							$("#frm").attr("action",url);
							frm.submit();
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
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�ͻ���Ϣ���
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
												��ӿͻ�
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="MFirm.firmId" bundle="${PropsFieldDic}" />��
													</td>
													<td>
														<input type="text" id="firm_firmId" name="entity.firm.firmId" 
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId' bundle='${PropsFieldLength}' />],ajax[mouseCheckFirmByFirmId]] input_text datepicker"/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.type" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<c:forEach items="${customer_typeMap}" var="map" varStatus="status">
															<input type="radio" name="entity.type" value="${map.key}" <c:if test="${status.index==0}">checked="checked"</c:if> />${map.value}&nbsp;
														</c:forEach>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.name" bundle="${PropsFieldDic}" />��
													</td>
													<td>
														<input type="text" id="name" name="entity.name" 
															class="validate[required,maxSize[<fmt:message key='Customer.name' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
													<td align="right">
														<fmt:message key="Customer.fullName" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<input type="text" id="fullName" name="entity.fullName"
															class="validate[maxSize[<fmt:message key='Customer.fullName' bundle='${PropsFieldLength}' />]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.cardType" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<c:forEach items="${customer_cardTypeMap}" var="map" varStatus="status">
															<input type="radio" name="entity.cardType" value="${map.key}" <c:if test="${status.index==0}">checked="checked"</c:if> />${map.value}&nbsp;
														</c:forEach>
													</td>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.card" bundle="${PropsFieldDic}" />��
													</td>
													<td>
														<input type="text" id="card" name="entity.card"
															class="validate[required,maxSize[<fmt:message key='Customer.card' bundle='${PropsFieldLength}' />]] input_text "/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.bankCode" bundle="${PropsFieldDic}" />��
													</td>
													<td>
														<select id="bankCode" name="entity.bankCode" class="validate[required]">
														<option value="" selected="selected">��ѡ��</option>
														<c:forEach items="${customer_bankCodeMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
													<td align="right">
														<fmt:message key="Customer.bankAccount" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<input type="text" id="bankAccount" name="entity.bankAccount"
															class="validate[maxSize[<fmt:message key='Customer.bankAccount' bundle='${PropsFieldLength}' />],custom[bankAccount]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														<fmt:message key="Customer.contactMan" bundle="${PropsFieldDic}" />��
													</td>
													<td>
														<input type="text" id="contactMan" name="entity.contactMan"
															class="validate[required,maxSize[<fmt:message key='Customer.contactMan' bundle='${PropsFieldLength}' />]] input_text "/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
													<td align="right">
														<fmt:message key="Customer.phone" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<input type="text" id="phone" name="entity.phone"
															class="validate[maxSize[<fmt:message key='Customer.phone' bundle='${PropsFieldLength}' />],custom[phone]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.email" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<input type="text" id="email" name="entity.email"
															class="validate[maxSize[<fmt:message key='Customer.email' bundle='${PropsFieldLength}' />],custom[email]] input_text "/>
													</td>
													<td align="right">
														<fmt:message key="Customer.postcode" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="2">
														<input type="text" id="postcode" name="entity.postcode"
															class="validate[maxSize[<fmt:message key='Customer.postcode' bundle='${PropsFieldLength}' />],custom[integer]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.address" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="4">
														<input type="text" id="address" name="entity.address" size="70"
															class="validate[maxSize[<fmt:message key='Customer.address' bundle='${PropsFieldLength}' />]] "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.note" bundle="${PropsFieldDic}" />��
													</td>
													<td colspan="4">
														<textarea id="note" rows="5" cols="70" name="entity.note" class="validate[maxSize[<fmt:message key='Customer.note' bundle='${PropsFieldLength}' />]]"></textarea>
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
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/dem/customerplay/addcustomer.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>