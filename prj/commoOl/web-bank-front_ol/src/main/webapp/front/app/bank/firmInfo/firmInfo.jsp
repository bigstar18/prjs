<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>签约操作</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
		var flag = '${flag}';
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			
			$("#performBtn").click(function(){<%//执行转账%>
				if($("#subfrm").validationEngine('validate')){
				//alert(${ABCMarketCode});
					if(affirm("您确认要执行此操作吗？")){
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
		function showPasswordTr(){<%//通过银行编号确认是否要显示密码%>
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
				document.getElementById("passwordth").innerText="银行密码：";
				document.getElementById("password").className="validate[required,custom[onlyNumberSp],minSize[6],maxSize[6]]";
			}else{//如果未选定银行信息
				if(${isCheckMarketPassword}){
					document.getElementById("passwordtr").style.display="inline";
					document.getElementById("passwordth").innerText="交易公司密码：";
					document.getElementById("password").className="validate[required,custom[password],maxSize[16]]";
				}else{
					document.getElementById("passwordtr").style.display="none";
					document.getElementById("password").className="";
				}
			}
	    	$("#subfrm").validationEngine("attach");
		}
		function bankCheckPwd(bankIDs,bankID){<%//银行是否判断密码%>
			if(!(bankIDs && bankIDs.length>=bankID.length)){<%//如果未设置需要判断密码的银行，则认为银行不需要判断%>
				return false;
			}
			if(bankIDs==bankID){<%//如果两个相等，说明只有这个银行判断密码%>
				return true;
			}
			if(bankIDs.indexOf(bankID+",")==0){<%//如果设置信息以本银行信息开头，认为需要验证密码%>
				return true;
			}
			bankIDs += ",";
			if(bankIDs.indexOf(","+bankID+",")>=0){<%//如果设置信息中包含本银行认为需要验证%>
				return true;
			}
			return false;
		}
		function abcOnclick(){
			window.open('http://www.abchina.com/cn/','',
			'height=500, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
		}
		function showMsgBoxCallbak(result,msg){<%//回掉函数%>
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
					温馨提示:
				</div>
				<div class="content">在此您可以查询您在交易公司的信息。</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/rgstDelFirmInfo.action" method="post" >
				<input type="hidden" name="MerchantID" id="MerchantID" value="XXXXXXXXXXXXX"/>
				<input type="hidden" name="MerchantName" id="MerchantName" value="XXXXXXXXXXXXXX"/>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						
						<tr>
							<th scope="row">选择银行： </th>
							<td>
								<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">请选择</option>
									<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
										<option value="${firmIDAndAccount.bank.bankID}">${firmIDAndAccount.bank.bankName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" width="30%">会员代码： </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.firm.firmID}
							<input type="hidden" id="firmID"  name="firmID" value="${firmIDAndAccount.firm.firmID}" />
							</td>
							</c:forEach>
							
						</tr>
						<tr>
							<th scope="row" width="30%">银行账号： </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.account}
							<input type="hidden" name="account" id="account" value="${firmIDAndAccount.account}">
							</td>
							</c:forEach>
						</tr>
						
						<tr>
							<th scope="row" width="30%">账号名称： </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.accountName}
							<input type="hidden" name="accountName" id="accountName" value="${firmIDAndAccount.accountName}">
							</td>
							</c:forEach>
						</tr>
						<tr>
							<th scope="row" width="30%">证件号码： </th>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
							<td>${firmIDAndAccount.card}</td>
							</c:forEach>
						</tr>
						
						<tr id="passwordtr" <c:if test="${!isCheckMarketPassword}">style="display: none;"</c:if>>
							<th scope="row" id="passwordth">验证密码：</th>
							<td>
								<input id="password" type="password" name="password" class="validate[required,custom[password],maxSize[16]]" style="width: 127px;"/>
								&nbsp;<script type="">showPasswordTr();</script>
							</td>
						</tr>
						
						
						<tr>
							<th width="14%" align="center">操作选择：</th>
							<td>
							<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
								<select id="rgstdelType" name="rgstdelType" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">请选择</option>
									<option value="0" <c:if test="${firmIDAndAccount.isOpen==1}">disabled="disabled"</c:if>>签约</option>
									<option value="1" <c:if test="${firmIDAndAccount.isOpen!=1}">disabled="disabled"</c:if>>解约</option>
								</select>
							</c:forEach>
							</td>
						</tr>
						<tr id="isCross" name="isCross"  style="display: none;">
							<th width="14%" align="center">类型选择：</th>
							<td>
							
								<select id="isCrossLine" name="isCrossLine" class="validate[required]" style="width: 127px;" >									
									<option value="0" >本行</option>
									<option value="1" >他行</option>
								</select>
							
							</td>
						</tr>
						<tr id ="abcNote" name="abcNote" style="display:none;">
							<th width="14%" align="center">注意事项：</th>
							<td>
								签约成功之后请进入<a href="#" onclick="abcOnclick()">农行网银</a>进行绑定结算账号操作！
							</td>
						</tr>
						
					</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="确认" class="btbg"/>
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