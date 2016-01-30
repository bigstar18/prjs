<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
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
			type="text/javascript" charset="gbk">
	
</script>
		<script
			src="${publicPath }/js/validationengine/jquery.validationEngine.js"
			type="text/javascript" charset="gbk">
	
</script>
		<script>
	function checkRecieverId() {
		var type = document.getElementById("recieverType").value;

		if (type == "4" || type == "5") {
			document.getElementById("content").style.display = "block";
		} else {
			document.getElementById("content").style.display = "none";
		}

	}
	jQuery(document).ready(function() {
		jQuery("#frm").validationEngine( {
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/checkMessageForm.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		function beforeCall(form, options) {
			return true;
		}

		// Called once the server replies to the ajax form validation request
			function ajaxValidationCallback(status, form, json, options) {
				if (status === true) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						form.validationEngine('detach');
						//$('#frm').attr('action', 'action');
						$('#frm').submit();
					}
				}
			}

			$("#add").click(function(check) {
				$("#frm").validationEngine('validateform');
			});
		});
</script>
		<title>消息添加</title>
	</head>

	<body style="overflow-y: hidden">
		<form id="frm" name="frm" method="post"
			action="${basePath}/trade/message/addMessage.action"
			targetType="hidden">
			<div class="div_cx">
				<table border="0" width="80%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :添加消息
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr height="30"></tr>
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
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<input type="hidden" id="userId" name="entity.userId"
													value="${CurrentUser.userId }" />
												<tr height="20">
													<td align="right" width="25%">
														管理员：
													</td>
													<td width="*">
														${CurrentUser.userId }
													</td>
												</tr>
												<tr>
													<tr height="20">
														<td align="right" width="25%">
															接收人类型：
														</td>
														<td width="*">
															<select id="recieverType" name="entity.recieverType"
																class="validate[required,maxSize[10],funcCall[checkRecieverId]] input_text">
																<option value="">
																	请选择
																</option>
																<c:forEach items="${recieverMap}" var="map">
																	<option value="${map.key }">
																		${map.value }
																	</option>
																</c:forEach>
															</select>
														</td>
													</tr>
													<tr>
														<td colspan="2">
															<div id="content" style="display: none;">
																<table border="0" cellspacing="0" cellpadding="4"
																	width="100%" align="center">
																	<tr height="20">
																		<td align="right" width="25%">
																			接收人：
																		</td>
																		<td width="*">
																			<input id="receiveId" name="entity.traderId"
																				class="validate[required,maxSize[17],ajax[checkMessageByUserId]] input_text">
																		</td>
																</table>
														</td>
													</tr>

													</div>

													</td>
												</tr>

												<tr>
													<td align="right">
														消息内容：
													</td>
													<td>
														<textarea rows="10" cols="30" id="message" class="validate[required,maxSize[<fmt:message key='message' bundle='${PropsFieldLength}'/>]]"
															name="entity.message" ></textarea>
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
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
						</td>
						<td align="right">
							<rightButton:rightButton name="添加" onclick="" className="btn_sec"
								action="/trade/message/addMessage.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick=window.close();>
								关闭
							</button>
						</td>
						<td width="10%"></td>
					</tr>
				</table>
			</div>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>