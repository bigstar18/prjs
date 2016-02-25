<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>填写签约信息页面</title>
       <link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>		
	<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			
			$("#preRgs").click(function(){//解约约
				if($("#subfrm").validationEngine('validate')){
					if(affirm("您确认要执行此操作吗？")){
						subfrm.action="${basePath}/bank/firmInfo/delRegistByPlat.action";
						$("#subfrm").submit();
					}
				}
			});
			
	    });
	
		function showMsgBoxCallbak(result,msg){<%//回掉函数%>
				$("#frm").submit();
		}
	function getRequest(){
			try{
				req=new XMLHttpRequest();
			}catch(tryMs){
				try{
					req=new ActiveXObject("Msxml2.XMLHTTP");
				}catch(otherMs){
					try{
						req=new ActiveXObject("Microsoft.XMLHTTP");
					}catch(failed){
						req=null;
					}
				}
			}
			return req;
	}
	function getInfo(){
		var req=getRequest();
		var bankID=document.getElementById("bankID").value;
		//alert(bankID);
		var url="${basePath}/bank/firmInfo/getFirmInfo.action?bankID="+bankID;
		req.open("GET", url, true);
		req.onreadystatechange = callback;
		req.send(null);		
	}	
	function callback(){
			if (req.readyState == 4) {
		//alert("进入回调函数");
		//alert("返回的状态吗："+request.status);
			if (req.status == 200) {
				//alert("返回码正常为200");
				var response = req.responseText;
				//if(response.trim()!=","){
				//alert(response);
					var strArray=new Array(4);
					strArray=response.split(",");
					
					//alert(strArray[0]+"==="+strArray[1]+"==="+strArray[2]);
					document.getElementById('account').value=strArray[0];
					document.getElementById('accountName').value=strArray[1];
					document.getElementById('card').value=strArray[2];
					document.getElementById('cardType').value=strArray[3];
					//document.frm.card.value=strArray[2];
					//$("#account").value=strArray[0];
					//$("#accountName").value=strArray[1];
					//$("#card").value=strArray[2];
					//alert(strArray[2]);
				//}
			}
		}
	}
</script>	
  </head>
  
  <body>
   <form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoFirmInfoPage.action"></form>
  <iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以进行填写签约交易商信息操作。</div>
			</div>
			<div class="clear"></div>
			<div id="u24" class="u24"  >						
			<div id="message" class="form margin_10b">			
						
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/delRegistByPlat.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
			    <tr>
			        <td align="right">签约的银行：&nbsp;</td>
					<td align="left">
						<select id="bankID" name="bankID" class="validate[required]" onchange="getInfo();" style="width: 127px;">
							<option value="">请选择</option>
							<c:forEach var="bankIDs" items="${vecBanks}">
								  <option value="${bankIDs.bankID}">${bankIDs.bankName}</option>
							</c:forEach>
						</select>
					</td>
			    </tr>
			    <tr>
					<td align="right">银行帐号：&nbsp;</td>
					<td align="left">
						<input id="account" name="account"  class="validate[required,maxSize[30]]" readonly="readonly"/><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">账户名：&nbsp;</td>
					<td align="left">
						<input id="accountName" name="accountName" class="validate[required,maxSize[30]]" readonly="readonly"><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType" class="validate[required,maxSize[30]]" readonly="readonly">
							<option value="">请选择</option>
							<option value="1">身份证</option>
							<option value="8">组织机构代码</option>
							<option value="9">营业执照</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input id="card" name="card" value="" type=text class="validate[required,maxSize[30]]" readonly="readonly"><span>*</span>
					</td>
				</tr>

				<tr>
						<th>输入资金密码：&nbsp;</th>
						<td><input type="password" name="pwd" id="pwd" class="validate[required,maxSize[6]]"/>预设平台资金密码,用于出入金,签解约</td>
					</tr>
					 <tr>
						<th>输入银行账户密码：&nbsp;</th>
						<td><input type="password" name="bankpwd" id="bankpwd" class="validate[required,maxSize[30]]"/></td>
					</tr>
			</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="preRgs"  value="确认" class="btbg"/>
							</span>
						</label>
					</div>
					
				
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
