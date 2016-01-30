<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.mgr.statictools.ApplicationContextInit"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
try{
request.setAttribute("needlessAccountBank",ApplicationContextInit.getConfig("needlessAccountBank"));
request.setAttribute("defaultAccount",ApplicationContextInit.getConfig("defaultAccount"));
}catch(Exception e){}
%>
<html>
	<head>
		<title>�޸Ľ����̺����а���Ϣ</title>
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script src="${basePath }/mgr/app/bank/js/bank.js" type="text/javascript" charset="GBK"></script>
		<script>
			jQuery(document).ready(function() {
				if ("" != '${ReturnValue.info}' + "") {
					parent.document.frames('leftFrame').location.reload();
				}
				if(isContentsBank('${needlessAccountBank}','${entity.bank.bankID}') && '${defaultAccount}'=='${entity.account}'){<%//�������Ҫ�����ʺţ����������ʺ�ΪĬ�������ʺţ������������Ϊ��%>
					$("#account").attr("value","");
					document.getElementById("account").className="validate[maxSize[<fmt:message key='FirmIDAndAccount.account' bundle='${PropsFieldLength}' />]] input_text";
					document.getElementById("accountspan").innerText="";
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
			<input type="hidden" name="entity.firm.firmID" value="${entity.firm.firmID}"/><%//���������̴��� %>
			<input type="hidden" name="entity.bank.bankID" value="${entity.bank.bankID}"/><%//���������б�� %>
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸Ľ����̺����а���Ϣ
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
												�޸���Ϣ
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
														${entity.firm.firmID}
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														���У�
													</td>
													<td width="30%">
														${entity.bank.bankID}
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required" id="accountspan">*</span>
														������˺ţ�
													</td>
													<td width="30%">
														<input type="text" id="account" name="entity.account" value="${entity.account}" <c:if test="${entity.isOpen==1}">readonly="readonly"</c:if>
															class="validate[required,maxSize[<fmt:message key='FirmIDAndAccount.account' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														������˻�����
													</td>
													<td width="30%">
														<input type="text" id="accountName" name="entity.accountName" value="${entity.accountName}" <c:if test="${entity.isOpen==1}">readonly="readonly"</c:if>
															class="validate[required,maxSize[<fmt:message key='FirmIDAndAccount.accountName' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														֤�����ͣ�
													</td>
													<td width="30%">
														<c:if test="${entity.isOpen==1}">
														<select id="cardType" name="entity.cardType" class="validate[required] input_text" disabled="disabled">
															<option value="">��ѡ��</option>
															<c:forEach var="map" items="${firmIDAndAccountCardType}">
															<option value="${map.key}" <c:if test="${entity.cardType eq map.key}">selected="selected"</c:if>>${map.value}</option>
															</c:forEach>
														</select>
														</c:if>
														<c:if test="${!(entity.isOpen==1)}">
														<select id="cardType" name="entity.cardType" class="validate[required] input_text">
															<option value="">��ѡ��</option>
															<c:forEach var="map" items="${firmIDAndAccountCardType}">
															<option value="${map.key}" <c:if test="${entity.cardType eq map.key}">selected="selected"</c:if>>${map.value}</option>
															</c:forEach>
														</select>
														</c:if>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														֤�����룺
													</td>
													<td width="30%">
														<input type="text" id="card" name="entity.card" value="${entity.card}" <c:if test="${entity.isOpen==1}">readonly="readonly"</c:if>
															class="validate[required,maxSize[<fmt:message key='FirmIDAndAccount.card' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														���������ƣ�
													</td>
													<td width="30%">
														<input type="text" id="bankName" name="entity.bankName" value="${entity.bankName}"
															class="validate[maxSize[<fmt:message key='FirmIDAndAccount.bankName' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														������ʡ�ݣ�
													</td>
													<td width="30%">
														<input type="text" id="bankProvince" name="entity.bankProvince" value="${entity.bankProvince}"
															class="validate[maxSize[<fmt:message key='FirmIDAndAccount.bankProvince' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														�����������У�
													</td>
													<td width="30%">
														<input type="text" id="bankCity" name="entity.bankCity" value="${entity.bankCity}"
															class="validate[maxSize[<fmt:message key='FirmIDAndAccount.bankCity' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														�绰��
													</td>
													<td width="30%">
														<input type="text" id="mobile" name="entity.mobile" value="${entity.mobile}"
															class="validate[maxSize[<fmt:message key='FirmIDAndAccount.mobile' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														�����ʼ���
													</td>
													<td width="30%">
														<input type="text" id="email" name="entity.email" value="${entity.email}"
															class="validate[maxSize[<fmt:message key='FirmIDAndAccount.email' bundle='${PropsFieldLength}' />]] input_text"/>
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
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec" action="/bank/firmregistbank/updateRegist.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;<button class="btn_sec" onClick=window.close();>�ر�</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>