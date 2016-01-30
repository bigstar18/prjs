<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��Ӵ�Ϊ����Ա</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
				jQuery(document).ready(function() {
					//ajax��֤��Ϊ����Ա�����Ƿ����
					jQuery("#frm").validationEngine({
						ajaxFormValidation : true,
						ajaxFormValidationURL : "../../ajaxcheck/checkAddAgentTraderForm.action?t="+Math.random(),
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
					$("#add").click(function(check){
						if ($("#frm").validationEngine('validateform')) {
						}
					});
				});
		</script>
	</head>
	<body style="overflow-y: hidden;overflow-x: hidden">
		<form id="frm" name="frm" method="post"  action="${basePath}/trade/agenttrader/addAgentTrader.action" targetType="hidden">
			<div class="st_title" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">��ܰ��ʾ :��Ϊ����Ա��Ϣ���</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">��Ӵ�Ϊ����Ա</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										    <tr>
										        <td align="right" class="td_size">
												    <span class="required">*</span>��Ϊ����Ա���룺
												</td>
												<td align="left" width=" " class="td_size">
													<input name="entity.agentTraderId" id="agentTraderId" type="text" 
													    class="validate[required,custom[onlyLetterNumber],maxSize[15],maxSize[<fmt:message key='AgentTrader.agentTraderId' bundle='${PropsFieldLength}' />],ajax[checkAgentTraderId]] input_text datepicker"/>
												</td>
												<td align="right" class="td_size">
												    <span class="required">*</span>��Ϊ����Ա���ƣ�
												</td>
												<td align="left" width=" " class="td_size">
													<input  name="entity.name" id="name" type="text" 
													    class="validate[required,maxSize[8],maxSize[<fmt:message key='AgentTrader.name' bundle='${PropsFieldLength}' />]] input_text" data-prompt-position="topLeft:0,8.5"/>
												</td>
											</tr>
											<tr>
												<td align="right" class="td_size">
												    <span class="required">*</span>���룺 
												</td>
												<td align="left" width=" " class="td_size">
													<input type="password" name="entity.password" id="password2"
							                        class="validate[required,maxSize[<fmt:message key='agentTraderPassword' bundle='${PropsFieldLength}' />],custom[password]] input_text" >
												</td>
												<td align="right" class="td_size">
												    <span class="required">*</span>ȷ�����룺 
												</td>
												<td align="left" width=" " class="td_size">
													<input type="password" name="password3" id="password3"
							                        class="validate[required,equals[password2]] input_text" data-prompt-position="topLeft:0,8.5">
												</td>
											</tr>
											<tr>
												<td align="right" class="td_size">
													<span class="required">*</span>��Ϊ����Ա���ͣ� 
												</td>
												<td align="left" class="td_size">
					                				<select id="type" name="entity.type" class="validate[required] input_text" style="width: 120px" >
					                				    <option value="">��ѡ��</option>
														<c:forEach var="type" items="${agentTraderTypeMap}">
						                					<option value="${type.key}">${type.value}</option>
						                				</c:forEach>
													</select>
					                			</td>
												<td align="right" class="td_size">
													<span class="required">*</span>��Ϊ����Ա״̬�� 
												</td>
												<td align="left" class="td_size">
					                				<select id="status" name="entity.status" class="validate[required] input_text" style="width: 120px" data-prompt-position="topLeft:0,8.5">
														<option value="">��ѡ��</option>
														<c:forEach var="status" items="${agentTraderStatusMap}">
						                					<option value="${status.key}">${status.value}</option>
						                				</c:forEach>
													</select>
					                			</td>
											</tr>
										</table>
										<div class="tab_pad">
											<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
												<tr>
													<td align="right">
														<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/trade/agenttrader/addAgentTrader.action" id="add"></rightButton:rightButton>
														&nbsp;&nbsp;
														<button class="btn_sec" onClick="window.close();">�ر�</button>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</td>
							</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>