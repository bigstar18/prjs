<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>模板添加</title>
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
				//获取交易商代码下拉列表
				var firmList = getfirmList();
				$("#firm_firmId").focus().autocomplete(firmList);

				//ajax验证交易商代码是否存在
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../../ajaxcheckVoucherModel/voucherModel/formCheckVoucherModelByCode.action",
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
				$("#add").click(function(){
					//验证信息
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
									温馨提示 :模板添加
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
												添加模板
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														模板代码：
													</td>
													<td colspan="4">
														<input type="text" id="code" name="entity.code"
															class="validate[required,maxSize[8]] "/>&nbsp;
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														模板名称：
													</td>
													<td colspan="4">
														<input type="text" id="name" name="entity.name" size="70"
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
														<input type="text" id="summaryNo" name="entity.summaryNo"
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
														<input type="text" id="debitCode" name="entity.debitCode"
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
														<input type="text" id="creditCode" name="entity.creditCode"
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
														<textarea id="note" rows="5" cols="70" name="entity.note" class="validate[maxSize[64]]"></textarea>
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
							<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/finance/financialVindicate/addVoucherModel.action" id="add"></rightButton:rightButton>
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