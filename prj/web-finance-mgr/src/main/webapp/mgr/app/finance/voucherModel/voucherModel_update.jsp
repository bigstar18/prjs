<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>修改模板</title>
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
			<input type="hidden" name="entity.code" value="${entity.code}"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改模板${entity.code}信息
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
												模板修改
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														模板代码：
													</td>
													<td colspan="5">
														${entity.code}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														模板名称：
													</td>
													<td colspan="4">
														<input type="text" id="name" name="entity.name" value="${entity.name}" size="70"
															class="validate[required,maxSize[32]] "/>&nbsp;
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														摘要号：
													</td>
													<td>
														<input type="text" id="summaryNo" name="entity.summaryNo" value="${entity.summaryNo}"
															class="validate[required,maxSize[5]] "/>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														需要合同号：
													</td>
													<td>
														<select id="needcontractNo" name="entity.needcontractNo" class="validate[required]">
														<option value="">请选择</option>
														<c:forEach items="${voucherModel_need}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("needcontractNo").value="${entity.needcontractNo}";
														</script>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>												
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														借方科目代码：
													</td>
													<td>
														<input type="text" id="debitCode" name="entity.debitCode" value="${entity.debitCode}"
															class="validate[required,maxSize[16]] "/>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													
													
													<td align="right">
														<span class="required">*</span>
														贷方科目代码：
													</td>
													<td>
														<input type="text" id="creditCode" name="entity.creditCode" value="${entity.creditCode}"
															class="validate[required,maxSize[16]] "/>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
												<td align="right">
														备注：
													</td>
													<td colspan="5">
														<textarea id="note" rows="5" cols="70" name="entity.note" class="validate[maxSize[64]]">${entity.note}</textarea>
													</td>
													<td>
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
							<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/finance/financialVindicate/updateVoucherModel.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>