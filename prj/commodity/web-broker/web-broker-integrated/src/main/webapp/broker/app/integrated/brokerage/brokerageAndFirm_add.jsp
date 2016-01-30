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
</script>
	<head>
		<title>添加居间商信息</title>
	</head>
		<body onload="">
		<form id="frm" method="post"
			action="${basePath}/broker/brokerage/managerFirmAdd.action" targetType="hidden">
			<div class="div_cx"  style="overflow:auto;height:200px;" >
				<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :添加居间商所辖交易商
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
												添加信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="right" class="table2_style">
												<tr>
													<td align="right" width="20%">
														居间商代码：
													</td>
													<td  width="30%">
														<input type="text"  id="brokerAgeId" name="entity.brokerAgeId" 
															class="input_text" style="background-color:#eee"
															readonly="false" value="${brokerAgeId }"/>														
													</td>																						
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														交易商代码：
													</td>
													<td>
														<input type="text"  id="firmId" name="entity.firmId" class="validate[required,maxSize[18] input_text"  />														
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
							<rightButton:rightButton name="添加" onclick="" className="btn_sec"
								action="/broker/brokerage/managerFirmAdd.action" id="update"></rightButton:rightButton>
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