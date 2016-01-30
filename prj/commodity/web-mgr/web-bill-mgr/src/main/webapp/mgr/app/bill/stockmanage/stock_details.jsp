<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript"></script>
	<script
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
		</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
<style type="text/css">
.none{
	display: none;
}
.block{
	display: block;
}
</style>
		
<script type="text/javascript">
		jQuery(document).ready(function(){
				// ����ѡ�е��ͻ���ʽչʾ��Ӧ��Ϣ 
				$("#deliveryStatus").change(function(){
					var value = $("#deliveryStatus").val();
					  if(value==0){
					 		document.getElementById('idnumbertr').style.display='block';
					 		document.getElementById('addressphone').style.display='none';
					 		
						  }
					  if(value==1){
						  	document.getElementById('idnumbertr').style.display='none';
						  	document.getElementById('addressphone').style.display='';
						  	
						  }
					});
					$("#invoiceStatus").change(function(){
						var value = $("#invoiceStatus").val();
						//alert(value);
						  	if(value==-1){ //�����߷�Ʊ
						  		$(".companyinvoice").hide();
						  		$(".personinvoice").hide();
							  }
						  	else if(value==0){//���˷�Ʊ
						  		$(".companyinvoice").hide();
						  		$(".personinvoice").show();
							  }
							else if (value == 1){//��˾��Ʊ
								$(".personinvoice").hide();
								$(".companyinvoice").show();
							}
						});
			});
		jQuery(document).ready(function(){
				//  չʾ�ֵ���Ӧ��Ϣ 
			//alert("1");
			var value ;
			if( $("#ValueId").val()!="" || $("#ValueId").val()!=null ){
				value=$("#ValueId").val()
			}
	 		var num ;
	 		if($("#num").val()!="" || $("#num").val()!=null){
	 			num=$("#num").val();
	 		}
	 		 	
				  if(value==0){
				 		//document.getElementById('idnumbertr').style.display='';
				 		//document.getElementById('addressphone').style.display='none';
				 		$("#addressphone").attr("style","display:none");
				 		 $("#deliveryStatus").find("option[value='0']").attr("selected",true);
				 		 if(num != 1){
						 		//document.getElementById("span").innerText = "�û�����";
						 		$("#span").text("�û�����");
					 		 }
					  }
				  if(value==1){
					  	//document.getElementById('idnumbertr').style.display='none';
				 		$("#idnumbertr").attr("style","display:none");
					  	//document.getElementById('addressphone').style.display='';
					  	$("#deliveryStatus").find("option[value='1']").attr("selected",true);
					  	if(num != 1){
					  			//document.getElementById("span").innerText = "����";
						 		$("#span").text("����");
					  		}
					  }		
		});

		jQuery(document).ready(function() {

			$("#frm").validationEngine('attach');

			$("#updatest").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("��ȷ��Ҫ������");
					if (vaild == true) {
						$("#frm").validationEngine('detach');
						
					$("#frm").submit();
				}
			}
		}	)

		});
			
