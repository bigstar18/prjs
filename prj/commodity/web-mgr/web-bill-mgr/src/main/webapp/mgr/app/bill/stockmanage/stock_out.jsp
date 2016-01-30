<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script
	src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="GBK">
	
</script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" charset="GBK"></script>

<script type="text/javascript">
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
		}
	}	)

	});
		
	jQuery(document).ready(function(){
		document.getElementById('idnumbertr').style.display='';
 		document.getElementById('addresstr').style.display='none';
 		document.getElementById('phonetr').style.display='none';
			$("#deliveryStatus").change(function(){
				var value = $("#deliveryStatus").val();
				  if(value==0){
				 		document.getElementById('idnumbertr').style.display='';
				 		document.getElementById('addresstr').style.display='none';
				 		document.getElementById('phonetr').style.display='none';
					  }
				  if(value==1){
					  	document.getElementById('idnumbertr').style.display='none';
					  	document.getElementById('addresstr').style.display='';
					  	document.getElementById('phonetr').style.display='';
					  }
				})
				
		});
		
		jQuery(document).ready(function(){
		
						document.getElementById('invoicePersonDiv').style.display='none';
						document.getElementById('invoicePersonPhoneDiv').style.display='none';
						document.getElementById('invoicePersonAddressDiv').style.display='none';
						
						document.getElementById('invoiceCompanyNameDiv').style.display='none';
						document.getElementById('invoiceDutyParagraphDiv').style.display='none';
						document.getElementById('invoiceCompanyPhoneDiv').style.display='none';
						document.getElementById('invoiceCompanyAddressDiv').style.display='none';
						document.getElementById('invoiceCompanyBankDiv').style.display='none';
						document.getElementById('invoiceCompanyBankAccountDiv').style.display='none';
			$("#invoiceStatus").change(function(){
				var value = $("#invoiceStatus").val();
				  	if(value==0){ //�����߷�Ʊ
						document.getElementById('invoicePersonDiv').style.display='none';
						document.getElementById('invoicePersonPhoneDiv').style.display='none';
						document.getElementById('invoicePersonAddressDiv').style.display='none';
						
						document.getElementById('invoiceCompanyNameDiv').style.display='none';
						document.getElementById('invoiceDutyParagraphDiv').style.display='none';
						document.getElementById('invoiceCompanyPhoneDiv').style.display='none';
						document.getElementById('invoiceCompanyAddressDiv').style.display='none';
						document.getElementById('invoiceCompanyBankDiv').style.display='none';
						document.getElementById('invoiceCompanyBankAccountDiv').style.display='none';
					  }
				  	else if(value==1){//���˷�Ʊ
						document.getElementById('invoicePersonDiv').style.display='';
						document.getElementById('invoicePersonPhoneDiv').style.display='';
						document.getElementById('invoicePersonAddressDiv').style.display='';
						
						document.getElementById('invoiceCompanyNameDiv').style.display='none';
						document.getElementById('invoiceDutyParagraphDiv').style.display='none';
						document.getElementById('invoiceCompanyPhoneDiv').style.display='none';
						document.getElementById('invoiceCompanyAddressDiv').style.display='none';
						document.getElementById('invoiceCompanyBankDiv').style.display='none';
						document.getElementById('invoiceCompanyBankAccountDiv').style.display='none';
					  }
					else if (value == 2){//��˾��Ʊ
						document.getElementById('invoicePersonDiv').style.display='none';
						document.getElementById('invoicePersonPhoneDiv').style.display='none';
						document.getElementById('invoicePersonAddressDiv').style.display='none';
						
						document.getElementById('invoiceCompanyNameDiv').style.display='';
						document.getElementById('invoiceDutyParagraphDiv').style.display='';
						document.getElementById('invoiceCompanyPhoneDiv').style.display='';
						document.getElementById('invoiceCompanyAddressDiv').style.display='';
						document.getElementById('invoiceCompanyBankDiv').style.display='';
						document.getElementById('invoiceCompanyBankAccountDiv').style.display='';
					}
				})
				
		});
</script>
<head>
<title><c:if test="${HaveWarehouse eq 1}">�ֵ�����</c:if> <c:if
		test="${HaveWarehouse eq 0}">�ֵ���������</c:if></title>
