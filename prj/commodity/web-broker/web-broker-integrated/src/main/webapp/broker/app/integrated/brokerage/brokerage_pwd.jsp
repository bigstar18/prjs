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
			$("#frm").validationEngine('attach');
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						//$('#frm').attr('action', 'action');
					    $("#frm").submit();
						//document.getElementById("update").disabled=true;
				}
			}})
		});
		function getHidden(type){
			if(type !=3){
				$("#content").css("display","block");
			}else{
				$("#content").css("display","none");
			}
		}
		function changeBrokerage(sel){
			$("#pbrokerAgeId").attr("value",sel.options[sel.selectedIndex].value);
		}
</script>
	<head>
		<title>居间商密码修改</title>
	</head>
		<body onload="">
		<form id="frm" method="post"
			action="${basePath}/broker/brokerage/updatePWD.action" targetType="hidden">
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :居间商密码修改
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
														居间商代码 ：
													</td>
													<td width="30%">
														<input type="text" id="brokerAgeId" style="background-color:#eee"
															readonly="false" class="input_text" name="entity.brokerAgeId" value="${entity.brokerAgeId }"/>
													</td>
													<td colspan="2" width="*">
														&nbsp;
													</td>
												</tr>
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
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="修改" onclick="" className="btn_sec"
								action="/broker/brokerage/updatePWD.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick=window.close();>
								关闭
							</button>&nbsp;&nbsp;&nbsp;&nbsp;
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