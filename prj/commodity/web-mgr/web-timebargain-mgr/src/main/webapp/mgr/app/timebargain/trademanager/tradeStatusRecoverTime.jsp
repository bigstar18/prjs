<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
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
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
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
				var vaild = affirm("��ȷ��Ҫ������");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
				    $("#frm").submit();
			}
		}})
	});
</script>
		
		
		
		
		
		<title>���ûָ�����ʱ��</title>
	</head>

	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
			<form id="frm" action="${basePath}/timebargain/tradeManager/updateRecoverTime.action"
				method="post" targetType="hidden">
				<table border="0" width="80%" align="center">
					<tr>
						<td >&nbsp;</td>
					</tr>
					<tr>
						<td >
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�趨�ָ�ʱ��
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td >
							<div class="div_cxtj">
						    	<div class="div_cxtjL"></div>
						        <div class="div_cxtjC">�趨�ָ�ʱ��</div>
						        <div class="div_cxtjR"></div>
				   		    </div>
							<div style="clear: both;"></div>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								width="100%" class="st_bor">
								<tr height="35">
									<td align="center">
										&nbsp;�ָ�ʱ�䣺
										<input type="text" name="recoverTime"  class="validate[required,custom[time]] text-input"
											id="recoverTime" value="${systemStatus.recoverTime }">
									</td>
								</tr>
								<tr height="35">
									<td align="center">
										<span class="req">&nbsp;��ʱ����д��ʽΪ��24hours HH:MM:SS��</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
		</div>
		<div > 
				<table border="0" cellspacing="0" cellpadding="4" width="80%"
					align="center">
					<tr>
						<td align="center">
						</td>
						<td align="right">
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec"
							action="/timebargain/tradeManager/updateRecoverTime.action"
							id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" id="btnClose"
									onclick="window.close();">
									�ر�
							</button>
						</td>
					</tr>
				</table>
		</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>