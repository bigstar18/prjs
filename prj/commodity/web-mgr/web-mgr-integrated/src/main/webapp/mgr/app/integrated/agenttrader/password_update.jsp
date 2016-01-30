<%@ page contentType="text/html;charset=GBK"%>
<jsp:directive.page import="gnnt.MEBS.common.mgr.model.User" />
<jsp:directive.page import="gnnt.MEBS.integrated.mgr.model.usermanage.Trader" />
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script 
        src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
</script>
<script
		src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
</script>
<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK">
</script>
<script>
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		$("#frm").validationEngine('attach');
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						$("#frm").validationEngine('detach');
						//$('#frm').attr('action', 'action');
						
						$('#frm').submit();
					}
				}
			});
		});
</script>
	<head>
		<title>代为交易员密码修改</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body  onload="setFocus()">
		<form name="frm" id="frm" method="post" action="${basePath }/trade/agenttrader/updatePassword.action" targetType="hidden">
			<input type="hidden" name="entity.agentTraderId" value="${entity.agentTraderId}"/>
			    <div class="st_title">
					<table border="0" width="90%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">温馨提示 :代为交易员密码修改</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">修改密码</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
														<tr height="35">
															<td align="right" class="td_size">
																<span class="required">*</span>
																	新密码 ：
															</td>
															<td align="left" class="td_size">
																<input type="password"name="password" id="password" style="width: 170"
																 class="validate[required,maxSize[<fmt:message key='agentTraderPassword' bundle='${PropsFieldLength}' />],custom[password]] input_text" >
															</td>
															<td align="left">
																<div class="onfocus">
																	不能为空！
																</div>
															</td>
														</tr>
														<tr height="35">
															<td align="right" class="td_size">
															<span class="required">*</span>
																          确认新密码 ：
															</td>
															<td align="left" class="td_size">
																<input type="password" name="password1" id="password1" style="width: 170"
																 class="validate[required,equals[password]] input_text" >
															</td>
															<td>&nbsp;</td>
														</tr>
											</table>
											<div class="tab_pad">
												<table border="0" cellspacing="0" cellpadding="0" width="100%"
													align="center">
													<tr height="35">
														<td align="right" id="tdId">
															<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/trade/agenttrader/updatePassword.action" id="update"></rightButton:rightButton>
														    &nbsp;&nbsp;
															<button class="btn_sec" onClick="window.close()">
																关闭
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
				    </div>
			    </form>
	</body>
<SCRIPT LANGUAGE="JavaScript">
function setFocus(){
	document.getElementById('password').focus();
}
</SCRIPT>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>