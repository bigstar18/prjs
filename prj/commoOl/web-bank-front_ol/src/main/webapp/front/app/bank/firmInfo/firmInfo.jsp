<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ǩԼ����</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
		var flag = '${flag}';
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			
			$("#performBtn").click(function(){<%//ִ��ת��%>
				if($("#subfrm").validationEngine('validate')){
				//alert(${ABCMarketCode});
					if(affirm("��ȷ��Ҫִ�д˲�����")){
						var bankID = $("#bankID").val();
						var isCross = $("#isCrossLine").val();
						var rgstdelType = $("#rgstdelType").val();
						if(bankID=="050"){
							subfrm.action="${basePath}/bank/firmInfo/gotoABCRgstDelFirmInfoPage.action";
						}
						if("17"==bankID&&"0"==isCross){
							subfrm.action="${basePath}/bank/firmInfo/synchroRegist.action";
						}
						if("17"==bankID&&"1"==isCross){
						
							subfrm.action="${basePath}/bank/firmInfo/gotoHXCrossFirmInfoPage.action?";
						}
						if(("17"==bankID||"12"==bankID)&&"1"==rgstdelType){
							subfrm.action="${basePath}/bank/firmInfo/delRegist.action";
						}
						if(("12"==bankID)&&"0"==rgstdelType){
							subfrm.action="${basePath}/bank/firmInfo/openRegist.action";
						}
						if("66"==bankID){
							subfrm.action="${basePath}/bank/firmInfo/gotoRgstDelFirmInfoPageGFB.action?";
						}
						$("#subfrm").submit();
					}
				}
			});
	    });
		function showPasswordTr(){<%//ͨ�����б��ȷ���Ƿ�Ҫ��ʾ����%>
			var bankIDs='';
			var bankID = $("#bankID").val();
			if($("#rgstdelType").val()=='0'){
				bankIDs='${rgstAccountNeedPasswordBankID}';
				//if("050"==bankID){
				//	document.getElementById("abcNote").style.display="block";
				//}else{
				//	document.getElementById("abcNote").style.display="none";
				//}
			}else if($("#rgstdelType").val()=='1'){
				bankIDs='${delAccountNeedPasswordBankID}';
				//document.getElementById("abcNote").style.display="none";
			}else{
				//document.getElementById("abcNote").style.display="none";
			}
			if("17"==bankID&&$("#rgstdelType").val()=='0'){
				document.getElementById("isCross").style.display="block";			
			}else{
				var aa=document.getElementById("isCross");
				if(aa!=null){
					document.getElementById("isCross").style.display="none";
				}
				
			}
			
			if(bankIDs && bankID && bankCheckPwd(bankIDs,bankID)){
				document.getElementById("passwordtr").style.display="inline";
				document.getElementById("passwordth").innerText="�������룺";
				document.getElementById("password").className="validate[required,custom[onlyNumberSp],minSize[6],maxSize[6]]";
			}else{//���δѡ��������Ϣ
				if(${isCheckMarketPassword}){
					document.getElementById("passwordtr").style.display="inline";
					document.getElementById("passwordth").innerText="���׹�˾���룺";
					document.getElementById("password").className="validate[required,custom[password],maxSize[16]]";
				}else{
					document.getElementById("passwordtr").style.display="none";
					document.getElementById("password").className="";
				}
			}
	    	$("#subfrm").validationEngine("attach");
		}
		function bankCheckPwd(bankIDs,bankID){<%//�����Ƿ��ж�����%>
			if(!(bankIDs && bankIDs.length>=bankID.length)){<%//���δ������Ҫ�ж���������У�����Ϊ���в���Ҫ�ж�%>
				return false;
			}
			if(bankIDs==bankID){<%//���������ȣ�˵��ֻ����������ж�����%>
				return true;
			}
			if(bankIDs.indexOf(bankID+",")==0){<%//���������Ϣ�Ա�������Ϣ��ͷ����Ϊ��Ҫ��֤����%>
				return true;
			}
			bankIDs += ",";
			if(bankIDs.indexOf(","+bankID+",")>=0){<%//���������Ϣ�а�����������Ϊ��Ҫ��֤%>
				return true;
			}
			return false;
		}
		function abcOnclick(){
			window.open('http://www.abchina.com/cn/','',
			'height=500, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
		}
		function showMsgBoxCallbak(result,msg){<%//�ص�����%>
			$("#frm").submit();
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoRgstDelFirmInfoPage.action"></form>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					��ܰ��ʾ:
				</div>
				<div class="content">�ڴ������Բ�ѯ���ڽ��׹�˾����Ϣ��</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/rgstDelFirmInfo.action" method="post" >
				<input type="hidden" name="MerchantID" id="MerchantID" value="XXXXXXXXXXXXX"/>
				<input type="hidden" name="MerchantName" id="MerchantName" value="XXXXXXXXXXXXXX"/>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						
						<tr>
							<th scope="row">ѡ�����У� </th>
							<td>
								<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">��ѡ��</option>
									<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
										<option value="${firmIDAndAccount.bank.bankID}">${firmIDAndAccount.bank.bankName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" width="30%">��Ա���룺 </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.firm.firmID}
							<input type="hidden" id="firmID"  name="firmID" value="${firmIDAndAccount.firm.firmID}" />
							</td>
							</c:forEach>
							
						</tr>
						<tr>
							<th scope="row" width="30%">�����˺ţ� </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.account}
							<input type="hidden" name="account" id="account" value="${firmIDAndAccount.account}">
							</td>
							</c:forEach>
						</tr>
						
						<tr>
							<th scope="row" width="30%">�˺����ƣ� </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.accountName}
							<input type="hidden" name="accountName" id="accountName" value="${firmIDAndAccount.accountName}">
							</td>
							</c:forEach>
						</tr>
						<tr>
							<th scope="row" width="30%">֤�����룺 </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.card}</td>
							</c:forEach>
						</tr>
						
						<tr id="passwordtr" <c:if test="${!isCheckMarketPassword}">style="display: none;"</c:if>>
							<th scope="row" id="passwordth">��֤���룺</th>
							<td>
								<input id="password" type="password" name="password" class="validate[required,custom[password],maxSize[16]]" style="width: 127px;"/>
								&nbsp;<script type="">showPasswordTr();</script>
							</td>
						</tr>
						
						
						<tr>
							<th width="14%" align="center">����ѡ��</th>
							<td>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
								<select id="rgstdelType" name="rgstdelType" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">��ѡ��</option>
									<option value="0" <c:if test="${firmIDAndAccount.isOpen==1}">disabled="disabled"</c:if>>ǩԼ</option>
									<option value="1" <c:if test="${firmIDAndAccount.isOpen!=1}">disabled="disabled"</c:if>>��Լ</option>
								</select>
							</c:forEach>
							</td>
						</tr>
						<tr id="isCross" name="isCross"  style="display: none;">
							<th width="14%" align="center">����ѡ��</th>
							<td>
							
								<select id="isCrossLine" name="isCrossLine" class="validate[required]" style="width: 127px;" >									
									<option value="0" >����</option>
									<option value="1" >����</option>
								</select>
							
							</td>
						</tr>
						<tr id ="abcNote" name="abcNote" style="display:none;">
							<th width="14%" align="center">ע�����</th>
							<td>
								ǩԼ�ɹ�֮�������<a href="#" onclick="abcOnclick()">ũ������</a>���а󶨽����˺Ų�����
							</td>
						</tr>
						
					</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="ȷ��" class="btbg"/>
							</span>
						</label>
					</div>
				</form>
				
			</div>
		</div>
		</form>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>