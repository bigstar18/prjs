<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�������޸�</title>
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script>
			jQuery(document).ready(function() {
				if ("" != '${ReturnValue.info}' + "") {
					parent.document.frames('leftFrame').location.reload();
				}
				var firm = $("#frm");
				firm.validationEngine('attach');
				$("#update").click(function(check) {
					if (firm.validationEngine('validate')) {
						var vaild = affirm("��ȷ��Ҫ������");
						if (vaild == true) {
							firm.validationEngine('detach');
							firm.attr("action",'${basePath}'+$("#update").attr("action"));
							firm.submit();
						}
					}
				});
			});
		</script>
	</head>
	<body style="overflow-y: hidden">
		<iframe id="hiddenframe" name="hiddenframe" width=0 height=0 style="display:none" src="" application="yes"></iframe>
		<form id="frm" method="post" action="" target="hiddenframe">
			<input type="hidden" name="entity.firmID" value="${entity.firmID}"/><%//���������б�� %>
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�������޸�
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
												������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														�����̴��룺
													</td>
													<td>
														${entity.firmID}
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														�����������
													</td>
													<td width="30%">
														<input type="text" id="maxPersgltransMoney" name="entity.maxPersgltransMoney"
															class="validate[custom[doubleCus],min[0.01],max[${maxDouble}]] input_text"
															value="<c:if test="${entity.maxPersgltransMoney>0}"><fmt:formatNumber value="${entity.maxPersgltransMoney}" pattern="0.00" /></c:if>" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														ÿ���������
													</td>
													<td width="30%">
														<input type="text" id="maxPertransMoney" name="entity.maxPertransMoney"
															class="validate[custom[doubleCus],min[0.01],max[${maxDouble}]] input_text"
															value="<c:if test="${entity.maxPertransMoney>0}"><fmt:formatNumber value="${entity.maxPertransMoney}" pattern="0.00" /></c:if>" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														ÿ�������������
													</td>
													<td width="30%">
														<input type="text" id="maxPertranscount" name="entity.maxPertranscount"
															class="validate[custom[integer],min[0],max[${maxDouble}]] input_text"
															value="${entity.maxPertranscount}" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														������˶�ȣ�
													</td>
													<td width="30%">
														<input type="text" id="maxAuditMoney" name="entity.maxAuditMoney"
															class="validate[custom[doubleCus],min[0.01],max[${maxDouble}]] input_text"
															value="<c:if test="${entity.maxAuditMoney>0}"><fmt:formatNumber value="${entity.maxAuditMoney}" pattern="0.00" /></c:if>" />
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
			<div class="div_cx">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec" action="/bank/firm/updateFirm.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;<button class="btn_sec" onClick=window.close();>�ر�</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>