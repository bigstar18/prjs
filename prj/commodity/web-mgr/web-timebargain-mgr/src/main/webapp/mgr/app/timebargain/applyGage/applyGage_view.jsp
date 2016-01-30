<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
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
	function AuditGo(){
		frm.status.value=2;
		frm.submit();
	}

	function AuditNoGo(){
		frm.status.value=3;
		frm.submit();
	}
	function cancelAudit(){
		window.close();
	}
		
</script>
	<head>
		<title>�ֶ�ҵ�����</title>
	</head>
	<body>
		<form id="frm" action="${basePath}/timebargain/applyGage/auditApplyGage.action?entity.applyId=${entity.applyId}" method="post" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="left"  class="table1_style">
				<input id="status" name="entity.status" value="1" type="hidden"/>
					<tr>
						<td>
							<table border="0"  width="100%" align="left">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												��ܰ��ʾ :�ֶ�ҵ�����<br>
												<span style="color: red">ֻ���ڱ���״̬�����</span>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												${applyGage_typeMap[entity.applyType] }������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" height="150px" align="center" class="table2_style">
												<tr height="40">
													<td align="right" width="20%">
														������:
													</td>
													<td align="left" width="30%">
														${entity.applyId }
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														�����̴���:
													</td>
													<td align="left" width="30%">
														${entity.firmId }
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														��������:
													</td>
													<td align="left" width="30%">
														${entity.customerId }
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														��������:
													</td>
													<td align="left" width="30%">
														${entity.quantity }
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														����ʱ��:
													</td>
													<td align="left" width="30%">
														<fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														������:
													</td>
													<td align="left" width="30%">
														${entity.creator }
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														��˱�ע:
													</td>
													<td colspan="3">
														<textarea name="entity.remark2" rows="3" cols="40"  style="width:450" styleClass="text" ></textarea>
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
			<table border="0" cellspacing="0" cellpadding="4" width="600px"
				align="left">
				<tr>
					<td align="center">
						<rightButton:rightButton name="ͨ��" onclick="AuditGo()" className="btn_sec"
							action="/timebargain/applyGage/auditApplyGage.action" id="go" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
					<rightButton:rightButton name="��ͨ��" onclick="AuditNoGo()" className="btn_sec"
							action="/timebargain/applyGage/auditApplyGage.action" id="nogo" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
					<rightButton:rightButton name="�ر�" onclick="cancelAudit();" className="btn_sec"
							action="" id="cancel" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>