</script>
	<head>
		<title>�ֵ�����</title>	
		<meta http-equiv="Pragma" content="no-cache"/>	
	</head>
	<body style="overflow-y: hidden">
		<form id="frm"  name="frm" method="post" action="${basePath }/stock/exitlist/stockOutUpdate.action"  targetType="hidden" >	
			<input type="hidden" id="ValueId" value="${outStock.deliveryStatus }" />
			<input type="hidden" id="Num" value="${num }" />
			<div
				style="overflow-x: hidden; position: relative;  overflow: auto; height: 400px; width: 100%;">
				<div class="div_cx">
					<table border="0" width="95%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">
										��ܰ��ʾ :�ֵ�����
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
													�ֵ���Ϣ
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<tr>
														<td align="right" width="20%">
															�ֵ��ţ�
														</td>
														<td width="20%">
															
															<input type="hidden"class="validate[required]" name="stockId"
																value="${entity.stockId }" />
															${entity.stockId}
														</td>
														<td align="right" width="20%">
															�ֿ��ţ�
														</td>
														<td width="30%">
															${entity.warehouseId}
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															�ֿ�ԭʼƾ֤�ţ�
														</td>
														<td width="30%">
															<div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${entity.realStockCode}</div>
														</td>
														<td align="right" width="20%">
															���������̣�
														</td>
														<td width="20%">
															${entity.ownerFirm}
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															Ʒ����
														</td>
														<td width="20%">
															${entity.breed.breedName}
														</td>
														<td align="right" width="20%">
															��Ʒ������
														</td>
														<td width="30%"><div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">
															<fmt:formatNumber value="${entity.quantity}"
																pattern="#,##0.00" />��${entity.unit }��</div>
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															����ʱ�䣺
														</td>
														<td width="20%">
															<fmt:formatDate value="${entity.createTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													<c:if test="${entity.lastTime !=null}">
														<td align="right" width="20%">
															�����ʱ�䣺
														</td>
														<td width="30%">
															<fmt:formatDate value="${entity.lastTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													</c:if>
													</tr>
													<c:if test="${entity.stockStatus eq 5||entity.stockStatus eq 2}">
													<tr>
														<td align="right" width="20%">
															�����Կ��
														</td> 
														<td width="20%">
															<input type="hidden"class="validate[required]" name="key"
																value="${outStock.key}" />
															${outStock.key} 
															
													</td> 
														
														
													</tr>
													<tr>
														<td align="right" width="20%">
															�����ʽ��
														</td>
														<td width="20%">
															<c:if test="${num == 1}">
																<select id="deliveryStatus" name="deliveryStatus" class="validate[required]" >
																	<option value="0">�û�����</option>
																	<option value="1">����</option>
																</select>
															</c:if>
															<c:if test="${num != 1}">
																<span id="span"></span>
															</c:if>
														</td>
														<td align="right" width="20%">
															����ˣ�
														</td>
														<td width="20%">
															<c:if test="${num == 1}">
																<input type="text" class="validate[required,maxSize[32]] input_text" id="deliveryPerson"
																	name="deliveryPerson" value="${outStock.deliveryPerson}" />
															</c:if>
															<c:if test="${num != 1}">
																${outStock.deliveryPerson}
															</c:if>
														</td>
													</tr>
													
													<tr id="idnumbertr">
														<td align="right" width="20%">
															��������֤�ţ�
														</td>
														<td width="20%">
														<c:if test="${num == 1}">
															<input type="text" class="validate[required,maxSize[18]] input_text" id="idnumber"
																name="idnumber" value="${outStock.idnumber}" />
														</c:if>
														<c:if test="${num != 1}">
															${outStock.idnumber}
														</c:if>
														</td>
													</tr>
													
													<!-- �鿴��Ʊ��Ϣ -->
												<c:if test="${num ne 1}">
													<tr >
														<td align="right" width="20%">
															�Ƿ񿪾ݷ�Ʊ��
														</td>
														<td width="20%">
														<c:if test="${invoiceStatus eq 0}">
															��
														</c:if>
														<c:if test="${invoiceStatus eq 1}">
															��
														</c:if>
														</td>
													</tr>
												<c:if test="${invoiceStatus eq 1}">
													<tr>
														<td align="right" width="20%">
															��Ʊ���ͣ�
														</td>
														<td>
															<c:if test="${invoiceinform.invoicetype eq 1}">��˾��Ʊ</c:if>
															<c:if test="${invoiceinform.invoicetype eq 0 }">���˷�Ʊ</c:if>
														</td>
											       <c:if test="${invoiceinform.invoicetype eq 1}">
														<td align="right" width="20%">��˾���ƣ�</td>
														<td width="30%">${invoiceinform.companyname }</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															��˰��ʶ���룺
														</td>
														<td width="20%">
															${invoiceinform.dutyparagraph }
														</td>
														<td align="right" width="20%">
															�������У�
														</td>
														<td width="30%">${invoiceinform.bank }
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															�����ʺţ�
														</td>
														<td width="20%">
															${invoiceinform.bankaccount }
														</td>
														<td align="right" width="20%">
															��˾ע��绰��
														</td>
														<td width="30%">${invoiceinform.bank }
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															��˾ע���ַ��
														</td>
														<td width="20%">
															${invoiceinform.address }
														</td>
												</c:if>
												<c:if test="${invoiceinform.invoicetype eq 0 }">
														<td align="right" width="20%">
															��Ʊ��������
														</td>
														<td width="30%">${invoiceinform.name }
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
														���˵绰��
														</td>
														<td width="20%">
															${invoiceinform.phone }
														</td>
														<td align="right" width="20%">
															������ϸ��ַ��
														</td>
														<td width="30%">${invoiceinform.address }
														</td>
												</c:if>
												</tr>
													</c:if>
												</c:if>
													<!-- �鿴��Ʊ��Ϣ ����-->
													<tr id="addressphone">
														<td align="right" width="20%">
															�ʼĵ�ַ��
														</td>
														<td width="20%">
															<c:if test="${num == 1}">
																<input type="text" class="validate[required,maxSize[32]] input_text" id="address"
																	name="address" value="${outStock.address}" />
															</c:if>
															<c:if test="${num != 1}">
																${outStock.address}
															</c:if>
														</td>
												
														<td align="right" width="20%">
																��ϵ�˵绰��
														</td>
														<td width="20%">
															<c:if test="${num == 1}">
																<input type="text" class="validate[required,maxSize[32]] input_text" id="phone"
																	name="phone" value="${outStock.phone}" />
															</c:if>
															<c:if test="${num != 1}">
																${outStock.phone}
															</c:if>
														</td>
														
													</tr>
													<!-- �޸ķ�Ʊ��Ϣ -->
												<c:if test="${num eq 1}">
												<tr >
														<td align="right" width="20%">
															�Ƿ񿪾ݷ�Ʊ��
														</td>
														<td width="20%">
														<select id="invoiceStatus" name="invoiceStatus" class="validate[required]">
																<option value="-1"  <c:if test="${invoiceStatus eq 0 }">selected="selected"</c:if>>����Ҫ��Ʊ</option>
																<option value="0" <c:if test="${invoiceinform.invoicetype eq 0 }">selected="selected"</c:if> >���˷�Ʊ</option>
																<option value="1" <c:if test="${invoiceinform.invoicetype eq 1 }">selected="selected"</c:if> >��˾��Ʊ</option>
														</select>
														</td>
														
													</tr>
													<c:if  test="${invoiceStatus eq 0}">
													<tr class="companyinvoice"  style="display: none;">
														<td align="right" width="20%">��˾���ƣ�</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[100],custom[onlyLetterNumberCN]] input_text"
														 id="invoiceCompanyName" name="invoiceCompanyName" />
														
														</td>
														<td align="right" width="20%">
															��˾ע���ַ��
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
														 id="invoiceCompanyAddress" name="invoiceCompanyAddress" />
															
														</td>
													</tr >
													<tr class="companyinvoice"  style="display: none;">
														<td align="right" width="20%">
															��˰��ʶ���룺
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[100],custom[onlyCapitalLetterNumber]] input_text"
														 id="invoiceDutyParagraph" name="invoiceDutyParagraph" />
															
														</td>
														<td align="right" width="20%">
															�������У�
														</td>
														<td width="30%"><input type="text" class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
														 id="invoiceCompanyBank" name="invoiceCompanyBank" />
													
														</td>
													</tr>
													<tr class="companyinvoice"  style="display: none;">
														<td align="right" width="20%">
															�����ʺţ�
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[32],custom[onlyNumberSp]] input_text"
														 id="invoiceCompanyBankAccount" name="invoiceCompanyBankAccount" />
															
														</td>
														<td align="right" width="20%">
															��˾ע��绰��
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[16],custom[phoneOrTel]] input_text"
														 id="invoiceCompanyPhone" name="invoiceCompanyPhone" />
														
														</td>
													</tr>
												  <tr class="personinvoice" style="display: none;">
														<td align="right" width="20%">
															��Ʊ��������
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
														 id="invoicePerson" name="invoicePerson" />
														
														</td>
														<td align="right" width="20%">
														���˵绰��
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[16],custom[phone]] input_text"
														 id="invoicePersonPhone" name="invoicePersonPhone" />
															
														</td>
													</tr>
													<tr class="personinvoice" style="display: none;">
														<td align="right" width="20%">
															������ϸ��ַ��
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
														 id="invoicePersonAddress" name="invoicePersonAddress" />
														
														</td>
												   </tr>
													</c:if>
												<c:if test="${invoiceStatus eq 1}">
												
												  <div id="companyinvoice">
												    
											       <c:if test="${invoiceinform.invoicetype eq 1}">
											        <tr class="companyinvoice">
														<td align="right" width="20%">��˾���ƣ�</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[100],custom[onlyLetterNumberCN]] input_text"
														 id="invoiceCompanyName" name="invoiceCompanyName" value="${invoiceinform.companyname }"/>
														
														</td>
														<td align="right" width="20%">
															��˾ע���ַ��
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
														 id="invoiceCompanyAddress" name="invoiceCompanyAddress" value="${invoiceinform.address }"/>
															
														</td>
													</tr >
													<tr class="companyinvoice">
														<td align="right" width="20%">
															��˰��ʶ���룺
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[100],custom[onlyCapitalLetterNumber]] input_text"
														 id="invoiceDutyParagraph" name="invoiceDutyParagraph" value="${invoiceinform.dutyparagraph }"/>
															
														</td>
														<td align="right" width="20%">
															�������У�
														</td>
														<td width="30%"><input type="text" class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
														 id="invoiceCompanyBank" name="invoiceCompanyBank" value="${invoiceinform.bank }"/>
													
														</td>
													</tr>
													<tr class="companyinvoice">
														<td align="right" width="20%">
															�����ʺţ�
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[32],custom[onlyNumberSp]] input_text"
														 id="invoiceCompanyBankAccount" name="invoiceCompanyBankAccount" value="${invoiceinform.bankaccount }"/>
															
														</td>
														<td align="right" width="20%">
															��˾ע��绰��
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[16],custom[phoneOrTel]] input_text"
														 id="invoiceCompanyPhone" name="invoiceCompanyPhone" value="${invoiceinform.phone }"/>
														
														</td>
													</tr>
													  <tr class="personinvoice" style="display: none;" >
														<td align="right" width="20%">
															��Ʊ��������
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
														 id="invoicePerson" name="invoicePerson" />
														
														</td>
														<td align="right" width="20%">
														���˵绰��
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[16],custom[phone]] input_text"
														 id="invoicePersonPhone" name="invoicePersonPhone" />
															
														</td>
													</tr>
													<tr class="personinvoice"  style="display: none;" >
														<td align="right" width="20%">
															������ϸ��ַ��
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
														 id="invoicePersonAddress" name="invoicePersonAddress" />
														
														</td>
												   </tr>
												</c:if>
												</div>
											 <div>
												<c:if test="${invoiceinform.invoicetype eq 0 }">
												<tr class="companyinvoice"  style="display: none;">
														<td align="right" width="20%">��˾���ƣ�</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[100],custom[onlyLetterNumberCN]] input_text"
														 id="invoiceCompanyName" name="invoiceCompanyName" />
														
														</td>
														<td align="right" width="20%">
															��˾ע���ַ��
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
														 id="invoiceCompanyAddress" name="invoiceCompanyAddress" />
															
														</td>
													</tr >
													<tr class="companyinvoice"  style="display: none;">
														<td align="right" width="20%">
															��˰��ʶ���룺
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[100],custom[onlyCapitalLetterNumber]] input_text"
														 id="invoiceDutyParagraph" name="invoiceDutyParagraph" />
															
														</td>
														<td align="right" width="20%">
															�������У�
														</td>
														<td width="30%"><input type="text" class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
														 id="invoiceCompanyBank" name="invoiceCompanyBank" />
													
														</td>
													</tr>
													<tr class="companyinvoice"  style="display: none;">
														<td align="right" width="20%">
															�����ʺţ�
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[32],custom[onlyNumberSp]] input_text"
														 id="invoiceCompanyBankAccount" name="invoiceCompanyBankAccount" />
															
														</td>
														<td align="right" width="20%">
															��˾ע��绰��
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[16],custom[phoneOrTel]] input_text"
														 id="invoiceCompanyPhone" name="invoiceCompanyPhone" />
														
														</td>
													</tr>
												  <tr class="personinvoice" >
														<td align="right" width="20%">
															��Ʊ��������
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
														 id="invoicePerson" name="invoicePerson" value="${invoiceinform.name }"/>
														
														</td>
														<td align="right" width="20%">
														���˵绰��
														</td>
														<td width="20%">
														<input type="text" class="validate[required,maxSize[16],custom[phone]] input_text"
														 id="invoicePersonPhone" name="invoicePersonPhone" value="${invoiceinform.phone }"/>
															
														</td>
													</tr>
													<tr class="personinvoice" >
														<td align="right" width="20%">
															������ϸ��ַ��
														</td>
														<td width="30%">
														<input type="text" class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
														 id="invoicePersonAddress" name="invoicePersonAddress" value="${invoiceinform.address }"/>
														
														</td>
												   </tr>
												</c:if>
													</div>
												
													</c:if>
												</c:if>
													<!-- �޸ķ�Ʊ��Ϣ ����-->
													
													</c:if>
													
												</table>
											</div>
										</td>
									</tr>
								</table>

							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<c:forEach var="map" items="${tpmap}">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													${map.key.name}
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<c:if test="${not empty map.value }">
														<c:set var="propertysize"
															value="${fn:length(map.value)}"></c:set>
														<tr>
															<c:forEach var="property"
																items="${map.value }" varStatus="status">
																<c:if
																	test="${(status.count-1)%2==0 and status.count!=1}">
														</tr>
														<tr>
													</c:if>
													<td align="right" width="20%" scope="row">
														${property.propertyName}��
													</td>
													<td width="30%">
														${property.propertyValue}
													</td>
													</c:forEach>
													<c:if test="${propertysize%2!=0}">
														<c:forEach begin="1" end="${2-(propertysize%2)}">
															<th align="center" valign="middle" scope="row">
																&nbsp;
															</th>
															<td align="center" valign="middle">
																&nbsp;
															</td>
														</c:forEach>
														
													</c:if>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
									</c:forEach>
								</table>
							</td>
						</tr>

						<tr>
							<td align="right">
								<c:if test="${num == 1}">	
								<rightButton:rightButton name="�޸�" onclick=""
									className="btn_sec" action="/stock/exitlist/stockOutUpdate.action"
									id="updatest"></rightButton:rightButton></c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn_sec" onClick=window.close();>
									�ر�
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>