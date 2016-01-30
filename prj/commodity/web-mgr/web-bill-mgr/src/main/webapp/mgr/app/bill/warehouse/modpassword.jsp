<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
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
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function() {

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
	}	);
	});
</script>
	<title>密码修改</title>
	<meta http-equiv="Pragma" content="no-cache">
</head>
<body>

	<form id="frm" name="frm" id="frm" method="post"
		action="<%=basePath%>/stock/warehouseUser/updatePassword.action" targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
			<div class="warning">
				<div class="content">
					温馨提示 :修改管理员密码
				</div>
			</div>
				<tr>
					<td>
						<div class="div_cxtj">
					    	<div class="div_cxtjL"></div>
					        <div class="div_cxtjC">仓库管理员信息</div>
					        <div class="div_cxtjR"></div>
					    </div>
			<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									用户名 ：
								</td>
								<td align="left">
									${entity.userId }
									<input name="entity.userId" type="hidden" class="input_text"
										value="${entity.userId }" readonly="readonly">
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									管理员名称 ：
								</td>
								<td align="left">
								${entity.name }
									<input name="entity.name" type="hidden" class="input_text"
										value="${entity.name }" readonly="readonly">
								</td>
								<td>&nbsp;</td>
							</tr>
							<!-- <tr height="35">
								<td align="right" class="td_size">
									原密码 ：
								</td>
								<td align="left">
								-->
									<input id="old" name="oldPassword" type="hidden"
										class="input_text_mid" value="${entity.password }">
									<!-- 	<strong class="check_input">&nbsp;*</strong>
								</td>
								 -->
							</tr> 
							<tr height="35">
								<td align="right" class="td_size">
								<span class="required">*</span>
									新密码 ：
								</td>
								<td align="left">
									<input id="password" onkeydown="if(event.keyCode==32) return false"  name="entity.password" type="password"
										class="validate[required,custom[password],maxSize[<fmt:message key='userPassword' bundle='${PropsFieldLength}'/>]] input_text">
								</td>
								<td>
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
								<td align="left">
									<input name="specialforAudit.password1" onkeydown="if(event.keyCode==32) return false"  type="password"  id="password1" class="validate[required,equals[password]] input_text">
								</td>
								<td>
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="right">
				<tr height="35">
					<td width="50%">&nbsp;</td>
					<td align="right" id="tdId">
						<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/stock/warehouseUser/updatePassword.action" id="update"></rightButton:rightButton>
					&nbsp;&nbsp;
					</td>
					<td align="right">
						<button class="btn_sec" onClick="window.close()">
							关闭
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>

<%@ include file="/mgr/public/jsp/footinc.jsp"%>