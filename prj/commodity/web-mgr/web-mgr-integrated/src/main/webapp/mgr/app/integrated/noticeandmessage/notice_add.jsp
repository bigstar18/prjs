<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript">
</script>
<script
	src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
	type="text/javascript">
</script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" >
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" ></script>
		
	<script>
	jQuery(document).ready(function() {
		jQuery("#frm").validationEngine('attach');
		$("#addNotice").click(function(){
				if(jQuery("#frm").validationEngine('validate')){
					//frm.action = "${basePath}" + $("#addNotice").attr("action");
					var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					$("#title").val($("#title").val().trim());
					frm.submit();
				}
				}
			});
		
	});
</script>
		<title>新公告</title>
</style>
	</head>
	<body style="overflow-y: hidden">
	<div style="height: 40"></div>
		<div class="div_cx">
			<form id="frm" method="post"
			action="${basePath }/trade/notice/addNotice.action"
			targetType="hidden">
			<input type="hidden" name="entity.userId" value="${CurrentUser.userId }">
				<table border="0" width="80%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :添加公告
								</div>
							</div>
						</td>
					</tr>
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
								<table border="0" cellspacing="0" cellpadding="4" width="100%"
									align="center" class="table2_style">
									<tr height="20">
										<td align="right">
											公告标题：
										</td>
										<td>
											<input type="text" id="title"
												class="validate[required,maxSize[<fmt:message key='noticeTitle' bundle='${PropsFieldLength}'/>]]"
												name="entity.title"  >
										</td>
										<td align="left" height="40" colspan="3">
											<div id="id_vTip" class=""></div>
										</td>
									</tr>
										<tr>
											<td align="right">
												管理员：
											</td>
											<td>
												${CurrentUser.userId }
											</td>
											<td align="left" height="40">
												<div id="fullName_vTip" class=""></div>
											</td>
										</tr>
									<tr>
										<tr>
											<td align="right">
												公告内容：
											</td>
											<td>
												<textarea rows="6" cols="40" id="entity.content"
													name="entity.content" class="validate[required,maxSize[<fmt:message key='noticeContent' bundle='${PropsFieldLength}'/>]]"  ></textarea>
											</td>
											<td align="left" height="40" colspan="3">
												<div id="input_text" class=""></div>
											</td>
										</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center">
				<tr>
					<td align="right">
						<rightButton:rightButton name="添加" onclick="" className="btn_sec"
							action="/trade/notice/addNotice.action" id="addNotice"></rightButton:rightButton>
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