<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<base target="_self"/> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>仓单<c:if test="${HaveWarehouse eq 1}">出库</c:if><c:if test="${HaveWarehouse eq 0}">出库申请</c:if></title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${basePath }/front/app/bill/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
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
		function showMsgBoxCallbak(result){
			if(result==1){
				closeDialog(1);
			}else{
				clearSubmitCount();
			}
		}
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
				  	if(value==0){ //不开具发票
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
				  	else if(value==1){//个人发票
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
					else if (value == 2){//公司发票
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
	</head>
	<body>
		<div class="main">
			<div class="warning">
				<div class="title font_orange_14b">温馨提示:</div>
				<div class="content">输入正确的仓单<c:if test="${HaveWarehouse eq 1}">出库</c:if><c:if test="${HaveWarehouse eq 0}">出库申请</c:if>的相关信息，否则出库失败！</div>
			</div>
			<iframe  name="hiddenframe" width=0 height=0  application='yes'></iframe>
			<form id="frm" name="frm" target="hiddenframe"
				action="${frontPath}/bill/unregister/exitStock.action"
				method="post">
				<div class="form margin_10b">
					<div class="column1">
						仓单<c:if test="${HaveWarehouse eq 1}">出库</c:if><c:if test="${HaveWarehouse eq 0}">出库申请</c:if>：
					</div>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row">
								仓单号：
								<span class="font_orange_12">*</span>
							</th>
							<td>
								<input type="hidden" name="stockId" value="${entity.stockId }"/>
								${entity.stockId }
							</td>
						</tr>
						<!-- 有仓版仓单出库 -->
						<c:if test="${HaveWarehouse eq 1}">
						<tr>
							<th scope="row">
								仓库系统账户编号：
								<span class="font_orange_12">*</span>
							</th>
							<td>
								<div class="validInput"><input type="input" id="userId" name="userId"
									class="validate[required] input_text"  value=""/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入账户编号！
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">
								仓库系统账户名：
								<span class="font_orange_12">*</span>
							</th>
							<td><div class="validInput">
								<input type="input" id="userName" name="userName"
									class="validate[required] input_text" value=""/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入账户名！&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">
								仓库系统验证密码：
								<span class="font_orange_12">*</span>
							</th>
							<td>
								<div class="validInput"><input type="password" id="password" name="password"
									class="validate[required] input_text" value=""/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入验证密码！
								</div>
							</td>
						</tr>
						</c:if>
						
						<!-- 无仓版仓单出库申请 -->
						<c:if test="${HaveWarehouse eq 0}">
						
						<tr>
							<th scope="row">
								提货方式：
								<span class="font_orange_12">*</span>
							</th>
							<td>
								<select id="deliveryStatus" name="deliveryStatus" class="validate[required]" >
									<option value="0">用户自提</option>
									<option value="1">配送</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">
								提货人：
								<span class="font_orange_12">*</span>
							</th>
							<td><div class="validInput">
								<input type="input" id="deliveryPerson" name="deliveryPerson"
									class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text" value=""/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入提货人！&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
						<tr id="idnumbertr">
							<th scope="row">
								提货人身份证号：
								<span class="font_orange_12">*</span>
							</th>
							<td><div class="validInput">
								<input type="input" id="idnumber" name="idnumber"
									class="validate[required,custom[id],maxSize[18]] input_text" value=""/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入提货人身份证号！&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
						<tr id="addresstr">
							<th scope="row">
								邮寄地址：
								<span class="font_orange_12">*</span>
							</th>
							<td><div class="validInput">
								<input type="input" class="validate[required,maxSize[32]] input_text" id="address"
									name="address"/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入邮寄地址！&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
						<tr id="phonetr">
							<th scope="row">
								电话：
								<span class="font_orange_12">*</span>
							</th>
							<td><div class="validInput">
								<input type="input" class="validate[required,maxSize[32],custom[phone]] input_text" id="phone"
									name="phone"/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入电话！&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
						<tr>
									<th align="right">发票选项：<span class="font_orange_12">*</span>
									</th>
									<td><select id="invoiceStatus" name="invoiceStatus"
										class="validate[required]">
											<option value="0">不需要发票</option>
											<option value="1">个人发票</option>
											<option value="2">公司发票</option>
									</select></td>
								</tr>
								<tr id="invoicePersonDiv">
									<th align="right">收票人姓名：<span class="font_orange_12">*</span></th>
									<td ><input type="text"
										class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
										id="invoicePerson" name="invoicePerson" /></td>
								</tr>
								<tr id="invoicePersonPhoneDiv">
									<th align="right">收票人电话：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[16],custom[phone]] input_text"
										id="invoicePersonPhone" name="invoicePersonPhone" /></td>
								</tr>
								<tr id="invoicePersonAddressDiv">
									<th align="right">收票人地址：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
										id="invoicePersonAddress" name="invoicePersonAddress" />
									</td>
								</tr>
								<tr id="invoiceCompanyNameDiv">
									<th align="right" >公司名称：
										<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[100],custom[onlyLetterNumberCN]] input_text"
										id="invoiceCompanyName" name="invoiceCompanyName" /></td>
								</tr>
								<tr id="invoiceDutyParagraphDiv">
									<th align="right" >纳税人识别码：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[100],custom[onlyCapitalLetterNumber]] input_text"
										id="invoiceDutyParagraph" name="invoiceDutyParagraph" />
									</td>
								</tr>
								<tr id="invoiceCompanyPhoneDiv">
									<th align="right">注册电话：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[16],custom[phoneOrTel]] input_text"
										id="invoiceCompanyPhone" name="invoiceCompanyPhone" /></td>
								</tr>
								<tr id="invoiceCompanyAddressDiv">
									<th align="right">注册地址：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[200],custom[onlyLetterNumberCN_]] input_text"
										id="invoiceCompanyAddress" name="invoiceCompanyAddress" />
									</td>
								</tr>
								<tr id="invoiceCompanyBankDiv">
									<th align="right">开户银行：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[32],custom[onlyLetterCN]] input_text"
										id="invoiceCompanyBank" name="invoiceCompanyBank" /></td>
								</tr>
								<tr id="invoiceCompanyBankAccountDiv">
									<th align="right">银行账号：<span class="font_orange_12">*</span>
									</th>
									<td ><input type="text"
										class="validate[required,maxSize[32],custom[onlyNumberSp]] input_text"
										id="invoiceCompanyBankAccount"
										name="invoiceCompanyBankAccount" /></td>
								</tr>
						
						</c:if>
					</table>
					<div class="page text_center">
					<label><input type="button" id="update" value="出库" class="btbg"/></label>&nbsp;&nbsp;
					<label><input type="button" value="关闭" onclick="window.close();" class="btbg"/></label>
				</div>
				</div>
			</form>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/dialogmessage.jsp"%>