<meta http-equiv="Pragma" content="no-cache">
</head>
<body style="overflow-y: hidden">
	<form id="frm" name="frm"
		action="${basePath }/stock/list/stockOut.action" targetType="hidden"
		method="post">
		<div>
			<div class="div_cx">
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :
									<c:if test="${HaveWarehouse eq 1}">�ֵ�����</c:if>
									<c:if test="${HaveWarehouse eq 0}">�ֵ���������</c:if>
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
											<div class="div_cxtjC">�ֵ���Ϣ</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" width="15%">�ֵ��ţ�</td>
													<td width="20%"><input type="hidden"
														class="validate[required]" name="stockId"
														value="${entity.stockId }" /> ${entity.stockId}</td>
												</tr>
												<!-- �вְ�ֵ����� -->
												<c:if test="${HaveWarehouse eq 1}">
													<tr>
														<td align="right"><span class='required'>*</span>�ֿ�ϵͳ�˻���ţ�
														</td>
														<td width="20%"><input type="text"
															class="validate[required] input_text" id="userId"
															name="userId" value="" /></td>
													</tr>
													<tr>
														<td align="right"><span class='required'>*</span>�ֿ�ϵͳ�˻�����
														</td>
														<td width="20%"><input type="text"
															class="validate[required] input_text" id="userName"
															name="userName" value="" /></td>
													</tr>
													<tr>
														<td align="right"><span class='required'>*</span>�ֿ�ϵͳ��֤���룺
														</td>
														<td width="20%"><input type="password"
															class="validate[required] input_text" id="password"
															name="password" value="" /></td>
													</tr>
												</c:if>
												<!-- �޲ְ�ֵ��������� -->
												<c:if test="${HaveWarehouse eq 0}">

													<tr>
														<td align="right"><span class='required'>*</span>�����ʽ��
														</td>
														<td><select id="deliveryStatus" name="deliveryStatus"
															class="validate[required]">
																<option value="0">�û�����</option>
																<option value="1">����</option>
														</select></td>
													</tr>

													<tr>
														<td align="right"><span class='required'>*</span>����ˣ�
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
															id="deliveryPerson" name="deliveryPerson" /></td>
													</tr>

													<tr id="idnumbertr">
														<td align="right"><span class='required'>*</span>��������֤�ţ�
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[18],custom[id]] input_text"
															id="idnumber" name="idnumber" /></td>
													</tr>

													<tr id="addresstr">
														<td align="right"><span class='required'>*</span>�ʼĵ�ַ��
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[32]] input_text"
															id="address" name="address" /></td>
													</tr>
													<tr id="phonetr">
														<td align="right"><span class='required'>*</span>�绰��
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[32],custom[phone]] input_text"
															id="phone" name="phone" /></td>
													</tr>
													<tr>
														<td align="right"><span class='required'>*</span>��Ʊѡ�
														</td>
														<td><select id="invoiceStatus" name="invoiceStatus"
															class="validate[required]">
																<option value="0">����Ҫ��Ʊ</option>
																<option value="1">���˷�Ʊ</option>
																<option value="2">��˾��Ʊ</option>
														</select></td>
													</tr>
													<tr id="invoicePersonDiv">
														<td align="right"><span
															class='required'>*</span>��Ʊ��������</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
															id="invoicePerson" name="invoicePerson" /></td>
													</tr>
													<tr id="invoicePersonPhoneDiv">
														<td align="right"><span class='required'>*</span>��Ʊ�˵绰��
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[16],custom[phone]] input_text"
															id="invoicePersonPhone" name="invoicePersonPhone" /></td>
													</tr>
													<tr id="invoicePersonAddressDiv">
														<td align="right"><span class='required'>*</span>��Ʊ�˵�ַ��
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
															id="invoicePersonAddress" name="invoicePersonAddress" />
														</td>
													</tr>
													<tr id="invoiceCompanyNameDiv">
														<td align="right" ><span
															class='required'>*</span>��˾���ƣ�</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[100],custom[onlyLetterNumberCN]] input_text"
															id="invoiceCompanyName" name="invoiceCompanyName" /></td>
													</tr>
													<tr id="invoiceDutyParagraphDiv">
														<td align="right" ><span
															class='required'>*</span>��˰��ʶ���룺</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[100],custom[onlyCapitalLetterNumber]] input_text"
															id="invoiceDutyParagraph" name="invoiceDutyParagraph" />
														</td>
													</tr>
													<tr id="invoiceCompanyPhoneDiv">
														<td align="right"><span class='required'>*</span>ע��绰��
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[16],custom[phoneOrTel]] input_text"
															id="invoiceCompanyPhone" name="invoiceCompanyPhone" /></td>
													</tr>
													<tr id="invoiceCompanyAddressDiv">
														<td align="right"><span class='required'>*</span>ע���ַ��
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
															id="invoiceCompanyAddress" name="invoiceCompanyAddress" />
														</td>
													</tr>
													<tr id="invoiceCompanyBankDiv">
														<td align="right"><span class='required'>*</span>�������У�
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
															id="invoiceCompanyBank" name="invoiceCompanyBank" /></td>
													</tr>
													<tr id="invoiceCompanyBankAccountDiv">
														<td align="right"><span class='required'>*</span>�����˺ţ�
														</td>
														<td width="20%"><input type="text"
															class="validate[required,maxSize[32],custom[onlyNumberSp]] input_text"
															id="invoiceCompanyBankAccount"
															name="invoiceCompanyBankAccount" /></td>
													</tr>
												</c:if>

											</table>
										</div>
									</td>
								</tr>
							</table>

						</td>
					</tr>

					<tr>

						<td align="right"><rightButton:rightButton name="����"
								onclick="" className="btn_sec"
								action="/stock/list/stockOut.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick=window.close();>�ر�</button>
							&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
		</div>
	</form>

</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>