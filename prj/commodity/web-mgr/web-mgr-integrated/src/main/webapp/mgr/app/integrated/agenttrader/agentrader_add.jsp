<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>添加代为交易员</title>
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
					//ajax验证代为交易员代码是否存在
					jQuery("#frm").validationEngine({
						ajaxFormValidation : true,
						ajaxFormValidationURL : "../../ajaxcheck/checkAddAgentTraderForm.action?t="+Math.random(),
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
								//添加信息URL
								var addDemoUrl = $("#add").attr("action");
								//全 URL 路径
								var url = "${basePath}"+addDemoUrl;
								$("#frm").attr("action",url);
								frm.submit();
							}
						}
					}

					//添加按钮注册点击事件
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
								<div class="content">温馨提示 :代为交易员信息添加</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">添加代为交易员</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										    <tr>
										        <td align="right" class="td_size">
												    <span class="required">*</span>代为交易员代码：
												</td>
												<td align="left" width=" " class="td_size">
													<input name="entity.agentTraderId" id="agentTraderId" type="text" 
													    class="validate[required,custom[onlyLetterNumber],maxSize[15],maxSize[<fmt:message key='AgentTrader.agentTraderId' bundle='${PropsFieldLength}' />],ajax[checkAgentTraderId]] input_text datepicker"/>
												</td>
												<td align="right" class="td_size">
												    <span class="required">*</span>代为交易员名称：
												</td>
												<td align="left" width=" " class="td_size">
													<input  name="entity.name" id="name" type="text" 
													    class="validate[required,maxSize[8],maxSize[<fmt:message key='AgentTrader.name' bundle='${PropsFieldLength}' />]] input_text" data-prompt-position="topLeft:0,8.5"/>
												</td>
											</tr>
											<tr>
												<td align="right" class="td_size">
												    <span class="required">*</span>密码： 
												</td>
												<td align="left" width=" " class="td_size">
													<input type="password" name="entity.password" id="password2"
							                        class="validate[required,maxSize[<fmt:message key='agentTraderPassword' bundle='${PropsFieldLength}' />],custom[password]] input_text" >
												</td>
												<td align="right" class="td_size">
												    <span class="required">*</span>确认密码： 
												</td>
												<td align="left" width=" " class="td_size">
													<input type="password" name="password3" id="password3"
							                        class="validate[required,equals[password2]] input_text" data-prompt-position="topLeft:0,8.5">
												</td>
											</tr>
											<tr>
												<td align="right" class="td_size">
													<span class="required">*</span>代为交易员类型： 
												</td>
												<td align="left" class="td_size">
					                				<select id="type" name="entity.type" class="validate[required] input_text" style="width: 120px" >
					                				    <option value="">请选择</option>
														<c:forEach var="type" items="${agentTraderTypeMap}">
						                					<option value="${type.key}">${type.value}</option>
						                				</c:forEach>
													</select>
					                			</td>
												<td align="right" class="td_size">
													<span class="required">*</span>代为交易员状态： 
												</td>
												<td align="left" class="td_size">
					                				<select id="status" name="entity.status" class="validate[required] input_text" style="width: 120px" data-prompt-position="topLeft:0,8.5">
														<option value="">请选择</option>
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
														<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/trade/agenttrader/addAgentTrader.action" id="add"></rightButton:rightButton>
														&nbsp;&nbsp;
														<button class="btn_sec" onClick="window.close();">关闭</button>
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