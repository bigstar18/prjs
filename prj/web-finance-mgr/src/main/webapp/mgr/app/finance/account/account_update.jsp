<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>修改科目信息</title>
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
									温馨提示 :修改科目${entity.code}信息
									<br/>
									<c:if test="${entity.isInit=='Y'}">
									   <span class="required">系统科目不可修改</span>
									</c:if>
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
												科目修改
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														科目代码：
													</td>
													<td>
														${entity.code}&nbsp;
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														科目名称：
													</td>
													<td>
														<input type="text" id="name" name="entity.name" style="width: 120px" value="${entity.name }"
															class="validate[required,maxSize[16]] "/>&nbsp;
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														借贷方向：
													</td>
													<td>
														<select id="dcFlag" name="entity.dcFlag" class="validate[required]" style="width: 120px">
														<option value="">请选择</option>
														<c:forEach items="${account_dCFlagMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("dcFlag").value="${entity.dcFlag}";
														</script>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>								
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														科目级别：
													</td>
													<td>
														<select id="accountLevel" name="entity.accountLevel" class="validate[required]" style="width: 120px">
														<option value="">请选择</option>
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														</select>
														<script type="text/javascript">
															document.getElementById("accountLevel").value="${entity.accountLevel}";
														</script>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
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
							<c:if test="${entity.isInit=='N'}">  
							<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/finance/account/updateAccount.action" id="update"></rightButton:rightButton>
							</c:if>
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