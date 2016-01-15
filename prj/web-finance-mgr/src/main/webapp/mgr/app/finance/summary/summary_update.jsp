<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>修改摘要</title>
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
			<input type="hidden" name="entity.summaryNo" value="${entity.summaryNo}"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改摘要${entity.summaryNo}信息
									<br/>
									<c:if test="${entity.isInit=='Y'}">
									   <span class="required">系统摘要不可修改</span>
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
												摘要修改
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														摘要号：
													</td>
													<td colspan="5">
														${entity.summaryNo}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														摘要名称：
													</td>
													<td colspan="4">
														<input type="text" id="summary" name="entity.summary" value="${entity.summary}" size="70"
															class="validate[required,maxSize[16]] "/>&nbsp;
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required"></span>
														归入总账：
													</td>
													<td>
														<select id="ledgerItem" name="entity.ledgerItem">
														<option value="">请选择</option>
														<c:forEach items="${ledgerFieldPage.result}" var="map" varStatus="status">
															<option value="${map.code}">${map.name}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("ledgerItem").value="${entity.ledgerItem}";
														</script>
													</td>
													<td>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														交易商资金借贷方向：
													</td>
													<td>
														<select id="fundDCFlag" name="entity.fundDCFlag" class="validate[required]">
														<option value="">请选择</option>
														<c:forEach items="${summary_fundDCFlagMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("fundDCFlag").value="${entity.fundDCFlag}";
														</script>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>												
												</tr>
												<tr>
													<td align="right">
														<span class="required"></span>
														对方科目代码：
													</td>
													<td>
														<input type="text" id="accountCodeOpp" name="entity.accountCodeOpp" value="${entity.accountCodeOpp}"
															class="validate[maxSize[16]] "/>
													</td>
													<td>
													</td>
													
													
													<td align="right">
														<span class="required">*</span>
														附加账：
													</td>
													<td>
														<select id="appendAccount" name="entity.appendAccount" class="validate[required]">
														<option value="">请选择</option>
														<c:forEach items="${summary_appendAccountMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("appendAccount").value="${entity.appendAccount}";
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
							<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/finance/summary/updateSummary.action" id="update"></rightButton:rightButton>
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