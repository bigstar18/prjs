<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<head>
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript">
</script>
<script
	src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="GBK">
</script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" charset="GBK">
</script>
	<script>
	jQuery(document).ready(function() {

		$("#frm").validationEngine('attach');
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("��ȷ��Ҫ������");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
				$('#frm').submit();
			}
		}
	}	);
	});
</script>
	<title>�����޸�</title>
</head>
<body>

	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/self/passwordSelfSave.action"
		targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="div_cxtj">
    	<div class="div_cxtjL"></div>
        <div class="div_cxtjC">�����޸�</div>
        <div class="div_cxtjR"></div>
    </div>
			<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<c:if test="${CurrentUser.type==0}">
								<td align="right" class="td_size" width="35%">
									��Ա���룺
								</td>
								<td align="left">
									<input name="userId" type="text" class="input_text_pwd"
										value="${CurrentUser.broker.brokerId }" readonly="readonly">
								</td>
								</c:if>
								<c:if test="${CurrentUser.type==4}">
								<td align="right" class="td_size" width="35%">
									�Ӽ��̴��룺
								</td>
								<td align="left">
									<input name="userId" type="text" class="input_text_pwd"
										value="${CurrentUser.brokerAge.brokerAgeId }" readonly="readonly">
								</td>
								</c:if>
							</tr>
							<tr height="35">
								<c:if test="${CurrentUser.type==0}">
								<td align="right" class="td_size">
									��Ա���� ��
								</td>
								<td align="left">
									<input name="name" type="text" class="input_text_pwd"
										value="${CurrentUser.broker.name }" readonly="readonly">
								</td>
								</c:if>
								<c:if test="${CurrentUser.type==4}">
								<td align="right" class="td_size">
									�Ӽ������� ��
								</td>
								<td align="left">
									<input name="name" type="text" class="input_text_pwd"
										value="${CurrentUser.brokerAge.name }" readonly="readonly">
								</td>
								</c:if>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									ԭ���� ��
								</td>
								<td align="left">
									<input id="old" size="28" onkeydown="if(event.keyCode==32) return false"  name="oldPassword" type="password"
										class="validate[required,maxSize[16]] input_text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									������ ��
								</td>
								<td align="left">
									<input id="password" onkeydown="if(event.keyCode==32) return false"  size="28" name="password" type="password"
										class="validate[required,maxSize[16],custom[password]] input_text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									��ȷ������ ��
								</td>
								<td align="left">
									<input size="28" onkeydown="if(event.keyCode==32) return false"  name="specialforAudit.password1" type="password"
										id="password1" class="validate[required,equals[password]] input_text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center">
				<tr height="35">
					<td align="center">
						<button class="btn_sec" id="update">
							����
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick=
	window.close();
>
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<%@ include file="../public/jsp/footinc.jsp"%>